package com.template;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class TemplateRow {
    private final List<Pair<String, String>> fields;

    public TemplateRow() {
        fields = new ArrayList<>();
    }

    public void addField(Pair<String, String> pair) {
        this.fields.add(pair);
    }

    public String getValueFromKey(String key) {
        return fields.stream().filter(pair -> pair.getKey().equals(key)).findAny().map(Pair::getValue).orElse("");
    }

}
