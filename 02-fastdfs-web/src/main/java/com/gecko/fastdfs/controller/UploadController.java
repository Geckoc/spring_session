package com.gecko.fastdfs.controller;

import com.gecko.fastdfs.util.FastDFSUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Controller
public class UploadController {

    @GetMapping("/toUpload")
    public String toUploadPage(){
        return "upload";
    }

    /**
     * 文件上传
     * @param fileName 前端传递过来的文件
     * @return url
     */
    @PostMapping("/fileUpload")
    @ResponseBody
    public String fileUpload(MultipartFile fileName){
        byte[] bytes = null;
        try {
            bytes = fileName.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String filename = fileName.getOriginalFilename();
        String  fileExt = FastDFSUtils.getFileExt(filename);

        return FastDFSUtils.fileUpload(bytes, fileExt);
    }

    @GetMapping("/fileDownload1")
    public void fileDownload(String groupName, String remoteFileName, HttpServletResponse response){
        String fileExt = FastDFSUtils.getFileExtWithPoint(remoteFileName);
        // 设置以流类型响应  （ 二进制流，不知道下载文件类型）	application/octet-stream
        response.setHeader("Content-Disposition", "attachment;filename=" + System.currentTimeMillis() + fileExt);
        response.setContentType("octet-stream");
        byte[] bytes = FastDFSUtils.fileDownload(groupName, remoteFileName);
        try {
            // 通过response对象直接将byte类型数组回写 设置了响应类型 浏览器会自动解析 完成下载
            response.getOutputStream().write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*
        ResponseEntity通常用于返回文件流
        @ResponseBody可以直接返回Json结果，
        ResponseEntity不仅可以返回json结果，还可以定义返回的HttpHeaders和HttpStatus
        ResponseEntity的优先级高于@ResponseBody。
        在不是ResponseEntity的情况下才去检查有没有@ResponseBody注解。
        如果响应类型是ResponseEntity可以不写@ResponseBody注解，写了也没有关系。
     */
    @GetMapping("fileDownload2")
    public ResponseEntity<byte[]> fileDownload2(String groupName, String remoteFileName, HttpServletResponse response){
        String fileExt = FastDFSUtils.getFileExtWithPoint(remoteFileName);
        byte[] fileBytes = FastDFSUtils.fileDownload(groupName, remoteFileName);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);//流类型
        httpHeaders.setContentDispositionFormData("attachment",System.currentTimeMillis() + fileExt);
        return new ResponseEntity<>(fileBytes, httpHeaders, HttpStatus.OK);
    }
}
