package jms;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import part2.MyResource;

public class Publisher {

    private String clientId;
    private Connection connection;
    private Session session;
    private MessageProducer messageProducer;

    public void create(String clientId, String topicName) throws JMSException {
        this.clientId = clientId;

        // create a Connection Factory
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
        connectionFactory.setTrustAllPackages(true);

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

    public void sendMessage(MyResource myResource) throws JMSException {
        ObjectMessage objectMessage = session.createObjectMessage(myResource);
        objectMessage.setStringProperty("destination", myResource.getDestination());

        messageProducer.send(objectMessage);
        System.out.println("sent object message " + myResource.getResourceType() + " " + myResource.getResourceValue() + " for " + myResource.getDestination());
    }

    public void sendMessage(String text, String destination) throws JMSException {
        // create a JMS TextMessage
        TextMessage textMessage = session.createTextMessage(text);
        textMessage.setStringProperty("destination", destination);

        // send the message to the topic destination
        messageProducer.send(textMessage);
        System.out.println(clientId + ": sent message with text= '" + text + "' to: " + destination);
    }
}