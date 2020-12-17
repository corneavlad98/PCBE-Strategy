package jms;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
public  class Producer {
    private String clientId;
    private Connection connection;
    private Session session;
    private MessageProducer messageProducer;

    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    public void create(String clientId, String queueName) throws JMSException {
        this.clientId = clientId;
        // Create a ConnectionFactory
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);

        // Create a Connection
        connection = connectionFactory.createConnection();
        connection.start();

        // Create a Session
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Create the destination (Topic or Queue)
        Queue queue = session.createQueue(queueName);

        // Create a MessageProducer from the Session to the Topic or Queue
        messageProducer = session.createProducer(queue);
        messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
    }

    public void sendMessage(String message) throws JMSException {
        TextMessage textMessage = session.createTextMessage(message);
        // send the message to the queue destination
        messageProducer.send(textMessage);
        System.out.println(clientId + ": sent message with text: '" + message + "'");

    }

    public void close() throws JMSException {
        session.close();
        connection.close();
    }
}
