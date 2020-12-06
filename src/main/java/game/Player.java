package game;

import jms.Producer;

import javax.jms.JMSException;
import java.util.Random;

public class Player implements Runnable {
    //volatile variables, stored in computer's main memory
    private volatile int goldResource;
    private volatile int stoneResource;
    private volatile int woodResource;
    private Producer producer;
    private int houseCount = 0;

    private static boolean stopRequested = false;
    protected GameResource gameResource = null;
    private ArrayAccessPair randomPair;
    private volatile boolean exit = false;

    public Player (GameResource gameResource, Producer producer){
        this.gameResource = gameResource;
        this.producer = producer;
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

    public void removeRandomly() throws JMSException {
        ArrayAccessPair myPair = generateRandomRemovePair(); //generates random pair
        int aux = gameResource.apply(myPair);
        if(myPair.array== Resource.WOOD){
            woodResource+=aux;
        }
        else if(myPair.array== Resource.STONE){
            stoneResource+=aux;
        }
        else if(myPair.array== Resource.GOLD){
            goldResource+=aux;
        }
//        System.out.println(Thread.currentThread().getName() + " ["+ myPair.getArray() + "," + myPair.getIndex()+ "] and got value: " + aux);
        this.producer.sendMessage(myPair.getArray().ordinal(), myPair.getIndex());
    }
    public void stopThread() {
        exit = true;
    }
    @Override
    public void run() {
        while (!exit && !Thread.currentThread().isInterrupted()) {
            try {
                if(houseCount != 3) {
                    try {
                        try {
                            removeRandomly();
                        } catch (JMSException e) {
                            e.printStackTrace();
                        }
                        if (woodResource >= MyConstants.WOOD_FOR_HOUSE && stoneResource >= MyConstants.STONE_FOR_HOUSE && goldResource >= MyConstants.GOLD_FOR_HOUSE) {
                            producer.sendTerminalMessage(Thread.currentThread().getName() + " Resources for house ready");
                            producer.sendTerminalMessage(Thread.currentThread().getName() + " Building house...");
                            Thread.sleep(3000);
                            producer.sendTerminalMessage(Thread.currentThread().getName() + " House built");
                            houseCount++;
                            woodResource -= MyConstants.WOOD_FOR_HOUSE;
                            stoneResource -= MyConstants.STONE_FOR_HOUSE;
                            goldResource -= MyConstants.GOLD_FOR_HOUSE;
                        }
                    } catch (InterruptedException e) {
                        System.out.println(Thread.currentThread().getName() + " got interupted in 3000ms sleep!");
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                    Thread.sleep(500);
                }
                else {
                    synchronized (MyConstants.lock) {
                        producer.sendTerminalMessage(Thread.currentThread().getName() + " won");
                        MyConstants.lock.notify();
                        Thread.currentThread().interrupt();
                    }
                }
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " got interupted");
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
