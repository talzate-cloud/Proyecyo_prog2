package bean;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class HolaMundoBean {
	public String getMensaje() {

		return "Hola Mundo (Tomcat 10, JSF 4, Jakarta EE 10, Prime Faces 13)";
	}
}
