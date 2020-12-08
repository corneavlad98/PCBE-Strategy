package jms;

import javax.jms.*;

import game.ArrayAccessPair;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import part2.MyPair;

public class Publisher {

    private String clientId;
    private Connection connection;
    private Session session;
    private MessageProducer messageProducer;

    public void create(String clientId, String topicName) throws JMSException {
        this.clientId = clientId;

        // create a Connection Factory
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
        ((ActiveMQConnectionFactory) connectionFactory).setTrustAllPackages(true);

        // create a Connection
        connection = connectionFactory.createConnection();
        connection.setClientID(clientId);

        // create a Session
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // create the Topic to which messages will be sent
        Topic topic = session.createTopic(topicName);

        // create a MessageProducer for sending messages
        messageProducer = session.createProducer(topic);
    }

    public void closeConnection() throws JMSException {
        connection.close();
    }

    public void sendMessage(MyPair accessPair) throws JMSException {
        ObjectMessage objectMessage = session.createObjectMessage(accessPair);

        messageProducer.send(objectMessage);

        System.out.println("sent object message " + accessPair);
    }

    public void sendMessage(String text) throws JMSException {

        // create a JMS TextMessage
        TextMessage textMessage = session.createTextMessage(text);

        // send the message to the topic destination
        messageProducer.send(textMessage);

        System.out.println(clientId + ": sent message with text={" + text + "}");
    }
}