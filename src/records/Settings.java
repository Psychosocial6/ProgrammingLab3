package records;

public record Settings(int sleepingTime) {

    public Settings(int sleepingTime) {
        this.sleepingTime = sleepingTime;
    }

    public void sleep() {
        try {
            Thread.sleep(sleepingTime * 1000);
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
