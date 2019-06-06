package com.kenta.tabuchi;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
	

	@PostConstruct
	public void init() {
		TestData testData = new TestData();
		testData.setSampleDataToDB(repository);
	}
	/**
	 * When user access "index.html" with no post data.This method will invoke.
	 * @param mav
	 * @return
	 */
	@RequestMapping(value="/",method=RequestMethod.GET)
	public ModelAndView index(ModelAndView mav) {
		mav.setViewName("index");
		Iterable<Student> list = repository.findAll();
		mav.addObject("recordSet", list);
		return mav;
	}
	
	/**
	 * When user push button for find by name.This method will invoke.
	 * @param mav
	 * @return
	 */
	@RequestMapping(value="/",method=RequestMethod.POST)
	public ModelAndView post(
			@RequestParam("upload_file")MultipartFile uploadFile,
			ModelAndView mav) 
	{
		mav.setViewName("index");
		mav.addObject("file_name",uploadFile.getOriginalFilename());
		CsvReader csvReader = new CsvReader();
		csvReader.addTableFromCsv(uploadFile,repository);
		mav.addObject("file_contents",csvReader.getLinesFromCsv(uploadFile));
		mav = new ModelAndView("redirect:/");
		return mav;
	}	

	
	/** 
	 * When user push add_record button,Active page will move there.and this method 
	 * will invoke like a constructor.
	 * @param student
	 * @param mav
	 * @return
	 */
	@RequestMapping(value="/add_record",method=RequestMethod.GET)
	public ModelAndView addRecord(@ModelAttribute("formModel")Student student,ModelAndView mav) {
		mav.setViewName("add_record");
		return mav;
	}
	
	
	/**
	 * This method implements validation to add_record form.
	 * @param student
	 * @param result
	 * @param mav
	 * @return
	 */
	@RequestMapping(value="/add_record",method=RequestMethod.POST)
	public ModelAndView form(
			@ModelAttribute("formModel") @Validated Student student,
			BindingResult result,
			ModelAndView mav)
	{
		ModelAndView adoptedMav = null;
		if(!result.hasErrors()) {
			repository.saveAndFlush(student);
			adoptedMav = new ModelAndView("redirect:/");
		}else {
			mav.setViewName("add_record");
			adoptedMav = mav;
		}
		return adoptedMav;
	}
	
	@RequestMapping(value="/find_record",method=RequestMethod.GET)
	public ModelAndView findRecord(
			ModelAndView mav) {
		mav.setViewName("find_record");
		Iterable<Student> list = repository.findAll();
		mav.addObject("recordSet", list);
		return mav;
	}
	@RequestMapping(value="/find_record",method=RequestMethod.POST)
	public ModelAndView findRecordPost(
			@RequestParam("name")String name,
			ModelAndView mav) {
		mav.setViewName("find_record");
		Iterable<Student> list = repository.findByNameLike("%"+name+"%");
		mav.addObject("recordSet",list);
		return mav;
	}
	@RequestMapping(value="/delete_record",method=RequestMethod.GET)
	public ModelAndView deleteRecord(
			ModelAndView mav) {
		mav.setViewName("delete_record");
		Iterable<Student> list = repository.findAll();
		mav.addObject("recordSet", list);
		return mav;
	}
	@RequestMapping(value="/delete_record",method=RequestMethod.POST)
	public ModelAndView deleteRecordPost(
		@RequestParam("id")String id,
		ModelAndView mav) {

		return mav;
	}
	@RequestMapping(value="/delete_comfirm/{id}",method=RequestMethod.GET)
	public ModelAndView deleteComfirm(
			@PathVariable("id")String id,
			ModelAndView mav) {
		mav.setViewName("delete_comfirm");
		Optional<Student> record = repository.findById(Long.valueOf(id));
		mav.addObject("formModel",record.get());
		return mav;
	}
	@RequestMapping(value="/delete_comfirm",method=RequestMethod.POST)
	public ModelAndView deleteComfirmPost(
			@RequestParam("hiddenId")String id,
			ModelAndView mav) {
		mav.setViewName("index");
		repository.deleteById(Long.valueOf(id));
		Iterable<Student> list = repository.findAll();
		mav.addObject("recordSet", list);
		return mav;
	}
	@RequestMapping(value="/edit_form/{id}",method=RequestMethod.GET)
	public ModelAndView editForm(
			@PathVariable("id")String id,
			ModelAndView mav) {
		mav.setViewName("delete_comfirm");
		Optional<Student> record = repository.findById(Long.valueOf(id));
		mav.addObject("formModel",record.get());
		return mav;
	}
	@RequestMapping(value="/edit_form",method=RequestMethod.POST)
	public ModelAndView editFormPost(
			@RequestParam("hiddenId")String id,
			ModelAndView mav) {
		mav.setViewName("index");
		repository.deleteById(Long.valueOf(id));
		Iterable<Student> list = repository.findAll();
		mav.addObject("recordSet", list);
		return mav;
	}
	@RequestMapping(value="/edit_select",method=RequestMethod.GET)
	public ModelAndView editSelectGet(
			ModelAndView mav) {
		Iterable<Student> list = repository.findAll();
		mav.addObject("recordSet", list);
		mav.setViewName("edit_select");
		return mav;
	}
	@RequestMapping(value="/edit_select",method=RequestMethod.POST)
	public ModelAndView editSelectPost(
			@RequestParam("hiddenId")String id,
			ModelAndView mav) {
		mav.setViewName("index");
		repository.deleteById(Long.valueOf(id));
		Iterable<Student> list = repository.findAll();
		mav.addObject("recordSet", list);
		return mav;
	}
}
