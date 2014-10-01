/* Formatted on 30/09/2014 10:05:52 p.m. (QP5 v5.163.1008.3004) */
CREATE OR REPLACE PACKAGE BODY CONTABILIDAD.PKG_LIBROMAYOR
AS
   PROCEDURE grabar (
      v_pers_empresamayor_n_pk     IN OUT CON_LIBROMAYOR.PERS_EMPRESAMAYOR_N_PK%TYPE,
      v_cont_periodomayor_n        IN OUT CON_LIBROMAYOR.CONT_PERIODOMAYOR_N%TYPE,
      v_cont_mesmayor_n            IN OUT CON_LIBROMAYOR.CONT_MESMAYOR_N%TYPE,
      v_lima_fecharegistro_d       IN OUT CON_LIBROMAYOR.LIMA_FECHAREGISTRO_D%TYPE,
      v_para_estadocierre_n_cod    IN OUT CON_LIBROMAYOR.PARA_ESTADOCIERRE_N_COD%TYPE,
      v_pers_empresausuario_n_pk   IN OUT CON_LIBROMAYOR.PERS_EMPRESAUSUARIO_N_PK%TYPE,
      V_pers_personausuario_n_pk   IN OUT CON_LIBROMAYOR.PERS_PERSONAUSUARIO_N_PK%TYPE,
      V_PARA_ESTADO_N_COD          IN OUT CON_LIBROMAYOR.PARA_ESTADO_N_COD%TYPE)
   IS
   BEGIN
      INSERT INTO CON_LIBROMAYOR (PERS_EMPRESAMAYOR_N_PK,
                                  CONT_PERIODOMAYOR_N,
                                  CONT_MESMAYOR_N,
                                  LIMA_FECHAREGISTRO_D,
                                  PARA_ESTADOCIERRE_N_COD,
                                  PERS_EMPRESAUSUARIO_N_PK,
                                  PERS_PERSONAUSUARIO_N_PK,
                                  PARA_ESTADO_N_COD)
           VALUES (v_pers_empresamayor_n_pk,
                   v_cont_periodomayor_n,
                   v_cont_mesmayor_n,
                   v_lima_fecharegistro_d,
                   v_para_estadocierre_n_cod,
                   v_pers_empresausuario_n_pk,
                   V_pers_personausuario_n_pk,
                   V_PARA_ESTADO_N_COD);
   END grabar;



   PROCEDURE modificar (
      v_pers_empresamayor_n_pk     IN OUT CON_LIBROMAYOR.PERS_EMPRESAMAYOR_N_PK%TYPE,
      v_cont_periodomayor_n        IN OUT CON_LIBROMAYOR.CONT_PERIODOMAYOR_N%TYPE,
      v_cont_mesmayor_n            IN OUT CON_LIBROMAYOR.CONT_MESMAYOR_N%TYPE,
      v_lima_fecharegistro_d       IN OUT CON_LIBROMAYOR.LIMA_FECHAREGISTRO_D%TYPE,
      v_para_estadocierre_n_cod    IN OUT CON_LIBROMAYOR.PARA_ESTADOCIERRE_N_COD%TYPE,
      v_pers_empresausuario_n_pk   IN OUT CON_LIBROMAYOR.PERS_EMPRESAUSUARIO_N_PK%TYPE,
      V_pers_personausuario_n_pk   IN OUT CON_LIBROMAYOR.PERS_PERSONAUSUARIO_N_PK%TYPE,
      V_PARA_ESTADO_N_COD          IN OUT CON_LIBROMAYOR.PARA_ESTADO_N_COD%TYPE)
   IS
   BEGIN
      UPDATE CON_LIBROMAYOR
         SET LIMA_FECHAREGISTRO_D = v_lima_fecharegistro_d,
             PARA_ESTADOCIERRE_N_COD = v_para_estadocierre_n_cod,
             PERS_EMPRESAUSUARIO_N_PK = v_pers_empresausuario_n_pk,
             PERS_PERSONAUSUARIO_N_PK = V_pers_personausuario_n_pk,
             PARA_ESTADO_N_COD = V_PARA_ESTADO_N_COD
       WHERE     PERS_EMPRESAMAYOR_N_PK = v_pers_empresamayor_n_pk
             AND CONT_PERIODOMAYOR_N = v_cont_periodomayor_n
             AND CONT_MESMAYOR_N = v_cont_mesmayor_n;
   END modificar;


   PROCEDURE getListaPorPk (
      V_LISTA                       OUT cursorLista,
      v_pers_empresamayor_n_pk   IN     CON_LIBROMAYOR.PERS_EMPRESAMAYOR_N_PK%TYPE,
      v_cont_periodomayor_n      IN     CON_LIBROMAYOR.CONT_PERIODOMAYOR_N%TYPE,
      v_cont_mesmayor_n          IN     CON_LIBROMAYOR.CONT_MESMAYOR_N%TYPE)
   IS
      var_lista   cursorLista;
   BEGIN
      OPEN var_lista FOR
         SELECT PERS_EMPRESAMAYOR_N_PK pPersEmpresaMayor,
                CONT_PERIODOMAYOR_N pContPeriodoMayor,
                CONT_MESMAYOR_N pContMesMayor,
                LIMA_FECHAREGISTRO_D pLimaFechaRegistro,
                PARA_ESTADOCIERRE_N_COD pParaEstadoCierre,
                PERS_EMPRESAUSUARIO_N_PK pPersEmpresaUsuario,
                PERS_PERSONAUSUARIO_N_PK pPersPersonaUsuario,
                PARA_ESTADO_N_COD pParaEstadoCod,
                NULL pTablaDescripcion
           FROM CON_LIBROMAYOR
          WHERE     PERS_EMPRESAMAYOR_N_PK = v_pers_empresamayor_n_pk
                AND CONT_PERIODOMAYOR_N = v_cont_periodomayor_n
                AND CONT_MESMAYOR_N = v_cont_mesmayor_n;

      V_LISTA := var_lista;
   END getListaPorPk;



   PROCEDURE getListaPorBuscar (
      V_LISTA                        OUT cursorLista,
      v_cont_mesmayor_n           IN     CON_LIBROMAYOR.CONT_MESMAYOR_N%TYPE,
      v_cont_periodomayor_n       IN     CON_LIBROMAYOR.CONT_PERIODOMAYOR_N%TYPE,
      v_para_estadocierre_n_cod   IN     CON_LIBROMAYOR.PARA_ESTADOCIERRE_N_COD%TYPE)
   IS
      var_lista   cursorLista;
   BEGIN
      OPEN var_lista FOR
         SELECT PERS_EMPRESAMAYOR_N_PK pPersEmpresaMayor,
                CONT_PERIODOMAYOR_N pContPeriodoMayor,
                CONT_MESMAYOR_N pContMesMayor,
                LIMA_FECHAREGISTRO_D pLimaFechaRegistro,
                PARA_ESTADOCIERRE_N_COD pParaEstadoCierre,
                PERS_EMPRESAUSUARIO_N_PK pPersEmpresaUsuario,
                PERS_PERSONAUSUARIO_N_PK pPersPersonaUsuario,
                PARA_ESTADO_N_COD pParaEstadoCod,
                NULL pTablaDescripcion
           FROM CON_LIBROMAYOR
          WHERE (v_cont_mesmayor_n IS NULL
                 OR CONT_MESMAYOR_N = v_cont_mesmayor_n)
                AND (v_cont_periodomayor_n IS NULL
                     OR CONT_PERIODOMAYOR_N = v_cont_periodomayor_n)
                AND (v_para_estadocierre_n_cod IS NULL
                     OR PARA_ESTADOCIERRE_N_COD = v_para_estadocierre_n_cod);

      V_LISTA := var_lista;
   END getListaPorBuscar;

   PROCEDURE getListMayorHist (
      V_LISTA                       OUT cursorLista,
      v_cont_mesmayor_n          IN     CON_LIBROMAYOR.CONT_MESMAYOR_N%TYPE,
      v_cont_periodomayor_n      IN     CON_LIBROMAYOR.CONT_PERIODOMAYOR_N%TYPE,
      v_para_estado_n_cod        IN     CON_LIBROMAYOR.PARA_ESTADO_N_COD%TYPE,
      v_pers_empresamayor_n_pk   IN     CON_LIBROMAYOR.PERS_EMPRESAMAYOR_N_PK%TYPE)
   IS
      var_lista   cursorLista;
   BEGIN
      OPEN var_lista FOR
         SELECT 'CH' pTablaDescripcion,
                CL.CONT_MESMAYOR_N pContMesMayor,
                CL.CONT_PERIODOMAYOR_N pContPeriodoMayor,
                CL.LIMA_FECHAREGISTRO_D pLimaFechaRegistro,
                CL.PARA_ESTADO_N_COD pParaEstadoCod,
                CL.PARA_ESTADOCIERRE_N_COD pParaEstadoCierre,
                CL.PERS_EMPRESAMAYOR_N_PK pPersEmpresaMayor,
                CL.PERS_EMPRESAUSUARIO_N_PK pPersEmpresaUsuario,
                CL.PERS_PERSONAUSUARIO_N_PK pPersPersonaUsuario
           FROM CON_LIBROMAYOR CL
          WHERE (v_cont_mesmayor_n IS NULL
                 OR CONT_MESMAYOR_N = v_cont_mesmayor_n)
                AND (v_cont_periodomayor_n IS NULL
                     OR CONT_PERIODOMAYOR_N = v_cont_periodomayor_n)
                AND (v_para_estado_n_cod IS NULL
                     OR PARA_ESTADO_N_COD = v_para_estado_n_cod)
                AND (PERS_EMPRESAMAYOR_N_PK = v_pers_empresamayor_n_pk)
         UNION
         SELECT 'CHL' pTablaDescripcion,
                CHL.CONT_MESMAYOR_N pContMesMayor,
                CHL.CONT_PERIODOMAYOR_N pContPeriodoMayor,
                CHL.LIMA_FECHAREGISTRO_D pLimaFechaRegistro,
                CHL.PARA_ESTADO_N_COD pParaEstadoCod,
                CHL.PARA_ESTADOCIERRE_N_COD pParaEstadoCierre,
                CHL.PERS_EMPRESAMAYOR_N_PK pPersEmpresaMayor,
                CHL.PERS_EMPRESAUSUARIO_N_PK pPersEmpresaUsuario,
                CHL.PERS_PERSONAUSUARIO_N_PK pPersPersonaUsuario
           FROM CON_LIBROMAYOR_HISTORICO CHL
          WHERE (v_cont_mesmayor_n IS NULL
                 OR CONT_MESMAYOR_N = v_cont_mesmayor_n)
                AND (v_cont_periodomayor_n IS NULL
                     OR CONT_PERIODOMAYOR_N = v_cont_periodomayor_n)
                AND (v_para_estado_n_cod IS NULL
                     OR PARA_ESTADO_N_COD = v_para_estado_n_cod)
                AND (PERS_EMPRESAMAYOR_N_PK = v_pers_empresamayor_n_pk)
         ORDER BY 3, 2;

      V_LISTA := var_lista;
   END getListMayorHist;

   PROCEDURE getAfterProcessedByPeriod (
      V_LISTA                    OUT cursorLista,
      v_cont_mesmayor_n       IN     CON_LIBROMAYOR.CONT_MESMAYOR_N%TYPE,
      v_cont_periodomayor_n   IN     CON_LIBROMAYOR.CONT_PERIODOMAYOR_N%TYPE,
      v_para_estado_n_cod     IN     CON_LIBROMAYOR.PARA_ESTADO_N_COD%TYPE)
   IS
      var_lista   cursorLista;
   BEGIN
      OPEN var_lista FOR
         SELECT CL.CONT_MESMAYOR_N pContMesMayor,
                CL.CONT_PERIODOMAYOR_N pContPeriodoMayor,
                CL.LIMA_FECHAREGISTRO_D pLimaFechaRegistro,
                CL.PARA_ESTADO_N_COD pParaEstadoCod,
                CL.PARA_ESTADOCIERRE_N_COD pParaEstadoCierre,
                CL.PERS_EMPRESAMAYOR_N_PK pPersEmpresaMayor,
                CL.PERS_EMPRESAUSUARIO_N_PK pPersEmpresaUsuario,
                CL.PERS_PERSONAUSUARIO_N_PK pPersPersonaUsuario,
                CL.PARA_ESTADO_N_COD pParaEstadoCod,
                NULL pTablaDescripcion
           FROM CON_LIBROMAYOR CL
          WHERE (TO_CHAR (
                    TO_DATE ( (CONT_PERIODOMAYOR_N || CONT_MESMAYOR_N),
                             'YYYYMM'),
                    'YYYYMM') >
                    TO_CHAR (
                       TO_DATE (
                          (v_cont_periodomayor_n || v_cont_mesmayor_n),
                          'YYYYMM'),
                       'YYYYMM'))
                AND (CL.PARA_ESTADO_N_COD = v_para_estado_n_cod);

      V_LISTA := var_lista;
   END getAfterProcessedByPeriod;


   PROCEDURE deleteMayorizado (
      v_pers_empresamayor_n_pk     IN OUT CON_LIBROMAYOR.PERS_EMPRESAMAYOR_N_PK%TYPE,
      v_cont_periodomayor_n        IN OUT CON_LIBROMAYOR.CONT_PERIODOMAYOR_N%TYPE,
      v_cont_mesmayor_n            IN OUT CON_LIBROMAYOR.CONT_MESMAYOR_N%TYPE,
      v_lima_fecharegistro_d       IN OUT CON_LIBROMAYOR.LIMA_FECHAREGISTRO_D%TYPE,
      v_para_estadocierre_n_cod    IN OUT CON_LIBROMAYOR.PARA_ESTADOCIERRE_N_COD%TYPE,
      v_pers_empresausuario_n_pk   IN OUT CON_LIBROMAYOR.PERS_EMPRESAUSUARIO_N_PK%TYPE,
      V_pers_personausuario_n_pk   IN OUT CON_LIBROMAYOR.PERS_PERSONAUSUARIO_N_PK%TYPE,
      V_PARA_ESTADO_N_COD          IN OUT CON_LIBROMAYOR.PARA_ESTADO_N_COD%TYPE,
      V_IPUSUARIOELIMINA_HIST_N    IN OUT CON_LIBROMAYOR_HISTORICO.CONT_IP_ELIMINA_HIST_D%TYPE)
   IS
      V_LIBROMAYOR_HIST_PK   NUMBER (7);
   BEGIN
      SELECT SEQ_CON_LIBROMAYOR_HISTORICO.NEXTVAL
        INTO V_LIBROMAYOR_HIST_PK
        FROM DUAL;

      INSERT INTO CON_LIBROMAYOR_HISTORICO (PERS_EMPRESAMAYOR_N_PK,
                                            CONT_PERIODOMAYOR_N,
                                            CONT_MESMAYOR_N,
                                            CONT_LIBROMAYOR_HIST_N_PK,
                                            LIMA_FECHAREGISTRO_D,
                                            PARA_ESTADO_N_COD,
                                            PARA_ESTADOCIERRE_N_COD,
                                            PERS_EMPRESAUSUARIO_N_PK,
                                            PERS_PERSONAUSUARIO_N_PK,
                                            CONT_IP_ELIMINA_HIST_D)
           VALUES (v_pers_empresamayor_n_pk,
                   v_cont_periodomayor_n,
                   v_cont_mesmayor_n,
                   V_LIBROMAYOR_HIST_PK,
                   v_lima_fecharegistro_d,
                   V_PARA_ESTADO_N_COD,
                   v_para_estadocierre_n_cod,
                   v_pers_empresausuario_n_pk,
                   V_pers_personausuario_n_pk,
                   V_IPUSUARIOELIMINA_HIST_N);

      INSERT INTO CON_LIBROMAYORDETALLE_HIST (PERS_EMPRESAMAYOR_N_PK,
                                              CONT_PERIODOMAYOR_N,
                                              CONT_MESMAYOR_N,
                                              CONT_LIBROMAYOR_HIST_N_PK,
                                              PERS_EMPRESACUENTA_N_PK,
                                              CONT_PERIODOCUENTA_N,
                                              CONT_NUMEROCUENTA_V,
                                              LMDE_DEBESOLESSALDO_N,
                                              LMDE_HABERSOLESSALDO_N,
                                              LMDE_DEBEEXTRANJEROSALDO_N,
                                              LMDE_HABEREXTRANJEROSALDO_N,
                                              LMDE_DEBESOLESSALDOFIN_N,
                                              LMDE_HABERSOLESSALDOFIN_N,
                                              LMDE_DEBEEXTRANJEROSALDOFIN_N,
                                              LMDE_HABEREXTRANJEROSALDOFIN_N,
                                              LMDE_DEBESOLES_N,
                                              LMDE_HABERSOLES_N,
                                              LMDE_DEBEEXTRANJERO_N,
                                              LMDE_HABEREXTRANJERO_N,
                                              PERS_EMPRESASUCURSAL_N_PK,
                                              SUCU_IDSUCURSAL_N_PK,
                                              SUDE_IDSUBSUCURSAL_N_PK)
         SELECT PERS_EMPRESAMAYOR_N_PK,
                CONT_PERIODOMAYOR_N,
                CONT_MESMAYOR_N,
                V_LIBROMAYOR_HIST_PK,
                PERS_EMPRESACUENTA_N_PK,
                CONT_PERIODOCUENTA_N,
                CONT_NUMEROCUENTA_V,
                LMDE_DEBESOLESSALDO_N,
                LMDE_HABERSOLESSALDO_N,
                LMDE_DEBEEXTRANJEROSALDO_N,
                LMDE_HABEREXTRANJEROSALDO_N,
                LMDE_DEBESOLESSALDOFIN_N,
                LMDE_HABERSOLESSALDOFIN_N,
                LMDE_DEBEEXTRANJEROSALDOFIN_N,
                LMDE_HABEREXTRANJEROSALDOFIN_N,
                LMDE_DEBESOLES_N,
                LMDE_HABERSOLES_N,
                LMDE_DEBEEXTRANJERO_N,
                LMDE_HABEREXTRANJERO_N,
                PERS_EMPRESASUCURSAL_N_PK,
                SUCU_IDSUCURSAL_N_PK,
                SUDE_IDSUBSUCURSAL_N_PK
           FROM CON_LIBROMAYORDETALLE
          WHERE     PERS_EMPRESAMAYOR_N_PK = v_pers_empresamayor_n_pk
                AND CONT_PERIODOMAYOR_N = v_cont_periodomayor_n
                AND CONT_MESMAYOR_N = v_cont_mesmayor_n;

      --Eliminando la cabecera de la tabla LIBRO_MAYOR y su detalle
      DELETE FROM CON_LIBROMAYORDETALLE
            WHERE     PERS_EMPRESAMAYOR_N_PK = v_pers_empresamayor_n_pk
                  AND CONT_PERIODOMAYOR_N = v_cont_periodomayor_n
                  AND CONT_MESMAYOR_N = v_cont_mesmayor_n;

      DELETE FROM CON_LIBROMAYOR
            WHERE     PERS_EMPRESAMAYOR_N_PK = v_pers_empresamayor_n_pk
                  AND CONT_PERIODOMAYOR_N = v_cont_periodomayor_n
                  AND CONT_MESMAYOR_N = v_cont_mesmayor_n;
   END deleteMayorizado;

   PROCEDURE getValidAccounts (
      V_ERROR_LIST                  OUT VARCHAR2,
      v_cont_mesmayor_n          IN     CON_LIBROMAYOR.CONT_MESMAYOR_N%TYPE,
      v_cont_periodomayor_n      IN     CON_LIBROMAYOR.CONT_PERIODOMAYOR_N%TYPE,
      v_para_estado_n_cod        IN     CON_LIBROMAYOR.PARA_ESTADO_N_COD%TYPE,
      v_pers_empresamayor_n_pk   IN     CON_LIBROMAYOR.PERS_EMPRESAMAYOR_N_PK%TYPE)
   IS
      V_NROCTA       CON_PLANCUENTA.CONT_NUMEROCUENTA_V%TYPE;
      V_NROCTA_SUP   VARCHAR2 (30);

      CURSOR V_LISTA_PLANCUENTA
      IS
           SELECT *
             FROM CON_PLANCUENTA PC
            WHERE PC.CONT_PERIODOCUENTA_N = v_cont_periodomayor_n
                  AND PC.PARA_ESTADO_N_COD = PKG_CONSTANTE.C_ESTADO_ACTIVO
         ORDER BY 3;
   BEGIN
      FOR i IN V_LISTA_PLANCUENTA
      LOOP
         V_NROCTA := i.CONT_NUMEROCUENTA_V;

         IF V_NROCTA IS NOT NULL AND (LENGTH (V_NROCTA) > 2)
         THEN
            V_NROCTA_SUP := SUBSTR (V_NROCTA, 0, (LENGTH (V_NROCTA) - 2));

            FOR j IN V_LISTA_PLANCUENTA
            LOOP
               IF V_NROCTA_SUP != j.CONT_NUMEROCUENTA_V
               THEN
                  V_ERROR_LIST := V_NROCTA || ',';
               END IF;
            END LOOP;
         END IF;
      END LOOP;
   END getValidAccounts;

   PROCEDURE processMayorizacion (
      V_PROC_MAYOR_RETURN             OUT NUMBER,
      v_cont_mesmayor_n            IN     CON_LIBROMAYOR.CONT_MESMAYOR_N%TYPE,
      v_cont_periodomayor_n        IN     CON_LIBROMAYOR.CONT_PERIODOMAYOR_N%TYPE,
      v_pers_empresamayor_n_pk     IN     CON_LIBROMAYOR.PERS_EMPRESAMAYOR_N_PK%TYPE,
      v_pers_empresauario_n_pk     IN     CON_LIBROMAYOR.PERS_EMPRESAUSUARIO_N_PK%TYPE,
      v_pers_personausuario_n_pk   IN     CON_LIBROMAYOR.PERS_PERSONAUSUARIO_N_PK%TYPE,
      v_cont_periodomayor_v        IN     VARCHAR2)
   IS
      v_n_haber_soles_calculo        NUMBER;
      v_n_debe_soles_calculo         NUMBER;
      v_n_haber_soles_calculo_fin    NUMBER;
      v_n_debe_soles_calculo_fin     NUMBER;
      v_n_haber_soles_calculo_sal    NUMBER;
      v_n_debe_soles_calculo_sal     NUMBER;
      v_n_haber_extranjera_calculo   NUMBER;
      v_n_debe_extranjera_calculo    NUMBER;
      v_n_haber_ext_calc_fin         NUMBER;
      v_n_debe_ext_calc_fin          NUMBER;
      v_n_haber_ext_calc_sal         NUMBER;
      v_n_debe_ext_calc_sal          NUMBER;
      v_n_nivel_cuenta               NUMBER;
      v_n_sz_cuenta_nv               NUMBER;

      -- lista de cuentas integradoras por niveles
      CURSOR v_lista_integradora_nivel (
         c_niv_cu_n NUMBER)
      IS
           SELECT CLMD.PERS_EMPRESAMAYOR_N_PK,
                  CLMD.PERS_EMPRESACUENTA_N_PK,
                  CLMD.CONT_PERIODOCUENTA_N,
                  SUBSTR (CLMD.cont_numerocuenta_v, 1, 2) || '0'
                  || SUBSTR (CLMD.cont_numerocuenta_v,
                             4,
                             LENGTH (CLMD.cont_numerocuenta_v))
                     AS cont_numerocuenta_v,
                  SUM (LMDE_DEBESOLESSALDO_N) LMDE_DEBESOLESSALDO_N,
                  SUM (LMDE_HABERSOLESSALDO_N) LMDE_HABERSOLESSALDO_N,
                  SUM (LMDE_DEBEEXTRANJEROSALDO_N) LMDE_DEBEEXTRANJEROSALDO_N,
                  SUM (LMDE_HABEREXTRANJEROSALDO_N) LMDE_HABEREXTRANJEROSALDO_N,
                  SUM (LMDE_DEBESOLES_N) LMDE_DEBESOLES_N,
                  SUM (LMDE_HABERSOLES_N) LMDE_HABERSOLES_N,
                  SUM (LMDE_DEBEEXTRANJERO_N) LMDE_DEBEEXTRANJERO_N,
                  SUM (LMDE_HABEREXTRANJERO_N) LMDE_HABEREXTRANJERO_N,
                  SUM (LMDE_DEBESOLESSALDOFIN_N) LMDE_DEBESOLESSALDOFIN_N,
                  SUM (LMDE_HABERSOLESSALDOFIN_N) LMDE_HABERSOLESSALDOFIN_N,
                  SUM (LMDE_DEBEEXTRANJEROSALDOFIN_N)
                     LMDE_DEBEEXTRANJEROSALDOFIN_N,
                  SUM (LMDE_HABEREXTRANJEROSALDOFIN_N)
                     LMDE_HABEREXTRANJEROSALDOFIN_N
             FROM con_libromayordetalle CLMD
            WHERE     SUBSTR (CLMD.cont_numerocuenta_v, 3, 1) != 0
                  AND CLMD.CONT_PERIODOMAYOR_N = v_cont_periodomayor_n
                  AND CLMD.CONT_MESMAYOR_N = v_cont_mesmayor_n
                  AND CLMD.LMDE_NIVELCUENTA_N = c_niv_cu_n
         GROUP BY (SUBSTR (CLMD.cont_numerocuenta_v, 1, 2) || '0'
                   || SUBSTR (CLMD.cont_numerocuenta_v,
                              4,
                              LENGTH (CLMD.cont_numerocuenta_v)),
                   PERS_EMPRESASUCURSAL_N_PK,
                   CLMD.PERS_EMPRESAMAYOR_N_PK,
                   CLMD.PERS_EMPRESACUENTA_N_PK,
                   CLMD.CONT_PERIODOCUENTA_N)
         ORDER BY 1, 2 ASC;

      -- lista de cuentas mayorizadas por niveles
      CURSOR v_lista_mayor_cuentas_nivel (
         c_niv_cu_n NUMBER)
      IS
           SELECT CLMD.PERS_EMPRESAMAYOR_N_PK,
                  CLMD.PERS_EMPRESACUENTA_N_PK,
                  CLMD.CONT_PERIODOCUENTA_N,
                  SUBSTR (CLMD.CONT_NUMEROCUENTA_V,
                          1,
                          LENGTH (CLMD.CONT_NUMEROCUENTA_V) - 2)
                     AS CONT_NUMEROCUENTA_V,
                  SUM (CLMD.LMDE_DEBESOLESSALDO_N) AS LMDE_DEBESOLESSALDO_N,
                  SUM (CLMD.LMDE_HABERSOLESSALDO_N) AS LMDE_HABERSOLESSALDO_N,
                  SUM (CLMD.LMDE_DEBEEXTRANJEROSALDO_N)
                     AS LMDE_DEBEEXTRANJEROSALDO_N,
                  SUM (CLMD.LMDE_HABEREXTRANJEROSALDO_N)
                     AS LMDE_HABEREXTRANJEROSALDO_N,
                  SUM (CLMD.LMDE_DEBESOLES_N) AS LMDE_DEBESOLES_N,
                  SUM (CLMD.LMDE_HABERSOLES_N) AS LMDE_HABERSOLES_N,
                  SUM (CLMD.LMDE_DEBEEXTRANJERO_N) AS LMDE_DEBEEXTRANJERO_N,
                  SUM (CLMD.LMDE_HABEREXTRANJERO_N) AS LMDE_HABEREXTRANJERO_N,
                  0 AS PERS_EMPRESASUCURSAL_N_PK,
                  0 AS SUCU_IDSUCURSAL_N_PK,
                  0 AS SUDE_IDSUBSUCURSAL_N_PK,
                  SUM (CLMD.LMDE_DEBESOLESSALDOFIN_N)
                     AS LMDE_DEBESOLESSALDOFIN_N,
                  SUM (CLMD.LMDE_HABERSOLESSALDOFIN_N)
                     AS LMDE_HABERSOLESSALDOFIN_N,
                  SUM (CLMD.LMDE_DEBEEXTRANJEROSALDOFIN_N)
                     AS LMDE_DEBEEXTRANJEROSALDOFIN_N,
                  SUM (CLMD.LMDE_HABEREXTRANJEROSALDOFIN_N)
                     AS LMDE_HABEREXTRANJEROSALDOFIN_N
             FROM CON_LIBROMAYORDETALLE CLMD
            WHERE     CLMD.CONT_PERIODOMAYOR_N = v_cont_periodomayor_n
                  AND CLMD.CONT_MESMAYOR_N = v_cont_mesmayor_n
                  AND CLMD.LMDE_NIVELCUENTA_N = c_niv_cu_n
                  AND LENGTH (
                         SUBSTR (CLMD.CONT_NUMEROCUENTA_V,
                                 1,
                                 LENGTH (CLMD.CONT_NUMEROCUENTA_V) - 2)) >= 2
         GROUP BY (CLMD.PERS_EMPRESAMAYOR_N_PK,
                   SUBSTR (CLMD.CONT_NUMEROCUENTA_V,
                           1,
                           LENGTH (CLMD.CONT_NUMEROCUENTA_V) - 2),
                   CLMD.PERS_EMPRESACUENTA_N_PK,
                   CLMD.CONT_PERIODOCUENTA_N);

      -- Lista de las cuentas registradas en el libro diario.
      CURSOR v_lista_detalle_libro_diario
      IS
           SELECT CLD.PERS_EMPRESACUENTA_N_PK,
                  CLD.CONT_PERIODO_N,
                  CLD.CONT_NUMEROCUENTA_V,
                  CLD.PERS_EMPRESASUCURSAL_N_PK,
                  CLD.SUCU_IDSUCURSAL_N_PK,
                  CLD.SUDE_IDSUBSUCURSAL_N_PK,
                  CLD2.LMDE_DEBESOLESSALDOFIN_N AS LMDE_DEBESOLESSALDO_N,
                  CLD2.LMDE_HABERSOLESSALDOFIN_N AS LMDE_HABERSOLESSALDO_N,
                  CLD2.LMDE_DEBEEXTRANJEROSALDOFIN_N
                     AS LMDE_DEBEEXTRANJEROSALDO_N,
                  CLD2.LMDE_HABEREXTRANJEROSALDOFIN_N
                     AS LMDE_HABEREXTRANJEROSALDO_N,
                  SUM (CLD.LDDE_DEBESOLES_N) AS LDDE_DEBESOLES_N,
                  SUM (CLD.LDDE_HABERSOLES_N) AS LDDE_HABERSOLES_N,
                  SUM (CLD.LDDE_DEBEEXTRANJERO_N) AS LDDE_DEBEEXTRANJERO_N,
                  SUM (CLD.LDDE_HABEREXTRANJERO_N) AS LDDE_HABEREXTRANJERO_N
             FROM    CON_LIBRODIARIODETALLE CLD
                  LEFT JOIN
                     CON_LIBROMAYORDETALLE cld2
                  ON (CLD.PERS_EMPRESACUENTA_N_PK =
                         CLD2.PERS_EMPRESACUENTA_N_PK
                      AND CLD.CONT_PERIODO_N = CLD2.CONT_PERIODOCUENTA_N
                      AND CLD.CONT_NUMEROCUENTA_V = CLD2.CONT_NUMEROCUENTA_V
                      AND CLD.PERS_EMPRESASUCURSAL_N_PK =
                             CLD2.PERS_EMPRESASUCURSAL_N_PK
                      AND CLD.SUCU_IDSUCURSAL_N_PK = CLD2.SUCU_IDSUCURSAL_N_PK
                      AND CLD.SUDE_IDSUBSUCURSAL_N_PK =
                             CLD2.SUDE_IDSUBSUCURSAL_N_PK
                      AND CLD2.CONT_MESMAYOR_N = v_cont_mesmayor_n - 1)
            WHERE CLD.CONT_PERIODOLIBRO_N = v_cont_periodomayor_v
         GROUP BY (CLD.PERS_EMPRESACUENTA_N_PK,
                   CLD.CONT_PERIODO_N,
                   CLD.CONT_NUMEROCUENTA_V,
                   CLD.PERS_EMPRESASUCURSAL_N_PK,
                   CLD.SUCU_IDSUCURSAL_N_PK,
                   CLD.SUDE_IDSUBSUCURSAL_N_PK,
                   CLD2.LMDE_DEBESOLESSALDOFIN_N,
                   CLD2.LMDE_HABERSOLESSALDOFIN_N,
                   CLD2.LMDE_DEBEEXTRANJEROSALDOFIN_N,
                   CLD2.LMDE_HABEREXTRANJEROSALDOFIN_N)
         UNION
         SELECT CLDANT.PERS_EMPRESACUENTA_N_PK,
                CLDANT.CONT_PERIODOCUENTA_N CONT_PERIODO_N,
                CLDANT.CONT_NUMEROCUENTA_V,
                CLDANT.PERS_EMPRESASUCURSAL_N_PK,
                CLDANT.SUCU_IDSUCURSAL_N_PK,
                CLDANT.SUDE_IDSUBSUCURSAL_N_PK,
                CLDANT.LMDE_DEBESOLESSALDOFIN_N AS LMDE_DEBESOLESSALDO_N,
                CLDANT.LMDE_HABERSOLESSALDOFIN_N AS LMDE_HABERSOLESSALDO_N,
                CLDANT.LMDE_DEBEEXTRANJEROSALDOFIN_N
                   AS LMDE_DEBEEXTRANJEROSALDO_N,
                CLDANT.LMDE_HABEREXTRANJEROSALDOFIN_N
                   AS LMDE_HABEREXTRANJEROSALDO_N,
                0 LDDE_DEBESOLES_N,
                0 LDDE_HABERSOLES_N,
                0 LDDE_DEBEEXTRANJERO_N,
                0 LDDE_HABEREXTRANJERO_N
           FROM CON_LIBROMAYORDETALLE CLDANT
          WHERE CLDANT.CONT_NUMEROCUENTA_V NOT IN
                   (SELECT DISTINCT CLD.CONT_NUMEROCUENTA_V
                      FROM CON_LIBRODIARIODETALLE CLD
                     WHERE CLD.CONT_PERIODOLIBRO_N = v_cont_periodomayor_v)
                AND CLDANT.CONT_MESMAYOR_N = v_cont_mesmayor_n - 1
                AND CLDANT.LMDE_NIVELCUENTA_N = 1
         ORDER BY PERS_EMPRESACUENTA_N_PK,
                  CONT_PERIODO_N,
                  CONT_NUMEROCUENTA_V;
   BEGIN
      v_n_nivel_cuenta := 1;
      v_n_sz_cuenta_nv := 0;
      V_PROC_MAYOR_RETURN := 0;

      FOR listRec IN v_lista_detalle_libro_diario
      LOOP
         v_n_debe_soles_calculo := NVL (listRec.LDDE_DEBESOLES_N, 0);
         v_n_haber_soles_calculo := NVL (listRec.LDDE_HABERSOLES_N, 0);
         v_n_debe_extranjera_calculo := NVL (listRec.LDDE_DEBEEXTRANJERO_N, 0);
         v_n_haber_extranjera_calculo :=
            NVL (listRec.LDDE_HABEREXTRANJERO_N, 0);

         -- calculando debe y haber soles x movimientos
         IF (v_n_debe_soles_calculo > v_n_haber_soles_calculo)
         THEN
            v_n_debe_soles_calculo :=
               v_n_debe_soles_calculo - v_n_haber_soles_calculo;
            v_n_haber_soles_calculo := 0;
         ELSE
            v_n_haber_soles_calculo :=
               v_n_haber_soles_calculo - v_n_debe_soles_calculo;
            v_n_debe_soles_calculo := 0;
         END IF;

         -- calculando debe y haber extranjera x movimientos
         IF (v_n_debe_extranjera_calculo > v_n_haber_extranjera_calculo)
         THEN
            v_n_debe_extranjera_calculo :=
               v_n_debe_extranjera_calculo - v_n_haber_extranjera_calculo;
            v_n_haber_extranjera_calculo := 0;
         ELSE
            v_n_haber_extranjera_calculo :=
               v_n_haber_extranjera_calculo - v_n_debe_extranjera_calculo;
            v_n_debe_extranjera_calculo := 0;
         END IF;

         -- sumando el saldo mas el haber o deber por movimiento.
         -- soles
         v_n_debe_soles_calculo_fin :=
            v_n_debe_soles_calculo + NVL (listRec.LMDE_DEBESOLESSALDO_N, 0);
         v_n_haber_soles_calculo_fin :=
            v_n_haber_soles_calculo + NVL (listRec.LMDE_HABERSOLESSALDO_N, 0);
         -- dolares
         v_n_debe_ext_calc_fin :=
            v_n_debe_extranjera_calculo
            + NVL (listRec.LMDE_DEBEEXTRANJEROSALDO_N, 0);
         v_n_haber_ext_calc_fin :=
            v_n_haber_extranjera_calculo
            + NVL (listRec.LMDE_HABEREXTRANJEROSALDO_N, 0);

         -- calcular saldo fin para soles
         IF (v_n_debe_soles_calculo_fin > v_n_haber_soles_calculo_fin)
         THEN
            v_n_debe_soles_calculo_fin :=
               v_n_debe_soles_calculo_fin - v_n_haber_soles_calculo_fin;
            v_n_haber_soles_calculo_fin := 0;
         ELSE
            v_n_haber_soles_calculo_fin :=
               v_n_haber_soles_calculo_fin - v_n_debe_soles_calculo_fin;
            v_n_debe_soles_calculo_fin := 0;
         END IF;

         -- calcular saldo fin para soles
         IF (v_n_debe_ext_calc_fin > v_n_haber_ext_calc_fin)
         THEN
            v_n_debe_ext_calc_fin :=
               v_n_debe_ext_calc_fin - v_n_haber_ext_calc_fin;
            v_n_haber_ext_calc_fin := 0;
         ELSE
            v_n_haber_ext_calc_fin :=
               v_n_haber_ext_calc_fin - v_n_debe_ext_calc_fin;
            v_n_debe_ext_calc_fin := 0;
         END IF;

         IF (v_n_sz_cuenta_nv < LENGTH (listRec.CONT_NUMEROCUENTA_V))
         THEN
            v_n_sz_cuenta_nv := LENGTH (listRec.CONT_NUMEROCUENTA_V);
         END IF;

         INSERT INTO CON_LIBROMAYORDETALLE (PERS_EMPRESAMAYOR_N_PK,        --1
                                            CONT_PERIODOMAYOR_N,           --2
                                            CONT_MESMAYOR_N,               --3
                                            PERS_EMPRESACUENTA_N_PK,       --4
                                            CONT_PERIODOCUENTA_N,          --5
                                            CONT_NUMEROCUENTA_V,           --6
                                            LMDE_DEBESOLESSALDO_N,         --7
                                            LMDE_HABERSOLESSALDO_N,        --8
                                            LMDE_DEBEEXTRANJEROSALDO_N,    --9
                                            LMDE_HABEREXTRANJEROSALDO_N,  --10
                                            LMDE_DEBESOLES_N,             --11
                                            LMDE_HABERSOLES_N,            --12
                                            LMDE_DEBEEXTRANJERO_N,        --13
                                            LMDE_HABEREXTRANJERO_N,       --14
                                            PERS_EMPRESASUCURSAL_N_PK,    --15
                                            SUCU_IDSUCURSAL_N_PK,         --16
                                            SUDE_IDSUBSUCURSAL_N_PK,      --17
                                            LMDE_DEBESOLESSALDOFIN_N,     --18
                                            LMDE_HABERSOLESSALDOFIN_N,    --19
                                            LMDE_DEBEEXTRANJEROSALDOFIN_N, --20
                                            LMDE_HABEREXTRANJEROSALDOFIN_N, --21
                                            LMDE_NIVELCUENTA_N)
              VALUES (v_pers_empresamayor_n_pk,                            --1
                      v_cont_periodomayor_n,                               --2
                      v_cont_mesmayor_n,                                   --3
                      listRec.PERS_EMPRESACUENTA_N_PK,                     --4
                      listRec.CONT_PERIODO_N,                              --5
                      listRec.CONT_NUMEROCUENTA_V,                         --6
                      NVL (listRec.LMDE_DEBESOLESSALDO_N, 0),              --7
                      NVL (listRec.LMDE_HABERSOLESSALDO_N, 0),             --8
                      NVL (listRec.LMDE_DEBEEXTRANJEROSALDO_N, 0),         --9
                      NVL (listRec.LMDE_HABEREXTRANJEROSALDO_N, 0),       --10
                      v_n_debe_soles_calculo,                             --11
                      v_n_haber_soles_calculo,                            --12
                      v_n_debe_extranjera_calculo,                        --13
                      v_n_haber_extranjera_calculo,                       --14
                      listRec.PERS_EMPRESASUCURSAL_N_PK,                  --15
                      listRec.SUCU_IDSUCURSAL_N_PK,                       --16
                      NVL (listRec.SUDE_IDSUBSUCURSAL_N_PK, 0),           --17
                      v_n_debe_soles_calculo_fin,                         --18
                      v_n_haber_soles_calculo_fin,                        --19
                      v_n_debe_ext_calc_fin,                              --20
                      v_n_haber_ext_calc_fin,                             --21
                      v_n_nivel_cuenta);
      END LOOP;

      v_n_sz_cuenta_nv := v_n_sz_cuenta_nv / 2;

      FOR listRecIntegradoraDetalle
         IN v_lista_integradora_nivel (v_n_nivel_cuenta)
      LOOP
         v_n_debe_soles_calculo_sal :=
            NVL (listRecIntegradoraDetalle.LMDE_DEBESOLESSALDO_N, 0);
         v_n_haber_soles_calculo_sal :=
            NVL (listRecIntegradoraDetalle.LMDE_HABERSOLESSALDO_N, 0);
         v_n_debe_ext_calc_sal :=
            NVL (listRecIntegradoraDetalle.LMDE_DEBEEXTRANJEROSALDO_N, 0);
         v_n_haber_ext_calc_sal :=
            NVL (listRecIntegradoraDetalle.LMDE_HABEREXTRANJEROSALDO_N, 0);

         -- calculando debe y haber soles x movimientos
         IF (v_n_debe_soles_calculo_sal > v_n_haber_soles_calculo_sal)
         THEN
            v_n_debe_soles_calculo_sal :=
               v_n_debe_soles_calculo_sal - v_n_haber_soles_calculo_sal;
            v_n_haber_soles_calculo_sal := 0;
         ELSE
            v_n_haber_soles_calculo_sal :=
               v_n_haber_soles_calculo_sal - v_n_debe_soles_calculo_sal;
            v_n_debe_soles_calculo_sal := 0;
         END IF;

         -- calculando debe y haber extranjera x movimientos
         IF (v_n_debe_ext_calc_sal > v_n_haber_ext_calc_sal)
         THEN
            v_n_debe_ext_calc_sal :=
               v_n_debe_ext_calc_sal - v_n_haber_ext_calc_sal;
            v_n_haber_ext_calc_sal := 0;
         ELSE
            v_n_haber_ext_calc_sal :=
               v_n_haber_ext_calc_sal - v_n_debe_ext_calc_sal;
            v_n_debe_ext_calc_sal := 0;
         END IF;

         v_n_debe_soles_calculo :=
            NVL (listRecIntegradoraDetalle.LMDE_DEBESOLES_N, 0);
         v_n_haber_soles_calculo :=
            NVL (listRecIntegradoraDetalle.LMDE_HABERSOLES_N, 0);
         v_n_debe_extranjera_calculo :=
            NVL (listRecIntegradoraDetalle.LMDE_DEBEEXTRANJERO_N, 0);
         v_n_haber_extranjera_calculo :=
            NVL (listRecIntegradoraDetalle.LMDE_HABEREXTRANJERO_N, 0);

         -- calculando debe y haber soles x movimientos
         IF (v_n_debe_soles_calculo > v_n_haber_soles_calculo)
         THEN
            v_n_debe_soles_calculo :=
               v_n_debe_soles_calculo - v_n_haber_soles_calculo;
            v_n_haber_soles_calculo := 0;
         ELSE
            v_n_haber_soles_calculo :=
               v_n_haber_soles_calculo - v_n_debe_soles_calculo;
            v_n_debe_soles_calculo := 0;
         END IF;

         -- calculando debe y haber extranjera x movimientos
         IF (v_n_debe_extranjera_calculo > v_n_haber_extranjera_calculo)
         THEN
            v_n_debe_extranjera_calculo :=
               v_n_debe_extranjera_calculo - v_n_haber_extranjera_calculo;
            v_n_haber_extranjera_calculo := 0;
         ELSE
            v_n_haber_extranjera_calculo :=
               v_n_haber_extranjera_calculo - v_n_debe_extranjera_calculo;
            v_n_debe_extranjera_calculo := 0;
         END IF;

         -- sumando el saldo mas el haber o deber por movimiento.
         -- soles
         v_n_debe_soles_calculo_fin :=
            v_n_debe_soles_calculo
            + NVL (listRecIntegradoraDetalle.LMDE_DEBESOLESSALDO_N, 0);
         v_n_haber_soles_calculo_fin :=
            v_n_haber_soles_calculo
            + NVL (listRecIntegradoraDetalle.LMDE_HABERSOLESSALDO_N, 0);
         -- dolares
         v_n_debe_ext_calc_fin :=
            v_n_debe_extranjera_calculo
            + NVL (listRecIntegradoraDetalle.LMDE_DEBEEXTRANJEROSALDO_N, 0);
         v_n_haber_ext_calc_fin :=
            v_n_haber_extranjera_calculo
            + NVL (listRecIntegradoraDetalle.LMDE_HABEREXTRANJEROSALDO_N, 0);

         -- calcular saldo fin para soles
         IF (v_n_debe_soles_calculo_fin > v_n_haber_soles_calculo_fin)
         THEN
            v_n_debe_soles_calculo_fin :=
               v_n_debe_soles_calculo_fin - v_n_haber_soles_calculo_fin;
            v_n_haber_soles_calculo_fin := 0;
         ELSE
            v_n_haber_soles_calculo_fin :=
               v_n_haber_soles_calculo_fin - v_n_debe_soles_calculo_fin;
            v_n_debe_soles_calculo_fin := 0;
         END IF;

         -- calcular saldo fin para soles
         IF (v_n_debe_ext_calc_fin > v_n_haber_ext_calc_fin)
         THEN
            v_n_debe_ext_calc_fin :=
               v_n_debe_ext_calc_fin - v_n_haber_ext_calc_fin;
            v_n_haber_ext_calc_fin := 0;
         ELSE
            v_n_haber_ext_calc_fin :=
               v_n_haber_ext_calc_fin - v_n_debe_ext_calc_fin;
            v_n_debe_ext_calc_fin := 0;
         END IF;

         BEGIN
            INSERT
              INTO CON_LIBROMAYORDETALLE (PERS_EMPRESAMAYOR_N_PK,          --1
                                          CONT_PERIODOMAYOR_N,             --2
                                          CONT_MESMAYOR_N,                 --3
                                          PERS_EMPRESACUENTA_N_PK,         --4
                                          CONT_PERIODOCUENTA_N,            --5
                                          CONT_NUMEROCUENTA_V,             --6
                                          LMDE_DEBESOLESSALDO_N,           --7
                                          LMDE_HABERSOLESSALDO_N,          --8
                                          LMDE_DEBEEXTRANJEROSALDO_N,      --9
                                          LMDE_HABEREXTRANJEROSALDO_N,    --10
                                          LMDE_DEBESOLES_N,               --11
                                          LMDE_HABERSOLES_N,              --12
                                          LMDE_DEBEEXTRANJERO_N,          --13
                                          LMDE_HABEREXTRANJERO_N,         --14
                                          PERS_EMPRESASUCURSAL_N_PK,      --15
                                          SUCU_IDSUCURSAL_N_PK,           --16
                                          SUDE_IDSUBSUCURSAL_N_PK,        --17
                                          LMDE_DEBESOLESSALDOFIN_N,       --18
                                          LMDE_HABERSOLESSALDOFIN_N,      --19
                                          LMDE_DEBEEXTRANJEROSALDOFIN_N,  --20
                                          LMDE_HABEREXTRANJEROSALDOFIN_N, --21
                                          LMDE_NIVELCUENTA_N)
            VALUES (v_pers_empresamayor_n_pk,                              --1
                    v_cont_periodomayor_n,                                 --2
                    v_cont_mesmayor_n,                                     --3
                    listRecIntegradoraDetalle.PERS_EMPRESACUENTA_N_PK,     --4
                    listRecIntegradoraDetalle.CONT_PERIODOCUENTA_N,        --5
                    listRecIntegradoraDetalle.CONT_NUMEROCUENTA_V,         --6
                    v_n_debe_soles_calculo_sal,                            --7
                    v_n_haber_soles_calculo_sal,                           --8
                    v_n_debe_ext_calc_sal,                                 --9
                    v_n_haber_ext_calc_sal,                               --10
                    v_n_debe_soles_calculo,                               --11
                    v_n_haber_soles_calculo,                              --12
                    v_n_debe_extranjera_calculo,                          --13
                    v_n_haber_extranjera_calculo,                         --14
                    0,                                                    --15
                    0,                                                    --16
                    0,                                                    --17
                    v_n_debe_soles_calculo_fin,                           --18
                    v_n_haber_soles_calculo_fin,                          --19
                    v_n_debe_ext_calc_fin,                                --20
                    v_n_haber_ext_calc_fin,                               --21
                    v_n_nivel_cuenta);
         EXCEPTION
            WHEN DUP_VAL_ON_INDEX
            THEN
               UPDATE CON_LIBROMAYORDETALLE
                  SET LMDE_DEBESOLESSALDOFIN_N =
                         (CASE
                             WHEN ( (LMDE_HABERSOLESSALDOFIN_N
                                     + listRecIntegradoraDetalle.LMDE_HABERSOLESSALDOFIN_N) >
                                      (NVL (
                                          listRecIntegradoraDetalle.LMDE_DEBESOLESSALDOFIN_N,
                                          0)
                                       + LMDE_DEBESOLESSALDOFIN_N))
                             THEN
                                0
                             ELSE
                                (NVL (
                                    listRecIntegradoraDetalle.LMDE_DEBESOLESSALDOFIN_N,
                                    0)
                                 + LMDE_DEBESOLESSALDOFIN_N)
                                - (LMDE_HABERSOLESSALDOFIN_N
                                   + listRecIntegradoraDetalle.LMDE_HABERSOLESSALDOFIN_N)
                          END),
                      LMDE_HABERSOLESSALDOFIN_N =
                         (CASE
                             WHEN ( (LMDE_DEBESOLESSALDOFIN_N
                                     + listRecIntegradoraDetalle.LMDE_DEBESOLESSALDOFIN_N) >
                                      (NVL (
                                          listRecIntegradoraDetalle.LMDE_HABERSOLESSALDOFIN_N,
                                          0)
                                       + LMDE_HABERSOLESSALDOFIN_N))
                             THEN
                                0
                             ELSE
                                NVL (
                                   listRecIntegradoraDetalle.LMDE_HABERSOLESSALDOFIN_N,
                                   0)
                                + LMDE_HABERSOLESSALDOFIN_N
                                - (LMDE_DEBESOLESSALDOFIN_N
                                   + listRecIntegradoraDetalle.LMDE_DEBESOLESSALDOFIN_N)
                          END),
                      LMDE_DEBEEXTRANJEROSALDOFIN_N =
                         (CASE
                             WHEN ( (LMDE_HABEREXTRANJEROSALDOFIN_N
                                     + listRecIntegradoraDetalle.LMDE_HABEREXTRANJEROSALDOFIN_N) >
                                      (NVL (
                                          listRecIntegradoraDetalle.LMDE_DEBEEXTRANJEROSALDOFIN_N,
                                          0)
                                       + LMDE_DEBEEXTRANJEROSALDOFIN_N))
                             THEN
                                0
                             ELSE
                                (NVL (
                                    listRecIntegradoraDetalle.LMDE_DEBEEXTRANJEROSALDOFIN_N,
                                    0)
                                 + LMDE_DEBEEXTRANJEROSALDOFIN_N)
                                - (LMDE_HABEREXTRANJEROSALDOFIN_N
                                   + listRecIntegradoraDetalle.LMDE_HABEREXTRANJEROSALDOFIN_N)
                          END),
                      LMDE_HABEREXTRANJEROSALDOFIN_N =
                         (CASE
                             WHEN ( (LMDE_DEBEEXTRANJEROSALDOFIN_N
                                     + listRecIntegradoraDetalle.LMDE_DEBEEXTRANJEROSALDOFIN_N) >
                                      (NVL (
                                          listRecIntegradoraDetalle.LMDE_HABEREXTRANJEROSALDOFIN_N,
                                          0)
                                       + LMDE_HABEREXTRANJEROSALDOFIN_N))
                             THEN
                                0
                             ELSE
                                NVL (
                                   listRecIntegradoraDetalle.LMDE_HABEREXTRANJEROSALDOFIN_N,
                                   0)
                                + LMDE_HABEREXTRANJEROSALDOFIN_N
                                - (LMDE_DEBEEXTRANJEROSALDOFIN_N
                                   + listRecIntegradoraDetalle.LMDE_DEBEEXTRANJEROSALDOFIN_N)
                          END),
                      LMDE_DEBESOLESSALDO_N =
                         (CASE
                             WHEN ( (LMDE_HABERSOLESSALDO_N
                                     + listRecIntegradoraDetalle.LMDE_HABERSOLESSALDO_N) >
                                      (NVL (
                                          listRecIntegradoraDetalle.LMDE_DEBESOLESSALDO_N,
                                          0)
                                       + LMDE_DEBESOLESSALDO_N))
                             THEN
                                0
                             ELSE
                                (NVL (
                                    listRecIntegradoraDetalle.LMDE_DEBESOLESSALDO_N,
                                    0)
                                 + LMDE_DEBESOLESSALDO_N)
                                - (LMDE_HABERSOLESSALDO_N
                                   + listRecIntegradoraDetalle.LMDE_HABERSOLESSALDO_N)
                          END),
                      LMDE_HABERSOLESSALDO_N =
                         (CASE
                             WHEN ( (LMDE_DEBESOLESSALDO_N
                                     + listRecIntegradoraDetalle.LMDE_DEBESOLESSALDO_N) >
                                      (NVL (
                                          listRecIntegradoraDetalle.LMDE_HABERSOLESSALDO_N,
                                          0)
                                       + LMDE_HABERSOLESSALDO_N))
                             THEN
                                0
                             ELSE
                                NVL (
                                   listRecIntegradoraDetalle.LMDE_HABERSOLESSALDO_N,
                                   0)
                                + LMDE_HABERSOLESSALDO_N
                                - (LMDE_DEBESOLESSALDO_N
                                   + listRecIntegradoraDetalle.LMDE_DEBESOLESSALDO_N)
                          END),
                      LMDE_DEBEEXTRANJEROSALDO_N =
                         (CASE
                             WHEN ( (LMDE_HABEREXTRANJEROSALDO_N
                                     + listRecIntegradoraDetalle.LMDE_HABEREXTRANJEROSALDO_N) >
                                      (NVL (
                                          listRecIntegradoraDetalle.LMDE_DEBEEXTRANJEROSALDO_N,
                                          0)
                                       + LMDE_DEBEEXTRANJEROSALDO_N))
                             THEN
                                0
                             ELSE
                                (NVL (
                                    listRecIntegradoraDetalle.LMDE_DEBEEXTRANJEROSALDO_N,
                                    0)
                                 + LMDE_DEBEEXTRANJEROSALDO_N)
                                - (LMDE_HABEREXTRANJEROSALDO_N
                                   + listRecIntegradoraDetalle.LMDE_HABEREXTRANJEROSALDO_N)
                          END),
                      LMDE_HABEREXTRANJEROSALDO_N =
                         (CASE
                             WHEN ( (LMDE_DEBEEXTRANJEROSALDO_N
                                     + listRecIntegradoraDetalle.LMDE_DEBEEXTRANJEROSALDO_N) >
                                      (NVL (
                                          listRecIntegradoraDetalle.LMDE_HABEREXTRANJEROSALDO_N,
                                          0)
                                       + LMDE_HABEREXTRANJEROSALDO_N))
                             THEN
                                0
                             ELSE
                                NVL (
                                   listRecIntegradoraDetalle.LMDE_HABEREXTRANJEROSALDO_N,
                                   0)
                                + LMDE_HABEREXTRANJEROSALDO_N
                                - (LMDE_DEBEEXTRANJEROSALDO_N
                                   + listRecIntegradoraDetalle.LMDE_DEBEEXTRANJEROSALDO_N)
                          END),
                      LMDE_DEBESOLES_N =
                         (CASE
                             WHEN ( (LMDE_HABERSOLES_N
                                     + listRecIntegradoraDetalle.LMDE_HABERSOLES_N) >
                                      (NVL (
                                          listRecIntegradoraDetalle.LMDE_DEBESOLES_N,
                                          0)
                                       + LMDE_DEBESOLES_N))
                             THEN
                                0
                             ELSE
                                (NVL (
                                    listRecIntegradoraDetalle.LMDE_DEBESOLES_N,
                                    0)
                                 + LMDE_DEBESOLES_N)
                                - (LMDE_HABERSOLES_N
                                   + listRecIntegradoraDetalle.LMDE_HABERSOLES_N)
                          END),
                      LMDE_HABERSOLES_N =
                         (CASE
                             WHEN ( (LMDE_DEBESOLES_N
                                     + listRecIntegradoraDetalle.LMDE_DEBESOLES_N) >
                                      (NVL (
                                          listRecIntegradoraDetalle.LMDE_HABERSOLES_N,
                                          0)
                                       + LMDE_HABERSOLES_N))
                             THEN
                                0
                             ELSE
                                NVL (
                                   listRecIntegradoraDetalle.LMDE_HABERSOLES_N,
                                   0)
                                + LMDE_HABERSOLES_N
                                - (LMDE_DEBESOLES_N
                                   + listRecIntegradoraDetalle.LMDE_DEBESOLES_N)
                          END),
                      LMDE_DEBEEXTRANJERO_N =
                         (CASE
                             WHEN ( (LMDE_HABEREXTRANJERO_N
                                     + listRecIntegradoraDetalle.LMDE_HABEREXTRANJERO_N) >
                                      (NVL (
                                          listRecIntegradoraDetalle.LMDE_DEBEEXTRANJERO_N,
                                          0)
                                       + LMDE_DEBEEXTRANJERO_N))
                             THEN
                                0
                             ELSE
                                (NVL (
                                    listRecIntegradoraDetalle.LMDE_DEBEEXTRANJERO_N,
                                    0)
                                 + LMDE_DEBEEXTRANJERO_N)
                                - (LMDE_HABEREXTRANJERO_N
                                   + listRecIntegradoraDetalle.LMDE_HABEREXTRANJERO_N)
                          END),
                      LMDE_HABEREXTRANJERO_N =
                         (CASE
                             WHEN ( (LMDE_DEBEEXTRANJERO_N
                                     + listRecIntegradoraDetalle.LMDE_DEBEEXTRANJERO_N) >
                                      (NVL (
                                          listRecIntegradoraDetalle.LMDE_HABEREXTRANJERO_N,
                                          0)
                                       + LMDE_HABEREXTRANJERO_N))
                             THEN
                                0
                             ELSE
                                NVL (
                                   listRecIntegradoraDetalle.LMDE_HABEREXTRANJERO_N,
                                   0)
                                + LMDE_HABEREXTRANJERO_N
                                - (LMDE_DEBEEXTRANJERO_N
                                   + listRecIntegradoraDetalle.LMDE_DEBEEXTRANJERO_N)
                          END)
                WHERE     PERS_EMPRESAMAYOR_N_PK = v_pers_empresamayor_n_pk
                      AND CONT_PERIODOMAYOR_N = v_cont_periodomayor_n
                      AND CONT_MESMAYOR_N = v_cont_mesmayor_n
                      AND PERS_EMPRESACUENTA_N_PK =
                             listRecIntegradoraDetalle.PERS_EMPRESACUENTA_N_PK
                      AND CONT_PERIODOCUENTA_N =
                             listRecIntegradoraDetalle.CONT_PERIODOCUENTA_N
                      AND CONT_NUMEROCUENTA_V =
                             listRecIntegradoraDetalle.CONT_NUMEROCUENTA_V;
            WHEN OTHERS
            THEN
               DBMS_OUTPUT.PUT_LINE (
                     SQLCODE
                  || ' - '
                  || SQLERRM
                  || ' -- '
                  || v_pers_empresamayor_n_pk
                  || ' - '
                  || v_cont_periodomayor_n
                  || ' - '
                  || v_cont_mesmayor_n
                  || ' - '
                  || listRecIntegradoraDetalle.PERS_EMPRESACUENTA_N_PK
                  || ' - '
                  || listRecIntegradoraDetalle.CONT_PERIODOCUENTA_N
                  || ' - '
                  || listRecIntegradoraDetalle.CONT_NUMEROCUENTA_V);
         END;
      END LOOP;

      -- INSERTANDO LOS PADRES
      LOOP
         FOR listRecMayorDetalle
            IN v_lista_mayor_cuentas_nivel (v_n_nivel_cuenta)
         LOOP
            v_n_debe_soles_calculo_sal :=
               NVL (listRecMayorDetalle.LMDE_DEBESOLESSALDO_N, 0);
            v_n_haber_soles_calculo_sal :=
               NVL (listRecMayorDetalle.LMDE_HABERSOLESSALDO_N, 0);
            v_n_debe_ext_calc_sal :=
               NVL (listRecMayorDetalle.LMDE_DEBEEXTRANJEROSALDO_N, 0);
            v_n_haber_ext_calc_sal :=
               NVL (listRecMayorDetalle.LMDE_HABEREXTRANJEROSALDO_N, 0);

            -- calculando debe y haber soles x movimientos
            IF (v_n_debe_soles_calculo_sal > v_n_haber_soles_calculo_sal)
            THEN
               v_n_debe_soles_calculo_sal :=
                  v_n_debe_soles_calculo_sal - v_n_haber_soles_calculo_sal;
               v_n_haber_soles_calculo_sal := 0;
            ELSE
               v_n_haber_soles_calculo_sal :=
                  v_n_haber_soles_calculo_sal - v_n_debe_soles_calculo_sal;
               v_n_debe_soles_calculo_sal := 0;
            END IF;

            -- calculando debe y haber extranjera x movimientos
            IF (v_n_debe_ext_calc_sal > v_n_haber_ext_calc_sal)
            THEN
               v_n_debe_ext_calc_sal :=
                  v_n_debe_ext_calc_sal - v_n_haber_ext_calc_sal;
               v_n_haber_ext_calc_sal := 0;
            ELSE
               v_n_haber_ext_calc_sal :=
                  v_n_haber_ext_calc_sal - v_n_debe_ext_calc_sal;
               v_n_debe_ext_calc_sal := 0;
            END IF;

            v_n_debe_soles_calculo :=
               NVL (listRecMayorDetalle.LMDE_DEBESOLES_N, 0);
            v_n_haber_soles_calculo :=
               NVL (listRecMayorDetalle.LMDE_HABERSOLES_N, 0);
            v_n_debe_extranjera_calculo :=
               NVL (listRecMayorDetalle.LMDE_DEBEEXTRANJERO_N, 0);
            v_n_haber_extranjera_calculo :=
               NVL (listRecMayorDetalle.LMDE_HABEREXTRANJERO_N, 0);

            -- calculando debe y haber soles x movimientos
            IF (v_n_debe_soles_calculo > v_n_haber_soles_calculo)
            THEN
               v_n_debe_soles_calculo :=
                  v_n_debe_soles_calculo - v_n_haber_soles_calculo;
               v_n_haber_soles_calculo := 0;
            ELSE
               v_n_haber_soles_calculo :=
                  v_n_haber_soles_calculo - v_n_debe_soles_calculo;
               v_n_debe_soles_calculo := 0;
            END IF;

            -- calculando debe y haber extranjera x movimientos
            IF (v_n_debe_extranjera_calculo > v_n_haber_extranjera_calculo)
            THEN
               v_n_debe_extranjera_calculo :=
                  v_n_debe_extranjera_calculo - v_n_haber_extranjera_calculo;
               v_n_haber_extranjera_calculo := 0;
            ELSE
               v_n_haber_extranjera_calculo :=
                  v_n_haber_extranjera_calculo - v_n_debe_extranjera_calculo;
               v_n_debe_extranjera_calculo := 0;
            END IF;

            -- sumando el saldo mas el haber o deber por movimiento.
            -- soles
            v_n_debe_soles_calculo_fin :=
               v_n_debe_soles_calculo
               + NVL (listRecMayorDetalle.LMDE_DEBESOLESSALDO_N, 0);
            v_n_haber_soles_calculo_fin :=
               v_n_haber_soles_calculo
               + NVL (listRecMayorDetalle.LMDE_HABERSOLESSALDO_N, 0);
            -- dolares
            v_n_debe_ext_calc_fin :=
               v_n_debe_extranjera_calculo
               + NVL (listRecMayorDetalle.LMDE_DEBEEXTRANJEROSALDO_N, 0);
            v_n_haber_ext_calc_fin :=
               v_n_haber_extranjera_calculo
               + NVL (listRecMayorDetalle.LMDE_HABEREXTRANJEROSALDO_N, 0);

            -- calcular saldo fin para soles
            IF (v_n_debe_soles_calculo_fin > v_n_haber_soles_calculo_fin)
            THEN
               v_n_debe_soles_calculo_fin :=
                  v_n_debe_soles_calculo_fin - v_n_haber_soles_calculo_fin;
               v_n_haber_soles_calculo_fin := 0;
            ELSE
               v_n_haber_soles_calculo_fin :=
                  v_n_haber_soles_calculo_fin - v_n_debe_soles_calculo_fin;
               v_n_debe_soles_calculo_fin := 0;
            END IF;

            -- calcular saldo fin para soles
            IF (v_n_debe_ext_calc_fin > v_n_haber_ext_calc_fin)
            THEN
               v_n_debe_ext_calc_fin :=
                  v_n_debe_ext_calc_fin - v_n_haber_ext_calc_fin;
               v_n_haber_ext_calc_fin := 0;
            ELSE
               v_n_haber_ext_calc_fin :=
                  v_n_haber_ext_calc_fin - v_n_debe_ext_calc_fin;
               v_n_debe_ext_calc_fin := 0;
            END IF;

            BEGIN
               INSERT
                 INTO CON_LIBROMAYORDETALLE (PERS_EMPRESAMAYOR_N_PK,       --1
                                             CONT_PERIODOMAYOR_N,          --2
                                             CONT_MESMAYOR_N,              --3
                                             PERS_EMPRESACUENTA_N_PK,      --4
                                             CONT_PERIODOCUENTA_N,         --5
                                             CONT_NUMEROCUENTA_V,          --6
                                             LMDE_DEBESOLESSALDO_N,        --7
                                             LMDE_HABERSOLESSALDO_N,       --8
                                             LMDE_DEBEEXTRANJEROSALDO_N,   --9
                                             LMDE_HABEREXTRANJEROSALDO_N, --10
                                             LMDE_DEBESOLES_N,            --11
                                             LMDE_HABERSOLES_N,           --12
                                             LMDE_DEBEEXTRANJERO_N,       --13
                                             LMDE_HABEREXTRANJERO_N,      --14
                                             PERS_EMPRESASUCURSAL_N_PK,   --15
                                             SUCU_IDSUCURSAL_N_PK,        --16
                                             SUDE_IDSUBSUCURSAL_N_PK,     --17
                                             LMDE_DEBESOLESSALDOFIN_N,    --18
                                             LMDE_HABERSOLESSALDOFIN_N,   --19
                                             LMDE_DEBEEXTRANJEROSALDOFIN_N, --20
                                             LMDE_HABEREXTRANJEROSALDOFIN_N, --21
                                             LMDE_NIVELCUENTA_N)
               VALUES (v_pers_empresamayor_n_pk,                           --1
                       v_cont_periodomayor_n,                              --2
                       v_cont_mesmayor_n,                                  --3
                       listRecMayorDetalle.PERS_EMPRESACUENTA_N_PK,        --4
                       listRecMayorDetalle.CONT_PERIODOCUENTA_N,           --5
                       listRecMayorDetalle.CONT_NUMEROCUENTA_V,            --6
                       v_n_debe_soles_calculo_sal,                         --7
                       v_n_haber_soles_calculo_sal,                        --8
                       v_n_debe_ext_calc_sal,                              --9
                       v_n_haber_ext_calc_sal,                            --10
                       v_n_debe_soles_calculo,                            --11
                       v_n_haber_soles_calculo,                           --12
                       v_n_debe_extranjera_calculo,                       --13
                       v_n_haber_extranjera_calculo,                      --14
                       listRecMayorDetalle.PERS_EMPRESASUCURSAL_N_PK,     --15
                       listRecMayorDetalle.SUCU_IDSUCURSAL_N_PK,          --16
                       NVL (listRecMayorDetalle.SUDE_IDSUBSUCURSAL_N_PK, 0), --17
                       v_n_debe_soles_calculo_fin,                        --18
                       v_n_haber_soles_calculo_fin,                       --19
                       v_n_debe_ext_calc_fin,                             --20
                       v_n_haber_ext_calc_fin,                            --21
                       v_n_nivel_cuenta + 1);
            EXCEPTION
               WHEN DUP_VAL_ON_INDEX
               THEN
                  UPDATE CON_LIBROMAYORDETALLE
                     SET LMDE_DEBESOLESSALDOFIN_N =
                            (CASE
                                WHEN ( (LMDE_HABERSOLESSALDOFIN_N
                                        + listRecMayorDetalle.LMDE_HABERSOLESSALDOFIN_N) >
                                         (NVL (
                                             listRecMayorDetalle.LMDE_DEBESOLESSALDOFIN_N,
                                             0)
                                          + LMDE_DEBESOLESSALDOFIN_N))
                                THEN
                                   0
                                ELSE
                                   (NVL (
                                       listRecMayorDetalle.LMDE_DEBESOLESSALDOFIN_N,
                                       0)
                                    + LMDE_DEBESOLESSALDOFIN_N)
                                   - (LMDE_HABERSOLESSALDOFIN_N
                                      + listRecMayorDetalle.LMDE_HABERSOLESSALDOFIN_N)
                             END),
                         LMDE_HABERSOLESSALDOFIN_N =
                            (CASE
                                WHEN ( (LMDE_DEBESOLESSALDOFIN_N
                                        + listRecMayorDetalle.LMDE_DEBESOLESSALDOFIN_N) >
                                         (NVL (
                                             listRecMayorDetalle.LMDE_HABERSOLESSALDOFIN_N,
                                             0)
                                          + LMDE_HABERSOLESSALDOFIN_N))
                                THEN
                                   0
                                ELSE
                                   NVL (
                                      listRecMayorDetalle.LMDE_HABERSOLESSALDOFIN_N,
                                      0)
                                   + LMDE_HABERSOLESSALDOFIN_N
                                   - (LMDE_DEBESOLESSALDOFIN_N
                                      + listRecMayorDetalle.LMDE_DEBESOLESSALDOFIN_N)
                             END),
                         LMDE_DEBEEXTRANJEROSALDOFIN_N =
                            (CASE
                                WHEN ( (LMDE_HABEREXTRANJEROSALDOFIN_N
                                        + listRecMayorDetalle.LMDE_HABEREXTRANJEROSALDOFIN_N) >
                                         (NVL (
                                             listRecMayorDetalle.LMDE_DEBEEXTRANJEROSALDOFIN_N,
                                             0)
                                          + LMDE_DEBEEXTRANJEROSALDOFIN_N))
                                THEN
                                   0
                                ELSE
                                   (NVL (
                                       listRecMayorDetalle.LMDE_DEBEEXTRANJEROSALDOFIN_N,
                                       0)
                                    + LMDE_DEBEEXTRANJEROSALDOFIN_N)
                                   - (LMDE_HABEREXTRANJEROSALDOFIN_N
                                      + listRecMayorDetalle.LMDE_HABEREXTRANJEROSALDOFIN_N)
                             END),
                         LMDE_HABEREXTRANJEROSALDOFIN_N =
                            (CASE
                                WHEN ( (LMDE_DEBEEXTRANJEROSALDOFIN_N
                                        + listRecMayorDetalle.LMDE_DEBEEXTRANJEROSALDOFIN_N) >
                                         (NVL (
                                             listRecMayorDetalle.LMDE_HABEREXTRANJEROSALDOFIN_N,
                                             0)
                                          + LMDE_HABEREXTRANJEROSALDOFIN_N))
                                THEN
                                   0
                                ELSE
                                   NVL (
                                      listRecMayorDetalle.LMDE_HABEREXTRANJEROSALDOFIN_N,
                                      0)
                                   + LMDE_HABEREXTRANJEROSALDOFIN_N
                                   - (LMDE_DEBEEXTRANJEROSALDOFIN_N
                                      + listRecMayorDetalle.LMDE_DEBEEXTRANJEROSALDOFIN_N)
                             END),
                         LMDE_DEBESOLESSALDO_N =
                            (CASE
                                WHEN ( (LMDE_HABERSOLESSALDO_N
                                        + listRecMayorDetalle.LMDE_HABERSOLESSALDO_N) >
                                         (NVL (
                                             listRecMayorDetalle.LMDE_DEBESOLESSALDO_N,
                                             0)
                                          + LMDE_DEBESOLESSALDO_N))
                                THEN
                                   0
                                ELSE
                                   (NVL (
                                       listRecMayorDetalle.LMDE_DEBESOLESSALDO_N,
                                       0)
                                    + LMDE_DEBESOLESSALDO_N)
                                   - (LMDE_HABERSOLESSALDO_N
                                      + listRecMayorDetalle.LMDE_HABERSOLESSALDO_N)
                             END),
                         LMDE_HABERSOLESSALDO_N =
                            (CASE
                                WHEN ( (LMDE_DEBESOLESSALDO_N
                                        + listRecMayorDetalle.LMDE_DEBESOLESSALDO_N) >
                                         (NVL (
                                             listRecMayorDetalle.LMDE_HABERSOLESSALDO_N,
                                             0)
                                          + LMDE_HABERSOLESSALDO_N))
                                THEN
                                   0
                                ELSE
                                   NVL (
                                      listRecMayorDetalle.LMDE_HABERSOLESSALDO_N,
                                      0)
                                   + LMDE_HABERSOLESSALDO_N
                                   - (LMDE_DEBESOLESSALDO_N
                                      + listRecMayorDetalle.LMDE_DEBESOLESSALDO_N)
                             END),
                         LMDE_DEBEEXTRANJEROSALDO_N =
                            (CASE
                                WHEN ( (LMDE_HABEREXTRANJEROSALDO_N
                                        + listRecMayorDetalle.LMDE_HABEREXTRANJEROSALDO_N) >
                                         (NVL (
                                             listRecMayorDetalle.LMDE_DEBEEXTRANJEROSALDO_N,
                                             0)
                                          + LMDE_DEBEEXTRANJEROSALDO_N))
                                THEN
                                   0
                                ELSE
                                   (NVL (
                                       listRecMayorDetalle.LMDE_DEBEEXTRANJEROSALDO_N,
                                       0)
                                    + LMDE_DEBEEXTRANJEROSALDO_N)
                                   - (LMDE_HABEREXTRANJEROSALDO_N
                                      + listRecMayorDetalle.LMDE_HABEREXTRANJEROSALDO_N)
                             END),
                         LMDE_HABEREXTRANJEROSALDO_N =
                            (CASE
                                WHEN ( (LMDE_DEBEEXTRANJEROSALDO_N
                                        + listRecMayorDetalle.LMDE_DEBEEXTRANJEROSALDO_N) >
                                         (NVL (
                                             listRecMayorDetalle.LMDE_HABEREXTRANJEROSALDO_N,
                                             0)
                                          + LMDE_HABEREXTRANJEROSALDO_N))
                                THEN
                                   0
                                ELSE
                                   NVL (
                                      listRecMayorDetalle.LMDE_HABEREXTRANJEROSALDO_N,
                                      0)
                                   + LMDE_HABEREXTRANJEROSALDO_N
                                   - (LMDE_DEBEEXTRANJEROSALDO_N
                                      + listRecMayorDetalle.LMDE_DEBEEXTRANJEROSALDO_N)
                             END),
                         LMDE_DEBESOLES_N =
                            (CASE
                                WHEN ( (LMDE_HABERSOLES_N
                                        + listRecMayorDetalle.LMDE_HABERSOLES_N) >
                                         (NVL (
                                             listRecMayorDetalle.LMDE_DEBESOLES_N,
                                             0)
                                          + LMDE_DEBESOLES_N))
                                THEN
                                   0
                                ELSE
                                   (NVL (
                                       listRecMayorDetalle.LMDE_DEBESOLES_N,
                                       0)
                                    + LMDE_DEBESOLES_N)
                                   - (LMDE_HABERSOLES_N
                                      + listRecMayorDetalle.LMDE_HABERSOLES_N)
                             END),
                         LMDE_HABERSOLES_N =
                            (CASE
                                WHEN ( (LMDE_DEBESOLES_N
                                        + listRecMayorDetalle.LMDE_DEBESOLES_N) >
                                         (NVL (
                                             listRecMayorDetalle.LMDE_HABERSOLES_N,
                                             0)
                                          + LMDE_HABERSOLES_N))
                                THEN
                                   0
                                ELSE
                                   NVL (
                                      listRecMayorDetalle.LMDE_HABERSOLES_N,
                                      0)
                                   + LMDE_HABERSOLES_N
                                   - (LMDE_DEBESOLES_N
                                      + listRecMayorDetalle.LMDE_DEBESOLES_N)
                             END),
                         LMDE_DEBEEXTRANJERO_N =
                            (CASE
                                WHEN ( (LMDE_HABEREXTRANJERO_N
                                        + listRecMayorDetalle.LMDE_HABEREXTRANJERO_N) >
                                         (NVL (
                                             listRecMayorDetalle.LMDE_DEBEEXTRANJERO_N,
                                             0)
                                          + LMDE_DEBEEXTRANJERO_N))
                                THEN
                                   0
                                ELSE
                                   (NVL (
                                       listRecMayorDetalle.LMDE_DEBEEXTRANJERO_N,
                                       0)
                                    + LMDE_DEBEEXTRANJERO_N)
                                   - (LMDE_HABEREXTRANJERO_N
                                      + listRecMayorDetalle.LMDE_HABEREXTRANJERO_N)
                             END),
                         LMDE_HABEREXTRANJERO_N =
                            (CASE
                                WHEN ( (LMDE_DEBEEXTRANJERO_N
                                        + listRecMayorDetalle.LMDE_DEBEEXTRANJERO_N) >
                                         (NVL (
                                             listRecMayorDetalle.LMDE_HABEREXTRANJERO_N,
                                             0)
                                          + LMDE_HABEREXTRANJERO_N))
                                THEN
                                   0
                                ELSE
                                   NVL (
                                      listRecMayorDetalle.LMDE_HABEREXTRANJERO_N,
                                      0)
                                   + LMDE_HABEREXTRANJERO_N
                                   - (LMDE_DEBEEXTRANJERO_N
                                      + listRecMayorDetalle.LMDE_DEBEEXTRANJERO_N)
                             END)
                   WHERE PERS_EMPRESAMAYOR_N_PK = v_pers_empresamayor_n_pk
                         AND CONT_PERIODOMAYOR_N = v_cont_periodomayor_n
                         AND CONT_MESMAYOR_N = v_cont_mesmayor_n
                         AND PERS_EMPRESACUENTA_N_PK =
                                listRecMayorDetalle.PERS_EMPRESACUENTA_N_PK
                         AND CONT_PERIODOCUENTA_N =
                                listRecMayorDetalle.CONT_PERIODOCUENTA_N
                         AND CONT_NUMEROCUENTA_V =
                                listRecMayorDetalle.CONT_NUMEROCUENTA_V;
            END;
         END LOOP;

         v_n_nivel_cuenta := v_n_nivel_cuenta + 1;
         EXIT WHEN v_n_sz_cuenta_nv < v_n_nivel_cuenta;
      END LOOP;

      V_PROC_MAYOR_RETURN := 1;
   EXCEPTION
      WHEN OTHERS
      THEN
         DELETE FROM CON_LIBROMAYORDETALLE
               WHERE     PERS_EMPRESAMAYOR_N_PK = v_pers_empresamayor_n_pk
                     AND CONT_PERIODOMAYOR_N = v_cont_periodomayor_n
                     AND CONT_MESMAYOR_N = v_cont_mesmayor_n;

         V_PROC_MAYOR_RETURN := 0;
   END processMayorizacion;
END PKG_LIBROMAYOR;
/