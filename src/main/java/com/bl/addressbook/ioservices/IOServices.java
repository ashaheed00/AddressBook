package com.bl.addressbook.ioservices;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.bl.addressbook.Contact;

public interface IOServices {
	public void writeData(List<Contact> addressBook) throws IOException;

	public List<Contact> readData() throws IOException;

	public int updateContact(String firstName, String column, String columnValue);

	public List<Contact> getContactsByCity(String cityName);

	public List<Contact> getContactsByState(String stateName);
}
