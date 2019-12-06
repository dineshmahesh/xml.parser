package spring.demo.spring.demo.model;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Getter;
import lombok.Setter;

@JacksonXmlRootElement(localName = "users")
@Getter
@Setter
public class Users {

	@JacksonXmlProperty(localName = "user")
	@JacksonXmlElementWrapper(useWrapping = false)
	List<User> users;

}
