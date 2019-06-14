package com.kenta.tabuchi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class M_StudentDao {

    private JdbcTemplate jdbc;
    
    public M_StudentDao(JdbcTemplate jdbc) {
    	this.jdbc = jdbc;

    }
    private List<Student> mapValuOfStudents(List<Map<String,Object>> list){
    	List<Student> recordSet = new ArrayList<Student>();
    	list.forEach(e->{
    		Student student = new Student();
    		student.setId(Long.valueOf(e.get("id").toString()));
    		student.setName(e.get("name").toString());
    		student.setNamePhonetic(e.get("namePhonetic").toString());
    		student.setBirthday(e.get("birthday").toString());
    		student.setPhone(e.get("phone").toString());
    		student.setEmail(e.get("email").toString());
    		student.setAddress(e.get("address").toString());
    		student.setGraduation(e.get("graduation").toString());
    		recordSet.add(student);
    	});
    	return recordSet;
    }
    public List<Student> findAll(){
    	SSHConnection sshcon = null;
    	List<Student> students = null;
    	final String sql = "SELECT * FROM M_student";
    	try {
			sshcon = new SSHConnection();
	    	List<Map<String,Object>> list = this.jdbc.queryForList(sql);
	    	students = mapValuOfStudents(list);
		} catch (Throwable e) {
			e.printStackTrace();
		}finally{
			sshcon.closeSSH();
		}
    	return students;
    }

    public List<Student> findAllByOrderByNamePhonetic(){
    	SSHConnection sshcon = null;
    	List<Student> students = null;
    	final String sql = "SELECT * FROM M_student ORDER BY namePhonetic ASC";
    	try {
			sshcon = new SSHConnection();
	    	List<Map<String,Object>> list = this.jdbc.queryForList(sql);
	    	students = mapValuOfStudents(list);
		} catch (Throwable e) {
			e.printStackTrace();
		}finally{
			sshcon.closeSSH();
		}
    	return students;
    }
    public List<Student> findAllByOrderById(){
    	SSHConnection sshcon = null;
    	List<Student> students = null;
    	final String sql = "SELECT * FROM M_student ORDER BY id ASC";
    	try {
			sshcon = new SSHConnection();
	    	List<Map<String,Object>> list = this.jdbc.queryForList(sql);
	    	students = mapValuOfStudents(list);
		} catch (Throwable e) {
			e.printStackTrace();
		}finally{
			sshcon.closeSSH();
		}
    	return students;
    }
    public List<Student> findAllByOrderByBirthday(){
    	SSHConnection sshcon = null;
    	List<Student> students = null;
    	final String sql = "SELECT * FROM M_student ORDER BY birthday ASC";
    	try {
			sshcon = new SSHConnection();
	    	List<Map<String,Object>> list = this.jdbc.queryForList(sql);
	    	students = mapValuOfStudents(list);
		} catch (Throwable e) {
			e.printStackTrace();
		}finally{
			sshcon.closeSSH();
		}
    	return students;
    }
}
