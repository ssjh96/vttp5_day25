package vttp5.paf.day25_lec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import vttp5.paf.day25_lec.service.ListConsumerService;
import vttp5.paf.day25_lec.service.ListMessagePoller;
import vttp5.paf.day25_lec.service.ListMessageProducer;
import vttp5.paf.day25_lec.service.ListProducerService;
import vttp5.paf.day25_lec.service.PublisherService;

@SpringBootApplication
@EnableAsync // This is to enable async processing so @Async is honoured
public class Day25LecApplication implements CommandLineRunner{

	// Pub Sub
	@Autowired
	PublisherService publisherService;

	// List
	@Autowired
	ListProducerService listProducerService;

	@Autowired
	ListConsumerService listConsumerService;

	// List Message Polling w Threads
	@Autowired
	ListMessagePoller listMessagePoller;

	@Autowired
	ListMessageProducer listMessageProducer;

	public static void main(String[] args) {
		SpringApplication.run(Day25LecApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Testing pub sub
		publisherService.publishMessage("hello motor");



		// Testing List without poller
		String queueName = "abc";

			// Producer send msg (use lrange 0 -1 to see the whole queue)
		listProducerService.sendMessage(queueName, "1 List");

		listProducerService.sendMessage(queueName, "2 List");

		listProducerService.sendMessage(queueName, "3 List");

			// Consumer retrieves msg
		String message = listConsumerService.getMessage(queueName);

		if (message != null)
		{
			System.out.println("Processing msg: " + message);
		}


		// Testing with poller
		listMessagePoller.startPolling(); // Start the message poller
		listMessageProducer.sendPollingMessage("This is for polling");
		listMessageProducer.sendPollingMessage("This is also for polling");
	}

}
