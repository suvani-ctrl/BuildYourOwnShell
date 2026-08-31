package shell;

/**
 * Carries per-invocation execution options such as stdout redirection.
 */
public final class ExecutionContext {

    private final String stdoutFile;
    private final boolean append;

    public ExecutionContext(String stdoutFile, boolean append) {
        this.stdoutFile = stdoutFile;
        this.append = append;
    }

    public static ExecutionContext none() {
        return new ExecutionContext(null, false);
    }

    public boolean hasStdoutRedirect() {
        return stdoutFile != null;
    }

    public String getStdoutFile() {
        return stdoutFile;
    }

    public boolean isAppend() {
        return append;
    }
}
