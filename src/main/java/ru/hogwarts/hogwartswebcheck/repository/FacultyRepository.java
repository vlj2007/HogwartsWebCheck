package ru.hogwarts.hogwartswebcheck.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.hogwartswebcheck.model.Faculty;

import java.util.Collection;
import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    List<Faculty> findFacultyByName(String name);

    List<Faculty> findFacultyByColor(String color);

    Collection<Faculty> findFacultyByColorIgnoreCase(String color);

    Collection<Faculty> findFacultyByNameIgnoreCase(String name);

}
