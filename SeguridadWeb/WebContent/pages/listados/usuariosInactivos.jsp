<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<tiles:insert definition="page.template">
	<tiles:put name="pageTitle" value="Lista de Usuarios Inactivos"/>
	<tiles:put name="body" value="/pages/listados/usuariosInactivosBody.jsp" type="page"/>
</tiles:insert>