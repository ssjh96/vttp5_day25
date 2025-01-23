package vttp5.paf.day25_lec.service;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ListConsumerService {
    
    @Autowired
    @Qualifier("myredis")
    RedisTemplate<String, String> template;

    public String getMessage(String queueName)
    {
        // Retrieve and remove the message from the Redis list (blocking for 5 seconds)
        String message = template.opsForList().rightPop(queueName, Duration.ofSeconds(5));

        if (message == null)
        {
            System.out.printf("No message in queue '%s'.\n", queueName);
        }

        System.out.printf("Message from queue named '%s' : '%s' \n", queueName, message);

        return message;
    }

}
