package vttp5.paf.day25_lec.service;

import java.time.Duration;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

public class ThreadWorker implements Runnable{

    @Autowired
    @Qualifier("myredis")
    RedisTemplate<String, String> template; // Redis Template for Redis ops

    private String name; // worker1 is a name, worker2 is a name, to know which thread is runnning, name for thread used for publishing messages
    

    // Constructor to initialise RedisTemplate and thread name
    public ThreadWorker() {
    }

    

    public ThreadWorker(RedisTemplate<String, String> template, String name) {
        this.template = template;
        this.name = name;
    }

    @Override
    public void run() {
        // Poll the redis List ("myqueue") for messages
        // D25 - slide 10, check if inside got thing or not, if have then get and process
        ListOperations<String, String> listOps = template.opsForList();

        while(true)
        {
            try
            {
                Optional<String> option = Optional.ofNullable(listOps.rightPop("myqueue", Duration.ofSeconds(30)));

                if(option.isEmpty())
                {
                    continue;
                }

                String payload = option.get();
                System.out.println(payload);
            }
            catch (Exception ex)
            {
                System.out.println(ex.getMessage());
            }
        }
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'run'");
    }
    
}
