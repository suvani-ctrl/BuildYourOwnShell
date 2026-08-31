package shell;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import core.CommandCompleter;

/**
 * Reads a line of input with TAB autocompletion support.
 */
public class LineReader {

    private static final String PROMPT = "$ ";

    private final CommandCompleter completer;
    private final BufferedReader fallbackReader;

    public LineReader(CommandCompleter completer) {
        this.completer = completer;
        this.fallbackReader = new BufferedReader(
                new InputStreamReader(System.in, StandardCharsets.UTF_8));
    }

    public String readLine() throws Exception {
        if (!isInteractiveInput()) {
            return fallbackReader.readLine();
        }

        Terminal.enableRawMode();
        try {
            return readLineRaw();
        } finally {
            Terminal.disableRawMode();
        }
    }

    private static boolean isInteractiveInput() throws Exception {
        if (System.console() != null) {
            return true;
        }

        Process process = new ProcessBuilder("stty", "-a")
                .redirectError(ProcessBuilder.Redirect.DISCARD)
                .start();
        return process.waitFor() == 0;
    }

    private String readLineRaw() throws Exception {
        StringBuilder line = new StringBuilder();

        while (true) {
            int key = System.in.read();
            if (key == -1) {
                return null;
            }

            if (key == '\n' || key == '\r') {
                System.out.println();
                return line.toString();
            }

            if (key == '\t') {
                String prefix = line.toString();
                String completion = completer.complete(prefix);
                if (completion != null) {
                    String suffix;
                    if (completion.startsWith(prefix)) {
                        suffix = completion.substring(prefix.length());
                    } else {
                        suffix = completion;
                    }
                    line.append(suffix);
                    System.out.print("\r\033[2K" + PROMPT + line.toString());
                    System.out.flush();
                }
                continue;
            }

            if (key == 127 || key == 8) {
                if (!line.isEmpty()) {
                    line.deleteCharAt(line.length() - 1);
                    System.out.print("\b \b");
                    System.out.flush();
                }
                continue;
            }

            line.append((char) key);
            System.out.print((char) key);
            System.out.flush();
        }
    }
}
