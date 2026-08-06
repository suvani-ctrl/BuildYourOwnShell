package commands;

import java.io.IOException;
import java.util.List;

import shell.ShellState;

public class ExternalCommand implements Command {
    
    private final String executablePath;
    
    public ExternalCommand(String executablePath) {
        this.executablePath = executablePath;
    }
    
    @Override
    public void execute(List<String> args, ShellState state) throws InterruptedException {
        List<String> fullCommand = new java.util.ArrayList<>();
        fullCommand.add(executablePath);
        fullCommand.addAll(args);
        
        ProcessBuilder pb = new ProcessBuilder(fullCommand);
        pb.directory(state.getCurrentDirectory().toFile());
        pb.environment().putAll(state.getEnvironment());
        pb.inheritIO();
        try {
            pb.start().waitFor();
        } catch (IOException ex) {
            System.getLogger(ExternalCommand.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
}