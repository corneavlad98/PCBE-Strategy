package jms;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.jms.core.BrowserCallback;
import part2.MyResource;
import part2.ResourceListener;

import javax.jms.*;
import java.util.Collections;
import java.util.Enumeration;

public class Consumer implements ExceptionListener {
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    private String clientId;
    private Connection connection;
    private Session session;
    private MessageConsumer messageConsumer;
    private Queue queue;

    public void create(String clientId, String queueName) throws JMSException {
        this.clientId = clientId;
        // Create a ConnectionFactory
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        connectionFactory.setTrustAllPackages(true);

        // Create a Connection
        connection = connectionFactory.createConnection();
        connection.start();

        connection.setExceptionListener(this);

        // Create a Session
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Create the destination (Topic or Queue)
        queue = session.createQueue(queueName);

        // Create a MessageProducer from the Session to the Topic or Queue
        messageConsumer = session.createConsumer(queue);

    }

    public Message receive(int latency) throws JMSException {
        return this.messageConsumer.receive(latency);
    }

    public void setMessageListener(MessageListener messageListener) throws JMSException {
        this.messageConsumer.setMessageListener(messageListener);
    }

    public String receivePlayerMessage(int latency) throws JMSException {
        Message message = messageConsumer.receive(latency);
        if (message instanceof TextMessage) {
            String text = ((TextMessage) message).getText();
            return text;
        }
        return "";
    }
    public MyResource receiveResourceMessage(int latency) throws JMSException {
        Message message = messageConsumer.receive(latency);
        if(message instanceof ObjectMessage) {
            MyResource resource = (MyResource) ((ObjectMessage) message).getObject();
            System.out.println("Main got: " + resource.getResourceType() + " with value: " + resource.getResourceValue());
            return resource;
        }
        return null;
    }

    public void close() throws JMSException {
        messageConsumer.close();
        session.close();
        connection.close();
    }

    public synchronized void onException(JMSException ex) {
        System.out.println("JMS Exception occured.  Shutting down client.");
    }
}

