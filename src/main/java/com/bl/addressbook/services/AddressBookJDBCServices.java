package com.bl.addressbook.services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.bl.addressbook.Contact;
import com.bl.addressbook.exception.AddressBookDBException;

public class AddressBookJDBCServices {

	private PreparedStatement contactPreparedStatement;
	private static AddressBookJDBCServices addressBookJDBCServices;

	private AddressBookJDBCServices() {
	}

	public static AddressBookJDBCServices getInstance() {
		if (addressBookJDBCServices == null)
			addressBookJDBCServices = new AddressBookJDBCServices();
		return addressBookJDBCServices;
	}

	private synchronized Connection getConnection() throws SQLException {
		String jdbcURL = "jdbc:mysql://localhost:3306/address_book_service?useSSL=false";
		String userName = "root";
		String password = "my964sql!!";
		return DriverManager.getConnection(jdbcURL, userName, password);
	}

	public List<Contact> readData() {
		String sql = String.format("select * from addressbook");
		return getContactList(sql);
	}

	public List<Contact> getContacts(String firstName) {
		String sql = String.format("select * from addressbook where first_name = '%s'", firstName);
		return getContactList(sql);
	}

	public List<Contact> getContactForDateRange(LocalDate startDate, LocalDate endDate) {
		String sql = String.format("SELECT * FROM addressbook WHERE date_added BETWEEN '%s' AND '%s';",
				Date.valueOf(startDate), Date.valueOf(endDate));
		return getContactList(sql);
	}

	public List<Contact> getContactByField(String columName, String columnValue) {
		String sql = String.format("SELECT * FROM addressbook WHERE %s = '%s';", columName, columnValue);
		return getContactList(sql);
	}

	public int updateContactUsingSQL(String firstName, String column, String columnValue) {
		String sql = String.format("UPDATE addressbook SET %s = '%s'  WHERE first_name = '%s';", column, columnValue,
				firstName);
		try (Connection connection = getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public Contact insertNewContactToDB(String date, String firstName, String lastName, String address, String city,
			String state, String zip, String phoneNo, String email) throws AddressBookDBException {
		String sql = String.format(
				"INSERT INTO addressbook (date_added,first_name,last_name,address,city,state,zip,phone_number,email) VALUES ('%s','%s','%s','%s','%s','%s','%s','%s','%s');",
				date, firstName, lastName, address, city, state, zip, phoneNo, email);
		Contact contact = null;
		try (Connection connection = getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			int result = preparedStatement.executeUpdate();
			if (result == 1)
				contact = new Contact(firstName, lastName, address, city, state, zip, phoneNo, email);
		} catch (SQLException e) {
			throw new AddressBookDBException("Wrong SQL or field given",
					AddressBookDBException.ExceptionType.WRONG_SQL);
		}
		return contact;
	}

	private List<Contact> getContactList(String sql) {
		List<Contact> contactList = new ArrayList<>();
		try (Connection connection = getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				contactList.add(new Contact(resultSet.getString("first_name"), resultSet.getString("last_name"),
						resultSet.getString("address"), resultSet.getString("city"), resultSet.getString("state"),
						resultSet.getString("zip"), resultSet.getString("phone_number"), resultSet.getString("email")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return contactList;
	}

}
