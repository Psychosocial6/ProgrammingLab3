package exceptions;

public class NumberOfLifeboatsException extends Exception {
    @Override
    public String getMessage() {
        return "Шлюпки кончились";
    }
}
