package com.kenta.tabuchi;

import org.springframework.web.multipart.MultipartFile;

/*I guess this class is required from read uploaded files*/
public class UploadForm {
	private MultipartFile multipartFile;

	public MultipartFile getMultipartFile() {
		return multipartFile;
	}

	public void setMultipartFile(MultipartFile multipartFile) {
		this.multipartFile = multipartFile;
	}
	
}
