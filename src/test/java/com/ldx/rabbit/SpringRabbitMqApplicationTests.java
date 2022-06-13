package com.ldx.rabbit;

import com.ldx.rabbit.properties.RabbitProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringRabbitMqApplicationTests {
@Autowired
private  RabbitProperties rabbitProperties;
    @Test
    void contextLoads() {
    }
    @Test
    void getRabbitMQProperties() {
//        System.out.println(rabbitProperties.getHost());
    }

}
