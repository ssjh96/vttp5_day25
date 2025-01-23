package vttp5.paf.day25_lec.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
public class PublisherService {
    
    @Autowired
    @Qualifier("myredis")
    RedisTemplate<String, String> template;

    @Autowired
    @Qualifier("mytopic")
    private ChannelTopic topic;

    public void publishMessage(String message)
    {
        // publish msg to the channel
        template.convertAndSend(topic.getTopic(), message);
        System.out.println(">>> PUBLISHER");
        System.out.println(">>> Published Message: " + message + " to topic: " + topic.getTopic());
    }
}
