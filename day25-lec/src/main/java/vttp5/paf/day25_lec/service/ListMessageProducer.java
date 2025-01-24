package vttp5.paf.day25_lec.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ListMessageProducer {

    @Autowired
    @Qualifier("myredis")
    RedisTemplate<String, String> template;

    public void sendPollingMessage(String message)
    {
        // push the msg into redist list
        template.opsForList().leftPush("orders", message);
        System.out.printf("Message sent to queue named '%s' : '%s' \n", "orders", message);
    }
    
}
