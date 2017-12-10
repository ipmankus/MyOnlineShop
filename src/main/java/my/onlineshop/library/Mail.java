package my.onlineshop.library;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

@Service
public class Mail{

    private Properties props;
    public Mail(){
        props = new Properties();
        this.props.put("mail.smtp.host", "smtp.gmail.com");
        this.props.put("mail.smtp.auth", "true");
        this.props.put("mail.smtp.starttls.enable", "true");
        this.props.put("mail.smtp.port", "587");
        this.props.setProperty("mail.debug", "true");
    }

    private static String readFile(String path, Charset encoding)throws IOException{
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public void sendVerifyMail(String to, String name, String token) throws MessagingException, IOException {
        String templatePath = System.getProperty("user.dir") +
                "\\src\\main\\resources\\static\\html\\emailVerify.html";

        String targetUrlActivate = "localhost/verify?email=" + to + "&token=" + token;
        String msg = readFile(templatePath, Charset.defaultCharset());
        msg = msg.replace("{name}", name)
                    .replace("{targetUrlActivate}", targetUrlActivate);

        JavaMailSenderImpl sender = new JavaMailSenderImpl();

        sender.setJavaMailProperties(props);
        sender.setUsername("activation.myonlineshop@gmail.com");
        sender.setPassword("onlineshopku");
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setText(msg, true);
        helper.setSubject("Aktivasi Akun Baru - My Online Shop");
        FileSystemResource res = new FileSystemResource(new File(System.getProperty("user.dir") +
                                            "\\src\\main\\resources\\static\\images\\logo.png"));
        helper.addInline("AbcXyz123", res);
        sender.send(message);
    }
}