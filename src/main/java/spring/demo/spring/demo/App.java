package spring.demo.spring.demo;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import spring.demo.spring.demo.model.Service;

public class App 
{
    public static void main( String[] args ) throws JsonParseException, JsonMappingException, IOException
    {
    	ObjectMapper objectMapper = new XmlMapper();
    	InputStream inputStream = App.class.getClassLoader().getResourceAsStream("services.xml");
    	TypeReference<List<Service>> typeReference = new TypeReference<List<Service>>() {};
    	List<Service> services = objectMapper.readValue(inputStream, typeReference);
    	System.out.println(services);
    }
}
