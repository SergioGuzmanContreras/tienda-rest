package mx.com.openwebinars.tienda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class TiendaRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TiendaRestApplication.class, args);
	}

}
