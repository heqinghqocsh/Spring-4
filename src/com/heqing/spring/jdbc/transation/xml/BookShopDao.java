package com.heqing.spring.jdbc.transation.xml;

public interface BookShopDao {

	//根据书号获取书的单价
	public float findBookPriceByIsbn(String isbn);
	
	//更新书的库存,使书号对应的库存减一
	public void updateBookStock(String isbn);
	
	//更新用户金额
	public void updateUserBalance(String username,int price);
	
}
