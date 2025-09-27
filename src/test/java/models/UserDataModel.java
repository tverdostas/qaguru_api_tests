package models;

import lombok.Data;

@Data
public class UserDataModel {
    int id;
    String email;
    String first_name;
    String last_name;
    String avatar;
}
