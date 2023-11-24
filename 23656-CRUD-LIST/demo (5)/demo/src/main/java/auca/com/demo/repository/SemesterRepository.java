package auca.com.demo.repository;

import auca.com.demo.domain.Semester;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SemesterRepository extends JpaRepository<Semester,Integer> {
}
