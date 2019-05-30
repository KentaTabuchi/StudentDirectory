package com.kenta.tabuchi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kenta.tabuchi.repositories.StudentRepository;

@Controller
public class ModelViewController {
	
	@Autowired
	StudentRepository repository;
	
	@RequestMapping(value="/")
	public ModelAndView index(ModelAndView mav) {
		mav.setViewName("index");
		Iterable<Student> list = repository.findAll();
		mav.addObject("recordSet", list);
		return mav;
	}
}
