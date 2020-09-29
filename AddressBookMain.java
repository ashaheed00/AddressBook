
// Version UC8

import java.util.Scanner;

public class AddressBookMain {

	private static Scanner sc = new Scanner(System.in);

	private static void operations(AddressBook addressBook) {
		System.out.println("Add new contact: type 1" + "\nEdit someone's details: type 2" + "\nDelete a contact: type 3"
				+ "\nGo Back to Main Menu" + "\nSearch by city: type 5" + "\nSearch by state: type 6"
				+ "\nExit the procces: type 0");
		System.out.print("Enter operation code: ");
		String option = sc.next();
		switch (option) {
		case "1":
			addressBook.addNewContact();
			break;
		case "2":
			System.out.println("Enter the person's name you want to edit: ");
			addressBook.editContact(sc.next(), sc.next());
			break;
		case "3":
			System.out.println("Enter the person's name to delete his/her details: ");
			addressBook.deleteContact(sc.next(), sc.next());
			break;
		case "4":
			mainMenu();
			break;
		case "5":
			System.out.println("Enter the city name to search: ");
			addressBook.viewPersonsByCity(sc.next());
			break;
		case "6":
			System.out.println("Enter the state name to search: ");
			addressBook.viewPersonsByState(sc.next());
			break;
		case "0":
			System.out.println("Exiting...");
			return;
		default:
			System.out.println("Wrong option entered");
		}
		operations(addressBook);
	}

	private static void mainMenu() {
		System.out.println("**Main Menu**" + "\nSelect Addressbook: Press 1" + "\nExit: Press 0");
		String menu = sc.next();
		switch (menu) {
		case "1":
			System.out.println("Availbale addressbooks => " + AddressBook.addressBookList.keySet()
					+ "\nEnter addressbook name properly: ");
			String addressBookName = sc.next();
			if (AddressBook.addressBookList.containsKey(addressBookName))
				operations(AddressBook.addressBookList.get(addressBookName));
			else
				System.out.println("Wrong address book name. Try again.");
			break;
		case "0":
			System.out.println("Exiting...");
			return;
		default:
			System.out.println("Wrong menu code entry. Try again.");
		}
		mainMenu();
	}

	public static void main(String[] args) {
		System.out.println("Welcome to Address Book Program!");

		// Creating two distinct address books
		AddressBook addressBookA = new AddressBook("addressBookA");
		AddressBook addressBookB = new AddressBook("addressBookB");
		// Entering into to main menu
		mainMenu();
		// checking whether my operations worked correctly or not
		System.out.println("addressBookA after all the operations => \n" + addressBookA.getContactList());
		System.out.println("addressBookB after all the operations => \n" + addressBookB.getContactList());
	}
}