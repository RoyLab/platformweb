package com.publisher.xmlparsers;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class TreeViewDocBuilder {
	
	public Document createTreeViewDoc(Document raw) throws ParserConfigurationException{
	
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document result = builder.newDocument();
		
		NodeList contents = raw.getElementsByTagName("content");
		if (contents.getLength() < 1) return null;
		
		Node content = contents.item(0);
		Node root = createPmentryNode(content, result);
		((Element)root).setAttribute("name", "飞机测试数据源");
		result.appendChild(root);
				
		return result;
	}
	
	protected Node createPmentryNode(Node raw, Document doc){
		
		Element root = doc.createElement("filter");
		root.setAttribute("name", getFilterName(raw));
		NodeList childNodes = raw.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++){
			Node item = childNodes.item(i);
			if (item.getNodeName() == "pmentry"){
				Node newNode = createPmentryNode(item, doc);
				root.appendChild(newNode);
				continue;
			}
			
			if (item.getNodeName() == "refdm"){
				Element textNode = doc.createElement("dm");
				textNode.setAttribute("name", getDmtitle(item));
				root.appendChild(textNode);
				Text newNode = doc.createTextNode(getDMCText(item));
				textNode.appendChild(newNode);
				continue;
			}
		}
		return root;
	}
	
	protected String getDMCText(Node node){
		
		NodeList nodes = node.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++){
			Node item = nodes.item(i);
			String name = item.getNodeName();
			if (name == "dmc"){
				return item.getTextContent().trim().replaceAll("\\s\\s*", "");
			}
		}
		return null;
	}
	
	protected String getFilterName(Node node){
		
		NodeList nodes = node.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++){
			Node item = nodes.item(i);
			String name = item.getNodeName();
			if (name == "title"){
				return item.getTextContent().trim();
			}
		}
		return null;		
	}
	
	protected String getDmtitle(Node node){
		
		NodeList nodes = node.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++){
			Node item = nodes.item(i);
			String name = item.getNodeName();
			if (name == "dmtitle"){
				return item.getTextContent().trim().replaceAll("\\s\\s*", "-");
			}
		}
		return null;		
	}
}
