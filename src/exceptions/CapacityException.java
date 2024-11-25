package exceptions;

public class CapacityException extends Exception {
    @Override
    public String getMessage() {
        return "Превышено ограничение вместимости";
    }
}
