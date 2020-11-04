package com.bl.addressbook;

import static org.junit.Assert.*;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.bl.addressbook.exception.AddressBookDBException;
import com.bl.addressbook.services.AddressBookServices;
import com.bl.addressbook.services.IOType;

public class AddressBookJsonServicesTest {
	private final String ADDRESS_BOOK_JSON = "C:/Users/user/eclipse-workspace/AddressBook/AddressBookJSON/TeamMembers.json";
	private AddressBookServices addressBookServices;
	private List<Contact> addressBook;

	@Before
	public void init() {
		addressBookServices = new AddressBookServices(Paths.get(ADDRESS_BOOK_JSON));
		addressBook = addressBookServices.getContacts(IOType.JSON_IO);
	}

	@Test
	public void givenAddressBookData_WhenRetrieved_ShouldMatchContactCount() {
		System.out.println(addressBookServices.getContacts(IOType.JSON_IO));
		assertEquals(4, addressBook.size());
	}

	@Test
	public void givenName_WhenUpdatedContactInfo_ShouldSyncWithDB() throws AddressBookDBException {
		addressBookServices.updateCity(IOType.JSON_IO, "Aditi", "Mumbai");
		addressBookServices.updateState(IOType.JSON_IO, "Aditi", "Maharashtra");
		boolean isSynced = addressBookServices.isAddressBookSynced(IOType.JSON_IO, "Aditi");
		assertTrue(isSynced);
	}

	@Test
	public void givenAddressBookData_WhenRetrievedByCity_ShouldMatchContactCount() {
		List<Contact> contactList = addressBookServices.getContactsByCity(IOType.JSON_IO, "Kolkata");
		assertEquals(1, contactList.size());
	}

	@Test
	public void givenAddressBookData_WhenRetrievedByState_ShouldMatchContactCount() {
		addressBookServices = new AddressBookServices(Paths.get(ADDRESS_BOOK_JSON));
		List<Contact> contactList = addressBookServices.getContactsByState(IOType.JSON_IO, "Kerala");
		assertEquals(2, contactList.size());
	}

	@Ignore
	@Test
	public void givenMultipeContacts_WhenAdded_ShouldSyncJsonFile() throws AddressBookDBException {
		List<Contact> contacts = new ArrayList<>();
		contacts.add(new Contact(3, "Trisha", "Krishnan", "68/1 Srishti Complex", "Ernakulam", "Kerala", "682011",
				"8725120000", "trisha@person.com"));
		contacts.add(new Contact(4, "Faizal", "Ahmed", "68/1 Beauty Complex", "Aluva", "Kerala", "683022", "8725120022",
				"faizal@person.com"));
		addressBookServices.addContacts(IOType.JSON_IO, contacts);
		assertEquals(4, addressBookServices.getContacts(IOType.JSON_IO).size());
	}
}
