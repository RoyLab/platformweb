package com.publisher.model;

public class TreeViewData {
	
	public class Node{
		
		private Node[] children;
		private String text;
		
		public Node(String t, String h){
			text = t;
		}

		public int getChildNum(){
			return (children == null)?-1:children.length;
		}
		
		public boolean isFilter(){
			if (getChildNum() < 0) return false;
			if (getChildren()[0].getChildNum() < 0) return false;
			return true;
		}
		
		public Node[] getChildren() {
			return children;
		}
		public void setChildren(Node[] children) {
			this.children = children;
		}
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
	};
	
	private Node root;

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}

}
