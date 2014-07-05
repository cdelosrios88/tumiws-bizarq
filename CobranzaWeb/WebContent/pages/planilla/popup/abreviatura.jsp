<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa Tumi         -->
	<!-- Autor     : FRANKO YALICO CAVERO    -->
	<!-- Modulo    : Cobranza                 -->
	<!-- Prototipo : EFECTUADO   -->
	<!-- Fecha     : 25/01/2013               -->

<h:panelGrid columns="1" style="border:0px">
	<h:column>
		<table border="0">
		<tr>
		  	<td style="width:50px;"></td>
		  	<td style="width:50px;"></td>
			<td style="width:50px;"></td>
			<td style="width:50px;"></td>
			<td style="width:50px;"></td>
			<td style="width:50px;"></td>
			<td style="width:50px;"></td>
			<td style="width:50px;"></td>
			<td style="width:50px;"></td>
		  	<td style="width:50px;"></td>
		  	<td style="width:50px;"></td>
		  	<td style="width:50px;"></td>
		</tr>
		
		<tr>
		<td colspan="12" align="left">
			<h:outputText value="(DEST): Personal destacado, si el recuadro se encuentra en fondo celeste es editable y la planilla
								 aún no se envía, si tiene fondo blanco el monto no es editable y la planilla fuen enviada."/>
			</td>
		</tr>
		
		<tr>
			<td colspan="12" align="left">
				<h:outputText value="(LIC): Personal en licencia, conforme al último padrón existente en el sistema."/>
			</td>
		</tr>
		
		<tr>
			<td colspan="12" align="left"><h:outputText value="(DEPEN): Personal corresponde a otra dependencia, conforme al último padrón existente en el sistema."/></td>
		</tr>
		
		<tr>
			<td colspan="12" align="left"><h:outputText value="(DJUD): Personal tiene descuento judicial, conforme al último padrón existente en el sistema."/></td>
		</tr>
		
		<tr>
			<td colspan="12" align="left"><h:outputText value="(100%): Personal tiene una carta al 100% en el último crédito, conforme al último padrón existente en el sistema."/>
			</td>
		</tr>
		
		<tr>
			<td colspan="12" align="left"><h:outputText value="Monto Envío: Pueden modificar los montos de envío mas no el total calculado por el sistema."/>
			</td>
		</tr>
		
		<tr>
			<td colspan="12" align="left"><h:outputText value="Tipo de planilla: Pueden agregar el tipo de planilla Haberes o Incentivos, en caso no lo tuviera asignado."/>
			</td>
		</tr>
		
		<tr>
			<td colspan="12">&nbsp;</td>
		</tr>
		
		</table>
	</h:column>
</h:panelGrid>
