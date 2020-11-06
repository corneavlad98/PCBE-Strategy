import java.util.Arrays;

public class Game  {
    private static GameResource gameResource;
    private static Player player1 = new Player(1);
    private static Player player2 = new Player(2);

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName());

        gameResource = new GameResource();
        Thread t1 = new Thread(gameResource);
        Thread tp1 = new Thread(player1);
        Thread tp2 = new Thread(player2);
        t1.start();
        System.out.println(Arrays.toString(GameResource.woodArray));
        System.out.println(Arrays.toString(GameResource.stoneArray));
        System.out.println(Arrays.toString(GameResource.goldArray));
        tp1.start();
        tp2.start();
        gameResource.apply(player1.randomEvent);
        gameResource.apply(player2.randomEvent);
    }
}
