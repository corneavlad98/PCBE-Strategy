package part2;

import jms.Consumer;
import jms.Producer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import static part2.AppConstants.*;

public class Main {

    private static ResourceGenerator resourceGenerator;
    private static PlayerHandler game;

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
        System.out.println(game);
    }

    private static void instantiate() throws JMSException {

        //instantiate objects
        game = new PlayerHandler(new Player(), new Player());
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
        Message resourceMessage, playerMessage;
        while (true) {
            Thread.sleep(WAIT_TIME_TO_GENERATE_RESOURCE_MS);
            toResourceProducer.sendMessage(GENERATE_RESOURCE_MESSAGE);
            resourceMessage = resourceConsumer.messageConsumer.receive(100);
            playerMessage = playerConsumer.messageConsumer.receive(100);
            if(playerMessage != null) {
                if (playerMessage instanceof TextMessage) {
                    String text = ((TextMessage) playerMessage).getText();
                    System.out.println(text);
                    break;
                }
            }
            if(resourceMessage != null) {
                if (resourceMessage instanceof ObjectMessage) {
                    MyResource resource = (MyResource) ((ObjectMessage) resourceMessage).getObject();
                    game.manageResource(resource);
                }
            }
        }
    }

    private static void closeAllConnections() throws JMSException {
        resourceConsumer.close();
        playerConsumer.close();
        toResourceProducer.close();

        game.closeConnections();
        resourceGenerator.closeConnections();
    }
}
