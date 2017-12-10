package my.onlineshop.library;
import my.onlineshop.service.UserService;
import java.util.Random;
import java.util.regex.Pattern;

public class Validate {

    private UserService userRepository;
    public Validate(UserService userRepository){
        this.userRepository = userRepository;
    }

    public String Email(String parEmail){
        Pattern VALID_EMAIL_ADDRESS_REGEX =
                    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                    Pattern.CASE_INSENSITIVE);
        boolean isMatch = VALID_EMAIL_ADDRESS_REGEX.matcher(parEmail).find();
        if(!isMatch)
            return "Alamat Email tidak valid.";
        else if(userRepository.existsByEmail(parEmail))
            return "Alamat Email sudah terdaftar.";
        else
            return null;
    }

    public String Name(String parName){
        if(parName.length() < 3)
            return "Nama terlalu pendek, minimal 3 karakter.";
        else
            return null;
    }

    public String Password(String parPassword){
        if(parPassword.length() < 6)
            return "Kata Sandi terlalu pendek, minimal 6 karakter.";
        else
            return null;
    }

    public String GenerateRandomToken(){
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            char c = chars[random.nextInt(chars.length)];
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }
}