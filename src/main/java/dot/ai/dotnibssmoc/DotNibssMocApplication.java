package dot.ai.dotnibssmoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DotNibssMocApplication {

    public static void main(String[] args) {
        SpringApplication.run(DotNibssMocApplication.class, args);
    }

}

