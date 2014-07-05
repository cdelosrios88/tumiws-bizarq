package pe.com.tumi.common.domain;

public class Log {
	private String comando;
	private String respuesta;
	
	public Log(){
	}
	public Log(String comando, String respuesta){
		this.comando = comando;
		this.respuesta = respuesta;
	}
	public String getComando() {
		return comando;
	}
	public void setComando(String comando) {
		this.comando = comando;
	}
	public String getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	
}
