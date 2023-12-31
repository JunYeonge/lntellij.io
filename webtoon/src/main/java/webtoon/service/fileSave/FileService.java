package webtoon.service.fileSave;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import webtoon.api.TimeController;

import java.io.FileOutputStream;
import java.util.UUID;
@Service
@Log
public class FileService {

        public String uploadFile(String uploadPath, String fileName, byte[] fileData) throws Exception {
            UUID uuid = UUID.randomUUID();
            String extension = fileName.substring(fileName.lastIndexOf("."));
            String savedFileName = uuid.toString() + extension;
            String fileUploadFullUrl = uploadPath + "/" + savedFileName;
            FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
            fos.write(fileData);
            fos.close();
            return savedFileName;

        }
    }

