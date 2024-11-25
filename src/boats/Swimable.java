package boats;

import enums.FlowDirection;

public interface Swimable {

    public void swim(double time, double direction, FlowDirection flowDirection, double flowVelocity, double width);

}
