package sit.int371.capstoneproject.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sit.int371.capstoneproject.dtos.FileUploadReturnDTO;
import sit.int371.capstoneproject.exceptions.BadRequestException;
import sit.int371.capstoneproject.services.FileService;


import java.util.List;

@RestController
@RequestMapping("/api/images")
public class FileController {
    @Autowired
    private FileService fileService;

    @GetMapping("/{fileId}")
    public ResponseEntity<Resource> getImages(@PathVariable String fileId){
        return fileService.getImage(fileId);
    }

    @GetMapping("/dorm/{dormId}")
    public List<FileUploadReturnDTO> getAllImagesByDormId(@PathVariable Integer dormId) {
        return fileService.getAllImagesByDormId(dormId);
    }

    @PostMapping("/upload")
    public List<FileUploadReturnDTO> uploadImages(
            HttpServletRequest request,
            @Valid @RequestPart(value = "files", required = true) @Size(max = 5, message = "You can upload a maximum of 5 files.") List<MultipartFile> files) throws BadRequestException {
        Integer dormId = Integer.valueOf(request.getHeader("x-api-key"));
        return fileService.uploadImages(files, dormId);
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<String> deletedImage(@PathVariable String fileId){
        String message = fileService.deleteImage(fileId);
        return ResponseEntity.ok(message);
    }

}
