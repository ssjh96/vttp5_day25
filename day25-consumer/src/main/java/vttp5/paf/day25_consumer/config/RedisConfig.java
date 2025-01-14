package vttp5.paf.day25_consumer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import vttp5.paf.day25_consumer.model.Todo;
import vttp5.paf.day25_consumer.service.ConsumerService;

@Configuration
public class RedisConfig 
{
    // Consumer need listen contianer and subscribe topic

    @Value("${redis.topic1}")
    private String redisTopic; // subscribe topic

    // Connection Factory is configturing all the settings to connect to redis DB
    @Bean
    RedisTemplate<String, Todo> redisTemplate(RedisConnectionFactory connFac, Jackson2JsonRedisSerializer<Todo> serializer)
    {
        RedisTemplate<String, Todo> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connFac);
        redisTemplate.setDefaultSerializer(serializer);
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

    @Bean 
    public Jackson2JsonRedisSerializer<Todo> jackson2JsonRedisSerializer() 
    {
        return new Jackson2JsonRedisSerializer<>(Todo.class);
    }

    @Bean // You need a container for listener adapter to listen to, container is using a listener to listen for a certain topic
    public RedisMessageListenerContainer listenerContainer(MessageListenerAdapter messageListenerAdapter, RedisConnectionFactory redisConnectionFactory)
    {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(messageListenerAdapter, new PatternTopic(redisTopic));

        return container;
    }

    @Bean // listener adapter is going to listen for Consumer Service, need a jackson serialiser to deserialise
    public MessageListenerAdapter listenerAdapter(ConsumerService redisConsumerService)
    {
        MessageListenerAdapter adapter = new MessageListenerAdapter(redisConsumerService);
        adapter.setSerializer(new Jackson2JsonRedisSerializer<>(Todo.class));

        // user stringserialiser to map manually, jackson2Jsonredisserialiser auto maps

        return adapter;
    }
}
