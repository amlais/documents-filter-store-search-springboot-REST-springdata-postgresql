package com.amir.tika;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.microsoft.ooxml.OOXMLParser;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

/**
 * Content Extraction permet l'extraction du contenu 
 * à partir d'un input de type MultipartFile 
 * en utilisant Apache Tika content Analysis toolkit
 */
public class ContentExtraction {
	public static List<String> strings = new ArrayList<String>();
	
	
	public String getContent(MultipartFile file)throws IOException, SAXException, TikaException{
	    //Instantiating tika facade class 
		strings.add("application/pdf");
		strings.add("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
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
	
	/*public String parseContent(String filetype, InputStream inputstream) throws IOException, SAXException, TikaException{
		BodyContentHandler handler = new BodyContentHandler();
		Metadata metadata = new Metadata();
		ParseContext pcontext = new ParseContext();
		switch (filetype) {
		    case "application/pdf":
		        return parsePDF(inputstream, handler, pcontext, metadata);
		    case "application/msword":
		    	return parseMSWORDS(inputstream, handler, pcontext, metadata);
		default: 
		    	return "Invalid content";
		}
	}
	
	public String parsePDF(InputStream inputstream, BodyContentHandler handler, ParseContext pcontext, Metadata metadata) throws IOException, SAXException, TikaException{
    	//parsing the document using PDF parser
    	PDFParser pdfparser = new PDFParser(); 
        pdfparser.parse(inputstream, handler, metadata,pcontext);
        return handler.toString();
	}
	
	public String parseMSWORDS(InputStream inputstream, BodyContentHandler handler, ParseContext pcontext, Metadata metadata) throws IOException, SAXException, TikaException{
		//OOXml parser
		OOXMLParser  msofficeparser = new OOXMLParser (); 
		msofficeparser.parse(inputstream, handler, metadata,pcontext);
		return handler.toString();
	}*/
}
