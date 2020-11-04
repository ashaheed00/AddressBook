package com.bl.addressbook.services;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.bl.addressbook.Contact;
import com.bl.addressbook.exception.AddressBookDBException;
import com.bl.addressbook.ioservices.AddressBookJDBCServices;
import com.bl.addressbook.ioservices.IOServices;

public class AddressBookServices {
	private List<Contact> contactList;
	private AddressBookJDBCServices addressBookJDBCServices;
	private Path filePath;

	public AddressBookServices(Path filePath) {
		this.filePath = filePath;
	}

	public AddressBookServices(List<Contact> contactList) {
		this.contactList = contactList;
	}

	public AddressBookServices() {
		// TODO Auto-generated constructor stub
	}

	public List<Contact> getContacts(IOType ioType) {
		IOServices ioService = IOObjectBuilder.getIOObject(ioType, filePath);
		try {
			contactList = ioService.readData();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return contactList;
	}

	public void addContacts(IOType ioType, List<Contact> contacts) {
		IOServices ioService = IOObjectBuilder.getIOObject(ioType, filePath);
		try {
			getContacts(ioType);
			contactList.addAll(contacts);
			ioService.writeData(contacts);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Contact> getContactsForDateRange(LocalDate startDate, LocalDate endDate) {
		addressBookJDBCServices = AddressBookJDBCServices.getInstance();
		return addressBookJDBCServices.getContactForDateRange(startDate, endDate);
	}

	public List<Contact> getContactsByCity(IOType ioType, String cityName) {
		IOServices ioService = IOObjectBuilder.getIOObject(ioType, filePath);
		return ioService.getContactsByCity(cityName);
	}

	public List<Contact> getContactsByState(IOType ioType, String stateName) {
		IOServices ioService = IOObjectBuilder.getIOObject(ioType, filePath);
		return ioService.getContactsByState(stateName);
	}

	public Map<String, List<Contact>> getContactsByCity(IOType ioType) {
		Map<String, List<Contact>> contactsByCityMap = new HashMap<String, List<Contact>>();
		contactList.stream().forEach(
				contact -> contactsByCityMap.put(contact.getState(), getContactsByCity(ioType, contact.getState())));
		return contactsByCityMap;
	}

	public Map<String, List<Contact>> getContactsByState(IOType ioType) {
		Map<String, List<Contact>> contactsByStateMap = new HashMap<String, List<Contact>>();
		contactList.stream().forEach(
				contact -> contactsByStateMap.put(contact.getState(), getContactsByState(ioType, contact.getState())));
		return contactsByStateMap;
	}

	public Contact addNewContact(String date, String firstName, String lastName, String address, String city,
			String state, String zip, String phoneNo, String email) throws AddressBookDBException {
		return addressBookJDBCServices.insertNewContactToDB(date, firstName, lastName, address, city, state, zip,
				phoneNo, email);
	}

	public void addNewMultipleContacts(List<Contact> contacts) throws AddressBookDBException {
		Map<Integer, Boolean> status = new HashMap<>();
		contacts.forEach(contact -> {
			status.put(contact.hashCode(), false);
			Runnable task = () -> {
				try {
					addressBookJDBCServices.insertNewContactToDB("2020-10-30", contact.getFirstName(),
							contact.getLastName(), contact.getAddress(), contact.getCity(), contact.getState(),
							contact.getZip(), contact.getPhoneNo(), contact.getEmail());
					status.put(contact.hashCode(), true);
				} catch (AddressBookDBException e) {
				}
			};
			Thread thread = new Thread(task, contact.getFirstName());
			thread.start();
		});
		while (status.containsValue(false))
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
	}

	public void updateCity(IOType ioType, String firstName, String city) throws AddressBookDBException {
		IOServices ioService = IOObjectBuilder.getIOObject(ioType, filePath);
		int result = ioService.updateContact(firstName, "city", city);
		if (result == 1)
			contactList = getContacts(ioType);
	}

	public void updateCity(String firstName, String city) throws AddressBookDBException {
		contactList.stream().filter(contact -> contact.getFirstName().equals(firstName)).findFirst().orElse(null)
				.setCity(city);
	}

	public void updateState(IOType ioType, String firstName, String state) throws AddressBookDBException {
		IOServices ioService = IOObjectBuilder.getIOObject(ioType, filePath);
		int result = 0;
		if (ioType == IOType.DB_IO)
			result = addressBookJDBCServices.updateContactUsingSQL(firstName, "state", state);
		if (ioType == IOType.JSON_IO)
			result = ioService.updateContact(firstName, "state", state);
		if (result == 1)
			contactList = getContacts(ioType);
	}

	public boolean isAddressBookSynced(IOType ioType, String firstName) {
		Contact contact = getContactData(ioType, firstName);
		return getContacts(ioType).contains(contact);
	}

	public Contact getContactByName(String name) {
		return contactList.stream().filter(e -> e.getFirstName().equals(name)).findFirst().orElse(null);
	}

	private Contact getContactData(IOType ioType, String name) {
		getContacts(ioType);
		return contactList.stream().filter(e -> e.getFirstName().equals(name)).findFirst().orElse(null);
	}

	public Map<String, Integer> countByState(IOType ioType) {
		Map<String, Integer> stateMap = new HashMap<String, Integer>();
		Map<String, List<Contact>> contactsByState = getContactsByState(ioType);
		Set<String> states = contactsByState.keySet();
		states.stream().forEach(state -> stateMap.put(state, contactsByState.get(state).size()));
		return stateMap;
	}

	public Map<String, Integer> countByCity(IOType ioType) {
		Map<String, Integer> cityMap = new HashMap<String, Integer>();
		Map<String, List<Contact>> contactsByCity = getContactsByState(ioType);
		Set<String> cities = contactsByCity.keySet();
		cities.stream().forEach(city -> cityMap.put(city, contactsByCity.get(city).size()));
		return cityMap;
	}

	public void removeContact(String firstName) {
		Contact contact = contactList.stream().filter(e -> e.getFirstName().equals(firstName)).findFirst().orElse(null);
		contactList.remove(contact);
	}
}
