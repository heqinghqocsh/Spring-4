package com.heqing.spring.jdbc.transation;

import java.util.List;

public interface Cashier {

	public void checkOut(String username,List<String> isbns);
	
}
