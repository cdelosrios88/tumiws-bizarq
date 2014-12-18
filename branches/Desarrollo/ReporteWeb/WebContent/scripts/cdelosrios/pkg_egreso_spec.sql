CREATE OR REPLACE PACKAGE REPORTE.PKG_TESORERIA_EGRESOS
AS
    TYPE cursorLista IS REF CURSOR;
    /* -----------------------------------------------------------------------------------------------------
        Nombre      : getCadenaCajaChica
        Proposito   : Procedimiento que retorna el nombre de la caja chica para el formulario 
                     Movimientos de Caja Chica
        Referencias : REQ14-010 Reporte de Egresos - Tesoreria
        Parametros  : V_LISTA        --->  Cursor que retornará el procedimiento.
                      N_TIPOFONDO    --->  Numero de fondo fijo.
                      N_IDSUCURSAL   --->  Identificador de la sucursal.
                      N_ANIOFONDO    --->  Anio del fondo fijo.

        Log de Cambios
        Fecha           Autor                 Descripcion
        16.12.2014      Luis Polanco          Creación de Stored Procedure
    ----------------------------------------------------------------------------------------------------- */   
    PROCEDURE getCadenaCajaChica( 
          V_LISTA                   OUT    cursorLista,
          N_TIPOFONDO               IN     NUMBER,
          N_IDSUCURSAL              IN     NUMBER,
          N_ANIOFONDO               IN     NUMBER);
    
    /* -----------------------------------------------------------------------------------------------------
        Nombre      : getMovimientosCajaChicaBody
        Proposito   : Procedimiento que retorna los movimientos realizados en Caja Chica 
                     utilizados en el formulario y reporte del mismo nombre.
        Referencias : REQ14-010 Reporte de Egresos - Tesoreria
        Parametros  : V_LISTA        --->  Cursor que retornará el procedimiento.
                      V_ITEMFONDO    --->  Item del fondo fijo seleccionado.
                      V_PERIODOFONDO --->  Periodo seleccionado de los fondos fijos.
                      N_MONTOSALDO   --->  Monto seleccionado del saldo de los fondos fijos.

        Log de Cambios
        Fecha           Autor                 Descripcion
        16.12.2014      Luis Polanco          Creación de Stored Procedure
    ----------------------------------------------------------------------------------------------------- */      
   PROCEDURE getMovimientosCajaChicaBody(
          V_LISTA                   OUT    cursorLista,
          V_ITEMFONDO               IN     NUMBER,
          V_PERIODOFONDO            IN     NUMBER,
          N_MONTOSALDO              IN     NUMBER);
   
   /* -----------------------------------------------------------------------------------------------------
        Nombre      : getMovimientosCajaChicaHead
        Proposito   : Procedimiento que retorna la cabecera de los movimientos realizados en Caja Chica 
                     utilizados en el reporte del mismo nombre.
        Referencias : REQ14-010 Reporte de Egresos - Tesoreria
        Parametros  : V_LISTA        --->  Cursor que retornará el procedimiento.
                      V_ITEMFONDO    --->  Item del fondo fijo seleccionado.
                      V_PERIODOFONDO --->  Periodo seleccionado de los fondos fijos.
                      N_MONTOSALDO   --->  Monto seleccionado del saldo de los fondos fijos.

        Log de Cambios
        Fecha           Autor                 Descripcion
        16.12.2014      Luis Polanco          Creación de Stored Procedure
    ----------------------------------------------------------------------------------------------------- */  
   PROCEDURE getMovimientosCajaChicaHead(
          V_LISTA                   OUT    cursorLista,
          V_ITEMFONDO               IN     NUMBER,
          V_PERIODOFONDO            IN     NUMBER,
          N_MONTOSALDO              IN     NUMBER);
   
   /* -----------------------------------------------------------------------------------------------------
        Nombre      : getEgresoHead
        Proposito   : Procedimiento que retorna los datos del Egreso seleccionado en el formulario
                     movimientos de caja chica.
        Referencias : REQ14-010 Reporte de Egresos - Tesoreria
        Parametros  : V_LISTA                --->  Cursor que retornará el procedimiento.
                      N_EMPRESAEGRESO        --->  Identificador de la empresa egreso.
                      N_ITEMEGRESOGENERAL     --->  Identificador del item general del egreso.

        Log de Cambios
        Fecha           Autor                 Descripcion
        16.12.2014      Luis Polanco          Creación de Stored Procedure
    ----------------------------------------------------------------------------------------------------- */  
   PROCEDURE getEgresoHead(
          V_LISTA                   OUT    cursorLista,
          N_EMPRESAEGRESO           IN     NUMBER,
          N_ITEMEGRESOGENERAL       IN     NUMBER);
   
   /* -----------------------------------------------------------------------------------------------------
        Nombre      : getEgresoDetalleBody
        Proposito   : Procedimiento que retorna los datos del Detalle Egreso seleccionado en el formulario
                     movimientos de caja chica (Se selecciona el Egreso).
        Referencias : REQ14-010 Reporte de Egresos - Tesoreria
        Parametros  : V_LISTA                --->  Cursor que retornará el procedimiento.
                      N_EMPRESAEGRESO        --->  Identificador de la empresa egreso.
                      N_ITEMEGRESOGENERAL     --->  Identificador del item general del egreso.

        Log de Cambios
        Fecha           Autor                 Descripcion
        16.12.2014      Luis Polanco          Creación de Stored Procedure
    ----------------------------------------------------------------------------------------------------- */  
    PROCEDURE getEgresoDetalleBody(
          V_LISTA                   OUT    cursorLista,
          N_EMPRESAEGRESO           IN     NUMBER,
          N_ITEMEGRESOGENERAL       IN     NUMBER);
   
END PKG_TESORERIA_EGRESOS;
/