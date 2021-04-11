package com.template;

import java.io.IOException;

public class Main {
	public static void main(String[] args) {
		try {
			String textFromFile = FileUtil.getTextFromFile("/sample-1.txt");

			TemplateData data = new TemplateData();
			data.addParameter("parameter1", "Texto 1");
			data.addParameter("parameter2", "Texto 2");

			TemplateRow row11 = new TemplateRow();
			row11.addField(new Pair<>("field1", "F 1 from list"));
			row11.addField(new Pair<>("field2", "F 2 from list"));
			TemplateRow row12 = new TemplateRow();
			row12.addField(new Pair<>("field1", "F 3 from list"));
			row12.addField(new Pair<>("field2", "F 4 from list"));
			TemplateRow row13 = new TemplateRow();
			row13.addField(new Pair<>("field1", "F 5 from list"));
			row13.addField(new Pair<>("field2", "F 6 from list"));

			data.addRow("list", row11);
			data.addRow("list", row12);
			data.addRow("list", row13);

			TemplateRow row21 = new TemplateRow();
			row21.addField(new Pair<>("field1", "F 1 from list 2"));
			row21.addField(new Pair<>("field2", "F 2 from list 2"));
			TemplateRow row22 = new TemplateRow();
			row22.addField(new Pair<>("field1", "F 3 from list 2"));
			row22.addField(new Pair<>("field2", "F 4 from list 2"));
			
			data.addRow("list2", row21);
			data.addRow("list2", row22);
			
			TemplateRow row31 = new TemplateRow();
			row21.addField(new Pair<>("field1", "F 1 from list 3"));
			row21.addField(new Pair<>("field2", "F 2 from list 3"));
			
			data.addRow("list3", row31);
			String templateWithValues = TemplateFiller.getTemplateWithValues(textFromFile, data);

			FileUtil.writeTextToFile("/sample-2.txt", templateWithValues);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
