package auca.com.demo.controller;

import auca.com.demo.domain.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import auca.com.demo.service.TeacherService;
import java.rmi.RemoteException;
import java.util.List;

@RestController
@RequestMapping(value = "/teachers/",consumes = {MediaType.APPLICATION_JSON_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE})
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @PostMapping(value = "/teacher")
    public ResponseEntity<?> saveTeacher(@RequestBody Teacher teacher) throws RemoteException {
        if (teacher != null) {
            Teacher savedTeach=teacherService.insertTeacher(teacher);
            if (savedTeach != null) {
                return new ResponseEntity<>("teacher Saved", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("failed", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("teacher is null", HttpStatus.BAD_REQUEST);

        }
    }
    @GetMapping(value = "/teacher")
    public ResponseEntity<List<?>>allTeachers() throws RemoteException {
        List<Teacher> teachers = teacherService.selectAllTeachers();
        return new ResponseEntity<>(teachers, HttpStatus.OK);

    }
    @GetMapping(value = "/teacher/{id}")
    public ResponseEntity<?>selectTeacher(@PathVariable ("id") Integer id) throws RemoteException{
        Teacher teache=teacherService.selectTeacher(id);
        if(teache!=null) {
            return new ResponseEntity<>(teache, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("student is null", HttpStatus.BAD_REQUEST);

        }
    }
    @PutMapping("/teacher/{id}")
    public ResponseEntity<?> updateTeacher(@PathVariable("id") Integer id, @RequestBody Teacher updatedTeacher) {
        try {
            Teacher existingTeacher = teacherService.selectTeacher(id);

            if (existingTeacher != null) {
                // Update the properties of the existing teacher with the new values
                existingTeacher.setCodes(updatedTeacher.getCodes());
                existingTeacher.setNames(updatedTeacher.getNames());
                existingTeacher.setQualification(updatedTeacher.getQualification());
                existingTeacher.setRole(updatedTeacher.getRole());

                // Update relationships (if necessary)
                if (updatedTeacher.getCourses() != null) {
                    existingTeacher.setCourses(updatedTeacher.getCourses());
                }

                Teacher updatedTeacherEntity = teacherService.insertTeacher(existingTeacher);

                if (updatedTeacherEntity != null) {
                    return new ResponseEntity<>("Teacher Updated", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Update failed", HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>("Teacher not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/teacher/{id}")
    public ResponseEntity<?> deleteTeacher(@PathVariable("id") Integer id) {
        try {
            teacherService.deleteTeacher(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
