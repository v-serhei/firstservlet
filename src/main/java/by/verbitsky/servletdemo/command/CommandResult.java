package by.verbitsky.servletdemo.command;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class CommandResult {
    private final Map<String, String> results = new HashMap<>();

    public Map<String, String> getResults() {
        return Collections.unmodifiableMap(results);
    }

    public void addAttribute(String name, String value) {
        results.put(name, value);
    }

    public String getAttribute(String key) {
        return results.get(key);
    }
}
