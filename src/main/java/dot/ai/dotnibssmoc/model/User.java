package dot.ai.dotnibssmoc.model;

import dot.ai.dotnibssmoc.model.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private Integer id;
    private  String userName;
    private UserRole role;
}
