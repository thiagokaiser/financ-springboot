package com.kaiser.financ.dto;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadDTO {
	private MultipartFile file;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	
}
