package sit.int371.capstoneproject.controllers;

import jakarta.validation.constraints.Size;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sit.int371.capstoneproject.dtos.FileUploadReturnDTO;
import sit.int371.capstoneproject.services.FileService;

import java.util.List;

@RestController
@RequestMapping("/api/images")
public class FileController {
    @Autowired
    private FileService fileService;

    @GetMapping("/{filename}")
    public ResponseEntity<Resource> getImages(@PathVariable String filename){
        return fileService.getImage(filename);
    }

    @PostMapping("/upload")
    public List<FileUploadReturnDTO> uploadImages(@RequestPart(value = "files", required = true) @Size(max = 5, message = "You can upload a maximum of 5 files.") List<MultipartFile> files) throws BadRequestException {
        return fileService.uploadImages(files);
    }
}
