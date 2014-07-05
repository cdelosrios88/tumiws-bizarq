<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.tumi.com.pe/tumi-h" prefix="tumih"%>
	<!-- Empresa   : Cooperativa El Tumi		-->
	<!-- Autor     : Christian De los Ríos   	-->
	<!-- Fecha     : 24/05/2014               	-->

<h:form id="frmFenacrep">
   	<rich:panel id="rpTabFenacrep" style="border:1px solid #17356f;width:980px;background-color:#DEEBF5">
	   	<div align="center">
			<b>REPORTE FENACREP</b>
		</div>
		<br/>
		<table width="400px" border="0" align="center">
			<tr>
	            <td width="80px">
					<h:outputText value="Periodo :"/>
				</td>
				<td width="100px" align="left">
					<h:selectOneMenu id="cboMes" value="#{fenacrepController.intParaMes}">
						<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
						<tumih:selectItems var="sel" cache="#{applicationScope.Constante.PARAM_T_MES_CALENDARIO}" 
							itemValue="#{sel.intIdDetalle}" itemLabel="#{sel.strDescripcion}" propertySort="intOrden"
							tipoVista="#{applicationScope.Constante.CACHE_TOTAL}"/>
					</h:selectOneMenu>
				</td>
				<td width="100px" align="left">
					<h:selectOneMenu id="cboAnioBusq" value="#{fenacrepController.intParaAnio}">
						<f:selectItem itemLabel="Seleccione.." itemValue="0"/>
						<f:selectItems id="lstYears" value="#{fenacrepController.listYears}"/>
					</h:selectOneMenu>
				</td>
				<td width="80px">
					<h:commandButton id="btnReporte" value="Reporte" styleClass="btnEstilos1" 
						action="#{fenacrepController.imprimirReporteFenacrep}"/>
				</td>
	        </tr>
        </table>
	</rich:panel>
</h:form>