package ecogame;

public class Pregunta {
    private String enunciado;
    private String opcion1;
    private String opcion2;
    private int opcionCorrecta;

    public Pregunta(String enunciado, String opcion1, String opcion2, int opcionCorrecta) {
        this.enunciado = enunciado;
        this.opcion1 = opcion1;
        this.opcion2 = opcion2;
        this.opcionCorrecta = opcionCorrecta;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public String getOpcion1() {
        return opcion1;
    }

    public String getOpcion2() {
        return opcion2;
    }

    public int getOpcionCorrecta() {
        return opcionCorrecta;
    }

    public boolean esOpcionCorrecta(String opcionSeleccionada) {
        return opcionSeleccionada.equals(opcion1) && opcionCorrecta == 1 ||
               opcionSeleccionada.equals(opcion2) && opcionCorrecta == 2;
    }
}
