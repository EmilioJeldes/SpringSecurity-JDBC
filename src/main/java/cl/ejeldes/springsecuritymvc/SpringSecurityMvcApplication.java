package cl.ejeldes.springsecuritymvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SpringSecurityMvcApplication implements CommandLineRunner {

    final private BCryptPasswordEncoder encoder;

    @Autowired
    public SpringSecurityMvcApplication(BCryptPasswordEncoder encoder) {this.encoder = encoder;}

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityMvcApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        String password = "12345";

        for (int i = 0; i < 2; i++) {
            String passEncoded = encoder.encode(password);
            System.out.println(passEncoded);
        }

    }
}
