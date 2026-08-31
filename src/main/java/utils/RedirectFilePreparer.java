package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import shell.ExecutionContext;

/**
 * Creates redirect target files before command execution, matching shell
 * behavior where redirection streams are set up prior to running the command.
 */
public final class RedirectFilePreparer {

    private RedirectFilePreparer() {
    }

    public static void prepare(ExecutionContext context) throws IOException {
        if (context.hasStdoutRedirect()) {
            prepareFile(context.getStdoutFile(), context.isStdoutAppend());
        }
        if (context.hasStderrRedirect()) {
            prepareFile(context.getStderrFile(), context.isStderrAppend());
        }
    }

    private static void prepareFile(String filePath, boolean append) throws IOException {
        Path path = Path.of(filePath);

        if (append) {
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
        } else {
            Files.writeString(path, "", StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        }
    }
}