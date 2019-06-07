package com.kenta.tabuchi;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MimeTypeUtils;
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

	private static final Logger logger = LoggerFactory.getLogger(CsvReader.class);

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
	public ModelAndView indexGet(ModelAndView mav) {
		mav.setViewName("index");
		Iterable<Student> list = repository.findAll();
		mav.addObject("recordSet", list);
		return mav;
	}
	

	@RequestMapping(value="/",params="onOderByNameClick",method=RequestMethod.POST)
	public ModelAndView indexPostSortByName(ModelAndView mav) {
		mav.setViewName("index");
		Iterable<Student>list = repository.findAllByOrderByNamePhonetic();
		mav.addObject("recordSet", list);
		return mav;
	}
	@RequestMapping(value="/",params="onOderByIdClick",method=RequestMethod.POST)
	public ModelAndView indexPostSortById(ModelAndView mav) {
		mav.setViewName("index");
		Iterable<Student>list = repository.findAllByOrderById();
		mav.addObject("recordSet", list);
		return mav;
	}
	@RequestMapping(value="/",params="onOderByBirthdayClick",method=RequestMethod.POST)
	public ModelAndView indexPostSortByBirthday(ModelAndView mav) {
		mav.setViewName("index");
		Iterable<Student>list = repository.findAllByOrderByBirthday();
		mav.addObject("recordSet", list);
		return mav;
	}
	                                         
	/**
	 * When user push button for find by name.This method will invoke.
	 * @param mav
	 * @return
	 */
	@RequestMapping(value="/",params="onCsvImportClick",method=RequestMethod.POST)
	public ModelAndView indexPostCsv(
			@RequestParam("upload_file")MultipartFile uploadFile,
			ModelAndView mav) 
	{
		logger.info("CSVからインポートがクリックされた");
		CsvReader csvReader = new CsvReader();
		csvReader.addTableFromCsv(uploadFile,repository);
		mav = new ModelAndView("redirect:/");
		return mav;
	}	
    /**
     * ユーザー情報csv形式でダウンロードする
     */
    @RequestMapping("csvDownload")
    public void csvDownload(HttpServletResponse response) {
    	logger.info("ページ遷移を確認");
        //文字コードと出力するCSVファイル名を設定
        //response.setContentType(MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE + ";charset=utf-8");
        response.setContentType("application/octet-stream" + ";charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"dl.csv\"");

		  try (PrintWriter pw = response.getWriter()) { 
			  List<Student> list =repository.findAll(); 
			  for (int i = 0; i < list.size(); i++) {
				  long id =	list.get(i).getId();
				  String name = list.get(i).getName();
				  String email =list.get(i).getEmail();
				  String address = list.get(i).getAddress();
		  
				  //CSVファイル内部に記載する形式で文字列を設定 
				  String outputString = id + "," + name + "," + email
				  + "," + address + "," + "\r\n";
				  
				  //CSVファイルに書き込み 
				  pw.print(outputString); 
				  } 
		  } catch (IOException e) {
		  e.printStackTrace(); 
		  }
		 
        
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
		mav.setViewName("edit_form");
		Optional<Student> record = repository.findById(Long.valueOf(id));
		mav.addObject("formModel",record.get());
		return mav;
	}

	@RequestMapping(value="/edit_form",method=RequestMethod.POST)
	@Transactional(readOnly=false)
	public ModelAndView editFormPost(
			@ModelAttribute("formModel")@Validated Student student,
			BindingResult result,
			ModelAndView mav) {
		repository.saveAndFlush(student);
		return new ModelAndView("redirect:/");
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
	@Transactional(readOnly=false)
	public ModelAndView editSelectPost(
			@ModelAttribute Student student,
			ModelAndView mav) {
		repository.saveAndFlush(student);
		return new ModelAndView("redirect:/");
	}

}
