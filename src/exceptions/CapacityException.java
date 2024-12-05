package exceptions;

public class CapacityException extends Exception {
    String boat;

    public CapacityException(String boat) {
        this.boat = boat;
    }

    @Override
    public String getMessage() {
        return String.format("Превышено ограничение вместимости на: %s", boat);
    }
}
