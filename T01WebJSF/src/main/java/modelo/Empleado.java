// modelo/Empleado.java - TU CÓDIGO ORIGINAL + DETALLES DE NÓMINA
package modelo;

import java.io.Serializable;
import java.util.Objects;

public class Empleado implements Serializable {

    private static final long serialVersionUID = 1L;
    private long id;
    private String nombres;
    private double salario;
    private int dias;

    public Empleado() {
        super();
    }

    public Empleado(long id, String nombres, double salario, int dias) {
        super();
        this.id = id;
        this.nombres = nombres;
        this.salario = salario;
        this.dias = dias;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    // ========== MÉTODO ORIGINAL - NO SE TOCA ==========
    public double getTotalPagar() {
        return salario / 30 * dias;
    }

    // ========== NUEVOS MÉTODOS PARA DETALLES DE NÓMINA ==========
    
    // Salario mensual (igual al original pero como método separado)
    public double getSalarioMensual() {
        return (salario / 30.0) * dias;
    }

    // Descuento de salud (4% del salario mensual)
    public double getDescuentoSalud() {
        return getSalarioMensual() * 0.04;
    }

    // Descuento de pensión (4% del salario mensual)
    public double getDescuentoPension() {
        return getSalarioMensual() * 0.04;
    }

    // Auxilio de transporte (solo si salario <= 2 salarios mínimos)
    public double getAuxilioTransporte() {
        return (salario <= 2600000) ? 162000 : 0;
    }

    // Total descuentos
    public double getTotalDescuentos() {
        return getDescuentoSalud() + getDescuentoPension();
    }

    // Total devengados (salario mensual + auxilio)
    public double getTotalDevengados() {
        return getSalarioMensual() + getAuxilioTransporte();
    }

    // Neto a pagar (devengados - descuentos)
    public double getNetoAPagar() {
        return getTotalDevengados() - getTotalDescuentos();
    }

    // ========== MÉTODOS PARA FORMATEAR EN PESOS ==========
    
    public String getSalarioFormateado() {
        return String.format("$%,.0f", salario);
    }

    public String getSalarioMensualFormateado() {
        return String.format("$%,.0f", getSalarioMensual());
    }

    public String getDescuentoSaludFormateado() {
        return String.format("$%,.0f", getDescuentoSalud());
    }

    public String getDescuentoPensionFormateado() {
        return String.format("$%,.0f", getDescuentoPension());
    }

    public String getAuxilioTransporteFormateado() {
        return String.format("$%,.0f", getAuxilioTransporte());
    }

    public String getTotalDescuentosFormateado() {
        return String.format("$%,.0f", getTotalDescuentos());
    }

    public String getTotalDevengadosFormateado() {
        return String.format("$%,.0f", getTotalDevengados());
    }

    public String getNetoAPagarFormateado() {
        return String.format("$%,.0f", getNetoAPagar());
    }

    public String getTotalPagarFormateado() {
        return String.format("$%,.0f", getTotalPagar());
    }

    // ========== MÉTODOS ORIGINALES - NO SE TOCAN ==========

    @Override
    public int hashCode() {
        return Objects.hash(dias, id, nombres, salario);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Empleado other = (Empleado) obj;
        return dias == other.dias && id == other.id && Objects.equals(nombres, other.nombres)
                && Double.doubleToLongBits(salario) == Double.doubleToLongBits(other.salario);
    }

    @Override
    public String toString() {
        return "Empleado [id=" + id + ", nombres=" + nombres + ", salario=" + salario + ", dias=" + dias + "]";
    }
}