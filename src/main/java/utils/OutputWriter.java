package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import shell.ExecutionContext;

public final class OutputWriter {

    private OutputWriter() {
    }

    public static void println(String message, ExecutionContext context) throws IOException {
        if (context.hasStdoutRedirect()) {
            Path path = Path.of(context.getStdoutFile());
            String line = message + "\n";
            if (context.isAppend()) {
                Files.writeString(path, line, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            } else {
                Files.writeString(path, line, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            }
        } else {
            System.out.println(message);
        }
    }
}
