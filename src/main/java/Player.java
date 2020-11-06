public class Player implements Runnable {
    //public GameResource gameResource;
    //TODO: Keep track of each resource
    public MyPair randomEvent = new MyPair(1,1);
    public int[] arr = new int[3];
    //aim is to get to an amount of each resource
    private int id;
    public Player (int id){
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }
    //TODO: Actually random
    public void getRandomEvent() {
        randomEvent = new MyPair(id,1);
        System.out.println(" xxxx" +  randomEvent.array);
    }

    public void setRandomEvent(MyPair randomEvent) {
        this.randomEvent = randomEvent;
    }

    @Override
    public void run() {
        System.out.println(this.id + " " + Thread.currentThread().getName());
        getRandomEvent();
    }
}
