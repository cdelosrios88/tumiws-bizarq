package pe.com.tumi.common.util;

import java.io.File;

import javax.faces.context.FacesContext;


public class ConstanteReporte {
	
	/** Constante para definir un usuario en sesion */
	public static final String SESSION_USER = "usuarioEnSesion";
	/** Constante para definir el usuario de creacion del objeto */
	public static final String USUARIO_CREACION = "usuarioCreacion";
	/** Constante para definir el usuario de modificacion del objeto */
	public static final String USUARIO_MODIFICACION = "usuarioModificacion";
	
	//Controladores (no tiene relación con los nombres de controladores definidos en  
	//el application-context-mbean.xml)
	public static final String CONTROLLER_FOR_ALTA						= "__alta__";
	public static final String CONTROLLER_FOR_ALTUALIZACION_VRF			= "__act_vrf__";
	public static final String CONTROLLER_FOR_ALTUALIZACION_ETIQUETA	= "__act_etiqueta__";
	
	public static final Long TIPO_ACCESO_SSH						= 1L;
	public static final Long TIPO_ACCESO_TELNET						= 2L;
	
	
	public static final Long STATUS_ORDEN_PROCESANDO				= 11L;
	public static final Long STATUS_ORDEN_POR_ACTIVAR 				= 14L;
	public static final Long STATUS_ORDEN_PENDIENTE_DE_EJECUCION 	= 15L;
	public static final Long STATUS_ORDEN_EJECUTADO 				= 12L;
	public static final Long STATUS_ORDEN_ERROR 					= 13L;
	public static final Long STATUS_ORDEN_CANCELADO					= 16L;
	
	public static final Long STATUS_PUERTO_DISPONIBLE 				= 1L;
	public static final Long STATUS_PUERTO_AISLADO 					= 2L;
	public static final Long STATUS_PUERTO_DESHABILITADO 			= 3L;
	
	public static final Long TIPO_TECNOLOGIA_ATM					= 100L;
	public static final Long TIPO_TECNOLOGIA_ETHERNET				= 101L;
	public static final Long TIPO_TECNOLOGIA_GPUNTO					= 102L;
	public static final Long TIPO_TECNOLOGIA_CONEXION_DIRECTA		= 104L;
	public static final Long TIPO_TECNOLOGIA_ADTRAM					= 105L;
	public static final Long TIPO_TECNOLOGIA_TRANSMISION			= 106L;
	
	public static final Long TIPO_ROUTER_CISCO_7200					= 3003L;
	public static final Long TIPO_ROUTER_CISCO_7500					= 3004L;
	public static final Long TIPO_ROUTER_CISCO_12410				= 3007L;
	
	public static final Long TIPO_SERVICIO_VPN_L					= 2201L;
	public static final Long TIPO_SERVICIO_INTERNET_L				= 2202L;
	
	
	public final static String LOG_SEPARA_COLUMNA_RESPUESTA 		= "@@@";
	public final static String LOG_SEPARA_COLUMNA_RESPUESTA_ER 		= "@@@";
	public final static String LOG_SEPARA_FILA_RESPUESTA 			= "&&&";
	public final static String LOG_SEPARA_FILA_RESPUESTA_ER 		= "&&&"; 
	
	public final static Integer[] accessListExceptions = new Integer[]{2093,2094,2095,2096,2097,2097,2098,2099};
	
	public static final String PARAMETRO_INVENTARIO_USUARIO 		= "INVENTARIO_USUARIO";
	public static final String PARAMETRO_INVENTARIO_USUARIO_CONTRASENHA = "INVENTARIO_USUARIO_CONTRASENHA";
	public static final String PARAMETRO_INVENTARIO_FTP_DIRECCION 	=	"INVENTARIO_FTP_DIRECCION";
	public static final String PARAMETRO_INVENTARIO_FTP_PUERTO		=	"INVENTARIO_FTP_PUERTO";
	public static final String PARAMETRO_INVENTARIO_CADENA_CONEXION_PHP	=	"INVENTARIO_CADENA_CONEXION_PHP";
	public static final String PARAMETRO_INVENTARIO_FTP_USUARIO		=	"INVENTARIO_FTP_USUARIO";
	public static final String PARAMETRO_INVENTARIO_FTP_CONTRASENHA	=	"INVENTARIO_FTP_CONTRASENHA";
	public static final String PARAMETRO_INVENTARIO_RUTA_TEMP		=	"INVENTARIO_RUTA_TEMP";
	public static final String PARAMETRO_ARCHIVO_TEMPORAL			=	"ARCHIVO_TEMPORAL";
	public static final String PARAMETRO_RUTA_TEMPORALES			=   "RUTA_TEMPORALES";
	public static final String PARAMETRO_RUTA_EJECUTABLES			=   "RUTA_EJECUTABLES";
	
	//public static final String PARAMETRO_INVENTARIO_USUARIO_CONTRASENHA = "INVENTARIO_USUARIO_CONTRASENHA";
	
	public static final String BEAN_HELPER = "parametroHelper";
	
	public static final String ELEMENTO_RED_ROUTER = "ROUTER";
	/** Constante para definir la opción "--Seleccionar--" en una lista desplegable */
	public static final String NO_OPTION_SELECTED = "__none__";
	/** Constante para definir la llave que se empleara en la encriptacion */
	public static final String KEY = "blue";
	/** Constante para definir el estado ACTIVO */
	public static final String ESTADO_ACTIVO = "A";
	/** Constante para definir el estado INACTIVO */
	public static final String ESTADO_INACTIVO = "I";
	//Constantes para identificar las clases de IP
	public static final String IP_CLASE_A = "A";
	public static final String IP_CLASE_B = "B";
	public static final String IP_CLASE_C = "C";
	public static final String IP_CLASE_NO_IDENTIFICADA = "__none__";
	//Constantes para identificar mï¿½scaras de subred de las clases de IP
	public static final String MASCARA_SUBRED_CLASE_B = "255.255.0.0";
	public static final String MASCARA_SUBRED_CLASE_C = "255.255.255.0";
	public static final String MASCARA_SUBRED_NO_IDENTIFICADA = "__none__";
	
	public static final String MASCARA_SUBRED = "255.255.255.252";
	//Constantes para las descripciones de los estados ACTIVO e INACTIVO
	public static final String DESCRIPCION_ESTADO_ACTIVO = "Activo";
	public static final String DESCRIPCION_ESTADO_INACTIVO = "Inactivo";
	//Constantes para definir los tipos de archivos que exportarÃ¡n los reportes
	public static final String ARCHIVO_PDF = "__pdf__";
	public static final String ARCHIVO_EXCEL = "__excel__";
	public static final String ARCHIVO_HTML = "__html__";
	
	//CONSTANTES PARA EL MANEJO DE TERMINALES	
	public static final int SSH_SESSION_TIMEOUT = 15000;
	public static final int SSH_DEFAULT_PORT = 22; 
	public static final int TERMINAL_TIME_TO_WAIT_FOR_ANSWER = 4000;
	
	//CONSTANTES DE PROVISION
	public static final String SEPARADOR_RUTA = "/";
	public static final String SEPARADOR_TEXTO_PLANO = ",";
	
	//Constantes para definir los tipos de ubicación geográfica
	public static final String UBICACION_DEPARTAMENTO = "DEP";
	public static final String UBICACION_PROVINCIA = "PRV";
	public static final String UBICACION_DISTRITO = "DIS";
	//Constantes para definir los tipos de puertos (Físico o Lógico)
	public static final String PUERTO_FISICO = "F";
	public static final String PUERTO_LOGICO = "L";
	public static final String PUERTO_CHANNEL = "C";
	//Constantes para identificar el estado de un puerto físico al momento de relacionarlo 
	//con la ubicación
	public static final String PUERTO_ESTADO_INCLUIDO = "I";
	public static final String PUERTO_ESTADO_EXCLUIDO = "E";
	//Constantes para definir los discriminadores de elemento de red
	public static final String ELEMENTO_RED_PUERTO = "PUERTO";
	public static final String ELEMENTO_RED_SLOT = "SLOT";
	public static final String ELEMENTO_RED_SUBSLOT = "SUBSLOT";
	public static final String ELEMENTO_VRF = "VRF";
	
	//Constantes para definir la ubicacion de archivos jasper 
	public static final String SEPARADOR = File.separator;
	public static final String FLG_PUERTO_PREDETERMINADO = "9081";
	
	
	public static final String RUTA_REPORTES_COMUNES = SEPARADOR +"WEB-INF"+ SEPARADOR +"classes"+ SEPARADOR +"pe"+ SEPARADOR +"com"+ 
		SEPARADOR +"americatel"+ SEPARADOR + "reportes" + SEPARADOR + "reportes" + SEPARADOR;
	public static final String REPORTE_GENERICO_HORIZONTAL = RUTA_REPORTES_COMUNES + "R_Generico_H.jasper";
	public static final String REPORTE_GENERICO_VERTICAL = RUTA_REPORTES_COMUNES + "R_Generico_V.jasper";
	//public static final String FOOTER_HORIZONTAL = RUTA_REPORTES_COMUNES + "FooterH.jasper";
	//public static final String HEADER_HORIZONTAL = RUTA_REPORTES_COMUNES + "HeaderH.jasper";
	//public static final String FOOTER_VERTICAL = RUTA_REPORTES_COMUNES + "FooterV.jasper";
	//public static final String HEADER_VERTICAL = RUTA_REPORTES_COMUNES + "HeaderV.jasper";
	//public static final String IMAGEN_HEADER = RUTA_REPORTES_COMUNES + "header.jpg";
	
	//Constantes para especificar la carpeta donde se encuentran ubicados los archivos jasper 
	//para los reportes de inventario
//	public static final String RUTA_REPORTES_INVENTARIO = "\\WEB-INF\\classes\\pe\\com\\telefonica\\inventario\\reporte\\";
	/*public static final String RUTA_REPORTES_OOSS = SEPARADOR + "WEB-INF" + SEPARADOR + "classes" + SEPARADOR + "pe" + 
			SEPARADOR + "com" + SEPARADOR + "americatel" + SEPARADOR + "reportes" + SEPARADOR + "reportes" + SEPARADOR;
	*/
	
	//public static final String RUTA_REPORTES = SEPARADOR + "WEB-INF" + SEPARADOR + "classes" + SEPARADOR + "pe" + 
	//SEPARADOR + "com" + SEPARADOR + "americatel" + SEPARADOR + "provision" + SEPARADOR + "reporte" + SEPARADOR;
	
	//public static final String RUTA_REPORTES  = SEPARADOR+ "D:" +SEPARADOR + "Telefonica" + SEPARADOR +"factoring"+ SEPARADOR +"factoring" +	
	//SEPARADOR + "src"+ SEPARADOR +"main" +SEPARADOR +"java"+ SEPARADOR +"pe" +SEPARADOR + "com" +SEPARADOR+ "telefonica" +SEPARADOR+"reportes"+SEPARADOR;
	
	public static final String RUTA_LOG4J = "WEB-INF"+SEPARADOR+"classes"+SEPARADOR+"pe"+SEPARADOR+"com"+SEPARADOR+"tumi"+SEPARADOR+"conf"+SEPARADOR+"log4j.properties";
	
	public static final String RUTA_REPORTES = SEPARADOR + "WEB-INF" + SEPARADOR + "classes" + SEPARADOR + "pe" + 
	SEPARADOR + "com" + SEPARADOR + "telefonica" + SEPARADOR + "reportes" + SEPARADOR;
	
	public static final String RUTA_UPLOAD_FOTOS = "D:"+ SEPARADOR + "ProyectoTumi" + SEPARADOR + "tumi" + SEPARADOR +
	"src" + SEPARADOR + "main" + SEPARADOR + "webapp";
	
	public static final String URL_FOTOS = "http:"+ SEPARADOR + SEPARADOR + "localhost:" + FLG_PUERTO_PREDETERMINADO + SEPARADOR + "tumi";
	
	//http://localhost:9081/tumi
	//D:/ProyectoTumi/tumi/src/main/webapp
	public static final String RUTA_FOTOS = "/images/photographs/";
	
	public static final String RUTA_UPLOADED = "C:/Temporal/";
	
	//public static final String RUTA_UPLDFOTOS = "C:\Archivos de programa\IBM\WebSphere\AppServer\profiles\AppSrv02\installedApps\computer-1a2610Node01Cell\tumi_christian.ear\tumi.war\images\photographs";
	
	/*public static final String RUTA_REPORTES_PROVISION = SEPARADOR + "WEB-INF" + SEPARADOR + "classes" + SEPARADOR + "pe" + 
			SEPARADOR + "com" + SEPARADOR + "americatel" + SEPARADOR + "provision";*/
	//Parametros
	public static final String PARAMETRO_PAGINA_DEFAULT_ADMINISTRADOR = "PAG_DEFAULT_ADMIN";
	public static final String PARAMETRO_PAGINA_DEFAULT_OTHER_USERS = "PAG_DEFAULT_OTHER_USERS";
	public static final String PARAMETRO_RUTA_INVENTARIO = "RUTA_INVENTARIO";
	public static final String PARAMETRO_NIVEL_DEFINICION_UBICACION_PUERTO = "NIV_DEFINICION_UBIGEO_PUERTO";
	
	//Códigos de tipos de movimiento
	public static final String TIPO_MOVIMIENTO_ALTA 					= "9";
	public static final String TIPO_MOVIMIENTO_BAJA 					= "10";
	public static final String TIPO_MOVIMIENTO_ACTUALIZACION_VELOCIDAD 	= "11";
	public static final String TIPO_MOVIMIENTO_ACTUALIZACION_POOL 		= "12";
	public static final String TIPO_MOVIMIENTO_ACTUALIZACION_PVC 		= "13";
	public static final String TIPO_MOVIMIENTO_ACTUALIZACION_IP 		= "14";
	public static final String TIPO_MOVIMIENTO_ACTUALIZACION_ETIQUETA 	= "15";
	public static final String TIPO_MOVIMIENTO_ACTUALIZACION_VRF 		= "16";
	public static final String TIPO_MOVIMIENTO_ALTA_CDS 				= "17";
	public static final String TIPO_MOVIMIENTO_INVENTARIO_P_LOGICO 		= "18";
	public static final String TIPO_MOVIMIENTO_SEPARA_INTERFACE			= "19";
	public static final String TIPO_MOVIMIENTO_CORTE_RECONEXION   		= "20";
	public static final String TIPO_MOVIMIENTO_CORTE   					= "31";
	public static final String TIPO_MOVIMIENTO_RECONEXION   			= "32";
	
	public static final Long TIPO_MOVIMIENTO_ALTA_L						= 9L;
	public static final Long TIPO_MOVIMIENTO_BAJA_L						= 10L;
	public static final Long TIPO_MOVIMIENTO_ACTUALIZACION_VELOCIDAD_L 	= 11L;
	public static final Long TIPO_MOVIMIENTO_ACTUALIZACION_POOL_L 		= 12L;
	public static final Long TIPO_MOVIMIENTO_ACTUALIZACION_PVC_L 		= 13L;
	public static final Long TIPO_MOVIMIENTO_ACTUALIZACION_IP_L 		= 14L;
	public static final Long TIPO_MOVIMIENTO_ACTUALIZACION_ETIQUETA_L 	= 15L;
	public static final Long TIPO_MOVIMIENTO_ACTUALIZACION_VRF_L 		= 16L;
	public static final Long TIPO_MOVIMIENTO_ALTA_CDS_L 				= 17L;
	public static final Long TIPO_MOVIMIENTO_INVENTARIO_P_LOGICO_L 		= 18L;
	public static final Long TIPO_MOVIMIENTO_SEPARA_INTERFACE_L   		= 19L;
	public static final Long TIPO_MOVIMIENTO_CORTE_RECONEXION_L   		= 20L;
	public static final Long TIPO_MOVIMIENTO_CORTE_L   					= 31L;
	public static final Long TIPO_MOVIMIENTO_RECONEXION_L   			= 32L;
	
	//Códigos de tipos de servicio
	public static final String TIPO_SERVICIO_IPVPN = "2201";
	public static final String TIPO_SERVICIO_INTERNET = "2202";
	public static final String TIPO_SUB_SERVICIO_IPVPN = "3221";
	//Códigos de tipos de subservicio
	public static final String TIPO_SUBSERVICIO_ATM = "2205";
	
	public static final Long TIPO_SUBSERVICIO_ATM_L 					= 2205L;
	
	//Niveles de ubicación del usados por el parámetro PARAMETRO_NIVEL_DEFINICION_UBICACION_PUERTO
	public static final String PUERTO_UBIGEO_NIVEL_DEPARTAMENTO = "PUDEP";
	public static final String PUERTO_UBIGEO_NIVEL_PROVINCIA = "PUPRV";
	public static final String PUERTO_UBIGEO_NIVEL_DISTRITO = "PUDIS";
	
	//Parámetros para reporte de ocupación de caudal
	public static final String REPORTE_PARAMETRO_SHOW_SLOT = "SLT";
	public static final String REPORTE_PARAMETRO_SHOW_INTERFACE = "INT";
	public static final String REPORTE_PARAMETRO_SHOW_DETAIL = "DET";
	public static final String REPORTE_PARAMETRO_SHOW_GRAPHIC = "GPH";
	
	//Protocolos de configuración
	public static final String PROTOCOLO_CONFIGURACION_BGP = "BGP";
	public static final String PROTOCOLO_CONFIGURACION_RIP = "RIP";
	public static final String PROTOCOLO_CONFIGURACION_ESTATICO = "EST";
	
	//Nümero de columnas del archivo de texto para la carga masiva de órdenes de servicio
	public static final Integer FORMATO_ALTA_NUMERO_TOTAL_COLUMNAS 						= 29;
	public static final Integer FORMATO_BAJA_NUMERO_TOTAL_COLUMNAS 						= 3;
	public static final Integer FORMATO_ACTUALIZACION_POOL_NUMERO_TOTAL_COLUMNAS 		= 4;
	public static final Integer FORMATO_ACTUALIZACION_VELOCIDAD_NUMERO_TOTAL_COLUMNAS 	= 4;
	public static final Integer FORMATO_ACTUALIZACION_ETIQUETA_NUMERO_TOTAL_COLUMNAS 	= 27;
	public static final Integer FORMATO_ACTUALIZACION_PVC_NUMERO_TOTAL_COLUMNAS 		= 6;
	public static final Integer FORMATO_ACTUALIZACION_IP_NUMERO_TOTAL_COLUMNAS 			= 5;
	public static final Integer FORMATO_ACTUALIZACION_VRF_NUMERO_TOTAL_COLUMNAS 		= 7;
	
	//Posiciones para las columnas para el envío masivo de ALTAS de Orden de Servicio
	public static final Integer FORMATO_ALTA_TIPO_SERVICIO = 0; 
	public static final Integer FORMATO_ALTA_TIPO_SUBSERVICIO = 1;
	public static final Integer FORMATO_ALTA_TIPO_TECNOLOGIA = 2;
	public static final Integer FORMATO_ALTA_CODIGO_DIGITAL = 3;
	public static final Integer FORMATO_ALTA_ROUTER = 4;
	public static final Integer FORMATO_ALTA_PUERTO_FISICO = 5;
	public static final Integer FORMATO_ALTA_IP_CLIENTE = 6;
	public static final Integer FORMATO_ALTA_MASCARA = 7;
	public static final Integer FORMATO_ALTA_VELOCIDAD_DESCARGA = 8;
	public static final Integer FORMATO_ALTA_VELOCIDAD_SUBIDA = 9;
	public static final Integer FORMATO_ALTA_VLAN = 10;
	public static final Integer FORMATO_ALTA_SVLAN = 11;
	public static final Integer FORMATO_ALTA_VPI = 12;
	public static final Integer FORMATO_ALTA_VCI = 13;
	public static final Integer FORMATO_ALTA_VRF_CODIGO = 14; // Aplica tambien para CODIGO CLIENTE
	public static final Integer FORMATO_ALTA_VRF_RD = 15;
	public static final Integer FORMATO_ALTA_VRF_AS = 16;
	public static final Integer FORMATO_ALTA_RAZONSOCIAL = 17;
//	public static final Integer FORMATO_ALTA_TELEFONO = 18;
//	public static final Integer FORMATO_ALTA_DIRECCION = 19;
	public static final Integer FORMATO_ALTA_PROTOCOLO_CONFIGURACION = 18;
	public static final Integer FORMATO_ALTA_CAUDAL_GESTION = 19;
	public static final Integer FORMATO_ALTA_CAUDAL_VOZ = 20;
	public static final Integer FORMATO_ALTA_CAUDAL_VIDEO = 21;
	public static final Integer FORMATO_ALTA_CAUDAL_PLATINO = 22;
	public static final Integer FORMATO_ALTA_CAUDAL_ORO = 23;
	public static final Integer FORMATO_ALTA_CAUDAL_PLATA = 24;
	public static final Integer FORMATO_ALTA_CAUDAL_BRONCE = 25;
	public static final Integer FORMATO_ALTA_CAUDAL_LDN = 26;
	public static final Integer FORMATO_ALTA_ACCESS_LIST = 27;
	public static final Integer FORMATO_ALTA_CIUDAD = 28;
	public static final Integer FORMATO_ALTA_OBSERVACIONES = 29;
	public static final Integer FORMATO_ALTA_FONO = 30;
	public static final Integer FORMATO_ALTA_SHUTDOWN = 31;
	
	//expresiones regulares
	public static final String MATCH_PROMPT = "^.*\r\n[a-zA-Z0-9]*[>|#]$";
	
	//Posiciones para las columnas para el envío masivo de BAJAS de Orden de Servicio
	public static final Integer FORMATO_BAJA_CODIGO_DIGITAL = 0;
	public static final Integer FORMATO_BAJA_ROUTER = 1;
	public static final Integer FORMATO_BAJA_PUERTO_LOGICO = 2;
	public static final Integer FORMATO_BAJA_FORZOSA = 3;
	public static final Integer FORMATO_BAJA_OBSERVACIONES = 4;
	
	//Posiciones para las columnas para el envío masivo de ACTUALIZACIÓN DE POOL
	public static final Integer FORMATO_ACTUALIZACION_POOL_CODIGO_DIGITAL = 0;
	public static final Integer FORMATO_ACTUALIZACION_POOL_ROUTER = 1;
	public static final Integer FORMATO_ACTUALIZACION_POOL_PUERTO_LOGICO = 2;
	public static final Integer FORMATO_ACTUALIZACION_POOL_ACCESS_LIST = 3;
	
	//Posiciones para las columnas para el envío masivo de ACTUALIZACIÓN DE VELOCIDAD
	public static final Integer FORMATO_ACTUALIZACION_VELOCIDAD_CODIGO_DIGITAL = 0;
	public static final Integer FORMATO_ACTUALIZACION_VELOCIDAD_ROUTER = 1;
	public static final Integer FORMATO_ACTUALIZACION_VELOCIDAD_PUERTO_LOGICO = 2;
	public static final Integer FORMATO_ACTUALIZACION_VELOCIDAD_VELOCIDAD_DESCARGA = 3;
	public static final Integer FORMATO_ACTUALIZACION_VELOCIDAD_VELOCIDAD_SUBIDA = 4;
	public static final Integer FORMATO_ACTUALIZACION_VELOCIDAD_CAUDAL_GESTION = 5;
	public static final Integer FORMATO_ACTUALIZACION_VELOCIDAD_CAUDAL_VOZ = 6;
	public static final Integer FORMATO_ACTUALIZACION_VELOCIDAD_CAUDAL_VIDEO = 7;
	public static final Integer FORMATO_ACTUALIZACION_VELOCIDAD_CAUDAL_PLATINO = 8;
	public static final Integer FORMATO_ACTUALIZACION_VELOCIDAD_CAUDAL_ORO = 9;
	public static final Integer FORMATO_ACTUALIZACION_VELOCIDAD_CAUDAL_PLATA = 10;
	public static final Integer FORMATO_ACTUALIZACION_VELOCIDAD_CAUDAL_BRONCE = 11;
	public static final Integer FORMATO_ACTUALIZACION_VELOCIDAD_CAUDAL_LDN = 12;
	
	//Posiciones para las columnas para el envío masivo de ACTUALIZACIÓN DE ETIQUETA
	public static final Integer FORMATO_ACTUALIZACION_ETIQUETA_TIPO_SERVICIO = 0;
	public static final Integer FORMATO_ACTUALIZACION_ETIQUETA_TIPO_SUBSERVICIO = 1;
	public static final Integer FORMATO_ACTUALIZACION_ETIQUETA_TIPO_TECNOLOGIA = 2;
	public static final Integer FORMATO_ACTUALIZACION_ETIQUETA_CODIGO_DIGITAL = 3;
	public static final Integer FORMATO_ACTUALIZACION_ETIQUETA_ROUTER = 4;
	public static final Integer FORMATO_ACTUALIZACION_ETIQUETA_PUERTO_LOGICO = 5;
	public static final Integer FORMATO_ACTUALIZACION_ETIQUETA_NODO = 6;
	public static final Integer FORMATO_ACTUALIZACION_ETIQUETA_VRF_CODIGO = 7;
	public static final Integer FORMATO_ACTUALIZACION_ETIQUETA_VRF_RD = 8;
	public static final Integer FORMATO_ACTUALIZACION_ETIQUETA_VRF_AS = 9;
	public static final Integer FORMATO_ACTUALIZACION_ETIQUETA_RAZONSOCIAL = 10;
	public static final Integer FORMATO_ACTUALIZACION_ETIQUETA_VELOCIDAD_DESCARGA = 11;
	public static final Integer FORMATO_ACTUALIZACION_ETIQUETA_VELOCIDAD_SUBIDA = 12;
	public static final Integer FORMATO_ACTUALIZACION_ETIQUETA_VLAN = 13;
	public static final Integer FORMATO_ACTUALIZACION_ETIQUETA_SVLAN = 14;
	public static final Integer FORMATO_ACTUALIZACION_ETIQUETA_VPI = 15;
	public static final Integer FORMATO_ACTUALIZACION_ETIQUETA_VCI = 16;
	public static final Integer FORMATO_ACTUALIZACION_ETIQUETA_PROTOCOLO_CONFIGURACION = 17;
	public static final Integer FORMATO_ACTUALIZACION_ETIQUETA_CAUDAL_GESTION = 18;
	public static final Integer FORMATO_ACTUALIZACION_ETIQUETA_CAUDAL_VOZ = 19;
	public static final Integer FORMATO_ACTUALIZACION_ETIQUETA_CAUDAL_VIDEO = 20;
	public static final Integer FORMATO_ACTUALIZACION_ETIQUETA_CAUDAL_PLATINO = 21;
	public static final Integer FORMATO_ACTUALIZACION_ETIQUETA_CAUDAL_ORO = 22;
	public static final Integer FORMATO_ACTUALIZACION_ETIQUETA_CAUDAL_PLATA = 23;
	public static final Integer FORMATO_ACTUALIZACION_ETIQUETA_CAUDAL_BRONCE = 24;
	public static final Integer FORMATO_ACTUALIZACION_ETIQUETA_CAUDAL_LDN = 25;
	public static final Integer FORMATO_ACTUALIZACION_ETIQUETA_CIUDAD = 26;
	public static final Integer FORMATO_ACTUALIZACION_ETIQUETA_OBSERVACIONES = 27;
	public static final Integer FORMATO_ACTUALIZACION_ETIQUETA_FONO = 28;
	
	//Posiciones para las columnas para el envío masivo de ACTUALIZACIÓN DE PVC
	public static final Integer FORMATO_ACTUALIZACION_PVC_CODIGO_DIGITAL = 0;
	public static final Integer FORMATO_ACTUALIZACION_PVC_ROUTER = 1;
	public static final Integer FORMATO_ACTUALIZACION_PVC_PUERTO_LOGICO = 2;
	public static final Integer FORMATO_ACTUALIZACION_PVC_PVC = 3;
	public static final Integer FORMATO_ACTUALIZACION_PVC_VPI = 4;
	public static final Integer FORMATO_ACTUALIZACION_PVC_VCI = 5;
	
	//Posiciones para las columnas para el envío masivo de ACTUALIZACIÓN DE IP
	public static final Integer FORMATO_ACTUALIZACION_IP_CODIGO_DIGITAL = 0;
	public static final Integer FORMATO_ACTUALIZACION_IP_ROUTER = 1;
	public static final Integer FORMATO_ACTUALIZACION_IP_PUERTO_LOGICO = 2;
	public static final Integer FORMATO_ACTUALIZACION_IP_NUEVA_IP = 3;
	public static final Integer FORMATO_ACTUALIZACION_IP_NUEVA_MASCARA = 4;
	
	//Posiciones para las columnas para el envío masivo de ACTUALIZACIÓN DE VRF
	public static final Integer FORMATO_ACTUALIZACION_VRF_CODIGO_DIGITAL = 0;
	public static final Integer FORMATO_ACTUALIZACION_VRF_ROUTER = 1;
	public static final Integer FORMATO_ACTUALIZACION_VRF_PUERTO_LOGICO = 2;
	public static final Integer FORMATO_ACTUALIZACION_VRF_CODIGO = 3;
	public static final Integer FORMATO_ACTUALIZACION_VRF_AS = 4;
	public static final Integer FORMATO_ACTUALIZACION_VRF_RD = 5;
	public static final Integer FORMATO_ACTUALIZACION_VRF_RAZONSOCIAL = 6;
	
	
	// Utilizafas para la interface serial
	public static final String TIMESLOT_CHARACTER_SLOT_LIBRE = "X";
	public static final String TIMESLOT_CHARACTER_SEPARADOR = ".";
	public static final int TIMESLOT_POSICION_MAX = 31;
	public static final int TIMESLOT_POSICION_MIN = 1;
	public static final int TIMESLOT_ANCHO_BANDA_KBITS = 64;
	public static final String TIMESLOT_ARREGLO_VACIO = "X.X.X.X.X.X.X.X.X.X.X.X.X.X.X.X.X.X.X.X.X.X.X.X.X.X.X.X.X.X.X";
	
	//Menu
	public static final String TIPO_MENU_NODO = "N";
	public static final String TIPO_MENU_PADRE = "F";
	public static final String SISTEMA_OPERATIVO_WIN = "WIN";
	public static final String SISTEMA_OPERATIVO_OTRO = "OTRO";
	
	public static final String BAJA_FORZOSA_ACTIVO = "A";
	public static final String BAJA_FORZOSA_INACTIVO = "I";
	
	//Balanceo
	public static final String BALANCEO_NO = "NO";
	public static final String BALANCEO_POR_PAQUETE = "PAQ";
	
	public static final String TIPO_CONTROLADOR_SONET = "SO";
	public static final String TIPO_CONTROLADOR_E3 = "E3";
	public static final String TIPO_CONTROLADOR_E1 = "E1";
	
	//Estado de la interface 
	public static final String ESTADO_INTERFACE_SHUTDOWN = "SHUT";
	public static final String ESTADO_INTERFACE_NO_SHUTDOWN = "NOSHUT";
	public static final String [] subNetMaskC = {"255.255.255.0","255.255.255.128",
												"255.255.255.192","255.255.255.224",
												"255.255.255.240","255.255.255.248",
												"255.255.255.252"};
	public static final String [] subNetMaskB = {"255.255.0.0","255.255.128.0",
												"255.255.192.0","255.255.224.0",
												"255.255.240.0","255.255.248.0",
												"255.255.252.0","255.255.254.0"};
	public static final String [] subNetMaskA = {"255.0.0.0","255.128.0.0",
												"255.192.0.0","255.224.0.0",
												"255.240.0.0","255.248.0.0",
												"255.252.0.0","255.254.0.0"};
	
	public static final String TIPO_ENCAPSULAMIENTO_PPP = "PPP";
	public static final String TIPO_ENCAPSULAMIENTO_FRAME_RELAY = "FRAME-RELAY";
	public static final String TIPO_ENCAPSULAMIENTO_FRAME_RELAY_POINT_TO_POINT = "FRAME-RELAY-POINT-TO-POINT";
	
	public static final String OPCION_BAJA_RUTEOS = "BR";
	public static final String OPCION_BAJA_ACCESS_LIST = "BA";
	public static final String OPCION_BAJA_CHANNEL_GROUP = "BC";
	
	
}