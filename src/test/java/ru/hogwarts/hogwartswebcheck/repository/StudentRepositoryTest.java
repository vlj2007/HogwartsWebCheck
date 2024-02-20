package ru.hogwarts.hogwartswebcheck.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentRepositoryTest {
    @LocalServerPort
    private int port;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() throws Exception{
        Assertions.assertThat(studentRepository).isNotNull();
    }

    @Test
    void testFacultyIsNotNull() throws Exception{
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty", String.class)).isNotNull();
    }





}