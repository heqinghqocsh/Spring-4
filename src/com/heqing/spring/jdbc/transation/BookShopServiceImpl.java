package com.heqing.spring.jdbc.transation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("bookShopService")
public class BookShopServiceImpl implements BookShopService{

	@Autowired
	private BookShopDao bookShopDao;
	
	//添加事务注解
	//使用propagation指定事务的传播行为，即当前的事务方法被另外一个事务方法调用时
	//如何使用事务，默认取值为REQUIRED，即使用调用方法的事务
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public void purchase(String username, String isbn) {
		//获取输的单价
		float price = bookShopDao.findBookPriceByIsbn(isbn);
		
		//更新书的库存
		bookShopDao.updateBookStock(isbn);
		
		//更新余额
		bookShopDao.updateUserBalance(username, (int)price);
	}

}
