public class ArrayAccessPair {
    Resource array;
    int index;

    public ArrayAccessPair(Resource array, int index) {
        this.array = array;
        this.index = index;
    }

    public Resource getArray() {
        return array;
    }

    public void setArray(Resource array) {
        this.array = array;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
