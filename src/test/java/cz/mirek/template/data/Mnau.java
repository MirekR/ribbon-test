package cz.mirek.template.data;

public class Mnau implements Voice {
    private String voice = "Mnau";

    @Override
    public void makeNoise() {
        System.out.println(voice);
    }
}
