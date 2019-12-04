package spring.demo.spring.demo.model;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import lombok.Data;

@Data
public class Service {
	private int profile;
	private String name;
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<Port> port;
}