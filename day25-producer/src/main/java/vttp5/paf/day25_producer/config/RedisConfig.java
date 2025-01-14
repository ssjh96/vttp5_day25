package vttp5.paf.day25_producer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import vttp5.paf.day25_producer.model.Todo;

@Configuration
public class RedisConfig 
{
    @Bean("todo") //("topic1") // topic1 is just a name for the bean, later want to use use qualifier? if bean got no name, redisTemplat (yellow) below is the name
    RedisTemplate<String, Todo> redisTemplate(RedisConnectionFactory connFac, Jackson2JsonRedisSerializer<Todo> serializer) // Key is String, value is Todo, jackson serialiser serialise auto?
    {
        RedisTemplate<String, Todo> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connFac);
        redisTemplate.setDefaultSerializer(serializer);
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

    @Bean // why do we need this?
    public Jackson2JsonRedisSerializer<Todo> jackson2JsonRedisSerializer()
    {
        return new Jackson2JsonRedisSerializer<>(Todo.class);
    }
}
