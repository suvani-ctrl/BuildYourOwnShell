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
            writeToFile(message + "\n", context.getStdoutFile(), context.isStdoutAppend());
        } else {
            System.out.println(message);
        }
    }

    public static void printErrln(String message, ExecutionContext context) throws IOException {
        if (context.hasStderrRedirect()) {
            writeToFile(message + "\n", context.getStderrFile(), context.isStderrAppend());
        } else {
            System.err.println(message);
        }
    }

    private static void writeToFile(String content, String filePath, boolean append) throws IOException {
        Path path = Path.of(filePath);
        if (append) {
            Files.writeString(path, content, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } else {
            Files.writeString(path, content, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        }
    }
}
