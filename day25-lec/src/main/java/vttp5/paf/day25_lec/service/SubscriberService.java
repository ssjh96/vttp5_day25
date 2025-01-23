package vttp5.paf.day25_lec.service;

import java.io.ByteArrayInputStream;
import java.io.StringReader;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import jakarta.json.Json;

@Service
// This one doesnt use ListenerAdapter (Manual way)
public class SubscriberService implements MessageListener
{

    @Override // unimplemented method, message subscriber service > add subscriber service to RedisConfig > autowired private SubscriberService subscriberService, inject into listener as it i a message listener

    // byte[] pattern > subscribe to a specific channel (e.g. sales) or a pattern of channels, (e.g. sales-*) which is all channels that start with sales-
    public void onMessage(Message message, byte[] pattern) 
    {
        // Message object contains serialised data
        byte[] data = message.getBody();
        String dataStr = new String(data);

        // shortcut
        // String data = new String(message.getBody());

        System.out.println(">>> SUBSCRIBER SERVICE 1");
        System.out.printf(">>> Message rcv: %s\n", dataStr);

        // process data if it is JSON 

        // Method 1:
        // String jsonString = new String(message.getBody());
        // JsonObject json = Json.createReader(new StringReader(jsonString)).readObject();

        // Method 2:
        // JsonReader reader = Json.createReader(new ByteArrayInputStream(data));
        // JsonObject jsonData = reader.readObject();
        
        // process

        // More complete
        // Convert byte[] to String
        // String data = new String(message.getBody());
        // System.out.printf("Message received: %s\n", data);

        // // Parse if JSON
        // try (JsonReader reader = Json.createReader(new ByteArrayInputStream(message.getBody()))) {
        //     JsonObject jsonObject = reader.readObject();
        //     System.out.printf("Parsed JSON: %s\n", jsonObject);
        // } catch (Exception e) {
        //     System.err.println("Failed to parse message as JSON: " + e.getMessage());
        // }
    }
    
}
