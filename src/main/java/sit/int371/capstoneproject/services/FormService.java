package sit.int371.capstoneproject.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sit.int371.capstoneproject.dtos.FormCreateDTO;
import sit.int371.capstoneproject.dtos.FormDTO;
import sit.int371.capstoneproject.entities.Dormitory;
import sit.int371.capstoneproject.entities.Form;
import sit.int371.capstoneproject.entities.Staff;
import sit.int371.capstoneproject.exceptions.ResourceNotFoundException;
import sit.int371.capstoneproject.repositories.DormitoryRepository;
import sit.int371.capstoneproject.repositories.FormRepository;
import sit.int371.capstoneproject.repositories.StaffRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class FormService {
    @Autowired
    private FormRepository formRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private DormitoryRepository dormitoryRepository;

    //Methode -get all forms
    public List<FormDTO> getAllForms() {
        // ดึงข้อมูลทั้งหมดจาก Form collection
        List<Form> forms = formRepository.findAll();
        List<FormDTO> formDTOList = new ArrayList<>();

        for (Form form : forms) {
            FormDTO formDTO = new FormDTO();
            BeanUtils.copyProperties(form, formDTO); // คัดลอกข้อมูลทั่วไปจาก Form

            // ดึง staffName จาก Staff collection
            staffRepository.findByStaffId(form.getStaffId())
                    .ifPresent(staff -> formDTO.setStaffName(staff.getStaffName()));

            // ดึง dormName และ dormAddress จาก Dormitory collection
            dormitoryRepository.findByDormId(form.getDormId())
                    .ifPresent(dorm -> {
                        formDTO.setDormName(dorm.getDormName());
                        formDTO.setAddress(dorm.getAddress());
                    });

            formDTOList.add(formDTO); // เพิ่มข้อมูลในรายการ
        }

        return formDTOList; // คืนค่ารายการของ FormDTO
    }

    //Methode -get form by id
    public FormDTO getFormById(Integer id) {
        Form form = formRepository.findByFormId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Form id " + id + " not found !!!"));

        //map basic fields from Form to FormDTO
        FormDTO formDTO = modelMapper.map(form, FormDTO.class);

        //Fetch staff details
        Staff staff = staffRepository.findByStaffId(form.getStaffId())
                .orElseThrow(() -> new ResourceNotFoundException("Staff id " + form.getStaffId() + " not found!!!"));
        formDTO.setStaffName(staff.getStaffName());

        //Fetch dormitory details
        Dormitory dormitory = dormitoryRepository.findByDormId(form.getDormId())
                .orElseThrow(() -> new ResourceNotFoundException("Dormitory id " + form.getDormId() + " not found!!!"));
        formDTO.setDormName(dormitory.getDormName());
        formDTO.setAddress(dormitory.getAddress());

        return formDTO;
    }

    //Method -create form
    public FormCreateDTO createForm(Form form){
        Form addForm = new Form();
        addForm.setFormId(form.getFormId());
        addForm.setUsername(form.getUsername());
        addForm.setForm_date(form.getForm_date());
        addForm.setEmail(form.getEmail());
        addForm.setPhone(form.getPhone());
        addForm.setDate_in(form.getDate_in());
        addForm.setDate_out(form.getDate_out());
        addForm.setStaffId(form.getStaffId());
        addForm.setDormId(form.getDormId());
        return modelMapper.map(formRepository.save(addForm), FormCreateDTO.class);
    }
}
