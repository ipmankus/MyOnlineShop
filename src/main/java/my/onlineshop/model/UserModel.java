package my.onlineshop.model;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class UserModel {

    @Id
    @NotNull
    private String email;
    public String getEmail(){
        return this.email;
    }

    @NotNull
    private String name;
    public String getName(){
        return this.name;
    }

    @NotNull
    private String password;
    public String getPassword(){
        return this.password;
    }

    @NotNull
    private String token;
    public String getToken(){
        return this.token;
    }

    @NotNull
    private boolean status;
    public boolean getStatus(){
        return this.status;
    }
    public void setStatus(boolean status){
        this.status = status;
    }

    public UserModel(){}
    public UserModel(String parEmail, String parName, String parPassword, String parToken){
        this.email = parEmail;
        this.name = parName;
        this.password = parPassword;
        this.token = parToken;
        this.status = false;
    }
}
