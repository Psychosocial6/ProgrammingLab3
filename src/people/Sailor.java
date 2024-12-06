package people;

import attributes.Weapon;
import exceptions.AmmoException;

public class Sailor extends Human {

    private Weapon weapon;
    private double strength;
    public boolean isRowing;

    public Sailor() {
        super();
        this.weapon = null;
        this.strength = 0;
        this.isRowing = false;

    }

    public Sailor(String name, Weapon weapon, double strength, boolean isRowing) {
        super(name);
        this.weapon = weapon;
        this.isRowing = isRowing;
        this.strength = strength;
    }

    public void useWeapon() {
        try {
            weapon.shoot(name);
            System.out.printf("Матрос %s выстрелил из %s\n", name, weapon.getType());
        }
        catch (AmmoException e) {
            System.out.println(e.getMessage());
        }
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public double getStrength() {
        return strength;
    }

    public void setStrength(double strength) {
        this.strength = strength;
    }
}
