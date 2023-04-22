package ru.netology.contributos;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.Testcontainers;
import org.testcontainers.containers.GenericContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DemoApplicationTests {
    public static GenericContainer<?> myDevApp = new GenericContainer<>("devapp").withExposedPorts(8080);
    public static GenericContainer<?> myProdApp = new GenericContainer<>("prodapp").withExposedPorts(8081);
    @Autowired
    TestRestTemplate restTemplate;
@BeforeAll
    public static void setUp() {
        myDevApp.start();
        myProdApp.start();
    }
    @Test
    void profileDev() {
        ResponseEntity<String> devForEntity = restTemplate.getForEntity("http://localhost:" + myDevApp.getMappedPort(8080) + "/profile", String.class);
        Assertions.assertEquals("Current profile is dev", devForEntity.getBody());
    }

    @Test
    void profileProd() {
        ResponseEntity<String> prodForEntity = restTemplate.getForEntity("http://localhost:" + myProdApp.getMappedPort(8081) + "/profile", String.class);
        Assertions.assertEquals("Current profile is production", prodForEntity.getBody());
    }
}
