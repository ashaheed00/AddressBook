package com.bl.addressbook;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.bl.addressbook.exception.AddressBookDBException;
import com.bl.addressbook.services.AddressBookServices;
import com.bl.addressbook.services.IOType;

public class AddressBookJDBCServicesTest {
	AddressBookServices addressBookServices;

	@Before
	public void initialize() {
		addressBookServices = new AddressBookServices();
	}

	@Ignore
	@Test
	public void givenAddressBookData_WhenRetrieved_ShouldMatchContactCount() {
		List<Contact> contactList = addressBookServices.getContacts(IOType.DB_IO);
		assertEquals(4, contactList.size());
	}

	@Ignore
	@Test
	public void givenName_WhenUpdatedContactInfo_ShouldSyncWithDB() throws AddressBookDBException {
		addressBookServices.updateCity(IOType.DB_IO, "Aditi", "Pune");
		boolean isSynced = addressBookServices.isAddressBookSynced(IOType.DB_IO, "Aditi");
		assertTrue(isSynced);
	}

	@Ignore
	@Test
	public void givenDateRange_WhenRetrievedContactInfo_ShouldMatchCount() throws AddressBookDBException {
		LocalDate startDate = LocalDate.of(2019, 01, 01);
		LocalDate endDate = LocalDate.now();
		List<Contact> contactList = addressBookServices.getContactsForDateRange(startDate, endDate);
		assertEquals(2, contactList.size());
	}

	@Ignore
	@Test
	public void givenAddressBookData_WhenRetrievedByCity_ShouldMatchContactCount() {
		List<Contact> contactList = addressBookServices.getContactsByCity(IOType.DB_IO, "Kolkata");
		assertEquals(1, contactList.size());
	}

	@Ignore
	@Test
	public void givenAddressBookData_WhenRetrievedByState_ShouldMatchContactCount() {
		List<Contact> contactList = addressBookServices.getContactsByState(IOType.DB_IO, "West Bengal");
		assertEquals(2, contactList.size());
	}

	@Ignore
	@Test
	public void givenContactData_WhenAddedToDB_ShouldSyncWithDB() throws AddressBookDBException {
		addressBookServices.addNewContact("2018-08-08", "Trisha", "Krishnan", "68/1 Srishti Complex", "Ernakulam",
				"Kerala", "682011", "8725120000", "trisha@person.com");
		boolean isSynced = addressBookServices.isAddressBookSynced(IOType.DB_IO, "Trisha");
		assertTrue(isSynced);
	}

	@Ignore
	@Test
	public void givenMultipeContacts_WhenAddedToDBWithMultiThreads_ShouldSyncWithDB() throws AddressBookDBException {
		List<Contact> contacts = new ArrayList<>() {
			{
				add(new Contact("Trisha", "Krishnan", "68/1 Srishti Complex", "Ernakulam", "Kerala", "682011",
						"8725120000", "trisha@person.com"));
				add(new Contact("Faizal", "Ahmed", "68/1 Beauty Complex", "Aluva", "Kerala", "683022", "8725120022",
						"faizal@person.com"));
			}
		};
		addressBookServices.addContacts(IOType.DB_IO, contacts);
		assertEquals(6, addressBookServices.getContacts(IOType.DB_IO).size());
	}
}
