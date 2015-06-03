package com.heqing.spring.jdbc.transation.xml;

import java.util.List;

public interface Cashier {

	public void checkOut(String username,List<String> isbns);
	
}
