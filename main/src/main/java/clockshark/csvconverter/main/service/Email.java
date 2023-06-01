package clockshark.csvconverter.main.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class Email {
    private final JavaMailSender mailSender;

    public Email(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmailWithAttachment(String to) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject("CSV Conversion Template");
            helper.setText("Your CSV Template is attached!");
            helper.setFrom("cs-csv@outlook.com");
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd");
            LocalDateTime now = LocalDateTime.now();

            FileSystemResource file = new FileSystemResource(new File("output.csv"));
            helper.addAttachment(("convFile"  + now + ".csv"), file);
            mailSender.send(message);
        }
        catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }


}
