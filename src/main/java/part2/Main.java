package part2;

import game.MyConstants;
import jms.Consumer;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;

public class Main {
    public static void main(String[] args) throws JMSException, InterruptedException
    {
        //--------CONNECTIONS DECLARATIONS--------//
        Player player1 = new Player();
        Player player2 = new Player();

        //create resource topic subscribers
        player1.resourceTopicSubscriber.create("player1", MyConstants2.RESOURCE_TOPIC, "destination = 'player1'");
        player2.resourceTopicSubscriber.create("player2", MyConstants2.RESOURCE_TOPIC, "destination = 'player2'");

        //create player notify topic subscribers
        player1.playerNotifyTopicSubscriber.create("player1Notify", MyConstants2.PLAYER_NOTIFY_TOPIC);
        player2.playerNotifyTopicSubscriber.create("player2Notify", MyConstants2.PLAYER_NOTIFY_TOPIC);

        //create "to main queue" producers
        player1.toMainQueueProducer.create("player1Producer", MyConstants2.PLAYER_TO_MAIN_QUEUE);
        player2.toMainQueueProducer.create("player2Producer", MyConstants2.PLAYER_TO_MAIN_QUEUE);

        //create "to main queue" consumer
        Consumer mainConsumer = new Consumer();
        mainConsumer.create("main", MyConstants2.PLAYER_TO_MAIN_QUEUE);

        //assign listener to main that consumes messages coming from players(from player queue) and sends to player topic appropriate messages
        PlayerMessagesListener playerMessagesListener = new PlayerMessagesListener();
        playerMessagesListener.notifyPlayerPublisher.create("playerListener", MyConstants2.PLAYER_NOTIFY_TOPIC);
        mainConsumer.messageConsumer.setMessageListener(playerMessagesListener);



        //-----RESOURCE GENERATION------//
        //every generator sends random resource values to both players in the same Topic
        ResourceGenerator woodResource = new ResourceGenerator("woodResource", MyConstants2.RESOURCE_TOPIC);
       // ResourceGenerator stoneResource = new ResourceGenerator("stoneResource", MyConstants2.RESOURCE_TOPIC);
        //ResourceGenerator goldResource = new ResourceGenerator("goldResource", MyConstants2.RESOURCE_TOPIC);

        Thread thread1 = new Thread(woodResource);
        //Thread thread2 = new Thread(stoneResource);
        // Thread thread3 = new Thread(goldResource);

        //starting all resource threads (start generating resource values)
        thread1.start();
        //thread2.start();
        //thread3.start();

        //wait for threads to finish (all resources where put in Topic)
        thread1.join();
        //thread2.join();
        //thread3.join();

        System.out.println("Wood resources generated!");

        //-----RESOURCE CONSUMING WITH COMMUNICATION------//
        Thread tp1 = new Thread(player1, MyConstants2.THREAD_ONE_NAME);
        Thread tp2 = new Thread(player2, MyConstants2.THREAD_TWO_NAME);
        tp1.start();
        tp2.start();

        //should end when main sends specific message to player
        tp1.join();
        tp2.join();

        System.out.println("closing program...");

        //!close publisher connections!
        woodResource.publisher.closeConnection();
       // stoneResource.publisher.closeConnection();
        //goldResource.publisher.closeConnection();
        playerMessagesListener.notifyPlayerPublisher.closeConnection();

        //!close subscriber connections!
        player1.resourceTopicSubscriber.closeConnection();
        player2.resourceTopicSubscriber.closeConnection();
        player1.playerNotifyTopicSubscriber.closeConnection();
        player2.playerNotifyTopicSubscriber.closeConnection();

        //!close producer connections!
        player1.toMainQueueProducer.close();
        player2.toMainQueueProducer.close();

        //!close consumer connections!
        mainConsumer.close();

        System.out.println("main consumed from player1: " + playerMessagesListener.getPlayer1Messages() + " messages!");
        System.out.println("main consumed from player2: " + playerMessagesListener.getPlayer2Messages() + " messages!");

    }

}
