package part2;

import jms.Consumer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

public class Main {
//    private static Player player1 = new Player();
//    private static Player player2 = new Player();
//    private static Consumer mainConsumer = new Consumer();
//
//    public static void main(String[] args) throws JMSException, InterruptedException {
//        //-----RESOURCE GENERATION------//
//
//        player1.subscriber.create("player1", AppConstants.RESOURCE_TOPIC, "destination = 'player1'");
//        player2.subscriber.create("player2", AppConstants.RESOURCE_TOPIC, "destination = 'player2'");
//
//        player1.producer.create("player1p", AppConstants.PLAYER_QUEUE);
//        player2.producer.create("player2p", AppConstants.PLAYER_QUEUE);
//
//        mainConsumer.create("main", AppConstants.PLAYER_QUEUE);
//
//        ResourceListener resourceListener = new ResourceListener(new PlayerHandler(player1, player2));
//
//        player1.subscriber.messageConsumer.setMessageListener(resourceListener);
//        player2.subscriber.messageConsumer.setMessageListener(resourceListener);
//        mainConsumer.messageConsumer.setMessageListener(new MainMessageListener());
//
//        //every generator sends random resource values to both players in the same Topic
//        ResourceGenerator woodResource = new ResourceGenerator("woodResource", AppConstants.RESOURCE_TOPIC);
//        ResourceGenerator stoneResource = new ResourceGenerator("stoneResource", AppConstants.RESOURCE_TOPIC);
//        ResourceGenerator goldResource = new ResourceGenerator("goldResource", AppConstants.RESOURCE_TOPIC);
//
//        Thread thread1 = new Thread(woodResource);
//        Thread thread2 = new Thread(stoneResource);
//        Thread thread3 = new Thread(goldResource);
//
//        //starting all resource threads (start generating resource values)
//        thread1.start();
//        thread2.start();
//        thread3.start();
//
//        //wait for threads to finish
//        thread1.join();
//        thread2.join();
//        thread3.join();
//
//        //close publisher connections
//        woodResource.publisher.closeConnection();
//        stoneResource.publisher.closeConnection();
//        goldResource.publisher.closeConnection();
//
//        //close subscriber connections
//        player1.subscriber.closeConnection();
//        player2.subscriber.closeConnection();
//        mainConsumer.close();
//
//
//        //check if fields where set correctly
//        System.out.println("["+ player1.getWoodResource()+ "," + player1.getStoneResource() + "," + player1.getGoldResource() + "]");
//        System.out.println("["+ player2.getWoodResource()+ "," + player2.getStoneResource() + "," + player2.getGoldResource() + "]");
//
//    }
    private static PlayerHandler game;
    private static Consumer consumer;
    private static ResourceGenerator resourceGenerator;
    static Thread resourceThread, gameThread;
    public static void instantiate() throws JMSException {
        game = new PlayerHandler(new Player(), new Player());
        resourceGenerator = new ResourceGenerator();
        consumer = new Consumer();
        consumer.create("MainConsumer", AppConstants.PLAYER_TO_MAIN_QUEUE);
        resourceThread = new Thread(resourceGenerator);
        gameThread = new Thread(game);
    }

    public static void main(String[] args) {
        Message message;
        try {
            instantiate();
            startGame();
            while (true) {
                //TODO: main cannot receive message
                message = consumer.messageConsumer.receive();
                if(message != null){
                    System.out.println("in main");
                    String text = ((TextMessage)message).getText();
                    System.out.println(text);
                    break;
                }
            }
            closeAllConnections();
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static void startGame() throws InterruptedException {
        resourceThread.setDaemon(true);
        gameThread.setDaemon(true);

        resourceThread.start();
        gameThread.start();

        resourceThread.join();
        gameThread.join();

    }

    private static void closeAllConnections() throws JMSException {
        consumer.close();
        game.closeConnections();
        resourceGenerator.closeConnections();
    }
}
