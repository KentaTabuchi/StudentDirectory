package com.kenta.tabuchi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kenta.tabuchi.Student;

/**
 * @author tabuchikenta
 *
 *　This is a special interface of SpringFrameWork.
 *　This interface provide us accessing DB easier.
 *	When we write method's define in this interface,Compiler implements it's method
 *	automatically.and the method contains the SQL in it's self with we never see SQL.
 *	
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
	public List<Student> findByNameLike(String name);
	public List<Student> findAllByOrderByName();
}
