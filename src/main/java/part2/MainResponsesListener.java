package part2;

import javax.jms.*;

public class MainResponsesListener implements MessageListener {
    private Player player1;
    private Player player2;
    @Override
    public void onMessage(Message message) {
        if(message instanceof TextMessage)
        {
            try {
                String textMessage = ((TextMessage) message).getText();
                if(textMessage.equals("Ok, player 1 build house!"));
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

}
