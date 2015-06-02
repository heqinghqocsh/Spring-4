package com.heqing.spring.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

public class JDBCTest {

	private ApplicationContext ctx = null;
	private JdbcTemplate jdbcTemplate;
	
	{
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		jdbcTemplate = (JdbcTemplate) ctx.getBean("jdbcTemplate");
	}
	
	/**
	 * 批量操作
	 */
	@Test
	public void testBatchUpdate(){
		String sql = "INSERT INTO employee(employeeName, employeeSalary"
				+ ", employeeEmail, dept_id) VALUES(?,?,?,?)";
		List<Object[]> batchArgs = new ArrayList<>();
		batchArgs.add(new Object[]{ "小雪",5000,"12345678@outlook.com","ewe"});
		batchArgs.add(new Object[]{ "小雨",8000,"1278@outlook.com","ewe"});
		jdbcTemplate.batchUpdate(sql,batchArgs);
		
	}
	
	@Test
	public void textUpdate(){
		String sql = "UPDATE employee SET employeeName = ? WHERE employeeId = ?";
		jdbcTemplate.update(sql, "何清",2);
	}
	
	@Test
	public void testDataSource() throws SQLException {
		DataSource dataSources = ctx.getBean(DataSource.class);
		System.out.println(dataSources.getConnection());
	}

}
