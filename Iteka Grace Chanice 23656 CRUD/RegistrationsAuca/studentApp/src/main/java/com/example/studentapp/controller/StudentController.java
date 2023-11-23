package com.example.studentapp.controller;

import com.example.studentapp.domain.Student;
import com.example.studentapp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.rmi.RemoteException;
import java.util.List;

@RestController
@RequestMapping(value = "/students/",consumes = {MediaType.APPLICATION_JSON_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE})
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping(value = "/student")
    public ResponseEntity<?> saveStudent(@RequestBody Student student) throws RemoteException {
        if (student != null) {
            Student savedStudent = studentService.insertStudent(student);
            if (savedStudent != null) {
                return new ResponseEntity<>("student Saved", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("failed", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("student is null", HttpStatus.BAD_REQUEST);

        }
    }
    @GetMapping(value = "/student")
    public ResponseEntity<?>allStudent() throws RemoteException {
        List<Student> students = studentService.selectAllStudents();
        if (students != null) {
            return new ResponseEntity<>(students, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("student is null", HttpStatus.BAD_REQUEST);

        }
    }
    @GetMapping(value = "/student/{id}")
    public ResponseEntity<?>selectStudent(@PathVariable ("id") Integer id) throws RemoteException{
        Student student=studentService.selectStudent(id);
        if(student!=null) {
            return new ResponseEntity<>(student, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("student is null", HttpStatus.BAD_REQUEST);

        }
    }
    @PutMapping("/student/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable("id") Integer id, @RequestBody Student updatedStudent) {
        try {
            Student existingStudent = studentService.selectStudent(id);

            if (existingStudent != null) {
                // Update the properties of the existing student with the new values
                existingStudent.setRegNo(updatedStudent.getRegNo());
                existingStudent.setFirstName(updatedStudent.getFirstName());
                existingStudent.setLastName(updatedStudent.getLastName());
                existingStudent.setEmail(updatedStudent.getEmail());
                existingStudent.setPhone(updatedStudent.getPhone());
                existingStudent.setDob(updatedStudent.getDob());
                existingStudent.setPassword(updatedStudent.getPassword());

                Student updatedStudentEntity = studentService.insertStudent(existingStudent);

                if (updatedStudentEntity != null) {
                    return new ResponseEntity<>("Student Updated", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Update failed", HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>("Student not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/student/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable("id") Integer id) {
        try {
            studentService.deleteStudent(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
