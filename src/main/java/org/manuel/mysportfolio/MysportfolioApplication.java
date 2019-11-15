package org.manuel.mysportfolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class MysportfolioApplication {

	public static void main(String[] args) {
		SpringApplication.run(MysportfolioApplication.class, args);
	}

}
