package io.muic.ooc.fab;

import java.util.List;
import java.util.Iterator;
import java.util.Random;

public class Fox extends Animal {

    private int foodLevel;

    @Override
    public void init(boolean randomAge, Field field, Location location){
        super.init(randomAge, field, location);
        foodLevel = RANDOM.nextInt(AnimalType.RABBIT.getFoodValue());
    }

    @Override
    protected int getMaxAge(){
        return 150;
    }

    @Override
    protected double getBreedingProbability(){
        return AnimalType.FOX.getBreedingProbability();
    }

    @Override
    protected int getMaxLiterSize(){
        return 2;
    }

    @Override
    protected int getBreedingAge(){
        return 15;
    }

    @Override
    public Location moveToNewLocation(){
        Location newLocation = findFood();
        if(newLocation == null){
            newLocation = field.freeAdjacentLocation(getLocation());
        }
        return newLocation;
    }

    @Override
    public void act(List<AnimalProperties> newAnimals) {
        incrementHunger();
        super.act(newAnimals);
    }

    public void incrementHunger() {
        foodLevel--;
        if (foodLevel <= 0) {
            setDead();
        }
    }
    public Location findFood() {
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while (it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if (animal instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) animal;
                if (rabbit.isAlive()) {
                    rabbit.setDead();
                    foodLevel = AnimalType.RABBIT.getFoodValue();
                    return where;
                }
            }
        }
        return null;
    }
}