package part2;

import jms.Producer;
import jms.Subscriber;

import javax.jms.JMSException;

public class Player {
    private int woodResource;
    private int stoneResource;
    private int goldResource;
    int houses = 0;

//    Subscriber subscriber = new Subscriber();
//    Producer producer = new Producer();

    public Player() {
        woodResource = 0;
        stoneResource= 0;
        goldResource = 0;
    }
    public void setResources(int woodResource, int stoneResource, int goldResource) {
        this.woodResource = woodResource;
        this.stoneResource = stoneResource;
        this.goldResource = goldResource;
    }

    public void addWood(int value){
        this.woodResource += value;
    }

    public void addStone(int value){
        this.stoneResource += value;
    }

    public void addGold(int value){
        this.goldResource += value;
    }


    public boolean hasEnoughResourcesForHouse() {
        if(this.woodResource >= AppConstants.WOOD_FOR_HOUSE && this.stoneResource >= AppConstants.STONE_FOR_HOUSE && this.goldResource >= AppConstants.GOLD_FOR_HOUSE){
            return true;
        }
        return false;
    }

    public void buildHouse() {
        woodResource -= AppConstants.WOOD_FOR_HOUSE;
        stoneResource -= AppConstants.STONE_FOR_HOUSE;
        goldResource -= AppConstants.GOLD_FOR_HOUSE;
        houses++;
    }

    public boolean hasEnoughHouses() {
        return this.houses == 3;
    }

    public int getWoodResource() {
        return woodResource;
    }

    public int getStoneResource() {
        return stoneResource;
    }

    public int getGoldResource() {
        return goldResource;
    }

    public void addResource(MyResource resource) {
        if(resource.resourceType.equals("woodResource")) {
            this.addWood(resource.resourceValue);
        }
        if(resource.resourceType.equals("stoneResource")) {
            this.addStone(resource.resourceValue);
        }
        if(resource.resourceType.equals("goldResource")) {
            this.addGold(resource.resourceValue);
        }
    }

    @Override
    public String toString() {
        return "Player{" +
                "woodResource=" + woodResource +
                ", stoneResource=" + stoneResource +
                ", goldResource=" + goldResource +
                ", houses=" + houses +
                '}';
    }
}
