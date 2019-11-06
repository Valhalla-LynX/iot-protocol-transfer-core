package com.valhalla;

import com.valhalla.server.ask.AskServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

/**
 * @author Valhalla
 * @description app
 * @date 2019/10/23
 **/
@SpringBootApplication
public class IoTCoreSpringBootApplication {
    private static IoTCoreSpringBootApplication ioTCoreSpringBootApplication;
    @Autowired
    private AskServer askServer;

    public static void main(String[] args) {
        SpringApplication.run(IoTCoreSpringBootApplication.class, args);
    }

    @PostConstruct
    public void init() {
        ioTCoreSpringBootApplication = this;
        askServer.start();
    }
}
