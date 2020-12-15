package part2;

import jms.Producer;
import jms.Subscriber;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

public class Player implements Runnable {
    private int woodResource;
    private int stoneResource;
    private int goldResource;
    private int houseCount = 0;

    Subscriber resourceTopicSubscriber = new Subscriber();
    Subscriber playerNotifyTopicSubscriber = new Subscriber();

    Producer toMainQueueProducer = new Producer();

    public Player()
    {
        woodResource = 0;
        stoneResource= 0;
        goldResource = 0;
    }
    public void setResources(MyResource resource) {
        if(resource.resourceType.equals("woodResource"))
            woodResource += resource.resourceValue;
        if(resource.resourceType.equals("stoneResource"))
            stoneResource += resource.resourceValue;
        if(resource.resourceType.equals("goldResource"))
            goldResource += resource.resourceValue;
    }

    public int getWoodResource() {
        return woodResource;
    }

    public int getStoneResource() {
        return stoneResource;
    }

    public int getGoldResource() {
        return goldResource;
    }

    public void setWoodResource(int woodResource) {
        this.woodResource += woodResource;
    }

    public void setStoneResource(int stoneResource) {
        this.stoneResource += stoneResource;
    }

    public void setGoldResource(int goldResource) {
        this.goldResource += goldResource;
    }

    @Override
    public void run() {
        while(houseCount < 3 && !MyConstants2.aPlayerWon) {
            if (woodResource < MyConstants2.WOOD_FOR_HOUSE) {
                try {
                    Message message = resourceTopicSubscriber.messageConsumer.receive();
                    MyResource resourceMessage = (MyResource) ((ObjectMessage) message).getObject();
                    System.out.println(Thread.currentThread().getName() + " got " + resourceMessage.resourceType + " with value: " + resourceMessage.resourceValue);

                    setResources(resourceMessage);
                    Thread.sleep(1000);

                } catch (JMSException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    toMainQueueProducer.sendMessage(Thread.currentThread().getName() + " Resources for house ready");

                    Message message = playerNotifyTopicSubscriber.messageConsumer.receive(4000);
                    if(message != null && !MyConstants2.aPlayerWon)
                    {
                        String textMessage = ((TextMessage) message).getText();
                        System.out.println(Thread.currentThread().getName() + " got " + textMessage);
                        if(textMessage.contains("build house") && !MyConstants2.aPlayerWon)
                        {
                            toMainQueueProducer.sendMessage(Thread.currentThread().getName() + " Building house...");
                            Thread.sleep(3000);
                            if(!MyConstants2.aPlayerWon)
                            {
                                toMainQueueProducer.sendMessage(Thread.currentThread().getName() + " House built");
                                houseCount++;
                                woodResource -= MyConstants2.WOOD_FOR_HOUSE;
                                // stoneResource -= MyConstants2.STONE_FOR_HOUSE;
                                //goldResource -= MyConstants2.GOLD_FOR_HOUSE;
                            }
                        }
                    }
                } catch (JMSException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            //System.out.println(Thread.currentThread().getName() + " won");
            toMainQueueProducer.sendMessage(Thread.currentThread().getName() + " won!");
            resourceTopicSubscriber.closeConnection();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
