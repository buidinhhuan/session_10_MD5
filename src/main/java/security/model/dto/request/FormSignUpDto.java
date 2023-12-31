package security.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormSignUpDto {
    private String name;
    private String username;
    private String password;
    private List<String> roles;
}
