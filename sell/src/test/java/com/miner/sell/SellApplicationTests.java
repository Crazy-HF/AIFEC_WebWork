package com.miner.sell;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@MapperScan(basePackages = "com.miner.sell.dataobject.mapper")
@SpringBootTest
public class SellApplicationTests {

	@Test
	public void contextLoads() {
	}

}
