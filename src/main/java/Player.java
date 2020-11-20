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
        Resource arr[] = Resource.values();
        int arraySelect = r.nextInt(3);
        int indexSelect = r.nextInt(MyConstants.ARRAY_MAX_LENGTH);
        randomPair = new ArrayAccessPair(arr[arraySelect], indexSelect);
        return randomPair;
    }

    public void removeRandomly() {
        ArrayAccessPair myPair = generateRandomRemovePair(); //generates random pair
        int aux = gameResource.apply(myPair);
        if(myPair.array==Resource.WOOD){
            woodResource+=aux;
        }
        else if(myPair.array==Resource.STONE){
            stoneResource+=aux;
        }
        else if(myPair.array==Resource.GOLD){
            goldResource+=aux;
        }
        System.out.println(Thread.currentThread().getName() + " ["+ myPair.getArray() + "," + myPair.getIndex()+ "] and got value: " + aux);
    }
    public void stopThread() {
        exit = true;
    }
    @Override
    public void run() {
        while (!exit && !Thread.currentThread().isInterrupted())
        {
            try
            {
                if(houseCount != 3)
                {
                    try
                    {
                        removeRandomly();
                        if (woodResource >= MyConstants.WOOD_FOR_HOUSE && stoneResource >= MyConstants.STONE_FOR_HOUSE && goldResource >= MyConstants.GOLD_FOR_HOUSE)
                        {
                            System.out.println(Thread.currentThread().getName() + " Resources for house ready");
                            System.out.println(Thread.currentThread().getName() + " Building house...");
                            Thread.sleep(3000);
                            System.out.println(Thread.currentThread().getName() + " House built");
                            houseCount++;
                            woodResource -= MyConstants.WOOD_FOR_HOUSE;
                            stoneResource -= MyConstants.STONE_FOR_HOUSE;
                            goldResource -= MyConstants.GOLD_FOR_HOUSE;
                        }
                    }catch (InterruptedException e)
                    {
                        System.out.println(Thread.currentThread().getName() + " got interupted in 3000ms sleep!");
                    }
                    Thread.sleep(500);
                }
                else
                {
                    synchronized (MyConstants.lock)
                    {
                        System.out.println(Thread.currentThread().getName() + " won");
                        MyConstants.lock.notify();
                        Thread.currentThread().interrupt();
                    }
                }
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " got interupted");
            }
        }
    }
}
