package com.kenta.tabuchi;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class StudentDirectoryApplication {
	@Autowired
	private JdbcTemplate jdbc;
	
	public static void main(String[] args) {
        try (ConfigurableApplicationContext ctx = SpringApplication.run(StudentDirectoryApplication.class, args)) {
        	StudentDirectoryApplication m = ctx.getBean(StudentDirectoryApplication.class);
            m.connect();
        }
      //SpringApplication.run(StudentDirectoryApplication.class, args);
		}
	private void connect() {
		try {
			SSHConnection sshcon = new SSHConnection();
			
	    
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    List<Map<String, Object>> list = jdbc.queryForList("SELECT * FROM M_student");
        list.forEach(System.out::println);

	}
}
