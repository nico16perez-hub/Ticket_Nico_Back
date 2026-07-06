package com.slmas.Sl.controller;

import com.slmas.Sl.domain.Guide;
import com.slmas.Sl.exceptions.RepositoryException;
import com.slmas.Sl.service.GuideService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/guides")
public class GuideController {

    GuideService guideService;

    public GuideController(GuideService guideService) {
        this.guideService = guideService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> CreateGuideHandler(@RequestBody Guide guide) {
        try {
            String response = guideService.createGuide(guide);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> UpdateGuideHandler(@RequestBody Guide guide) {
        try {
            String response = guideService.updateGuide(guide);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/getAllGuides")
    public ResponseEntity<?> GetAllGuidesHandler() {
        try {
            List<Guide> guides = guideService.getAllGuides();
            return ResponseEntity.status(HttpStatus.OK).body(guides);
        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/getUserGuides")
    public ResponseEntity<?> GetUserGuidesHandler() {
        try {
            List<Guide> guides = guideService.getUserGuides();
            return ResponseEntity.status(HttpStatus.OK).body(guides);
        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/getById")
    public ResponseEntity<?> GetGuideByIdHandler(@RequestParam Long id) {
        try {
            Guide guide = guideService.getById(id);
            return ResponseEntity.status(HttpStatus.OK).body(guide);
        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> DeleteGuideByIdHandler(@RequestParam Long id) {
        try {
            String response = guideService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
