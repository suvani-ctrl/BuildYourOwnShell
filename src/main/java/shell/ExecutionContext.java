package shell;

/**
 * Carries per-invocation execution options such as stdout/stderr redirection.
 */
public final class ExecutionContext {

    private final String stdoutFile;
    private final boolean stdoutAppend;
    private final String stderrFile;
    private final boolean stderrAppend;

    public ExecutionContext(
            String stdoutFile,
            boolean stdoutAppend,
            String stderrFile,
            boolean stderrAppend) {
        this.stdoutFile = stdoutFile;
        this.stdoutAppend = stdoutAppend;
        this.stderrFile = stderrFile;
        this.stderrAppend = stderrAppend;
    }

    public static ExecutionContext none() {
        return new ExecutionContext(null, false, null, false);
    }

    public boolean hasStdoutRedirect() {
        return stdoutFile != null;
    }

    public String getStdoutFile() {
        return stdoutFile;
    }

    public boolean isStdoutAppend() {
        return stdoutAppend;
    }

    public boolean hasStderrRedirect() {
        return stderrFile != null;
    }

    public String getStderrFile() {
        return stderrFile;
    }

    public boolean isStderrAppend() {
        return stderrAppend;
    }
}
