package vttp5.paf.day25_lec.service;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component // mark class as spring-managed component so that it can be autowried where needed
public class ListMessagePoller {
    
    // @Autowired 
    // private RedisTemplate<String, String> template;

    @Autowired
    @Qualifier("myredis")
    RedisTemplate<String, String> template;

    @Async // Allows a method to run in a separtae thread, enabling non-blocking asynchronous execution
    // Ensures that this polling method doesnt block the main application thread, without it, it would run on same thread and potentially slow down or block startup process
    public void startPolling()
    {
        // Create runnable tasks for polling
        // Runnable is used to execute the polling task asynchronusly using an ExecutorService
        Runnable poller = () -> { // Runnable is a blueprint for what a thread should do, it has one method, run(), where the task is defined.
            // Used it to define custom tasks for threads and allows creation of multiple threads to handle diff tasks concurrently.
            ListOperations<String, String> orderList = template.opsForList();

            while(true) // infinite loop to keep polling
            {
                try {

                    // Perform BRPOP op with timeout 5sec
                    Optional<String> opt = Optional.ofNullable(
                        orderList.rightPop("orders", Duration.ofSeconds(5)) // block for 5 seconds if the list is empty, if queue has message, it returns the message, otherwise returns null 
                        // But optional wrap makes it return optional of it, but if null return empty optional
                    );

                    if (opt.isPresent())
                    {
                        String data = opt.get(); // Get the message from the list
                        System.out.printf("New message: %s\n", data); // Process the message

                        // JsonReader reader = Json.createReader(new StringReader(opt.get()));
                        // JsonObject data = reader.readObject();
                    }
                    
                } catch (Exception e) {
                    System.err.println("Error while polling: " + e.getMessage());
                }
            }
        };

        // Execute the Runnable task in a separate thread
        // Create a single-threaded executor to execute the runnable task to ensure that this polling task runs indepedently in its own thread
        Executors.newSingleThreadExecutor().execute(poller);
    }
}


// ADD INFO
// Lamda Expression

// "The poller variable must be of type Runnable."
// "Runnable has one abstract method: run()."
// "The lambda expression corresponds to the run() method of Runnable."

// Runnable poller = () -> {
//     // Task code here
// };

// Equivalent
// Runnable poller = new Runnable() {
//     @Override
//     public void run() {
//         // Task code here
//     }
// };


