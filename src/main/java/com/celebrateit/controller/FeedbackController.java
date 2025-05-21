package com.celebrateit.controller;

import com.celebrateit.dto.FeedbackRequestDTO;
import com.celebrateit.dto.FeedbackResponseDTO;
import com.celebrateit.service.FeedbackService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/add")
    public ResponseEntity<FeedbackResponseDTO> addFeedback(@RequestBody FeedbackRequestDTO feedbackRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(feedbackService.addFeedback(feedbackRequestDTO));
    }

    @GetMapping("/{feedbackId}")
    public ResponseEntity<FeedbackResponseDTO> getFeedbackById(@PathVariable Integer feedbackId) {
        return ResponseEntity.ok(feedbackService.getFeedbackById(feedbackId));
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<FeedbackResponseDTO>> getAllFeedbacks() {
        return ResponseEntity.ok(feedbackService.getAllFeedbacks());
    }
}
