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
import sit.int371.capstoneproject.mails.EmailService;
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

    @Autowired
    private EmailService emailService;

    //Methode -get all forms
    public List<FormDTO> getAllForms() {
        // ดึงข้อมูลทั้งหมดจาก Form collection
        List<Form> forms = formRepository.findAll();
        if(forms.isEmpty()){
            throw new ResourceNotFoundException("Forms not found!");
        }

        List<FormDTO> formDTOList = new ArrayList<>();
        for (Form form : forms) {
            FormDTO formDTO = new FormDTO();
            BeanUtils.copyProperties(form, formDTO); // คัดลอกข้อมูลทั่วไปจาก Form

            // ดึง staffName จาก Staff collection
            staffRepository.findByStaffId(form.getStaffId())
                    .ifPresent(staff -> {
                        formDTO.setStaffName(staff.getStaffName());
                        formDTO.setStaffEmail(staff.getStaffEmail());
                        formDTO.setStaffPhone(staff.getStaffPhone());
                    });

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

        //แปลง Form เป็น FormDTO
        FormDTO formDTO = modelMapper.map(form, FormDTO.class);

        //Fetch และเติมข้อมูล StaffName
        Staff staff = staffRepository.findByStaffId(form.getStaffId())
                .orElseThrow(() -> new ResourceNotFoundException("Staff id " + form.getStaffId() + " not found!!!"));
        formDTO.setStaffName(staff.getStaffName());
        formDTO.setStaffEmail(staff.getStaffEmail());
        formDTO.setStaffPhone(staff.getStaffPhone());

        //Fetch และเติมข้อมูล Dormitory
        Dormitory dormitory = dormitoryRepository.findByDormId(form.getDormId())
                .orElseThrow(() -> new ResourceNotFoundException("Dormitory id " + form.getDormId() + " not found!!!"));
        formDTO.setDormName(dormitory.getDormName());
        formDTO.setAddress(dormitory.getAddress());

        return formDTO;
    }

    public List<FormDTO> getFormsByUserId(Integer userId) {
        List<Form> forms = formRepository.findByUserId(userId);

        if (forms.isEmpty()) {
            throw new ResourceNotFoundException("Forms by User ID " + userId + " not found !!!");
        }

        return forms.stream().map(form -> {
            // แปลง Form เป็น FormDTO
            FormDTO formDTO = modelMapper.map(form, FormDTO.class);

            // Fetch และเติมข้อมูล Staff
            Staff staff = staffRepository.findByStaffId(form.getStaffId())
                    .orElseThrow(() -> new ResourceNotFoundException("Staff ID " + form.getStaffId() + " not found!!!"));
            formDTO.setStaffName(staff.getStaffName());
            formDTO.setStaffEmail(staff.getStaffEmail());
            formDTO.setStaffPhone(staff.getStaffPhone());

            // Fetch และเติมข้อมูล Dormitory
            Dormitory dormitory = dormitoryRepository.findByDormId(form.getDormId())
                    .orElseThrow(() -> new ResourceNotFoundException("Dormitory ID " + form.getDormId() + " not found!!!"));
            formDTO.setDormName(dormitory.getDormName());
            formDTO.setAddress(dormitory.getAddress());

            return formDTO;
        }).toList();
    }

    //Method -create form
    public FormCreateDTO createForm(Form form){
        Form addForm = new Form();
        addForm.setFormId(form.getFormId());
        addForm.setName(form.getName());
        addForm.setForm_date(form.getForm_date());
        addForm.setEmail(form.getEmail());
        addForm.setPhone(form.getPhone());
        addForm.setDate_in(form.getDate_in());
        addForm.setDate_out(form.getDate_out());
        addForm.setDescription(form.getDescription());
        addForm.setStaffId(form.getStaffId());
        addForm.setDormId(form.getDormId());
        addForm.setUserId(form.getUserId());
        //save to database
        Form saveForm = formRepository.save(addForm);
        //ดึงข้อมูล Staff ผ่าน staffId
        Staff staff = staffRepository.findByStaffId(form.getStaffId()).orElseThrow(
                () -> new ResourceNotFoundException("Staff does not exist!"));
        // ส่งอีเมลไปยัง staff หลังจากการสร้าง Form
        String subject = "New Form Submission for Dormitory Reservation";
        String body = "Dear " + staff.getStaffName() + ",\n\n" +
                "A new form has been submitted for dormitory reservation. " +
                "Here are the details:\n\n" +
                "Form ID: " + saveForm.getFormId() + "\n" +
                "Name: " + saveForm.getName() + "\n" +
                "Phone: " + saveForm.getPhone() + "\n" +
                "Email: " + saveForm.getEmail() + "\n" +
                "Date In: " + saveForm.getDate_in() + "\n" +
                "Date Out: " + saveForm.getDate_out() + "\n\n" +
                "Kind regards,\nYour Dormitory Reservation System";

        emailService.sendEmail(staff.getStaffEmail(), subject, body);

        // แปลง Form ที่บันทึกแล้วเป็น FormCreateDTO เพื่อส่งกลับ
        return modelMapper.map(saveForm, FormCreateDTO.class);
    }

    //Method -update/edit form
    public FormCreateDTO updateDorm(Integer id, FormCreateDTO formCreateDTO){
        Form exitsForm = formRepository.findByFormId(id).orElseThrow(
                () -> new ResourceNotFoundException(id + "does not exited!!!"));
        //ตรวจสอบว่า staffId มีอยู่ในฐานข้อมูลหรือไม่
        if (!staffRepository.existsByStaffId(formCreateDTO.getStaffId())) {
            throw new ResourceNotFoundException("Staff id " + formCreateDTO.getStaffId() + " not exited!!!");
        }
        //ตรวจสอบว่า dormId มีอยู่ในฐานข้อมูลหรือไม่
        if (!dormitoryRepository.existsByDormId(formCreateDTO.getDormId())) {
            throw new ResourceNotFoundException("Dormitory id " + formCreateDTO.getDormId() + " not exited!!!");
        }

        exitsForm.setName(formCreateDTO.getName());
        exitsForm.setForm_date(formCreateDTO.getForm_date());
        exitsForm.setEmail(formCreateDTO.getEmail());
        exitsForm.setPhone(formCreateDTO.getPhone());
        exitsForm.setDate_in(formCreateDTO.getDate_in());
        exitsForm.setDate_out(formCreateDTO.getDate_out());
        exitsForm.setDescription(formCreateDTO.getDescription());
        exitsForm.setStaffId(formCreateDTO.getStaffId());
        exitsForm.setDormId(formCreateDTO.getDormId());
        exitsForm.setUserId(formCreateDTO.getUserId());
        Form updatedForm = formRepository.save(exitsForm);
        return modelMapper.map(updatedForm, FormCreateDTO.class);
    }

    //Method -delete form
    public String deleteForm(Integer id){
        if (formRepository.existsByFormId(id)){
            formRepository.deleteByFormId(id);
            return "Form with Id " + id + " has been deleted successfully!";
        }else {
            throw new ResourceNotFoundException("Form with Id " + id + " dose not exited!!!");
        }
    }
}
