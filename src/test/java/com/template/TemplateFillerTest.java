package com.template;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TemplateFillerTest {
    Logger log = Logger.getLogger(TemplateFillerTest.class.getName());

    @Test
    void parameterTest() throws IOException {
        String parameter1 = "Text 1";
        String parameter2 = "Text 2";
        File file = new File(TemplateFillerTest.class.getResource("/parameterTest.txt").getPath());
        String textFromFile = FileUtil.getTextFromFile(file);
        TemplateData data = new TemplateData();
        data.addParameter("parameter1", parameter1);
        data.addParameter("parameter2", parameter2);
        String templateWithValues = TemplateFiller.getTemplateWithValues(textFromFile, data);
        assertTrue(templateWithValues.contains(parameter1));
        assertTrue(templateWithValues.contains(parameter2));
        log.info(templateWithValues);
    }

    @Test
    void listTest() throws IOException {
        int count = 5;
        String field1 = "F-1 from ROW : ";
        String field2 = "F-2 from ROW : ";
        File file = new File(TemplateFillerTest.class.getResource("/listTest.txt").getPath());
        String textFromFile = FileUtil.getTextFromFile(file);
        TemplateData data = new TemplateData();
        data.addParameter("count", String.valueOf(count));
        for (int i = 0; i < count; i++) {
            TemplateRow row = new TemplateRow();
            row.addField(new Pair<>("field1", field1 + (i + 1)));
            row.addField(new Pair<>("field2", field2 + (i + 1)));
            data.addRow("list", row);
        }
        String templateWithValues = TemplateFiller.getTemplateWithValues(textFromFile, data);
        assertTrue(templateWithValues.contains(field1));
        assertTrue(templateWithValues.contains(field2));
        log.info(templateWithValues);
    }


    @Test
    void listMultipleTest() throws IOException {
        int count = 3;
        String field1 = "F-1 from ROW : ";
        String field2 = "F-2 from ROW : ";
        File file = new File(TemplateFillerTest.class.getResource("/multipleListTest.txt").getPath());
        String textFromFile = FileUtil.getTextFromFile(file);

        TemplateData data = new TemplateData();
        data.addParameter("parameter1", "Text Filler Test");

        for (int i = 0; i < count; i++) {
            TemplateRow row = new TemplateRow();
            row.addField(new Pair<>("field1", field1 + (i + 1)));
            row.addField(new Pair<>("field2", field2 + (i + 1)));
            data.addRow("list", row);
        }

        for (int i = 0; i < count; i++) {
            TemplateRow row = new TemplateRow();
            row.addField(new Pair<>("field1", field1 + (i + 1)));
            row.addField(new Pair<>("field2", field2 + (i + 1)));
            data.addRow("list2", row);
        }
        String templateWithValues = TemplateFiller.getTemplateWithValues(textFromFile, data);
        assertTrue(templateWithValues.contains(field1));
        assertTrue(templateWithValues.contains(field2));
        log.info(templateWithValues);
    }

}
