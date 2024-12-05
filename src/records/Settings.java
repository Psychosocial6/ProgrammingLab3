package records;

import java.util.HashMap;

public record Settings(HashMap<String, HashMap<String, Object>> settings) {

    public Settings(HashMap<String, HashMap<String, Object>> settings) {
        this.settings = settings;
    }

    public void sleep(int sleepingTime) {
        try {
            Thread.sleep(sleepingTime * 1000);
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
