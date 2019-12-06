package spring.demo.spring.demo;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import spring.demo.spring.demo.model.User;
import spring.demo.spring.demo.model.Users;

public class PasswordEncryptDemo {
	
	public static void main(String[] args) {
		XmlMapper mapper = new XmlMapper();
		InputStream inputStream = App.class.getClassLoader().getResourceAsStream("user.details.xml");
		try {
			Users users = mapper.readValue(inputStream, Users.class);
			List<User> userList = users.getUsers();
			userList.forEach(user -> {
				String encryptedPassword = PasswordUtils.encrypt(user.getPassword());
				user.setPassword(encryptedPassword);
			});
			
			FileWriter writer = new FileWriter("user.details.encrypted.xml");
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			mapper.writeValue(writer, users);
			writer.close();
			
			inputStream = new FileInputStream("user.details.encrypted.xml");
			users = mapper.readValue(inputStream, Users.class);
			inputStream.close();
			
			userList = users.getUsers();
			userList.forEach(user -> {
				String encryptedPassword = PasswordUtils.decrypt(user.getPassword());
				user.setPassword(encryptedPassword);
			});
			inputStream.close();

		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("The End");
	}
}
