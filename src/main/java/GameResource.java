import java.util.Arrays;
import java.util.Random;

public class GameResource implements Runnable {
    public static int[] woodArray;
    public static int[] stoneArray;
    public static int[] goldArray;
    private static GameResource instance = null;


    public GameResource() {
        woodArray = generateRandomArray(MyConstants.WOOD_MAX_LENGTH);
        stoneArray = generateRandomArray(MyConstants.STONE_MAX_LENGTH);
        goldArray = generateRandomArray(MyConstants.GOLD_MAX_LENGTH);

    }
    private static int[] generateRandomArray(int size){
        int[] randArray = new int[size];
        Random rand = new Random();

        for (int i = 0; i < size; i++) {
            randArray[i] = rand.nextInt(100);
        }
        return randArray;
    }


//    public static GameResource getInstance() {
//        if(instance == null){
//            instance = new GameResource();
//        }
//        return instance;
//    }
    //TODO: keep track of the value and the array that was depleted
    public void apply(MyPair myPair){

        if(myPair.array == 0){
            woodArray[myPair.index] = 0;
            System.out.println(Arrays.toString(woodArray));
        }
        else if(myPair.array == 1)
        {
            stoneArray[myPair.index] = 0;
            System.out.println(Arrays.toString(stoneArray));
        }
        else {
            goldArray[myPair.index] = 0;
            System.out.println(Arrays.toString(goldArray));
        }
    }

    @Override
    public void run() {
        System.out.println("aaa" + Thread.currentThread().getName());
    }
}

