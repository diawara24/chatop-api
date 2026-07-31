package com.openclassroom.chatopapi.services;


import com.openclassroom.chatopapi.exception.domaines.NotAnImageFileException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static com.openclassroom.chatopapi.constantes.FileConstant.*;
import static org.springframework.util.MimeTypeUtils.*;

@Service
@RequiredArgsConstructor
public class FileService {

    @Value("${upload.dir}")
    private String uploadDir;

    public String savePicture(String rentalName, MultipartFile picture) throws IOException, NotAnImageFileException {
        // tester si l'image est au bon format
        if (!Arrays.asList(IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE, IMAGE_GIF_VALUE).contains(picture.getContentType())){
            throw new NotAnImageFileException(
                    String.format(NOT_AN_IMAGE_FILE, picture.getOriginalFilename())
            );
        }

        String fileName =  picture.getOriginalFilename();
        String relativePath = rentalName + FORWARD_SLASH + fileName;
        Path fullPath = Paths.get(uploadDir, relativePath);

        try {
            Files.createDirectories(fullPath.getParent());
            picture.transferTo(fullPath.toFile());
        } catch (IOException e) {
            throw new IOException(ERROR_PROCESSING_FILE);
        }
        return FILES_PATH + relativePath;
    }
}
