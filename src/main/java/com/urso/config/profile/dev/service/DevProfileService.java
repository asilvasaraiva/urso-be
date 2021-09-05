package com.urso.config.profile.dev.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DevProfileService {

    public void teste(){
        log.info("|----- Utilizando Profile de Teste -----|");
    }
}
