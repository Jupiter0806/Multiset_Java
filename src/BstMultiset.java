import java.io.PrintStream;
import java.util.*;

public class BstMultiset<T> extends Multiset<T>
{
	private TreeNode root;

	private boolean debugMode;

	public BstMultiset() {
		// Implement me!
		this.root = null;

		// before submission: set it as false
		this.debugMode = true;
	} // end of BstMultiset()

	public void add(T item) {
		// Implement me!
		if (this.root != null) {
			// non-empty tree, need to find the position
			TreeNode currentNode = this.root;
			while (currentNode != null) {
				// todo: how to compare the item
				//  == tests for reference equality
				//  use equals
				if (currentNode.element.equals(item)) {
					// find existing node in the tree, add its count
					currentNode.addCount();
					break;
				} else if (currentNode.element.toString().compareTo(item.toString()) > 0) {
					// smeller, go left
					if (currentNode.getLeftNode() != null) {
						// having node at its left, continue go
						currentNode = currentNode.getLeftNode();
					} else {
						// having no node at its left, pend new node to its left
						TreeNode node = new TreeNode(item, 1);
						node.linkPreviousNode(currentNode);
						currentNode.linkLeftNode(node);
						break;
					}
				} else {
					// greater, go right
					if (currentNode.getRightNode() != null) {
						// having node at its right, continue go
						currentNode = currentNode.getRightNode();
					} else {
						// having no node at its right, pend new node to its right
						TreeNode node = new TreeNode(item, 1);
						node.linkPreviousNode(currentNode);
						currentNode.linkRightNode(node);
						break;
					}
				}
			}
		} else {
			// empty tree add to root
			this.root = new TreeNode(item, 1);
		}
	} // end of add()


	public int search(T item) {
		// Implement me!
		int count = 0;

		TreeNode existingNode = this.getNodeByElement(item);
		if (existingNode != null) {
			count = existingNode.count;
		}
		this.debugOutput("Search item of " + item.toString() + " got count " + count);
		// default return, please override when you implement this method
		return count;
	} // end of add()


	public void removeOne(T item) {
		// Implement me!
		TreeNode existingNode = this.getNodeByElement(item);
		if (existingNode != null) {
			if (existingNode.count == 1) {
				// only one element is this set, remove all
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
		TreeNode existingNode = this.getNodeByElement(item);
		if (existingNode != null) {
			if (existingNode == this.root) {
				if (this.root.getRightNode() != null) {
					// this tree root has its right node,
					// 	so need to find this right's left to connect
					if (this.root.getLeftNode() != null) {
						TreeNode newRoot = this.root.getRightNode();
						if (newRoot.getLeftNode() != null) {
							// this right have its left,
							//  so need to go deeper
							TreeNode currentNode = newRoot.getLeftNode();
							while (currentNode.getLeftNode() != null) {
								currentNode = currentNode.getLeftNode();
							}
							// link to new position
							currentNode.linkLeftNode(this.root.getLeftNode());
							this.root.getLeftNode().linkPreviousNode(currentNode);

							// remove root
							this.root.unLinkRightNode();
							this.root.unLinkLeftNode();
							newRoot.unlinkPreviousNode();
							this.root = newRoot;
						} else {
							// link to new position
							newRoot.linkLeftNode(this.root.getLeftNode());
							this.root.getLeftNode().linkPreviousNode(newRoot);
							// remove root
							this.root.unLinkRightNode();
							this.root.unLinkLeftNode();
							newRoot.unlinkPreviousNode();
							this.root = newRoot;
						}
					} else {
						// havig no left, so the right will be root
						TreeNode newRoot = this.root.getRightNode();
						this.root.unLinkRightNode();
						newRoot.unlinkPreviousNode();
						this.root = newRoot;
					}
				} else {
					// this tree root has no its right node
					//	so the root's left will be root again
					if (this.root.getLeftNode() != null) {
						// having root's left
						TreeNode newRoot = this.root.getLeftNode();
						this.root.unLinkLeftNode();
						newRoot.unlinkPreviousNode();
						this.root = newRoot;
					} else {
						// having no root's left and also no right
						// so just set root to null
						this.root = null;
					}
				}
			} else {
				if (existingNode.getLeftNode() != null) {
					// it has its left, replace it with its left
					
					this.debugOutput("Promote left");
					// link to new position
					existingNode.getLeftNode().linkPreviousNode(existingNode.getPreviousNode());
					if (existingNode.getPreviousNode().getLeftNode() == existingNode) {
						// this node is its previous node's left,
						// 	so its previous left should be replaced

						this.debugOutput("Link previous to left");
						existingNode.getPreviousNode().linkLeftNode(existingNode.getLeftNode());
					} else {
						// this node is its previous node's right,
						// 	so its previous right should be replaced

						this.debugOutput("Link previous to right");
						existingNode.getPreviousNode().linkRightNode(existingNode.getLeftNode());
					}
					if (existingNode.getRightNode() != null) {
						if (existingNode.getLeftNode().getRightNode() != null) {
							// having this node's left's right,
							//  so need to find position this node's right
							TreeNode currentNode = existingNode.getLeftNode().getRightNode();
							while (currentNode.getRightNode() != null) {
								currentNode = currentNode.getRightNode();
							}
							currentNode.linkRightNode(existingNode.getRightNode());
							existingNode.getRightNode().linkPreviousNode(currentNode);
						} else {
							// having no this node's left's right
							//  pend this node's right to its left's right
							existingNode.getLeftNode().linkRightNode(existingNode.getRightNode());
							existingNode.getRightNode().linkPreviousNode(existingNode.getLeftNode());
						}
					}

					// remove existingNode
					existingNode.unlinkPreviousNode();
					existingNode.unLinkLeftNode();
					existingNode.unLinkRightNode();
				} else if (existingNode.getRightNode() != null) {
					// it has no left, but right, replace it with its right

					this.debugOutput("Promote right");
					// link to new position
					existingNode.getRightNode().linkPreviousNode(existingNode.getPreviousNode());
					if (existingNode.getPreviousNode().getLeftNode() == existingNode) {
						// this node is its previous node's left,
						// 	so its previous left should be replaced

						this.debugOutput("Link previous to left");
						existingNode.getPreviousNode().linkLeftNode(existingNode.getRightNode());
					} else {
						// this node is its previous node's right,
						// 	so its previous right should be replaced

						this.debugOutput("Link previous to right");
						existingNode.getPreviousNode().linkRightNode(existingNode.getRightNode());
					}

					// remove existingNode
					existingNode.unlinkPreviousNode();
					existingNode.unLinkLeftNode();
					existingNode.unLinkRightNode();
				} else {
					// remove existingNode
					if (existingNode.getPreviousNode().getLeftNode() == existingNode) {
						existingNode.getPreviousNode().unLinkLeftNode();
					} else {
						existingNode.getPreviousNode().unLinkRightNode();
					}
					existingNode.unlinkPreviousNode();
					existingNode.unLinkLeftNode();
					existingNode.unLinkRightNode();
				}
			}
		} else {
			this.debugOutput("No item " + item.toString());
		}
	} // end of removeAll()


	public void print(PrintStream out) {
		// Implement me!
		// 
		Stack stack = new Stack();
		TreeNode currentNode = this.root;

		while (currentNode != null) {
			out.println(currentNode.toString());
			
			if (currentNode.getLeftNode() != null) {
				stack.push(currentNode.getLeftNode());
			}
			if (currentNode.getRightNode() != null) {
				stack.push(currentNode.getRightNode());
			}

			// if (stack.lastNode != null) {
			// 	this.debugOutput("after push " + stack.lastNode.toString());
			// } else {
			// 	this.debugOutput("after push empty stack");
			// }

			currentNode = (TreeNode) stack.pop();
			// if (stack.lastNode != null) {
			// 	this.debugOutput("after pop " + stack.lastNode.toString());
			// } else {
			// 	this.debugOutput("after pop empty stack");
			// }
		}
	} // end of print()

	private TreeNode getNodeByElement(T item) {
		TreeNode currentNode = this.root;
		while (currentNode != null) {
			// todo: how to compare the item
			//  == tests for reference equality
			//  use equals
			if (currentNode.element.equals(item)) {
				return currentNode;
			} else if (currentNode.element.toString().compareTo(item.toString()) > 0) {
				// smeller, go left
				currentNode = currentNode.getLeftNode();
			} else {
				// greater, go right
				currentNode = currentNode.getRightNode();
			}
		}
		return null;
	}

	private void debugOutput(String str) {
		if (this.debugMode) {
			System.out.println(str);
		}
	}

} // end of class BstMultiset

class TreeNode<T> {
	public T element;
	public int count;

	protected TreeNode previous;
	protected TreeNode left;
	protected TreeNode right; 

	public TreeNode(T element, int count) {
		this.element = element;
		this.count = count;
		
		this.previous = null;
		this.left = null;
		this.right = null;
	}

	public void linkPreviousNode(TreeNode node) {
		this.previous = node;
	}

	public void unlinkPreviousNode() {
		this.previous = null;
	}

	public TreeNode getPreviousNode() {
		return this.previous;
	}

	public void linkLeftNode(TreeNode node) {
		this.left = node;
	}

	public void unLinkLeftNode() {
		this.left = null;
	}

	public TreeNode getLeftNode() {
		return this.left;
	}

	public void linkRightNode(TreeNode node) {
		this.right = node;
	}

	public void unLinkRightNode() {
		this.right = null;
	}

	public TreeNode getRightNode() {
		return this.right;
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

class Stack<TreeNode> extends LinkedListMultiset<TreeNode> {
	public void push(TreeNode item) {
		this.add(item);
	}

	public TreeNode pop() {
		if (this.lastNode != null) {
			TreeNode item = (TreeNode) this.lastNode.element;
			if (this.lastNode.getPreviousNode() != null) {
				Node newLastNode = this.lastNode.getPreviousNode();
				newLastNode.unlinkNextNode();
				this.lastNode.unlinkPreviousNode();
				this.lastNode = newLastNode;
			} else {
				this.lastNode = null;
			}
			return item;
		} else {
			return null;
		}
		
	}
}