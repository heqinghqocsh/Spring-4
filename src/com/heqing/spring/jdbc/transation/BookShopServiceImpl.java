package com.heqing.spring.jdbc.transation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("bookShopService")
public class BookShopServiceImpl implements BookShopService{

	@Autowired
	private BookShopDao bookShopDao;
	
	//添加事务注解
	//1.使用propagation指定事务的传播行为，即当前的事务方法被另外一个事务方法调用时
	//如何使用事务，默认取值为REQUIRED，即使用调用方法的事务
	//2.isolation 指定事务的隔离级别，最常用的取值为READ_COMMITTED（读已提交）
	//3.默认情况下Spring的声明式事务对所有的运行时异常进行回滚，也可以通过对应的属性进行设置
	//4.使用readOnly 指定事务是否为只读
	//5.使用timeout 指定强制回滚事务之前事务可以占用的时间,防止一个连接占用的时间过长
	@Transactional(propagation=Propagation.REQUIRED
			,isolation=Isolation.READ_COMMITTED
			,readOnly=false
			,timeout=3)
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
