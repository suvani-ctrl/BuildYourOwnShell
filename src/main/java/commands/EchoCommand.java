package commands;

import java.util.List;

import shell.ShellState;

public class EchoCommand implements Command {  // ← MUST have "implements Command"
    @Override
    public void execute(List<String> args, ShellState state) {
        System.out.println(String.join(" ", args));
    }
}