package cz.mirek.template.data;

public class Haf implements Voice {
    private String voice = "Haf";

    @Override
    public void makeNoise() {
        System.out.println(voice);
    }
}
