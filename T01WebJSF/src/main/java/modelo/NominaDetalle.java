package modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class NominaDetalle implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Long id;
    private Empleado empleado;
    private String mes;
    private int año;
    private int diasLaborados;
    private LocalDate fechaCalculo;

    public NominaDetalle() {
        this.fechaCalculo = LocalDate.now();
    }
    
    public NominaDetalle(Empleado empleado, String mes, int año, int diasLaborados) {
        this();
        this.empleado = empleado;
        this.mes = mes;
        this.año = año;
        this.diasLaborados = diasLaborados;
    }

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Empleado getEmpleado() {
        return empleado;
    }
    
    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }
    
    public String getMes() {
        return mes;
    }
    
    public void setMes(String mes) {
        this.mes = mes;
    }
    
    public int getAño() {
        return año;
    }
    
    public void setAño(int año) {
        this.año = año;
    }
    
    public int getDiasLaborados() {
        return diasLaborados;
    }
    
    public void setDiasLaborados(int diasLaborados) {
        this.diasLaborados = diasLaborados;
    }
    
    public LocalDate getFechaCalculo() {
        return fechaCalculo;
    }
    
    public void setFechaCalculo(LocalDate fechaCalculo) {
        this.fechaCalculo = fechaCalculo;
    }

    public double getSalarioMensual() {
        if (empleado != null) {
            empleado.setDias(diasLaborados);
            return empleado.getSalarioMensual();
        }
        return 0;
    }
    
    public double getDescuentoSalud() {
        if (empleado != null) {
            empleado.setDias(diasLaborados);
            return empleado.getDescuentoSalud();
        }
        return 0;
    }
    
    public double getDescuentoPension() {
        if (empleado != null) {
            empleado.setDias(diasLaborados);
            return empleado.getDescuentoPension();
        }
        return 0;
    }
    
    public double getAuxilioTransporte() {
        if (empleado != null) {
            return empleado.getAuxilioTransporte();
        }
        return 0;
    }
    
    public double getTotalDescuentos() {
        if (empleado != null) {
            empleado.setDias(diasLaborados);
            return empleado.getTotalDescuentos();
        }
        return 0;
    }
    
    public double getTotalDevengados() {
        if (empleado != null) {
            empleado.setDias(diasLaborados);
            return empleado.getTotalDevengados();
        }
        return 0;
    }
    
    public double getNetoAPagar() {
        if (empleado != null) {
            empleado.setDias(diasLaborados);
            return empleado.getNetoAPagar();
        }
        return 0;
    }
    
    public double getTotalPagar() {
        if (empleado != null) {
            empleado.setDias(diasLaborados);
            return empleado.getTotalPagar();
        }
        return 0;
    }

    public String getPeriodoFormateado() {
        return mes + " " + año;
    }
    
    public String getFechaCalculoFormateada() {
        return fechaCalculo.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
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
    
    @Override
    public String toString() {
        return "NominaDetalle{" +
                "id=" + id +
                ", empleado=" + (empleado != null ? empleado.getNombres() : "null") +
                ", periodo=" + getPeriodoFormateado() +
                ", diasLaborados=" + diasLaborados +
                '}';
    }
}