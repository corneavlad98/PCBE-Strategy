import java.util.Random;

public class Player implements Runnable {
    protected GameResource gameResource = null;
    //TODO: Keep track of each resource
    private ArrayAccessPair randomPair;
    //aim is to get to an amount of each resource
    public Player (GameResource gameResource){
        this.gameResource = gameResource;
    }

    //TODO: Actually random
    //generate random pair to select array and an index from it
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
        gameResource.apply(myPair); //remove resource with the given pair
        System.out.println(Thread.currentThread().getName() + " removed from array: " + myPair.getArray() + " resource from index: " + myPair.getIndex());
    }

    @Override
    public void run() {
        try{
            for (int i = 0; i < 4; i++)
            {
                removeRandomly();
                Thread.sleep(2000);
            }

        }catch (InterruptedException e){
            System.out.println("Thread "+ Thread.currentThread().getName() + " interrupted");
        }
    }
}
