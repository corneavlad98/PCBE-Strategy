package part2;

import static part2.AppConstants.*;

public class Player {
    private int woodResource;
    private int stoneResource;
    private int goldResource;
    private int houses = 0;


    public Player() {
        woodResource = 0;
        stoneResource= 0;
        goldResource = 0;
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
        return this.woodResource >= WOOD_FOR_HOUSE && this.stoneResource >= STONE_FOR_HOUSE && this.goldResource >= GOLD_FOR_HOUSE;
    }

    public void buildHouse() {
        woodResource -= WOOD_FOR_HOUSE;
        stoneResource -= STONE_FOR_HOUSE;
        goldResource -= GOLD_FOR_HOUSE;
        houses++;
    }

    public boolean hasEnoughHouses() {
        return this.houses == 3;
    }

    public void addResource(MyResource resource) {
        if(resource.getResourceType().equals("woodResource")) {
            this.addWood(resource.getResourceValue());
        }
        if(resource.getResourceType().equals("stoneResource")) {
            this.addStone(resource.getResourceValue());
        }
        if(resource.getResourceType().equals("goldResource")) {
            this.addGold(resource.getResourceValue());
        }
    }

    @Override
    public String toString() {
        return "[" +
                "woodResource = " + woodResource +
                ", stoneResource = " + stoneResource +
                ", goldResource = " + goldResource +
                ", houses = " + houses +
                ']';
    }
}
