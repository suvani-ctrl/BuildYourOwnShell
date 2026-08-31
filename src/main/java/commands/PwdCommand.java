package commands;

import java.util.List;

import shell.ExecutionContext;
import shell.ShellState;
import utils.OutputWriter;

public class PwdCommand extends BuiltinCommand {

    @Override
    public void execute(List<String> args, ShellState state, ExecutionContext context) throws Exception {
        OutputWriter.println(state.getCurrentDirectory().toString(), context);
    }
}
