package com.example.demo.controller;

import com.example.demo.model.AssignCourse;
import com.example.demo.repository.AssignCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")  // Adjust as per your frontend's URL
public class AssignCourseController {

    @Autowired
    private AssignCourseRepository assignCourseRepository;

    @PostMapping("/assign-course")
    public AssignCourse assignCourse(@RequestBody AssignCourse assignCourse) {
        System.out.println("Received payload: " + assignCourse); // Debugging
        return assignCourseRepository.save(assignCourse);
    }

    @GetMapping("/assigned-courses")
    public List<AssignCourse> getAssignedCourses() {
        return assignCourseRepository.findAll();
    }
}
