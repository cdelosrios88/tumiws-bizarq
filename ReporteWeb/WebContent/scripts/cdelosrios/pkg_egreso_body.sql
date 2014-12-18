CREATE OR REPLACE PACKAGE BODY REPORTE.PKG_TESORERIA_EGRESOS
AS
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
   PROCEDURE getCadenaCajaChica (V_LISTA           OUT cursorLista,
                                 N_TIPOFONDO    IN     NUMBER,
                                 N_IDSUCURSAL   IN     NUMBER,
                                 N_ANIOFONDO    IN     NUMBER)
   IS
      var_lista   cursorLista;
   BEGIN
      OPEN var_lista FOR
         SELECT (ROWNUM - 1) AS pNROSECUENCIAL,
                TT01.pEMPRESAEGRESO,
                TT01.pITEMEGRESO,
                TT01.pITEMPERIODO,
                TT01.pITEMFONDOFIJO,
                TT01.pMONTOAPERTURA,
                TT01.pTEXTOCOMBO
           FROM (  SELECT TC.PERS_EMPRESAEGRESO_N_PK AS pEMPRESAEGRESO,
                          TC.TESO_ITEMEGRESOGENERAL_N AS pITEMEGRESO,
                          TC.TESO_ITEMPERIODOFONDO_N AS pITEMPERIODO,
                          TC.TESO_ITEMFONDOFIJO_N AS pITEMFONDOFIJO,
                          TC.COFF_MONTOAPERTURA_N AS pMONTOAPERTURA,
                          (DECODE (
                              TC.TESO_ITEMPERIODOFONDO_N,
                              NULL, '',
                              (   SUBSTR (TC.TESO_ITEMPERIODOFONDO_N, 1, 4)
                               || '-'
                               || SUBSTR (TC.TESO_ITEMPERIODOFONDO_N, 5, 6)))
                           || '-'
                           || TC.TESO_ITEMFONDOFIJO_N
                           || '  S/.'
                           || TC.COFF_MONTOAPERTURA_N)
                             AS pTEXTOCOMBO
                     FROM TESORERIA.TES_CONTROLFONDOSFIJOS TC
                    WHERE (N_TIPOFONDO IS NULL
                           OR TC.PARA_TIPOFONDOFIJO_N_COD = N_TIPOFONDO)
                          AND (N_IDSUCURSAL IS NULL
                               OR TC.SUCU_IDSUCURSAL_N = N_IDSUCURSAL)
                          AND (N_ANIOFONDO IS NULL
                               OR SUBSTR (TC.TESO_ITEMPERIODOFONDO_N, 1, 4) =
                                     N_ANIOFONDO)
                 ORDER BY 6 DESC) TT01;

      V_LISTA := var_lista;
   END getCadenaCajaChica;

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
   PROCEDURE getMovimientosCajaChicaBody (V_LISTA             OUT cursorLista,
                                          V_ITEMFONDO      IN     NUMBER,
                                          V_PERIODOFONDO   IN     NUMBER,
                                          N_MONTOSALDO     IN     NUMBER)
   IS
      var_lista   cursorLista;
   BEGIN
      OPEN var_lista FOR
           SELECT (DECODE (
                      TC.TESO_ITEMPERIODOFONDO_N,
                      NULL, '',
                      (   SUBSTR (TC.TESO_ITEMPERIODOFONDO_N, 1, 4)
                       || '-'
                       || SUBSTR (TC.TESO_ITEMPERIODOFONDO_N, 5, 6))))
                  || '-'
                  || TC.TESO_ITEMFONDOFIJO_N
                     AS pNROMOVIMIENTO,
                  TE.EGRE_FECHAEGRESO_D AS pFECHAEGRESO,
                  UPPER (TE.EGRE_OBSERVACION_V) AS pCONCEPTO,
                  TE.EGRE_MONTOTOTAL_N AS pMONTOREPORTE,
                  TE.TESO_ITEMEGRESOGENERAL_N as pITEMEGRESO
             FROM    TESORERIA.TES_EGRESOS TE
                  INNER JOIN
                     TESORERIA.TES_CONTROLFONDOSFIJOS TC
                  ON (TC.PERS_EMPRESAEGRESO_N_PK = TE.PERS_EMPRESAEGRESO_N_PK)
                     AND (TC.TESO_ITEMEGRESOGENERAL_N =
                             TE.TESO_ITEMEGRESOGENERAL_N)
            WHERE (V_ITEMFONDO IS NULL OR TC.TESO_ITEMFONDOFIJO_N = V_ITEMFONDO)
                  AND (V_PERIODOFONDO IS NULL
                       OR TC.TESO_ITEMPERIODOFONDO_N = V_PERIODOFONDO)
                  AND (N_MONTOSALDO IS NULL
                       OR TC.COFF_MONTOAPERTURA_N = N_MONTOSALDO)
         ORDER BY 5 DESC;

      V_LISTA := var_lista;
   END getMovimientosCajaChicaBody;

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
   PROCEDURE getMovimientosCajaChicaHead (V_LISTA             OUT cursorLista,
                                          V_ITEMFONDO      IN     NUMBER,
                                          V_PERIODOFONDO   IN     NUMBER,
                                          N_MONTOSALDO     IN     NUMBER)
   IS
      var_lista   cursorLista;
   BEGIN
      OPEN var_lista FOR
         SELECT ( (DECODE (
                      TC.TESO_ITEMPERIODOFONDO_N,
                      NULL, '',
                      (   SUBSTR (TC.TESO_ITEMPERIODOFONDO_N, 1, 4)
                       || '-'
                       || SUBSTR (TC.TESO_ITEMPERIODOFONDO_N, 5, 6))))
                 || '-'
                 || TC.TESO_ITEMFONDOFIJO_N)
                   AS pNROMOVIMIENTO,
                TC.COFF_ESTADOFONDO_N_COD AS pCODIGOESTADO,
                DECODE (TC.COFF_ESTADOFONDO_N_COD,
                        1, 'ABIERTO',
                        2, 'CERRADO',
                        'NA')
                   AS pDESCESTADO,
                TC.SUCU_IDSUCURSAL_N,
                (CASE
                    WHEN PE.PERS_TIPOPERSONA_N_COD = 1
                    THEN
                       (SELECT UPPER (
                                     PN.NATU_APELLIDOPATERNO_V
                                  || ' '
                                  || PN.NATU_APELLIDOMATERNO_V
                                  || ' '
                                  || PN.NATU_NOMBRES_V)
                          FROM PERSONA.PER_NATURAL PN
                         WHERE PN.PERS_PERSONA_N = PE.PERS_PERSONA_N) --PERSONA NATURAL
                    WHEN PE.PERS_TIPOPERSONA_N_COD = 2
                    THEN
                       (SELECT UPPER (PJ.JURI_RAZONSOCIAL_V)
                          FROM PERSONA.PER_JURIDICA PJ
                         WHERE PJ.PERS_PERSONA_N = PE.PERS_PERSONA_N) --PERSONA JURIDICA
                    ELSE
                       'NA'
                 END)
                   AS pNOMBRESUCURSAL,
                TC.COFF_MONTOAPERTURA_N AS pMONTOASIGNADO,
                TC.COFF_MONTOSALDO_N AS pMONTOSALDO,
                TC.COFF_MONTOUTILIZADO_N AS pMONTOOTORGADO,
                TO_CHAR (TC.COFF_FECHACIERRE_D, 'DD/MM/YYYY') AS pFECHACIERRE
           FROM TESORERIA.TES_EGRESOS TE
                INNER JOIN TESORERIA.TES_CONTROLFONDOSFIJOS TC
                   ON (TC.PERS_EMPRESAEGRESO_N_PK =
                          TE.PERS_EMPRESAEGRESO_N_PK)
                      AND (TC.TESO_ITEMEGRESOGENERAL_N =
                              TE.TESO_ITEMEGRESOGENERAL_N)
                LEFT JOIN SEGURIDAD.SEG_M_SUCURSAL SS
                   ON (TC.SUCU_IDSUCURSAL_N = SS.SUCU_IDSUCURSAL_N)
                LEFT JOIN PERSONA.PER_PERSONA PE
                   ON (PE.PERS_PERSONA_N = SS.PERS_PERSONA_N_PK)
          WHERE (V_ITEMFONDO IS NULL OR TC.TESO_ITEMFONDOFIJO_N = V_ITEMFONDO)
                AND (V_PERIODOFONDO IS NULL
                     OR TC.TESO_ITEMPERIODOFONDO_N = V_PERIODOFONDO)
                AND (N_MONTOSALDO IS NULL
                     OR TC.COFF_MONTOAPERTURA_N = N_MONTOSALDO);

      V_LISTA := var_lista;
   END getMovimientosCajaChicaHead;

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
   PROCEDURE getEgresoHead (V_LISTA                  OUT cursorLista,
                            N_EMPRESAEGRESO       IN     NUMBER,
                            N_ITEMEGRESOGENERAL   IN     NUMBER)
   IS
      var_lista   cursorLista;
   BEGIN
      OPEN var_lista FOR
         SELECT TC.PERS_EMPRESAEGRESO_N_PK pIntEmpresaEgreso_n,
                TC.TESO_ITEMEGRESOGENERAL_N pInItemEgresoGeneral_n,
                ( (DECODE (
                      TC.TESO_ITEMPERIODOFONDO_N,
                      NULL, '',
                      (   SUBSTR (TC.TESO_ITEMPERIODOFONDO_N, 1, 4)
                       || '-'
                       || SUBSTR (TC.TESO_ITEMPERIODOFONDO_N, 5, 6))))
                 || '-'
                 || TC.TESO_ITEMFONDOFIJO_N)
                   AS pNROMOVIMIENTO,
                ( (DECODE (
                      TE.EGRE_ITEMPERIODOEGRESO_N,
                      NULL, '',
                      (   SUBSTR (TE.EGRE_ITEMPERIODOEGRESO_N, 1, 4)
                       || '-'
                       || SUBSTR (TE.EGRE_ITEMPERIODOEGRESO_N, 5, 6))))
                 || '-'
                 || TE.EGRE_ITEMEGRESO_N)
                   AS pNROEGRESO,
                TC.SUCU_IDSUCURSAL_N pIDSUCURSAL_N,
                (CASE
                    WHEN PE.PERS_TIPOPERSONA_N_COD = 1
                    THEN
                       (SELECT UPPER (
                                     PN.NATU_APELLIDOPATERNO_V
                                  || ' '
                                  || PN.NATU_APELLIDOMATERNO_V
                                  || ' '
                                  || PN.NATU_NOMBRES_V)
                          FROM PERSONA.PER_NATURAL PN
                         WHERE PN.PERS_PERSONA_N = PE.PERS_PERSONA_N) --PERSONA NATURAL
                    WHEN PE.PERS_TIPOPERSONA_N_COD = 2
                    THEN
                       (SELECT UPPER (PJ.JURI_RAZONSOCIAL_V)
                          FROM PERSONA.PER_JURIDICA PJ
                         WHERE PJ.PERS_PERSONA_N = PE.PERS_PERSONA_N) --PERSONA JURIDICA
                    ELSE
                       'NA'
                 END)
                   AS pNOMBRESUCURSAL,
                TO_CHAR (TE.EGRE_FECHAEGRESO_D, 'DD/MM/YYYY') AS pFECHAEGRESO,
                UPPER (TE.EGRE_OBSERVACION_V) AS pCONCEPTO,
                TE.PARA_FORMAPAGO_N_COD AS pNROFORMAPAGO,
                UPPER (MT.TABL_DESCRIPCION_V) AS pFORMAPAGO,
                (SELECT UPPER (MT.TABL_DESCRIPCION_V)
                   FROM    TESORERIA.TES_EGRESODETALLE TED
                        LEFT JOIN
                           PARAMETRO.MAE_M_TABLAS MT
                        ON MT.TABL_IDDETALLE_N = TED.PARA_TIPOMONEDA_N_COD
                  WHERE TED.PERS_EMPRESAEGRESO_N_PK =
                           TC.PERS_EMPRESAEGRESO_N_PK
                        AND TED.TESO_ITEMEGRESOGENERAL_N =
                               TC.TESO_ITEMEGRESOGENERAL_N
                        AND MT.TABL_IDMAESTRO_N = 47
                        AND ROWNUM = 1)
                   AS pMONEDA,
                TE.EGRE_MONTOTOTAL_N AS pMONTO,
                UPPER (
                   PKG_UTIL_NUMBER.getLetrasNumero (TE.EGRE_MONTOTOTAL_N))
                   AS pDESCRIPCIONMONTO,
                TC.COFF_MONTOAPERTURA_N AS pMONTOAPERTURA,
                TC.COFF_MONTOSALDO_N AS pMONTOSALDO,
                TC.COFF_MONTOUTILIZADO_N AS pMONTOUTILIZADO,
                TO_CHAR (TC.COFF_FECHACIERRE_D, 'DD/MM/YYYY') AS pFECHACIERRE,
                UPPER (TB.BACU_NOMBRECUENTA_V) AS pNOMBRECUENTA,
                TB.CONT_NUMEROCUENTA_V AS pCONTNROCUENTA,
                (CASE
                    WHEN PE0.PERS_TIPOPERSONA_N_COD = 1
                    THEN
                       (SELECT UPPER (
                                     PN.NATU_APELLIDOPATERNO_V
                                  || ' '
                                  || PN.NATU_APELLIDOMATERNO_V
                                  || ' '
                                  || PN.NATU_NOMBRES_V)
                          FROM PERSONA.PER_NATURAL PN
                         WHERE PN.PERS_PERSONA_N = PE0.PERS_PERSONA_N) --PERSONA NATURAL
                    WHEN PE0.PERS_TIPOPERSONA_N_COD = 2
                    THEN
                       (SELECT UPPER (PJ.JURI_RAZONSOCIAL_V)
                          FROM PERSONA.PER_JURIDICA PJ
                         WHERE PJ.PERS_PERSONA_N = PE0.PERS_PERSONA_N) --PERSONA JURIDICA
                    ELSE
                       'NA'
                 END)
                   AS pNOMBREBANCO,
                TE.EGRE_NUMEROCHEQUE_N AS pNROCHEQUE,
                TE.EGRE_NUMEROPLANILLA_N AS pNROPLANILLA,
                (TE.CONT_PERIODOLIBRO_N || '-' || TE.CONT_CODIGOLIBRO_N)
                   pNROASIENTO,
                TE.PERS_PERSONAUSUARIO_N_PK AS pPKNOMBREUSUARIO,
                (CASE
                    WHEN PE0.PERS_TIPOPERSONA_N_COD = 1
                    THEN
                       (SELECT UPPER (
                                     PN.NATU_APELLIDOPATERNO_V
                                  || ' '
                                  || PN.NATU_APELLIDOMATERNO_V
                                  || ' '
                                  || PN.NATU_NOMBRES_V)
                          FROM PERSONA.PER_NATURAL PN
                         WHERE PN.PERS_PERSONA_N = PE0.PERS_PERSONA_N) --PERSONA NATURAL
                    WHEN PE0.PERS_TIPOPERSONA_N_COD = 2
                    THEN
                       (SELECT UPPER (PJ.JURI_RAZONSOCIAL_V)
                          FROM PERSONA.PER_JURIDICA PJ
                         WHERE PJ.PERS_PERSONA_N = PE0.PERS_PERSONA_N) --PERSONA JURIDICA
                    ELSE
                       'NA'
                 END)
                   AS pNOMBREUSUARIO,
                TO_CHAR (TE.EGRE_FECHAREGISTRO_D, 'DD/MM/YYYY HH:MI:SS')
                   AS pFECHAHORA,
                TC.PERS_PERSONARESPONSABLE_N_PK AS pPKPERSONAENTREGUE,
                (CASE
                    WHEN PE2.PERS_TIPOPERSONA_N_COD = 1
                    THEN
                       (SELECT UPPER (
                                     PN.NATU_APELLIDOPATERNO_V
                                  || ' '
                                  || PN.NATU_APELLIDOMATERNO_V
                                  || ' '
                                  || PN.NATU_NOMBRES_V)
                          FROM PERSONA.PER_NATURAL PN
                         WHERE PN.PERS_PERSONA_N = PE2.PERS_PERSONA_N) --PERSONA NATURAL
                    WHEN PE0.PERS_TIPOPERSONA_N_COD = 2
                    THEN
                       (SELECT UPPER (PJ.JURI_RAZONSOCIAL_V)
                          FROM PERSONA.PER_JURIDICA PJ
                         WHERE PJ.PERS_PERSONA_N = PE2.PERS_PERSONA_N) --PERSONA JURIDICA
                    ELSE
                       'NA'
                 END)
                   AS pPERSONAENTREGUE,
                PD.DOCU_NUMEROIDENTIDAD_V AS pNRODOCUMENTO,
                PD.PARA_TIPOIDENTIDAD_N_COD AS pCODTIPODOCUMENTO,
                UPPER (MT1.TABL_DESCRIPCION_V) AS pDESCTIPODOCUMENTO
           FROM TESORERIA.TES_EGRESOS TE
                INNER JOIN TESORERIA.TES_CONTROLFONDOSFIJOS TC
                   ON (TC.PERS_EMPRESAEGRESO_N_PK =
                          TE.PERS_EMPRESAEGRESO_N_PK)
                      AND (TC.TESO_ITEMEGRESOGENERAL_N =
                              TE.TESO_ITEMEGRESOGENERAL_N)
                LEFT JOIN SEGURIDAD.SEG_M_SUCURSAL SS
                   ON (TC.SUCU_IDSUCURSAL_N = SS.SUCU_IDSUCURSAL_N)
                LEFT JOIN PERSONA.PER_PERSONA PE
                   ON (PE.PERS_PERSONA_N = SS.PERS_PERSONA_N_PK)
                INNER JOIN PARAMETRO.MAE_M_TABLAS MT
                   ON (MT.TABL_IDDETALLE_N = TE.PARA_FORMAPAGO_N_COD)
                INNER JOIN TESORERIA.TES_BANCOCUENTA TB
                   ON TB.TESO_ITEMBANCOCUENTA_N = TE.EGRE_ITEMBANCOCUENTA_N
                LEFT JOIN PERSONA.PER_PERSONA PE0
                   ON PE0.PERS_PERSONA_N = TB.PERS_PERSONA_N
                LEFT JOIN PERSONA.PER_PERSONA PE1
                   ON PE1.PERS_PERSONA_N = TE.PERS_PERSONAUSUARIO_N_PK
                LEFT JOIN PERSONA.PER_PERSONA PE2
                   ON PE2.PERS_PERSONA_N = TC.PERS_PERSONARESPONSABLE_N_PK
                LEFT JOIN PERSONA.PER_DOCUMENTO PD
                   ON PE2.PERS_PERSONA_N = PD.PERS_PERSONA_N
                INNER JOIN PARAMETRO.MAE_M_TABLAS MT1
                   ON (MT1.TABL_IDDETALLE_N = PD.PARA_TIPOIDENTIDAD_N_COD)
          WHERE (N_EMPRESAEGRESO IS NULL
                 OR TC.PERS_EMPRESAEGRESO_N_PK = N_EMPRESAEGRESO)
                AND (N_ITEMEGRESOGENERAL IS NULL
                     OR TC.TESO_ITEMEGRESOGENERAL_N = N_ITEMEGRESOGENERAL)
                AND MT.TABL_IDMAESTRO_N = 217
                AND MT1.TABL_IDMAESTRO_N = 294;

      V_LISTA := var_lista;
   END getEgresoHead;

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
   PROCEDURE getEgresoDetalleBody (V_LISTA                  OUT cursorLista,
                                   N_EMPRESAEGRESO       IN     NUMBER,
                                   N_ITEMEGRESOGENERAL   IN     NUMBER)
   IS
      var_lista   cursorLista;
   BEGIN
      OPEN var_lista FOR
           SELECT TED.PERS_EMPRESAEGRESO_N_PK pIntEmpresaEgreso_n,
                  TED.TESO_ITEMEGRESOGENERAL_N pInItemEgresoGeneral_n,
                  TED.PARA_TIPOMONEDA_N_COD AS pCODMONEDA,
                  UPPER (MT.TABL_DESCRIPCION_V) AS pNOMBREMONEDA,
                  UPPER (TED.EGDE_DESCRIPCIONEGRESO_V) AS pDESCRIPCION,
                  TED.SUCU_IDSUCURSAL_N AS pCODSUCURSAL,
                  (CASE
                      WHEN PE.PERS_TIPOPERSONA_N_COD = 1
                      THEN
                         (SELECT UPPER (
                                       PN.NATU_APELLIDOPATERNO_V
                                    || ' '
                                    || PN.NATU_APELLIDOMATERNO_V
                                    || ' '
                                    || PN.NATU_NOMBRES_V)
                            FROM PERSONA.PER_NATURAL PN
                           WHERE PN.PERS_PERSONA_N = PE.PERS_PERSONA_N) --PERSONA NATURAL
                      WHEN PE.PERS_TIPOPERSONA_N_COD = 2
                      THEN
                         (SELECT UPPER (PJ.JURI_RAZONSOCIAL_V)
                            FROM PERSONA.PER_JURIDICA PJ
                           WHERE PJ.PERS_PERSONA_N = PE.PERS_PERSONA_N) --PERSONA JURIDICA
                      ELSE
                         'NA'
                   END)
                     AS pNOMBRESUCURSAL,
                  TED.EGDE_MONTOCARGO_N AS pMONTODEBE,
                  TED.EGDE_MONTOABONO_N AS pMONTOABONO
             FROM TESORERIA.TES_EGRESODETALLE TED
                  LEFT JOIN PARAMETRO.MAE_M_TABLAS MT
                     ON MT.TABL_IDDETALLE_N = TED.PARA_TIPOMONEDA_N_COD
                  LEFT JOIN SEGURIDAD.SEG_M_SUCURSAL SS
                     ON (TED.SUCU_IDSUCURSAL_N = SS.SUCU_IDSUCURSAL_N)
                  LEFT JOIN PERSONA.PER_PERSONA PE
                     ON (PE.PERS_PERSONA_N = SS.PERS_PERSONA_N_PK)
            WHERE (N_EMPRESAEGRESO IS NULL
                   OR TED.PERS_EMPRESAEGRESO_N_PK = N_EMPRESAEGRESO)
                  AND (N_ITEMEGRESOGENERAL IS NULL
                       OR TED.TESO_ITEMEGRESOGENERAL_N = N_ITEMEGRESOGENERAL)
                  AND MT.TABL_IDMAESTRO_N = 47
         ORDER BY 1, 2 DESC;

      V_LISTA := var_lista;
   END getEgresoDetalleBody;
END PKG_TESORERIA_EGRESOS;
/
