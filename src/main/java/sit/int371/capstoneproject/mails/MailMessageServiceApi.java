package sit.int371.capstoneproject.mails;

import com.mailgun.client.MailgunClient;
import com.mailgun.model.message.Message;
import com.mailgun.model.message.MessageResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sit.int371.capstoneproject.dtos.FormDTO;
import sit.int371.capstoneproject.entities.Form;
import sit.int371.capstoneproject.repositories.FormRepository;

import java.util.Map;


@Service
@RequiredArgsConstructor
public class MailMessageServiceApi {
    @Value("${email.api-key}")
    private final String API_KEY;
    @Autowired
    private FormRepository formRepository;
    @Autowired
    private ModelMapper modelMapper;

    public void sendFormDetails(String email, @NotNull Integer formId){
        //เรียกใช้ mailgun API เพื่อส่งอีเมล
        com.mailgun.api.v3.MailgunMessagesApi mailgunMessagesApi = MailgunClient.config(API_KEY)
                .createApi(com.mailgun.api.v3.MailgunMessagesApi.class);

        Form currentForm = formRepository.findByFormId(formId).orElseThrow(
                () -> new RuntimeException("Form does not Exist!!"));
        FormDTO formDTO = modelMapper.map(currentForm, FormDTO.class);

        // แปลง FormDTO เป็น JSON String (ถ้าใช้ Mailgun ต้องทำให้เป็น String)
//        ObjectMapper objectMapper = new ObjectMapper();
//        String newFormStringFormat;
//        try {
//            newFormStringFormat = objectMapper.writeValueAsString(formDTO);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException("Error converting FormDTO to JSON", e);
//        }
        Map<String, Object> newFormVariables = Map.of(
                "email", email,
//                "newFormStringFormat", newFormStringFormat,
                "formDate",currentForm.getForm_date(),
                "name",currentForm.getName()

        );


        Message message = Message.builder()
                .from("KMUTT Compare <notification@kmutt.kasidate.me>")
                .to(email)
                .subject("Welcome, Booking Dormitory!")
                .template("new-form-notify")
                .tracking(false)
                .mailgunVariables(newFormVariables)
                .build();

        MessageResponse messageResponse = mailgunMessagesApi.sendMessage("kmutt.kasidate.me", message);
    }

}
