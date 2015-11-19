package com.publisher.xmlparsers;

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
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.publisher.Config;
import com.publisher.utils.AsciiSaveUtil;
import com.publisher.utils.DBWriter;
import com.publisher.utils.OperateXMLByDOM;

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
	
	public boolean parse() throws Exception{
		
		if (!isValid()) return false;

		File pmcFile = new File(dirName+pmcFileName);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(pmcFile);
		
		if (doc == null) return false;
		
		loadDirectory(doc);
		
//		TODO for test only.
		List<String> itemList = getRequestedItemList(doc);
		if (itemList == null) return false;
		
		List<File> fileList = getUpdatingFileList(itemList);
		updateContent(fileList);
		return true;
	}
	
	protected boolean isValid(){
		return !(dirName.isEmpty() || pmcFileName.isEmpty());
	}

	protected String getPMName(Document doc){
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
		return pmcName;
	}

	// 检查项目是否存在对应文件，是否需要被更新
	protected List<File> getUpdatingFileList(List<String> fileList){
		
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
		return validFiles;		
	}
	
	// 打开pmc入口，读取所有的相关dm的名字
	// TO-DO: 检查是否存在无法读取的文件，载入ServletContext
	protected List<String> getRequestedItemList(Document doc) throws Exception{
		
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
		
		return result;
	}
	
	protected boolean updateContent(List<File> fileList) throws SQLException, SAXException, IOException, ParserConfigurationException {
		
		DBWriter dbWriter = new DBWriter();
		
		dbWriter.initTables();
		//TODO debug
		int count = 0;
		for (File file: fileList){
			count ++;
			System.out.println("Insert DM: "+file);
			dbWriter.addDM(file);
			if (count > 5) break;
		}
		
		dbWriter.destroy();
		return true;
	}

	protected void loadDirectory(Document doc) throws TransformerException, ParserConfigurationException {
		
		TreeViewDocBuilder builder = new TreeViewDocBuilder();
		Document newDoc = builder.createTreeViewDoc(doc);
		String xmlStr = OperateXMLByDOM.doc2FormatString(newDoc);
		Config.getServletContext().setAttribute("xmldirectory", xmlStr);
		AsciiSaveUtil.saveAscii("C:\\Users\\RUI\\Desktop\\test.xml", xmlStr);
	}
}
