package part2;

import jms.Producer;
import jms.Publisher;

import javax.jms.JMSException;
import java.util.Random;

public class ResourceGenerator implements Runnable {
    Producer producer = new Producer();

    public ResourceGenerator() throws JMSException {
        producer.create("ResourceGenerator", AppConstants.RESOURCE_TO_PLAYER_QUEUE);
    }

    public void closeConnections() throws JMSException {
        producer.close();
    }

    String[] resources = {"woodResource", "stoneResource", "goldResource"};

    @Override
    public void run() {
//        int i = 0;
//        while (i < 6) {
//            try {
//                Thread.sleep(1000);
//
//                //trimitem resurse pentru ambii playeri
//                publisher.sendMessage(new MyResource(clientId, generateRandomResource(), "player1"));
//                publisher.sendMessage(new MyResource(clientId, generateRandomResource(),"player2"));
//                i++;
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }   catch (JMSException e) {
//                e.printStackTrace();
//            }
//        }
      Random r = new Random();
        while (true) {
            try {
                Thread.sleep(1000);
                int index = r.nextInt(3);
                int value = generateRandomResource(index);
                producer.sendMessage(new MyResource(resources[index], value));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    private int generateRandomResource(int index) {
        if(resources[index].equals("woodResource")) {
            return generateRandomWithLimits(30,120);
        }
        if(resources[index].equals("stoneResource")) {
            return generateRandomWithLimits(20,90);
        }
        if(resources[index].equals("goldResource")) {
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
