package com.publisher.models;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.publisher.Config;

public class TreeViewDocBuilder {
	
	public static Document createTreeViewDoc(Document raw) throws ParserConfigurationException{
	
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document result = builder.newDocument();
		
		
		
		return null;
	}
	
	public class Node{
		
		private Node[] children;
		private String text;
		
		public Node(String t, String h){
			text = t;
			
			Document doc = null;
			Element element = doc.createElement("directory");
		
			
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
