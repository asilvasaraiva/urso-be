package br.com.urso.config.profiles.prod;

import br.com.urso.config.profiles.prod.service.ProdProfileService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.ILoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Objects;

@Slf4j
@Configuration
@Profile("prod")
public class ProdProfile {


    private final ProdProfileService prodProfileService;

    @Value("${spring.datasource.username}")
    private String username;

    public ProdProfile(ProdProfileService prodProfileService) {
        this.prodProfileService = prodProfileService;
    }

    @Bean
    public boolean instantiateH2Database(){
        log.info("USANDO PROFILE DE PRODUÇÃO");
        log.info(username);
        if(Objects.equals(username, "prod"))
        prodProfileService.InstantiateDataBase();
        return true;
    }
}
