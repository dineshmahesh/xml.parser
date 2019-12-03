package spring.demo.spring.demo.model;

import java.util.List;

import lombok.Data;

@Data
public class Service {
	private int profile;
	private String name;
	private List<Port> port;
}