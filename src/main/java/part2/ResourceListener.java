package part2;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

public class ResourceListener implements MessageListener {
    private int woodValue;
    private int stoneValue;
    private int goldValue;
    @Override
    public void onMessage(Message message) {
        if(message instanceof ObjectMessage)
        {
            try {
                //String destination = message.getStringProperty("destination");
                MyResource resourceMessage =  (MyResource) ((ObjectMessage) message).getObject();

                System.out.println(resourceMessage.destination + " " + resourceMessage.resourceType + " value is: " + resourceMessage.resourceValue);
                setResourceValue(resourceMessage.resourceType, resourceMessage.resourceValue);

            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
    public void setResourceValue(String resourceType, int resourceValue) {
        if(resourceType.equals("woodResource"))
        {
            woodValue += resourceValue;
        }
        if(resourceType.equals("stoneResource"))
        {
            stoneValue += resourceValue;
        }
        if(resourceType.equals("goldResource"))
        {
            goldValue += resourceValue;
        }
    }

    public int getWoodValue() {
        return woodValue;
    }

    public int getStoneValue() {
        return stoneValue;
    }

    public int getGoldValue() {
        return goldValue;
    }
}
