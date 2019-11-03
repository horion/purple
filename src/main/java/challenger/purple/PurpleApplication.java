package challenger.purple;

import challenger.purple.controller.PurpleController;
import challenger.purple.model.event.TransactionEvent;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;

import java.util.concurrent.Executors;

@SpringBootApplication(exclude = {WebMvcAutoConfiguration.class,})
public class PurpleApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(PurpleApplication.class, args);
    }

    @Bean(name = "applicationEventMulticaster")
    public ApplicationEventMulticaster simpleApplicationEventMulticaster() {

        SimpleApplicationEventMulticaster eventMulticaster = new SimpleApplicationEventMulticaster();

        eventMulticaster.setTaskExecutor(Executors.newFixedThreadPool(20));

        return eventMulticaster;
    }


    @Bean
    public PurpleController purpleController(){
        return new PurpleController();
    }

    @Override
    public void run(String... args) throws Exception {
        purpleController().startProcess(args);
    }


}
