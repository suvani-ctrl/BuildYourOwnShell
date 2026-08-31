package commands;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import shell.ExecutionContext;
import shell.ShellState;

public class ExternalCommand implements Command {

    private final String commandName;

    public ExternalCommand(String commandName) {
        this.commandName = commandName;
    }

    @Override
    public void execute(List<String> args, ShellState state, ExecutionContext context)
            throws InterruptedException, IOException {
        List<String> fullCommand = new ArrayList<>(args.size() + 1);
        fullCommand.add(commandName);
        fullCommand.addAll(args);

        ProcessBuilder processBuilder = new ProcessBuilder(fullCommand);
        processBuilder.directory(state.getCurrentDirectory().toFile());
        processBuilder.environment().putAll(state.getEnvironment());

        boolean hasStdoutRedirect = context.hasStdoutRedirect();
        boolean hasStderrRedirect = context.hasStderrRedirect();

        if (!hasStdoutRedirect && !hasStderrRedirect) {
            processBuilder.inheritIO();
        } else {
            processBuilder.redirectInput(ProcessBuilder.Redirect.INHERIT);

            if (hasStdoutRedirect) {
                File outputFile = new File(context.getStdoutFile());
                if (context.isStdoutAppend()) {
                    processBuilder.redirectOutput(ProcessBuilder.Redirect.appendTo(outputFile));
                } else {
                    processBuilder.redirectOutput(outputFile);
                }
            } else {
                processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            }

            if (hasStderrRedirect) {
                File errorFile = new File(context.getStderrFile());
                if (context.isStderrAppend()) {
                    processBuilder.redirectError(ProcessBuilder.Redirect.appendTo(errorFile));
                } else {
                    processBuilder.redirectError(errorFile);
                }
            } else {
                processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);
            }
        }

        Process process = processBuilder.start();
        process.waitFor();
    }
}
