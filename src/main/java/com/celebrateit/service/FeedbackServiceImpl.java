package com.celebrateit.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.celebrateit.dao.FeedbackDao;
import com.celebrateit.dao.UserDao;
import com.celebrateit.dto.FeedbackRequestDTO;
import com.celebrateit.dto.FeedbackResponseDTO;
import com.celebrateit.pojo.Feedback;
import com.celebrateit.pojo.Users;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackDao feedbackDao;

    @Autowired
    private UserDao usersDao;

    @Autowired
    private ModelMapper mapper;

    @Override
    public FeedbackResponseDTO addFeedback(FeedbackRequestDTO feedbackRequestDTO) {
        Users user = usersDao.findById(feedbackRequestDTO.getUserid())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + feedbackRequestDTO.getUserid()));

        Feedback feedback = mapper.map(feedbackRequestDTO, Feedback.class);
        feedback.setUser(user);

        Feedback savedFeedback = feedbackDao.save(feedback);
        FeedbackResponseDTO responseDTO = mapper.map(savedFeedback, FeedbackResponseDTO.class);
        responseDTO.setUserid(user.getUserid()); // Ensuring User ID is set in response

        return responseDTO;
    }

    @Override
    public FeedbackResponseDTO getFeedbackById(Integer feedbackId) {
        Feedback feedback = feedbackDao.findById(feedbackId)
                .orElseThrow(() -> new RuntimeException("Feedback not found with ID: " + feedbackId));

        FeedbackResponseDTO responseDTO = mapper.map(feedback, FeedbackResponseDTO.class);
        responseDTO.setUserid(feedback.getUser().getUserid()); // Ensuring User ID is set in response

        return responseDTO;
    }

    @Override
    public List<FeedbackResponseDTO> getAllFeedbacks() {
        List<Feedback> feedbackList = feedbackDao.findAll();
        return feedbackList.stream()
                .map(feedback -> {
                    FeedbackResponseDTO responseDTO = mapper.map(feedback, FeedbackResponseDTO.class);
                    responseDTO.setUserid(feedback.getUser().getUserid()); // Setting User ID properly
                    return responseDTO;
                })
                .collect(Collectors.toList());
    }
}
