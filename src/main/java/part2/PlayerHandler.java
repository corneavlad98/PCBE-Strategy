package part2;

import jms.Consumer;
import jms.Producer;

import javax.jms.JMSException;
import java.util.Random;

import static part2.AppConstants.*;

public class PlayerHandler {

    Player player1, player2;
    Producer producer = new Producer();

    public PlayerHandler(Player player1, Player player2) throws JMSException {
        this.player1 = player1;
        this.player2 = player2;
        producer.create("PlayersProducer", PLAYER_TO_MAIN_QUEUE);
    }

    public void manageResource(MyResource resource) {
        try {
            System.out.println("Received resource " + resource);
            Random random = new Random();
            int selector = random.nextInt(2);
            if(selector == 0) {
                player1.addResource(resource);
                System.out.println(1 + " " + player1);
                if(player1.hasEnoughResourcesForHouse()) {
                    player1.buildHouse();
                }
            }
            else {
                player2.addResource(resource);
                System.out.println(2 + " " + player2);
                if(player2.hasEnoughResourcesForHouse()){
                    player2.buildHouse();
                }
            }
            if(player1.hasEnoughHouses()) {
                notifyMain(1);
            }

            if(player2.hasEnoughHouses()) {
                notifyMain(2);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private void notifyMain(int playerIndex) throws JMSException {
        producer.sendMessage("Player " + playerIndex + " has won");
    }

    public void closeConnections() throws JMSException {
        producer.close();
    }

    @Override
    public String toString() {
        return "Players[" +
                "Player1 = " + player1 +
                ", Player2 = " + player2 +
                ']';
    }
}
