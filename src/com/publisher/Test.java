package com.publisher;

import java.io.File;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import com.publisher.api.SearchEngine;
import com.publisher.xmlparsers.PMParser;

public class Test {
	
	public static void main(String[] args) {
		
		// 初始化解析xml，放在服务器的初始化函数中。
		PMParser pm = new PMParser("D:\\eclipse\\jdbctest2\\xml");//规定xml的路径
		try {
			pm.initialize();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 全文搜索的示例
		try {
			SearchEngine searchEngine = new SearchEngine();
			System.out.println(searchEngine.fullTextSearch("飞机"));
			searchEngine.destroy();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public File[] parseDirectory(String path){
		
		File file = new File(path);
		if (file.exists() && file.isDirectory()) {
			 return file.listFiles();
		} else {
			System.err.println("文件不存在或不是一个目录名");
		}
		return null;
	}
	
	public static void traverseFolder1(String path) {
		int fileNum = 0, folderNum = 0;
		File file = new File(path);
		if (file.exists()) {
			LinkedList<File> list = new LinkedList<File>();
			File[] files = file.listFiles();
			for (File file2 : files) {
				if (file2.isDirectory()) {
					System.out.println("文件??" + file2.getAbsolutePath());
					list.add(file2);
					
					getModifiedTime(file2.getAbsolutePath());
					fileNum++;
				} else {
					System.out.println("文件:" + file2.getAbsolutePath());
					folderNum++;
				}
			}
			File temp_file;
			while (!list.isEmpty()) {
				temp_file = list.removeFirst();
				files = temp_file.listFiles();
				if (files == null) 				System.out.println(temp_file);

				for (File file2 : files) {
					if (file2.isDirectory()) {
						System.out.println("文件??" + file2.getAbsolutePath());
						list.add(file2);
						fileNum++;
					} else {
						System.out.println("文件:" + file2.getAbsolutePath());
						folderNum++;
					}
				}
			}
		} else {
			System.out.println("文件不存??");
		}
		System.out.println("文件夹共??" + folderNum + ",文件共有:" + fileNum);

	}
	
	public static void getModifiedTime(String path){
		
		File file = new File(path);
		long time = file.lastModified();
		Date d = new Date(time);
		Format simpleFormat = new SimpleDateFormat("E dd MMM yyyy hh:mm:ss a");
		String dateString = simpleFormat.format(d);
		System.err.println(file.getName()+" 最后修改时间："+dateString);		
	}

}
