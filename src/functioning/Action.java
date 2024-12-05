package functioning;

import attributes.*;
import boats.*;
import enums.*;
import environment.River;
import exceptions.*;
import people.*;
import records.Settings;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class Action {

    private River river;
    private StoryTeller storyTeller;
    private Settings settings;

    private FlowDirection flowDirection;
    private double width;
    private double flowVelocity;
    private String storyTellerName;
    private int shipCapacity;
    private double shipXCoordinate;
    private double shipYCoordinate;
    private double shipEnginePower;
    private int shipNumberOfLifeboats;
    private int shipLifeboatsCapacity;
    private int longboatCapacity;
    private double longboatXCoordinate;
    private double longboatYCoordinate;
    private double spyglassDistance;
    private double spyglassEffectiveDistance;
    private WeaponType sailorWeaponType;
    private int weaponAmmo;
    private double sailorStrength;
    private boolean isRowing;
    private int sleepingTime;

    public Action() {
        settings = new Settings(SettingsParser.parseSettings(new File("src/settings.txt")));
        init();
        river = new River(flowDirection, width, flowVelocity, new ArrayList<Boat>());
        storyTeller = new StoryTeller(storyTellerName);
        river.addBoat(new Ship(shipCapacity, shipXCoordinate, shipYCoordinate, shipEnginePower, shipNumberOfLifeboats, shipLifeboatsCapacity));
        river.addBoat(new Longboat(longboatCapacity, longboatXCoordinate, longboatYCoordinate));
        storyTeller.setSpyglass(new Spyglass(spyglassDistance, spyglassEffectiveDistance));

        for (Boat boat : river.getBoats()) {
            for (int i = 0; i < boat.getCapacity(); i++) {
                try {
                    boat.addSailor(new Sailor(NameGenerator.getName(), new Weapon(sailorWeaponType, weaponAmmo), sailorStrength, isRowing));
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
                settings.sleep(sleepingTime);
                boat.getSailors().get(random.nextInt(0, boat.getSailors().size())).useWeapon();
                settings.sleep(sleepingTime);
                boat.getSailors().get(random.nextInt(0, boat.getSailors().size())).useWeapon();
                settings.sleep(sleepingTime);
            }
        }
        storyTeller.useSpyglass(river);
        settings.sleep(sleepingTime);
        ArrayList<Boat> copy = (ArrayList<Boat>) river.getBoats().clone();
        for (Boat boat : copy) {
            if (boat.getType() == BoatType.SHIP) {
                try {
                    int peopleInDroppedLifeboat = 3;
                    river.addBoat(((Ship) boat).dropLifeboat(peopleInDroppedLifeboat));
                } catch (NumberOfLifeboatsException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        settings.sleep(sleepingTime);
        for (Boat boat : river.getBoats()) {
            if (boat.getType() == BoatType.LIFEBOAT) {
                boat.swim(5, 270, river.getflowDirection(), river.getFlowVelocity(), river.getWidth());
            }
        }
        settings.sleep(sleepingTime);
        storyTeller.useSpyglass(river);
        settings.sleep(sleepingTime);
        for (Boat boat : river.getBoats()) {
            if (boat.getType() == BoatType.LIFEBOAT) {
                boat.swim(5, 270, river.getflowDirection(), river.getFlowVelocity(), river.getWidth());
            }
        }
        settings.sleep(sleepingTime);
        storyTeller.useSpyglass(river);
    }

    private void init() {
        sleepingTime = Integer.valueOf((String) settings.settings().get("sleeping").get("sleepingTime"));
        switch ((String) settings.settings().get("river").get("flowDirection")) {
            case "LEFT":
                flowDirection = FlowDirection.LEFT;
                break;
            default:
                flowDirection = FlowDirection.RIGHT;
                break;
        }
        width = Double.valueOf((String) settings.settings().get("river").get("width"));
        flowVelocity = Double.valueOf((String) settings.settings().get("river").get("flowVelocity"));
        storyTellerName = (String) settings.settings().get("storyTeller").get("name");
        shipCapacity = Integer.valueOf((String) settings.settings().get("ship").get("capacity"));
        shipXCoordinate = Double.valueOf((String) settings.settings().get("ship").get("xCoordinate"));
        shipYCoordinate = Double.valueOf((String) settings.settings().get("ship").get("yCoordinate"));
        shipEnginePower = Double.valueOf((String) settings.settings().get("ship").get("enginePower"));
        shipNumberOfLifeboats = Integer.valueOf((String) settings.settings().get("ship").get("numberOfLifeboats"));
        shipLifeboatsCapacity = Integer.valueOf((String) settings.settings().get("ship").get("lifeboatsCapacity"));
        longboatCapacity = Integer.valueOf((String) settings.settings().get("longboat").get("capacity"));
        longboatXCoordinate = Double.valueOf((String) settings.settings().get("longboat").get("xCoordinate"));
        longboatYCoordinate = Double.valueOf((String) settings.settings().get("longboat").get("yCoordinate"));
        spyglassDistance = Double.valueOf((String) settings.settings().get("spyglass").get("distance"));
        spyglassEffectiveDistance = Double.valueOf((String) settings.settings().get("spyglass").get("effectiveDistance"));
        switch ((String) settings.settings().get("sailor").get("weaponType")) {
            case "RIFLE":
                sailorWeaponType = WeaponType.RIFLE;

        }
        weaponAmmo = Integer.valueOf((String) settings.settings().get("sailor").get("ammo"));
        sailorStrength = Double.valueOf((String) settings.settings().get("sailor").get("strength"));
        isRowing = Boolean.valueOf((String) settings.settings().get("sailor").get("isRowing"));
    }
}
