package core;

import commands.Command;
import commands.EchoCommand;
import commands.CdCommand;
import commands.PwdCommand;
import commands.ExitCommand;
import commands.TypeCommand;
import commands.ExternalCommand;

import java.util.HashMap;
import java.util.Map;

/**
 * Registry of all built-in commands. Also handles external command lookup.
 */
public class CommandRegistry {

    private final Map<String, Command> builtinCommands;
    private final PathResolver pathResolver;

    public CommandRegistry(PathResolver pathResolver) {
        this.pathResolver = pathResolver;
        this.builtinCommands = new HashMap<>();
        registerBuiltins();
    }

    private void registerBuiltins() {
        builtinCommands.put("echo", new EchoCommand());
        builtinCommands.put("cd", new CdCommand());
        builtinCommands.put("pwd", new PwdCommand());
        builtinCommands.put("exit", new ExitCommand());
    }

    /**
     * TypeCommand needs a reference to this registry to check built-ins. Must
     * be called after construction.
     */
    public void registerTypeCommand() {
        builtinCommands.put("type", new TypeCommand(this, pathResolver));
    }

    /**
     * Returns a command by name. Checks built-ins first, then external
     * commands. Returns null if no command is found.
     */
    public Command getCommand(String commandName) {
        // Built-ins take priority
        Command builtin = builtinCommands.get(commandName);
        if (builtin != null) {
            return builtin;
        }

        // Check for external commands
        String executablePath = pathResolver.findExecutable(commandName);
        if (executablePath != null) {
            return new ExternalCommand(commandName);
        }

        return null;
    }

    public boolean isBuiltin(String commandName) {
        return builtinCommands.containsKey(commandName);
    }
}
