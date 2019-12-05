package spring.demo.spring.demo;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class CSVUtils {
	private final static String separateor = ",";

	public static void writeRow(Writer writer, List<String> values) throws IOException {

        boolean isFirstElement = true;

        StringBuilder builder = new StringBuilder();
        for (String value : values) {
            if (!isFirstElement) {
                builder.append(separateor);
            }
            builder.append(value);
            isFirstElement = false;
        }
        builder.append("\n");
        writer.append(builder.toString());
    }
}
