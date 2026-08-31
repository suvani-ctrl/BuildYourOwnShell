package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Tokenizes a shell input line into words, respecting single quotes, double
 * quotes, and backslash escaping.
 */
public final class Tokenizer {

    private Tokenizer() {
    }

    public static List<String> tokenize(String input) {
        if (input == null || input.isBlank()) {
            return Collections.emptyList();
        }

        List<String> tokens = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inSingleQuotes = false;
        boolean inDoubleQuotes = false;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (inSingleQuotes) {
                if (c == '\'') {
                    inSingleQuotes = false;
                } else {
                    current.append(c);
                }
                continue;
            }

            if (inDoubleQuotes) {
                if (c == '"') {
                    inDoubleQuotes = false;
                } else if (c == '\\' && i + 1 < input.length()) {
                    char next = input.charAt(++i);
                    if (next == '"' || next == '\\' || next == '$' || next == '\n') {
                        current.append(next);
                    } else {
                        current.append(c).append(next);
                    }
                } else {
                    current.append(c);
                }
                continue;
            }

            if (c == '\'') {
                inSingleQuotes = true;
            } else if (c == '"') {
                inDoubleQuotes = true;
            } else if (c == '\\' && i + 1 < input.length()) {
                current.append(input.charAt(++i));
            } else if (Character.isWhitespace(c)) {
                flushToken(tokens, current);
            } else {
                current.append(c);
            }
        }

        flushToken(tokens, current);
        return tokens;
    }

    private static void flushToken(List<String> tokens, StringBuilder current) {
        if (!current.isEmpty()) {
            tokens.add(current.toString());
            current.setLength(0);
        }
    }
}
