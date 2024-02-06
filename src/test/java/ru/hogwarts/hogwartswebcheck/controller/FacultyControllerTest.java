package ru.hogwarts.hogwartswebcheck.controller;

import ru.hogwarts.hogwartswebcheck.model.Faculty;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collection;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FacultyControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestRestTemplate restTemplate;
    Long id = 66L;
    String name = "newFaculty";
    String color = "pin";
    Faculty faculty = new Faculty(id, name, color);

    @Autowired
    private FacultyController facultyController;

    @Test
    public void contextLoads() throws Exception {
        Assertions.assertThat(facultyController).isNotNull();
    }

    @Test
    public void testGetFacultyId() throws Exception {
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty/1", String.class))
                .contains("1");
    }

    @Test
    public void testPutFaculty() {
        Long id = this.restTemplate.postForObject("http://localhost:" + port + "/faculty", faculty, Faculty.class).getId();
        Faculty faculty = new Faculty(id, name, color);

        restTemplate.put("http://localhost:" + port + "/faculty", faculty);
        Assertions
                .assertThat((this.restTemplate.getForObject("http://localhost:" + port +
                                "/student/" + id,
                        String.class))).toString().contains("newfaculty");

    }

    @Test
    public void testDeletedFacultyById() throws Exception {
        Long id = this.restTemplate.postForObject("http://localhost:" + port + "/faculty",
                faculty, Faculty.class).getId();
        restTemplate.delete("http://localhost:" + port
                + "/faculty/" + id);
        Assertions
                .assertThat((this.restTemplate.getForObject("http://localhost:" + port +
                                "/faculty/" + id,
                        String.class)))
                .toString().contains("500");
    }

//    @Test
//    public void testGetFacultyByNameOrColor() throws Exception{
//        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty", Collection.class)).isNotNull();
//        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty?color=1", String.class)).isNotNull();
//        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty?name=1", String.class)).isNotNull();
//    }

}