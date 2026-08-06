package core;

import java.io.File;

/**
 * Finds external commands by searching directories in the system PATH.
 * The PATH is read once at construction and cached for performance.
 */
public class PathResolver {

    private final String[] pathDirectories;

    public PathResolver() {
        String pathEnv = System.getenv("PATH");
        
        // Empty PATH means no external commands can be found
        if (pathEnv == null || pathEnv.isEmpty()) {
            this.pathDirectories = new String[0];
            return;
        }
        
        this.pathDirectories = pathEnv.split(File.pathSeparator);
    }

    /**
     * Returns the full path of an executable command, or null if not found.
     */
    public String findExecutable(String command) {
        // Direct full-path check (e.g., "/bin/ls")
        if (command.contains(File.separator)) {
            File file = new File(command);
            return (file.exists() && file.canExecute()) ? file.getAbsolutePath() : null;
        }
        
        // Search PATH in order - first match wins
        for (String dir : pathDirectories) {
            File file = new File(dir, command);
            if (file.exists() && file.canExecute()) {
                return file.getAbsolutePath();
            }
        }
        
        return null;
    }
}