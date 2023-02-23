package br.com.ndc.ecommercedemoapi;

import br.com.ndc.ecommercedemoapi.model.Product;
import br.com.ndc.ecommercedemoapi.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EcommerceDemoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceDemoApiApplication.class, args);
	}


//	@Bean
//	CommandLineRunner runner(ProductService productService) {
//		return args -> {
//			productService.save(new Product(null, "Camiseta Branca M", 4500L, "https://source.unsplash.com/WWesmHEgXDs/w=600"));
//			productService.save(new Product(null, "Camisa Azul P", 9000L, "https://source.unsplash.com/RqYTuWkTdEs/w=600"));
//		};
//	}
}
