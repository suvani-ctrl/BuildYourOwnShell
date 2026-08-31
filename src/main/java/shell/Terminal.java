package shell;

/**
 * Enables/disables raw terminal mode via stty for character-by-character input.
 */
public final class Terminal {

    private Terminal() {
    }

    public static void enableRawMode() throws Exception {
        new ProcessBuilder("stty", "-icanon", "-echo", "min", "1", "time", "0")
                .inheritIO()
                .start()
                .waitFor();
    }

    public static void disableRawMode() throws Exception {
        new ProcessBuilder("stty", "sane")
                .inheritIO()
                .start()
                .waitFor();
    }
}
