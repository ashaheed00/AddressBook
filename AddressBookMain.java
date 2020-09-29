// Version UC5

import java.util.Scanner;

public class AddressBookMain {

	private static Scanner sc = new Scanner(System.in);

	private static void operations(AddressBook addressBook) {
		String menu = sc.next();
		switch (menu) {
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
		case "0":
			System.out.println("Exiting...");
			return;
		default:
			System.out.println("Wrong option chosen");
		}
		System.out.print("Enter next operation: ");
		operations(addressBook);
	}

	public static void main(String[] args) {
		System.out.println("Welcome to Address Book Program!");

		AddressBook addressBook = new AddressBook();
		System.out.println("Add new contact: type 1" + "\nEdit someone's details: type 2" + "\nDelete a contact: type 3"
				+ "\nExit the procces: type 0");
		operations(addressBook);
	}
}