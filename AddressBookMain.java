// Version UC2

public class AddressBookMain {

	public static void main(String[] args) {
		System.out.println("Welcome to Address Book Program!");

		System.out.println("Add new contact using field constructor...");
		AddressBook addressBook = new AddressBook();
		addressBook.addNewContact();
	}
}