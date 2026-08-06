package commands;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import shell.ShellState;

public class CdCommand implements Command {
    
    @Override
    public void execute(List<String> args, ShellState state) {
        String target = args.isEmpty() ? state.getEnv("HOME") : args.get(0);
        
        // Expand ~ to home directory
        if (target != null && target.startsWith("~")) {
            String home = state.getEnv("HOME");
            if (home != null) {
                target = target.replace("~", home);
            }
        }
        
        Path newPath = state.getCurrentDirectory().resolve(target).normalize();
        
        if (Files.isDirectory(newPath)) {
            state.setCurrentDirectory(newPath);
        } else {
            System.err.println("cd: " + target + ": No such directory");
        }
    }
}