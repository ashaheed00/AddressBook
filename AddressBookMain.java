UC2:

// Version UC2

import java.util.Scanner;
import java.util.Map;
import java.util.TreeMap;

public class AddressBookMain {

	private String firstName, lastName, name;
	private String address, city, state, zip;
	private String phoneNumber, email;

	public AddressBookMain() {
	}

	public AddressBookMain(String firstName, String lastName, String address, String city, String state, String zip,
			String phoneNumber, String email) {

		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.name = (firstName + " " + lastName).toUpperCase();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	private Map<String, AddressBookMain> contacts = new TreeMap<>();

	public void addThisContact(AddressBookMain person) {
		person.name = (firstName + " " + lastName).toUpperCase();
		contacts.put(name, person);
	}

	public void showAddressBook() {
		for (String key : contacts.keySet())
			System.out.println(contacts.get(key));
	}

	public void addNewContact() {

		AddressBookMain person = new AddressBookMain();
		System.out.println("First Name:");
		person.setFirstName(sc.next());
		System.out.println("Last Name:");
		person.setLastName(sc.next());
		System.out.println("Address:");
		person.setAddress(sc.next());
		System.out.println("City:");
		person.setCity(sc.next());
		System.out.println("State:");
		person.setCity(sc.next());
		System.out.println("Zip:");
		person.setZip(sc.next());
		System.out.println("Phone Number:");
		person.setPhoneNumber(sc.next());
		System.out.println("Email:");
		person.setEmail(sc.next());

String name = (person.getFirstName() + " " + person.getLastName()).toUpperCase();
	    	contacts.put(name, person);


	}

	@Override
	public String toString() {
		return "[firstName=" + firstName + ", lastName=" + lastName + ", address=" + address + ", city=" + city
				+ ", state=" + state + ", zip=" + zip + ", phoneNumber=" + phoneNumber + ", email=" + email + "]";
	}

	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.println("Welcome to Address Book Program!");

		System.out.println("Adding contact using field constructor...");
		AddressBookMain adbk = new AddressBookMain(sc.next(), sc.next(), sc.next(), sc.next(), sc.next(), sc.next(),
				sc.next(), sc.next());
		adbk.addThisContact(person1);
		// Using two different methods to add contacts
		// above one uses constructor and below one uses setters
		adbk.addNewContact();
		adbk.showAddressBook();
		sc.close();
	}

}
