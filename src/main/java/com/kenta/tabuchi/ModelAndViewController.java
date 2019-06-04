package com.kenta.tabuchi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
	
	/**
	 * This is the method that makes some sample data for test.
	 */
	@PostConstruct
	public void init() {
		Student student1 = new Student();
		student1.setName("山田 太郎");
		student1.setBirthday("1956/12/10");
		student1.setPhone("090-1111-2222");
		student1.setAddress("東京都江東区古谷町一丁目2番3号");
		student1.setEmail("taro.yamada@google.com");
		student1.setGraduation("1974");
		repository.saveAndFlush(student1);
		
		Student student2 = new Student();
		student2.setName("鈴木 花子");
		student2.setBirthday("1964/05/03");
		student2.setPhone("080-1234-3456");
		student2.setAddress("大阪府大阪市都島区柏町５丁目４番２２号");
		student2.setEmail("suzuki.hanako@exchange.com");
		student2.setGraduation("1982");
		repository.saveAndFlush(student2);
		
		Student student3 = new Student();
		student3.setName("山田 花子");
		student3.setBirthday("1970/01/30");
		student3.setPhone("0120-9999-5555");
		student3.setAddress("北海道旭川市斗亜流町３丁目１０番５号");
		student3.setEmail("hanako.yamada@yahoo.co.jp");
		student3.setGraduation("1986");
		repository.saveAndFlush(student3);
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
			@RequestParam("name")String name,
			@RequestParam("upload_file")MultipartFile file,
			ModelAndView mav) 
	{
		mav.setViewName("index");
		Iterable<Student> list = repository.findByNameLike("%"+name+"%");//% is wild card.
		mav.addObject("file_name",file.getOriginalFilename());
		mav.addObject("file_contents", fileContents(file));
		mav.addObject("recordSet", list);
		return mav;
	}	
	private String fileContents(MultipartFile file) {
		String line = null;
		try {
			InputStream stream = file.getInputStream();
			Reader reader = new InputStreamReader(stream);
			BufferedReader buf= new BufferedReader(reader);
			line = buf.readLine();
			
		} catch (IOException e) {
			line = "Can't read contents.";
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return line;
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
}
