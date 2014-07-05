<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>

		<h:panelGrid columns="2" width="600">
			<h:graphicImage value="/factoring/images/icons/lock.jpg" width="110" height="130"/>
			<h:outputText style="font-size: 11px; text-align: justify;" value="Advertencia: El sistema de control de accesos solicita el cambio de 
				contraseņa debido a que la contraseņa proporcionada ha expirado (35 dias o mas desde la ultima actualizacion),
				el usuario administrador ha reinciado la contraseņa o es la primera vez que inicia sesion. Para restablecer la contraseņa haga clic sobre la opcion 
				'Cambiar Contraseņa' y vuelva a iniciar sesion."/>
		</h:panelGrid>
