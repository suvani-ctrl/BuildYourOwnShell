package utils;

import java.util.Collections;
import java.util.List;

/**
 * Parses tokenized input into a command name, arguments, and optional stdout
 * redirection.
 */
public final class CommandLineParser {

    public record ParsedCommand(
            String commandName,
            List<String> args,
            String stdoutFile,
            boolean append) {
    }

    private CommandLineParser() {
    }

    public static ParsedCommand parse(List<String> tokens) {
        if (tokens.isEmpty()) {
            return new ParsedCommand("", Collections.emptyList(), null, false);
        }

        int redirectIndex = -1;
        boolean append = false;

        for (int i = 0; i < tokens.size(); i++) {
            String token = tokens.get(i);
            if (">".equals(token)) {
                redirectIndex = i;
                append = false;
                break;
            }
            if (">>".equals(token)) {
                redirectIndex = i;
                append = true;
                break;
            }
        }

        if (redirectIndex == -1) {
            return new ParsedCommand(
                    tokens.get(0),
                    List.copyOf(tokens.subList(1, tokens.size())),
                    null,
                    false);
        }

        String stdoutFile = redirectIndex + 1 < tokens.size()
                ? tokens.get(redirectIndex + 1)
                : null;

        List<String> commandTokens = tokens.subList(0, redirectIndex);
        if (commandTokens.isEmpty()) {
            return new ParsedCommand("", Collections.emptyList(), stdoutFile, append);
        }

        return new ParsedCommand(
                commandTokens.get(0),
                List.copyOf(commandTokens.subList(1, commandTokens.size())),
                stdoutFile,
                append);
    }
}
