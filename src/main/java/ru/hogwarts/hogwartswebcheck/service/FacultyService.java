package ru.hogwarts.hogwartswebcheck.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import ru.hogwarts.hogwartswebcheck.api.IFaculty;
import ru.hogwarts.hogwartswebcheck.model.Faculty;
import ru.hogwarts.hogwartswebcheck.model.Student;
import ru.hogwarts.hogwartswebcheck.repository.FacultyRepository;
import ru.hogwarts.hogwartswebcheck.exception.BadRequestException;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class FacultyService implements IFaculty {

    @Autowired
    private final FacultyRepository facultyRepository;


    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty editFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(Long id) {
        return facultyRepository.findById(id).orElseThrow(() -> new BadRequestException("Отсутствует id"));
    }

    public void deleteFacultyById(long id) {
//        Faculty faculty = findFaculty(id);
        Faculty faculty = facultyRepository.findById(id).orElseThrow(() -> new BadRequestException("Отсутствует id"));
        Set<Student> students = faculty.getStudents();
        for (Student student : students) {
            student.setFaculty(null);
        }
        facultyRepository.deleteById(id);
    }

    public void deleteAllFaculty(Faculty faculty) {
        facultyRepository.deleteAll();

    }

    public List<Faculty> showAllFaculty() {
        return facultyRepository.findAll();
    }

    public List<Faculty> findFacultyByName(String name) {
        return facultyRepository.findFacultyByName(name);
    }

    public Collection<Faculty> findFacultyByNameIgnoreCase(String name) {
        return facultyRepository.findFacultyByNameIgnoreCase(name);
    }

    public Collection<Faculty> findFacultyByColorIgnoreCase(String color) {
        return facultyRepository.findFacultyByColorIgnoreCase(color);
    }

    public Collection<Faculty> findFacultyByColor(String color) {
        return facultyRepository.findFacultyByColor(color);
    }

}
