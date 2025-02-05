package sit.int371.capstoneproject.mails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);           // อีเมลผู้รับ staff
        message.setSubject(subject); // หัวข้อ
        message.setText(body);       // เนื้อหาของอีเมล
        mailSender.send(message);    // ส่งอีเมล
    }
}
