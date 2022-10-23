package com.example.todo.service;

import com.example.todo.model.ConfirmUserToken;
import com.example.todo.model.EmailModel;
import com.example.todo.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Service
@Slf4j
public class EmailSenderService {

    private final String subject = "WELCOME!";
    private final String text = "WELCOME TO TODO APP!\nYou can put your plans there and you will not forget anything!";
    public static final String WEB_URL="http://localhost:8080";
    public static final String CONFIRM_USER_EMAIL_URL=WEB_URL+"/users/confirm-email?uuid=";
    public static final String FROM_EMAIL="test@mail.com";
    public static final String CONFIRM_USER_TEMPLATE="confirm-email";

    @Autowired
    private ConfirmUserService confirmUserService;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String toEmail){

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("arsmuhammed1907@gmail.com");
        message.setTo(toEmail);
        message.setText(text);
        message.setSubject(subject);

        mailSender.send(message);

        System.out.println("Mail sent successfully..");

    }

    public void sendEmail(User user) {
        try {

            ConfirmUserToken confirmUserToken=confirmUserService.createToken(user);
            EmailModel emailModel = initModel(user, confirmUserToken);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());


            Context context = new Context();
            context.setVariables(emailModel.getModel());
            String html = templateEngine.process(emailModel.getTemplateName(), context);
            helper.setTo(emailModel.getTo());
            helper.setText(html, true);
            helper.setSubject(emailModel.getSubject());
            helper.setFrom(emailModel.getFrom());

            mailSender.send(message);
        } catch (Exception e){
            log.error("Email send error: {} "+e.getClass()+e.getMessage());
            throw new RuntimeException(e);

        }

    }


    private EmailModel initModel(User user, ConfirmUserToken confirmUserToken) {
        Map<String, Object> model = new HashMap<>();
        model.put("Username",user.getUsername());
        model.put("signature","Todo App");

        model.put("confirmUrl", CONFIRM_USER_EMAIL_URL+confirmUserToken.getToken()); //  /users/confirm-email?uuid=12345
        return new EmailModel(FROM_EMAIL,user.getEmail(),
                "Issue Management: Confirm Email",CONFIRM_USER_TEMPLATE,model);

    }

}
