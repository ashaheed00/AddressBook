
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class AddressBook {

	static Scanner in = new Scanner(System.in);
	private List<Contact> contactList = new ArrayList<>();
	private Map<String, Contact> contactMap = new HashMap<>();
	static Map<String, AddressBook> addressBookList = new HashMap<>();

	public AddressBook(String objectName) {
		addressBookList.put(objectName, this);
	}

	public List<Contact> getContactList() {
		return contactList;
	}

	public Map<String, Contact> getContactMap() {
		return contactMap;
	}

	public void addNewContact() {
		Contact person = new Contact();
		System.out.print("First Name: ");
		person.setFirstName(in.next());
		System.out.print("Last Name: ");
		person.setLastName(in.next());
		if (contactMap.containsKey(person.getName())) {
			System.out.println("Duplicate Name.");
			return;
		}
		System.out.print("Address: ");
		person.setAddress(in.next());
		System.out.print("City: ");
		person.setCity(in.next());
		System.out.print("State: ");
		person.setState(in.next());
		System.out.print("Zip: ");
		person.setZip(in.next());
		System.out.print("Phone Number: ");
		person.setPhoneNo(in.next());
		System.out.print("Email: ");
		person.setEmail(in.next());

		contactList.forEach(other -> {
			if (other.equals(person)) {
				System.out.println("Duplicate details.");
				return;
			}
		});

		contactList.add(person);
		contactMap.put(person.getName(), person);
	}

	public void editContact(String firstName, String lastName) {
		String name = (firstName + " " + lastName).toLowerCase();

		if (contactMap.containsKey(name)) {
			System.out.println("What do you want to edit?");
			String s = in.next().toLowerCase();
			switch (s) {
			case ("address"):
				System.out.print("Enter new address: ");
				contactMap.get(name).setAddress(in.next());
				break;
			case ("city"):
				System.out.print("Enter new city: ");
				contactMap.get(name).setCity(in.next());
				break;
			case ("state"):
				System.out.print("Enter new state: ");
				contactMap.get(name).setState(in.next());
				break;
			case ("zip"):
				System.out.print("Enter zip: ");
				contactMap.get(name).setZip(in.next());
				break;
			case ("phoneno"):
				System.out.print("Enter phone no: ");
				contactMap.get(name).setPhoneNo(in.next());
				break;
			case ("email"):
				System.out.print("Enter new email: ");
				contactMap.get(name).setEmail(in.next());
				break;
			}

		} else
			System.out.println(name.toUpperCase() + " is not present in the Address Book.");
	}

	public void deleteContact(String firstName, String lastName) {
		String name = (firstName + " " + lastName).toLowerCase();
		if (contactMap.containsKey(name)) {
			System.out.println("Type 'Y'to confirm, else type anything.");
			if (in.next().toUpperCase().equals("Y"))
				contactMap.remove(name);
			else
				return;
		} else {
			System.out.println(name.toUpperCase() + " is not present in the Address Book.");
		}
	}


	// Search by city and state
	public ArrayList<Contact> searchPersonsByCity(String city) {
		ArrayList<Contact> personsByCity = new ArrayList<>();
		contactList.forEach(person -> {
			if (person.getCity().equals(city))
				personsByCity.add(person);
		});
		return personsByCity;
	}

	public ArrayList<Contact> searchPersonsByState(String state) {
		ArrayList<Contact> personsByState = new ArrayList<>();
		contactList.forEach(person -> {
			if (person.getState().equals(state))
				personsByState.add(person);
		});
		return personsByState;
	}


	// View whole address book grouped by city or state
	private Map<String, ArrayList<Contact>> personsByCityMap = new TreeMap<>();

	public Map<String, ArrayList<Contact>> viewPersonsByCity() {
		contactList
				.forEach(person ->
				personsByCityMap.put(person.getCity(), searchPersonsByCity(person.getCity())));
		return personsByCityMap;
	}

	private Map<String, ArrayList<Contact>> personsByStateMap = new TreeMap<>();

	public Map<String, ArrayList<Contact>> viewPersonsByState() {
		contactList
				.forEach(person ->
				personsByStateMap.put(person.getState(), searchPersonsByState(person.getState())));
		return personsByStateMap;
	}
	

	// Counting by city and state
	public int countByCity(String city) {
		return viewPersonsByCity().get(city).size();
	}

	public int countByState(String state) {
		return viewPersonsByState().get(state).size();
	}
}