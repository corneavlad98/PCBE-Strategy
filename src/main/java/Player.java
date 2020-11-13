import java.util.Arrays;
import java.util.Random;

public class Player implements Runnable {
    //volatile variables, stored in computer's main memory
    private volatile int goldResource;
    private volatile int stoneResource;
    private volatile int woodResource;
    private int  houseCount = 0;

    private static boolean stopRequested = false;
    protected GameResource gameResource = null;
    private ArrayAccessPair randomPair;

    private volatile boolean exit = false;

    public Player (GameResource gameResource){
        this.gameResource = gameResource;
    }
    public ArrayAccessPair generateRandomRemovePair()
    {
        Random r = new Random();
        int arraySelect = r.nextInt(3);
        int indexSelect = r.nextInt(MyConstants.ARRAY_MAX_LENGTH);
        randomPair = new ArrayAccessPair(arraySelect,indexSelect);
        return randomPair;
    }

    public void removeRandomly() {
        ArrayAccessPair myPair = generateRandomRemovePair(); //generates random pair
        int aux = gameResource.apply(myPair);
        if(myPair.array==0){
            woodResource+=aux;
        }
        else if(myPair.array==1){
            stoneResource+=aux;
        }
        else if(myPair.array==2){
            goldResource+=aux;
        }
       System.out.println(Thread.currentThread().getName() + " ["+ myPair.getArray() + "," + myPair.getIndex()+ "] and got value: " + aux);
    }
    public void gameFinish()
    {
        System.out.println(Thread.currentThread().getName() + " won");
        System.out.println(Thread.currentThread().getName() + " Wood value: " + woodResource);
        System.out.println(Thread.currentThread().getName() + " Gold value: " + goldResource);
        System.out.println(Thread.currentThread().getName() + " Stone value: " + stoneResource);
        System.out.println("Final wood: " + Arrays.toString(GameResource.woodArray));
        System.out.println("Final stone: "+ Arrays.toString(GameResource.stoneArray));
        System.out.println("Final gold: "+ Arrays.toString(GameResource.goldArray));
        System.exit(0);
    }
    @Override
    public void run() {
        while (!exit) {
            try {
                removeRandomly();
                if (woodResource >= MyConstants.WOOD_FOR_HOUSE && stoneResource >= MyConstants.STONE_FOR_HOUSE && goldResource >= MyConstants.GOLD_FOR_HOUSE) {
                    System.out.println(Thread.currentThread().getName() + " Resources for house ready");
                    System.out.println(Thread.currentThread().getName() + " Building house...");
                    Thread.sleep(3000);
                    System.out.println(Thread.currentThread().getName() + " House built");
                    houseCount++;
                    woodResource -= MyConstants.WOOD_FOR_HOUSE;
                    stoneResource -= MyConstants.STONE_FOR_HOUSE;
                    goldResource -= MyConstants.GOLD_FOR_HOUSE;
                    if (houseCount == 3) {
                        exit = true;
                        gameFinish();
                    }
                }
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " got interupted");
            }
        }
    }
}
