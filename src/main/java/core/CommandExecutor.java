package core;

import commands.Command;
import commands.ExitCommand;
import shell.ExecutionContext;
import shell.ShellState;
import utils.CommandLineParser;
import utils.CommandLineParser.ParsedCommand;
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

        ParsedCommand parsed = CommandLineParser.parse(tokens);
        String commandName = parsed.commandName();
        List<String> args = parsed.args();
        ExecutionContext context = new ExecutionContext(parsed.stdoutFile(), parsed.append());

        Command command = registry.getCommand(commandName);
        if (command == null) {
            System.out.println(commandName + ": command not found");
            return Result.CONTINUE;
        }

        command.execute(args, state, context);

        if (command instanceof ExitCommand) {
            return Result.EXIT;
        }

        return Result.CONTINUE;
    }
}
