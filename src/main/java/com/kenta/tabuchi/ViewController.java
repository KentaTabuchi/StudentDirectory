package com.kenta.tabuchi;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ViewController {
	
    @Autowired
    private JdbcTemplate jdbc;
    
	

@RequestMapping(value= {"/","/{num}"},method=RequestMethod.GET)
public ModelAndView indexGet(@PathVariable(name="num",required=false)Integer num,ModelAndView mav) {
	mav.setViewName("index");
	M_StudentDao dao = new M_StudentDao(jdbc);
	List<Student> recordset = null;

	if(num==null) {
		recordset= dao.findAll();
	}
	else {
		switch(num) {
		case 0:
			recordset = dao.findAllByOrderByNamePhonetic();
			break;
		case 1:
			recordset = dao.findAllByOrderById();
			break;
		case 2:
			recordset = dao.findAllByOrderByBirthday();
			break;
		}
	}

	mav.addObject("recordSet", recordset);
	return mav;
	}
}