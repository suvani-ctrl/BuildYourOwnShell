package shell;

import core.CommandExecutor;
import core.CommandRegistry;
import core.PathResolver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * REPL loop for the shell. Reads input, delegates to CommandExecutor, and
 * repeats until exit.
 */
public class Shell {

    private static final String PROMPT = "$ ";

    private final CommandExecutor executor;
    private final ShellState state;

    public Shell() {
        PathResolver pathResolver = new PathResolver();
        CommandRegistry registry = new CommandRegistry(pathResolver);
        registry.registerTypeCommand();
        this.executor = new CommandExecutor(registry);
        this.state = new ShellState();
    }

    public void run() throws Exception {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in, StandardCharsets.UTF_8));

        while (true) {
            System.out.print(PROMPT);
            System.out.flush();

            String line = reader.readLine();
            if (line == null) {
                break;
            }

            if (executor.execute(line, state) == CommandExecutor.Result.EXIT) {
                break;
            }
        }
    }
}
