package part2;

import game.Resource;

import java.io.Serializable;

public class MyPair implements Serializable {
    int array;
    int index;

    public MyPair(int array, int index) {
        this.array = array;
        this.index = index;
    }

    public int getArray() {
        return array;
    }

    public void setArray(int array) {
        this.array = array;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
