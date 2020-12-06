package game;

import jms.Consumer;
import jms.Producer;

import javax.jms.JMSException;
import java.util.Arrays;

public class Game  {
    private static GameResource gameResource = new GameResource();
    private static Player player1;
    private static Player player2;

    public static void main(String[] args) throws JMSException {
        System.out.println("thread " +Thread.currentThread().getName());
        System.out.println("Initial wood: " + Arrays.toString(GameResource.woodArray));
        System.out.println("Initial stone: "+ Arrays.toString(GameResource.stoneArray));
        System.out.println("Initial gold: "+ Arrays.toString(GameResource.goldArray));

        Consumer mainConsumer = new Consumer();
        mainConsumer.create("mainConsumer", MyConstants.PLAYER_QUEUE);

        Producer producer1 = new Producer();
        Producer producer2 = new Producer();
        producer1.create("player1producer", MyConstants.PLAYER_QUEUE);
        producer2.create("player2producer", MyConstants.PLAYER_QUEUE);
        player1 = new Player(gameResource, producer1);
        player2 = new Player(gameResource, producer2);

        Thread tp1 = new Thread(player1, MyConstants.THREAD_ONE_NAME);
        Thread tp2 = new Thread(player2, MyConstants.THREAD_TWO_NAME);

        tp1.start();
        tp2.start();

        synchronized (MyConstants.lock) {
            try {
                MyConstants.lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Resumed");
            tp1.interrupt();
            tp2.interrupt();
            player1.stopThread();
            player2.stopThread();

            System.out.println("Final wood: " + Arrays.toString(GameResource.woodArray));
            System.out.println("Final stone: "+ Arrays.toString(GameResource.stoneArray));
            System.out.println("Final gold: "+ Arrays.toString(GameResource.goldArray));

            int mess = 0;
            int winningPlayer = 0;

            System.out.println(producer1.getSentMessages() + " " + producer2.getSentMessages());

            while(mess < producer1.getSentMessages() + producer2.getSentMessages()) {
                String message = mainConsumer.receiveMessage();
                if(message.equals("Player 2 won")){
                    winningPlayer = 2;
                    break;
                }
                else if(message.equals("Player 1 won")) {
                    winningPlayer = 1;
                    break;
                }
                mess++;
            }

            mainConsumer.close();
            producer1.close();
            producer2.close();

            System.out.println((winningPlayer == 2) ? "Player 2 won" : "Player 1 won");
        }
    }
}
