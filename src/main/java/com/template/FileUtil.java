package com.template;

import lombok.NoArgsConstructor;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.regex.Pattern;

@NoArgsConstructor
public class FileUtil {

    public static String getTextFromFile(File file) throws IOException {
        Pattern pat = Pattern.compile(".*\\R|.+\\z");
        StringBuilder textFromFile = new StringBuilder();
        try (Scanner in = new Scanner(file, StandardCharsets.UTF_8)) {
            String line;
            while ((line = in.findWithinHorizon(pat, 0)) != null) {
                textFromFile.append(line);
            }
            return textFromFile.toString();
        }
    }

}
