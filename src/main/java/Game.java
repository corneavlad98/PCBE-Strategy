import java.util.Arrays;

public class Game  {
    private static GameResource gameResource = new GameResource();
    private static Player player1 = new Player(gameResource);
    private static Player player2 = new Player(gameResource);

    public static void main(String[] args){
        System.out.println("thread " +Thread.currentThread().getName());
        System.out.println("Initial wood: " + Arrays.toString(GameResource.woodArray));
        System.out.println("Initial stone: "+ Arrays.toString(GameResource.stoneArray));
        System.out.println("Initial gold: "+ Arrays.toString(GameResource.goldArray));

        Thread tp1 = new Thread(player1, MyConstants.THREAD_ONE_NAME);
        Thread tp2 = new Thread(player2, MyConstants.THREAD_TWO_NAME);
        tp1.start();
        tp2.start();
    }
}
