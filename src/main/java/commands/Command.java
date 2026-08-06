package commands; 

import java.util.List;

import shell.ShellState;

public interface Command {
    void execute(List<String> args, ShellState state) throws Exception;
}