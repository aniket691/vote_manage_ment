package com.app.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.app.utils.DBUtils.*;

import com.app.entities.User;

public class UserDaoImpl implements UserDao {
	// state
	private Connection cn;
	private PreparedStatement pst1, pst2, pst3, pst4, pst5;

	// def ctor of the DAO layer
	public UserDaoImpl() throws SQLException {
		// get cn from db utils
		cn = openConnection();
		// pst1 : sign in
		pst1 = cn.prepareStatement("select * from users where email=? and password=?");
		// pst2 : get user details
		pst2 = cn.prepareStatement("select * from users where role='voter'");
		// pst3 : voter reg
		/*
		 * id | first_name | last_name | email | password | dob | status | role
		 */
		pst3 = cn.prepareStatement("insert into users values(default,?,?,?,?,?,?,?)");
		System.out.println("user dao created...");
		pst4 = cn.prepareStatement("update users set password = ? where email = ? and password = ?");
		pst5 = cn.prepareStatement("delete from users where id =?");
	}

	@Override
	public User signIn(String email, String password) throws SQLException {
		// 1. set IN params
		pst1.setString(1, email);
		pst1.setString(2, password);
		// 2. exec query
		try (ResultSet rst = pst1.executeQuery()) {
			// rst cursor : before the 1st row

			if (rst.next()) // => successful login
				return new User(rst.getInt(1), rst.getString(2), rst.getString(3), email, password, rst.getDate(7),
						rst.getDouble(6), rst.getString(8));
		}
		return null;
	}

	@Override
	public List<User> getUserDetails() throws SQLException {
		// 0. create empty list
		List<User> users = new ArrayList<>();
		// 2 . exec -- exec query
		try (ResultSet rst = pst2.executeQuery()) {
			while (rst.next())
				users.add(new User(rst.getInt(1), rst.getString(2), rst.getString(3), rst.getString(4),
						rst.getString(5), rst.getDate(7), rst.getDouble(6), rst.getString(8)));
		}
		System.out.println(users);
		return users;// dao rets populated list of users to the caller
	}

	@Override
	public String voterRegistration(User newVoter) throws SQLException {
		// 1. set IN params
		/*
		 * int userId, String firstName, String lastName, String email, String password,
		 * Date dob, boolean status, String role
		 */
		pst3.setString(1, newVoter.getFirstName());
		pst3.setString(2, newVoter.getLastName());
		pst3.setString(3, newVoter.getEmail());
		pst3.setString(4, newVoter.getPassword());
		pst3.setDouble(5, newVoter.getReg_amt());
		pst3.setDate(6, (Date) newVoter.getDob());
		pst3.setString(7, newVoter.getRole());
		// exec : executeUpdate
		int rows = pst3.executeUpdate();
		if (rows == 1)
			return "Voter registered....";
		return "Voter registration failed !";
	}

	// add clean up method to close DB resources
	public void cleanUp() throws SQLException {
		System.out.println("user dao cleaned up");
		if (pst1 != null)
			pst1.close();
		if (pst2 != null)
			pst2.close();
		if (pst3 != null)
			pst3.close();
		if (pst4 != null)
			pst4.close();
		if (pst5 != null)
			pst5.close();
		closeConnection();
	}

	@Override
	public String voterUpdatePassword(String email, String oldpassword, String newpassword) throws SQLException {
		// TODO Auto-generated method stub
		pst4.setString(1, newpassword);
		pst4.setString(2, email);
		pst4.setString(3, oldpassword);
		int rows = pst4.executeUpdate();
		if (rows == 1)
			return "Password Update !!";
		return "Invalid user";
	}

	@Override
	public int DeleteVoter(int id) throws SQLException {
		pst5.setInt(1, id);
		int rows = pst5.executeUpdate();
		if (rows > 0)
			return 1;
		return -1;
	}

	@Override
	public void addUser(User user) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO users (first_name, last_name, email, password, reg_amt, reg_date, role) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement stmt = cn.prepareStatement(sql)) {
			stmt.setString(1, user.getFirstName());
			stmt.setString(2, user.getLastName());
			stmt.setString(3, user.getEmail());
			stmt.setString(4, user.getPassword());
			stmt.setDouble(5, user.getReg_amt());
			stmt.setDate(6, new Date(user.getDob().getTime()));
			stmt.setString(7, user.getRole());

			stmt.executeUpdate();
		}
	}

}