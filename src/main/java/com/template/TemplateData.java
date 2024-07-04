package com.template;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class TemplateData {
    private Map<String, String> parameters;
    private Map<String, List<TemplateRow>> rows;

    public TemplateData() {
        this.parameters = new HashMap<>();
        this.rows = new HashMap<>();
    }

    public void addParameter(String key, String value) {
        this.parameters.put(key, value);
    }

    public void createTemplateRow(String name) {
        rows.put(name, new ArrayList<>());
    }

    public void addRow(String listName, TemplateRow row) {
        if (!rows.containsKey(listName))
            createTemplateRow(listName);
        rows.get(listName).add(row);
    }
}
