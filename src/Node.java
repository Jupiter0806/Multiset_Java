class Node<T> {
	public T element;
	public int count;

	protected Node previous;
	protected Node next;

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