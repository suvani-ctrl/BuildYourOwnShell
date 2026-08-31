package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Parses tokenized input into a command name, arguments, and optional stdout
 * or stderr redirection.
 */
public final class CommandLineParser {

    public record ParsedCommand(
            String commandName,
            List<String> args,
            String stdoutFile,
            boolean stdoutAppend,
            String stderrFile,
            boolean stderrAppend) {
    }

    private CommandLineParser() {
    }

    public static ParsedCommand parse(List<String> tokens) {
        if (tokens.isEmpty()) {
            return emptyParsed();
        }

        List<String> commandTokens = new ArrayList<>();
        String stdoutFile = null;
        boolean stdoutAppend = false;
        String stderrFile = null;
        boolean stderrAppend = false;

        for (int i = 0; i < tokens.size(); i++) {
            String token = tokens.get(i);

            if ("2>>".equals(token)) {
                if (i + 1 < tokens.size()) {
                    stderrFile = tokens.get(++i);
                    stderrAppend = true;
                }
                continue;
            }

            if ("2>".equals(token)) {
                if (i + 1 < tokens.size()) {
                    stderrFile = tokens.get(++i);
                    stderrAppend = false;
                }
                continue;
            }

            if ("2".equals(token) && i + 1 < tokens.size()) {
                String next = tokens.get(i + 1);
                if (">>".equals(next) && i + 2 < tokens.size()) {
                    stderrFile = tokens.get(i + 2);
                    stderrAppend = true;
                    i += 2;
                    continue;
                }
                if (">".equals(next) && i + 2 < tokens.size()) {
                    stderrFile = tokens.get(i + 2);
                    stderrAppend = false;
                    i += 2;
                    continue;
                }
            }

            if ("1>>".equals(token)) {
                if (i + 1 < tokens.size()) {
                    stdoutFile = tokens.get(++i);
                    stdoutAppend = true;
                }
                continue;
            }

            if ("1>".equals(token)) {
                if (i + 1 < tokens.size()) {
                    stdoutFile = tokens.get(++i);
                    stdoutAppend = false;
                }
                continue;
            }

            if ("1".equals(token) && i + 1 < tokens.size()) {
                String next = tokens.get(i + 1);
                if (">>".equals(next) && i + 2 < tokens.size()) {
                    stdoutFile = tokens.get(i + 2);
                    stdoutAppend = true;
                    i += 2;
                    continue;
                }
                if (">".equals(next) && i + 2 < tokens.size()) {
                    stdoutFile = tokens.get(i + 2);
                    stdoutAppend = false;
                    i += 2;
                    continue;
                }
            }

            if (">>".equals(token)) {
                if (i + 1 < tokens.size()) {
                    stdoutFile = tokens.get(++i);
                    stdoutAppend = true;
                }
                continue;
            }

            if (">".equals(token)) {
                if (i + 1 < tokens.size()) {
                    stdoutFile = tokens.get(++i);
                    stdoutAppend = false;
                }
                continue;
            }

            commandTokens.add(token);
        }

        if (commandTokens.isEmpty()) {
            return new ParsedCommand("", Collections.emptyList(),
                    stdoutFile, stdoutAppend, stderrFile, stderrAppend);
        }

        return new ParsedCommand(
                commandTokens.get(0),
                List.copyOf(commandTokens.subList(1, commandTokens.size())),
                stdoutFile,
                stdoutAppend,
                stderrFile,
                stderrAppend);
    }

    private static ParsedCommand emptyParsed() {
        return new ParsedCommand("", Collections.emptyList(), null, false, null, false);
    }
}
