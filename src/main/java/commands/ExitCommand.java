package commands;

import java.util.List;

import shell.ShellState;

public class ExitCommand implements Command {
    @Override
    public void execute(List<String> args, ShellState state) {
        System.out.println("Goodbye!");
        System.exit(0);
    }
}