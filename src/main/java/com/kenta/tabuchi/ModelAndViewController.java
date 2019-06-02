package com.kenta.tabuchi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kenta.tabuchi.repositories.StudentRepository;

@Controller
public class ModelAndViewController {
	
	@Autowired
	StudentRepository repository;
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public ModelAndView index(ModelAndView mav) {
		mav.setViewName("index");
		Iterable<Student> list = repository.findAll();
		mav.addObject("recordSet", list);
		return mav;
	}
	@RequestMapping(value="/",method=RequestMethod.POST)
	public ModelAndView post(UploadForm uploadForm,ModelAndView mav) throws IOException {

		mav.addObject("msg",uploadForm.getMultipartFile().getOriginalFilename());
		mav.setViewName("file_open_test");
		return mav;
	}
	
	
	@RequestMapping(value="/add_record",method=RequestMethod.GET)
	public ModelAndView addRecord(@ModelAttribute("formModel")Student student,ModelAndView mav) {
		mav.setViewName("add_record");
		return mav;
	}
	@RequestMapping(value="/add_record",method=RequestMethod.POST)
	public ModelAndView form(@ModelAttribute("formModel")Student student,ModelAndView mav) {
		repository.saveAndFlush(student);
		return new ModelAndView("redirect:/");
	}
}
