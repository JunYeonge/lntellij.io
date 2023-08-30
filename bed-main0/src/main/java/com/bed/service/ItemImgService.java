package com.bed.service;

import com.bed.entity.ItemImg;
import com.bed.repository.ItemImgRepository;
import com.bed.repository.ItemRepositoryCustomImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
//@Transactional
public class ItemImgService {

    @Value("${itemImgLocation}")
    private String itemImgLocation ;



    private final ItemImgRepository itemImgRepository;
    private final FileService fileService;
    private final ItemRepositoryCustomImpl itemRepositoryCustom;
    private final EntityManager entityManager;

    public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws Exception {
        String oriImgName = itemImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        if (!StringUtils.isEmpty(oriImgName)) {
            imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
            imgUrl = "/item_images/" + imgName;
        }

        itemImg.updateItemImg(oriImgName, imgName, imgUrl);

        itemImgRepository.save(itemImg);
    }




    public void updateItemImg(Long itemImgId, MultipartFile itemImgFile) throws Exception {
        // 이미지 파일이 비어 있을 경우 업데이트 작업을 건너뜁니다.
        if (itemImgFile.isEmpty()) {
            return;
        }

        ItemImg savedItemImg = itemImgRepository.findById(itemImgId)
                .orElseThrow(EntityNotFoundException::new);
        System.out.println("itemImgId: " + itemImgId);


        // 기존 이미지 파일 삭제
        if (!StringUtils.isEmpty(savedItemImg.getImgName())) {
            fileService.deleteFile(itemImgLocation + "/" + savedItemImg.getImgName());
        }

        try {
            String oriImgName = itemImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
            String imgUrl = "/item_images/" + imgName;
            savedItemImg.updateItemImg(oriImgName, imgName, imgUrl);
            // 변경된 값은 트랜잭션이 커밋될 때 자동으로 업데이트됩니다.
        } catch (IOException e) {
            // 파일 업로드 실패 시 예외 처리
            throw new Exception("이미지 파일 업로드 중 오류가 발생하였습니다.");
        } catch (Exception e) {
            // 기타 예외 처리
            throw new Exception("이미지 업데이트 중 오류가 발생하였습니다.");
        }
        System.out.println();
    }



    public List<Long> getImageIdsForItem() {
        // 이미지 ID를 가져오는 로직을 구현합니다.
        // 예시로는 임의의 이미지 ID 리스트를 반환하도록 작성했습니다.
        List<Long> imageIds = entityManager.createQuery("SELECT i.id FROM ItemImg i", Long.class)
                .getResultList();

        return imageIds;
    }

    public void deleteItemImg(Long itemId){

        itemRepositoryCustom.deleteByItemId(itemId);
    }
}