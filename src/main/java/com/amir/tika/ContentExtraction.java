package com.amir.tika;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.springframework.web.multipart.MultipartFile;

/**
 * Content Extraction permet l'extraction du contenu 
 * à partir d'un input de type MultipartFile 
 * en utilisant Apache Tika content Analysis toolkit
 */
public class ContentExtraction {
	public final List<String> strings = new ArrayList<>(
		    Arrays.asList("application/pdf", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", "text/plain"));
	
	public String getContent(MultipartFile file)throws IOException, TikaException{
	    //Instantiating tika facade class 
	    Tika tika = new Tika();
	    //detecting the file type using detect method
	    String filetype;
	    TikaInputStream tk = TikaInputStream.get(file.getInputStream());
		filetype = tika.detect(tk);
		String var;
		if(strings.contains(filetype)){
			var = tika.parseToString(tk);
			return new String(var.getBytes("UTF-8"),"ISO-8859-1");
		}
		else
			return "Invalid content";
    }
}
