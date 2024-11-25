package boats;

import enums.BoatType;
import enums.FlowDirection;
import exceptions.CapacityException;
import people.Sailor;

import static java.lang.Math.*;
public class Lifeboat extends Boat implements Swimable {

    public Lifeboat(BoatType type, int capacity, double xCoordinate, double yCoordinate) {
        super(type, capacity, xCoordinate, yCoordinate);
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
        double power = 0;
        for (Sailor sailor : sailors) {
            if (sailor.isRowing) {
                power += sailor.getStrength();
            }
        }

        double deltaX;
        double deltaY;
        direction *= (PI / 180);
        deltaX = cos(direction) * power * time;
        if (flowDirection == FlowDirection.RIGHT) {
            deltaX += flowVelocity * time;
        }
        else {
            deltaX -= flowVelocity * time;
        }
        deltaY = sin(direction) * power * time;

        xCoordinate += deltaX;
        if (yCoordinate + deltaY >= width) {
            yCoordinate = width;
            System.out.println("Шлюпка пристала к берегу\n");
        }
        else if (yCoordinate + deltaY <= 0) {
            yCoordinate = 0;
            System.out.println("Шлюпка пристала к берегу\n");
        }
        else {
            yCoordinate += deltaY;
            System.out.printf("Шлюпка приплыла на координаты x = %f, y = %f\n", xCoordinate, yCoordinate);
        }
    }
}
