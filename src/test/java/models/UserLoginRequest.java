package models;

import lombok.Data;
import lombok.Setter;

@Data
public class UserLoginRequest {
    @Setter
    private String email;
    @Setter
    private String password;
}
