package utils;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class CompactUtils {

	public static void writeContentToFile(String filePath, String content) {
		PrintWriter out;
		try {
			out = new PrintWriter(filePath);
			out.println(content);
			out.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

}
