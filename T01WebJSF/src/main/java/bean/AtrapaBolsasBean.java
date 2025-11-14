package bean;

import java.util.Arrays;
import java.util.List;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import modelo.AtrapaBolsasEstado;

@Named
@RequestScoped
public class AtrapaBolsasBean {

    private final AtrapaBolsasEstado estadoInicial = new AtrapaBolsasEstado(0, 3, 1, 3);

    public AtrapaBolsasEstado getEstadoInicial() {
        return estadoInicial;
    }

    public String getInstrucciones() {
        return "Mueve al pingüino con las flechas izquierda y derecha para atrapar las bolsas de monedas que caen.";
    }

    public List<String> getConsejos() {
        return Arrays.asList(
                "Cada bolsa atrapada suma puntos y acelera el ritmo del juego.",
                "Si dejas caer tres bolsas, el minijuego termina.",
                "Pulsa el botón Reiniciar para volver a intentarlo.");
    }
}
