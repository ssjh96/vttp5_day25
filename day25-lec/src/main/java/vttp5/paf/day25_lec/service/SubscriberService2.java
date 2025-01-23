package vttp5.paf.day25_lec.service;

import org.springframework.stereotype.Service;

@Service
// This one uses listenerAdapter
public class SubscriberService2 {
    
    public void handleMessage(String message)
    {
        System.out.println(">>> SUBCRIBER SERVICE 2");
        System.out.printf(">>> Message rcv: %s\n", message);
    }
}
