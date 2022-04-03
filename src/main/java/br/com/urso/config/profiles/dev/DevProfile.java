package br.com.urso.config.profiles.dev;

import br.com.urso.config.profiles.dev.service.DevProfileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Objects;

@Configuration
@Profile("dev")
public class DevProfile {


    private final DevProfileService devProfileService;

    @Value("${spring.datasource.username}")
    private String username;
    public DevProfile(DevProfileService devProfileService) {
        this.devProfileService = devProfileService;
    }

    @Bean
    public boolean instantiateH2Database(){
        if(Objects.equals(username, "dev"))
        devProfileService.InstantiateDataBase();
        return true;
    }
}
