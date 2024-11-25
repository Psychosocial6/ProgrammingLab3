package boats;

import enums.BoatType;
import enums.FlowDirection;
import exceptions.CapacityException;
import exceptions.NumberOfLifeboatsException;
import people.Sailor;

import static java.lang.Math.*;
import static java.lang.Math.sin;

public class Ship extends Boat implements Swimable {

    private double enginePower;
    private int numberOfLifeboats;
    private int lifeboatsCapacity;

    public Ship(BoatType type, int capacity, double xCoordinate, double yCoordinate, double enginePower, int numberOfLifeboats, int lifeboatsCapacity) {
        super(BoatType.SHIP, capacity, xCoordinate, yCoordinate);
        this.enginePower = enginePower;
        this.numberOfLifeboats = numberOfLifeboats;
        this.lifeboatsCapacity = lifeboatsCapacity;
    }

    public Lifeboat dropLifeboat(int numberOfPeople) throws NumberOfLifeboatsException {
        if (numberOfLifeboats == 0) {
            throw new NumberOfLifeboatsException();
        }
        Lifeboat lifeboat = new Lifeboat(BoatType.LIFEBOAT, lifeboatsCapacity, xCoordinate, yCoordinate);
        for (int i = 0; i < numberOfPeople; i++) {
            try {
                lifeboat.addSailor(sailors.getFirst());
                sailors.removeFirst();
            }
            catch (CapacityException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("С корабля спустили шлюпку");
        return lifeboat;
    }

    public Lifeboat dropLifeboat(int[] people) throws NumberOfLifeboatsException {
        if (numberOfLifeboats == 0) {
            throw new NumberOfLifeboatsException();
        }
        Lifeboat lifeboat = new Lifeboat(BoatType.LIFEBOAT, lifeboatsCapacity, xCoordinate, yCoordinate);
        for (int i : people) {
            try {
                lifeboat.addSailor(sailors.get(i));
                sailors.remove(i);
            }
            catch (CapacityException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("С корабля спустили шлюпку");
        return lifeboat;
    }

    @Override
    public void addSailor(Sailor sailor) throws CapacityException {
        if (sailors.size() < capacity) {
            sailors.add(sailor);
        }
        else {
            throw new CapacityException();
        }
    }

    @Override
    public void swim(double time, double direction, FlowDirection flowDirection, double flowVelocity, double width) {
        double deltaX;
        double deltaY;
        direction *= (PI / 180);
        deltaX = cos(direction) * enginePower * time;
        if (flowDirection == FlowDirection.RIGHT) {
            deltaX += flowVelocity * time;
        }
        else {
            deltaX -= flowVelocity * time;
        }
        deltaY = sin(direction) * enginePower * time;

        xCoordinate += deltaX;
        if (yCoordinate + deltaY >= width) {
            yCoordinate = width;
            System.out.println("Корабль пристал к берегу\n");
        }
        else if (yCoordinate + deltaY <= 0) {
            yCoordinate = 0;
            System.out.println("Корабль пристал к берегу\n");
        }
        else {
            yCoordinate += deltaY;
            System.out.printf("Корабль приплыл на координаты x = %f, y = %f\n", xCoordinate, yCoordinate);
        }
    }
}
