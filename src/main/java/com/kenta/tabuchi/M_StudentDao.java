package com.kenta.tabuchi;

import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;

public class M_StudentDao {

    private JdbcTemplate jdbc;
    
    public M_StudentDao(JdbcTemplate jdbc) {
    	this.jdbc = jdbc;
    	try {
			SSHConnection sshcon = new SSHConnection();

		} catch (Throwable e) {
			e.printStackTrace();
		}
    }
    
    public List<Map<String,Object>> findAll(){
    	List list = this.jdbc.queryForList("SELECT * FROM M_student");
    	list.forEach(System.out::println);
    	return list;
    }
    	
}
