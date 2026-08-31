package commands;

import java.util.List;

import shell.ExecutionContext;
import shell.ShellState;

public class ExitCommand extends BuiltinCommand {

    @Override
    public void execute(List<String> args, ShellState state, ExecutionContext context) {
        // Shell loop terminates when CommandExecutor detects ExitCommand.
    }
}
