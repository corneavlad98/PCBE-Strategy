package part2;

import javax.jms.Message;
import javax.jms.MessageListener;

public class ResourceListener implements MessageListener{
    ResourceGenerator resourceGenerator;

    public ResourceListener(ResourceGenerator resourceGenerator) {
        this.resourceGenerator = resourceGenerator;
    }

    @Override
    public void onMessage(Message message) {
        this.resourceGenerator.generateResourceAndSendMessage(message);
    }
}
