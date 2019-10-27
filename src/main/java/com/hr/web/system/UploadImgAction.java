package com.hr.web.system;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hr.bean.system.UploadImgInfo;
import com.hr.bean.system.WebResult;
import com.hr.util.DateUtil;
import com.hr.util.IpUtil;

@Controller
@RequestMapping("/system")
public class UploadImgAction extends BaseAction {

	/**
	 * 上传图片
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/uploadImage")
	public WebResult uploadImage(@RequestParam("file") MultipartFile file, HttpServletRequest request)
			throws Exception {
		rs = new WebResult();
		try {
			String winRoot = env.getProperty("file.upload.win.root");
			String linuxRoot = env.getProperty("file.upload.linux.root");
			String uploadPath = env.getProperty("file.upload.img.path");
			String nginxImg = this.getBaseImgUrl();
			String upload_dir_path = "";
			String dbSavePath = "";
			if (IpUtil.isWindows()) {// windows系统
				upload_dir_path = winRoot + uploadPath;
			} else {// 其他系统
				upload_dir_path = linuxRoot + uploadPath;
			}
			String currentMonth = DateUtil.getCurrentMonth();
			upload_dir_path = upload_dir_path + "/" + currentMonth;
			dbSavePath = uploadPath + "/" + currentMonth;
			File imgDirFile = new File(upload_dir_path);
			if (!imgDirFile.exists()) {
				imgDirFile.mkdirs();
			}
			List<UploadImgInfo> fileList = new ArrayList<UploadImgInfo>();
			// 原文件名称
			String trueName = file.getOriginalFilename();
			// 文件后缀名
			String ext = FilenameUtils.getExtension(trueName);
			// 系统生成的文件名
			String fileName = file.getOriginalFilename();
			fileName = System.currentTimeMillis() + new Random().nextInt(10000) + "." + ext;
			String upload_img = upload_dir_path + "/" + fileName;
			String db_save_img = dbSavePath + "/" + fileName;
			File saveFile = new File(upload_img);
			file.transferTo(saveFile);
			// 构造返回参数
			rs.setOK("上传成功！");
			UploadImgInfo uploadImgInfo = new UploadImgInfo();
			uploadImgInfo.setImgName(fileName);
			uploadImgInfo.setImgPath(upload_img);
			uploadImgInfo.setImgCallPath(db_save_img);
			uploadImgInfo.setImgSrcUrl(nginxImg + db_save_img);
			fileList.add(uploadImgInfo);
			rs.setDataSingle(uploadImgInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

}
