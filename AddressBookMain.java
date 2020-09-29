// Version UC6

import java.util.Scanner;

public class AddressBookMain {

	private static Scanner sc = new Scanner(System.in);

<<<<<<< HEAD
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
		person.name = (person.getFirstName() + " " + person.getLastName()).toUpperCase();
		contacts.put(name, person);
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
		System.out.println("Thank you. Data is collected.");

		String name = (person.getFirstName() + " " + person.getLastName()).toUpperCase();
		contacts.put(name, person);
	}

	public void showAddressBook() {
		for (String key : contacts.keySet())
			System.out.println(contacts.get(key));
	}

	public void editContact(String fullName) {
		String key = fullName.toUpperCase();
		System.out.println("What do you want to edit?");
		String element = sc.next().toUpperCase();
		switch (element) {
		case "ADDRESS":
			contacts.get(key).setAddress(sc.next());
			break;
		case "CITY":
			contacts.get(key).setCity(sc.next());
=======
	private static void operations(AddressBook addressBook) {
		String menu = sc.next();
		switch (menu) {
		case "1":
			addressBook.addNewContact();
>>>>>>> UC5
			break;
		case "2":
			System.out.println("Enter the person's name you want to edit: ");
			addressBook.editContact(sc.next(), sc.next());
			break;
		case "3":
			System.out.println("Enter the person's name to delete his/her details: ");
			addressBook.deleteContact(sc.next(), sc.next());
			break;
		case "0":
			System.out.println("Exiting...");
			return;
		default:
			System.out.println("Wrong option chosen");
		}
		System.out.print("Enter next operation: ");
		operations(addressBook);
	}
<<<<<<< HEAD

	public void deleteContact(String fullName) {
		contacts.remove(fullName.toUpperCase());
	}

	@Override
	public String toString() {
		return "[firstName=" + firstName + ", lastName=" + lastName + ", address=" + address + ", city=" + city
				+ ", state=" + state + ", zip=" + zip + ", phoneNumber=" + phoneNumber + ", email=" + email + "]";
	}

	static Scanner sc = new Scanner(System.in);
=======
>>>>>>> UC5

	public static void main(String[] args) {
		System.out.println("Welcome to Address Book Program!");

<<<<<<< HEAD
		AddressBookMain addressBook1 = new AddressBookMain();
		// Person 1
		addressBook1.addNewContact();
		// Person 2
		addressBook1.addNewContact();

		AddressBookMain addressBook2 = new AddressBookMain();
		// Person 1
		addressBook2.addNewContact();
		// Person 2
		addressBook2.addNewContact();

		// Showing two different address book... 
		//Each having different contact details
		addressBook1.showAddressBook();
		addressBook2.showAddressBook();

		sc.close();
=======
		AddressBook addressBook = new AddressBook();
		System.out.println("Add new contact: type 1" + "\nEdit someone's details: type 2" + "\nDelete a contact: type 3"
				+ "\nExit the procces: type 0");
		operations(addressBook);
>>>>>>> UC5
	}
}