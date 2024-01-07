package io.github.the_jasoney.toiletto;

import com.bedatadriven.jackson.datatype.jts.JtsModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ToilettoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToilettoApplication.class, args);
	}

	@Bean
	public JtsModule jtsModule() {
		return new JtsModule();
	}
}
