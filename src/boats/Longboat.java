package boats;

import enums.BoatType;
import enums.FlowDirection;
import exceptions.CapacityException;
import functioning.CoordinatesUtil;
import people.Sailor;

public class Longboat extends Boat {

    public Longboat(int capacity, double xCoordinate, double yCoordinate) {
        super(BoatType.LONGBOAT, capacity, xCoordinate, yCoordinate);
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
        double power = 0;
        for (Sailor sailor : sailors) {
            if (sailor.isRowing) {
                power += sailor.getStrength();
            }
        }

        xCoordinate = CoordinatesUtil.newCoordinates(time, direction, flowDirection, flowVelocity, width, power, xCoordinate, yCoordinate)[0];
        yCoordinate = CoordinatesUtil.newCoordinates(time, direction, flowDirection, flowVelocity, width, power, xCoordinate, yCoordinate)[1];

        if (yCoordinate == width) {
            System.out.println("Баркас пристал к берегу\n");
        }
        else if (yCoordinate == 0) {
            System.out.println("Баркас пристал к берегу\n");
        }
        else {
            System.out.printf("Баркас приплыл на координаты x = %f, y = %f\n", xCoordinate, yCoordinate);
        }
    }
}
