package ru.hogwarts.hogwartswebcheck.api;

import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.hogwartswebcheck.model.Avatar;

import java.io.IOException;

public interface IAvatar {
    void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException;

    Avatar findAvatar(Long studentId);
}
