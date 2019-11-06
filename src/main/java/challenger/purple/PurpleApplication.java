package challenger.purple;

import challenger.purple.agents.StdinExecute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.Executors;

@EnableAsync
@EnableScheduling
@SpringBootApplication(exclude = {WebMvcAutoConfiguration.class,})
public class PurpleApplication implements ApplicationRunner {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(PurpleApplication.class, args);
        SpringApplication.exit(ctx, (ExitCodeGenerator) () -> 0);
    }

    @Bean(name = "applicationEventMulticaster")
    public ApplicationEventMulticaster simpleApplicationEventMulticaster() {

        SimpleApplicationEventMulticaster eventMulticaster = new SimpleApplicationEventMulticaster();

        eventMulticaster.setTaskExecutor(Executors.newFixedThreadPool(20));

        return eventMulticaster;
    }

    @Autowired
    StdinExecute execute;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        execute.startProcess(args.getSourceArgs());
    }
}
