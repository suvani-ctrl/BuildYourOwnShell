package commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import shell.ShellState;

public class ExternalCommand implements Command {

    private final String commandName;

    public ExternalCommand(String commandName) {
        this.commandName = commandName;
    }

    @Override
    public void execute(List<String> args, ShellState state) throws InterruptedException, IOException {
        List<String> fullCommand = new ArrayList<>(args.size() + 1);
        fullCommand.add(commandName);
        fullCommand.addAll(args);

        ProcessBuilder processBuilder = new ProcessBuilder(fullCommand);
        processBuilder.directory(state.getCurrentDirectory().toFile());
        processBuilder.environment().putAll(state.getEnvironment());
        processBuilder.inheritIO();

        Process process = processBuilder.start();
        process.waitFor();
    }
}
