/* Formatted on 16/09/2014 11:45:54 p.m. (QP5 v5.163.1008.3004) */
CREATE OR REPLACE PACKAGE BODY CONTABILIDAD.PKG_LIBROMAYOR
AS
   PROCEDURE grabar (
      v_pers_empresamayor_n_pk     IN OUT CON_LIBROMAYOR.PERS_EMPRESAMAYOR_N_PK%TYPE,
      v_cont_periodomayor_n        IN OUT CON_LIBROMAYOR.CONT_PERIODOMAYOR_N%TYPE,
      v_cont_mesmayor_n            IN OUT CON_LIBROMAYOR.CONT_MESMAYOR_N%TYPE,
      v_lima_fecharegistro_d       IN OUT CON_LIBROMAYOR.LIMA_FECHAREGISTRO_D%TYPE,
      v_para_estadocierre_n_cod    IN OUT CON_LIBROMAYOR.PARA_ESTADOCIERRE_N_COD%TYPE,
      v_pers_empresausuario_n_pk   IN OUT CON_LIBROMAYOR.PERS_EMPRESAUSUARIO_N_PK%TYPE,
      V_pers_personausuario_n_pk   IN OUT CON_LIBROMAYOR.PERS_PERSONAUSUARIO_N_PK%TYPE)
   IS
   BEGIN
      INSERT INTO CON_LIBROMAYOR (PERS_EMPRESAMAYOR_N_PK,
                                  CONT_PERIODOMAYOR_N,
                                  CONT_MESMAYOR_N,
                                  LIMA_FECHAREGISTRO_D,
                                  PARA_ESTADOCIERRE_N_COD,
                                  PERS_EMPRESAUSUARIO_N_PK,
                                  PERS_PERSONAUSUARIO_N_PK)
           VALUES (v_pers_empresamayor_n_pk,
                   v_cont_periodomayor_n,
                   v_cont_mesmayor_n,
                   v_lima_fecharegistro_d,
                   v_para_estadocierre_n_cod,
                   v_pers_empresausuario_n_pk,
                   V_pers_personausuario_n_pk);
   END grabar;



   PROCEDURE modificar (
      v_pers_empresamayor_n_pk     IN OUT CON_LIBROMAYOR.PERS_EMPRESAMAYOR_N_PK%TYPE,
      v_cont_periodomayor_n        IN OUT CON_LIBROMAYOR.CONT_PERIODOMAYOR_N%TYPE,
      v_cont_mesmayor_n            IN OUT CON_LIBROMAYOR.CONT_MESMAYOR_N%TYPE,
      v_lima_fecharegistro_d       IN OUT CON_LIBROMAYOR.LIMA_FECHAREGISTRO_D%TYPE,
      v_para_estadocierre_n_cod    IN OUT CON_LIBROMAYOR.PARA_ESTADOCIERRE_N_COD%TYPE,
      v_pers_empresausuario_n_pk   IN OUT CON_LIBROMAYOR.PERS_EMPRESAUSUARIO_N_PK%TYPE,
      V_pers_personausuario_n_pk   IN OUT CON_LIBROMAYOR.PERS_PERSONAUSUARIO_N_PK%TYPE)
   IS
   BEGIN
      UPDATE CON_LIBROMAYOR
         SET LIMA_FECHAREGISTRO_D = v_lima_fecharegistro_d,
             PARA_ESTADOCIERRE_N_COD = v_para_estadocierre_n_cod,
             PERS_EMPRESAUSUARIO_N_PK = v_pers_empresausuario_n_pk,
             PERS_PERSONAUSUARIO_N_PK = V_pers_personausuario_n_pk
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
                PERS_PERSONAUSUARIO_N_PK pPersPersonaUsuario
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
                PERS_PERSONAUSUARIO_N_PK pPersPersonaUsuario
           FROM CON_LIBROMAYOR
          WHERE (v_cont_mesmayor_n IS NULL
                 OR CONT_MESMAYOR_N = v_cont_mesmayor_n)
                AND (v_cont_periodomayor_n IS NULL
                     OR CONT_PERIODOMAYOR_N = v_cont_periodomayor_n)
                AND (v_para_estadocierre_n_cod IS NULL
                     OR PARA_ESTADOCIERRE_N_COD = v_para_estadocierre_n_cod);

      V_LISTA := var_lista;
   END getListaPorBuscar;

   PROCEDURE processMayorizacion (V_PERIODO_N           IN     NUMBER,
                                  V_LIBROMAYORID_N_PK      OUT NUMBER)
   IS
   BEGIN
      --Finalmente insert en LIBROMAYOR
      NULL;
      /*INSERT INTO CON_LIBROMAYOR (PERS_EMPRESAMAYOR_N_PK,
                                  CONT_PERIODOMAYOR_N,
                                  CONT_MESMAYOR_N,
                                  LIMA_FECHAREGISTRO_D,
                                  PARA_ESTADOCIERRE_N_COD,
                                  PERS_EMPRESAUSUARIO_N_PK,
                                  PERS_PERSONAUSUARIO_N_PK)
           VALUES (v_pers_empresamayor_n_pk,
                   v_cont_periodomayor_n,
                   v_cont_mesmayor_n,
                   v_lima_fecharegistro_d,
                   v_para_estadocierre_n_cod,
                   v_pers_empresausuario_n_pk,
                   V_pers_personausuario_n_pk);*/
   END processMayorizacion;
END PKG_LIBROMAYOR;
/