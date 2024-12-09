package boats;

import enums.BoatType;
import enums.FlowDirection;
import exceptions.CapacityException;
import functioning.CoordinatesUtil;
import people.Sailor;

public class Lifeboat extends Boat {

    public Lifeboat() {
        super();
        type = BoatType.LIFEBOAT;
    }

    public Lifeboat(int capacity, double xCoordinate, double yCoordinate, int hp) {
        super(BoatType.LIFEBOAT, capacity, xCoordinate, yCoordinate, hp);
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
        if (hp > 0) {
            double power = 0;
            for (Sailor sailor : sailors) {
                if (sailor.isRowing) {
                    power += sailor.getStrength();
                }
            }

            xCoordinate = CoordinatesUtil.newCoordinates(time, direction, flowDirection, flowVelocity, width, power, xCoordinate, yCoordinate)[0];
            yCoordinate = CoordinatesUtil.newCoordinates(time, direction, flowDirection, flowVelocity, width, power, xCoordinate, yCoordinate)[1];

            if (yCoordinate == width) {
                System.out.println("Шлюпка пристала к берегу\n");
            } else if (yCoordinate == 0) {
                yCoordinate = 0;
                System.out.println("Шлюпка пристала к берегу\n");
            } else {
                System.out.printf("Шлюпка приплыла на координаты x = %f, y = %f\n", xCoordinate, yCoordinate);
            }
        }
        else {
            System.out.println(String.format("%s не может плыть, нужен ремонт", toString()));
        }
    }
}
