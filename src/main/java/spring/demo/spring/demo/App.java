package spring.demo.spring.demo;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import spring.demo.spring.demo.model.Service;

public class App {
	static int frequency = 0;
	static List<Service> serviceList;
    public static void main( String[] args ) throws JsonParseException, JsonMappingException, IOException {
    	ObjectMapper objectMapper = new XmlMapper();
    	InputStream inputStream = App.class.getClassLoader().getResourceAsStream("services.xml");
    	TypeReference<List<Service>> typeReference = new TypeReference<List<Service>>() {};
    	List<Service> services = objectMapper.readValue(inputStream, typeReference);
//    	System.out.println(services);
    	
    	Map<String, List<Service>> duplicatedServicesMap = new HashMap<String, List<Service>>();

    	List<String> protocols = Arrays.asList("tcp", "udp");
    	protocols.forEach(protocol -> {
    		serviceList = new ArrayList<Service>();
	    	services.stream().forEach(service -> {
	    	     service.getPort().stream().filter(port -> protocol.equals(port.getProtocol()))
	 							  .forEach(port -> { 
	 								  services.forEach(secondService -> {
	 									  secondService.getPort().stream().filter(secondPort -> protocol.equals(secondPort.getProtocol()))
	 									  			   .forEach(secondPort -> { 
	 									  				   if(secondPort.getNumber() == port.getNumber()) 
	 									  					   frequency++;
	 									  			   });
								  		 });
	 							  });
	    	     if(frequency > 1) 
	    	    	 serviceList.add(service);
	    	     frequency = 0;
	    	     duplicatedServicesMap.put(protocol, serviceList);
	    	});
	    	System.out.println(protocol + ": " + serviceList);
    	});
    	System.out.println(duplicatedServicesMap);
    	
    	CSVWriter writer = new CSVWriter(new FileWriter("service.csv"));
    	StatefulBeanToCsv<Service> beanToCsv = new StatefulBeanToCsvBuilder<Service>(writer).build();
        try {
			beanToCsv.write(duplicatedServicesMap.get(protocols.get(0)));
			beanToCsv.write(duplicatedServicesMap.get(protocols.get(1)));
		} catch (CsvDataTypeMismatchException e) {
			e.printStackTrace();
		} catch (CsvRequiredFieldEmptyException e) {
			e.printStackTrace();
		}
        writer.close();
    }
}
