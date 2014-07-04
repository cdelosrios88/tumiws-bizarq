package pe.com.tumi.common.util;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import pe.com.tumi.tesoreria.egreso.domain.SolicitudPersonal;
import pe.com.tumi.tesoreria.egreso.domain.SolicitudPersonalDetalle;
import pe.com.tumi.tesoreria.egreso.domain.SolicitudPersonalPago;
import jxl.*; 

public class ManejadorExcel {
	
	protected static Logger log = Logger.getLogger(MyUtil.class);
	private static SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yy");
	private static DecimalFormat formatoDecimal = new DecimalFormat();
	
	public static List<SolicitudPersonal> manejarExcelSolicitudPersonal(File file)throws Exception{
		//log.info(archivo.getRutaActual());
		//Workbook workbook = Workbook.getWorkbook(archivo.getFile());
		Workbook workbook = Workbook.getWorkbook(file);
		Sheet sheet = workbook.getSheet(0);
		List<SolicitudPersonal> listaSolicitudPersonal = new ArrayList<SolicitudPersonal>();
		List<SolicitudPersonalDetalle> listaSolicitudPersonalDetalle = new ArrayList<SolicitudPersonalDetalle>();
		List<SolicitudPersonalPago> listaSolicitudPersonalPago = new ArrayList<SolicitudPersonalPago>();
		
		cargarSolicitudPersonal(sheet, listaSolicitudPersonal);
		cargarSolicitudPersonalDetalle(sheet, listaSolicitudPersonalDetalle);
		cargarSolicitudPersonalPago(sheet, listaSolicitudPersonalPago);
	
		for(SolicitudPersonal solicitudPersonal : listaSolicitudPersonal){
			for(SolicitudPersonalDetalle solicitudPersonalDetalle : listaSolicitudPersonalDetalle)
				if(solicitudPersonal.getId().getIntItemSolicitudPersonal().equals(solicitudPersonalDetalle.getId().getIntItemSolicitudPersonal()))
					solicitudPersonal.getListaSolicitudPersonalDetalle().add(solicitudPersonalDetalle);

			for(SolicitudPersonalPago solicitudPersonalPago : listaSolicitudPersonalPago)
				if(solicitudPersonal.getId().getIntItemSolicitudPersonal().equals(solicitudPersonalPago.getId().getIntItemSolicitudPersonal()))
					solicitudPersonal.getListaSolicitudPersonalPago().add(solicitudPersonalPago);
		}
		
		return listaSolicitudPersonal;
	}
	
	private static void cargarSolicitudPersonalPago(Sheet sheet, List<SolicitudPersonalPago> listaSolicitudPersonalPago)throws Exception{
		int indexRegistro = obtenerIndiceFila("detalle de pago", sheet);
		boolean continuaLectura = Boolean.TRUE;
		
		while(continuaLectura){
			Cell cellPrueba = sheet.getCell(0,indexRegistro);
			//valida hasta que fila se puede leer
			if(cellPrueba.getContents()==null  || cellPrueba.getContents().isEmpty()){
				continuaLectura = Boolean.FALSE;
				break;
			}
			
			SolicitudPersonalPago solicitudPersonalPago = new SolicitudPersonalPago();
			Cell cell = null;
			
			cell = sheet.getCell(Columna.SPP_EMPRESA.getValor(), indexRegistro);
			solicitudPersonalPago.getId().setIntPersEmpresa(getInt(cell));
			
			cell = sheet.getCell(Columna.SPP_CORRELATIVO.getValor(), indexRegistro);
			solicitudPersonalPago.getId().setIntItemSolicitudPersonal(getInt(cell));
			
			cell = sheet.getCell(Columna.SPP_CORRELATIVOPAGO.getValor(), indexRegistro);
			solicitudPersonalPago.getId().setIntItemSolicitudPersonalPago(getInt(cell));
			
			cell = sheet.getCell(Columna.SPP_EMPRESAEGRESO.getValor(), indexRegistro);
			solicitudPersonalPago.setIntPersEmpresaEgreso(getInt(cell));
			
			cell = sheet.getCell(Columna.SPP_ITEMEGRESO.getValor(), indexRegistro);
			solicitudPersonalPago.setIntItemEgresoGeneral(getInt(cell));
			
			cell = sheet.getCell(Columna.SPP_MONTO.getValor(), indexRegistro);
			solicitudPersonalPago.setBdMonto(getBd(cell));
			
			cell = sheet.getCell(Columna.SPP_ESTADO.getValor(), indexRegistro);
			solicitudPersonalPago.setIntParaEstado(getInt(cell));
			
			listaSolicitudPersonalPago.add(solicitudPersonalPago);
			indexRegistro++;
			
			if(indexRegistro == sheet.getRows())
				continuaLectura = Boolean.FALSE;
		}
	}
	
	private static void cargarSolicitudPersonalDetalle(Sheet sheet, List<SolicitudPersonalDetalle> listaSolicitudPersonalDetalle)throws Exception{
		int indexRegistro = obtenerIndiceFila("detalle", sheet);
		boolean continuaLectura = Boolean.TRUE;
		
		while(continuaLectura){
			Cell cellPrueba = sheet.getCell(0,indexRegistro);
			//valida hasta que fila se puede leer
			if(cellPrueba.getContents()==null  || cellPrueba.getContents().isEmpty()){
				continuaLectura = Boolean.FALSE;
				break;
			}
			
			SolicitudPersonalDetalle solicitudPersonalDetalle = new SolicitudPersonalDetalle();
			Cell cell = null;
			
			cell = sheet.getCell(Columna.SPD_EMPRESA.getValor(), indexRegistro);
			solicitudPersonalDetalle.getId().setIntPersEmpresa(getInt(cell));
			solicitudPersonalDetalle.setIntPersEmpresaAbonado(solicitudPersonalDetalle.getId().getIntPersEmpresa());
			
			cell = sheet.getCell(Columna.SPD_CORRELATIVO.getValor(), indexRegistro);
			solicitudPersonalDetalle.getId().setIntItemSolicitudPersonal(getInt(cell));
			
			cell = sheet.getCell(Columna.SPD_CORRELATIVODETALLE.getValor(), indexRegistro);
			solicitudPersonalDetalle.getId().setIntItemSolicitudPersonalDetalle(getInt(cell));
			
			cell = sheet.getCell(Columna.SPD_DOCUMENTOGENERAL.getValor(), indexRegistro);
			solicitudPersonalDetalle.setIntParaDocumentoGeneral(getInt(cell));
			
			cell = sheet.getCell(Columna.SPD_PERSONA.getValor(), indexRegistro);
			solicitudPersonalDetalle.setIntPersPersonaAbonado(getInt(cell));
			
			cell = sheet.getCell(Columna.SPD_MONTOTOTAL.getValor(), indexRegistro);
			solicitudPersonalDetalle.setBdMonto(getBd(cell));
			
			cell = sheet.getCell(Columna.SPD_SUCURSAL.getValor(), indexRegistro);
			solicitudPersonalDetalle.setIntSucuIdSucursal(getInt(cell));
			
			cell = sheet.getCell(Columna.SPD_SUBSUCURSAL.getValor(), indexRegistro);
			solicitudPersonalDetalle.setIntSudeIdSubsucursal(getInt(cell));
			
			cell = sheet.getCell(Columna.SPD_AREA.getValor(), indexRegistro);
			solicitudPersonalDetalle.setIntIdArea(getInt(cell));
			
			cell = sheet.getCell(Columna.SPD_EMPRESAPLANCUENTA.getValor(), indexRegistro);
			solicitudPersonalDetalle.setIntPersEmpresaCuenta(getInt(cell));
			
			cell = sheet.getCell(Columna.SPD_PERIODOPLANCUENTA.getValor(), indexRegistro);
			solicitudPersonalDetalle.setIntContPeriodoCuenta(getInt(cell));
			
			cell = sheet.getCell(Columna.SPD_NUMEROPLANCUENTA.getValor(), indexRegistro);
			solicitudPersonalDetalle.setStrContNumeroCuenta(getStr(cell));
			
			cell = sheet.getCell(Columna.SPD_DEBEHABER.getValor(), indexRegistro);
			solicitudPersonalDetalle.setIntParaOpcionDebeHaber(getInt(cell));
			
			cell = sheet.getCell(Columna.SPD_NUMERODOCUMENTO.getValor(), indexRegistro);
			solicitudPersonalDetalle.setIntNumeroDocumento(getInt(cell));
			
			
			listaSolicitudPersonalDetalle.add(solicitudPersonalDetalle);
			indexRegistro++;
			
			if(indexRegistro == sheet.getRows())
				continuaLectura = Boolean.FALSE;
		}
	}
	
	private static void cargarSolicitudPersonal(Sheet sheet, List<SolicitudPersonal> listaSolicitudPersonal)throws Exception{
		int indexRegistro = obtenerIndiceFila("cabecera", sheet);
		boolean continuaLectura = Boolean.TRUE;
		
		while(continuaLectura){
			Cell cellPrueba = sheet.getCell(0,indexRegistro);
			//valida hasta que fila se puede leer
			if(cellPrueba.getContents()==null  || cellPrueba.getContents().isEmpty()){
				continuaLectura = Boolean.FALSE;
				break;
			}
			
			SolicitudPersonal solicitudPersonal = new SolicitudPersonal();
			Cell cell = null;
			
			cell = sheet.getCell(Columna.SP_EMPRESA.getValor(), indexRegistro);
			solicitudPersonal.getId().setIntPersEmpresa(getInt(cell));
			solicitudPersonal.setIntPersEmpresaPersona(solicitudPersonal.getId().getIntPersEmpresa());
			solicitudPersonal.setIntPersPersonaGiro(solicitudPersonal.getId().getIntPersEmpresa());
			solicitudPersonal.setIntPersEmpresaUsuario(solicitudPersonal.getId().getIntPersEmpresa());
			
			cell = sheet.getCell(Columna.SP_CORRELATIVO.getValor(), indexRegistro);
			solicitudPersonal.getId().setIntItemSolicitudPersonal(getInt(cell));
			
			cell = sheet.getCell(Columna.SP_FECHAREGISTRO.getValor(), indexRegistro);
			solicitudPersonal.setTsFechaRegistro(getTs(cell));
			
			cell = sheet.getCell(Columna.SP_DOCUMENTOGENERAL.getValor(), indexRegistro);
			solicitudPersonal.setIntParaDocumentoGeneral(getInt(cell));
			
			cell = sheet.getCell(Columna.SP_SUBTIPO.getValor(), indexRegistro);
			solicitudPersonal.setIntParaSubTipoDocumentoPlanilla(getInt(cell));
			
			cell = sheet.getCell(Columna.SP_TIPOOPERACION.getValor(), indexRegistro);
			solicitudPersonal.setIntParaAgrupacionPago(getInt(cell));
			
			cell = sheet.getCell(Columna.SP_PERSONA.getValor(), indexRegistro);
			solicitudPersonal.setIntPersPersonaGiro(getInt(cell));
			
			cell = sheet.getCell(Columna.SP_MONEDA.getValor(), indexRegistro);
			solicitudPersonal.setIntParaTipoMoneda(getInt(cell));
			
			cell = sheet.getCell(Columna.SP_MONTOTOTAL.getValor(), indexRegistro);
			solicitudPersonal.setBdMontoTotalSolicitud(getBd(cell));
			
			cell = sheet.getCell(Columna.SP_PERIODO.getValor(), indexRegistro);
			solicitudPersonal.setIntPeriodoPago(getInt(cell));
			
			cell = sheet.getCell(Columna.SP_TIPOPLANILLA.getValor(), indexRegistro);
			solicitudPersonal.setStrTipoPlanilla(getStr(cell));
			
			cell = sheet.getCell(Columna.SP_TIPOPERIODO.getValor(), indexRegistro);
			solicitudPersonal.setIntParaTipoPeriodo(getInt(cell));
			
			cell = sheet.getCell(Columna.SP_OBSERVACION.getValor(), indexRegistro);
			solicitudPersonal.setStrObservacion(getStr(cell));
			
			cell = sheet.getCell(Columna.SP_ESTADO.getValor(), indexRegistro);
			solicitudPersonal.setIntParaEstado(getInt(cell));
			
			cell = sheet.getCell(Columna.SP_USUARIO.getValor(), indexRegistro);
			solicitudPersonal.setIntPersPersonaUsuario(getInt(cell));
			
			
			listaSolicitudPersonal.add(solicitudPersonal);
			indexRegistro++;
			
			if(indexRegistro == sheet.getRows())
				continuaLectura = Boolean.FALSE;
		}
	}
	
	//Encuentra el indice de fila donde esta strTexto = 'cabecera', 'detalle', 'detallepago'
	private static Integer obtenerIndiceFila(String strTexto, Sheet sheet){
		
		int intIndice = 0;
		int intNumeroFilas = sheet.getRows(); 
		Cell cellPrueba = null;
		while(intIndice < intNumeroFilas){
			cellPrueba = sheet.getCell(0,intIndice);
			if(cellPrueba.getContents()!=null 
			&& !cellPrueba.getContents().isEmpty()		
			&& cellPrueba.getContents().equalsIgnoreCase(strTexto)){
				return (intIndice + 2);
			}
			intIndice++;
		}
		return null;
	}
	
	private static Integer getInt(Cell cell){
		try{
			if(cell.getContents()==null || cell.getContents().isEmpty())
				return null;
			
			return Integer.parseInt(cell.getContents());
		}catch(Exception e){
			log.error(e.getMessage());
			return null;
		}
	}
	
	private static BigDecimal getBd(Cell cell){
		try{
			if(cell.getContents()==null  || cell.getContents().isEmpty())
				return null;
			
			formatoDecimal.setParseBigDecimal(true);
			return (BigDecimal) formatoDecimal.parse(cell.getContents());
		}catch(Exception e){
			log.error(cell.getContents());
			log.error(e.getMessage());
			return null;
		}
	}
	
	private static String getStr(Cell cell){
		try{
			if(cell.getContents()==null  || cell.getContents().isEmpty())
				return null;
			
			return cell.getContents();
		}catch(Exception e){
			log.error(e.getMessage());
			return null;
		}
	}
	
	private static Timestamp getTs(Cell cell){
		try{
			if(cell.getContents()==null  || cell.getContents().isEmpty())
				return null;
			
			return new Timestamp(formatoFecha.parse(cell.getContents()).getTime());			
		}catch(Exception e){
			log.error(e.getMessage());
			return null;
		}
	}
}