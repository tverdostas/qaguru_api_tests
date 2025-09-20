package models;

import io.qameta.allure.internal.shadowed.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDataResponseModel {
    private UserDataModel data;

    public UserDataModel getData() {
        return data;
    }
}
