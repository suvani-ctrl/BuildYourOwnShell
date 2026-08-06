package commands;

import java.util.List;

import core.CommandRegistry;
import core.PathResolver;
import shell.ShellState;

/**
 * The 'type' built-in command. Reports whether a command is built-in,
 * external, or not found.
 */
public class TypeCommand implements Command {

    private final CommandRegistry registry;
    private final PathResolver pathResolver;

    public TypeCommand(CommandRegistry registry, PathResolver pathResolver) {
        this.registry = registry;
        this.pathResolver = pathResolver;
    }

    @Override
    public void execute(List<String> args, ShellState state) {
        String commandName = args.get(0);
        
        if (registry.isBuiltin(commandName)) {
            System.out.println(commandName + " is a shell builtin");
        } else {
            String path = pathResolver.findExecutable(commandName);
            if (path != null) {
                System.out.println(commandName + " is " + path);
            } else {
                System.out.println(commandName + ": not found");
            }
        }
    }
}