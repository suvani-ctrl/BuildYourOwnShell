package commands;

import java.util.List;

import shell.ShellState;

public class EchoCommand extends BuiltinCommand {

    @Override
    public void execute(List<String> args, ShellState state) {
        System.out.println(String.join(" ", args));
    }
}
