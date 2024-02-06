package ru.hogwarts.hogwartswebcheck.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.hogwartswebcheck.exception.BadRequestException;
import ru.hogwarts.hogwartswebcheck.model.Faculty;
import ru.hogwarts.hogwartswebcheck.repository.AvatarRepository;
import ru.hogwarts.hogwartswebcheck.repository.FacultyRepository;
import ru.hogwarts.hogwartswebcheck.service.AvatarService;
import ru.hogwarts.hogwartswebcheck.service.FacultyService;

import java.util.Collection;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class FacultyControllerMVCTest {
    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FacultyRepository facultyRepository;

    @MockBean
    private AvatarRepository avatarRepository;

    @SpyBean
    private FacultyService facultyService;

    @SpyBean
    private AvatarService avatarService;

    @InjectMocks
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate restTemplate;

    Long id = 1L;
    String name = "newFaculty";
    String color = "pin";



    @Test
    public void saveFacultyTest() throws Exception{
        JSONObject facultyObject = new JSONObject();
        facultyObject.put(name, color);
        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));
        mockMvc.perform(MockMvcRequestBuilders
                .post("/faculty")
                .content(facultyObject.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
        mockMvc.perform(get("/faculty/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void testGetFacultyId() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty/1",
                        String.class))
                .contains("1");
    }

    @Test
    public void testGetFacultyColor() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty/green",
                        String.class))
                .contains("green");
    }

    @Test
    public void getFacultyInfoTest() throws Exception{
        Assertions
                .assertThat(this.restTemplate.<Collection>getForObject("http://localhost:" + port + "/faculty/find",
                        Collection.class)).isNotNull();
    }

    @Test
    public void givenId_whenGetNotExistingPerson_thenStatus404anExceptionThrown() throws Exception {
        ResultActions resultActions = mockMvc.perform(
                        get("/faculty/11"))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult -> mvcResult.getResolvedException().getClass().equals(BadRequestException.class));
    }

    @Test
    public void editFaculty_whenUpdate_thenStatus200andUpdatedReturns() throws Exception {
        JSONObject facultyObject = new JSONObject();
        facultyObject.put(name, color);
        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

            long id = this.restTemplate.postForObject("http://localhost:" + port + "/faculty", faculty.getId(), Faculty.class).getId();
            Faculty newFaculty = new Faculty(id, name, color);
            restTemplate.put( "http://localhost:" + port + "/faculty",newFaculty);
            Assertions.assertThat((this.restTemplate.getForObject("http://localhost:" + port + "/student/" + id, String.class))).toString().contains(name);
        }


//    @Test
//    public void givenPerson_whenDeletePerson_thenStatus200() throws Exception {
//        JSONObject facultyObject = new JSONObject();
//        facultyObject.put(name, color);
//        Faculty faculty = new Faculty();
//        faculty.setId(id);
//        faculty.setName(name);
//        faculty.setColor(color);
//        ResultActions resultActions = mockMvc.perform(
//                        delete("/faculty/{id}", faculty.getId()))
//                .andExpect(status().isOk())
//                .andExpect(content().json(objectMapper.writeValueAsString(faculty)));
//    }




}
