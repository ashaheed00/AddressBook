package com.bl.addressbook;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.bl.addressbook.exception.AddressBookDBException;
import com.bl.addressbook.services.AddressBookServices;
import com.google.gson.Gson;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class AddressBookJsonServerTest {

	@Before
	public void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 3000;
	}

	private List<Contact> getAddressBook() {
		Response response = RestAssured.get("/addressbook");
		Contact[] addresbook = new Gson().fromJson(response.asString(), Contact[].class);
		return new ArrayList<>(Arrays.asList(addresbook));
	}

	@Test
	public void givenAddressBook_WhenRetrieved_ShouldMatchTheCount() {
		List<Contact> contactList = getAddressBook();
		assertEquals(5, contactList.size());
	}

	private Response addContactToJSONServer(Contact contact) {
		String empJson = new Gson().toJson(contact);
		RequestSpecification requestSpecification = RestAssured.given();
		requestSpecification.header("Content-Type", "application/json");
		requestSpecification.body(empJson);
		return requestSpecification.post("/addressbook");
	}

	@Test
	public void givenMultipleContactData_WhenAdded_ShouldMatchTheCountAndStatusCode() {
		List<Contact> contacts = new ArrayList<>() {
			{
				add(new Contact(6, "Dev", "Patel", "68/1 Aura Complex", "Ernakulam", "Kerala", "682011", "8725120000",
						"dev@person.com"));
				add(new Contact(7, "Josh", "Smith", "68/1 Bristi Complex", "Aluva", "Kerala", "683022", "8725120022",
						"josh@person.com"));
			}
		};
		for (Contact contact : contacts) {
			Response response = addContactToJSONServer(contact);
			assertEquals(201, response.statusCode());
		}
	}

	private Response updateContactData(Contact contact) {
		String contactJson = new Gson().toJson(contact);
		RequestSpecification requestSpecification = RestAssured.given();
		requestSpecification.header("Content-Type", "application/json");
		requestSpecification.body(contactJson);
		return requestSpecification.put("/addressbook/" + contact.getId());
	}

	@Test
	public void givenNewCityForContact_WhenUpdated_ShouldMatchStatusCode() throws AddressBookDBException {
		AddressBookServices addressBookServices = new AddressBookServices(getAddressBook());
		addressBookServices.updateCity("Aditi", "Mumbai");
		Contact contact = addressBookServices.getContactByName("Aditi");
		Response response = updateContactData(contact);
		assertEquals(200, response.getStatusCode());
	}

	private Response deleteContact(Contact contact) {
		String contactJson = new Gson().toJson(contact);
		RequestSpecification requestSpecification = RestAssured.given();
		requestSpecification.header("Content-Type", "application/json");
		requestSpecification.body(contactJson);
		return requestSpecification.delete("/addressbook/" + contact.getId());
	}

	@Test
	public void givenEmployeeId_WhenDelelted_ShouldMatchStatusCodeAndCount() {
		AddressBookServices addressBookServices = new AddressBookServices(getAddressBook());
		Contact contact = addressBookServices.getContactByName("Dev");
		addressBookServices.removeContact("Dev");
		Response response = deleteContact(contact);
		assertEquals(200, response.getStatusCode());
	}

}
