package com.study.downloadandupload;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@Controller
public class FileController {
    @RequestMapping("/uploadfile")
    /*
    注意:
        1、参数表中MultipartFile myfile的myfile与upload.jsp中name属性相同(即从前端传递到后台的myfile)，否则
        String filename = myfile.getOriginalFilename();会报空指针异常
        2、uploading下面上传之前必须得有东西存在，否则报异常，软件bug
    */
    public String uploadFile(MultipartFile myfile, HttpServletRequest request){
        //获得uploading文件夹的服务器地址
        String path = request.getRealPath("/uploading");
        System.out.println("path:"+path);

        //获得所上传文件的名称
        String filename = myfile.getOriginalFilename();
        //通过拼接字符串获得上传文件的服务器路径
        String finalpath = path + "/" +filename;
        try {
            //复制文件到服务器(对应输入和输出的操作)
            myfile.transferTo(new File(finalpath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //将文件名存入request域，以便显示在客户端(网页)
        request.setAttribute("fname",filename);
        return "success";
    }

    @RequestMapping("/download")
    public ResponseEntity<byte[]> downloadFile(String imagename,HttpServletRequest request){
        //获得uploading文件夹的服务器地址
        String path = request.getRealPath("/uploading");
        System.out.println("path:"+path);
        //创建消息头对象
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        //告诉浏览器以附件的形式来处理响应的结果
        headers.setContentDispositionFormData("attachment",imagename);
        File file = new File(path + "/" + imagename);
        ResponseEntity<byte[]> responseEntity = null;
        try {
            responseEntity =
                    new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.CREATED);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseEntity;
    }
}
