import java.util.Scanner;

public class AddressBook {
	Contact person = new Contact();
	static Scanner in = new Scanner(System.in);

	public AddressBook() {
	}

	public void addNewContact() {
		System.out.print("First Name: ");
		person.setFirstName(in.next());
		System.out.print("Last Name: ");
		person.setLastName(in.next());
		System.out.print("Address: ");
		person.setAddress(in.next());
		System.out.print("City: ");
		person.setCity(in.next());
		System.out.print("State: ");
		person.setState(in.next());
		System.out.print("Zip: ");
		person.setZip(in.next());
		System.out.println("Phone Number: ");
		person.setPhoneNo(in.next());
		System.out.println("Email: ");
		person.setEmail(in.next());
	}
}