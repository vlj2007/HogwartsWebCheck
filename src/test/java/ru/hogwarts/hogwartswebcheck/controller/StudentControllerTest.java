package ru.hogwarts.hogwartswebcheck.controller;

import ru.hogwarts.hogwartswebcheck.model.Faculty;
import ru.hogwarts.hogwartswebcheck.model.Student;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.hogwartswebcheck.repository.StudentRepository;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestRestTemplate restTemplate;
    Long id = 66L;
    String name = "newStudent";
    int age = 44;
    Student student = new Student(id, name, age);

    @Autowired
    private StudentController studentController;

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void contextLoads() throws Exception{
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    public void testGetStudents() throws Exception{
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/students", String.class))
                .contains("1");
    }

    @Test
    public void testPutStudent(){
        Long id = this.restTemplate.postForObject("http://localhost:" + port + "/students", student, Student.class).getId();
        Student student = new Student(id, name, age);

        restTemplate.put( "http://localhost:" + port + "/students",student);
        Assertions
                .assertThat((this.restTemplate.getForObject("http://localhost:" + port +
                                "/student/" + id,
                        String.class))).toString().contains("newstudent");
    }

    @Test
    public void testDeletedStudentById() throws Exception {
        Long id = this.restTemplate.postForObject("http://localhost:" + port + "/students",
                student, Student.class).getId();
        restTemplate.delete("http://localhost:" + port
                + "/students/" + id);
        Assertions
                .assertThat((this.restTemplate.getForObject("http://localhost:" + port +
                                "/students/" + id,
                        String.class)))
                .toString().contains("500");
    }

}