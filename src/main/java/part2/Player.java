package part2;

import jms.Subscriber;

public class Player {
    private int woodResource;
    private int stoneResource;
    private int goldResource;

    Subscriber subscriber = new Subscriber();

    public Player()
    {
        woodResource = 0;
        stoneResource= 0;
        goldResource = 0;
    }
    public void setResources(int woodResource, int stoneResource, int goldResource) {
        this.woodResource = woodResource;
        this.stoneResource = stoneResource;
        this.goldResource = goldResource;
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
}
