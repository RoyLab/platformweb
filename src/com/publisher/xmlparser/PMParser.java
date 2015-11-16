package com.publisher.xmlparser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.publisher.dbutil.DBWriter;

public class PMParser {
	
	private String dirName = "";
	private String pmcFileName = "";
	private String pmcReg = "PMC-.*\\.xml";
	
	public PMParser(String path) {
		
		File dir = new File(path);
		if (!dir.exists() || !dir.isDirectory())
		{
			System.err.println("路径不存在或不是一个目录名");
			return;
		}		
		Pattern pattern = Pattern.compile(pmcReg);
		File[] files = dir.listFiles();
		for (File file: files){
			Matcher m = pattern.matcher(file.getName());
			if (m.matches()){
				dirName = path+((path.charAt(path.length()-1) == '\\')?"":"\\");
				pmcFileName = file.getName();
				break;
			}
		}
	}
	
	public boolean updateContent() throws Exception{
	
		List<String> fileList = establishDirectory();
		if (fileList == null) return false;
		
		File dir = new File(dirName);
		File[] xmls = dir.listFiles(new FilenameFilter(){
			public boolean accept(File file, String name) {
				return name.endsWith(".xml");
			}
		});
		
		fileList.sort(null);
		Arrays.sort(xmls);
		
		LinkedList<File> validFiles = new LinkedList<File>();
		int idx = 0, num = xmls.length;
		for (String fileName:fileList){
			Pattern pattern = Pattern.compile(fileName);
			while(idx < num){
				Matcher matcher = pattern.matcher(xmls[idx].getName());
				if (matcher.matches()){
					validFiles.add(xmls[idx]);
					idx ++;
					break;
				}
				idx ++;
			}
			if (idx >= num) break;			
		}
//		for (File file:xmls){
//			System.out.println(file.getName());
//		}
//		
//		System.out.println("***********************************");
//		for (String name: fileList){
//			System.out.println(name);
//		}
//		
//		for (File file:validFiles){
//			System.out.println(file.getName());
//		}
//		
		updateContent(validFiles);
		return true;
	}
	
	
	// 打开pmc入口，读取所有的相关dm的名字
	// TO-DO: 检查是否存在无法读取的文件，载入ServletContext
	protected List<String> establishDirectory() throws Exception{
		
		if (!isValid()) return null;
		
		File pmcFile = new File(dirName+pmcFileName);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(pmcFile);
		
		// 取得pmc的名字
		String pmcName = "";
		if (doc != null){
			NodeList pmc = doc.getElementsByTagName("pmc");
			if (pmc == null || pmc.getLength() != 1) 
				return null;
			
			NodeList pmcContent = pmc.item(0).getChildNodes();
			for (int i = 0; i < pmcContent.getLength(); i++){
				if (pmcContent.item(i).getNodeName() == "#text") continue;
				pmcName += pmcContent.item(i).getTextContent() + "-";
			}
			
			NodeList pmtitle = doc.getElementsByTagName("pmtitle");
			if (pmtitle == null || pmtitle.getLength() != 1) 
				return null;
			pmcName += pmtitle.item(0).getTextContent();
		}
		
		System.out.println("出版物名称为: " + pmcName);
		
		// 取得文件列表
		ArrayList<String> result = new ArrayList<String>();
		NodeList refdms = doc.getElementsByTagName("refdm");
		for (int i = 0; i < refdms.getLength(); i++){
			Node refdm = refdms.item(i);
			if (refdm.getParentNode().getNodeName() == "pmentry"){
				String dmName = "DMC";
				NodeList refdmChildren = refdm.getChildNodes();
				for (int j = 0; j < refdmChildren.getLength(); j++){
					Node code = refdmChildren.item(j);
					if (code.getNodeName() == "dmc"){
						dmName += code.getTextContent().replaceAll("\\s\\s*", ".*");
					} else if (code.getNodeName() == "language"){
						dmName += code.getAttributes().getNamedItem("language").getNodeValue()+"-";
						dmName += code.getAttributes().getNamedItem("country").getNodeValue()+".xml";
					}
				}
				result.add(dmName);
			}
		}
		//ParseDocument(doc, result);
		LoadIntoServletContext(doc);
		
		return result;
	}
	
	protected boolean updateContent(List<File> fileList) throws SQLException, SAXException, IOException, ParserConfigurationException{
		
		DBWriter dbWriter = null;
		try {
			System.out.println("fdafdsfa");
			dbWriter = new DBWriter();
		} catch (Exception e) {
			dbWriter.destroy();
			e.printStackTrace();
			return false;
		}
		dbWriter.initTables();
		for (File file: fileList){
			System.out.println("Insert DM: "+file);
			dbWriter.addDM(file);
		}
		
		dbWriter.destroy();
		return true;
	}

	protected void LoadIntoServletContext(Node node) throws TransformerException{
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer t = tf.newTransformer();
		t.setOutputProperty("encoding","utf-8");	
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		t.transform(new DOMSource(node), new StreamResult(bos));
		@SuppressWarnings("unused")
		String xmlStr = bos.toString();
		//System.out.println(xmlStr);
	}

	protected void ParseDocument(Document doc, List<File> list){
		
		// 取得第一个目录pmentry节点
		Node content = doc.getElementsByTagName("content").item(0);
		Node curNode = content.getFirstChild();
		while (curNode.getNodeName() != "pmentry" &&
				curNode.getNextSibling() != null)
			curNode = curNode.getNextSibling();
		
		if (curNode.getNodeName() != "pmentry") return;
		
		NodeList nl = curNode.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++){
			Node node1 = nl.item(i);			
			if (node1.getNodeName() == "#text") continue;

			String name = node1.getNodeName();
			switch (name){
			case "refdm":
				ParseRefdm(node1, list);
				break;
			case "pmentry":
				//ParsePmentry(node1, list);
				break;
			case "title":
				// TO-DO:
				break;
			default:
				System.err.println("unexpected token: "+name);
				break;
			}
		}
	}
	
	protected void ParseRefdm(Node node, List<File> list){
		// TO-DO
	}
	
	protected boolean isValid(){
		return !(dirName.isEmpty() || pmcFileName.isEmpty());
	}
}
