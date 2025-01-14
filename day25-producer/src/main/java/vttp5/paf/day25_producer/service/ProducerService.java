package vttp5.paf.day25_producer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import vttp5.paf.day25_producer.model.Todo;

@Service
public class ProducerService 
{
    @Autowired @Qualifier("todo")
    RedisTemplate<String, Todo> redisTemplate;   
    
    @Value("${redis.topic1}")
    private String topic1;

    public void sendMessage(Todo todo)
    {
        redisTemplate.convertAndSend(topic1, todo);
    }


}
