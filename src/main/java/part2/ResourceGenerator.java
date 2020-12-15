package part2;

import jms.Consumer;
import jms.Producer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.util.Random;

import static part2.AppConstants.*;

public class ResourceGenerator{
    Producer producer = new Producer();
    Consumer consumer = new Consumer();

    public ResourceGenerator() throws JMSException {
        producer.create("ResourceGeneratorP", RESOURCE_TO_MAIN_QUEUE);
        consumer.create("ResourceGeneratorC", MAIN_TO_RESOURCES_QUEUE);
        consumer.messageConsumer.setMessageListener(new ResourceListener(this));
    }

    public void generateResourceAndSendMessage(Message message) {
        try {
            if(message instanceof TextMessage) {
                String text = ((TextMessage) message).getText();
                if(text.equals(GENERATE_RESOURCE_MESSAGE)) {
                    Random r = new Random();
                    int index = r.nextInt(RESOURCES.length);
                    int value = generateRandomResource(index);
                    producer.sendMessage(new MyResource(RESOURCES[index], value));
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private int generateRandomResource(int index) {
        if(RESOURCES[index].equals("woodResource")) {
            return generateRandomWithLimits(30,120);
        }
        if(RESOURCES[index].equals("stoneResource")) {
            return generateRandomWithLimits(20,90);
        }
        if(RESOURCES[index].equals("goldResource")) {
            return generateRandomWithLimits(5,40);
        }
        return 0;
    }

    private int generateRandomWithLimits(int randomLowerLimit, int randomUpperLimit) {
        if (randomLowerLimit >= randomUpperLimit) {
            throw new IllegalArgumentException("upper limit must be greater than lower limit");
        }
        Random r = new Random();
        return r.nextInt((randomUpperLimit - randomLowerLimit) + 1) + randomLowerLimit;
    }

    public void closeConnections() throws JMSException {
        producer.close();
        consumer.close();
    }
}
