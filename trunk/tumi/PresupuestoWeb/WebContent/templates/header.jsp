<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<div>
	<script type="text/javascript" language="javascript">
		function confirmDelete() {
			return confirm('Esta seguro de eliminar el registro?');
		}
		function confirmReiniciar() {
			return confirm('Esta seguro de reiniciar la contraseña?');
		}
	</script>
	<h:form>
		<table border="0" width="1000px">
		<tr>
			<td style="width:200px;"></td>
			<td style="width:800px;"></td>
		</tr>
		<tr>
			<td height="75px">
				<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
					<tr>
					<td><h:graphicImage value="/images/icons/tumi.jpg" width="200px" height="75px"/></td>
					</tr>
				</table>
			</td>
			<td height="75px">
				<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
				<tr bgcolor="#89D887">
					<td style="width:400px;"></td>
					<td style="width:150px;">
						<h:outputText value=" Usuario: Yessica Tucto " style="color:#ffffff;padding-left: 5px;"/>
					</td>
					<td style="width:150px;">
						<h:commandLink id="linkContrasenha" value=" Cambiar Contraseña " action="cambioContrasenha" style="color:#ffffff;padding-left: 5px;"/>
					</td>
					<td style="width:100px;">
						<h:commandLink id="linkCerrarSession" value=" Salir " action="cerrarSession" style="color:#ffffff;padding-left: 5px;"/>
					</td>
				</tr>
				<tr bgcolor="#C3D79C">
					<td colspan="4">&nbsp;</td>
				</tr>
				</table>	
			</td>
		</tr>
		</table>
	</h:form>
</div>