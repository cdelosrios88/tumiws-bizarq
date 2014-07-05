<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
	<meta http-equiv="Cache-Control" content="no-store"/>
	<meta name="keywords" content=""/>
	<meta name="description" content=""/>
	<title>ERP Tumi <tiles:getAsString name="pageTitle" ignore="true" /></title>
	

	<link  rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reset-fonts-grids.css"/>
	 <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/default1.css"/>  
	<script type="text/javascript">
		 A4J.AJAX.onError = function(req, status, message){
			window.alert("Custom onError handler "+message);
		}
	</script>
</head>
<body >
<f:view>
	<div id="doc4" class="loginfactoring">
		<div id="hd">
			
		</div>
		<div id="bd">
			<div id="yui-main">
				<div class="yui-b" >
					<!-- MAIN INICIO -->
					<div>
						<tiles:insert attribute="body" flush="false" />
					</div>
				</div>
			</div>
		</div>
		<div id="ft">
				
		</div>
	</div>
</f:view>
</body>
</html>