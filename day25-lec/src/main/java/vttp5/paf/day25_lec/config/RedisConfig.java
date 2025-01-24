package vttp5.paf.day25_lec.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import vttp5.paf.day25_lec.service.SubscriberService;
import vttp5.paf.day25_lec.service.SubscriberService2;

@Configuration
public class RedisConfig 
{
    // Load Redis properties from application.properties
    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private int redisPort;

    @Value("${spring.data.redis.username}")
    private String redisUserName;

    @Value("${spring.data.redis.password}")
    private String redisPassword;

    // Inject subscriberservice to process receive message  -   part of Publisher/Subscriber
    // Publisher is typically client, subscriber is typically server
    @Autowired
    private SubscriberService subscriberService;
    
    @Autowired SubscriberService2 subscriberService2;

    // CLIENT/PUBLISHER
    // client sends/publishes messages to a channel or topic, it doesnt know who or how many subscribers are listening.
    // In this example - there should be a publisher service in the code that uses RedisTemplate to publish msg to a redis topic (e.g. "mytopic")
    // Calls RedisTemplate.convertAndSend() to send msg

    // SERVER/SUBSCRIBER
    // listens to a specific channel or topic, whenever a msg is published to the subscribe ch, the subscriber rcvs and procceses it.
    // In this example - there should be a subscriber service that is listening for msg on "mytopic"
    // Implements MessageListener(e.g. SubscriberService) to handle incoming msg.
    


    // SET UP CONNECTION TO MY REDIS SERVER 
    // Configure RedisConnectionFactory for connecting to redis server
    // Uses JedisConnectionFactory to communicate w redis (Jedis is one of the popular Redis Client in java)
    public RedisConnectionFactory createConnectionFactory()
    {
        // configure standalone Redis
        final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisHost, redisPort);
        config.setDatabase(0); // uses default DB

        // Set username and password if provided
        if(!redisUserName.equals("") && !redisPassword.equals(""))
        {
            config.setUsername(redisUserName);
            config.setPassword(redisPassword);
        }

        // Create redis connection factory using Jedis
        final JedisClientConfiguration jedisClient = JedisClientConfiguration.builder().build();
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(config, jedisClient);
        jedisConnectionFactory.afterPropertiesSet();

        return jedisConnectionFactory;
    }



    // Configure/Create a RedisTemplate bean to interact with redis for publishing, subscribing, and general operation like storing/retrieving data
    // StringRedisSerializer ensure both key and values stored as plain string in Redis
    // RedisTemplate acts as publisher for PubSub system, 
    // template.convertAndSend() used to publish data to a topic, 
    // RedisTemplate is injected into service for publishing msgs
    @Bean("myredis")
    public RedisTemplate<String, String> redisTemplate()
    {
        // Use connection factory to create the RedisTemplate
        RedisConnectionFactory redisConnectionFactory = createConnectionFactory();
        
        // Set the connection factory
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // Use String serialisers for key and values (makes debugging easier)
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());

        return redisTemplate; // return configured template
    }


    // Configure RedisMessageListenerContainer which listens for messages published to specific topics
    // This sets up a container to listen for messages
    // Assocaiates a message listener (subscriberService) with a topic "mytopic"
    @Bean // this listener is your subscriber service class?


    public RedisMessageListenerContainer createMessageListenerContainer()
    {
        // User connection factory for the container
        RedisConnectionFactory redisConnectionFactory = createConnectionFactory();
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();

        container.setConnectionFactory(redisConnectionFactory);

        // without listenerAdpater
        container.addMessageListener(subscriberService, ChannelTopic.of("mytopic"));

        // without listeneradapter and with pattern
        // container.addMessageListener(subscriberService, new PatternTopic("sales-*"));

        // With listenerAdapter
        // add a msg listener for the topic "mytopic"
        // container.addMessageListener(listenerAdapter(subscriberService2), ChannelTopic.of("mytopic"));

        return container;
    }

    // Adapter to connect a subscriber svc to the msg-ing system
    // Converts incoming redis messages into a format that SubscriberService can process
    // Internally, it invokes a method (like onMessage) in the subscriberservice
    // This is same as "msg-ing w pubsub-1" in the slides, where saleseventsvc implements msglistener to process msgs
    // listeneradapter automates the process?
    @Bean 
    public MessageListenerAdapter listenerAdapter (SubscriberService2 redisConsumerService)
    {
        // Link the adapter to the subscriber serivce
        MessageListenerAdapter adapter = new MessageListenerAdapter(redisConsumerService, "handleMessage");

        // the default serialiser is
        // Uncomment if you need JSON deserialization for messages
        // adapter.setSerializer(new Jackson2JsonRedisSerializer<>(String.class));

        return adapter;
    }

    @Bean("mytopic")
    public ChannelTopic someTopic()
    {
        return new ChannelTopic("mytopic");
    }
}
