package pe.com.tumi.tesoreria.banco.facade;
import java.util.List;

import javax.ejb.Remote;

import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalle;
import pe.com.tumi.empresa.domain.SubSucursalPK;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.tesoreria.banco.domain.Acceso;
import pe.com.tumi.tesoreria.banco.domain.AccesoDetalle;
import pe.com.tumi.tesoreria.banco.domain.AccesoDetalleRes;
import pe.com.tumi.tesoreria.banco.domain.Bancocuenta;
import pe.com.tumi.tesoreria.banco.domain.BancocuentaId;
import pe.com.tumi.tesoreria.banco.domain.Bancocuentacheque;
import pe.com.tumi.tesoreria.banco.domain.BancocuentachequeId;
import pe.com.tumi.tesoreria.banco.domain.Bancofondo;
import pe.com.tumi.tesoreria.banco.domain.BancofondoId;
import pe.com.tumi.tesoreria.banco.domain.Fondodetalle;
import pe.com.tumi.tesoreria.egreso.domain.ControlFondosFijos;

@Remote
public interface BancoFacadeRemote {
	public Bancofondo grabarBanco(Bancofondo o) throws BusinessException;
	public Bancofondo grabarFondo(Bancofondo o) throws BusinessException;
	public List<Bancofondo> buscarBancoFondo(Bancofondo o) throws BusinessException;
	public List<Bancocuentacheque> getListaBancoCuentaChequePorBancoCuenta(Bancocuenta o) throws BusinessException;
	public Bancofondo modificarFondo(Bancofondo o) throws BusinessException;
	public Bancofondo modificarBanco(Bancofondo o) throws BusinessException;
	public Bancofondo eliminarBancoFondo(Bancofondo o) throws BusinessException;
	public List<Fondodetalle> getListaFondoDetallePorSubsucursalPK(SubSucursalPK o) throws BusinessException;
	public Bancofondo getBancoFondoPorId(BancofondoId o) throws BusinessException;
	public Acceso grabarAcceso(Acceso o) throws BusinessException;
	public List<Acceso> buscarAcceso(Acceso o) throws BusinessException;
	public Bancocuenta getBancoCuentaPorId(BancocuentaId o) throws BusinessException;
	public Acceso modificarAcceso(Acceso o) throws BusinessException;
	public Acceso anularAcceso(Acceso o) throws BusinessException;
	public List<AccesoDetalleRes> getListaAccesoDetalleResPorAccesoDetalle(AccesoDetalle o) throws BusinessException;
	public Bancofondo getBancoFondoPorTipoFondoFijoYMoneda(Bancofondo o) throws BusinessException;
	public Acceso validarAccesoParaApertura(Acceso o, Integer intTipoFondoFijo, Integer intTipoMoneda) throws BusinessException;
	public List<Fondodetalle> getListaFondoDetallePorBancoFondo(Bancofondo o) throws BusinessException;
	public Bancocuentacheque getBancoCuentaChequePorId(BancocuentachequeId o) throws BusinessException;
	public Bancocuentacheque modificarBancoCuentaCheque(Bancocuentacheque o) throws BusinessException;
	public Bancofondo getBancoFondoPorBancoCuenta(Bancocuenta bancoCuenta) throws BusinessException;
	public Bancocuenta getBancoCuentaPorBancoCuentaCheque(Bancocuentacheque bancoCuentaCheque) throws BusinessException;
	public Bancofondo getBancoFondoPorBancoCuentaCheque(Bancocuentacheque bancoCuentaCheque) throws BusinessException;
	public List<Bancofondo> getBancoFondoPorEmpresayPersonaBanco(Integer intEmpresa, Integer intPersona) throws BusinessException;
	public List<Bancocuenta> getBancoCuentaPorBancoFondo(Bancofondo bancoFondo) throws BusinessException;
	public Bancocuenta modificarBancoCuenta(Bancocuenta o) throws BusinessException;
	public Bancofondo obtenerBancoFondoPorControl(ControlFondosFijos controlFondosFijos) throws BusinessException;
	//Autor: jchavez / Tarea: Modificacion / Fecha: 30.09.2014
	public Bancofondo obtenerBancoFondoParaIngreso(Usuario usuario, ControlFondosFijos controlFondosFijosCerrar) throws BusinessException;
	public Bancofondo obtenerBancoFondoParaIngreso(Usuario usuario, Integer intMoneda) throws BusinessException;
	//Fin jchavez - 30.09.2014
	public Bancofondo obtenerBancoFondoParaIngresoDesdeControl(ControlFondosFijos controlFondosFijos) throws BusinessException;
	public List<Bancofondo> buscarBancoFondoParaDeposito(Bancofondo o, Usuario usuario) throws BusinessException;
	public List<Bancofondo> obtenerListaFondoExistente(Integer intIdEmpresa)throws BusinessException;
	public List<Bancofondo> obtenerListaBancoExistente(Integer intIdEmpresa)throws BusinessException;
	public Bancocuentacheque getUltimoBancoCuentaCheque(Bancocuenta bancoCuenta)throws BusinessException;
	public Fondodetalle obtenerFondoDetalleContable(Bancofondo bancoFondo, Sucursal sucursal, Subsucursal subsucursal) throws BusinessException;
	public Bancofondo obtenerBancoPorPlanCuenta(LibroDiarioDetalle libroDiarioDetalle) throws BusinessException;
	public Acceso obtenerAccesoPorControlFondosFijos(ControlFondosFijos controlFondosFijos) throws BusinessException;
	public List<Bancocuenta> buscarListaBancoCuenta(Bancocuenta bancoCuentaFiltro) throws BusinessException;
	//Autor: jchavez / Tarea: Creación / Fecha: 16.10.2014
	public List<Fondodetalle> getDocumentoPorFondoFijo(Integer intEmpresa, Integer intTipoFondoFijo, Integer intMoneda) throws BusinessException;
}
