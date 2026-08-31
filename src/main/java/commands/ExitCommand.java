package commands;

import java.util.List;

import shell.ShellState;

public class ExitCommand extends BuiltinCommand {

    @Override
    public void execute(List<String> args, ShellState state) {
        // Shell loop terminates when CommandExecutor detects ExitCommand.
    }
}
