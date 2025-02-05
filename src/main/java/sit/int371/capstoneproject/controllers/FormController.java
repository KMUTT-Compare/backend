package sit.int371.capstoneproject.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sit.int371.capstoneproject.autoId.SequenceGenerateFormService;
import sit.int371.capstoneproject.dtos.FormCreateDTO;
import sit.int371.capstoneproject.dtos.FormDTO;
import sit.int371.capstoneproject.entities.Form;
import sit.int371.capstoneproject.exceptions.ResourceNotFoundException;
import sit.int371.capstoneproject.services.FormService;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:5173","http://127.0.0.1:5173","http://cp24kk2.sit.kmutt.ac.th","https://kmutt-compare.sit.kmutt.ac.th"})
@RequestMapping("/api/forms")
public class FormController {
    @Autowired
    private FormService formService;
    @Autowired
    private ModelMapper modelMapper;


    @Autowired
    private SequenceGenerateFormService sequenceGenerateFormService;
    // Get All Form
    @GetMapping
    public ResponseEntity<List<FormDTO>> getAllForms() {
        List<FormDTO> forms = formService.getAllForms();
        if (forms.isEmpty()) {
            throw new ResourceNotFoundException("Forms not found!");
        }
        return ResponseEntity.ok(forms);
    }

    // Get Form By id
    @GetMapping("/{id}")
    public ResponseEntity<FormDTO> getFormById(@PathVariable Integer id) {
        FormDTO formDTO = formService.getFormById(id);
        return ResponseEntity.ok(formDTO);
    }

    // Get Form By user id
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<FormDTO>> getFormByUserId(@PathVariable Integer userId) {
        List<FormDTO> formDTO = formService.getFormsByUserId(userId);
        return ResponseEntity.ok(formDTO);
    }

    //Create Form
    @PostMapping("")
    public FormCreateDTO createdForm(@Valid @RequestBody FormCreateDTO formCreateDTO) {
        // Generate formId และ map DTO ไปเป็น Entity
        Form form = modelMapper.map(formCreateDTO, Form.class);
        form.setFormId((int) sequenceGenerateFormService.generateSequence(Form.SEQUENCE_NAME));

        // บันทึก Form ผ่าน Service
        FormCreateDTO createdForm = formService.createForm(form);

        // ส่ง Response กลับโดยมี HTTP 201 Created
        return createdForm;
    }

    //Update Form
    @PutMapping("/{id}")
    public FormCreateDTO updatedForm(@PathVariable Integer id, @Valid @RequestBody FormCreateDTO formCreateDTO){
        return formService.updateDorm(id, formCreateDTO);
    }

    //Delete Form
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletedForm(@PathVariable Integer id){
        String message = formService.deleteForm(id);
        return ResponseEntity.ok(message);
    }
}
