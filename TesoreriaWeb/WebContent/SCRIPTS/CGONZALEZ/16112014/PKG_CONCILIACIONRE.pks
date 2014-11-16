CREATE OR REPLACE PACKAGE TESORERIA.PKG_CONCILIACIONRE
AS
   TYPE cursorLista IS REF CURSOR;

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
      v_fechaconcilia            IN OUT TES_CONCILIACIONRE.CORE_FECHACONCILIA_D%TYPE);


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
      v_fechaconcilia            IN OUT TES_CONCILIACIONRE.CORE_FECHACONCILIA_D%TYPE);

   PROCEDURE getListaPorPk (
      V_LISTA                    OUT cursorLista,
      v_empresaconciliacion   IN     TES_CONCILIACIONRE.PERS_EMPRESACONCILIACION_N_PK%TYPE,
      v_itemconciliacion      IN     TES_CONCILIACIONRE.TESO_ITEMCONCILIACIONRE_N%TYPE);

   PROCEDURE getListFilter (V_LISTA                     OUT cursorLista,
                            dtBusqFechaDesde         IN     DATE,
                            dtBusqFechaHasta         IN     DATE,
                            intBusqPersEmpresa       IN     NUMERIC,
                            intBusqItemBancoFondo    IN     NUMERIC,
                            intBusqItemBancoCuenta   IN     NUMERIC);

   /*Inicio: REQ14-006 Bizarq - 26/10/2014 */
   PROCEDURE getLastConciliacionByCuenta (
      V_LISTA                    OUT cursorLista,
      v_empresaconciliacion   IN     TES_CONCILIACIONRE.PERS_EMPRESACONCILIACION_N_PK%TYPE,
      V_PARA_ESTADO_N         IN     TES_CONCILIACIONRE.PARA_ESTADO_N_COD%TYPE,
      V_ITEMBANCOFONDO_N      IN     TES_CONCILIACIONRE.TESO_ITEMBANCOFONDO_N%TYPE,
      V_ITEMBANCOCUENTA_N     IN     TES_CONCILIACIONRE.TESO_ITEMBANCOCUENTA_N%TYPE);
/*Fin: REQ14-006 Bizarq - 26/10/2014 */
END PKG_CONCILIACIONRE;
/

