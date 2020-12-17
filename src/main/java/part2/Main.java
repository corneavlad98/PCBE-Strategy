package part2;

import jms.Consumer;
import jms.Producer;

import javax.jms.JMSException;
import static part2.AppConstants.*;

public class Main {

    private static ResourceGenerator resourceGenerator;
    private static PlayerHandler playerHandler;

    private static Consumer resourceConsumer;
    private static Consumer playerConsumer;
    private static Producer toResourceProducer;

    public static void main(String[] args)  {
        try {
            instantiate();
            runGame();
            closeAllConnections();
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(playerHandler);
    }

    private static void instantiate() throws JMSException {

        //instantiate objects
        playerHandler = new PlayerHandler(new Player(), new Player());
        resourceGenerator = new ResourceGenerator();

        //create connections
        //consumers
        resourceConsumer = new Consumer();
        playerConsumer = new Consumer();
        resourceConsumer.create("MainResourceConsumer", RESOURCE_TO_MAIN_QUEUE);
        playerConsumer.create("MainPlayerConsumer", PLAYER_TO_MAIN_QUEUE);

        //producers
        toResourceProducer = new Producer();
        toResourceProducer.create("ToResourceProducer", MAIN_TO_RESOURCES_QUEUE);
    }

    private static void runGame() throws JMSException, InterruptedException {
        String  playerMessage;
        MyResource resourceMessage;
        while (true) {
            Thread.sleep(WAIT_TIME_TO_GENERATE_RESOURCE_MS);

            //tell resource generator to make a resource
            toResourceProducer.sendMessage(GENERATE_RESOURCE_MESSAGE);
            //receive the resource from the generator
            resourceMessage = resourceConsumer.receiveResourceMessage(MESSAGE_LATENCY);

            if(resourceMessage != null) {
                playerHandler.manageResource(resourceMessage);
            }

            //receive notification message from player handler
            playerMessage = playerConsumer.receivePlayerMessage(MESSAGE_LATENCY);
            if(playerMessage.contains("has won")) {
                System.out.println(playerMessage);
                break;
            }
            else if(playerMessage.contains("is ready to build a house")) {
                if(playerMessage.contains("1")) {
                    playerHandler.letPlayerBuildHouse(1);
                }
                if(playerMessage.contains("2")) {
                    playerHandler.letPlayerBuildHouse(2);
                }
            }
        }
    }

    private static void closeAllConnections() throws JMSException {
        resourceConsumer.close();
        playerConsumer.close();
        toResourceProducer.close();

        playerHandler.closeConnections();
        resourceGenerator.closeConnections();
    }
}
