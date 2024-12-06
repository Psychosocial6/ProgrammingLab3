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
        settings = new Settings(SettingsParser.parseSettings(new File("src/config.txt")));
        try {
            init();
        }
        catch (WrongDataException e) {
            System.out.println(e.getMessage());
        }
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
                double time = 5;
                double direction = 270;
                boat.swim(time, direction, river.getflowDirection(), river.getFlowVelocity(), river.getWidth());
            }
        }
        settings.sleep(sleepingTime);
        storyTeller.useSpyglass(river);
        settings.sleep(sleepingTime);
        for (Boat boat : river.getBoats()) {
            if (boat.getType() == BoatType.LIFEBOAT) {
                double time = 5;
                double direction = 270;
                boat.swim(time, direction, river.getflowDirection(), river.getFlowVelocity(), river.getWidth());
            }
        }
        settings.sleep(sleepingTime);
        storyTeller.useSpyglass(river);
    }

    private void init() throws WrongDataException {
        sleepingTime = Integer.valueOf((String) settings.config().get("sleeping").get("sleepingTime"));
        if (sleepingTime < 0) {
            throw new WrongDataException("Неверные данные sleeping : sleepingTime");
        }

        switch ((String) settings.config().get("river").get("flowDirection")) {
            case "LEFT":
                flowDirection = FlowDirection.LEFT;
                break;
            default:
                flowDirection = FlowDirection.RIGHT;
                break;
        }
        if (flowDirection == null) {
            throw new WrongDataException("Неверные данные river : flowDirection");
        }

        width = Double.valueOf((String) settings.config().get("river").get("width"));
        if (width <= 0) {
            throw new WrongDataException("Неверные данные river : width");
        }

        flowVelocity = Double.valueOf((String) settings.config().get("river").get("flowVelocity"));
        if (flowVelocity < 0) {
            throw new WrongDataException("Неверные данные river : flowVelocity");
        }

        storyTellerName = (String) settings.config().get("storyTeller").get("name");

        shipCapacity = Integer.valueOf((String) settings.config().get("ship").get("capacity"));
        if (shipCapacity < 0) {
            throw new WrongDataException("Неверные данные ship : capacity");
        }

        shipXCoordinate = Double.valueOf((String) settings.config().get("ship").get("xCoordinate"));

        shipYCoordinate = Double.valueOf((String) settings.config().get("ship").get("yCoordinate"));

        shipEnginePower = Double.valueOf((String) settings.config().get("ship").get("enginePower"));
        if (shipEnginePower <= 0) {
            throw new WrongDataException("Неверные данные river : enginePower");
        }

        shipNumberOfLifeboats = Integer.valueOf((String) settings.config().get("ship").get("numberOfLifeboats"));
        if (shipNumberOfLifeboats < 0) {
            throw new WrongDataException("Неверные данные ship : numberOfLifeboats");
        }

        shipLifeboatsCapacity = Integer.valueOf((String) settings.config().get("ship").get("lifeboatsCapacity"));
        if (shipLifeboatsCapacity < 0) {
            throw new WrongDataException("Неверные данные ship : lifeboatsCapacity");
        }

        longboatCapacity = Integer.valueOf((String) settings.config().get("longboat").get("capacity"));
        if (longboatCapacity < 0) {
            throw new WrongDataException("Неверные данные longboat : capacity");
        }

        longboatXCoordinate = Double.valueOf((String) settings.config().get("longboat").get("xCoordinate"));

        longboatYCoordinate = Double.valueOf((String) settings.config().get("longboat").get("yCoordinate"));

        spyglassDistance = Double.valueOf((String) settings.config().get("spyglass").get("distance"));
        if (spyglassDistance <= 0) {
            throw new WrongDataException("Неверные данные spyglass : distance");
        }

        spyglassEffectiveDistance = Double.valueOf((String) settings.config().get("spyglass").get("effectiveDistance"));
        if (spyglassEffectiveDistance <= 0) {
            throw new WrongDataException("Неверные данные spyglass : effectiveDistance");
        }

        switch ((String) settings.config().get("sailor").get("weaponType")) {
            case "RIFLE":
                sailorWeaponType = WeaponType.RIFLE;
                break;
        }
        if (sailorWeaponType == null) {
            throw new WrongDataException("Неверные данные sailor : weaponType");
        }

        weaponAmmo = Integer.valueOf((String) settings.config().get("sailor").get("ammo"));
        if (weaponAmmo <= 0) {
            throw new WrongDataException("Неверные данные sailor : ammo");
        }

        sailorStrength = Double.valueOf((String) settings.config().get("sailor").get("strength"));
        if (sailorStrength <= 0) {
            throw new WrongDataException("Неверные данные sailor : strength");
        }

        isRowing = Boolean.valueOf((String) settings.config().get("sailor").get("isRowing"));
    }
}
