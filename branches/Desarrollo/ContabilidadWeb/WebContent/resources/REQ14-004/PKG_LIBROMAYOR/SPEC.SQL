/* Formatted on 16/09/2014 11:46:07 p.m. (QP5 v5.163.1008.3004) */
CREATE OR REPLACE PACKAGE CONTABILIDAD.PKG_LIBROMAYOR
AS
   TYPE cursorLista IS REF CURSOR;

   PROCEDURE grabar (
      v_pers_empresamayor_n_pk     IN OUT CON_LIBROMAYOR.PERS_EMPRESAMAYOR_N_PK%TYPE,
      v_cont_periodomayor_n        IN OUT CON_LIBROMAYOR.CONT_PERIODOMAYOR_N%TYPE,
      v_cont_mesmayor_n            IN OUT CON_LIBROMAYOR.CONT_MESMAYOR_N%TYPE,
      v_lima_fecharegistro_d       IN OUT CON_LIBROMAYOR.LIMA_FECHAREGISTRO_D%TYPE,
      v_para_estadocierre_n_cod    IN OUT CON_LIBROMAYOR.PARA_ESTADOCIERRE_N_COD%TYPE,
      v_pers_empresausuario_n_pk   IN OUT CON_LIBROMAYOR.PERS_EMPRESAUSUARIO_N_PK%TYPE,
      V_pers_personausuario_n_pk   IN OUT CON_LIBROMAYOR.PERS_PERSONAUSUARIO_N_PK%TYPE);

   PROCEDURE modificar (
      v_pers_empresamayor_n_pk     IN OUT CON_LIBROMAYOR.PERS_EMPRESAMAYOR_N_PK%TYPE,
      v_cont_periodomayor_n        IN OUT CON_LIBROMAYOR.CONT_PERIODOMAYOR_N%TYPE,
      v_cont_mesmayor_n            IN OUT CON_LIBROMAYOR.CONT_MESMAYOR_N%TYPE,
      v_lima_fecharegistro_d       IN OUT CON_LIBROMAYOR.LIMA_FECHAREGISTRO_D%TYPE,
      v_para_estadocierre_n_cod    IN OUT CON_LIBROMAYOR.PARA_ESTADOCIERRE_N_COD%TYPE,
      v_pers_empresausuario_n_pk   IN OUT CON_LIBROMAYOR.PERS_EMPRESAUSUARIO_N_PK%TYPE,
      V_pers_personausuario_n_pk   IN OUT CON_LIBROMAYOR.PERS_PERSONAUSUARIO_N_PK%TYPE);

   PROCEDURE getListaPorPk (
      V_LISTA                       OUT cursorLista,
      v_pers_empresamayor_n_pk   IN     CON_LIBROMAYOR.PERS_EMPRESAMAYOR_N_PK%TYPE,
      v_cont_periodomayor_n      IN     CON_LIBROMAYOR.CONT_PERIODOMAYOR_N%TYPE,
      v_cont_mesmayor_n          IN     CON_LIBROMAYOR.CONT_MESMAYOR_N%TYPE);

   PROCEDURE getListaPorBuscar (
      V_LISTA                        OUT cursorLista,
      v_cont_mesmayor_n           IN     CON_LIBROMAYOR.CONT_MESMAYOR_N%TYPE,
      v_cont_periodomayor_n       IN     CON_LIBROMAYOR.CONT_PERIODOMAYOR_N%TYPE,
      v_para_estadocierre_n_cod   IN     CON_LIBROMAYOR.PARA_ESTADOCIERRE_N_COD%TYPE);

   PROCEDURE processMayorizacion (V_PERIODO_N           IN     NUMBER,
                                  V_LIBROMAYORID_N_PK      OUT NUMBER);
END PKG_LIBROMAYOR;
/