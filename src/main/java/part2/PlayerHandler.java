package part2;

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
            System.out.println("Player handler received: " + resource);
            //assign the resource to one of the 2 players (assigning is random)
            Random random = new Random();
            int selector = random.nextInt(2);
            if(selector == 0) {
                player1.addResource(resource);
                System.out.println("Player1 has " + player1);
                if(player1.hasEnoughResourcesForHouse()) {
                    player1.buildHouse();
                }
            }
            else {
                player2.addResource(resource);
                System.out.println("Player 2 has "+ player2);
                if(player2.hasEnoughResourcesForHouse()){
                    player2.buildHouse();
                }
            }
            if(player1.hasEnoughHouses()) {
                notifyMainAPlayerWon(1);
            }

            if(player2.hasEnoughHouses()) {
                notifyMainAPlayerWon(2);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private void notifyMainAPlayerWon(int playerIndex) throws JMSException {
        producer.sendMessage("Player " + playerIndex + " has won");
    }
    private void notifyMainBuildingHouse(int playerIndex) throws JMSException {
        producer.sendMessage("Player " + playerIndex + " building house...");
    }
    private void notifyMainHouseBuilt(int playerIndex) throws JMSException {
        producer.sendMessage("Player " + playerIndex + " has built a house!");
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
