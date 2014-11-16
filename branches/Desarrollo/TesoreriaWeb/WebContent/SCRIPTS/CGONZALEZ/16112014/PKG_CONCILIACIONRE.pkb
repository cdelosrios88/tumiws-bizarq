CREATE OR REPLACE PACKAGE BODY TESORERIA.PKG_CONCILIACIONRE
AS
   PROCEDURE grabar (
      v_empresaconciliacion      IN OUT TES_CONCILIACIONRE.PERS_EMPRESACONCILIACION_N_PK%TYPE,
      v_itemconciliacion         IN OUT TES_CONCILIACIONRE.TESO_ITEMCONCILIACIONRE_N%TYPE,
      v_fechaconciliacion        IN OUT TES_CONCILIACIONRE.CORE_FECHACONCILIACION_D%TYPE,
      v_empresa                  IN OUT TES_CONCILIACIONRE.PERS_EMPRESA_N_PK%TYPE,
      v_itembancofondo           IN OUT TES_CONCILIACIONRE.TESO_ITEMBANCOFONDO_N%TYPE,
      v_itembancocuenta          IN OUT TES_CONCILIACIONRE.TESO_ITEMBANCOCUENTA_N%TYPE,
      v_montosaldoinicial        IN OUT TES_CONCILIACIONRE.CORE_MONTOSALDOINICIAL_N%TYPE,
      v_montodebe                IN OUT TES_CONCILIACIONRE.CORE_MONTODEBE_N%TYPE,
      v_montohaber               IN OUT TES_CONCILIACIONRE.CORE_MONTOHABER_N%TYPE,
      v_registrosconciliados     IN OUT TES_CONCILIACIONRE.CORE_REGISTROSCONCILIADOS_N%TYPE,
      v_registrosnoconciliados   IN OUT TES_CONCILIACIONRE.CORE_REGISTROSNOCONCILIADOS_N%TYPE,
      v_empresaconci             IN OUT TES_CONCILIACIONRE.PERS_EMPRESACONCI_N_PK%TYPE,
      v_personaconci             IN OUT TES_CONCILIACIONRE.PERS_PERSONACONCI_N_PK%TYPE,
      v_empresaconcianula        IN OUT TES_CONCILIACIONRE.PERS_EMPRESACONCIANULA_N_PK%TYPE,
      v_personaconcianula        IN OUT TES_CONCILIACIONRE.PERS_PERSONACONCIANULA_N_PK%TYPE,
      v_fechaanula               IN OUT TES_CONCILIACIONRE.CORE_FECHAANULA_D%TYPE,
      v_observacionanula         IN OUT TES_CONCILIACIONRE.CORE_OBSERVACIONANULA_V%TYPE,
      v_estado                   IN OUT TES_CONCILIACIONRE.PARA_ESTADO_N_COD%TYPE,
      v_fechaconcilia            IN OUT TES_CONCILIACIONRE.CORE_FECHACONCILIA_D%TYPE)
   IS
   BEGIN
      SELECT SEQ_CONCILIACION.NEXTVAL INTO v_itemconciliacion FROM DUAL;

      INSERT
        INTO TES_CONCILIACIONRE (
                TES_CONCILIACIONRE.PERS_EMPRESACONCILIACION_N_PK,
                TES_CONCILIACIONRE.TESO_ITEMCONCILIACIONRE_N,
                TES_CONCILIACIONRE.CORE_FECHACONCILIACION_D,
                TES_CONCILIACIONRE.PERS_EMPRESA_N_PK,
                TES_CONCILIACIONRE.TESO_ITEMBANCOFONDO_N,
                TES_CONCILIACIONRE.TESO_ITEMBANCOCUENTA_N,
                TES_CONCILIACIONRE.CORE_MONTOSALDOINICIAL_N,
                TES_CONCILIACIONRE.CORE_MONTODEBE_N,
                TES_CONCILIACIONRE.CORE_MONTOHABER_N,
                TES_CONCILIACIONRE.CORE_REGISTROSCONCILIADOS_N,
                TES_CONCILIACIONRE.CORE_REGISTROSNOCONCILIADOS_N,
                TES_CONCILIACIONRE.PERS_EMPRESACONCI_N_PK,
                TES_CONCILIACIONRE.PERS_PERSONACONCI_N_PK,
                TES_CONCILIACIONRE.PERS_EMPRESACONCIANULA_N_PK,
                TES_CONCILIACIONRE.PERS_PERSONACONCIANULA_N_PK,
                TES_CONCILIACIONRE.CORE_FECHAANULA_D,
                TES_CONCILIACIONRE.CORE_OBSERVACIONANULA_V,
                TES_CONCILIACIONRE.PARA_ESTADO_N_COD,
                TES_CONCILIACIONRE.CORE_FECHACONCILIA_D)
      VALUES (v_empresaconciliacion,
              v_itemconciliacion,
              v_fechaconciliacion,
              v_empresa,
              v_itembancofondo,
              v_itembancocuenta,
              v_montosaldoinicial,
              v_montodebe,
              v_montohaber,
              v_registrosconciliados,
              v_registrosnoconciliados,
              v_empresaconci,
              v_personaconci,
              v_empresaconcianula,
              v_personaconcianula,
              v_fechaanula,
              v_observacionanula,
              v_estado,
              v_fechaconcilia);
   END grabar;



   PROCEDURE modificar (
      v_empresaconciliacion      IN OUT TES_CONCILIACIONRE.PERS_EMPRESACONCILIACION_N_PK%TYPE,
      v_itemconciliacion         IN OUT TES_CONCILIACIONRE.TESO_ITEMCONCILIACIONRE_N%TYPE,
      v_fechaconciliacion        IN OUT TES_CONCILIACIONRE.CORE_FECHACONCILIACION_D%TYPE,
      v_empresa                  IN OUT TES_CONCILIACIONRE.PERS_EMPRESA_N_PK%TYPE,
      v_itembancofondo           IN OUT TES_CONCILIACIONRE.TESO_ITEMBANCOFONDO_N%TYPE,
      v_itembancocuenta          IN OUT TES_CONCILIACIONRE.TESO_ITEMBANCOCUENTA_N%TYPE,
      v_montosaldoinicial        IN OUT TES_CONCILIACIONRE.CORE_MONTOSALDOINICIAL_N%TYPE,
      v_montodebe                IN OUT TES_CONCILIACIONRE.CORE_MONTODEBE_N%TYPE,
      v_montohaber               IN OUT TES_CONCILIACIONRE.CORE_MONTOHABER_N%TYPE,
      v_registrosconciliados     IN OUT TES_CONCILIACIONRE.CORE_REGISTROSCONCILIADOS_N%TYPE,
      v_registrosnoconciliados   IN OUT TES_CONCILIACIONRE.CORE_REGISTROSNOCONCILIADOS_N%TYPE,
      v_empresaconci             IN OUT TES_CONCILIACIONRE.PERS_EMPRESACONCI_N_PK%TYPE,
      v_personaconci             IN OUT TES_CONCILIACIONRE.PERS_PERSONACONCI_N_PK%TYPE,
      v_empresaconcianula        IN OUT TES_CONCILIACIONRE.PERS_EMPRESACONCIANULA_N_PK%TYPE,
      v_personaconcianula        IN OUT TES_CONCILIACIONRE.PERS_PERSONACONCIANULA_N_PK%TYPE,
      v_fechaanula               IN OUT TES_CONCILIACIONRE.CORE_FECHAANULA_D%TYPE,
      v_observacionanula         IN OUT TES_CONCILIACIONRE.CORE_OBSERVACIONANULA_V%TYPE,
      v_estado                   IN OUT TES_CONCILIACIONRE.PARA_ESTADO_N_COD%TYPE,
      v_fechaconcilia            IN OUT TES_CONCILIACIONRE.CORE_FECHACONCILIA_D%TYPE)
   IS
   BEGIN
      UPDATE TES_CONCILIACIONRE
         SET         --PPERS_EMPRESACONCILIACION_N_PK = v_empresaconciliacion,
             --PTESO_ITEMCONCILIACIONRE_N = v_itemconciliacion,
             CORE_FECHACONCILIACION_D = v_fechaconciliacion,
             PERS_EMPRESA_N_PK = v_empresa,
             TESO_ITEMBANCOFONDO_N = v_itembancofondo,
             TESO_ITEMBANCOCUENTA_N = v_itembancocuenta,
             CORE_MONTOSALDOINICIAL_N = v_montosaldoinicial,
             CORE_MONTODEBE_N = v_montodebe,
             CORE_MONTOHABER_N = v_montohaber,
             CORE_REGISTROSCONCILIADOS_N = v_registrosconciliados,
             CORE_REGISTROSNOCONCILIADOS_N = v_registrosnoconciliados,
             PERS_EMPRESACONCI_N_PK = v_empresaconci,
             PERS_PERSONACONCI_N_PK = v_personaconci,
             PERS_EMPRESACONCIANULA_N_PK = v_empresaconcianula,
             PERS_PERSONACONCIANULA_N_PK = v_personaconcianula,
             CORE_FECHAANULA_D = v_fechaanula,
             CORE_OBSERVACIONANULA_V = v_observacionanula,
             PARA_ESTADO_N_COD = v_estado,
             CORE_FECHACONCILIA_D = v_fechaconcilia
       WHERE PERS_EMPRESACONCILIACION_N_PK = v_empresaconciliacion
             AND TESO_ITEMCONCILIACIONRE_N = v_itemconciliacion;
   END modificar;


   PROCEDURE getListaPorPk (
      V_LISTA                    OUT cursorLista,
      v_empresaconciliacion   IN     TES_CONCILIACIONRE.PERS_EMPRESACONCILIACION_N_PK%TYPE,
      v_itemconciliacion      IN     TES_CONCILIACIONRE.TESO_ITEMCONCILIACIONRE_N%TYPE)
   IS
      var_lista   cursorLista;
   BEGIN
      OPEN var_lista FOR
         SELECT TES_CONCILIACIONRE.PERS_EMPRESACONCILIACION_N_PK
                   pempresaconciliacion,
                TES_CONCILIACIONRE.TESO_ITEMCONCILIACIONRE_N
                   pitemconciliacion,
                TES_CONCILIACIONRE.CORE_FECHACONCILIACION_D
                   pfechaconciliacion,
                TES_CONCILIACIONRE.PERS_EMPRESA_N_PK pempresa,
                TES_CONCILIACIONRE.TESO_ITEMBANCOFONDO_N pitembancofondo,
                TES_CONCILIACIONRE.TESO_ITEMBANCOCUENTA_N pitembancocuenta,
                TES_CONCILIACIONRE.CORE_MONTOSALDOINICIAL_N
                   pmontosaldoinicial,
                TES_CONCILIACIONRE.CORE_MONTODEBE_N pmontodebe,
                TES_CONCILIACIONRE.CORE_MONTOHABER_N pmontohaber,
                TES_CONCILIACIONRE.CORE_REGISTROSCONCILIADOS_N
                   pregistrosconciliados,
                TES_CONCILIACIONRE.CORE_REGISTROSNOCONCILIADOS_N
                   pregistrosnoconciliados,
                TES_CONCILIACIONRE.PERS_EMPRESACONCI_N_PK pempresaconci,
                TES_CONCILIACIONRE.PERS_PERSONACONCI_N_PK ppersonaconci,
                TES_CONCILIACIONRE.PERS_EMPRESACONCIANULA_N_PK
                   pempresaconcianula,
                TES_CONCILIACIONRE.PERS_PERSONACONCIANULA_N_PK
                   ppersonaconcianula,
                TES_CONCILIACIONRE.CORE_FECHAANULA_D pfechaanula,
                TES_CONCILIACIONRE.CORE_OBSERVACIONANULA_V pobservacionanula,
                TES_CONCILIACIONRE.PARA_ESTADO_N_COD pestado,
                TES_CONCILIACIONRE.CORE_FECHACONCILIA_D ptsFechaConcilia
           FROM TES_CONCILIACIONRE
          WHERE PERS_EMPRESACONCILIACION_N_PK = v_empresaconciliacion
                AND TESO_ITEMCONCILIACIONRE_N = v_itemconciliacion;

      V_LISTA := var_lista;
   END getListaPorPk;


   /**/
   /**/

   PROCEDURE getListFilter (V_LISTA                     OUT cursorLista,
                            dtBusqFechaDesde         IN     DATE,
                            dtBusqFechaHasta         IN     DATE,
                            intBusqPersEmpresa       IN     NUMERIC,
                            intBusqItemBancoFondo    IN     NUMERIC,
                            intBusqItemBancoCuenta   IN     NUMERIC)
   IS
      var_lista   cursorLista;
   BEGIN
      OPEN var_lista FOR
           SELECT CONC.PERS_EMPRESACONCILIACION_N_PK pempresaconciliacion,
                  CONC.TESO_ITEMCONCILIACIONRE_N pitemconciliacion,
                  CONC.CORE_FECHACONCILIACION_D pfechaconciliacion,
                  CONC.PERS_EMPRESA_N_PK pempresa,
                  CONC.TESO_ITEMBANCOFONDO_N pitembancofondo,
                  CONC.TESO_ITEMBANCOCUENTA_N pitembancocuenta,
                  CONC.CORE_MONTOSALDOINICIAL_N pmontosaldoinicial,
                  CONC.CORE_MONTODEBE_N pmontodebe,
                  CONC.CORE_MONTOHABER_N pmontohaber,
                  CONC.CORE_REGISTROSCONCILIADOS_N pregistrosconciliados,
                  CONC.CORE_REGISTROSNOCONCILIADOS_N pregistrosnoconciliados,
                  CONC.PERS_EMPRESACONCI_N_PK pempresaconci,
                  CONC.PERS_PERSONACONCI_N_PK ppersonaconci,
                  CONC.PERS_EMPRESACONCIANULA_N_PK pempresaconcianula,
                  CONC.PERS_PERSONACONCIANULA_N_PK ppersonaconcianula,
                  CONC.CORE_FECHAANULA_D pfechaanula,
                  CONC.CORE_OBSERVACIONANULA_V pobservacionanula,
                  CONC.PARA_ESTADO_N_COD pestado,
                  CONC.CORE_FECHACONCILIA_D ptsFechaConcilia,
                  --
                  PERJU.JURI_RAZONSOCIAL_V pDescripcionbanco,
                  MAE1.TABL_DESCRIPCION_V pDescripciontipocuenta,
                  MAE2.TABL_DESCRIPCION_V pDescripcionmoneda,
                  BCOCTA.CONT_NUMEROCUENTA_V pNrocuenta,
                  (  CONC.CORE_MONTOSALDOINICIAL_N
                   + CONC.CORE_MONTODEBE_N
                   + CONC.CORE_MONTOHABER_N)
                     pSaldocaja,
                  (CONC.CORE_REGISTROSCONCILIADOS_N
                   + CONC.CORE_REGISTROSNOCONCILIADOS_N)
                     pNromovimientos,
                  SUM (
                       det1.CODE_SALDOINICIAL
                     + det1.CODE_MONTODEBE_N
                     - det1.CODE_MONTOHABER_N)
                     pSaldoConciliacion,
                  SUM (
                       det2.CODE_SALDOINICIAL
                     + det2.CODE_MONTODEBE_N
                     - det2.CODE_MONTOHABER_N)
                     pPorconciliar
             --
             FROM TES_CONCILIACIONRE CONC,
                  TES_CONCILIACIONDET DET1,
                  TES_CONCILIACIONDET DET2,
                  PERSONA.PER_JURIDICA PERJU,
                  PERSONA.PER_PERSONA PERS,
                  TES_BANCOCUENTA BCOCTA,
                  PERSONA.PER_CUENTABANCARIA CTABRIA,
                  PARAMETRO.MAE_M_TABLAS MAE1,
                  PARAMETRO.MAE_M_TABLAS MAE2
            WHERE BCOCTA.PERS_EMPRESA_N_PK = CONC.PERS_EMPRESA_N_PK
                  AND BCOCTA.TESO_ITEMBANCOFONDO_N = CONC.TESO_ITEMBANCOFONDO_N
                  AND BCOCTA.TESO_ITEMBANCOCUENTA_N =
                         CONC.TESO_ITEMBANCOCUENTA_N
                  AND BCOCTA.PERS_PERSONA_N = PERJU.PERS_PERSONA_N
                  AND PERJU.PERS_PERSONA_N = PERS.PERS_PERSONA_N
                  AND BCOCTA.PERS_EMPRESA_N_PK(+) = CTABRIA.PERS_PERSONA_N
                  AND BCOCTA.PERS_CUENTABANCARIA_N =
                         CTABRIA.PERS_CUENTABANCARIA_N
                  AND MAE1.TABL_IDMAESTRO_N = 202               -- TIPO CUENTA
                  AND MAE2.TABL_IDMAESTRO_N = 47                -- TIPO MONEDA
                  AND CTABRIA.CUBA_TIPOCUENTA_N_COD = MAE1.TABL_IDDETALLE_N
                  AND CTABRIA.CUBA_MONEDA_N_COD = MAE2.TABL_IDDETALLE_N
                  AND (CONC.PERS_EMPRESACONCILIACION_N_PK =
                          DET1.PERS_EMPRESACONCILIACION_N_PK(+))
                  AND (CONC.TESO_ITEMCONCILIACIONRE_N =
                          DET1.TESO_ITEMCONCILIACIONRE_N(+))
                  AND (DET1.CODE_INDICADORCONCI_N(+) = 1)
                  AND (CONC.PERS_EMPRESACONCILIACION_N_PK =
                          DET2.PERS_EMPRESACONCILIACION_N_PK(+))
                  AND (CONC.TESO_ITEMCONCILIACIONRE_N =
                          DET2.TESO_ITEMCONCILIACIONRE_N(+))
                  AND (DET2.CODE_INDICADORCONCI_N(+) = 0)
                  -- FILTROS FECHA Y BANCO
                  AND (intBusqItemBancoFondo IS NULL
                       OR CONC.TESO_ITEMBANCOFONDO_N = intBusqItemBancoFondo)
                  AND (intBusqItemBancoCuenta IS NULL
                       OR CONC.TESO_ITEMBANCOCUENTA_N = intBusqItemBancoCuenta)
                  AND (dtBusqFechaDesde IS NULL
                       OR TRUNC(CONC.CORE_FECHACONCILIACION_D) >= TRUNC(dtBusqFechaDesde))
                  AND (dtBusqFechaHasta IS NULL
                       OR TRUNC(CONC.CORE_FECHACONCILIACION_D) <= TRUNC(dtBusqFechaHasta))
         GROUP BY CONC.PERS_EMPRESACONCILIACION_N_PK,
                  CONC.TESO_ITEMCONCILIACIONRE_N,
                  CONC.CORE_FECHACONCILIACION_D,
                  CONC.PERS_EMPRESA_N_PK,
                  CONC.TESO_ITEMBANCOFONDO_N,
                  CONC.TESO_ITEMBANCOCUENTA_N,
                  CONC.CORE_MONTOSALDOINICIAL_N,
                  CONC.CORE_MONTODEBE_N,
                  CONC.CORE_MONTOHABER_N,
                  CONC.CORE_REGISTROSCONCILIADOS_N,
                  CONC.CORE_REGISTROSNOCONCILIADOS_N,
                  CONC.PERS_EMPRESACONCI_N_PK,
                  CONC.PERS_PERSONACONCI_N_PK,
                  CONC.PERS_EMPRESACONCIANULA_N_PK,
                  CONC.PERS_PERSONACONCIANULA_N_PK,
                  CONC.CORE_FECHAANULA_D,
                  CONC.CORE_OBSERVACIONANULA_V,
                  CONC.PARA_ESTADO_N_COD,
                  CONC.CORE_FECHACONCILIA_D,
                  PERJU.JURI_RAZONSOCIAL_V,
                  MAE1.TABL_DESCRIPCION_V,
                  MAE2.TABL_DESCRIPCION_V,
                  BCOCTA.CONT_NUMEROCUENTA_V,
                  CONC.CORE_MONTOSALDOINICIAL_N,
                  CONC.CORE_MONTODEBE_N,
                  CONC.CORE_MONTOHABER_N;

      V_LISTA := var_lista;
   END getListFilter;
   
   /*Inicio: REQ14-006 Bizarq - 26/10/2014 */
   PROCEDURE getLastConciliacionByCuenta (
      V_LISTA                    OUT cursorLista,
      v_empresaconciliacion   IN     TES_CONCILIACIONRE.PERS_EMPRESACONCILIACION_N_PK%TYPE,
      V_PARA_ESTADO_N         IN     TES_CONCILIACIONRE.PARA_ESTADO_N_COD%TYPE,
      V_ITEMBANCOFONDO_N      IN     TES_CONCILIACIONRE.TESO_ITEMBANCOFONDO_N%TYPE,
      V_ITEMBANCOCUENTA_N     IN     TES_CONCILIACIONRE.TESO_ITEMBANCOCUENTA_N%TYPE)
   IS
      var_lista   cursorLista;
   BEGIN
      OPEN var_lista FOR
         SELECT PERS_EMPRESACONCILIACION_N_PK pempresaconciliacion,
                TESO_ITEMCONCILIACIONRE_N pitemconciliacion,
                CORE_FECHACONCILIACION_D pfechaconciliacion,
                PERS_EMPRESA_N_PK pempresa,
                TESO_ITEMBANCOFONDO_N pitembancofondo,
                TESO_ITEMBANCOCUENTA_N pitembancocuenta,
                CORE_MONTOSALDOINICIAL_N pmontosaldoinicial,
                CORE_MONTODEBE_N pmontodebe,
                CORE_MONTOHABER_N pmontohaber,
                CORE_REGISTROSCONCILIADOS_N pregistrosconciliados,
                CORE_REGISTROSNOCONCILIADOS_N pregistrosnoconciliados,
                PERS_EMPRESACONCI_N_PK pempresaconci,
                PERS_PERSONACONCI_N_PK ppersonaconci,
                PERS_EMPRESACONCIANULA_N_PK pempresaconcianula,
                PERS_PERSONACONCIANULA_N_PK ppersonaconcianula,
                CORE_FECHAANULA_D pfechaanula,
                CORE_OBSERVACIONANULA_V pobservacionanula,
                PARA_ESTADO_N_COD pestado,
                CORE_FECHACONCILIA_D ptsFechaConcilia
           FROM TES_CONCILIACIONRE CONC
           WHERE CONC.TESO_ITEMCONCILIACIONRE_N =
                   (SELECT MAX (CONC2.TESO_ITEMCONCILIACIONRE_N)
                      FROM TES_CONCILIACIONRE CONC2
                     WHERE (CONC2.PERS_EMPRESACONCILIACION_N_PK =
                        v_empresaconciliacion)
                AND (CONC2.PARA_ESTADO_N_COD = V_PARA_ESTADO_N)
                AND (CONC2.TESO_ITEMBANCOFONDO_N = V_ITEMBANCOFONDO_N)
                AND (CONC2.TESO_ITEMBANCOCUENTA_N = V_ITEMBANCOCUENTA_N)        
           );

      V_LISTA := var_lista;
   END getLastConciliacionByCuenta;
   /*Fin: REQ14-006 Bizarq - 26/10/2014 */
END PKG_CONCILIACIONRE;
/

