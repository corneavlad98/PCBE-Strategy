package part2;

import game.MyConstants;
import jms.Consumer;
import jms.Producer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import java.util.Random;

public class PlayerHandler implements Runnable{

    Player player1, player2;
    Producer producer = new Producer();
    Consumer consumer = new Consumer();

    public PlayerHandler(Player player1, Player player2) throws JMSException {
        this.player1 = player1;
        this.player2 = player2;
        consumer.create("PlayersConsumer", AppConstants.RESOURCE_TO_PLAYER_QUEUE);
        producer.create("PlayersProducer", AppConstants.PLAYER_TO_MAIN_QUEUE);
    }



//    public void addResource(String destination, String resourceType, int resourceValue) {
//        if(destination.equals("player1")){
//            if (resourceType.equals("woodResource")) {
//                player1.addWood(resourceValue);
//            }
//            else{
//                if(resourceType.equals("stoneResource")) {
//                    player1.addStone(resourceValue);
//                }
//                else {
//                    player1.addGold(resourceValue);
//                }
//            }
//            if(player1.hasEnoughResourcesForHouse()) {
//                System.out.println("House");
//                player1.buildHouse();
//            }
//        }
//        else {
//            if (resourceType.equals("woodResource")) {
//                player2.addWood(resourceValue);
//            }
//            else{
//                if(resourceType.equals("stoneResource")) {
//                    player2.addStone(resourceValue);
//                }
//                else {
//                    player2.addGold(resourceValue);
//                }
//            }
//
//            if(player2.hasEnoughResourcesForHouse()) {
//                System.out.println("House");
//                player2.buildHouse();
//            }
//
//        }
//    }

    @Override
    public void run() {
        while (true) {
            try {
                Message message = consumer.messageConsumer.receive();
                if(message != null) {
                    if(message instanceof ObjectMessage) {
                        MyResource resource = (MyResource) ((ObjectMessage) message).getObject();
                        System.out.println("Received resource " + resource);
                        Random random = new Random();
                        int selector = random.nextInt(2);
                        if(selector == 0) {
                            player1.addResource(resource);
                            System.out.println(1 + " " + player1);
                            if(player1.hasEnoughResourcesForHouse()) {
                                player1.buildHouse();
                            }
                        }
                        else {
                            player2.addResource(resource);
                            System.out.println(2 + " " + player2);
                            if(player2.hasEnoughResourcesForHouse()){
                                player2.buildHouse();
                            }
                        }
                    }

                    if(player1.hasEnoughHouses()) {
                        notifyMain(1);
                    }

                    if(player2.hasEnoughHouses()) {
                        notifyMain(2);
                    }
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    private void notifyMain(int playerIndex) throws JMSException {
        producer.sendMessage(playerIndex + "");
    }

    public void closeConnections() throws JMSException {
        consumer.close();
        producer.close();
    }
}
