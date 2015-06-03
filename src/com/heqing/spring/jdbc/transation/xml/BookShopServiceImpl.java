package com.heqing.spring.jdbc.transation.xml;


public class BookShopServiceImpl implements BookShopService{

	private BookShopDao bookShopDao;
	
	public void setBookShopDao(BookShopDao bookShopDao) {
		this.bookShopDao = bookShopDao;
	}
	
	public void purchase(String username, String isbn) {
		//获取输的单价
		float price = bookShopDao.findBookPriceByIsbn(isbn);
		
		//更新书的库存
		bookShopDao.updateBookStock(isbn);
		
		//更新余额
		bookShopDao.updateUserBalance(username, (int)price);
	}

}
