package jghjgjghj;

public class Emisora_online extends Emisora{

    final double GANANCIA_ONLINE=0.023;
    private String url;
    @Override
    double ganancias() {
        return num_oyentes*GANANCIA_ONLINE ;
    }

    public Emisora_online(int num_emisora, String nombre_emisora, int num_oyentes, String url) {
        super(num_emisora, nombre_emisora, num_oyentes);
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Emisora_online{" +
                "GANANCIA_ONLINE=" + GANANCIA_ONLINE +
                ", url='" + url + '\'' +
                ", num_emisora=" + num_emisora +
                ", nombre_emisora='" + nombre_emisora + '\'' +
                ", emitiendo=" + emitiendo +
                ", beneficios=" + beneficios +
                ", num_oyentes=" + num_oyentes +
                "} " + super.toString();
    }
}
