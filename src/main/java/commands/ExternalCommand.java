package commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import shell.ShellState;

public class ExternalCommand implements Command {

    private final String executablePath;

    public ExternalCommand(String executablePath) {
        this.executablePath = executablePath;
    }

    @Override
    public void execute(List<String> args, ShellState state) throws InterruptedException, IOException {
        List<String> fullCommand = new ArrayList<>(args.size() + 1);
        fullCommand.add(executablePath);
        fullCommand.addAll(args);

        ProcessBuilder processBuilder = new ProcessBuilder(fullCommand);
        processBuilder.directory(state.getCurrentDirectory().toFile());
        processBuilder.environment().putAll(state.getEnvironment());
        processBuilder.inheritIO();

        Process process = processBuilder.start();
        process.waitFor();
    }
}
