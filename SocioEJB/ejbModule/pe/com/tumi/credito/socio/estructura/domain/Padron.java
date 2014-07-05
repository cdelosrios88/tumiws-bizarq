package pe.com.tumi.credito.socio.estructura.domain;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class Padron extends TumiDomain {

	private PadronId 	id;
	private String 		strPro;
	private String 		strProgSub;
	private String 		strCodEst;
	private String 		strCodigoNivel;
	private String 		strCodeje;
	private String 		strTipoPla;
	private String 		strNombre;
	private String 		strPlaza;
	private String 		strLibEle;
	private Date 		dtFecNac;
	private String 		strCodCar;
	private String 		strRegim;
	private BigDecimal 	bdLiquid;
	private String 		strCondic;
	private	BigDecimal	bdPorJud;
	private	Integer		intLicenp;
	private	Integer		intLicSub;
	private	Integer		intPorguar;
	private	Integer		intEstado;
	private	String 		strDL6;
	private	Integer		intIPSSAT;
	
	public Padron(){
		super();
		id = new PadronId();
	}

	public PadronId getId() {
		return id;
	}

	public void setId(PadronId id) {
		this.id = id;
	}

	public String getStrPro() {
		return strPro;
	}

	public void setStrPro(String strPro) {
		this.strPro = strPro;
	}

	public String getStrProgSub() {
		return strProgSub;
	}

	public void setStrProgSub(String strProgSub) {
		this.strProgSub = strProgSub;
	}

	public String getStrCodEst() {
		return strCodEst;
	}

	public void setStrCodEst(String strCodEst) {
		this.strCodEst = strCodEst;
	}

	public String getStrCodigoNivel() {
		return strCodigoNivel;
	}

	public void setStrCodigoNivel(String strCodigoNivel) {
		this.strCodigoNivel = strCodigoNivel;
	}

	public String getStrCodeje() {
		return strCodeje;
	}

	public void setStrCodeje(String strCodeje) {
		this.strCodeje = strCodeje;
	}

	public String getStrTipoPla() {
		return strTipoPla;
	}

	public void setStrTipoPla(String strTipoPla) {
		this.strTipoPla = strTipoPla;
	}

	public String getStrNombre() {
		return strNombre;
	}

	public void setStrNombre(String strNombre) {
		this.strNombre = strNombre;
	}

	public String getStrPlaza() {
		return strPlaza;
	}

	public void setStrPlaza(String strPlaza) {
		this.strPlaza = strPlaza;
	}

	public String getStrLibEle() {
		return strLibEle;
	}

	public void setStrLibEle(String strLibEle) {
		this.strLibEle = strLibEle;
	}

	public Date getDtFecNac() {
		return dtFecNac;
	}

	public void setDtFecNac(Date dtFecNac) {
		this.dtFecNac = dtFecNac;
	}

	public String getStrCodCar() {
		return strCodCar;
	}

	public void setStrCodCar(String strCodCar) {
		this.strCodCar = strCodCar;
	}

	public String getStrRegim() {
		return strRegim;
	}

	public void setStrRegim(String strRegim) {
		this.strRegim = strRegim;
	}

	public BigDecimal getBdLiquid() {
		return bdLiquid;
	}

	public void setBdLiquid(BigDecimal bdLiquid) {
		this.bdLiquid = bdLiquid;
	}

	public String getStrCondic() {
		return strCondic;
	}

	public void setStrCondic(String strCondic) {
		this.strCondic = strCondic;
	}

	public BigDecimal getBdPorJud() {
		return bdPorJud;
	}

	public void setBdPorJud(BigDecimal bdPorJud) {
		this.bdPorJud = bdPorJud;
	}

	public Integer getIntLicenp() {
		return intLicenp;
	}

	public void setIntLicenp(Integer intLicenp) {
		this.intLicenp = intLicenp;
	}

	public Integer getIntEstado() {
		return intEstado;
	}

	public void setIntEstado(Integer intEstado) {
		this.intEstado = intEstado;
	}

	public String getStrDL6() {
		return strDL6;
	}

	public void setStrDL6(String strDL6) {
		this.strDL6 = strDL6;
	}

	public Integer getIntIPSSAT() {
		return intIPSSAT;
	}

	public void setIntIPSSAT(Integer intIPSSAT) {
		this.intIPSSAT = intIPSSAT;
	}

	public Integer getIntLicSub() {
		return intLicSub;
	}

	public void setIntLicSub(Integer intLicSub) {
		this.intLicSub = intLicSub;
	}

	public Integer getIntPorguar() {
		return intPorguar;
	}

	public void setIntPorguar(Integer intPorguar) {
		this.intPorguar = intPorguar;
	}

	@Override
	public String toString() {
		return "Padron [id=" + id + ", strPro=" + strPro + ", strProgSub="
				+ strProgSub + ", strCodEst=" + strCodEst + ", strCodigoNivel="
				+ strCodigoNivel + ", strCodeje=" + strCodeje + ", strTipoPla="
				+ strTipoPla + ", strNombre=" + strNombre + ", strPlaza="
				+ strPlaza + ", strLibEle=" + strLibEle + ", dtFecNac="
				+ dtFecNac + ", strCodCar=" + strCodCar + ", strRegim="
				+ strRegim + ", bdLiquid=" + bdLiquid + ", strCondic="
				+ strCondic + ", bdPorJud=" + bdPorJud + ", intLicenp="
				+ intLicenp + ", intLicSub=" + intLicSub + ", intPorguar="
				+ intPorguar + ", intEstado=" + intEstado + ", strDL6="
				+ strDL6 + ", intIPSSAT=" + intIPSSAT + "]";
	}
	
	
}
