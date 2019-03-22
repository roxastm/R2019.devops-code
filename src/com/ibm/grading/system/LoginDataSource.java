package com.ibm.grading.system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDataSource {

	Connection cn;

	private final String USERNAME = "root";
	private final String PASSWORD = "123456";
	private final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/grading_db";
	private final String TABLE_NAME = "account_table";
	private final String TABLE_USER = "user_table";

	private final String USERNAME_COLUMN = "username";
	private final String PASSWORD_COLUMN = "password";
	private final String NAME_COLUMN = "name";
	private final String ROLE_COLUMN = "role";
	private final String USER_ID_COLUMN = "user_id";

	private final String ACCOUNT_ID_COLUMN = "account_id";

	private final String VALIDATE_ACCOUNT_QUERY = "SELECT * FROM " + TABLE_NAME + " account INNER JOIN " + TABLE_USER
			+ " user ON account." + ACCOUNT_ID_COLUMN + " = user." + ACCOUNT_ID_COLUMN + " WHERE " + USERNAME_COLUMN
			+ " = ? AND " + PASSWORD_COLUMN + " = ? ";

	private PreparedStatement validateAccountPs;

	public boolean open() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			cn = DriverManager.getConnection(CONNECTION_STRING, USERNAME, PASSWORD);
			validateAccountPs = cn.prepareStatement(VALIDATE_ACCOUNT_QUERY);

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("Can't Connect: " + e.getMessage());
		}
		return false;
	}

	public void close() {
		try {
			if (cn != null) {
				cn.close();
			}
			if (validateAccountPs != null) {
				validateAccountPs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public User login(Account account) {

		ResultSet rs = null;
		try {

			validateAccountPs.setString(1, account.getUsername());
			validateAccountPs.setString(2, account.getPassword());
			rs = validateAccountPs.executeQuery();

			if (rs.next()) {
				User user = new User();
				user.setName(rs.getString(NAME_COLUMN));
				user.setUserId(rs.getString(USER_ID_COLUMN));
				user.setRole(rs.getString(ROLE_COLUMN));

				return user;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
}
