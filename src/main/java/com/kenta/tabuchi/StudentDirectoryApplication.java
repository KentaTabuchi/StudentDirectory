package com.kenta.tabuchi;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;


  @SpringBootApplication public class StudentDirectoryApplication {
  
  public static void main(String[] args) {
	
  //SpringApplication.run(StudentDirectoryApplication.class, args); }
  //}
	  
  try (ConfigurableApplicationContext ctx = SpringApplication.run(StudentDirectoryApplication.class, args)) {
  	StudentDirectoryApplication m = ctx.getBean(StudentDirectoryApplication.class);
      m.method();
  }}
  
  @Autowired
  private JdbcTemplate jdbc;

  public void method() {

      Session session = null;

      try {
          String strSshUser = "vagrant";
          String strSshPassword = "vagrant";
          String strSshHost = "127.0.0.1";
          int nSshPort = 2222;

          String strRemoteHost = "127.0.0.1";
          int nLocalPort = 3306;
          int nRemotePort = 3306;


          final JSch jsch = new JSch();
          session = jsch.getSession(strSshUser, strSshHost, nSshPort);
          session.setPassword(strSshPassword);

          final Properties config = new Properties();
          config.put("StrictHostKeyChecking", "no");
          session.setConfig(config);

          session.connect();
          
          session.setPortForwardingL(nLocalPort, strRemoteHost, nRemotePort);

          List<Map<String, Object>> list = this.jdbc.queryForList("SELECT * FROM M_student");
          list.forEach(System.out::println);

      } catch (JSchException e) {
          e.printStackTrace();
      } finally {
          session.disconnect();
      }
  }}
  
  
  
 

