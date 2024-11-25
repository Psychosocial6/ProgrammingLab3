package functioning;

import attributes.Spyglass;
import attributes.Weapon;
import boats.Boat;
import boats.Lifeboat;
import boats.Longboat;
import boats.Ship;
import enums.BoatType;
import enums.FlowDirection;
import enums.WeaponType;
import environment.River;
import exceptions.CapacityException;
import exceptions.NumberOfLifeboatsException;
import people.Sailor;
import people.StoryTeller;
import records.Settings;

import java.util.ArrayList;
import java.util.Random;

public class Action {

    private River river;
    private StoryTeller storyTeller;
    private Settings settings;

    public Action() {
        river = new River(FlowDirection.LEFT, 250, 1, new ArrayList<Boat>());
        storyTeller = new StoryTeller("Расказчик");
        settings = new Settings(1);
        river.addBoat(new Ship(BoatType.SHIP, 10, 0, 150, 50, 2, 10));
        river.addBoat(new Longboat(BoatType.LONGBOAT, 5, 200, 200));
        storyTeller.setSpyglass(new Spyglass(100, 50));

        for (Boat boat : river.getBoats()) {
            for (int i = 0; i < boat.getCapacity(); i++) {
                try {
                    boat.addSailor(new Sailor(NameGenerator.getName(), new Weapon(WeaponType.RIFLE, 10), 5, true));
                }
                catch (CapacityException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public void go() {
        Random random = new Random();
        for (Boat boat : river.getBoats()) {
            if (boat.getType() == BoatType.SHIP) {
                boat.getSailors().get(random.nextInt(0, boat.getSailors().size())).useWeapon();
                settings.sleep();
                boat.getSailors().get(random.nextInt(0, boat.getSailors().size())).useWeapon();
                settings.sleep();
                boat.getSailors().get(random.nextInt(0, boat.getSailors().size())).useWeapon();
                settings.sleep();
            }
        }
        storyTeller.useSpyglass(river);
        settings.sleep();
        ArrayList<Boat> copy = (ArrayList<Boat>) river.getBoats().clone();
        for (Boat boat : copy) {
            if (boat instanceof Ship) {
                try {
                    river.addBoat(((Ship) boat).dropLifeboat(3));
                } catch (NumberOfLifeboatsException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        settings.sleep();
        for (Boat boat : river.getBoats()) {
            if (boat instanceof Lifeboat) {
                ((Lifeboat) boat).swim(5, 270, river.getFLOW_DIRECTION(), river.getFlowVelocity(), river.getWIDTH());
            }
        }
        settings.sleep();
        storyTeller.useSpyglass(river);
        settings.sleep();
        for (Boat boat : river.getBoats()) {
            if (boat instanceof Lifeboat) {
                ((Lifeboat) boat).swim(5, 270, river.getFLOW_DIRECTION(), river.getFlowVelocity(), river.getWIDTH());
            }
        }
        settings.sleep();
        storyTeller.useSpyglass(river);
    }
}
