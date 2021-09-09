package br.com.urso.config.profile.dev;

import br.com.urso.config.profile.dev.service.DevProfileService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DevProfile {


    private final DevProfileService devProfileService;


    public DevProfile(DevProfileService devProfileService) {
        this.devProfileService = devProfileService;
    }

    @Bean
    public boolean instantiateH2Database(){
        devProfileService.InstantiateDataBase();
        return true;
    }
}
