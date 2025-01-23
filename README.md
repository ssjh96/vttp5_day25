# vttp5_day25

order -\-\- -\<- orderdetails

order has 
List<OrderItems> orderdetails



not desirable
order -\-\- -\<- orderdetails

order has 
List<OrderDetails> 

and orderdetails has
Order order



PUB SUB 
Step 1: Subscriber Creation

Key Points:
Implements MessageListener: The subscriber must implement the MessageListener interface to handle incoming messages.
Handles Messages: The onMessage(Message message, byte[] pattern) method processes messages received from Redis.

@Service
public class SubscriberService implements MessageListener {
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String data = new String(message.getBody());
        System.out.printf("Message received: %s\n", data);
        // Add additional processing logic here if needed
    }
}



Step 2: Configuring Redis
The RedisConfig class connects your application to the Redis server and links subscribers to topics.

Key Points:
RedisConnectionFactory:

Establishes a connection to Redis using the credentials and settings provided in application.properties.
Example: RedisStandaloneConfiguration is used for a single-node Redis setup.
RedisTemplate:

Acts as the publisher for sending messages to a Redis topic.
Configured with serializers like StringRedisSerializer to ensure data is stored and retrieved in plain text.
RedisMessageListenerContainer:

Associates subscribers (MessageListener) with specific topics (e.g., mytopic).
Listens for messages published to the configured topic.
MessageListenerAdapter (Optional):

Converts messages into a format the subscriber can handle, such as invoking a specific method in the subscriber class.

@Bean
public RedisMessageListenerContainer createMessageListenerContainer() {
    RedisMessageListenerContainer container = new RedisMessageListenerContainer();
    container.setConnectionFactory(createConnectionFactory());
    container.addMessageListener(subscriberService, ChannelTopic.of("mytopic"));
    return container;
}



Step 3: Publishing Messages
A publisher sends messages to a Redis topic using the RedisTemplate.

Key Points:
RedisTemplate:
The convertAndSend method is used to send a message to a topic.
ChannelTopic:
Represents the topic to which messages are sent.

@Service
public class PublisherService {
    @Autowired
    private RedisTemplate<String, String> template;

    @Autowired
    private ChannelTopic topic;

    public void publishMessage(String message) {
        template.convertAndSend(topic.getTopic(), message);
        System.out.println("Message published: " + message);
    }
}





