package com.adu.springboot;

import com.adu.springboot.pojo.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot01ApplicationTests {


	@Autowired
	private Person person;

	@Autowired
	private ApplicationContext ioc;

	@Test
	public void contextIOC() {
		boolean helloService = ioc.containsBean("helloService");
		System.out.println(helloService);
	}

	@Test
	public void contextLoads() {
		System.out.println(person);
	}

	/**
	 * 日志记录
	 */
	@Test
	public void contextLogs() {
        Logger logger = LoggerFactory.getLogger(getClass());
        logger.trace("这是trace...日志");
        logger.debug("这是debug...日志");
        logger.warn("这是warn...日志");
        logger.info("这是info...日志");
        logger.error("这是error...日志");
    }

}
