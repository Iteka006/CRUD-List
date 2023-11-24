package auca.com.demo.repository;

import auca.com.demo.domain.CourseDefinition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseDefinitionRepository extends JpaRepository<CourseDefinition,Integer> {
}
