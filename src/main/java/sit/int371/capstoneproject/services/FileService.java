package sit.int371.capstoneproject.services;


import org.apache.coyote.BadRequestException;
import org.apache.tika.Tika;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import sit.int371.capstoneproject.dtos.FileUploadReturnDTO;
import sit.int371.capstoneproject.util.UUIDv7;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {
    private String uploadDir = "cap-file-upload";
    private String baseUrl = "http://localhost:8080/api/images";

    private final Tika tika = new Tika();

    public List<FileUploadReturnDTO> uploadImages(List<MultipartFile> multipartFileList) throws BadRequestException {
        if (multipartFileList.size() ==0 ){
            throw new BadRequestException();
        }
        ArrayList<FileUploadReturnDTO> fileUploadReturnDTOList = new ArrayList<>();
        try {
            File directory = new File(uploadDir);
            if(!directory.exists()){
                directory.mkdirs();
            }
            for (MultipartFile multipartFile: multipartFileList){
                String generateFileName = String.valueOf(UUIDv7.randomUUID());
                Path filePath = Path.of(uploadDir, generateFileName);
                Files.copy(multipartFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                String uploadDate = String.valueOf(LocalDateTime.now());
                fileUploadReturnDTOList.add(new FileUploadReturnDTO(multipartFile.getOriginalFilename(), uploadDate, baseUrl + "/" + generateFileName));
            }
            return fileUploadReturnDTOList;
        }
        catch (IOException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public ResponseEntity<Resource> getImage(String filename){
        try {
            Path filePath = Paths.get(uploadDir).resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

        if(!resource.exists()){
            throw new ResourceNotFoundException("Image not found!!!");
        }
        String contentType = tika.detect(filePath);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"").contentType(MediaType.parseMediaType(contentType)).body(resource);

        }catch (IOException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
