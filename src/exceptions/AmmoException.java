package exceptions;

public class AmmoException extends Exception {

    @Override
    public String getMessage() {
        return "Патроны закончились";
    }
}
