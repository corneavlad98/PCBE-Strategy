import java.util.Random;

public class Player implements Runnable {
    private volatile int goldResource;
    private volatile int stoneResource;
    private volatile int woodResource;
    private volatile int  houseCount = 0;
    private static boolean stopRequested = false;
    private static boolean isGameOn = true;
    protected GameResource gameResource = null;
    private ArrayAccessPair randomPair;
    //aim is to get to an amount of each resource
    public Player (GameResource gameResource){
        this.gameResource = gameResource;
    }

    public static synchronized boolean isStopRequested() {
        return stopRequested;
    }

    public static synchronized void requestStop(){
        stopRequested = true;
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

    @Override
    public void run() {
        try{
            while (!isStopRequested())
            {
                removeRandomly();
                if(woodResource >= MyConstants.WOOD_FOR_HOUSE && stoneResource >= MyConstants.STONE_FOR_HOUSE && goldResource >= MyConstants.GOLD_FOR_HOUSE)
                {
                    System.out.println(Thread.currentThread().getName() + " Resources for house ready");
                    System.out.println(Thread.currentThread().getName() + " Building house...");
                    Thread.sleep(5000);
                    System.out.println(Thread.currentThread().getName() + " House built");
                    houseCount++;
                    woodResource -= MyConstants.WOOD_FOR_HOUSE;
                    stoneResource -= MyConstants.STONE_FOR_HOUSE;
                    goldResource -= MyConstants.GOLD_FOR_HOUSE;
                    if(houseCount==3){
                        requestStop();
                    }
                }
                Thread.sleep(500);
            }
            if(isGameOn) {
                System.out.println(Thread.currentThread().getName() + " won");
                isGameOn = false;
            }
            System.out.println(Thread.currentThread().getName() + " Wood value: " + woodResource);
            System.out.println(Thread.currentThread().getName() + " Gold value: " + goldResource);
            System.out.println(Thread.currentThread().getName() + " Stone value: " + stoneResource);
        }catch (InterruptedException e){
            System.out.println("Thread "+ Thread.currentThread().getName() + " interrupted");
        }
    }
}
