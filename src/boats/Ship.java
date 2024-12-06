package boats;

import enums.BoatType;
import enums.FlowDirection;
import exceptions.CapacityException;
import exceptions.NumberOfLifeboatsException;
import functioning.CoordinatesUtil;
import people.Sailor;

public class Ship extends Boat {

    private double enginePower;
    private int numberOfLifeboats;
    private int lifeboatsCapacity;

    public Ship() {
        super();
        enginePower = 10;
        numberOfLifeboats = 2;
        lifeboatsCapacity = 5;
    }

    public Ship(int capacity, double xCoordinate, double yCoordinate, double enginePower, int numberOfLifeboats, int lifeboatsCapacity) {
        super(BoatType.SHIP, capacity, xCoordinate, yCoordinate);
        this.enginePower = enginePower;
        this.numberOfLifeboats = numberOfLifeboats;
        this.lifeboatsCapacity = lifeboatsCapacity;
    }

    public Lifeboat dropLifeboat(int numberOfPeople) throws NumberOfLifeboatsException {
        if (numberOfLifeboats == 0) {
            throw new NumberOfLifeboatsException(toString());
        }
        Lifeboat lifeboat = new Lifeboat(lifeboatsCapacity, xCoordinate, yCoordinate);
        for (int i = 0; i < numberOfPeople; i++) {
            try {
                lifeboat.addSailor(sailors.get(0));
                sailors.remove(0);
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
            throw new NumberOfLifeboatsException(toString());
        }
        Lifeboat lifeboat = new Lifeboat(lifeboatsCapacity, xCoordinate, yCoordinate);
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
            throw new CapacityException(toString());
        }
    }

    @Override
    public void swim(double time, double direction, FlowDirection flowDirection, double flowVelocity, double width) {
        xCoordinate = CoordinatesUtil.newCoordinates(time, direction, flowDirection, flowVelocity, width, enginePower, xCoordinate, yCoordinate)[0];
        yCoordinate = CoordinatesUtil.newCoordinates(time, direction, flowDirection, flowVelocity, width, enginePower, xCoordinate, yCoordinate)[1];

        if (yCoordinate == width) {
            System.out.println("Корабль пристал к берегу\n");
        }
        else if (yCoordinate == 0) {
            System.out.println("Корабль пристал к берегу\n");
        }
        else {
            System.out.printf("Корабль приплыл на координаты x = %f, y = %f\n", xCoordinate, yCoordinate);
        }
    }
}
