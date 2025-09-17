package bean;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import modelo.Empleado;
import modelo.NominaDetalle;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Named
@SessionScoped
public class NominaBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Inject
    private EmpleadoBean empleadoBean;
    
    // Propiedades para el cálculo de nómina
    private NominaDetalle nominaDetalle = new NominaDetalle();
    private List<NominaDetalle> nominas = new ArrayList<>();
    private String mesSeleccionado;
    private int añoSeleccionado = LocalDate.now().getYear();
    private Long empleadoIdSeleccionado;
    private int diasLaborados = 30;
    
    // Lista de meses
    private final List<String> meses = Arrays.asList(
        "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
        "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
    );
    
    public String calcularNominaMensual() {
        try {
            // Validar datos
            if (!validarDatosNomina()) {
                return null;
            }
            
            // Buscar empleado
            Empleado empleadoEncontrado = buscarEmpleadoPorId(empleadoIdSeleccionado);
            if (empleadoEncontrado == null) {
                mostrarMensajeError("No se encontró empleado con ID: " + empleadoIdSeleccionado);
                return null;
            }
            
            // Crear detalle de nómina
            nominaDetalle = new NominaDetalle(empleadoEncontrado, mesSeleccionado, añoSeleccionado, diasLaborados);
            nominaDetalle.setId(System.currentTimeMillis()); 
            
            // Agregar a la lista de nóminas calculadas
            nominas.add(nominaDetalle);
            
            mostrarMensajeExito("Nómina calculada exitosamente para " + empleadoEncontrado.getNombres());
            
            return "detalleNomina?faces-redirect=true";
            
        } catch (Exception e) {
            mostrarMensajeError("Error al calcular nómina: " + e.getMessage());
            return null;
        }
    }
    
    public void prepararNuevoCalculo() {
        nominaDetalle = new NominaDetalle();
        empleadoIdSeleccionado = null;
        diasLaborados = 30;
        mesSeleccionado = null;
    }
    
    public void seleccionarEmpleadoParaNomina(Long empleadoId) {
        this.empleadoIdSeleccionado = empleadoId;
        Empleado emp = buscarEmpleadoPorId(empleadoId);
        if (emp != null) {
            mostrarMensajeInfo("Empleado seleccionado: " + emp.getNombres());
        }
    }

    
    public List<NominaDetalle> getNominasPorMes(String mes, int año) {
        return nominas.stream()
                .filter(n -> n.getMes().equals(mes) && n.getAño() == año)
                .toList();
    }
    
    public List<NominaDetalle> getNominasPorEmpleado(Long empleadoId) {
        return nominas.stream()
                .filter(n -> n.getEmpleado().getId() == empleadoId)
                .toList();
    }
    
    public NominaDetalle getUltimaNominaEmpleado(Long empleadoId) {
        return nominas.stream()
                .filter(n -> n.getEmpleado().getId() == empleadoId)
                .max((n1, n2) -> n1.getFechaCalculo().compareTo(n2.getFechaCalculo()))
                .orElse(null);
    }

    public String generarReporteDetallado() {
        if (nominaDetalle.getEmpleado() == null) {
            mostrarMensajeWarn("No hay nómina seleccionada para generar reporte");
            return null;
        }
        
        StringBuilder reporte = new StringBuilder();
        reporte.append("=== REPORTE DETALLADO DE NÓMINA ===\n\n");

        reporte.append("DATOS DEL EMPLEADO:\n");
        reporte.append("Nombre: ").append(nominaDetalle.getEmpleado().getNombres()).append("\n");
        reporte.append("ID: ").append(nominaDetalle.getEmpleado().getId()).append("\n");
        reporte.append("Período: ").append(nominaDetalle.getPeriodoFormateado()).append("\n");
        reporte.append("Días laborados: ").append(nominaDetalle.getDiasLaborados()).append("\n");
        reporte.append("Fecha de cálculo: ").append(nominaDetalle.getFechaCalculoFormateada()).append("\n\n");

        reporte.append("DEVENGADOS:\n");
        reporte.append("Salario base mensual: ").append(nominaDetalle.getEmpleado().getSalarioFormateado()).append("\n");
        reporte.append("Salario por días trabajados: ").append(nominaDetalle.getSalarioMensualFormateado()).append("\n");
        reporte.append("Auxilio de transporte: ").append(nominaDetalle.getAuxilioTransporteFormateado()).append("\n");
        reporte.append("TOTAL DEVENGADOS: ").append(nominaDetalle.getTotalDevengadosFormateado()).append("\n\n");

        reporte.append("DESCUENTOS DE LEY:\n");
        reporte.append("Salud (4%): ").append(nominaDetalle.getDescuentoSaludFormateado()).append("\n");
        reporte.append("Pensión (4%): ").append(nominaDetalle.getDescuentoPensionFormateado()).append("\n");
        reporte.append("TOTAL DESCUENTOS: ").append(nominaDetalle.getTotalDescuentosFormateado()).append("\n\n");

        reporte.append("NETO A PAGAR: ").append(nominaDetalle.getNetoAPagarFormateado()).append("\n\n");

        reporte.append("COMPARACIÓN:\n");
        reporte.append("Cálculo simple (método original): ").append(nominaDetalle.getTotalPagarFormateado()).append("\n");
        reporte.append("Cálculo real (con descuentos): ").append(nominaDetalle.getNetoAPagarFormateado()).append("\n");
        
        mostrarMensajeExito("Reporte generado exitosamente");
        
        return reporte.toString();
    }

    private boolean validarDatosNomina() {
        boolean valido = true;

        if (empleadoIdSeleccionado == null || empleadoIdSeleccionado <= 0) {
            mostrarMensajeError("Debe seleccionar un empleado");
            valido = false;
        }

        if (mesSeleccionado == null || mesSeleccionado.trim().isEmpty()) {
            mostrarMensajeError("Debe seleccionar un mes");
            valido = false;
        }

        if (añoSeleccionado < 2020 || añoSeleccionado > LocalDate.now().getYear() + 1) {
            mostrarMensajeError("Año debe estar entre 2020 y " + (LocalDate.now().getYear() + 1));
            valido = false;
        }

        if (diasLaborados < 1 || diasLaborados > 30) {
            mostrarMensajeError("Días laborados debe estar entre 1 y 30");
            valido = false;
        }
        
        return valido;
    }
    
    private Empleado buscarEmpleadoPorId(Long id) {
        return empleadoBean.getEmpleados().stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);
    }

    private void mostrarMensajeExito(String mensaje) {
        FacesContext.getCurrentInstance().addMessage(null, 
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", mensaje));
    }
    
    private void mostrarMensajeError(String mensaje) {
        FacesContext.getCurrentInstance().addMessage(null, 
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", mensaje));
    }
    
    private void mostrarMensajeWarn(String mensaje) {
        FacesContext.getCurrentInstance().addMessage(null, 
            new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", mensaje));
    }
    
    private void mostrarMensajeInfo(String mensaje) {
        FacesContext.getCurrentInstance().addMessage(null, 
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", mensaje));
    }

    public NominaDetalle getNominaDetalle() {
        return nominaDetalle;
    }
    
    public void setNominaDetalle(NominaDetalle nominaDetalle) {
        this.nominaDetalle = nominaDetalle;
    }
    
    public List<NominaDetalle> getNominas() {
        return nominas;
    }
    
    public void setNominas(List<NominaDetalle> nominas) {
        this.nominas = nominas;
    }
    
    public String getMesSeleccionado() {
        return mesSeleccionado;
    }
    
    public void setMesSeleccionado(String mesSeleccionado) {
        this.mesSeleccionado = mesSeleccionado;
    }
    
    public int getAñoSeleccionado() {
        return añoSeleccionado;
    }
    
    public void setAñoSeleccionado(int añoSeleccionado) {
        this.añoSeleccionado = añoSeleccionado;
    }
    
    public Long getEmpleadoIdSeleccionado() {
        return empleadoIdSeleccionado;
    }
    
    public void setEmpleadoIdSeleccionado(Long empleadoIdSeleccionado) {
        this.empleadoIdSeleccionado = empleadoIdSeleccionado;
    }
    
    public int getDiasLaborados() {
        return diasLaborados;
    }
    
    public void setDiasLaborados(int diasLaborados) {
        this.diasLaborados = diasLaborados;
    }
    
    public List<String> getMeses() {
        return meses;
    }
    
    public EmpleadoBean getEmpleadoBean() {
        return empleadoBean;
    }
    
    public void setEmpleadoBean(EmpleadoBean empleadoBean) {
        this.empleadoBean = empleadoBean;
    }
}