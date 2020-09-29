// Version UC3

import java.util.Scanner;

public class AddressBookMain {

	public static void main(String[] args) {
		System.out.println("Welcome to Address Book Program!");

		System.out.println("Add new contact using field constructor...");
		AddressBook addressBook = new AddressBook();
		addressBook.addNewContact();

		System.out.println("Enter the person's full name to edit his/her details: ");
		Scanner sc = new Scanner(System.in);
		addressBook.editContact(sc.next(), sc.next());
		sc.close();
	}
}