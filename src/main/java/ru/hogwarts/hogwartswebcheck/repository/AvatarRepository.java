package ru.hogwarts.hogwartswebcheck.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.hogwartswebcheck.model.Avatar;

import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {

    Optional<Avatar> findByStudentId(Long studentId);
}
