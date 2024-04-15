package com.postit.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.postit.services.FileService;

@Service
public class FileServiceImpl implements FileService{

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		
		//File name eg abc.png
		String name = file.getOriginalFilename();
		
		//Random name generate file to make every file unique
		String randomID = UUID.randomUUID().toString();
		String fileName = randomID.concat(name.substring(name.lastIndexOf(".")));
		
		//Full file path
		String filePath = path + File.separator + fileName;
		
		//Create file destination folder if not already present
		File f = new File(path);
		if(!f.exists())
		{
			f.mkdir();
		}
		
		//Copy file to new destination
		Files.copy(file.getInputStream(), Paths.get(filePath));
		
		return fileName;		
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		
		String fullPath = path + File.separator + fileName;
		InputStream is = new FileInputStream(fullPath);
		
		//DB logic to return input stream
		
		return is;
	}

}
