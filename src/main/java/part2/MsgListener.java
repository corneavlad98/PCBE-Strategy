package part2;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

public class MsgListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        try {
            System.out.println("Message id is " + message.getJMSMessageID());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
