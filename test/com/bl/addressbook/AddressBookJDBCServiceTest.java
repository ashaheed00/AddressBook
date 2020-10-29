package com.bl.addressbook;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class AddressBookJDBCServiceTest {
	AddressBookJDBCServices addressBookJDBCServices;

	@Before
	public void initialize() {
		addressBookJDBCServices = new AddressBookJDBCServices();
	}

	@Test
	public void givenAddressBookData_WhenRetrieved_ShouldMatchContactCount() {
		List<Contact> contactList = addressBookJDBCServices.readData();
		assertEquals(4, contactList.size());
	}
}
