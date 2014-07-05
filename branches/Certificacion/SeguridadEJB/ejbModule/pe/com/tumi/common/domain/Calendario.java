//package pe.com.telefonica.common.domain;
//
//import java.util.Date;
//
//import javax.persistence.Column;
//import javax.persistence.DiscriminatorColumn;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Inheritance;
//import javax.persistence.InheritanceType;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.SequenceGenerator;
//import javax.persistence.Table;
//
//@Entity
//@Table(name = "SEG_CALENDARIO_CAL")
//@SequenceGenerator(name = "SEG_CALENDARIO_SEQ", sequenceName = "SEG_CALENDARIO_SEQ", allocationSize = 1, initialValue = 1)
//public class Calendario extends TipoBase{
//	
//	private Long idSis;
//	
//	@Id
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEG_CALENDARIO_SEQ")
//	@Column(length = 5, name = "ID_CAL_C")
//	@Override
//	public Long getId() {
//		return super.getId();
//	}
//
//	public void setIdSis(Long idSis) {
//		this.idSis = idSis;
//	}
//
//	//Actualizar FK
//	@Column(length=60,name="SIS_COD")
//	//@ManyToOne(fetch = FetchType.LAZY)
//	//@JoinColumn(name = "SIS_COD", nullable=false)
//	public Long getIdSis() {
//		return idSis;
//	}
//
//}
