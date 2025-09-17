package bean;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import modelo.Empleado;
import java.util.List;
import java.util.ArrayList;

@Named
@RequestScoped
public class EmpleadoBean {
    private Empleado empleado = new Empleado();

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public List<Empleado> getEmpleados() {
        return new ArrayList<>();
    }
}