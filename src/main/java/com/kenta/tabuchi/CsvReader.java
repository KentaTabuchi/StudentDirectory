package com.kenta.tabuchi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import com.kenta.tabuchi.repositories.StudentRepository;

public class CsvReader {
	private static final Logger logger = LoggerFactory.getLogger(CsvReader.class);
    

	
	/**
	 * This method adds record to db Table from csv file that uploded by user.
	 * @param uploadFile
	 * @param repository
	 */
	public void addTableFromCsv(MultipartFile uploadFile,StudentRepository repository) {
		logger.info("ログテスト");
		List<String> lines=fileContents(uploadFile);
		lines.forEach(line->{
			Student student = new Student();
			String[] data = line.split(",", -1);
			student.setName(data[1]);
			student.setBirthday(data[2]);
			student.setPhone(data[3]);
			student.setEmail(data[4]);
			student.setAddress(data[5]);
			student.setGraduation(data[6]);
			repository.saveAndFlush(student);
		});
	}
	
	/**
	 * @param uploadFile
	 * @return
	 * This method created for reading test.
	 */
	public List<String> getLinesFromCsv(MultipartFile uploadFile) {
		logger.info("ログテスト");
		List<String> result = new ArrayList<String>();
	
		List<String> lines=fileContents(uploadFile);
		lines.forEach(line->{
			String[] data = line.split(",", -1);
			result.add(data[0]);
			result.add(data[1]);
			result.add(data[2]);
			result.add(data[3]);
			result.add(data[4]);
			result.add(data[5]);
			result.add(data[6]);
		});
		return result;
	}
	
	/**
	 * This method open file from posted by user at "index.html".
	 * @param uploadFile 
	 * @return
	 */
	private List<String> fileContents(MultipartFile uploadFile) {
		List<String> lines = new ArrayList<String>();
		String line = null;
		try {
			InputStream stream = uploadFile.getInputStream();			
			Reader reader = new InputStreamReader(stream);
			BufferedReader buf= new BufferedReader(reader);
			while((line = buf.readLine()) != null) {
				lines.add(line);
			}
			line = buf.readLine();
			buf.close();
		} catch (IOException e) {
			line = "Can't read contents.";
			lines.add(line);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lines;
	}
}
