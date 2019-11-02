package challenger.purple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;

@SpringBootApplication(exclude = {WebMvcAutoConfiguration.class,})
public class PurpleApplication {

    public static void main(String[] args) {
        SpringApplication.run(PurpleApplication.class, args);
    }

}
