package shell;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * The shell's memory - current directory and environment variables. All
 * commands share the same state instance.
 */
public class ShellState {

    private Path currentDirectory;
    private final Map<String, String> environment;

    public ShellState() {
        this.currentDirectory = Paths.get(System.getProperty("user.dir"));
        this.environment = new HashMap<>(System.getenv());
    }

    public Path getCurrentDirectory() {
        return currentDirectory;
    }

    public void setCurrentDirectory(Path path) {
        this.currentDirectory = path;
    }

    public String getEnv(String key) {
        return environment.get(key);
    }

    public void setEnv(String key, String value) {
        environment.put(key, value);
    }

    public Map<String, String> getEnvironment() {
        return new HashMap<>(environment);
    }
}
