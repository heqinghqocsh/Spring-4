package com.heqing.spring.jdbc.transation.xml;

import org.springframework.jdbc.core.JdbcTemplate;

public class BookShopDaoImpl implements BookShopDao {

	private JdbcTemplate jdbcTemplate;
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public float findBookPriceByIsbn(String isbn) {
		String sql = "SELECT price from book WHERE isbn = ?";
		return jdbcTemplate.queryForObject(sql, Float.class, isbn);
	}

	@Override
	public void updateBookStock(String isbn) {
		//检查书的库存是否足够，若不够，则抛出异常
		String sql = "SELECT stock from book_stock WHERE isbn = ?";
		if (jdbcTemplate.queryForObject(sql, Integer.class, isbn) == 0) {
			throw new BookStockException("库存不足");
		}
		
		sql = "UPDATE book_stock SET stock = stock - 1 WHERE isbn = ?";
		jdbcTemplate.update(sql,isbn);
	}

	@Override
	public void updateUserBalance(String username, int price) {
		String sql = "SELECT balance from account where username = ?";
		if (jdbcTemplate.queryForObject(sql, Float.class, username) < price) {
			throw new UserAccountException("余额不足");
		}
		
		sql = "UPDATE account SET balance = balance - ? WHERE username = ?";
		jdbcTemplate.update(sql, price,username);

	}
	
}
