import java.util.Random;

public class GameResource{
    public static int[] woodArray;
    public static int[] stoneArray;
    public static int[] goldArray;
    public int[] getWoodArray() {
        return woodArray;
    }
    public GameResource() {
        woodArray = generateRandomArray(MyConstants.ARRAY_MAX_LENGTH, 30, 120);
        stoneArray = generateRandomArray(MyConstants.ARRAY_MAX_LENGTH, 20, 90);
        goldArray = generateRandomArray(MyConstants.ARRAY_MAX_LENGTH, 5, 40);
    }

    private static int[] generateRandomArray(int size, int randomLowerLimit, int randomUpperLimit) {
        if (randomLowerLimit >= randomUpperLimit) {
            throw new IllegalArgumentException("upper limit must be greater than lower limit");
        }

        int[] randArray = new int[size];
        Random r = new Random();
        for (int i = 0; i < size; i++) {
            randArray[i] = r.nextInt((randomUpperLimit - randomLowerLimit) + 1) + randomLowerLimit;
        }
        return randArray;
    }

    synchronized public int apply(ArrayAccessPair myPair){
        int aux =0;
        if(myPair.array == Resource.WOOD){
            aux = woodArray[myPair.index];
            woodArray[myPair.index] = 0;
        }
        else if(myPair.array == Resource.STONE)
        {
            aux= stoneArray[myPair.index];
            stoneArray[myPair.index] = 0;
        }
        else {
            aux=goldArray[myPair.index];
            goldArray[myPair.index] = 0;
        }
        return aux;
    }

}

