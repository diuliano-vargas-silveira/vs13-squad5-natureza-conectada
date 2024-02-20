package br.com.vemser.naturezaconectada.naturezaconectada;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class
NaturezaConectadaApplication {

	public static void main(String[] args) {
		SpringApplication.run(NaturezaConectadaApplication.class, args);

	}

}