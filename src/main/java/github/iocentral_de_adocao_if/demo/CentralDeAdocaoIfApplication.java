package github.iocentral_de_adocao_if.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {
		org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
		org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration.class
})

public class CentralDeAdocaoIfApplication {

	public static void main(String[] args) {
		SpringApplication.run(CentralDeAdocaoIfApplication.class, args);
	}

}
