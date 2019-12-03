package spring.demo.spring.demo.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Data;

@Data
public class Port {
	private String protocol;
	@JacksonXmlProperty(localName = "port-number")
	private int number;

}
