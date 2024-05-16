package com.app.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.mysql.cj.jdbc.CallableStatement;

public class AccountDaoImpl implements AccountDao {

	private Connection cn;
	private CallableStatement cst1;

	@Override
	public String transferFunds(int srcNo, int descAccNo, 
			double amount) throws SQLException {
		// TODO Auto-generated method stub
		cst1.setInt(1, srcNo);
		cst1.setInt(2, descAccNo);
		cst1.setDouble(3, amount);
		return "updated src balance" + cst1.getDouble(4) + "dest balanbce" + cst1.getDouble(5);
	}

	public void cleanup() throws SQLException {
		System.out.println("Acc Dao clean up");
		if (cst1 != null)
			cst1.close();
	}
}
