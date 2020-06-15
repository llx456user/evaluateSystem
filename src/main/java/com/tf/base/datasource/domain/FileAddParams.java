package com.tf.base.datasource.domain;

import org.springframework.web.multipart.MultipartFile;

import com.tf.base.common.domain.DatasourceFile;

public class FileAddParams extends DatasourceFile {

	private MultipartFile file;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
}
