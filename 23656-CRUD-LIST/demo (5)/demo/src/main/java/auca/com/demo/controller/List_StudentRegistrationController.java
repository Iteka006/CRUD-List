package auca.com.demo.controller;

import auca.com.demo.domain.StudentRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import auca.com.demo.service.StudentRegService;
import java.rmi.RemoteException;
import java.util.List;

@RestController
@RequestMapping(value = "/registrations/",consumes = {MediaType.APPLICATION_JSON_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE})
public class List_StudentRegistrationController {
    @Autowired
    private StudentRegService studentRegService;
    @PostMapping(value = "/register")
    public ResponseEntity<?> saveRegistration(@RequestBody StudentRegistration registration) throws RemoteException {
        if (registration != null) {
            StudentRegistration savedReg=studentRegService.insertStudent(registration);
            if (savedReg != null) {
                return new ResponseEntity<>("Registration Saved", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("failed", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Registration is null", HttpStatus.BAD_REQUEST);

        }
    }
    @GetMapping(value = "/register")
    public ResponseEntity<?>allRegistration() throws RemoteException{
        List<StudentRegistration> regs=studentRegService.selectAllStudents();
        if(regs !=null) {
            return new ResponseEntity<>(regs, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }    }
    @GetMapping(value = "/register/{id}")
    public ResponseEntity<?>selectRegistration(@PathVariable ("id") Integer id) throws RemoteException{
        StudentRegistration reg=studentRegService.selectStudent(id);
        if(reg!=null) {
            return new ResponseEntity<>(reg, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("student is null", HttpStatus.BAD_REQUEST);

        }
    }

    @PutMapping("/register/{id}")
    public ResponseEntity<?> updateRegistration(@PathVariable("id") Integer id, @RequestBody StudentRegistration updatedRegistration) {
        try {
            StudentRegistration existingRegistration = studentRegService.selectStudent(id);

            if (existingRegistration != null) {
                // Update the properties of the existing registration with the new values
                existingRegistration.setRegDate(updatedRegistration.getRegDate());
                existingRegistration.setStudent(updatedRegistration.getStudent());
                existingRegistration.setSem(updatedRegistration.getSem());
                existingRegistration.setUnit(updatedRegistration.getUnit());
                existingRegistration.setCourses(updatedRegistration.getCourses());

                StudentRegistration updatedRegistrationEntity = studentRegService.insertStudent(existingRegistration);

                if (updatedRegistrationEntity != null) {
                    return new ResponseEntity<>("Registration Updated", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Update failed", HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>("Registration not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping(value="/register/{id}")
    public ResponseEntity<?> deleteRegistration(@PathVariable("id") Integer id) {
        try {
            studentRegService.deleteStudent(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/register/semester/{semid}")
    public ResponseEntity<?>studentPerSem(@PathVariable("semid") Integer semid) throws  RemoteException{
        List<StudentRegistration> students=studentRegService.studentsPerSem(semid);
        return new ResponseEntity<>(students,HttpStatus.OK);
    }
    @GetMapping(value = "/register/academic/{acaid}/semester/{semid}")
    public ResponseEntity<?>studentPerSemDep(@PathVariable("acaid") Integer acaid, @PathVariable("semid") Integer semid) throws  RemoteException{
        List<StudentRegistration> studpersemsDeps=studentRegService.studentsPerSemAndDep(acaid,semid);
        return new ResponseEntity<>(studpersemsDeps,HttpStatus.OK);
    }
    @GetMapping(value = "/register/semester/{semid}/course/{courseid}")
    public ResponseEntity<?>studentPerSemCourse(@PathVariable("semid") Integer semid, @PathVariable("courseid") Integer courseid) throws  RemoteException{
        List<StudentRegistration> studperCourse=studentRegService.studentsPerSemCourse(semid,courseid);
        return new ResponseEntity<>(studperCourse,HttpStatus.OK);
    }
}
