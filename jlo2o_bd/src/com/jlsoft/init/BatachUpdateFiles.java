package com.jlsoft.init;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 修改文件
 * 2015-12-4 下午2:19:24 
 * @author Uktar
 *
 */
public class BatachUpdateFiles {

	/**
	 * 修改文件
	 * 
	 * @param baseDirName 上级目录
	 * @param targetWord 目标词
	 * @param replaceWord 替换词
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void updateFiles(String baseDirName, String targetWord, String replaceWord) throws IOException,
			InterruptedException {

		String tempName = null;
		File baseDir = new File(baseDirName);

		List filesList = new ArrayList();

		if (!baseDir.exists() || !baseDir.isDirectory()) {
			System.out.println("目录不存在");
		}
		else {
			String[] filelist = baseDir.list();

			for (int i = 0; i < filelist.length; i++) {

				File readfile = new File(baseDirName + "\\" + filelist[i]);

				if (!readfile.isDirectory()) {

					filesList.add(readfile.getAbsoluteFile());
					// 读文件
					File file = new File(readfile.getAbsoluteFile().toString());
					String content = readFile(file);
					Long fileDate = readfile.lastModified();
					// 替换内容
					content = content.replaceAll(targetWord, replaceWord);
					// 写文件
					writeFile(content, file);
					readfile.setLastModified(fileDate);

				}
				else if (readfile.isDirectory()) {
					// 如果是目录递归替换
					updateFiles(baseDirName + "\\" + filelist[i], targetWord, replaceWord);
				}
			}
		}
		System.out.println("共" + filesList.size() + "个文件被修改");
	}

	/**
	 * 读文件
	 * 
	 * @param file 文件
	 * @return
	 */
	public static String readFile(File file) {

		StringBuilder builer = new StringBuilder();
		String line = null;
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(file));

			while ((line = reader.readLine()) != null) {
				builer.append(line + "\r\n");
			}
			reader.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (reader != null) {
					reader.close();
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}

		return builer.toString();
	}

	/**
	 * 写文件
	 * 
	 * @param content 内容
	 * @param targetFile 目标文件
	 * @return
	 */
	public static boolean writeFile(String content, File targetFile) {

		OutputStreamWriter writer = null;

		try {
			if (targetFile == null) {
				return false;
			}
			// 以utf-8 写文件
			writer = new OutputStreamWriter(new FileOutputStream(targetFile), "UTF-8");

			writer.write(content);
			writer.flush();
			writer.close();
			return true;
		}
		catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		finally {
			try {
				if (writer != null) {
					writer.close();
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws IOException, InterruptedException {

		// TODO Auto-generated method stub
		String dirName = "E:\\html_files";
		String targetWord = "127.0.0.1";
		String replaceWord = "localhost";
		BatachUpdateFiles.updateFiles(dirName, targetWord, replaceWord);
	}

}
