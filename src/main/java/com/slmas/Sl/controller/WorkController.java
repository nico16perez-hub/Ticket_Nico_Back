package com.slmas.Sl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.slmas.Sl.domain.Images;
import com.slmas.Sl.dto.request.TicketRequestDto;
import com.slmas.Sl.dto.request.WorkRequestDto;
import com.slmas.Sl.dto.response.TicketResponseDto;
import com.slmas.Sl.dto.response.WorkResponseDto;
import com.slmas.Sl.exceptions.RepositoryException;
import com.slmas.Sl.service.TicketService;
import com.slmas.Sl.service.WorkService;
import com.slmas.Sl.utils.ImageCompressor;
import com.slmas.Sl.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/work")
public class WorkController {
    WorkService workService;
    @Autowired
    private SimpMessagingTemplate template;

    public WorkController(WorkService workService) {
        this.workService = workService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> CreateWorkHandler(@RequestParam(value = "file", required = false) MultipartFile file,
                                               @RequestParam("work") String workJson) {
        try {
            WorkRequestDto workRequestDto = new ObjectMapper().readValue(workJson, WorkRequestDto.class);
            ValidationUtil.validate(workRequestDto, WorkRequestDto.Create.class);
            Images images = new Images();
            if (file != null && !file.isEmpty()) {
                images = ImageCompressor.compressImage(file.getBytes());
            }
            Long workId = workService.createWork(workRequestDto, images);
            template.convertAndSend("/topic/notifications", "Nuevo trabajo creado/" + workId);
            return ResponseEntity.status(HttpStatus.OK).body("Trabajo creado correctamente!");
        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/getFilteredWorks")
    public ResponseEntity<?> GetFilteredWorksHandler(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                       @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        try {
            List<WorkResponseDto> works = workService.getFilteredWorks(startDate, endDate);
            return ResponseEntity.status(HttpStatus.OK).body(works);

        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/downloadWorks")
    public ResponseEntity<?> downloadWorksHandler(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        try {
            List<WorkResponseDto> works = workService.downloadWorks(startDate, endDate);
            return ResponseEntity.status(HttpStatus.OK).body(works);

        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/getUserTickets")
    public ResponseEntity<?> GetUsersWorksHandler(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                                                    @RequestParam Long userId) {
        try {
            List<WorkResponseDto> works = workService.getUserWorks(startDate, endDate, userId);
            return ResponseEntity.status(HttpStatus.OK).body(works);
        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/getWork")
    public ResponseEntity<?> GetWorkByIdHandler(@RequestParam Long workId) {
        try {
            WorkResponseDto work = workService.getWorkById(workId);
            return ResponseEntity.status(HttpStatus.OK).body(work);
        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<?> editWorkHandler(@RequestParam("work") String workJson) {
        try {
            WorkRequestDto workRequestDto = new ObjectMapper().readValue(workJson, WorkRequestDto.class);
            String response = workService.editWork(workRequestDto.getDescription(), workRequestDto.getId());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
