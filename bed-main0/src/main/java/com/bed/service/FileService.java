package com.bed.service;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
@Log
public class FileService {

    public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception{
        UUID uuid = UUID.randomUUID(); //
        String extension = originalFileName.substring(originalFileName.lastIndexOf(".")); //확장자를 추출
        String savedFileName = uuid.toString() + extension; //새로운 랜덤한 파일명을 만든다.
        String fileUploadFullUrl = uploadPath + "/" + savedFileName;
        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);

        fos.write(fileData);

        fos.close();
        return savedFileName;
    }
    public void deleteFile(String filePath) throws Exception{
        File deleteFile = new File(filePath);
        if(deleteFile.exists()) {
            deleteFile.delete();
            log.info("파일을 삭제하였습니다.");
        } else {
            log.info("파일이 존재하지 않습니다.");
        }
    }

}