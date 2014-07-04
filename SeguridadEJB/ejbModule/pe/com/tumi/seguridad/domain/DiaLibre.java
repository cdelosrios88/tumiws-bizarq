/*package pe.com.telefonica.seguridad.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import pe.com.telefonica.common.domain.Calendario;

@Entity
@Table(name="SEG_DIALIBRE_DL")
@SequenceGenerator(name="SEG_DIALIBRE_SEQ",sequenceName="SEG_DIALIBRE_SEQ",allocationSize=1,initialValue=6000)
public class DiaLibre{
//public class DiaLibre {
	
	private Long id;
	private Date fecha;
	private Date inicio;
	private Date fin;
	private Long id_cal;
	
	@Id
	@Column(length=5,name="ID_DIA_L")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEG_DIALIBRE_SEQ")
	public Long getId() {
		return id;
	}

	
	@Column(length=100,name="FECH_L")
	public Date getFecha() {
		return fecha;
	}
	
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	@Column(length=100,name="FECH_INI")
	public Date getInicio() {
		return inicio;
	}

	public void setFin(Date fin) {
		this.fin = fin;
	}
	
	@Column(length=100,name="FECH_FIN")
	public Date getFin() {
		return fin;
	}
	
	public void setId_cal(Long id_cal) {
		this.id_cal = id_cal;
	}
	
	@Column(length=100,name="ID_CAL")
	public Long getId_cal() {
		return id_cal;
	}


	public void setId(Long id) {
		this.id = id;
	}
}
*/