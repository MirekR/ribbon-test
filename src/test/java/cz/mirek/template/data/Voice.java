package cz.mirek.template.data;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "_class")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Haf.class, name = "cz.mirek.template.data.Haf"),
        @JsonSubTypes.Type(value = Mnau.class, name = "cz.mirek.template.data.Mnau")
})
public interface Voice {
    void makeNoise();
}
