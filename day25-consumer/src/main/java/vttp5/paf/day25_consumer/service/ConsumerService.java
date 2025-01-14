package vttp5.paf.day25_consumer.service;

import org.springframework.stereotype.Service;

import vttp5.paf.day25_consumer.model.Todo;

@Service
public class ConsumerService 
{
    public void handleMessage(Todo todo)
    {
        // You can perform smth here but we are just printing out whatever we receive for this example
        // Can for example save to DB
        System.out.println(todo.toString());
    }
}
