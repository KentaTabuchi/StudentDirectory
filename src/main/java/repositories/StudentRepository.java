package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kenta.tabuchi.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

}
