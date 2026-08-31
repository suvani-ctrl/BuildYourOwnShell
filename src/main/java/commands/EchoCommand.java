package commands;

import java.util.List;

import shell.ExecutionContext;
import shell.ShellState;
import utils.OutputWriter;

public class EchoCommand extends BuiltinCommand {

    @Override
    public void execute(List<String> args, ShellState state, ExecutionContext context) throws Exception {
        OutputWriter.println(String.join(" ", args), context);
    }
}
