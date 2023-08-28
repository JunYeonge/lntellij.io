package com.bed.service;

import com.bed.constant.AsFormStatus;
import com.bed.dto.AsFormDto;
import com.bed.dto.AsFormSearchDto;
import com.bed.dto.AsHistDto;
import com.bed.entity.Article;
import com.bed.entity.AsForm;
import com.bed.entity.AsFormImg;
import com.bed.repository.AsFormImgRepository;
import com.bed.repository.AsFormRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AsFormService {
    private final AsFormRepository asFormRepository;
    private final AsFormImgService asFormImgService;
    private final AsFormImgRepository asFormImgRepository;

//as신청서 내용 저장 -- 기입한 내용과 첨부이미지 둘다 저장
    public Long saveAs(AsForm asForm,MultipartFile file) throws Exception{
        asFormRepository.save(asForm);
        AsFormImg asFormImg = new AsFormImg();
        asFormImg.setAsForm(asForm);
        asFormImgService.saveAsFormImg(asFormImg,file);
        return asForm.getId();
    }
//as신청서 내용 저장 -- 기입한 내용만 저장(첨부이미지는 보내지 않은 경우)
    public AsForm saveAsForm(AsForm asForm)
    {
        return asFormRepository.save(asForm);
    }
//as신청서(기입한 내용과 첨부이미지 둘다 받아 처리) -- 초반에 사용했으나 다른 메서드로 대체함
    public Long saveAsFormFile(AsFormDto asFormDto, MultipartFile asFormImgFile)throws Exception{
        AsForm asForm = asFormDto.createAsForm();
        asFormRepository.save(asForm);

        AsFormImg asFormImg = new AsFormImg();
        asFormImg.setAsForm(asForm);

        asFormImgService.saveAsFormImg(asFormImg,asFormImgFile);
        return asForm.getId();
    }
//as신청 리스트 - 동적쿼리, 페이저 기능
    @Transactional(readOnly = true)
    public Page<AsForm> getAdminAsPage(AsFormSearchDto asFormSearchDto, Pageable pageable) {
        return asFormRepository.getAdminAsPage(asFormSearchDto, pageable);
    }
//유저페이지에서 본인 as신청 리스트 확인
    @Transactional
    public Page<AsHistDto> getAsList(String name, Pageable pageable) {
        List<AsForm> asFormList = asFormRepository.findAsForms(name,pageable);
        //유저이메일과 페이징조건을 이용하여 목록조회
        Long totalCount = asFormRepository.countAsForm(name);
        //유저의 신청서 총 개수를 구한다.
        List<AsHistDto> asHistDtoList = new ArrayList<>();
//        for(AsForm asForm : asFormList){
//            AsHistDto asHistDto = new AsHistDto(asForm);
//            asHistDtoList.add(asHistDto);
//        }
        for (int i = 0; i < asFormList.size(); i++) {
            AsForm asForm = asFormList.get(i);
            AsHistDto asHistDto = new AsHistDto(asForm);
            asHistDto.setSeqNumber(pageable.getPageNumber() * pageable.getPageSize() + i + 1);
            asHistDtoList.add(asHistDto);
        }
        return new PageImpl<AsHistDto>(asHistDtoList,pageable,totalCount);
    }


    public void checkAs(Long id) {
        AsForm asForm = asFormRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        asForm.setAsFormStatus(AsFormStatus.CHECK);
    }
    public void deleteAs(List<Long> selectedForms) {
        List<AsForm> asForms = asFormRepository.findAllById(selectedForms);
        asFormRepository.deleteAll(asForms);
    }
}
