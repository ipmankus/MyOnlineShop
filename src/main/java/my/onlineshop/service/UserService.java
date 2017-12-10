package my.onlineshop.service;

import org.springframework.data.repository.CrudRepository;
import my.onlineshop.model.UserModel;

public interface UserService extends CrudRepository<UserModel, Long> {
    boolean existsByEmail(String email);
    boolean existsByEmailAndToken(String email, String token);
    boolean existsByEmailAndPassword(String email, String password);
    UserModel getByEmail(String email);
    UserModel getByEmailAndToken(String email, String token);
}
