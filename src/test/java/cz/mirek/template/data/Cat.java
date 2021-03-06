package cz.mirek.template.data;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Cat implements Animal {
    public String name;
    private Voice voice = new Mnau();

    @Override
    @JsonIgnore
    public String getAnimalName() {
        return name;
    }

    @Override
    public void setAnimalName(String name) {
        this.name = name;
    }

    @Override
    public Voice getVoice() {
        return voice;
    }
}
