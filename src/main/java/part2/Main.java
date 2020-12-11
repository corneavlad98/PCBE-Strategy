package part2;

import javax.jms.JMSException;

public class Main {
    private static Player player1 = new Player();
    private static Player player2 = new Player();

    public static void main(String[] args) throws JMSException, InterruptedException {
        //-----RESOURCE GENERATION------//

        player1.subscriber.create("player1", MyConstants2.RESOURCE_TOPIC, "destination = 'player1'");
        player2.subscriber.create("player2", MyConstants2.RESOURCE_TOPIC, "destination = 'player2'");

        ResourceListener resourceListenerPlayer1 = new ResourceListener();
        ResourceListener resourceListenerPlayer2 = new ResourceListener();

        player1.subscriber.messageConsumer.setMessageListener(resourceListenerPlayer1);
        player2.subscriber.messageConsumer.setMessageListener(resourceListenerPlayer2);

        //every generator sends random resource values to both players in the same Topic
        ResourceGenerator woodResource = new ResourceGenerator("woodResource", MyConstants2.RESOURCE_TOPIC);
        ResourceGenerator stoneResource = new ResourceGenerator("stoneResource", MyConstants2.RESOURCE_TOPIC);
        ResourceGenerator goldResource = new ResourceGenerator("goldResource", MyConstants2.RESOURCE_TOPIC);

        Thread thread1 = new Thread(woodResource);
        Thread thread2 = new Thread(stoneResource);
        Thread thread3 = new Thread(goldResource);

        //starting all resource threads (start generating resource values)
        thread1.start();
        thread2.start();
        thread3.start();

        //wait for threads to finish
        thread1.join();
        thread2.join();
        thread3.join();

        //close publisher connections
        woodResource.publisher.closeConnection();
        stoneResource.publisher.closeConnection();
        goldResource.publisher.closeConnection();

        //close subscriber connections
        player1.subscriber.closeConnection();
        player2.subscriber.closeConnection();

        //set resource values in Player resource fields
        player1.setResources(resourceListenerPlayer1.getWoodValue(), resourceListenerPlayer1.getStoneValue(), resourceListenerPlayer1.getGoldValue());
        player2.setResources(resourceListenerPlayer2.getWoodValue(), resourceListenerPlayer2.getStoneValue(), resourceListenerPlayer2.getGoldValue());

        //check if fields where set correctly
        System.out.println("["+ player1.getWoodResource()+ "," + player1.getStoneResource() + "," + player1.getGoldResource() + "]");
        System.out.println("["+ player2.getWoodResource()+ "," + player2.getStoneResource() + "," + player2.getGoldResource() + "]");

    }
}
