package vttp5.paf.day25_producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import vttp5.paf.day25_producer.model.Todo;
import vttp5.paf.day25_producer.service.ProducerService;

@SpringBootApplication
public class Day25ProducerApplication {

	@Autowired
	private static ProducerService producerService; // why is producerService static?

	public static void main(String[] args) {
		SpringApplication.run(Day25ProducerApplication.class, args);

		// for (int i = 0; i < 10000; i++)
		// {
		// 	Todo todo = new Todo();
		// 	todo.setId(i);
		// 	todo.setTaskName("Task " + i);
		// 	producerService.sendMessage(todo);
		// }
	}

}
