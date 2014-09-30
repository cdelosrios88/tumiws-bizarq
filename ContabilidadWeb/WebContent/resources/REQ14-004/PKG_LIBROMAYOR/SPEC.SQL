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
      V_pers_personausuario_n_pk   IN OUT CON_LIBROMAYOR.PERS_PERSONAUSUARIO_N_PK%TYPE,
      V_PARA_ESTADO_N_COD          IN OUT CON_LIBROMAYOR.PARA_ESTADO_N_COD%TYPE);

   PROCEDURE modificar (
      v_pers_empresamayor_n_pk     IN OUT CON_LIBROMAYOR.PERS_EMPRESAMAYOR_N_PK%TYPE,
      v_cont_periodomayor_n        IN OUT CON_LIBROMAYOR.CONT_PERIODOMAYOR_N%TYPE,
      v_cont_mesmayor_n            IN OUT CON_LIBROMAYOR.CONT_MESMAYOR_N%TYPE,
      v_lima_fecharegistro_d       IN OUT CON_LIBROMAYOR.LIMA_FECHAREGISTRO_D%TYPE,
      v_para_estadocierre_n_cod    IN OUT CON_LIBROMAYOR.PARA_ESTADOCIERRE_N_COD%TYPE,
      v_pers_empresausuario_n_pk   IN OUT CON_LIBROMAYOR.PERS_EMPRESAUSUARIO_N_PK%TYPE,
      V_pers_personausuario_n_pk   IN OUT CON_LIBROMAYOR.PERS_PERSONAUSUARIO_N_PK%TYPE,
      V_PARA_ESTADO_N_COD          IN OUT CON_LIBROMAYOR.PARA_ESTADO_N_COD%TYPE);

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

   PROCEDURE getListMayorHist (
      V_LISTA                       OUT cursorLista,
      v_cont_mesmayor_n          IN     CON_LIBROMAYOR.CONT_MESMAYOR_N%TYPE,
      v_cont_periodomayor_n      IN     CON_LIBROMAYOR.CONT_PERIODOMAYOR_N%TYPE,
      v_para_estado_n_cod        IN     CON_LIBROMAYOR.PARA_ESTADO_N_COD%TYPE,
      v_pers_empresamayor_n_pk   IN     CON_LIBROMAYOR.PERS_EMPRESAMAYOR_N_PK%TYPE);

   PROCEDURE getAfterProcessedByPeriod (
      V_LISTA                    OUT cursorLista,
      v_cont_mesmayor_n       IN     CON_LIBROMAYOR.CONT_MESMAYOR_N%TYPE,
      v_cont_periodomayor_n   IN     CON_LIBROMAYOR.CONT_PERIODOMAYOR_N%TYPE,
      v_para_estado_n_cod     IN     CON_LIBROMAYOR.PARA_ESTADO_N_COD%TYPE);

   PROCEDURE deleteMayorizado (
      v_pers_empresamayor_n_pk     IN OUT CON_LIBROMAYOR.PERS_EMPRESAMAYOR_N_PK%TYPE,
      v_cont_periodomayor_n        IN OUT CON_LIBROMAYOR.CONT_PERIODOMAYOR_N%TYPE,
      v_cont_mesmayor_n            IN OUT CON_LIBROMAYOR.CONT_MESMAYOR_N%TYPE,
      v_lima_fecharegistro_d       IN OUT CON_LIBROMAYOR.LIMA_FECHAREGISTRO_D%TYPE,
      v_para_estadocierre_n_cod    IN OUT CON_LIBROMAYOR.PARA_ESTADOCIERRE_N_COD%TYPE,
      v_pers_empresausuario_n_pk   IN OUT CON_LIBROMAYOR.PERS_EMPRESAUSUARIO_N_PK%TYPE,
      V_pers_personausuario_n_pk   IN OUT CON_LIBROMAYOR.PERS_PERSONAUSUARIO_N_PK%TYPE,
      V_PARA_ESTADO_N_COD          IN OUT CON_LIBROMAYOR.PARA_ESTADO_N_COD%TYPE,
      V_IPUSUARIOELIMINA_HIST_N    IN OUT CON_LIBROMAYOR_HISTORICO.CONT_IP_ELIMINA_HIST_D%TYPE);

   PROCEDURE getValidAccounts (
      V_ERROR_LIST                  OUT VARCHAR2,
      v_cont_mesmayor_n          IN     CON_LIBROMAYOR.CONT_MESMAYOR_N%TYPE,
      v_cont_periodomayor_n      IN     CON_LIBROMAYOR.CONT_PERIODOMAYOR_N%TYPE,
      v_para_estado_n_cod        IN     CON_LIBROMAYOR.PARA_ESTADO_N_COD%TYPE,
      v_pers_empresamayor_n_pk   IN     CON_LIBROMAYOR.PERS_EMPRESAMAYOR_N_PK%TYPE);

   PROCEDURE processMayorizacion (
      V_PROC_MAYOR_RETURN             OUT NUMBER,
      v_cont_mesmayor_n            IN     CON_LIBROMAYOR.CONT_MESMAYOR_N%TYPE,
      v_cont_periodomayor_n        IN     CON_LIBROMAYOR.CONT_PERIODOMAYOR_N%TYPE,
      v_pers_empresamayor_n_pk     IN     CON_LIBROMAYOR.PERS_EMPRESAMAYOR_N_PK%TYPE,
      v_pers_empresauario_n_pk     IN     CON_LIBROMAYOR.PERS_EMPRESAUSUARIO_N_PK%TYPE,
      v_pers_personausuario_n_pk   IN     CON_LIBROMAYOR.PERS_PERSONAUSUARIO_N_PK%TYPE,
      v_cont_periodomayor_v        IN     VARCHAR2);
END PKG_LIBROMAYOR;
/