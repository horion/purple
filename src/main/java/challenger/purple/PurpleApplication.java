package challenger.purple;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.Executors;

@EnableAsync
@EnableScheduling
@SpringBootApplication(exclude = {WebMvcAutoConfiguration.class,})
public class PurpleApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(PurpleApplication.class, args);
    }

    @Bean(name = "applicationEventMulticaster")
    public ApplicationEventMulticaster simpleApplicationEventMulticaster() {

        SimpleApplicationEventMulticaster eventMulticaster = new SimpleApplicationEventMulticaster();

        eventMulticaster.setTaskExecutor(Executors.newFixedThreadPool(1));

        return eventMulticaster;
    }


    @Override
    public void run(String... args) {
    }


}
