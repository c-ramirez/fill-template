package com.template;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.regex.Pattern;

public class FileUtil {
	private FileUtil() {
	}

	public static String getTextFromFile(String path) throws IOException {
		File file = new File(FileUtil.class.getResource(path).getPath());
		Pattern pat = Pattern.compile(".*\\R|.+\\z");
		StringBuilder textFromFile = new StringBuilder();
		try (Scanner in = new Scanner(file, StandardCharsets.UTF_8)) {
			String line;
			while ((line = in.findWithinHorizon(pat, 0)) != null) {// We include break line
				textFromFile.append(line);
			}
			return textFromFile.toString();
		}

	}

	public static void writeTextToFile(String path, String text) throws IOException {
		File file = new File(path);
		try (BufferedWriter w = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
			w.write(text);
		}
	}
}
