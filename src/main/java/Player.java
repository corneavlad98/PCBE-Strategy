import java.util.Arrays;
import java.util.Random;

public class Player implements Runnable {
    //volatile variables, stored in computer's main memory
    private volatile int goldResource;
    private volatile int stoneResource;
    private volatile int woodResource;
    private int  houseCount = 0;
    protected GameResource gameResource = null;
    private ArrayAccessPair randomPair;

    private volatile boolean exit = false;

    public Player (GameResource gameResource){
        this.gameResource = gameResource;
    }
    public ArrayAccessPair generateRandomRemovePair() {
        Random r = new Random();
        Resource resourceArray[] = Resource.values();
        int arraySelect = r.nextInt(3);
        int indexSelect = r.nextInt(MyConstants.ARRAY_MAX_LENGTH);
        randomPair = new ArrayAccessPair(resourceArray[arraySelect], indexSelect);
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
       System.out.println(Thread.currentThread().getName() + " ["+ myPair.getArray() + ", " + myPair.getIndex()+ "] and got value: " + aux);
    }

    public void stopThread() {
        exit = true;
    }
//
//    @Override
//    public void run() {
//        while (!exit) {
//            try {
//                removeRandomly();
//                if (woodResource >= MyConstants.WOOD_FOR_HOUSE && stoneResource >= MyConstants.STONE_FOR_HOUSE && goldResource >= MyConstants.GOLD_FOR_HOUSE) {
//                    if (houseCount != 3) {
//
//
//                        //System.out.println(Thread.currentThread().getName() + " Resources for house ready");
//                        //System.out.println(Thread.currentThread().getName() + " Building house...");
//                        Thread.sleep(3000);
//                        System.out.println(Thread.currentThread().getName() + " House built");
//                        houseCount++;
//                        woodResource -= MyConstants.WOOD_FOR_HOUSE;
//                        stoneResource -= MyConstants.STONE_FOR_HOUSE;
//                        goldResource -= MyConstants.GOLD_FOR_HOUSE;
//                    }
//                    else {
//                        synchronized (Game.lock) {
//                            Game.lock.notify();
//                            exit = true;
//                            System.out.println(Thread.currentThread().getName() + " won");
//                        }
//
//                    }
//
//                }
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                System.out.println(Thread.currentThread().getName() + " got interrupted");
//            }
//        }
//    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                removeRandomly();
                if (woodResource >= MyConstants.WOOD_FOR_HOUSE && stoneResource >= MyConstants.STONE_FOR_HOUSE && goldResource >= MyConstants.GOLD_FOR_HOUSE) {
                    if (houseCount != 3) {
                        System.out.println(Thread.currentThread().getName() + " Resources for house ready");
                        System.out.println(Thread.currentThread().getName() + " Building house...");
                        Thread.sleep(3000);
                        System.out.println(Thread.currentThread().getName() + " House built");
                        houseCount++;
                        woodResource -= MyConstants.WOOD_FOR_HOUSE;
                        stoneResource -= MyConstants.STONE_FOR_HOUSE;
                        goldResource -= MyConstants.GOLD_FOR_HOUSE;
                    }
                    else {
                        System.out.println(Thread.currentThread().getName() + " won");
                        //Thread.currentThread().interrupt();
                        synchronized (Game.lock) {
                            Game.lock.notify();

                        }
                    }
                }
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(Thread.currentThread().getName() + " got interrupted");
            }
        }
    }


}
