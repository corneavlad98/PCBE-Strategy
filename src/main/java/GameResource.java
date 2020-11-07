
import java.util.Arrays;
import java.util.Random;

public class GameResource{
    public static int[] woodArray;
    public static int[] stoneArray;
    public static int[] goldArray;

    public GameResource() {
        //3 resources arrays, generated randomly
        woodArray = generateRandomArray(MyConstants.ARRAY_MAX_LENGTH, 30, 120);
        stoneArray = generateRandomArray(MyConstants.ARRAY_MAX_LENGTH, 20, 90);
        goldArray = generateRandomArray(MyConstants.ARRAY_MAX_LENGTH, 5, 40);
    }

    //generate array with given size that contains random numbers between given range
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


    //TODO: keep track of the value and the array that was depleted
    synchronized public void apply(ArrayAccessPair myPair){
        if(myPair.array == 0){
            woodArray[myPair.index] = 0;
        }
        else if(myPair.array == 1)
        {
            stoneArray[myPair.index] = 0;
        }
        else {
            goldArray[myPair.index] = 0;
        }
    }

}

