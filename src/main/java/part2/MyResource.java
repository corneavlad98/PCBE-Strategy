package part2;

import game.Resource;

import java.io.Serializable;

public class MyResource implements Serializable {
    int resourceValue;
    String destination;
    String resourceType;

    public MyResource(String resourceType, int resourceValue, String destination) {
        this.resourceType = resourceType;
        this.resourceValue = resourceValue;
        this.destination = destination;

    }

    public String getResourceType() {
        return resourceType;
    }
    public String getDestination() {
        return destination;
    }
    public int getResourceValue() {
        return resourceValue;
    }



}