package cz.mirek.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserData {
    @JsonProperty
    public int userId;

    @JsonProperty
    public int id;

    @JsonProperty
    public String title;

    @Override
    public String toString() {
        return "UserData{" +
                "userId=" + userId +
                ", id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
