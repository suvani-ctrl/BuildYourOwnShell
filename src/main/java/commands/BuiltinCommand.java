package commands;

import java.util.List;

import shell.ExecutionContext;
import shell.ShellState;

/**
 * Abstract base for all built-in shell commands.
 */
public abstract class BuiltinCommand implements Command {

    @Override
    public abstract void execute(List<String> args, ShellState state, ExecutionContext context) throws Exception;
}
