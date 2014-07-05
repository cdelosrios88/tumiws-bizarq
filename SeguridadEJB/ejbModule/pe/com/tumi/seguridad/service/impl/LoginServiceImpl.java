package pe.com.tumi.seguridad.service.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.dao.GenericDao;
import pe.com.tumi.common.service.impl.GenericServiceImpl;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.DaoException;
import pe.com.tumi.common.util.DateHelper;
import pe.com.tumi.common.util.Encriptador;
import pe.com.tumi.common.util.ParametroHelper;
import pe.com.tumi.common.util.StringHelper;
import pe.com.tumi.seguridad.domain.LoginLog;
import pe.com.tumi.seguridad.domain.Usuario;
import pe.com.tumi.seguridad.service.LoginService;

public class LoginServiceImpl extends GenericServiceImpl implements LoginService{

	protected static Logger log = Logger.getLogger(LoginServiceImpl.class);
	private GenericDao usuarioDAO;
	private GenericDao loginLogDAO;
	private ParametroHelper parametroHelper;
	private String mensaje;
	private DecimalFormat formatoDecimal = new DecimalFormat("####.#");
	private Date ultimoIngreso;
	private Integer intentosValidos;
	private Integer intentosRechazados;
	
	public HashMap validateUsuario(Usuario usuario, int intento, Long sistema ) throws DaoException{
		try{		
			log.info("Inicio de Sesion al:" + DateHelper.getFechaActual());
			HashMap map = new HashMap();
			HashMap mapLogin = new HashMap();
			HashMap mapLoginLog = new HashMap();
			String message = "";
			String flag = "false";
			String redirectTo = "login";
	
			//Loading parameters
			int maximoTiempoInactividad = Integer.valueOf(parametroHelper.getParametro(Constante.MAXIMO_TIEMPO_INACTIVIDAD, sistema)).intValue();
			int maximoTiempoSinCambioPass = Integer.valueOf(parametroHelper.getParametro(Constante.MAXIMO_TIEMPO_SIN_CAMBIO_PASS, sistema)).intValue();
			double tiempoEsperaReactivacion = Double.valueOf(parametroHelper.getParametro(Constante.TIEMPO_ESPERA_REACTIVACION_DIAS, sistema)).doubleValue();
			int maximoIntentosLogin = Integer.valueOf(parametroHelper.getParametro(Constante.MAXIMO_NRO_INTENTOS_LOGIN, sistema)).intValue();
	
			boolean esPasswordErrado = false;
			String localHost = "";
			try {
				localHost = String.valueOf(InetAddress.getLocalHost());
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
				log.debug("IP/Host no definido");
			}
			if (usuario.getCodigo() != null ){
				log.info("Login :"  + usuario.getCodigo().trim());
				String enc_clave =  usuario.getContrasenha();
				log.info("Clave :"  + enc_clave );
				enc_clave = Encriptador.encryptBlowfish(enc_clave, Constante.KEY );
				usuario.setContrasenha(enc_clave);
				usuario.setCodigo(usuario.getCodigo().toUpperCase().trim());
			}
			Usuario usuarioBusqueda = new Usuario();
			usuarioBusqueda.setCodigo(usuario.getCodigo());
			List listaUsuarios = usuarioDAO.findByObject(usuarioBusqueda);
			Usuario user = new Usuario();
			if (listaUsuarios.size() == 0 || listaUsuarios == null) user = null;
			else user = (Usuario)listaUsuarios.get(0);
	
			//No existe el usuario
			
			if (user!= null){
				LoginLog ll = new LoginLog();
				ll.setUsuario_id(user.getId());
				ll.setIp(StringHelper.dameMiIp(localHost));
				ll.setHost(StringHelper.dameMiHost(localHost));
				ll.setFechaIntento(new Date());
				ll.setSistema_id(sistema);
				LoginLog listaTemporalLog = new LoginLog();
				listaTemporalLog.setUsuario_id(user.getId());
				List listaLogxUsuario =(List) loginLogDAO.findByObject(listaTemporalLog);
				
				Date lastAccess = ultimoIngreso(listaLogxUsuario, Constante.INTENTO_VALIDO);
			
		//Se valida que el usuario y la contraseña sean validos
				
				if (StringHelper.miCad(user.getContrasenha(),",").equals(usuario.getContrasenha())) {
					
					mapLoginLog = loadData(user);
					Date fail = ultimoIngreso(listaLogxUsuario, Constante.INTENTO_RECHAZADO);
					
		//Se verifica si ha sido bloqueado por login fallido y se activa pasado los * min
					
					if ((user.getEstado().equalsIgnoreCase(Constante.ESTADO_INACTIVO))&& (DateHelper.fechasDiferenciaEnDias(today(),fail) >= tiempoEsperaReactivacion )){
						user.setEstado(Constante.ESTADO_HABILITADO);
						try {
							usuarioDAO.save(user);
							log.info("Actualizacion de Estado del Usuario - Correcta");
						} catch (Exception e) {
							log.error(e.getMessage());
							throw new DaoException(e);
						}
					}else{
						if ((user.getEstado().equalsIgnoreCase(Constante.ESTADO_INACTIVO))&& (DateHelper.fechasDiferenciaEnDias(today(),fail) < tiempoEsperaReactivacion )){
							message = "El usuario ha sido bloqueado por "+ formatoDecimal.format(tiempoEsperaReactivacion * 24) + " hora(s). Intente mas tarde.";
							redirectTo = "login";
							flag = "false";
							mapLogin.put("message", message);
							mapLogin.put("flag", flag);
							mapLogin.put("redirecTo", redirectTo);
							map.put("mapLogin", mapLogin);
							return map;
						}
					}
		//Se redirecciona para usuario nuevos						
					
					log.info("lastAccess  "  +  lastAccess  );
					log.info("today()  "  +  today()  );
					log.info("maximoTiempoInactividad  "  +  maximoTiempoInactividad  );
					log.info("(int)DateHelper.fechasDiferenciaEnDias(today(),lastAccess) "  +  (int)DateHelper.fechasDiferenciaEnDias(today(),lastAccess)  );
				
					
					if (!user.getEstado().equalsIgnoreCase(Constante.ESTADO_NUEVO)){
						
		//No se permite el ingreso de usuarios Bloqueados
		
						if (!user.getEstado().equalsIgnoreCase(Constante.ESTADO_BLOQUEADO)) {
		
		//Bloquear al usuario si no esta activo por mas de * dias		
							
							if (((int)DateHelper.fechasDiferenciaEnDias(today(),lastAccess)<= maximoTiempoInactividad) ) {
								
		
		
		
		
		//Si la contraseña no se ha cambiado en * dias
							
								if ((int)DateHelper.fechasDiferenciaEnDias(today(),user.getFechaCambioPass())<= maximoTiempoSinCambioPass ){
									redirectTo = "inicio";
									flag = "true";
									ll.setTipoIntento(Constante.INTENTO_VALIDO);
									try {
										loginLogDAO.save(ll);
										log.info("Login Válido");
										log.info("Connection established from : " + localHost);
									} catch (Exception e) {
										log.error(e.getMessage());
										throw new DaoException(e);
									}
								}else{
									redirectTo="cambio";
									flag = "true";
								}
							}else{
								message = "El usuario esta bloqueado por inactividad. Contactese con el administrador";
								flag = "false";
								redirectTo = "login";
								user.setEstado(Constante.ESTADO_BLOQUEADO);
								try {
									usuarioDAO.save(user);
									log.info("Actualizacion de Estado del Usuario - Correcta");
								} catch (Exception e) {
									log.error(e.getMessage());
									throw new DaoException(e);
								}	
							}
						}else{
							message = "El usuario esta bloqueado. Contactese con el administrador";
							flag = "false";
							redirectTo = "login";
						}
					}else{
						redirectTo = "cambio";
						flag = "true";
					}
				}else{
					esPasswordErrado = true;
					redirectTo = "login";
					flag = "false";				
					if (intento <= maximoIntentosLogin){
						message = "Usuario y/o Contraeña incorrectos.";
					}else{
						message = "Se ha bloqueado al usuario durante " + formatoDecimal.format(tiempoEsperaReactivacion * 24) + " hora(s).";
						redirectTo = "login";
						flag = "false";
						user.setEstado(Constante.ESTADO_INACTIVO);
						try {
							usuarioDAO.save(user);
							log.info("Usuario: " + user.getCodigo() + " conectado.");
							log.info("Actualizacion de Estado del Usuario - Correcta");
						} catch (Exception e) {
							log.error(e.getMessage());
							throw new DaoException(e);
						}	
					}
				}
				if (esPasswordErrado){
					if (!(user.getEstado().equalsIgnoreCase(Constante.ESTADO_BLOQUEADO)||user.getEstado().equalsIgnoreCase(Constante.ESTADO_INACTIVO)||user.getEstado().equalsIgnoreCase(Constante.ESTADO_NUEVO)||((int)DateHelper.fechasDiferenciaEnDias(today(),user.getFechaCambioPass()) > maximoTiempoSinCambioPass )||((int)DateHelper.fechasDiferenciaEnDias(today(),lastAccess)> maximoTiempoInactividad ))){
						ll.setTipoIntento(Constante.INTENTO_RECHAZADO);
						flag = "false";
						redirectTo = "login";
						try {
							loginLogDAO.save(ll);
							log.info("Login Rechazado");
						} catch (Exception e) {
							log.error(e.getMessage());
							throw new DaoException(e);
						}
					}
				}
			}else{
				message = ("Usuario y/o Contraeña incorrectos.");
				redirectTo = "login";
			}
			
			// Armando Mapas
			mapLogin.put("message", message);
			mapLogin.put("flag", flag);
			mapLogin.put("redirectTo", redirectTo);
			mapLogin.put("user", user);
			
			map.put("mapLogin", mapLogin);
			map.put("mapLoginLog", mapLoginLog);
			
			return map;
		}catch (DaoException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}		
	}

	public HashMap validateContrasenia(Usuario usuario, String newPassword, String verifyPassword, Long sistema) throws DaoException{
		try{
			Usuario user = usuario;
			HashMap map = new HashMap();
			LoginLog loginLog = new LoginLog();
			log.info("Usuario cambiando contraseña: " + usuario.getCodigo() + " el " + new Date());
			map.put("flag", "false");
	
			//Creando registro temporal para usuarios nuevos
			loginLog.setFechaIntento(new Date());
			try {
				String localHost = String.valueOf(InetAddress.getLocalHost());
				loginLog.setHost(StringHelper.dameMiHost(localHost));
				loginLog.setIp(StringHelper.dameMiIp(localHost));
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
				log.error("IP/Host no definido");
			}
			loginLog.setTipoIntento(Constante.INTENTO_VALIDO);
			loginLog.setUsuario_id(usuario.getId());
	
			//Parametros del Sistema
			int longitudPassword = Integer.valueOf(parametroHelper.getParametro(Constante.LONGITUD_PASSWORD, sistema)).intValue();
			double tiempoEsperaCambioPass = Double.valueOf(parametroHelper.getParametro(Constante.TIEMPO_ESPERA_CAMBIO_PASS_DIAS, sistema)).doubleValue();
			int maximoPassAlmacenados = Integer.valueOf(parametroHelper.getParametro(Constante.MAXIMOS_PASSWORDS_ALMACENADOS, sistema)).intValue();
			
	//Verifico Fecha del ultimo cambio de contraseña
	
			if(DateHelper.fechasDiferenciaEnDias(today(),user.getFechaCambioPass())<= tiempoEsperaCambioPass ){
				map.put("message", "Debe esperar un plazo minimo de "+ formatoDecimal.format(tiempoEsperaCambioPass * 24) +" hora(s) para poder cambiar su contraseña.");
			} else{
				if (newPassword.equals(verifyPassword)){
					if (controlPass(user.getContrasenha(), newPassword, user.getCodigo(), longitudPassword)){
						//Crear registro ficticio para el primer ingreso de usuario nuevos
						try{	
							if(user.getEstado().equalsIgnoreCase(Constante.ESTADO_NUEVO)){
								loginLogDAO.save(loginLog);
							}
						}catch(Exception e){
							e.printStackTrace();
							log.error("No se creo registro");
							throw new DaoException(e);
						}
						String enc_clave =  newPassword;
						String miPassword="";
						enc_clave = Encriptador.encryptBlowfish(enc_clave, Constante.KEY );
						newPassword = enc_clave;
						miPassword=newPassword + ","+ user.getContrasenha();
						user.setContrasenha(StringHelper.listaPassword(miPassword, maximoPassAlmacenados));
						user.setEstado(Constante.ESTADO_HABILITADO);
						user.setFechaCambioPass(today());
						user.setUsuarioModificacion(user.getId());
						user.setFechaModificacion(new Date());
						map.put("message","Se realizo el cambio de contraseña exitosamente");
						map.put("flag", "true");
						try {
							usuarioDAO.save(user);
						} catch (Exception e) {
							log.error(e.getMessage());
							map.put("message",e);
							throw new DaoException(e);
						}
					}else{
						map.put("message",getMensaje());
					}
				}else{
					map.put("message","Las contraseñas ingresadas son diferentes. Por favor, asegúrese que sean iguales.");
				}
			}
			return map;
		}catch (DaoException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
	public HashMap loadData(Usuario usuario) throws DaoException{
		try{		
			HashMap map = new HashMap();
			Date ultimoIngresoExitoso = new Date();
			LoginLog ll = new LoginLog();
			List listaTemporalLog = null;
	
			int intentosAcertados = 0;
			int intentosFallados = 0;
			ll.setUsuario_id(usuario.getId());
			listaTemporalLog = loginLogDAO.findByObject(ll);
			ultimoIngresoExitoso = ultimoIngreso(listaTemporalLog, Constante.INTENTO_VALIDO);
			
			for (int i=0; i<=(listaTemporalLog.size()-1);i++){
				if (DateHelper.fechasDiferenciaEnDias(ultimoIngresoExitoso,((LoginLog)listaTemporalLog.get(i)).getFechaIntento())<=0){
					if (((LoginLog)listaTemporalLog.get(i)).getTipoIntento().equalsIgnoreCase(Constante.INTENTO_VALIDO)){
						intentosAcertados++;
					}else{
						if (((LoginLog)listaTemporalLog.get(i)).getTipoIntento().equalsIgnoreCase(Constante.INTENTO_RECHAZADO)){
							intentosFallados++;
						}
					}
				}
			}
			
			setUltimoIngreso(ultimoIngreso(listaTemporalLog, Constante.INTENTO_VALIDO));
			setIntentosRechazados(new Integer(intentosFallados));
			setIntentosValidos(new Integer(intentosAcertados));
			
			map.put("fullName", usuario.getNombre() + " " + usuario.getApePa());
			map.put("lastLogin", getUltimoIngreso());
			map.put("failAttempts", getIntentosRechazados());
			
			return map;
		}catch (DaoException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
	public static Date ultimoIngreso(List tempo, String tipoIntento){
		Date ultimo = Constante.FECHA_INICIO;
		Date fecha_bd;
		String intento;
		
		for (int i=0; i<=(tempo.size()-1);i++){
			fecha_bd = ((LoginLog) tempo.get(i)).getFechaIntento();
			intento = ((LoginLog)tempo.get(i)).getTipoIntento();
			if((DateHelper.fechasDiferenciaEnDias(ultimo, fecha_bd)<=0)&& intento.equalsIgnoreCase(tipoIntento)){
				ultimo = fecha_bd;
			}
		}
		return ultimo;
	}
	
	public boolean controlPass(String contraIni, String contraFini, String codigo, int longitudMinima){
		String final_enc = Encriptador.encryptBlowfish(contraFini, Constante.KEY);
		String ini_enc = StringHelper.miCad(contraIni,",");
		String ci = contraIni;
		boolean flag = false;

//Si es menor que ?

		if (contraFini.length()>= longitudMinima){
			
//Si es igual a alguna contraseña anterior
			
			for (int j=0; j<=StringHelper.frecuenciaCaracter(contraIni, ",");j++){
				if (!ini_enc.equals(final_enc)){
					ci=StringHelper.reCad(ci,",");
					ini_enc=StringHelper.miCad(ci,",");
				}else{
					setMensaje("No debe utilizar contraseñas anteriores.");
					return false;
				}					
			}

//Que no sea igual al codigo o lo contenga
			
			if(!StringHelper.contains(contraFini, codigo)){
				
//Que no sea el codigo de usuario invertido
				
				if (!contraFini.toUpperCase().equals(StringHelper.invertirCadena(codigo.toUpperCase()))){
					
//Que contenga al menos una letra y/o un numero
					if(StringHelper.tieneLetras(contraFini)){
						if(StringHelper.tieneNumeros(contraFini)){
							flag=true;
						}else{
							setMensaje("Debe ingresar una contraseña con al menos un número");
						}
					}else{
						setMensaje("Debe ingresar una contraseña con al menos una letra");
					}
				}else{
					setMensaje("La nueva contraseña no debe ser igual al codigo de usuario invertido");
				}
			}else{
				setMensaje("La nueva contraseña no debe contener el codigo de usuario");
			}
		}else{
			setMensaje("Debe ingresar una contraseña con al menos " + longitudMinima +" caracteres");
		}
		
		return flag;
	}
	
	public Date today() {
		return DateHelper.getFechaActual();
	}

	public void setUsuarioDAO(GenericDao usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	public GenericDao getUsuarioDAO() {
		return usuarioDAO;
	}

	public void setParametroHelper(ParametroHelper parametroHelper) {
		this.parametroHelper = parametroHelper;
	}

	public ParametroHelper getParametroHelper() {
		return parametroHelper;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setLoginLogDAO(GenericDao loginLogDAO) {
		this.loginLogDAO = loginLogDAO;
	}

	public GenericDao getLoginLogDAO() {
		return loginLogDAO;
	}

	public void setUltimoIngreso(Date ultimoIngreso) {
		this.ultimoIngreso = ultimoIngreso;
	}

	public Date getUltimoIngreso() {
		return ultimoIngreso;
	}

	public void setIntentosValidos(Integer intentosValidos) {
		this.intentosValidos = intentosValidos;
	}

	public Integer getIntentosValidos() {
		return intentosValidos;
	}

	public void setIntentosRechazados(Integer intentosRechazados) {
		this.intentosRechazados = intentosRechazados;
	}

	public Integer getIntentosRechazados() {
		return intentosRechazados;
	}
}
