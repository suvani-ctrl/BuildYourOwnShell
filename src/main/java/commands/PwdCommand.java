package commands;

import java.util.List;

import shell.ShellState;

public class PwdCommand extends BuiltinCommand {

    @Override
    public void execute(List<String> args, ShellState state) {
        System.out.println(state.getCurrentDirectory());
    }
}
