package sit.int371.capstoneproject.services;


import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import sit.int371.capstoneproject.dtos.FileUploadReturnDTO;
import sit.int371.capstoneproject.exceptions.BadRequestException;
import sit.int371.capstoneproject.exceptions.InternalServerException;
import sit.int371.capstoneproject.exceptions.ResourceNotFoundException;
import sit.int371.capstoneproject.repositories.FileRepository;
import sit.int371.capstoneproject.repositories.StaffRepository;
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
    private final String uploadDir = "cap-file-upload";
    //private final String baseUrl = "http://localhost:8080/api/images";
    private final String baseUrl = "https://kmutt-compare.sit.kmutt.ac.th/api/images";
    
    private final Tika tika = new Tika();
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private StaffRepository staffRepository;

    public List<FileUploadReturnDTO> uploadImages(List<MultipartFile> multipartFileList, Integer staffId) throws BadRequestException {
        ArrayList<FileUploadReturnDTO> fileUploadReturnDTOList = new ArrayList<>();
        try {
            File directory = new File(uploadDir);
            if(!directory.exists()){
                directory.mkdirs();
            }
            for (MultipartFile multipartFile: multipartFileList){
                // ตรวจสอบว่าไฟล์ individual ไม่เป็น null และไม่ว่าง
                if (multipartFile == null || multipartFile.isEmpty()) {
                    throw new BadRequestException("One or more files are null or empty");
                }
                String generateFileName = String.valueOf(UUIDv7.randomUUID());
                Path filePath = Path.of(uploadDir, generateFileName);
                Files.copy(multipartFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                String uploadDate = String.valueOf(LocalDateTime.now());
                //จัดการ database ทั้งหมด
                // ตรวจสอบว่า staffId มีอยู่ในฐานข้อมูลหรือไม่
                if (!staffRepository.existsByStaffId(staffId)) {
                    throw new ResourceNotFoundException("Staff id " + staffId + " not exited!!!");
                }
                sit.int371.capstoneproject.entities.File fileEntity = new sit.int371.capstoneproject.entities.File();
                fileEntity.setFileId(generateFileName);
                fileEntity.setFileName(multipartFile.getOriginalFilename());
                fileEntity.setUploadDate(uploadDate);
                fileEntity.setStaffId(staffId);
                fileRepository.save(fileEntity);
                fileUploadReturnDTOList.add(new FileUploadReturnDTO(generateFileName, multipartFile.getOriginalFilename(), uploadDate, baseUrl + "/" + generateFileName));
            }
            return fileUploadReturnDTOList;
        }
        catch (IOException e){
            throw new InternalServerException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
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
            throw new InternalServerException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public List<FileUploadReturnDTO> getAllImage(){
       List<sit.int371.capstoneproject.entities.File> files = fileRepository.findAll();
       if(files.isEmpty()){
           throw new ResourceNotFoundException("No files found");
       }
        return getFileUploadReturnDTOS(files);
    }

    private List<FileUploadReturnDTO> getFileUploadReturnDTOS(List<sit.int371.capstoneproject.entities.File> files) {
        ArrayList<FileUploadReturnDTO> fileUploadReturnDTOList = new ArrayList<>();
        for(sit.int371.capstoneproject.entities.File file: files){
            fileUploadReturnDTOList.add(new FileUploadReturnDTO(file.getFileId(), file.getFileName(), file.getUploadDate(), baseUrl + "/" + file.getFileId()));
        }
        return fileUploadReturnDTOList;
    }


    public List<FileUploadReturnDTO> getAllImagesByStaffId(Integer staffId) {
        List<sit.int371.capstoneproject.entities.File> files = fileRepository.findByStaffId(staffId);
        if(files.isEmpty()){
            throw new ResourceNotFoundException("No files found by Staff id: " + staffId);
        }
        return getFileUploadReturnDTOS(files);
    }

public String deleteImage(String id) {
    if (fileRepository.existsByFileId(id)) {
        try {
            Path filePath = Path.of(uploadDir, id); // Path ไปโฟลเดอร์ cap-file-upload
            if (Files.exists(filePath)) { // ตรวจสอบว่าไฟล์มีอยู่
                Files.delete(filePath);
            } else {
                throw new ResourceNotFoundException("File Id: " + id + " does not exist in the folder!");
            }
            fileRepository.deleteByFileId(id); // ลบข้อมูลจาก database
            return "File ID: " + id + " has been deleted successfully!";
        } catch (IOException e) {
            throw new InternalServerException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete file: " + e.getMessage());
        }
    } else {
        throw new ResourceNotFoundException("File ID " + id + " does not exist!");
    }


}

}
