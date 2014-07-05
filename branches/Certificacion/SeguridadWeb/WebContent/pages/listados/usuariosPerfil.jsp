<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<tiles:insert definition="page.template">
	<tiles:put name="pageTitle" value="Lista de Usuarios por Perfil"/>
	<tiles:put name="body" value="/pages/listados/usuariosPerfilBody.jsp" type="page"/>
</tiles:insert>