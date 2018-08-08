import java.io.PrintStream;
import java.util.*;

public class LinkedListMultiset<T> extends Multiset<T>
{
	private Node firstNode;
	private Node lastNode;

	private boolean debugMode;

	public LinkedListMultiset() {
		// Implement me!
		// Done
		this.firstNode = null;
		this.lastNode = null;

		// before submission: set it as false
		this.debugMode = true;
	} // end of LinkedListMultiset()
	
	
	public void add(T item) {
		// Implement me!
		// Done
		if (this.lastNode != null) {
			// having nodes in this set, adding it or pending it
			Node existingNode = this.getNodeByElement(item);
			if (existingNode != null) {
				// having the item in this set, add its count
				existingNode.addCount();
			} else {
				// having no item in this set, pedning it the ending
				Node newNode = new Node(item, 1);
				newNode.linkPreviousNode(this.lastNode);
				this.lastNode.linkNextNode(newNode);
				this.lastNode = newNode;
			}
		} else {
			// this is an empty linked list, so add a node directly
			Node node = new Node(item, 1);
			this.firstNode = node;
			this.lastNode = node;
		}
	} // end of add()
	
	// todo: do need a specific search algorithm?
	//  or faster searching
	public int search(T item) {
		// Implement me!
		// Done		
		int count = 0;

		Node existingNode = this.getNodeByElement(item);
		if (existingNode != null) {
			count = existingNode.count;
		}
		this.debugOutput("Search item of " + item.toString() + " got count " + count);
		// default return, please override when you implement this method
		return count;
	} // end of add()
	
	
	public void removeOne(T item) {
		// Implement me!
		// Done
		Node existingNode = this.getNodeByElement(item);
		if (existingNode != null) {
			if (existingNode.count == 1) {
				// we only have one this element in this set, so remove All
				this.removeAll(item);
			} else {
				existingNode.deductCount();
			}
		} else {
			this.debugOutput("No item " + item.toString());
		}	
	} // end of removeOne()
	
	
	public void removeAll(T item) {
		// Implement me!
		// Done
		Node existingNode = this.getNodeByElement(item);
		if (existingNode != null) {
			if (existingNode == this.firstNode) {
				this.firstNode = existingNode.getNextNode();
				this.firstNode.unlinkPreviousNode();
			} else if (existingNode == this.lastNode) {
				this.lastNode = existingNode.getPreviousNode();
				this.lastNode.unlinkNextNode();
			} else {
				existingNode.getPreviousNode().linkNextNode(existingNode.getNextNode());
				existingNode.getNextNode().linkPreviousNode(existingNode.getPreviousNode());
			}
		} else {
			this.debugOutput("No item " + item.toString());
		}
	} // end of removeAll()
	
	
	public void print(PrintStream out) {
		// Implement me!
		// Done
		if (this.firstNode != null) {
			Node currentNode = this.firstNode;
			out.println(currentNode.toString());
			currentNode = currentNode.getNextNode();
			while (currentNode != null) {
				out.println(currentNode.toString());
				currentNode = currentNode.getNextNode();
			}
		} else {
			//before submission: comment it 
			out.println("empty");
		}
	} // end of print()
	
	private Node getNodeByElement(T item) {
		Node currentNode = this.firstNode;
		while (currentNode != null) {
			// todo: how to compare the item
			//  == tests for reference equality
			//  if use String.equals
			if (currentNode.element.equals(item)) {
				return currentNode;
			}
			currentNode = currentNode.getNextNode();
		}

		return null;
	}

	private void debugOutput(String str) {
		if (this.debugMode) {
			System.out.println(str);
		}
	}

} // end of class LinkedListMultiset

class Node<T> {
	public T element;
	public int count;

	private Node previous;
	private Node next;

	public Node(T element, int count) {
		this.element = element;
		this.count = count;
		
		this.next = null;
	}

	public void linkPreviousNode(Node node) {
		this.previous = node;
	}

	public void unlinkPreviousNode() {
		this.previous = null;
	}

	public Node getPreviousNode() {
		return this.previous;
	}

	public void linkNextNode(Node node) {
		this.next = node;
	}

	public void unlinkNextNode() {
		this.next = null;
	}

	public Node getNextNode() {
		return this.next;
	}

	public void addCount() {
		this.count += 1;
	}

	public void deductCount() {
		this.count -= 1;
	}

	@Override
	public String toString() {
		return this.element.toString() + " | " + this.count;
	}
}