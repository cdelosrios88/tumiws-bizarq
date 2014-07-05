package pe.com.tumi.creditos.domain;

public class Estructura {
	private		Integer		nIdEmpresa;
	private		Long		nIdCodigo;
	private		Integer		nIdNivel;
	private		Integer		nIdCaso;
	private		String		sCaso;
	private		String		sRazonSocial;
	
	//Getters y Setters
	public Integer getnIdEmpresa() {
		return nIdEmpresa;
	}
	public void setnIdEmpresa(Integer nIdEmpresa) {
		this.nIdEmpresa = nIdEmpresa;
	}
	public Long getnIdCodigo() {
		return nIdCodigo;
	}
	public void setnIdCodigo(Long nIdCodigo) {
		this.nIdCodigo = nIdCodigo;
	}
	public Integer getnIdNivel() {
		return nIdNivel;
	}
	public void setnIdNivel(Integer nIdNivel) {
		this.nIdNivel = nIdNivel;
	}
	public Integer getnIdCaso() {
		return nIdCaso;
	}
	public void setnIdCaso(Integer nIdCaso) {
		this.nIdCaso = nIdCaso;
	}
	public String getsCaso() {
		return sCaso;
	}
	public void setsCaso(String sCaso) {
		this.sCaso = sCaso;
	}
	public String getsRazonSocial() {
		return sRazonSocial;
	}
	public void setsRazonSocial(String sRazonSocial) {
		this.sRazonSocial = sRazonSocial;
	}
}
