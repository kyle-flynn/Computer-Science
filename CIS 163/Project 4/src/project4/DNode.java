package project4;


import java.io.*;

public class DNode<E> implements Serializable {
		private E data;
		private DNode next;
		private DNode prev;
		
		public DNode(E data, DNode next, DNode prev) {
			super();
			this.data = data;
			this.next = next;
			this.prev = prev;
		}

		public DNode(E data, DNode next) {
			super();
			this.data = data;
			this.next = next;
		}
		
		public DNode() {
		}

		public E getData() {
			return data;
		}

		public void setData(E data) {
			this.data = data;
		}

		public DNode getNext() {
			return next;
		}

		public void setNext(DNode next) {
			this.next = next;
		}
		
		
		
}
