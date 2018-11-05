package cz.mirek.template;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.mirek.template.data.Animal;
import cz.mirek.template.data.Cat;
import cz.mirek.template.data.Dog;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class JacksonTest {
    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setup() {
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }

    @Test
    public void polyTest() throws IOException {
        Animal dog = new Dog();
        dog.setAnimalName("Dog");

        Animal cat = new Cat();
        cat.setAnimalName("Cat");

        List<Animal> animals = Arrays.asList(dog, cat);

        System.out.println(mapper.writeValueAsString(dog));

        //System.out.println(mapper.writeValueAsString(cat));

        Animal animal = mapper.readValue(json(), Dog.class);


    }

    private String json() {
        return "{\"name\":\"Dog\",\"voice\":{\"_class\": \"cz.mirek.template.data.Haf\", \"voice\":\"Haf\"}}\n";
    }

}
