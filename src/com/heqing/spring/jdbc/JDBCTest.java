package com.heqing.spring.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public class JDBCTest {

	private ApplicationContext ctx = null;
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	{
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		jdbcTemplate = (JdbcTemplate) ctx.getBean("jdbcTemplate");
		namedParameterJdbcTemplate = (NamedParameterJdbcTemplate) ctx
				.getBean("namedParameterJdbcTemplate");
	}
	
	/**
	 * 使用具名参数是可以使用update(String sql, SqlParameterSource paramSource) 方法进行更新操作
	 * 1.SQL语句中的参数名和类的属性一致
	 * 2.使用BeanPropertySqlParameterSource作为参数
	 * 	 */
	@Test
	public void testNamedParameterJdbcTemplate2(){
		String sql = "INSERT INTO department(departmentName) VALUES(:name)";
		Department department = new Department();
		department.setName("xxxxxxx");
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(department);
		namedParameterJdbcTemplate.update(sql, paramSource);
		
	}
	
	
	/**
	 * 具名参数：可以为参数取名字
	 * 1.好处：若有多个参数，则不用去对应位置，便于维护
	 * 2.缺点：较为麻烦
	 */
	@Test
	public void testNamedParameterJdbcTemplate(){
		String sql = "INSERT INTO employee(employeeName, employeeSalary"
				+ ", employeeEmail, dept_id) VALUES(:name,:salary,:email,:dept_id)";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("name", "HQOCSH");
		paramMap.put("email", "heiqng@utlook.com");
		paramMap.put("salary", 10);
		paramMap.put("dept_id", "heha");
		namedParameterJdbcTemplate.update(sql, paramMap);
	}
	
	
	/**
	 * 从数据库获取一条记录，并且得到对应的一个对象
	 * 1.其中的RowMapper指定如何去映射结果集的行
	 * 2.使用sql 中列的别名完成列名和类的属性的映射，例如employeeName name
	 * 3.不支持级联属性，如dept_id as \"dept.id\".
	 */
	@Test
	public void testQueryForObject(){
		String sql = "SELECT employeeId id,employeeName name,employeeEmail email "
				+ "FROM employee WHERE employeeId = ?";
		RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>(Employee.class);
		Employee employee = jdbcTemplate.queryForObject(sql, rowMapper, 12);
		System.out.println(employee);
	}
	
	/**
	 * 查到实体类的集合
	 * 注意调用的不是queryforlist（）方法
	 */
	@Test
	public void testQueryForList(){
		String sql = "SELECT employeeId id,employeeName name,employeeEmail email "
				+ "FROM employee";
		RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>(Employee.class);
		List<Employee> employees = jdbcTemplate.query(sql, rowMapper);
		for (Employee employee : employees) {
			System.out.println(employee);
		}
	}
	
	/**
	 * 获取某一个值
	 */
	@Test
	public void testQueryForValue(){
		String sql = "SELECT count(employeeId) FROM employee";
		long count = jdbcTemplate.queryForObject(sql, Long.class);
		System.out.println("一共有记录："+count);
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
