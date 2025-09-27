package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsersListResponse {
    @Getter
    private int page;
    @Getter
    @JsonProperty("per_page")
    private int perPage;
    @Getter
    private int total;
    @Getter
    @JsonProperty("total_pages")
    private int totalPages;
    private List<UserDataModel> data;
}
