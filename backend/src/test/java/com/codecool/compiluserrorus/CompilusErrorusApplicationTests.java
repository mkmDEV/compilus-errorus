package com.codecool.compiluserrorus;


import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataJpaTest
@ActiveProfiles("test")
public class CompilusErrorusApplicationTests {

    @Test
    @Order(1)
    public void contextLoads() {
    }

    @Test
    @Order(2)
    public void one() {
        System.out.println("1");
    }

    @Test
    @Order(3)
    public void two() {
        System.out.println("2");
    }

    @Test
    @Order(4)
    public void three() {
        System.out.println("3");
    }

}
