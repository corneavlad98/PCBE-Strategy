package part2;

import game.Resource;

import java.io.Serializable;

public class MyResource implements Serializable {
    private int resourceValue;
    private String resourceType;

    public MyResource(String resourceType, int resourceValue) {
        this.resourceType = resourceType;
        this.resourceValue = resourceValue;
    }
    public String getResourceType() {
        return resourceType;
    }
    public int getResourceValue() {
        return resourceValue;
    }

    @Override
    public String toString() {
        return "MyResource{" +
                "resourceValue=" + resourceValue +
                ", resourceType='" + resourceType + '\'' +
                '}';
    }
}