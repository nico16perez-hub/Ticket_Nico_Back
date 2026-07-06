package com.slmas.Sl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.slmas.Sl.domain.Images;
import com.slmas.Sl.domain.Pending;
import com.slmas.Sl.dto.request.TicketRequestDto;
import com.slmas.Sl.exceptions.RepositoryException;
import com.slmas.Sl.service.PendingService;
import com.slmas.Sl.utils.ImageCompressor;
import com.slmas.Sl.utils.ValidationUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pending")
public class PendingController {
    PendingService pendingService;

    public PendingController(PendingService pendingService) {
        this.pendingService = pendingService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> CreatePendingHandler(@RequestBody Map<String, String> requestBody) {
        try {
            String notes = requestBody.get("notes");
            pendingService.createPending(notes);
            return ResponseEntity.status(HttpStatus.OK).body("Tarea creada correctamente!");
        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/delete")
    public ResponseEntity<?> DeletePendingHandler(@RequestParam Long id) {
        try {
            String response = pendingService.deletePending(id);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> GetAllPendingHandler() {
        try {
            List<Pending> response = pendingService.getAll();
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
