package sit.int371.capstoneproject.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sit.int371.capstoneproject.ListMapper;
import sit.int371.capstoneproject.autoId.SequenceGenerateFormService;
import sit.int371.capstoneproject.dtos.DormitoryDTO;
import sit.int371.capstoneproject.dtos.FormCreateDTO;
import sit.int371.capstoneproject.dtos.FormDTO;
import sit.int371.capstoneproject.entities.Dormitory;
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
    private ListMapper listMapper;

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

    //Create Form
    @PostMapping("")
    public FormCreateDTO createdForm(@Valid @RequestBody FormCreateDTO formCreateDTO){
        //generate form id
        formCreateDTO.setFormId((int) sequenceGenerateFormService.generateSequence(Form.SEQUENCE_NAME));
        Form form = modelMapper.map(formCreateDTO, Form.class);
        return formService.createForm(form);
    }
}
