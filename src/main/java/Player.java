public class Player implements Runnable {
    //public GameResource gameResource;
    public MyPair randomEvent = new MyPair(1,1);
    private int id;
    public Player (int id){
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
