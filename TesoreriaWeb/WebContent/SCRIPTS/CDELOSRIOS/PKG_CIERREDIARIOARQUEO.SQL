CREATE OR REPLACE PACKAGE BODY TESORERIA.PKG_CIERREDIARIOARQUEO
AS
   PROCEDURE grabar (
      v_empresaarqueo            IN OUT TES_CIERREDIARIOARQUEO.PERS_EMPRESAARQUEO_N_PK%TYPE,
      v_tipoarqueo               IN OUT TES_CIERREDIARIOARQUEO.PARA_TIPOARQUEO_N_COD%TYPE,
      v_idsucursal               IN OUT TES_CIERREDIARIOARQUEO.SUCU_IDSUCURSAL_N%TYPE,
      v_idsubsucursal            IN OUT TES_CIERREDIARIOARQUEO.SUDE_IDSUBSUCURSAL_N%TYPE,
      v_itemcierrediarioarqueo   IN OUT TES_CIERREDIARIOARQUEO.TESO_ITEMCIDIAR_N%TYPE,
      v_fechacierrearqueo        IN OUT TES_CIERREDIARIOARQUEO.TESO_FECHACIERREARQUEO_D%TYPE,
      v_empresaresponsable       IN OUT TES_CIERREDIARIOARQUEO.PERS_EMPRESARESPONSABLE_N_PK%TYPE,
      v_personaresponsable       IN OUT TES_CIERREDIARIOARQUEO.PERS_PERSONARESPONSABLE_N_PK%TYPE,
      v_observacion              IN OUT TES_CIERREDIARIOARQUEO.CIDA_OBSERVACION_V%TYPE,
      v_fecharegistro            IN OUT TES_CIERREDIARIOARQUEO.CIDA_FECHAREGISTRO_D%TYPE,
      v_estadocierre             IN OUT TES_CIERREDIARIOARQUEO.PARA_ESTADOCIERRE_N_COD%TYPE,
      v_montototalefectivo       IN OUT TES_CIERREDIARIOARQUEO.CIDA_MONTOTOTALEFECTIVO_N%TYPE,
      v_montototalfondo          IN OUT TES_CIERREDIARIOARQUEO.CIDA_MONTOTOTALFONDO_N%TYPE,
      v_montodiferencia          IN OUT TES_CIERREDIARIOARQUEO.CIDA_MONTODIFERENCIA_N%TYPE,
      v_empresacierrean          IN OUT TES_CIERREDIARIOARQUEO.PERS_EMPRESACIERREAN_N_PK%TYPE,
      v_personacierrean          IN OUT TES_CIERREDIARIOARQUEO.PERS_PERSONACIERREAN_N_PK%TYPE,
      v_observacionanula         IN OUT TES_CIERREDIARIOARQUEO.CIDA_OBSERVACIONANULA_V%TYPE,
      v_fechacierreanula         IN OUT TES_CIERREDIARIOARQUEO.CIDA_FECHACIERREANULA_D%TYPE)
   IS
   BEGIN
      SELECT SEQ_CIERREDIARIOARQUEO.NEXTVAL
        INTO v_itemcierrediarioarqueo
        FROM DUAL;

      INSERT INTO TES_CIERREDIARIOARQUEO (PERS_EMPRESAARQUEO_N_PK,
                                          PARA_TIPOARQUEO_N_COD,
                                          SUCU_IDSUCURSAL_N,
                                          SUDE_IDSUBSUCURSAL_N,
                                          TESO_ITEMCIDIAR_N,
                                          TESO_FECHACIERREARQUEO_D,
                                          PERS_EMPRESARESPONSABLE_N_PK,
                                          PERS_PERSONARESPONSABLE_N_PK,
                                          CIDA_OBSERVACION_V,
                                          CIDA_FECHAREGISTRO_D,
                                          PARA_ESTADOCIERRE_N_COD,
                                          CIDA_MONTOTOTALEFECTIVO_N,
                                          CIDA_MONTOTOTALFONDO_N,
                                          CIDA_MONTODIFERENCIA_N,
                                          PERS_EMPRESACIERREAN_N_PK,
                                          PERS_PERSONACIERREAN_N_PK,
                                          CIDA_OBSERVACIONANULA_V,
                                          CIDA_FECHACIERREANULA_D)
           VALUES (v_empresaarqueo,
                   v_tipoarqueo,
                   v_idsucursal,
                   v_idsubsucursal,
                   v_itemcierrediarioarqueo,
                   v_fechacierrearqueo,
                   v_empresaresponsable,
                   v_personaresponsable,
                   v_observacion,
                   v_fecharegistro,
                   v_estadocierre,
                   v_montototalefectivo,
                   v_montototalfondo,
                   v_montodiferencia,
                   v_empresacierrean,
                   v_personacierrean,
                   v_observacionanula,
                   v_fechacierreanula);
   END grabar;



   PROCEDURE modificar (
      v_empresaarqueo            IN OUT TES_CIERREDIARIOARQUEO.PERS_EMPRESAARQUEO_N_PK%TYPE,
      v_tipoarqueo               IN OUT TES_CIERREDIARIOARQUEO.PARA_TIPOARQUEO_N_COD%TYPE,
      v_idsucursal               IN OUT TES_CIERREDIARIOARQUEO.SUCU_IDSUCURSAL_N%TYPE,
      v_idsubsucursal            IN OUT TES_CIERREDIARIOARQUEO.SUDE_IDSUBSUCURSAL_N%TYPE,
      v_itemcierrediarioarqueo   IN OUT TES_CIERREDIARIOARQUEO.TESO_ITEMCIDIAR_N%TYPE,
      v_fechacierrearqueo        IN OUT TES_CIERREDIARIOARQUEO.TESO_FECHACIERREARQUEO_D%TYPE,
      v_empresaresponsable       IN OUT TES_CIERREDIARIOARQUEO.PERS_EMPRESARESPONSABLE_N_PK%TYPE,
      v_personaresponsable       IN OUT TES_CIERREDIARIOARQUEO.PERS_PERSONARESPONSABLE_N_PK%TYPE,
      v_observacion              IN OUT TES_CIERREDIARIOARQUEO.CIDA_OBSERVACION_V%TYPE,
      v_fecharegistro            IN OUT TES_CIERREDIARIOARQUEO.CIDA_FECHAREGISTRO_D%TYPE,
      v_estadocierre             IN OUT TES_CIERREDIARIOARQUEO.PARA_ESTADOCIERRE_N_COD%TYPE,
      v_montototalefectivo       IN OUT TES_CIERREDIARIOARQUEO.CIDA_MONTOTOTALEFECTIVO_N%TYPE,
      v_montototalfondo          IN OUT TES_CIERREDIARIOARQUEO.CIDA_MONTOTOTALFONDO_N%TYPE,
      v_montodiferencia          IN OUT TES_CIERREDIARIOARQUEO.CIDA_MONTODIFERENCIA_N%TYPE,
      v_empresacierrean          IN OUT TES_CIERREDIARIOARQUEO.PERS_EMPRESACIERREAN_N_PK%TYPE,
      v_personacierrean          IN OUT TES_CIERREDIARIOARQUEO.PERS_PERSONACIERREAN_N_PK%TYPE,
      v_observacionanula         IN OUT TES_CIERREDIARIOARQUEO.CIDA_OBSERVACIONANULA_V%TYPE,
      v_fechacierreanula         IN OUT TES_CIERREDIARIOARQUEO.CIDA_FECHACIERREANULA_D%TYPE)
   IS
   BEGIN
      UPDATE TES_CIERREDIARIOARQUEO
         SET TESO_FECHACIERREARQUEO_D = v_fechacierrearqueo,
             PERS_EMPRESARESPONSABLE_N_PK = v_empresaresponsable,
             PERS_PERSONARESPONSABLE_N_PK = v_personaresponsable,
             CIDA_OBSERVACION_V = v_observacion,
             CIDA_FECHAREGISTRO_D = v_fecharegistro,
             PARA_ESTADOCIERRE_N_COD = v_estadocierre,
             CIDA_MONTOTOTALEFECTIVO_N = v_montototalefectivo,
             CIDA_MONTOTOTALFONDO_N = v_montototalfondo,
             CIDA_MONTODIFERENCIA_N = v_montodiferencia,
             PERS_EMPRESACIERREAN_N_PK = v_empresacierrean,
             PERS_PERSONACIERREAN_N_PK = v_personacierrean,
             CIDA_OBSERVACIONANULA_V = v_observacionanula,
             CIDA_FECHACIERREANULA_D = v_fechacierreanula
       WHERE     PERS_EMPRESAARQUEO_N_PK = v_empresaarqueo
             AND PARA_TIPOARQUEO_N_COD = v_tipoarqueo
             AND SUCU_IDSUCURSAL_N = v_idsucursal
             AND SUDE_IDSUBSUCURSAL_N = v_idsubsucursal
             AND TESO_ITEMCIDIAR_N = v_itemcierrediarioarqueo;
   END modificar;


   PROCEDURE getListaPorPk (
      V_LISTA                       OUT cursorLista,
      v_empresaarqueo            IN     TES_CIERREDIARIOARQUEO.PERS_EMPRESAARQUEO_N_PK%TYPE,
      v_tipoarqueo               IN     TES_CIERREDIARIOARQUEO.PARA_TIPOARQUEO_N_COD%TYPE,
      v_idsucursal               IN     TES_CIERREDIARIOARQUEO.SUCU_IDSUCURSAL_N%TYPE,
      v_idsubsucursal            IN     TES_CIERREDIARIOARQUEO.SUDE_IDSUBSUCURSAL_N%TYPE,
      v_itemcierrediarioarqueo   IN     TES_CIERREDIARIOARQUEO.TESO_ITEMCIDIAR_N%TYPE)
   IS
      var_lista   cursorLista;
   BEGIN
      OPEN var_lista FOR
         SELECT PERS_EMPRESAARQUEO_N_PK pempresaarqueo,
                PARA_TIPOARQUEO_N_COD ptipoarqueo,
                SUCU_IDSUCURSAL_N pidsucursal,
                SUDE_IDSUBSUCURSAL_N pidsubsucursal,
                TESO_ITEMCIDIAR_N pitemcidiar,
                TESO_FECHACIERREARQUEO_D pFechaCierreArqueo,
                PERS_EMPRESARESPONSABLE_N_PK pempresaresponsable,
                PERS_PERSONARESPONSABLE_N_PK ppersonaresponsable,
                CIDA_OBSERVACION_V pobservacion,
                CIDA_FECHAREGISTRO_D pfecharegistro,
                PARA_ESTADOCIERRE_N_COD pestadocierre,
                CIDA_MONTOTOTALEFECTIVO_N pmontototalefectivo,
                CIDA_MONTOTOTALFONDO_N pmontototalfondo,
                CIDA_MONTODIFERENCIA_N pmontodiferencia,
                PERS_EMPRESACIERREAN_N_PK pempresacierrean,
                PERS_PERSONACIERREAN_N_PK ppersonacierrean,
                CIDA_OBSERVACIONANULA_V pobservacionanula,
                CIDA_FECHACIERREANULA_D pfechacierreanula
           FROM TES_CIERREDIARIOARQUEO
          WHERE     PERS_EMPRESAARQUEO_N_PK = v_empresaarqueo
                AND PARA_TIPOARQUEO_N_COD = v_tipoarqueo
                AND SUCU_IDSUCURSAL_N = v_idsucursal
                AND SUDE_IDSUBSUCURSAL_N = v_idsubsucursal
                AND TESO_ITEMCIDIAR_N = v_itemcierrediarioarqueo;

      V_LISTA := var_lista;
   END getListaPorPk;


   PROCEDURE getListaPorBuscar (
      V_LISTA                  OUT cursorLista,
      v_empresaarqueo       IN     TES_CIERREDIARIOARQUEO.PERS_EMPRESAARQUEO_N_PK%TYPE,
      v_tipoarqueo          IN     TES_CIERREDIARIOARQUEO.PARA_TIPOARQUEO_N_COD%TYPE,
      v_idsucursal          IN     TES_CIERREDIARIOARQUEO.SUCU_IDSUCURSAL_N%TYPE,
      v_idsubsucursal       IN     TES_CIERREDIARIOARQUEO.SUDE_IDSUBSUCURSAL_N%TYPE,
      v_fechacierrearqueo   IN     TES_CIERREDIARIOARQUEO.TESO_FECHACIERREARQUEO_D%TYPE,
      v_estadocierre        IN     TES_CIERREDIARIOARQUEO.PARA_ESTADOCIERRE_N_COD%TYPE)
   IS
      var_lista   cursorLista;
   BEGIN
      OPEN var_lista FOR
           SELECT PERS_EMPRESAARQUEO_N_PK pempresaarqueo,
                  PARA_TIPOARQUEO_N_COD ptipoarqueo,
                  SUCU_IDSUCURSAL_N pidsucursal,
                  SUDE_IDSUBSUCURSAL_N pidsubsucursal,
                  TESO_ITEMCIDIAR_N pitemcidiar,
                  TESO_FECHACIERREARQUEO_D pFechaCierreArqueo,
                  PERS_EMPRESARESPONSABLE_N_PK pempresaresponsable,
                  PERS_PERSONARESPONSABLE_N_PK ppersonaresponsable,
                  CIDA_OBSERVACION_V pobservacion,
                  CIDA_FECHAREGISTRO_D pfecharegistro,
                  PARA_ESTADOCIERRE_N_COD pestadocierre,
                  CIDA_MONTOTOTALEFECTIVO_N pmontototalefectivo,
                  CIDA_MONTOTOTALFONDO_N pmontototalfondo,
                  CIDA_MONTODIFERENCIA_N pmontodiferencia,
                  PERS_EMPRESACIERREAN_N_PK pempresacierrean,
                  PERS_PERSONACIERREAN_N_PK ppersonacierrean,
                  CIDA_OBSERVACIONANULA_V pobservacionanula,
                  CIDA_FECHACIERREANULA_D pfechacierreanula
             FROM TES_CIERREDIARIOARQUEO
            WHERE PERS_EMPRESAARQUEO_N_PK = v_empresaarqueo
                  AND (v_tipoarqueo IS NULL
                       OR PARA_TIPOARQUEO_N_COD = v_tipoarqueo)
                  AND (v_idsucursal IS NULL OR SUCU_IDSUCURSAL_N = v_idsucursal)
                  AND (v_idsubsucursal IS NULL
                       OR SUDE_IDSUBSUCURSAL_N = v_idsubsucursal)
                  AND (v_fechacierrearqueo IS NULL
                       OR (TRUNC (TESO_FECHACIERREARQUEO_D) =
                              TRUNC (v_fechacierrearqueo)))
                  AND (v_estadocierre IS NULL
                       OR PARA_ESTADOCIERRE_N_COD = v_estadocierre)
         --Agregado 16.12.2013 JCHAVEZ
         ORDER BY TESO_FECHACIERREARQUEO_D DESC;

      V_LISTA := var_lista;
   END getListaPorBuscar;


   PROCEDURE getListaParaDiaAnterior (
      V_LISTA              OUT cursorLista,
      v_empresaarqueo   IN     TES_CIERREDIARIOARQUEO.PERS_EMPRESAARQUEO_N_PK%TYPE,
      v_tipoarqueo      IN     TES_CIERREDIARIOARQUEO.PARA_TIPOARQUEO_N_COD%TYPE,
      v_idsucursal      IN     TES_CIERREDIARIOARQUEO.SUCU_IDSUCURSAL_N%TYPE,
      v_idsubsucursal   IN     TES_CIERREDIARIOARQUEO.SUDE_IDSUBSUCURSAL_N%TYPE)
   IS
      var_lista   cursorLista;
   BEGIN
      OPEN var_lista FOR
           SELECT PERS_EMPRESAARQUEO_N_PK pempresaarqueo,
                  PARA_TIPOARQUEO_N_COD ptipoarqueo,
                  SUCU_IDSUCURSAL_N pidsucursal,
                  SUDE_IDSUBSUCURSAL_N pidsubsucursal,
                  TESO_ITEMCIDIAR_N pitemcidiar,
                  TESO_FECHACIERREARQUEO_D pFechaCierreArqueo,
                  PERS_EMPRESARESPONSABLE_N_PK pempresaresponsable,
                  PERS_PERSONARESPONSABLE_N_PK ppersonaresponsable,
                  CIDA_OBSERVACION_V pobservacion,
                  CIDA_FECHAREGISTRO_D pfecharegistro,
                  PARA_ESTADOCIERRE_N_COD pestadocierre,
                  CIDA_MONTOTOTALEFECTIVO_N pmontototalefectivo,
                  CIDA_MONTOTOTALFONDO_N pmontototalfondo,
                  CIDA_MONTODIFERENCIA_N pmontodiferencia,
                  PERS_EMPRESACIERREAN_N_PK pempresacierrean,
                  PERS_PERSONACIERREAN_N_PK ppersonacierrean,
                  CIDA_OBSERVACIONANULA_V pobservacionanula,
                  CIDA_FECHACIERREANULA_D pfechacierreanula
             FROM TES_CIERREDIARIOARQUEO
            WHERE     PERS_EMPRESAARQUEO_N_PK = v_empresaarqueo
                  AND PARA_TIPOARQUEO_N_COD = v_tipoarqueo
                  AND SUCU_IDSUCURSAL_N = v_idsucursal
                  AND SUDE_IDSUBSUCURSAL_N = v_idsubsucursal
                  AND PARA_ESTADOCIERRE_N_COD = 1
                  AND ROWNUM <= 1
         ORDER BY TESO_ITEMCIDIAR_N DESC;

      V_LISTA := var_lista;
   END getListaParaDiaAnterior;


   PROCEDURE getListaSaldoFechas (
      V_LISTA              OUT cursorLista,
      v_empresasaldos   IN     TES_SALDOS.PERS_EMPRESASALDOS_N_PK%TYPE)
   IS
      var_lista   cursorLista;
   BEGIN
      OPEN var_lista FOR
         --Inicio: REQ14-005 - bizarq - 19/10/2014
         /*Select
              SUDE_IDSUBSUCURSAL_N,
              max(TESO_FECHACIERREARQUEO_D) as max_fecha
         from
              TES_CIERREDIARIOARQUEO
         group by SUDE_IDSUBSUCURSAL_N;*/
         SELECT MAX (TESO_FECHACIERREARQUEO_D) AS max_fecha
           FROM TES_CIERREDIARIOARQUEO
          WHERE PERS_EMPRESAARQUEO_N_PK = v_empresasaldos 
          /*Inicio: REQ14-006 - bizarq - 19/10/2014*/
            AND (PARA_ESTADOCIERRE_N_COD = PKG_CONSTANTE.C_ESTADO_ACTIVO);
      	  /*Fin: REQ14-006 - bizarq - 19/10/2014*/
      --Inicio: REQ14-005 - bizarq - 19/10/2014
      V_LISTA := var_lista;
   END getListaSaldoFechas;
END PKG_CIERREDIARIOARQUEO;
/
