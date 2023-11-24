package auca.com.demo.service;

import auca.com.demo.domain.CourseDefinition;

import java.rmi.RemoteException;
import java.util.List;

public interface CourseDefService {
    CourseDefinition insertCourseDefinition(CourseDefinition courseDefinition) throws RemoteException;

    CourseDefinition selectCourseDefinition(int id) throws RemoteException;

    List<CourseDefinition> selectAllCourseDefinitions()throws RemoteException;

    boolean deleteCourseDefinition(int id) throws RemoteException;

    CourseDefinition updateCourseDefinition(int courseId, String name,String Description) throws RemoteException;
}
