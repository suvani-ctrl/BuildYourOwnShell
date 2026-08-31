package commands;

import java.util.List;

import core.CommandRegistry;
import core.PathResolver;
import shell.ExecutionContext;
import shell.ShellState;
import utils.OutputWriter;

/**
 * The 'type' built-in command. Reports whether a command is built-in,
 * external, or not found.
 */
public class TypeCommand extends BuiltinCommand {

    private final CommandRegistry registry;
    private final PathResolver pathResolver;

    public TypeCommand(CommandRegistry registry, PathResolver pathResolver) {
        this.registry = registry;
        this.pathResolver = pathResolver;
    }

    @Override
    public void execute(List<String> args, ShellState state, ExecutionContext context) throws Exception {
        if (args.isEmpty()) {
            return;
        }

        String commandName = args.get(0);

        if (registry.isBuiltin(commandName)) {
            OutputWriter.println(commandName + " is a shell builtin", context);
            return;
        }

        String path = pathResolver.findExecutable(commandName);
        if (path != null) {
            OutputWriter.println(commandName + " is " + path, context);
        } else {
            OutputWriter.println(commandName + ": not found", context);
        }
    }
}
