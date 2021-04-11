package com.template;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TemplateFiller {
	private TemplateFiller() {
	}

	// regex used for parameters
	static final String PARAMETER_REGEX = "&P\\{(%s)\\}";

	// regex used for lists
	static final String LIST_STRUCTURE_REGEX = "&L\\[(.*?)\\}\\}";
	static final String COLLECTION_NAME_REGEX = "&L\\[(.*?)\\]";
	static final String COLLECTION_BODY_REGEX = "\\]\\{\\{(.*?)\\}\\}";

	// regex used for fields
	static final String FIELD_REGEX = "&F\\{(%s)\\}";
	static final String LIST_ELEMENT_REGEX = "&L\\[%s.*?\\}\\}";

	public static String getTemplateWithValues(String template, TemplateData data) {
		String value = setParametersValuesInTemplate(template, data.getParameters());
		return setFieldsValueInTemplate(value, data.getRows());
	}

	public static String setParametersValuesInTemplate(String template, Map<String, String> parameters) {
		if (parameters.isEmpty())
			return template;
		String generatedParameter = String.format(PARAMETER_REGEX, String.join("|", parameters.keySet()));
		Pattern parameterPattern = Pattern.compile(generatedParameter);
		Matcher parameterMatcher = parameterPattern.matcher(template);
		StringBuffer sb = new StringBuffer();
		while (parameterMatcher.find())
			parameterMatcher.appendReplacement(sb, escapeSpecialCharacter(parameters.get(parameterMatcher.group(1))));
		parameterMatcher.appendTail(sb);
		return sb.toString();
	}

	public static String setFieldsValueInTemplate(String template, Map<String, List<TemplateRow>> rows) {
		Pattern listStructurePattern = Pattern.compile(LIST_STRUCTURE_REGEX, Pattern.DOTALL);
		Matcher listStructureMatcher = listStructurePattern.matcher(template);

		Pattern collectionNamePattern = Pattern.compile(COLLECTION_NAME_REGEX, Pattern.DOTALL);
		Pattern collectionBodyPattern = Pattern.compile(COLLECTION_BODY_REGEX, Pattern.DOTALL);

		Map<String, String> mapFieldValue = createNamedStructures(listStructureMatcher, collectionNamePattern,
				collectionBodyPattern);
		replaceValuesInCollection(rows, mapFieldValue);
		template = replaceCollectionsInTemplate(template, mapFieldValue);
		return template;
	}

	private static String replaceCollectionsInTemplate(String template, Map<String, String> mapFieldValue) {
		for (Entry<String, String> entry : mapFieldValue.entrySet()) {
			Pattern listElementPattern = Pattern.compile(String.format(LIST_ELEMENT_REGEX, entry.getKey()),
					Pattern.DOTALL);
			Matcher listElementMatcher = listElementPattern.matcher(template);
			StringBuffer fieldValues = new StringBuffer();
			while (listElementMatcher.find()) {
				listElementMatcher.appendReplacement(fieldValues, escapeSpecialCharacter(entry.getValue()));
			}
			listElementMatcher.appendTail(fieldValues);
			template = fieldValues.toString();
		}
		return template;
	}

	private static void replaceValuesInCollection(Map<String, List<TemplateRow>> rows,
			Map<String, String> mapFieldValue) {
		for (Entry<String, String> entry : mapFieldValue.entrySet()) {
			List<TemplateRow> templateRow = rows.get(entry.getKey());
			if (templateRow == null) {
				mapFieldValue.put(entry.getKey(), "");
				continue;
			}
			StringBuilder stb = new StringBuilder();
			for (TemplateRow tempRow : templateRow) {
				final Pattern fieldPattern = Pattern.compile(
						String.format(FIELD_REGEX,
								String.join("|",
										tempRow.getFields().stream().map(Pair::getKey).collect(Collectors.toList()))),
						Pattern.DOTALL);
				Matcher fieldMatcher = fieldPattern.matcher(entry.getValue());
				StringBuffer sb = new StringBuffer();
				while (fieldMatcher.find())
					fieldMatcher.appendReplacement(sb,
							escapeSpecialCharacter(tempRow.getValueFromKey(fieldMatcher.group(1))));
				fieldMatcher.appendTail(sb);
				stb.append(sb.toString());
			}
			mapFieldValue.put(entry.getKey(), stb.toString());
		}

	}

	private static Map<String, String> createNamedStructures(Matcher listStructureMatcher,
			Pattern collectionNamePattern, Pattern collectionBodyPattern) {
		Map<String, String> mapFieldValue = new HashMap<>();
		while (listStructureMatcher.find()) {
			String group = listStructureMatcher.group();
			Matcher collectionNameMatcher = collectionNamePattern.matcher(group);
			while (collectionNameMatcher.find()) {
				String nameCollection = collectionNameMatcher.group(1);
				Matcher collectionBodyMatcher = collectionBodyPattern.matcher(group);
				while (collectionBodyMatcher.find())
					mapFieldValue.put(nameCollection, collectionBodyMatcher.group(1));
			}
		}
		return mapFieldValue;
	}

	private static String escapeSpecialCharacter(String word) {
		if (word == null)
			return "";
		String[] regexChars = new String[] { "\\", ".", "+", "*", "?", "^", "$", "(", ")", "[", "]", "{", "}", "|" };
		for (String regexChar : regexChars)
			word = word.replace(regexChar, "\\" + regexChar);
		return word;
	}
}
