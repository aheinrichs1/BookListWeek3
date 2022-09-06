import java.util.List;
import java.util.Scanner;

import controller.ListItemHelper;
import model.ListItem;

/**
 * @author alexh - aheinrichs
 * CIS175 - Fall 2022
 * Sep 6, 2022
 */

public class StartProgram {

		static Scanner in = new Scanner(System.in);
		static ListItemHelper lih = new ListItemHelper();

		private static void addAnItem() {
			System.out.print("Enter a book title: ");
			String title = in.nextLine();
			System.out.print("Enter an author: ");
			String author = in.nextLine();
			System.out.print("Enter a category: ");
			String category = in.nextLine();
			ListItem toAdd = new ListItem(title, author, category);
			lih.insertItem(toAdd);
		}

		private static void deleteAnItem() {
			System.out.print("Enter the title to delete: ");
			String title = in.nextLine();
			System.out.print("Enter the author to delete: ");
			String author = in.nextLine();
			System.out.print("Enter the category to delete: ");
			String category = in.nextLine();
			ListItem toDelete = new ListItem(title, author, category);
			lih.deleteItem(toDelete);
		}

		private static void editAnItem() {
			System.out.println("How would you like to search? ");
			System.out.println("1 : Search by Title");
			System.out.println("2 : Search by Author");
			System.out.println("3 : Search by Category");
			int searchBy = in.nextInt();
			in.nextLine();
			List<ListItem> foundItems;
			if (searchBy == 1) {
				System.out.print("Enter the book title: ");
				String titleName = in.nextLine();
				foundItems = lih.searchForItemByTitle(titleName);
			} else if (searchBy == 2) {
				System.out.print("Enter the author: ");
				String authorName = in.nextLine();
				foundItems = lih.searchForItemByAuthor(authorName);
			} else {
				System.out.print("Enter the category: ");
				String categoryName = in.nextLine();
				foundItems = lih.searchForItemByCategory(categoryName);
			}

			if (!foundItems.isEmpty()) {
				System.out.println("Found Results.");
				for (ListItem l : foundItems) {
					System.out.println("ID: " + l.getId() + " : Details: " + l.returnItemDetails());
				}
				System.out.print("Which ID to edit: ");
				int idToEdit = in.nextInt();

				ListItem toEdit = lih.searchForItemById(idToEdit);
				System.out.println("Retrieved " + toEdit.getTitle() + " by " + toEdit.getAuthor()
								   + " in the " + toEdit.getCategory() + " category");
				System.out.println("1 : Update Title");
				System.out.println("2 : Update Author");
				System.out.println("3 : Update Category");
				System.out.println("4 : Cancel");
				int update = in.nextInt();
				in.nextLine();

				if (update == 1) {
					System.out.print("New Title: ");
					String newTitle = in.nextLine();
					toEdit.setTitle(newTitle);
				} else if (update == 2) {
					System.out.print("New Author: ");
					String newAuthor = in.nextLine();
					toEdit.setAuthor(newAuthor);
				} else if (update == 3) {
					System.out.print("New Category: ");
					String newCategory = in.nextLine();
					toEdit.setCategory(newCategory);
				}

				lih.updateItem(toEdit);

				} else {
					System.out.println("---- No results found");
			}

		}

		public static void main(String[] args) {
			runMenu();
		}

		public static void runMenu() {
			boolean goAgain = true;
			System.out.println("--- Welcome to my collection of books! ---");
			while (goAgain) {
				System.out.println("*  Select from the list:");
				System.out.println("*  1 -- Add a book");
				System.out.println("*  2 -- Edit a book");
				System.out.println("*  3 -- Delete a book");
				System.out.println("*  4 -- View the list");
				System.out.println("*  5 -- Exit the program");
				System.out.print("*  Your selection: ");
				int selection = in.nextInt();
				in.nextLine();

				if (selection == 1) {
					addAnItem();
				} else if (selection == 2) {
					editAnItem();
				} else if (selection == 3) {
					deleteAnItem();
				} else if (selection == 4) {
					viewTheList();
				} else {
					lih.cleanUp();
					System.out.println("   Goodbye!   ");
					goAgain = false;
				}

			}

		}

		private static void viewTheList() {
			List<ListItem> allItems = lih.showAllItems();
			System.out.println("\nBOOKS:\n-----------");
			for(ListItem singleItem : allItems) {
				System.out.println(singleItem.returnItemDetails());
			}
			System.out.println("-----------\n");
		}

	}