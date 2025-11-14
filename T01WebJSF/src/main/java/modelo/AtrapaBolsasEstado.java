package modelo;

public class AtrapaBolsasEstado {

    private int puntaje;
    private int vidas;
    private int nivel;
    private int velocidadBolsa;

    public AtrapaBolsasEstado() {
    }

    public AtrapaBolsasEstado(int puntaje, int vidas, int nivel, int velocidadBolsa) {
        this.puntaje = puntaje;
        this.vidas = vidas;
        this.nivel = nivel;
        this.velocidadBolsa = velocidadBolsa;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public int getVidas() {
        return vidas;
    }

    public void setVidas(int vidas) {
        this.vidas = vidas;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getVelocidadBolsa() {
        return velocidadBolsa;
    }

    public void setVelocidadBolsa(int velocidadBolsa) {
        this.velocidadBolsa = velocidadBolsa;
    }
}
