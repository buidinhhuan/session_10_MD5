package security.service;

import security.model.domain.Users;
import security.model.dto.request.FormSignUpDto;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<Users> findAll();
    Optional<Users> findByUserName(String username);
    Users save(FormSignUpDto users);

}
