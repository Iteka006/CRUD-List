package auca.com.demo.service;

import auca.com.demo.domain.Course;

import java.rmi.RemoteException;
import java.util.List;

public interface CourseService {
    Course insertCourse(Course course) throws RemoteException;

    Course selectCourse(int courseId)throws RemoteException;

    List<Course> selectAllCourses()throws RemoteException;

    boolean deleteCourse(int id) throws RemoteException;

    Course updateAcademicUnit(Course course)throws RemoteException;
    List<Course> getCourseByDepAndSemester(int acaid, int semid);
    List<Course> getCoursePerStudent(int studentId);
}
