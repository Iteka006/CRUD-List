package auca.com.demo.repository;

import auca.com.demo.domain.AcademicUnit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcademicRepository extends JpaRepository<AcademicUnit,Integer> {
}
