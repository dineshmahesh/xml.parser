package spring.demo.spring.demo;

import java.io.FileWriter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import spring.demo.spring.demo.model.Port;
import spring.demo.spring.demo.model.Service;

public class App {
	static int frequency = 0;
	static List<Service> serviceList;
    public static void main( String[] args ) {
    	try {
    		ObjectMapper objectMapper = new XmlMapper();
    		InputStream inputStream = App.class.getClassLoader().getResourceAsStream("services.xml");
    		TypeReference<List<Service>> typeReference = new TypeReference<List<Service>>() {};
    		List<Service> services = objectMapper.readValue(inputStream, typeReference);
    		inputStream.close();
    		
    		Map<String, List<Service>> duplicatedServicesMap = new HashMap<String, List<Service>>();
    		
    		List<String> protocols = Arrays.asList("tcp", "udp");
    		protocols.forEach(protocol -> {
    			serviceList = new ArrayList<Service>();
    			services.forEach(service -> {
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
    		
    		List<String> headers = Arrays.asList("Name", "Profile", "Port/Protocol", "Duplicated In");
    		FileWriter writer = new FileWriter("service.csv");
    		CSVUtils.writeRow(writer, headers);
    		
    		List<String> row;
    		for (String protocol : protocols) {
    			for (Service service : duplicatedServicesMap.get(protocol)) {
    				row = new ArrayList<String>();
    				row.add(service.getName());
    				row.add(String.valueOf(service.getProfile()));
    				StringBuilder portsBuilder = new StringBuilder();
    				boolean firstPort = true;
    				for (Port port : service.getPort()) {
    					portsBuilder.append(port.getNumber()).append("/").append(port.getProtocol());
    					if (firstPort)
    						portsBuilder.append(" ");
    					firstPort = false;
    				}
    				
    				row.add(portsBuilder.toString());
    				row.add(protocol);
    				CSVUtils.writeRow(writer, row);
    			}
    		}
    		writer.close();
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }
}
