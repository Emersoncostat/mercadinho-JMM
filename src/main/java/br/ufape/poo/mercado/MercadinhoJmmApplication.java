package br.ufape.poo.mercado;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("br.ufape.poo.mercado.repository")
public class MercadinhoJmmApplication {

    public static void main(String[] args) {
        SpringApplication.run(MercadinhoJmmApplication.class, args);
    }
}