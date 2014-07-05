<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<tiles:insert definition="page.template">
	<tiles:put name="pageTitle" value="Lista de Opciones de Menu agrupados por Usuario - Rol"/>
	<tiles:put name="body" value="/pages/listados/usuariosPerfilMenuBody.jsp" type="page"/>
</tiles:insert>