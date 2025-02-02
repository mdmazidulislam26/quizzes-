package com.mazidul.oas.service;

import com.mazidul.oas.dao.QuestionDao;
import com.mazidul.oas.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            List<Question> questions = questionDao.findAll();
            if (questions.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(questions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try {
            List<Question> questions = questionDao.findByCategory(category);
            if (questions.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(questions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> addQuestion(Question question) {
        if (question == null || question.getQuestionTitle() == null || question.getQuestionTitle().isEmpty()) {
            return new ResponseEntity<>("Invalid question data", HttpStatus.BAD_REQUEST);
        }
        questionDao.save(question);
        return new ResponseEntity<>("Question added successfully", HttpStatus.CREATED);
    }

    public ResponseEntity<String> deleteQuestion(Integer id) {
        if (questionDao.findById(id).isPresent()) {
            questionDao.deleteById(id);
            return new ResponseEntity<>("Question deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Question not found", HttpStatus.NOT_FOUND);
        }
    }
}
