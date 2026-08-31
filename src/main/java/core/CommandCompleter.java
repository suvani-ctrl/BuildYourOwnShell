package core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Provides command-name autocompletion for the first token on the input line.
 */
public class CommandCompleter {

    private final CommandRegistry registry;

    public CommandCompleter(CommandRegistry registry) {
        this.registry = registry;
    }

    /**
     * Returns the completed command followed by a trailing space when there is
     * exactly one match, otherwise null.
     */
    public String complete(String input) {
        if (input.contains(" ")) {
            return null;
        }

        List<String> matches = registry.getMatchingCommandNames(input);
        if (matches.size() == 1) {
            return matches.get(0) + " ";
        }

        return null;
    }
}
