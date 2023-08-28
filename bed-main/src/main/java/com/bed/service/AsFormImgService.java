package com.bed.service;

import com.bed.dto.AsFormImgDto;
import com.bed.entity.AsFormImg;
import com.bed.repository.AsFormImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityExistsException;

@Service
@Transactional
@RequiredArgsConstructor
public class AsFormImgService {

    @Value("${asFormImgLocation}")
    private String asFormImgLocation;
    private final AsFormImgRepository asFormImgRepository;
    private final FileService fileService;


    public void saveAsFormImg(AsFormImg asFormImg, MultipartFile asFormImgFile)throws Exception{
        String asOriImgName = asFormImgFile.getOriginalFilename();
        String asImgName="";
        String asImgUrl ="";

        //파일업로드
        if(!StringUtils.isEmpty(asOriImgName)){
            asImgName = fileService.uploadFile(asFormImgLocation,asOriImgName,asFormImgFile.getBytes());
            asImgUrl = "/asForm/"+asImgName;
        }
        //이미지 정보 저장
        asFormImg.updateAsImg(asOriImgName,asImgName,asImgUrl);
        asFormImgRepository.save(asFormImg);
    }
    public void updateAsFormImg(Long asFormImdId,MultipartFile asFormImgFile)throws Exception{
        if(!asFormImgFile.isEmpty()){
            AsFormImg savedAsFormImg = asFormImgRepository.findById(asFormImdId)
                    .orElseThrow(EntityExistsException::new);
        if(!StringUtils.isEmpty(savedAsFormImg.getAsImgName())){
            fileService.deleteFile(asFormImgLocation+"/"+savedAsFormImg.getAsImgName());
        }
        String asOriImgName = asFormImgFile.getOriginalFilename();
        String asImgName = fileService.uploadFile(asFormImgLocation,asOriImgName,asFormImgFile.getBytes());
        String asImgUrl = "/asForm/"+asImgName;
        savedAsFormImg.updateAsImg(asOriImgName,asImgName,asImgUrl);
        }
    }
}
