/*Sedak Puri
 * Assignment 4 
 * Professor Albow
 */
import java.io.*;
import java.util.Scanner;

public class Driver {
	public static void main(String[] args) {

		//File Output
		Filesystem obj;
		try {
			ObjectInputStream o = new ObjectInputStream(new FileInputStream("fs.data"));
			obj = (Filesystem) o.readObject();
			o.close();
		} catch(FileNotFoundException e) {
			obj = new Filesystem();
		} catch (ClassNotFoundException e) {
			System.out.println("Class wasn't found");
			obj = new Filesystem();
			System.exit(0);
		} catch (IOException e) {
			System.out.println("IOException");
			obj = new Filesystem();
			System.exit(0);
		}

		//File Input
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Enter a command: ");
		String input = keyboard.next();

		//While loop that controls console
		while(!input.equalsIgnoreCase("quit")) {
			switch (input) {
			case "cd":
				try {
					obj.cd(keyboard.next());
				} catch (IllegalStateException e) {
					System.out.println(e.getMessage());
				}
				break;

			case "ls":
				obj.ls();
				break;

			case "touch":
				try {
					obj.touch(keyboard.next());
				} catch (IllegalStateException e) {
					System.out.println(e.getMessage());
				}
				break;

			case "mkdir":
				try {
					obj.mkdir(keyboard.next());
				} catch (IllegalStateException e) {
					System.out.println(e.getMessage());
				}
				break;

			case "pwd":
				obj.pwd();
				break;

			case "rm":
				try {
					obj.rm(keyboard.next());
				} catch (IllegalStateException e) {
					System.out.println(e.getMessage());
				}
				break;

			case "rmdir":
				try {
					obj.rmdir(keyboard.next());
				} catch (IllegalStateException e) {
					System.out.println(e.getMessage());
				}
				break;

			case "tree":
				obj.tree();
				break;

			default:
				System.out.println("Command not recognized");
			}
			System.out.println("Enter a command: ");
			input = keyboard.next();
		}
		keyboard.close();

		//Writing Object
		try {
			ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("fs.data"));
			o.writeObject(obj);
			o.close();
		} catch (IOException e) {
			System.out.println("Unable to Write (save file failed!)");
			System.exit(0);
		}
	}
}
