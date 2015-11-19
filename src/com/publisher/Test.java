package com.publisher;

import org.w3c.dom.Document;

import com.publisher.utils.OperateXMLByDOM;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Document doc = OperateXMLByDOM.xml2Doc("D:/pubRes/xml/DMC-SAMPLE-A-00-00-00-00A-041A-A_000-01_zh-CN.xml");
		System.out.println(OperateXMLByDOM.doc2String(doc.getElementsByTagName("content").item(0)));
	}

}
