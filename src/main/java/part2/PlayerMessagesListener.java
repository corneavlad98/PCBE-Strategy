package part2;

import game.MyConstants;
import jms.Producer;
import jms.Publisher;

import javax.jms.*;

public class PlayerMessagesListener implements MessageListener {
    private int player1Messages = 0;
    private int player2Messages = 0;
    public Publisher notifyPlayerPublisher = new Publisher();

    @Override
    public void onMessage(Message message) {
        if(message instanceof TextMessage)
        {
            try {
                String textMessage = ((TextMessage) message).getText();
                if(textMessage.equals("Player 1 Resources for house ready"))
                {
                    player1Messages++;
                    notifyPlayerPublisher.sendMessage("Ok, player 1 build house!", "player1Notify");
                }
                else if(textMessage.equals("Player 2 Resources for house ready"))
                {
                    player2Messages++;
                    notifyPlayerPublisher.sendMessage("Ok, player 2 build house!", "player2Notify");
                }
                else if(textMessage.equals("Player 1 won!"))
                {
                    System.out.println(textMessage);
                    player1Messages++;
                    MyConstants2.aPlayerWon = true;
                    MyConstants2.PlayerOneWon = true;
                }
                else if(textMessage.equals("Player 2 won!"))
                {
                    System.out.println(textMessage);
                    player2Messages++;
                    MyConstants2.aPlayerWon = true;
                    MyConstants2.PlayerTwoWon = true;
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    public int getPlayer1Messages() {
        return player1Messages;
    }

    public int getPlayer2Messages() {
        return player2Messages;
    }
}
