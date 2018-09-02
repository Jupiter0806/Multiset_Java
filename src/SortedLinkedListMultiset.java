import java.io.PrintStream;
import java.util.*;

public class SortedLinkedListMultiset<T> extends Multiset<T>
{
	private Node firstNode;
	private Node lastNode;

	private boolean debugMode;

	public SortedLinkedListMultiset() {
		// Implement me!
		// Done
		this.firstNode = null;
		this.lastNode = null;

		// before submission: set it as false
		this.debugMode = false;
	} // end of SortedLinkedListMultiset()
	
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
				Node currentNode = this.firstNode;
				
				while (currentNode != null) {
					// todo: how to compare the item
					//  == tests for reference equality
					//  if use String.equals
					if (currentNode.element.toString().compareTo(item.toString()) < 0) {
						// currentNode < item
						// this.debugOutput("smaller");
					} else {
						// current Node > item
						// this.debugOutput("bigger");
						if (currentNode == this.firstNode) {
							newNode.linkNextNode(currentNode);
							currentNode.linkPreviousNode(newNode);
							this.firstNode = newNode;
						} else {
							newNode.linkNextNode(currentNode);
							newNode.linkPreviousNode(currentNode.getPreviousNode());
							currentNode.getPreviousNode().linkNextNode(newNode);
							currentNode.linkPreviousNode(newNode);
						}
						break;
					}
					currentNode = currentNode.getNextNode();
				}

				if (currentNode == null) {
					// this item is the biggest element in the list
					//  so append it to the end
					this.lastNode.linkNextNode(newNode);
					newNode.linkPreviousNode(this.lastNode);
					this.lastNode = newNode;
				}
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
			// out.println("empty");
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
	
} // end of class SortedLinkedListMultiset