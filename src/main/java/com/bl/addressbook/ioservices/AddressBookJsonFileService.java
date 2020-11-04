package com.bl.addressbook.ioservices;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.bl.addressbook.Contact;
import com.bl.addressbook.services.IOType;
import com.google.gson.Gson;

public class AddressBookJsonFileService implements IOServices {
	public Path jsonFilePath;
	private List<Contact> contactList;

	public AddressBookJsonFileService(Path jsonFilePath) {
		this.jsonFilePath = jsonFilePath;
		try {
			contactList = readData();
		} catch (IOException e) {
		}
	}

	public void writeData(List<Contact> addressBook) throws IOException {

		try (Writer writer = Files.newBufferedWriter(jsonFilePath)) {
			Gson gson = new Gson();
			String json = gson.toJson(addressBook);
			writer.write(json);
		}
	}

	public int updateContact(String firstName, String column, String columnValue) {
		try {
			if (column.toLowerCase().equals("city")) {
				contactList.stream().filter(e -> e.getFirstName().equals(firstName)).findFirst().orElse(null)
						.setCity(columnValue);
				writeData(contactList);
				return 1;
			}
			if (column.toLowerCase().equals("state")) {
				contactList.stream().filter(e -> e.getFirstName().equals(firstName)).findFirst().orElse(null)
						.setState(columnValue);
				writeData(contactList);
				return 1;
			}
			return 0;
		} catch (NullPointerException | IOException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public List<Contact> readData() throws IOException {
		try (Reader reader = Files.newBufferedReader(jsonFilePath)) {
			Gson gson = new Gson();
			Contact[] contactArray = gson.fromJson(reader, Contact[].class);
			contactList = new ArrayList<Contact>(Arrays.asList(contactArray));
			return contactList;
		}
	}

	public List<Contact> getContactsByCity(String cityName) {
		return contactList.stream().filter(contact -> contact.getCity().equals(cityName)).collect(Collectors.toList());
	}

	public List<Contact> getContactsByState(String stateName) {
		return contactList.stream().filter(contact -> contact.getState().equals(stateName))
				.collect(Collectors.toList());
	}

	public Map<String, List<Contact>> getContactsByCity() {
		Map<String, List<Contact>> personsByCityMap = new HashMap<String, List<Contact>>();
		contactList.stream()
				.forEach(person -> personsByCityMap.put(person.getCity(), getContactsByCity(person.getCity())));
		return personsByCityMap;
	}

	public Map<String, List<Contact>> getContactsByState(IOType ioType) {
		Map<String, List<Contact>> contactsByStateMap = new HashMap<String, List<Contact>>();
		contactList.stream()
				.forEach(contact -> contactsByStateMap.put(contact.getState(), getContactsByState(contact.getState())));
		return contactsByStateMap;
	}
}
