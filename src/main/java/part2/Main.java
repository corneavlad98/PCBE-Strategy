package part2;

import jms.Subscriber;

import javax.jms.JMSException;

public class Main {
    public static void main(String[] args) throws JMSException {
        Subscriber subscriber = new Subscriber();
        subscriber.create("s", "aaa.t");
        MsgListener msgListener = new MsgListener();
        subscriber.messageConsumer.setMessageListener(msgListener);
        Thread thread = new Thread(new ResourceGenerator("a"));
        Thread thread1 = new Thread(new ResourceGenerator("b"));
        Thread thread2 = new Thread(new ResourceGenerator("c"));



        thread.start();
        thread2.start();
        thread1.start();

    }
}
