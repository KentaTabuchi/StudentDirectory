package com.kenta.tabuchi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;

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
    
    public List<Student> findById(String id){
    	SSHConnection sshcon = null;
    	List<Student> students = null;
    	final String sql = "SELECT * FROM M_student WHERE id = ?";
    	try {
			sshcon = new SSHConnection();
	    	List<Map<String,Object>> list = this.jdbc.queryForList(sql,id);
	    	students = mapValuOfStudents(list);
		} catch (Throwable e) {
			e.printStackTrace();
		}finally{
			sshcon.closeSSH();
		}
    	return students;
    }
    
    public List<Student> findByNameLike(String name){
    	SSHConnection sshcon = null;
    	List<Student> students = null;
    	final String sql = "SELECT * FROM M_student WHERE name LIKE ?";
    	try {
			sshcon = new SSHConnection();
	    	List<Map<String,Object>> list = this.jdbc.queryForList(sql,"%"+name+"%");
	    	students = mapValuOfStudents(list);
		} catch (Throwable e) {
			e.printStackTrace();
		}finally{
			sshcon.closeSSH();
		}
    	return students;
    }
    public void insert(Student student) {
    	SSHConnection sshcon = null;
    	final String sql = 
   "INSERT INTO M_student (name,namePhonetic,birthday,phone,email,address,graduation) VALUES(?,?,?,?,?,?,?)";
    	
    	try {
			sshcon = new SSHConnection();
			String [] ps = new String[7];//setting prepared statement.
			ps[0]=student.getName();
			ps[1]=student.getNamePhonetic();
			ps[2]=student.getBirthday();
			ps[3]=student.getPhone();
			ps[4]=student.getEmail();
			ps[5]=student.getAddress();
			ps[6]=student.getGraduation();

	    	this.jdbc.update(sql,ps[0],ps[1],ps[2],ps[3],ps[4],ps[5],ps[6]);
		} catch (Throwable e) {
			e.printStackTrace();
		}finally{
			sshcon.closeSSH();
		}
    }
    public void deleteById(String id) {
    	SSHConnection sshcon = null;
    	final String sql = "DELETE FROM M_student WHERE id = ?";
    	
    	try {
			sshcon = new SSHConnection();
	    	this.jdbc.update(sql,id);
		} catch (Throwable e) {
			e.printStackTrace();
		}finally{
			sshcon.closeSSH();
		}
    }
}
