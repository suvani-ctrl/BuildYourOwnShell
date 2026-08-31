package commands; 

import java.util.List;

import shell.ExecutionContext;
import shell.ShellState;

public interface Command {
    void execute(List<String> args, ShellState state, ExecutionContext context) throws Exception;
}
