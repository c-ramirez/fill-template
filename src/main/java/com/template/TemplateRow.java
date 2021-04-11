package com.template;

import java.util.ArrayList;
import java.util.List;

public class TemplateRow {
	private List<Pair<String, String>> fields;

	public TemplateRow() {
		fields = new ArrayList<>();
	}

	public void addField(Pair<String, String> pair) {
		this.fields.add(pair);
	}

	public List<Pair<String, String>> getFields() {
		return fields;
	}

	public String getValueFromKey(String key) {
		return fields.stream().filter(pair -> pair.getKey().equals(key)).findAny().map(Pair::getValue).orElse("");
	}

}
