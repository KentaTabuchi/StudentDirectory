package com.kenta.tabuchi;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
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
			M_StudentDao dao = new M_StudentDao(jdbc);
			dao.insert(student);
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
		M_StudentDao dao = new M_StudentDao(jdbc);
		List<Student> recordset = dao.findAll();
		mav.addObject("recordSet", recordset);
		return mav;
	}
	@RequestMapping(value="/find_record",method=RequestMethod.POST)
	public ModelAndView findRecordPost(
			@RequestParam("name")String name,
			ModelAndView mav) {
		mav.setViewName("find_record");
		M_StudentDao dao = new M_StudentDao(jdbc);
		List<Student> recordset = dao.findByNameLike(name);
		mav.addObject("recordSet",recordset);
		return mav;
	}
	@RequestMapping(value="/delete_record",method=RequestMethod.GET)
	public ModelAndView deleteRecord(
			ModelAndView mav) {
		mav.setViewName("delete_record");
		M_StudentDao dao = new M_StudentDao(jdbc);
		Iterable<Student> list = dao.findAll();
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
		M_StudentDao dao = new M_StudentDao(jdbc);
		List<Student> recordset = dao.findById(id);
		mav.addObject("formModel",recordset.get(0));
		return mav;
	}
	@RequestMapping(value="/delete_comfirm",method=RequestMethod.POST)
	public ModelAndView deleteComfirmPost(
			@RequestParam("hiddenId")String id,
			ModelAndView mav) {
		mav.setViewName("index");
		M_StudentDao dao = new M_StudentDao(jdbc);
		dao.deleteById(id);
		List<Student> recordset = dao.findAll();
		mav.addObject("recordSet", recordset);
		return mav;
	}
	@RequestMapping(value="/edit_form/{id}",method=RequestMethod.GET)
	public ModelAndView editForm(
			@PathVariable("id")String id,
			ModelAndView mav) {
		M_StudentDao dao = new M_StudentDao(jdbc);
		mav.setViewName("edit_form");
		List<Student> recordset = dao.findById(id);
		mav.addObject("formModel",recordset.get(0));
		return mav;
	}

	@RequestMapping(value="/edit_form",method=RequestMethod.POST)
	public ModelAndView editFormPost(
			@ModelAttribute("formModel")@Validated Student student,
			BindingResult result,
			ModelAndView mav) {
		M_StudentDao dao = new M_StudentDao(jdbc);
		dao.updateById(student);
		return new ModelAndView("redirect:/");
	}
	@RequestMapping(value="/edit_select",method=RequestMethod.GET)
	public ModelAndView editSelectGet(
			ModelAndView mav) {
		M_StudentDao dao = new M_StudentDao(jdbc);
		List<Student> recordset = dao.findAll();
		mav.addObject("recordSet", recordset);
		mav.setViewName("edit_select");
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
		CsvReader csvReader = new CsvReader();
		M_StudentDao dao = new M_StudentDao(jdbc);
		csvReader.addTableFromCsv(uploadFile,dao);
		mav = new ModelAndView("redirect:/");
		return mav;
	}
    /**
     * This method downloads CSV file that contains all records form M_Students table. 
     */
    @RequestMapping("csvDownload")
    public void csvDownload(HttpServletResponse response) {
    	M_StudentDao dao = new M_StudentDao(jdbc);
    	CsvReader csvReader = new CsvReader();
    	csvReader.exportCSV(response, dao);
    }

}