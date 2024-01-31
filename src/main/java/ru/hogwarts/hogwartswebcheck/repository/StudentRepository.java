package ru.hogwarts.hogwartswebcheck.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hogwarts.hogwartswebcheck.model.Student;

import java.util.Collection;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findStudentByAge(int age);

    List<Student> findStudentByName(String name);

    Collection<Student> findByAgeBetween(int min, int max);

    Collection<Student> findStudentByNameIgnoreCase(String name);

    Collection<Student> findStudentByNameIgnoreCaseIsLike(String like);

}
