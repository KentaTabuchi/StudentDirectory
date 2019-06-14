package com.kenta.tabuchi;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ViewController {
	
    @Autowired
    private JdbcTemplate jdbc;
    
	
	@RequestMapping(value= "/",method=RequestMethod.GET)
	public ModelAndView indexGet(ModelAndView mav) {
		mav.setViewName("test");
		M_StudentDao dao = new M_StudentDao(jdbc);
		List<Map<String,Object>> recordset = dao.findAll();
		mav.addObject("msg",recordset.get(0));
		
		return mav;
	}
}
