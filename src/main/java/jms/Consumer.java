package jms;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.jms.core.BrowserCallback;

import javax.jms.*;
import java.util.Collections;
import java.util.Enumeration;

public class Consumer implements ExceptionListener {
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    private String clientId;
    private Connection connection;
    private Session session;
    private MessageConsumer messageConsumer;

    public void create(String clientId, String queueName) throws JMSException {
        this.clientId = clientId;
        // Create a ConnectionFactory
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);

        // Create a Connection
        connection = connectionFactory.createConnection();
        connection.start();

        connection.setExceptionListener(this);

        // Create a Session
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Create the destination (Topic or Queue)
        Queue queue = session.createQueue(queueName);


        // Create a MessageProducer from the Session to the Topic or Queue
        messageConsumer = session.createConsumer(queue);

    }

    public String receiveMessage() throws JMSException {
        Message message = messageConsumer.receive(1000);
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            String text = textMessage.getText();
            //System.out.println("Received: " + text);
            return text;
        } else {
            //System.out.println("Received: " + message);
            return message.toString();
        }
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

