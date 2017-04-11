package com.yzz.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.yzz.dto.ResultData;

/**
 * 
 * @description:多文件上传工具
 *
 * @author 杨志钊
 * @date 2016年12月29日 下午5:18:51
 */
public class UploadFilesUtil {

	private static Logger logger = Logger.getLogger(UploadFilesUtil.class);

	public static ResultData<List<String>> copyFiles(String targetDir, List<MultipartFile> files) {
		ResultData<List<String>> returnData = new ResultData<>();
		List<String> uris = new ArrayList<>();
		String dir = targetDir.substring(targetDir.lastIndexOf("webapps") + 7);
		for (MultipartFile multipartFile : files) {

			if (!multipartFile.isEmpty()) {
				long size = multipartFile.getSize();
				if (size >= UploadFilesState.UPLOAD_SIZELIMITED) {
					returnData.setCode(400);
					returnData.setMsg(UploadFilesState.UPLOAD_OVERSIZE);

					return returnData;
				}

				String fileName = getNewFileName(multipartFile);
				dir = dir + "\\" + fileName;
				try {
					multipartFile.transferTo(new File(targetDir, fileName));
					uris.add(dir);
				} catch (Exception e) {
					returnData.setCode(400);
					returnData.setMsg(UploadFilesState.UPLOAD_INNERERROR);
					logger.error(e.getMessage());

					return returnData;
				}
			}
		}

		returnData.setData(uris);
		returnData.setMsg(UploadFilesState.UPLOAD_SUCCESS);
		return returnData;
	}

	private static String getNewFileName(MultipartFile multipartFile) {
		String fileName = multipartFile.getOriginalFilename();
		fileName = fileName.substring(0, fileName.lastIndexOf(".")) + System.currentTimeMillis()
				+ fileName.substring(fileName.lastIndexOf("."));
		return fileName;
	}

	public static String getTargetDir(HttpServletRequest request) {

		String targetDir = request.getServletContext().getRealPath(UploadFilesState.UPLOAD_TARGETDIR);

		File targetFileDir = new File(targetDir);
		if (!targetFileDir.exists()) {
			targetFileDir.mkdir();
		}

		return targetDir;
	}

}
