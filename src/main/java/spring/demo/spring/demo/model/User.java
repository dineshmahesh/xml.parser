package spring.demo.spring.demo.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
	@JacksonXmlProperty(isAttribute = true, localName ="username")
	private String username;
	@JacksonXmlProperty(isAttribute = true, localName ="password")
	private String password;
}
