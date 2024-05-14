package com.selecao.leilao.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class Config {

    public static String salt;

    @Autowired
    public Config(Environment env) {
        salt = env.getProperty("salt");
    }

}
