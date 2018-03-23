package cz.mirek.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserData {
    @JsonProperty
    private int userId;

    @JsonProperty
    private int id;

    @JsonProperty
    private String title;

    @Override
    public String toString() {
        return "UserData{" +
                "userId=" + userId +
                ", id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
