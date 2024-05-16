package com.app.dao;

import java.sql.SQLException;

public interface AccountDao {
	String transferFunds(int srcNo, int descAccNo, double amount) throws SQLException;
}

