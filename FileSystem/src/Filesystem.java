/*Sedak Puri
 * Assignment 4 
 * Professor Albow
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;

public class Filesystem implements Serializable{
	private static final long serialVersionUID = -6858120700328294761L;
	private Node root;
	private Node currentDirectory;

	//Constructor
	public Filesystem() {			
		root = new Node("", null, true);	
		currentDirectory = root;
	}

	private class Node implements Serializable{
		private static final long serialVersionUID = -5261541711359207962L;
		String name;
		ArrayList<Node> child; 
		Node parent;
		boolean isDirectory;

		//Constructor
		public Node(String name, Node parent, boolean isDirectory) {
			this.name = name; 
			this.parent = parent;
			this.isDirectory = isDirectory;
			child = new ArrayList<>();
		}

		public boolean isDirectory() {
			return isDirectory;
		}

		public ArrayList<Node> children(){
			return child;
		}

		public void appendChild(String name, boolean isDirectory) {
			child.add(new Node(name, currentDirectory, isDirectory));
		}

		//TODO: return true if this node is the root (it's parent is null)
		public boolean isRoot() {
			return parent == null;
		}

		public String toString() {
			return name;
		}
	}

	/**
	 * Method to check if currentDirectory has child with name
	 * @param name is the name you are checking
	 */
	public void checkMakeFile(String name) {
		for(Node i: currentDirectory.children()) {
			if(i.toString().equals(name)) {
				throw new IllegalStateException("Name already exists");
			}
		}
	}

	/**
	 * Method to list out all children node's names
	 */
	public void ls() {
		System.out.println();

		for (Node i: currentDirectory.children()) {
			System.out.println(i);
		}
		System.out.println();
	}

	/**
	 * Method to make a new directory with the specified name
	 * @param name is the name you want to set as the directory
	 */
	public void mkdir(String name) {
		checkMakeFile(name);
		currentDirectory.appendChild(name, true);
	}

	/**
	 * Method to make new file
	 * @param name is the name you want as the filename
	 */
	public void touch(String name) {
		checkMakeFile(name);
		currentDirectory.appendChild(name, false);
	}

	/**
	 * Method to print full path name from current directory
	 */
	public void pwd() {
		Stack<String> stack = new Stack<>();
		StringBuilder sb = new StringBuilder();

		//Adding into the stack
		Node temp = currentDirectory;
		while(!temp.isRoot()) {
			stack.add(temp.name);
			temp = temp.parent;
		}

		//Popping out of the stack
		sb.append("/");
		while(stack.size() != 0) {
			sb.append(stack.pop() + "/");
		}

		System.out.println(sb.toString());
	}

	/**
	 * Change the currentDirectory to the specified name
	 * @param name is the name you want set to the currentDirectory
	 */
	public void cd(String name) {
		//If you are moving to parent directory
		if (name.trim().equals("..")) {
			if (currentDirectory == root) {
				System.out.println("You are already at root directory!");
				return;
			}
			currentDirectory = currentDirectory.parent;
			return;
		}
	
		//Looking through children for matching node
		for(Node i: currentDirectory.children()) {																								
			if(i.isDirectory() && i.toString().equalsIgnoreCase(name)) {
				currentDirectory = i;
				return;
			}
		}
		throw new IllegalStateException("CurrentDirectory doesn't have a child named " + name + " that is a directory");
	}

	/**
	 * Method to remove a file with the specified name
	 * @param name is the name of the file you wish to remove
	 */
	public void rm(String name) {
		for(int i = 0; i < currentDirectory.child.size(); i++) {
			if (!currentDirectory.children().get(i).isDirectory() && currentDirectory.children().get(i).name.equalsIgnoreCase(name)) {
				currentDirectory.children().remove(i);
				return;
			}
		}
		throw new IllegalStateException("The file is either a directory or does not exist");
	}


	/**
	 * Method to remove the directory with the specified name
	 * @param name
	 */
	public void rmdir(String name) {
		for(int i = 0; i < currentDirectory.child.size(); i++) {
			if (currentDirectory.children().get(i).name.equalsIgnoreCase(name)) {
				if (currentDirectory.children().get(i).isDirectory() && currentDirectory.children().get(i).children().size() == 0) { 						
					currentDirectory.children().remove(i);
					return;
				}
			}
		}
		throw new IllegalStateException("The directory entered is either not a directory or the directory is not empty");
	}

	/**
	 * Method to pretty print the tree
	 */
	public void tree() {
		StringBuilder sb = new StringBuilder();
		toString(currentDirectory, sb, 0);
		System.out.println(sb.toString());
	}

	
	/**
	 * Helper method to help with the tree printing...
	 * @param n is the node that is being passed in
	 * @param sb is the string builder object that is recursively passed through the method
	 * @param level is the level of spacing
	 */ 
	private void toString(Node n, StringBuilder sb, int level) {
		if (n != null) {
			for (int i = 0; i < level; i++) {
				sb.append("  ");
			}

			//Recursively print the left and right children
			sb.append(n.name + "\n");


			for (int i = 0; i < n.children().size(); i++) {
				toString(n.children().get(i), sb, level + 1);											
			}
		}
	}
}