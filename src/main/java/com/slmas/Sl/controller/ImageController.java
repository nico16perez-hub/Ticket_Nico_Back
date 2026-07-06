package com.slmas.Sl.controller;

import com.slmas.Sl.domain.Images;
import com.slmas.Sl.utils.ImageCompressor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final Path uploadDir = Paths.get("/opt/uploads");

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("image") MultipartFile image) {
        try {
            // Ruta de almacenamiento
            Path uploadDir = Paths.get("/opt/uploads");
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            // Convertir la imagen usando el compresor
            Images compressedImages = ImageCompressor.compressImage(image.getBytes());

            // Guardar la imagen comprimida (full image)
            String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
            Path imagePath = uploadDir.resolve(fileName);
            Files.write(imagePath, compressedImages.getFullImage());  // Guardamos la imagen completa

            // Generar URL para acceder a la imagen y al thumbnail
            String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/images/")
                    .path(fileName)
                    .toUriString();

            // Devolver las URLs en la respuesta
            Map<String, String> response = Map.of(
                    "url", imageUrl
            );
            return ResponseEntity.ok(response);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Error al subir la imagen"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Error al comprimir la imagen"));
        }
    }


    @GetMapping("/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            Path filePath = uploadDir.resolve(filename);
            Resource resource = (Resource) new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null);
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // Ajusta el tipo de contenido según sea necesario
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

}
