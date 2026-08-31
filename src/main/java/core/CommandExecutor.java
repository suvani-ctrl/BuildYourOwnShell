package core;

import commands.Command;
import commands.ExitCommand;
import shell.ShellState;
import utils.Tokenizer;

import java.util.List;

/**
 * Command Executor pattern: parses input, resolves the command, and delegates
 * execution to the appropriate handler.
 */
public class CommandExecutor {

    public enum Result {
        CONTINUE,
        EXIT
    }

    private final CommandRegistry registry;

    public CommandExecutor(CommandRegistry registry) {
        this.registry = registry;
    }

    public Result execute(String inputLine, ShellState state) throws Exception {
        List<String> tokens = Tokenizer.tokenize(inputLine);
        if (tokens.isEmpty()) {
            return Result.CONTINUE;
        }

        String commandName = tokens.get(0);
        List<String> args = tokens.subList(1, tokens.size());

        Command command = registry.getCommand(commandName);
        if (command == null) {
            System.out.println(commandName + ": command not found");
            return Result.CONTINUE;
        }

        command.execute(args, state);

        if (command instanceof ExitCommand) {
            return Result.EXIT;
        }

        return Result.CONTINUE;
    }
}
