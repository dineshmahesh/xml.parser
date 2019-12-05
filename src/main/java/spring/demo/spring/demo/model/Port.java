package spring.demo.spring.demo.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class Port {
	private String protocol;
	@JacksonXmlProperty(localName = "port-number")
	private int number;
}
