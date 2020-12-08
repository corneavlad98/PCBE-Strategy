package part2;

import jms.Producer;
import jms.Publisher;

import javax.jms.JMSException;
import java.util.Random;

public class ResourceGenerator implements Runnable {

    Publisher publisher = new Publisher();
    public ResourceGenerator(String s) throws JMSException {
        publisher.create(s, "aaa.t");
    }
    @Override
    public void run() {
        int i = 0;
        while (i < 10) {
            try {
                Thread.sleep(2000);
                publisher.sendMessage(new MyPair(generateRandomResource(), generateRandomResource()));
                i++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
        try {
            publisher.closeConnection();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private int generateRandomResource() {
        Random random = new Random();
        return random.nextInt(10);
    }
}
