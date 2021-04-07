package com.itts.common.utils;

import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

@Slf4j
public class FileUtil {

	/**
	 * 判断文件大小
	 *
	 * @param :multipartFile:上传的文件
	 * @param size: 限制大小
	 * @param unit:限制单位（B,K,M,G)
	 * @return boolean:是否大于
	 */
	public static boolean checkFileSize(MultipartFile multipartFile, int size, String unit) {
		long len = multipartFile.getSize();//上传文件的大小, 单位为字节.
		//准备接收换算后文件大小的容器
		double fileSize = 0;
		if ("B".equals(unit.toUpperCase())) {
			fileSize = (double) len;
		} else if ("K".equals(unit.toUpperCase())) {
			fileSize = (double) len / 1024;
		} else if ("M".equals(unit.toUpperCase())) {
			fileSize = (double) len / 1048576;
		} else if ("G".equals(unit.toUpperCase())) {
			fileSize = (double) len / 1073741824;
		}
		//如果上传文件大于限定的容量
		if (fileSize > size) {
			return false;
		}
		return true;
	}

	public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
		File targetFile = new File(filePath);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		FileOutputStream out = new FileOutputStream(filePath + fileName);
		out.write(file);
		out.flush();
		out.close();
	}

	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public static String renameToUUID(String fileName) {
		return UUID.randomUUID() + "." + fileName.substring(fileName.lastIndexOf(".") + 1);
	}

	/**
	 * byte[] 转文件
	 * @param byteArray
	 * @param targetPath
	 */
	public static void byte2File(byte[] byteArray, String targetPath){
		InputStream in = new ByteArrayInputStream(byteArray);
		File file = new File(targetPath);
		String path = targetPath.substring(0, targetPath.lastIndexOf("/"));
		if (!file.exists()) {
			new File(path).mkdir();
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			int len = 0;
			byte[] buf = new byte[1024];
			while ((len = in.read(buf)) != -1) {
				fos.write(buf, 0, len);
			}
			fos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != fos) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public static String uploadFile(@RequestParam MultipartFile multipartFile) throws IOException{
		String[] fileAbsolutPath = {};
		String fileName = multipartFile.getOriginalFilename();
		String ext = fileName.substring(fileName.lastIndexOf(".")+1);
		byte[] file_buff = null;
		InputStream inputStream = multipartFile.getInputStream();
		if (inputStream!=null) {
			int len1 = inputStream.available();
			file_buff = new byte[len1];
			inputStream.read(file_buff);
		}
		inputStream.close();
		FastDFSFile file = new FastDFSFile(fileName,file_buff,ext);
		if(file == null){
			log.error("文件上传失败, 文件为空");
		}else{
			fileAbsolutPath = FastDFSClient.upload(file);
			if(fileAbsolutPath==null){
				log.error("【文件上传失败】");
				throw new ServiceException(ErrorCodeEnum.UPLOAD_FAIL_ERROR);
			}
			String path = FastDFSClient.getTrackerUrl()+"/"+fileAbsolutPath[0]+"/"+fileAbsolutPath[1];
			log.info("【文件上传成功】,path: {}",path);
			return path;
		}
		return null;
	}
}
