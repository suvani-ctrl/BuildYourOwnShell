package commands;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import shell.ExecutionContext;
import shell.ShellState;
import utils.OutputWriter;

public class CdCommand extends BuiltinCommand {

    @Override
    public void execute(List<String> args, ShellState state, ExecutionContext context) throws Exception {
        String target = args.isEmpty() ? state.getEnv("HOME") : args.get(0);

        if (target == null || target.isEmpty()) {
            return;
        }

        if (target.startsWith("~")) {
            String home = state.getEnv("HOME");
            if (home != null) {
                target = home + target.substring(1);
            }
        }

        Path newPath = state.getCurrentDirectory().resolve(target).normalize();

        if (Files.isDirectory(newPath)) {
            state.setCurrentDirectory(newPath.toAbsolutePath().normalize());
        } else {
            String displayTarget = args.isEmpty() ? target : args.get(0);
            OutputWriter.printErrln("cd: " + displayTarget + ": No such file or directory", context);
        }
    }
}
