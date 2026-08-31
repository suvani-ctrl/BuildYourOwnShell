package shell;

import core.CommandCompleter;
import core.CommandExecutor;
import core.CommandRegistry;
import core.PathResolver;

/**
 * REPL loop for the shell. Reads input, delegates to CommandExecutor, and
 * repeats until exit.
 */
public class Shell {

    private static final String PROMPT = "$ ";

    private final CommandExecutor executor;
    private final LineReader lineReader;
    private final ShellState state;

    public Shell() {
        PathResolver pathResolver = new PathResolver();
        CommandRegistry registry = new CommandRegistry(pathResolver);
        registry.registerTypeCommand();
        this.executor = new CommandExecutor(registry);
        this.lineReader = new LineReader(new CommandCompleter(registry));
        this.state = new ShellState();
    }

    public void run() throws Exception {
        while (true) {
            System.out.print(PROMPT);
            System.out.flush();

            String line = lineReader.readLine();
            if (line == null) {
                break;
            }

            if (executor.execute(line, state) == CommandExecutor.Result.EXIT) {
                break;
            }
        }
    }
}
