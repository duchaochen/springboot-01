package com.adu.springboot;

import com.adu.springboot.pojo.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot01ApplicationTests {

	@Autowired
	private Person person;

	@Test
	public void contextLoads() {
		System.out.println(person);
	}

}
