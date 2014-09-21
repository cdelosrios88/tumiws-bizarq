CREATE OR REPLACE PACKAGE BODY CONTABILIDAD.PKG_LIBROMAYOR
AS

PROCEDURE grabar (
   v_pers_empresamayor_n_pk           IN OUT CON_LIBROMAYOR.PERS_EMPRESAMAYOR_N_PK%TYPE,
   v_cont_periodomayor_n              IN OUT CON_LIBROMAYOR.CONT_PERIODOMAYOR_N%TYPE,
   v_cont_mesmayor_n                  IN OUT CON_LIBROMAYOR.CONT_MESMAYOR_N%TYPE,
   v_lima_fecharegistro_d             IN OUT CON_LIBROMAYOR.LIMA_FECHAREGISTRO_D%TYPE,
   v_para_estadocierre_n_cod          IN OUT CON_LIBROMAYOR.PARA_ESTADOCIERRE_N_COD%TYPE,
   v_pers_empresausuario_n_pk         IN OUT CON_LIBROMAYOR.PERS_EMPRESAUSUARIO_N_PK%TYPE,
   V_pers_personausuario_n_pk         IN OUT CON_LIBROMAYOR.PERS_PERSONAUSUARIO_N_PK%TYPE
   )
IS
BEGIN
  insert into CON_LIBROMAYOR
    (PERS_EMPRESAMAYOR_N_PK,     CONT_PERIODOMAYOR_N,
     CONT_MESMAYOR_N,            LIMA_FECHAREGISTRO_D,
     PARA_ESTADOCIERRE_N_COD,    PERS_EMPRESAUSUARIO_N_PK,
     PERS_PERSONAUSUARIO_N_PK)
  values
    (v_pers_empresamayor_n_pk,   v_cont_periodomayor_n,
     v_cont_mesmayor_n,          v_lima_fecharegistro_d,
     v_para_estadocierre_n_cod,  v_pers_empresausuario_n_pk,
     V_pers_personausuario_n_pk);
END grabar;



PROCEDURE modificar (
   v_pers_empresamayor_n_pk           IN OUT CON_LIBROMAYOR.PERS_EMPRESAMAYOR_N_PK%TYPE,
   v_cont_periodomayor_n              IN OUT CON_LIBROMAYOR.CONT_PERIODOMAYOR_N%TYPE,
   v_cont_mesmayor_n                  IN OUT CON_LIBROMAYOR.CONT_MESMAYOR_N%TYPE,
   v_lima_fecharegistro_d             IN OUT CON_LIBROMAYOR.LIMA_FECHAREGISTRO_D%TYPE,
   v_para_estadocierre_n_cod          IN OUT CON_LIBROMAYOR.PARA_ESTADOCIERRE_N_COD%TYPE,
   v_pers_empresausuario_n_pk         IN OUT CON_LIBROMAYOR.PERS_EMPRESAUSUARIO_N_PK%TYPE,
   V_pers_personausuario_n_pk         IN OUT CON_LIBROMAYOR.PERS_PERSONAUSUARIO_N_PK%TYPE
   )
IS
BEGIN

  update
         CON_LIBROMAYOR
     set
         LIMA_FECHAREGISTRO_D         = v_lima_fecharegistro_d,
         PARA_ESTADOCIERRE_N_COD      = v_para_estadocierre_n_cod,
         PERS_EMPRESAUSUARIO_N_PK     = v_pers_empresausuario_n_pk,
         PERS_PERSONAUSUARIO_N_PK     = V_pers_personausuario_n_pk
   where
         PERS_EMPRESAMAYOR_N_PK       = v_pers_empresamayor_n_pk
     and CONT_PERIODOMAYOR_N          = v_cont_periodomayor_n
     and CONT_MESMAYOR_N              = v_cont_mesmayor_n;
END modificar;


PROCEDURE getListaPorPk (
   V_LISTA                            OUT cursorLista,
   v_pers_empresamayor_n_pk           IN  CON_LIBROMAYOR.PERS_EMPRESAMAYOR_N_PK%TYPE,
   v_cont_periodomayor_n              IN  CON_LIBROMAYOR.CONT_PERIODOMAYOR_N%TYPE,
   v_cont_mesmayor_n                  IN  CON_LIBROMAYOR.CONT_MESMAYOR_N%TYPE
   )
IS
    var_lista   cursorLista;
BEGIN
    OPEN var_lista FOR
    select
           PERS_EMPRESAMAYOR_N_PK     pPersEmpresaMayor,
           CONT_PERIODOMAYOR_N        pContPeriodoMayor,
           CONT_MESMAYOR_N            pContMesMayor,
           LIMA_FECHAREGISTRO_D       pLimaFechaRegistro,
           PARA_ESTADOCIERRE_N_COD    pParaEstadoCierre,
           PERS_EMPRESAUSUARIO_N_PK   pPersEmpresaUsuario,
           PERS_PERSONAUSUARIO_N_PK   pPersPersonaUsuario
      from
           CON_LIBROMAYOR
     where
           PERS_EMPRESAMAYOR_N_PK       = v_pers_empresamayor_n_pk
     and   CONT_PERIODOMAYOR_N          = v_cont_periodomayor_n
     and   CONT_MESMAYOR_N              = v_cont_mesmayor_n;
    V_LISTA := var_lista;
END getListaPorPk;



PROCEDURE getListaPorBuscar (
   V_LISTA                            OUT cursorLista,
   v_cont_mesmayor_n                  IN  CON_LIBROMAYOR.CONT_MESMAYOR_N%TYPE,
   v_cont_periodomayor_n              IN  CON_LIBROMAYOR.CONT_PERIODOMAYOR_N%TYPE,
   v_para_estadocierre_n_cod          IN  CON_LIBROMAYOR.PARA_ESTADOCIERRE_N_COD%TYPE
   )
IS
    var_lista   cursorLista;
BEGIN
    OPEN var_lista FOR
    select
           PERS_EMPRESAMAYOR_N_PK     pPersEmpresaMayor,
           CONT_PERIODOMAYOR_N        pContPeriodoMayor,
           CONT_MESMAYOR_N            pContMesMayor,
           LIMA_FECHAREGISTRO_D       pLimaFechaRegistro,
           PARA_ESTADOCIERRE_N_COD    pParaEstadoCierre,
           PERS_EMPRESAUSUARIO_N_PK   pPersEmpresaUsuario,
           PERS_PERSONAUSUARIO_N_PK   pPersPersonaUsuario
      from
           CON_LIBROMAYOR
     where
           (v_cont_mesmayor_n IS NULL OR CONT_MESMAYOR_N = v_cont_mesmayor_n)
     and   (v_cont_periodomayor_n IS NULL OR CONT_PERIODOMAYOR_N = v_cont_periodomayor_n)
     and   (v_para_estadocierre_n_cod IS NULL OR PARA_ESTADOCIERRE_N_COD = v_para_estadocierre_n_cod);
    V_LISTA := var_lista;
END getListaPorBuscar;

PROCEDURE getListMayorHist (
   V_LISTA                            OUT cursorLista,
   v_cont_mesmayor_n                  IN  CON_LIBROMAYOR.CONT_MESMAYOR_N%TYPE,
   v_cont_periodomayor_n              IN  CON_LIBROMAYOR.CONT_PERIODOMAYOR_N%TYPE,
   v_para_estado_n_cod          IN  CON_LIBROMAYOR.PARA_ESTADO_N_COD%TYPE,
   v_pers_empresamayor_n_pk           IN  CON_LIBROMAYOR.PERS_EMPRESAMAYOR_N_PK%TYPE
   )
IS
    var_lista   cursorLista;
BEGIN
    OPEN var_lista FOR
    SELECT 'CH'                         pTablaDescripcion,
           CL.CONT_MESMAYOR_N           pContMesMayor,
           CL.CONT_PERIODOMAYOR_N       pContPeriodoMayor,
           CL.LIMA_FECHAREGISTRO_D      pLimaFechaRegistro,
           CL.PARA_ESTADO_N_COD         pParaEstadoCod,
           CL.PARA_ESTADOCIERRE_N_COD   pParaEstadoCierre,
           CL.PERS_EMPRESAMAYOR_N_PK    pPersEmpresaMayor,
           CL.PERS_EMPRESAUSUARIO_N_PK  pPersEmpresaUsuario,
           CL.PERS_PERSONAUSUARIO_N_PK  pPersPersonaUsuario
      FROM CON_LIBROMAYOR CL
    UNION
    SELECT 'CHL'                        pTablaDescripcion,
           CHL.CONT_MESMAYOR_N          pContMesMayor,
           CHL.CONT_PERIODOMAYOR_N      pContPeriodoMayor,
           CHL.LIMA_FECHAREGISTRO_D     pLimaFechaRegistro,
           CHL.PARA_ESTADO_N_COD        pParaEstadoCod,
           CHL.PARA_ESTADOCIERRE_N_COD  pParaEstadoCierre,
           CHL.PERS_EMPRESAMAYOR_N_PK   pPersEmpresaMayor,
           CHL.PERS_EMPRESAUSUARIO_N_PK pPersEmpresaUsuario,
           CHL.PERS_PERSONAUSUARIO_N_PK pPersPersonaUsuario
      FROM CON_LIBROMAYOR_HISTORICO CHL;
    V_LISTA := var_lista;
END getListMayorHist;

END PKG_LIBROMAYOR;
/