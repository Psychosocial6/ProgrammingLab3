package exceptions;

public class AmmoException extends Exception {
    private String name;

    public AmmoException(String name) {
        this.name = name;
    }

    @Override
    public String getMessage() {
        return String.format("Патроны закончились у матроса %s", name);
    }
}
