package com.bl.addressbook;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.bl.addressbook.AddressBookService.IOService;
import com.bl.addressbook.exception.AddressBookDBException;

public class AddressBookJDBCServiceTest {
	AddressBookService addressBookService;

	@Before
	public void initialize() {
		addressBookService = new AddressBookService();
	}

	@Test
	public void givenAddressBookData_WhenRetrieved_ShouldMatchContactCount() {
		List<Contact> contactList = addressBookService.readContactData(IOService.DB_IO);
		assertEquals(4, contactList.size());
	}

	@Test
	public void givenName_WhenUpdatedContactInfo_ShouldSyncWithDB() throws AddressBookDBException {
		addressBookService.updateCity("Aditi", "Pune");
		boolean isSynced = addressBookService.isAddressBookSyncedWithDB("Aditi");
		assertTrue(isSynced);
	}
}
