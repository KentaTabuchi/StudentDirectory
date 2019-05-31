package com.kenta.tabuchi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.kenta.tabuchi.repositories.StudentRepository;

@Controller
public class ModelAndViewController {
	
	@Autowired
	StudentRepository repository;
	
	@RequestMapping("/")
	public ModelAndView index(ModelAndView mav) {
		mav.setViewName("index");
		Iterable<Student> list = repository.findAll();
		mav.addObject("recordSet", list);
		return mav;
		
	}
	
	@RequestMapping(value="/add_record",method=RequestMethod.GET)
	public ModelAndView addRecord(@ModelAttribute("formModel")Student student,ModelAndView mav) {
		mav.setViewName("add_record");
		return mav;
	}
}
