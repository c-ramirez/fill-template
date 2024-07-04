package com.template;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class Pair<K, V> {
    private K key;
    private V value;
}