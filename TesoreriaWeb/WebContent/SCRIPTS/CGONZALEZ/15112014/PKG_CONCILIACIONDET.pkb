CREATE OR REPLACE PACKAGE BODY TESORERIA.PKG_CONCILIACIONDET
AS
   PROCEDURE grabar (
      v_empresaconciliacion   IN OUT TES_CONCILIACIONDET.PERS_EMPRESACONCILIACION_N_PK%TYPE,
      v_itemconciliacion      IN OUT TES_CONCILIACIONDET.TESO_ITEMCONCILIACIONRE_N%TYPE,
      v_itemconciliaciondet   IN OUT TES_CONCILIACIONDET.TESO_ITEMCONCILIACIONDE_N%TYPE,
      v_empresaegreso         IN OUT TES_CONCILIACIONDET.PERS_EMPRESAEGRESO_N_PK%TYPE,
      v_itemegresogeneral     IN OUT TES_CONCILIACIONDET.TESO_ITEMEGRESOGENERAL_N%TYPE,
      v_empresaingreso        IN OUT TES_CONCILIACIONDET.PERS_EMPRESAINGRESO_N_PK%TYPE,
      v_itemingresogeneral    IN OUT TES_CONCILIACIONDET.TESO_ITEMINGRESOGENERAL_N%TYPE,
      v_indicadorcheck        IN OUT TES_CONCILIACIONDET.CODE_INDICADORCHECK_N%TYPE,
      v_indicadorconci        IN OUT TES_CONCILIACIONDET.CODE_INDICADORCONCI_N%TYPE,
      v_saldoinicial          IN OUT TES_CONCILIACIONDET.CODE_SALDOINICIAL%TYPE,
      v_montodebe             IN OUT TES_CONCILIACIONDET.CODE_MONTODEBE_N%TYPE,
      v_montohaber            IN OUT TES_CONCILIACIONDET.CODE_MONTOHABER_N%TYPE,
      v_empresacheckcon       IN OUT TES_CONCILIACIONDET.PERS_EMPRESACHECKCON_N_PK%TYPE,
      v_personacheckcon       IN OUT TES_CONCILIACIONDET.PERS_PERSONACHECKCON_N_PK%TYPE,
      v_fechacheck            IN OUT TES_CONCILIACIONDET.CODE_FECHACHECK_D%TYPE,
      v_numerooperacion       IN OUT TES_CONCILIACIONDET.CODE_NUMEROOPERACION_N%TYPE,
      v_sucursalgira          IN OUT TES_CONCILIACIONDET.SUCU_IDSUCURSALGIR_N%TYPE,
      v_subsucursalgira       IN OUT TES_CONCILIACIONDET.SUDE_IDSUBSUCURSALGIR_N%TYPE,
      v_sucursalpaga          IN OUT TES_CONCILIACIONDET.SUCU_IDSUCURSALPAG_N%TYPE,
      v_subsucursalpaga       IN OUT TES_CONCILIACIONDET.SUDE_IDSUBSUCURSALPAG_N%TYPE)
   IS
   BEGIN
      SELECT SEQ_CONCILIACIONDET.NEXTVAL INTO v_itemconciliaciondet FROM DUAL;

      INSERT
        INTO TES_CONCILIACIONDET (
                TES_CONCILIACIONDET.PERS_EMPRESACONCILIACION_N_PK,
                TES_CONCILIACIONDET.TESO_ITEMCONCILIACIONRE_N,
                TES_CONCILIACIONDET.TESO_ITEMCONCILIACIONDE_N,
                TES_CONCILIACIONDET.PERS_EMPRESAEGRESO_N_PK,
                TES_CONCILIACIONDET.TESO_ITEMEGRESOGENERAL_N,
                TES_CONCILIACIONDET.PERS_EMPRESAINGRESO_N_PK,
                TES_CONCILIACIONDET.TESO_ITEMINGRESOGENERAL_N,
                TES_CONCILIACIONDET.CODE_INDICADORCHECK_N,
                TES_CONCILIACIONDET.CODE_INDICADORCONCI_N,
                TES_CONCILIACIONDET.CODE_SALDOINICIAL,
                TES_CONCILIACIONDET.CODE_MONTODEBE_N,
                TES_CONCILIACIONDET.CODE_MONTOHABER_N,
                TES_CONCILIACIONDET.PERS_EMPRESACHECKCON_N_PK,
                TES_CONCILIACIONDET.PERS_PERSONACHECKCON_N_PK,
                TES_CONCILIACIONDET.CODE_FECHACHECK_D,
                TES_CONCILIACIONDET.CODE_NUMEROOPERACION_N,
                TES_CONCILIACIONDET.SUCU_IDSUCURSALGIR_N,
                TES_CONCILIACIONDET.SUDE_IDSUBSUCURSALGIR_N,
                TES_CONCILIACIONDET.SUCU_IDSUCURSALPAG_N,
                TES_CONCILIACIONDET.SUDE_IDSUBSUCURSALPAG_N)
      VALUES (v_empresaconciliacion,
              v_itemconciliacion,
              v_itemconciliaciondet,
              v_empresaegreso,
              v_itemegresogeneral,
              v_empresaingreso,
              v_itemingresogeneral,
              v_indicadorcheck,
              v_indicadorconci,
              v_saldoinicial,
              v_montodebe,
              v_montohaber,
              v_empresacheckcon,
              v_personacheckcon,
              v_fechacheck,
              v_numerooperacion,
              v_sucursalgira,
              v_subsucursalgira,
              v_sucursalpaga,
              v_subsucursalpaga);
   END grabar;



   PROCEDURE modificar (
      v_empresaconciliacion   IN OUT TES_CONCILIACIONDET.PERS_EMPRESACONCILIACION_N_PK%TYPE,
      v_itemconciliacion      IN OUT TES_CONCILIACIONDET.TESO_ITEMCONCILIACIONRE_N%TYPE,
      v_itemconciliaciondet   IN OUT TES_CONCILIACIONDET.TESO_ITEMCONCILIACIONDE_N%TYPE,
      v_empresaegreso         IN OUT TES_CONCILIACIONDET.PERS_EMPRESAEGRESO_N_PK%TYPE,
      v_itemegresogeneral     IN OUT TES_CONCILIACIONDET.TESO_ITEMEGRESOGENERAL_N%TYPE,
      v_empresaingreso        IN OUT TES_CONCILIACIONDET.PERS_EMPRESAINGRESO_N_PK%TYPE,
      v_itemingresogeneral    IN OUT TES_CONCILIACIONDET.TESO_ITEMINGRESOGENERAL_N%TYPE,
      v_indicadorcheck        IN OUT TES_CONCILIACIONDET.CODE_INDICADORCHECK_N%TYPE,
      v_indicadorconci        IN OUT TES_CONCILIACIONDET.CODE_INDICADORCONCI_N%TYPE,
      v_saldoinicial          IN OUT TES_CONCILIACIONDET.CODE_SALDOINICIAL%TYPE,
      v_montodebe             IN OUT TES_CONCILIACIONDET.CODE_MONTODEBE_N%TYPE,
      v_montohaber            IN OUT TES_CONCILIACIONDET.CODE_MONTOHABER_N%TYPE,
      v_empresacheckcon       IN OUT TES_CONCILIACIONDET.PERS_EMPRESACHECKCON_N_PK%TYPE,
      v_personacheckcon       IN OUT TES_CONCILIACIONDET.PERS_PERSONACHECKCON_N_PK%TYPE,
      v_fechacheck            IN OUT TES_CONCILIACIONDET.CODE_FECHACHECK_D%TYPE,
      v_numerooperacion       IN OUT TES_CONCILIACIONDET.CODE_NUMEROOPERACION_N%TYPE,
      v_sucursalgira          IN OUT TES_CONCILIACIONDET.SUCU_IDSUCURSALGIR_N%TYPE,
      v_subsucursalgira       IN OUT TES_CONCILIACIONDET.SUDE_IDSUBSUCURSALGIR_N%TYPE,
      v_sucursalpaga          IN OUT TES_CONCILIACIONDET.SUCU_IDSUCURSALPAG_N%TYPE,
      v_subsucursalpaga       IN OUT TES_CONCILIACIONDET.SUDE_IDSUBSUCURSALPAG_N%TYPE)
   IS
   BEGIN
      UPDATE TES_CONCILIACIONDET
         SET --TES_CONCILIACIONDET.PERS_EMPRESACONCILIACION_N_PK = v_empresaconciliacion,
         --TES_CONCILIACIONDET.TESO_ITEMCONCILIACIONRE_N = v_itemconciliacion,
             TES_CONCILIACIONDET.TESO_ITEMCONCILIACIONDE_N =
                v_itemconciliaciondet,
             TES_CONCILIACIONDET.PERS_EMPRESAEGRESO_N_PK = v_empresaegreso,
             TES_CONCILIACIONDET.TESO_ITEMEGRESOGENERAL_N =
                v_itemingresogeneral,
             TES_CONCILIACIONDET.PERS_EMPRESAINGRESO_N_PK = v_empresaingreso,
             TES_CONCILIACIONDET.TESO_ITEMINGRESOGENERAL_N =
                v_itemingresogeneral,
             TES_CONCILIACIONDET.CODE_INDICADORCHECK_N = v_indicadorcheck,
             TES_CONCILIACIONDET.CODE_INDICADORCONCI_N = v_indicadorconci,
             TES_CONCILIACIONDET.CODE_SALDOINICIAL = v_saldoinicial,
             TES_CONCILIACIONDET.CODE_MONTODEBE_N = v_montodebe,
             TES_CONCILIACIONDET.CODE_MONTOHABER_N = v_montohaber,
             TES_CONCILIACIONDET.PERS_EMPRESACHECKCON_N_PK = v_empresacheckcon,
             TES_CONCILIACIONDET.PERS_PERSONACHECKCON_N_PK = v_personacheckcon,
             TES_CONCILIACIONDET.CODE_FECHACHECK_D = v_fechacheck,
             TES_CONCILIACIONDET.CODE_NUMEROOPERACION_N = v_numerooperacion,
             TES_CONCILIACIONDET.SUCU_IDSUCURSALGIR_N = v_sucursalgira,
             TES_CONCILIACIONDET.SUDE_IDSUBSUCURSALGIR_N = v_subsucursalgira,
             TES_CONCILIACIONDET.SUCU_IDSUCURSALPAG_N = v_sucursalpaga,
             TES_CONCILIACIONDET.SUDE_IDSUBSUCURSALPAG_N = v_subsucursalpaga
       WHERE TES_CONCILIACIONDET.PERS_EMPRESACONCILIACION_N_PK =
                v_empresaconciliacion
             AND TES_CONCILIACIONDET.TESO_ITEMCONCILIACIONRE_N =
                    v_itemconciliacion
             AND TES_CONCILIACIONDET.TESO_ITEMCONCILIACIONDE_N =
                    v_itemconciliaciondet;
   END modificar;



   PROCEDURE getListaPorPk (
      V_LISTA                    OUT cursorLista,
      v_empresaconciliacion   IN     TES_CONCILIACIONDET.PERS_EMPRESACONCILIACION_N_PK%TYPE,
      v_itemconciliacion      IN     TES_CONCILIACIONDET.TESO_ITEMCONCILIACIONRE_N%TYPE,
      v_itemconciliaciondet   IN OUT TES_CONCILIACIONDET.TESO_ITEMCONCILIACIONDE_N%TYPE)
   IS
      var_lista   cursorLista;
   BEGIN
      OPEN var_lista FOR
         SELECT TES_CONCILIACIONDET.PERS_EMPRESACONCILIACION_N_PK
                   pempresaconciliacion,
                TES_CONCILIACIONDET.TESO_ITEMCONCILIACIONRE_N
                   pitemconciliacion,
                TES_CONCILIACIONDET.TESO_ITEMCONCILIACIONDE_N
                   pitemconciliaciondet,
                TES_CONCILIACIONDET.PERS_EMPRESAEGRESO_N_PK pempresaegreso,
                TES_CONCILIACIONDET.TESO_ITEMEGRESOGENERAL_N
                   pitemegresogeneral,
                TES_CONCILIACIONDET.PERS_EMPRESAINGRESO_N_PK pempresaingreso,
                TES_CONCILIACIONDET.TESO_ITEMINGRESOGENERAL_N
                   pitemingresogeneral,
                TES_CONCILIACIONDET.CODE_INDICADORCHECK_N pindicadorcheck,
                TES_CONCILIACIONDET.CODE_INDICADORCONCI_N pindicadorconci,
                TES_CONCILIACIONDET.CODE_SALDOINICIAL psaldoinicial,
                TES_CONCILIACIONDET.CODE_MONTODEBE_N pmontodebe,
                TES_CONCILIACIONDET.CODE_MONTOHABER_N pmontohaber,
                TES_CONCILIACIONDET.PERS_EMPRESACHECKCON_N_PK
                   pempresacheckcon,
                TES_CONCILIACIONDET.PERS_PERSONACHECKCON_N_PK
                   ppersonacheckcon,
                TES_CONCILIACIONDET.CODE_FECHACHECK_D pfechacheck,
                TES_CONCILIACIONDET.CODE_NUMEROOPERACION_N pnumerooperacion,
                TES_CONCILIACIONDET.SUCU_IDSUCURSALGIR_N
                   pintSucuIdSucursalGira,
                TES_CONCILIACIONDET.SUDE_IDSUBSUCURSALGIR_N
                   pintSubSucuIdSucursalGira,
                TES_CONCILIACIONDET.SUCU_IDSUCURSALPAG_N
                   pintSucuIdSucursalPaga,
                TES_CONCILIACIONDET.SUDE_IDSUBSUCURSALPAG_N
                   pintSubSucuIdSucursalPaga
           FROM TES_CONCILIACIONDET
          WHERE TES_CONCILIACIONDET.PERS_EMPRESACONCILIACION_N_PK =
                   v_empresaconciliacion
                AND TES_CONCILIACIONDET.TESO_ITEMCONCILIACIONRE_N =
                       v_itemconciliacion
                AND v_itemconciliaciondet is null or TES_CONCILIACIONDET.TESO_ITEMCONCILIACIONDE_N =
                       v_itemconciliaciondet
                       ORDER BY 1,2,3;

      V_LISTA := var_lista;
   END getListaPorPk;
END PKG_CONCILIACIONDET;
/

