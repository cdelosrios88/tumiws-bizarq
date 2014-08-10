create or replace 
PACKAGE           PKG_SESSION
AS
   TYPE cursorlista IS REF CURSOR;

   PROCEDURE getListaSession (v_LISTA OUT cursorlista);

   PROCEDURE getListaPorPk (
      v_LISTA                  OUT cursorlista,
      V_PERS_EMPRESA_N_PK   IN     SEG_V_SESSION.PERS_EMPRESA_N_PK%TYPE,
      V_PERSONA_N_PK        IN     SEG_V_SESSION.PERS_PERSONA_N_PK%TYPE,
      V_SESSION_N_PK        IN     SEG_V_SESSION.SESS_IDSESSION_N_PK%TYPE);

   PROCEDURE grabar (
      V_PERS_EMPRESA_N_PK   IN OUT SEG_V_SESSION.PERS_EMPRESA_N_PK%TYPE,
      V_PERSONA_N_PK        IN OUT SEG_V_SESSION.PERS_PERSONA_N_PK%TYPE,
      V_SESSION_N_PK        IN OUT SEG_V_SESSION.SESS_IDSESSION_N_PK%TYPE,
      V_FECHAREGISTRO_D     IN OUT SEG_V_SESSION.SESS_FECHAREGISTRO_D%TYPE,
      V_FECHATERMINO_D      IN OUT SEG_V_SESSION.SESS_FECHATERMINO_D%TYPE,
      V_SUCURSAL_N          IN OUT SEG_V_SESSION.SUCU_IDSUCURSAL_N%TYPE,
      V_ACCESO_REMOTO_N     IN OUT SEG_V_SESSION.SESS_ACCESOREMOTO_N%TYPE,
      V_ID_ESTADOSESION_N   IN OUT SEG_V_SESSION.SESS_IDESTADOSESSION_N%TYPE,
      V_ID_WEBSESSION_N     IN OUT SEG_V_SESSION.SESS_IDWEBSESSION_N%TYPE,
      V_ID_SID_N            IN OUT SEG_V_SESSION.SESS_SID_N%TYPE,
      V_MACADDRESS_V        IN OUT SEG_V_SESSION.SESS_MACADDRESS_V%TYPE,
      V_INDCABINA_N         IN OUT SEG_V_SESSION.SESS_INDCABINA_N%TYPE);

   PROCEDURE modificar (
      V_PERS_EMPRESA_N_PK   IN OUT SEG_V_SESSION.PERS_EMPRESA_N_PK%TYPE,
      V_PERSONA_N_PK        IN OUT SEG_V_SESSION.PERS_PERSONA_N_PK%TYPE,
      V_SESSION_N_PK        IN OUT SEG_V_SESSION.SESS_IDSESSION_N_PK%TYPE,
      V_FECHAREGISTRO_D     IN OUT SEG_V_SESSION.SESS_FECHAREGISTRO_D%TYPE,
      V_FECHATERMINO_D      IN OUT SEG_V_SESSION.SESS_FECHATERMINO_D%TYPE,
      V_SUCURSAL_N          IN OUT SEG_V_SESSION.SUCU_IDSUCURSAL_N%TYPE,
      V_ACCESO_REMOTO_N     IN OUT SEG_V_SESSION.SESS_ACCESOREMOTO_N%TYPE,
      V_ID_ESTADOSESION_N   IN OUT SEG_V_SESSION.SESS_IDESTADOSESSION_N%TYPE,
      V_ID_WEBSESSION_N     IN OUT SEG_V_SESSION.SESS_IDWEBSESSION_N%TYPE,
      V_ID_SID_N            IN OUT SEG_V_SESSION.SESS_SID_N%TYPE,
      V_MACADDRESS_V        IN OUT SEG_V_SESSION.SESS_MACADDRESS_V%TYPE,
      V_INDCABINA_N         IN OUT SEG_V_SESSION.SESS_INDCABINA_N%TYPE);

   PROCEDURE getValidSessionByUser (
      V_PERSONA_N_PK   IN     SEG_V_SESSION.PERS_PERSONA_N_PK%TYPE,
      V_ACTSESSION_N      OUT NUMBER);

   PROCEDURE getListByUser (
      v_LISTA             OUT cursorlista,
      V_PERSONA_N_PK   IN     SEG_V_SESSION.PERS_PERSONA_N_PK%TYPE);
      
  PROCEDURE getListSessionWeb(
      v_LISTA OUT cursorlista,
      v_pers_empresa_n_pk   IN seg_v_session.pers_empresa_n_pk%TYPE,
      v_sucu_idsucursal_n IN seg_m_sucursal.pers_persona_n_pk%TYPE,
      v_pers_natu_nombres IN persona.per_natural.natu_nombres_v%TYPE,
      v_sess_fecharegistro_d IN seg_v_session.sess_fecharegistro_d%TYPE,
      v_sess_fechatermino_d IN seg_v_session.sess_fechatermino_d%TYPE,
      v_sess_idestadosession_n IN seg_v_session.sess_idestadosession_n%TYPE      
  );
  
  PROCEDURE getListBlockDB(
      v_LISTA OUT cursorlista,
      v_schema IN VARCHAR2,
      v_program IN VARCHAR2,
      v_object IN VARCHAR2
  );
  
  PROCEDURE getListSessionDB(
      v_LISTA OUT cursorlista,
      v_schema IN VARCHAR2,
      v_program IN VARCHAR2
  );
      
END PKG_SESSION;