package boats;

import enums.BoatType;
import exceptions.CapacityException;
import people.Sailor;

import java.util.ArrayList;
import java.util.Objects;

public abstract class Boat {

    protected BoatType type;
    protected int capacity;
    protected double xCoordinate;
    protected double yCoordinate;
    protected ArrayList<Sailor> sailors;

    public Boat(BoatType type, int capacity, double xCoordinate, double yCoordinate) {
        this.type = type;
        this.capacity = capacity;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.sailors = new ArrayList<Sailor>(capacity);
    }

    public abstract void addSailor(Sailor sailor) throws CapacityException;

    public BoatType getType() {
        return type;
    }

    public void setType(BoatType type) {
        this.type = type;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(double xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public double getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(double yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public ArrayList<Sailor> getSailors() {
        return sailors;
    }

    public void setSailors(ArrayList<Sailor> sailors) {
        this.sailors = sailors;
    }

    @Override
    public String toString() {
        return "Плавсредство типа " + type;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Boat boat = (Boat) o;
        return capacity == boat.capacity && type == boat.type;
    }

    @Override
    public int hashCode() {
        return 666 * type.hashCode() + capacity;
    }
}
