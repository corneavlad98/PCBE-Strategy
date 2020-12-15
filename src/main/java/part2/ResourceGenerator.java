package part2;

import jms.Publisher;

import javax.jms.JMSException;
import java.util.Random;

public class ResourceGenerator implements Runnable {
    String clientId;
    Publisher publisher = new Publisher();

    public ResourceGenerator(String clientId, String topicName) throws JMSException {
        this.clientId = clientId;
        publisher.create(clientId, topicName);
    }

    @Override
    public void run() {
        int i = 0;
        while (i < 15) {
            try {
                Thread.sleep(200);

                //trimitem resurse pentru ambii playeri
                publisher.sendMessage(new MyResource(clientId, generateRandomResource(), "player1"));
                publisher.sendMessage(new MyResource(clientId, generateRandomResource(),"player2"));
                i++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }   catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    private int generateRandomResource() {
        if(clientId.equals("woodResource")) {
           return generateRandomWithLimits(30,120);
        }
        if(clientId.equals("stoneResource")) {
            return generateRandomWithLimits(20,90);
        }
        if(clientId.equals("goldResource")) {
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
}
