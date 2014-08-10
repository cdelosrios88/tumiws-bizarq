create or replace 
PACKAGE BODY PKG_SESSION
AS
   PROCEDURE getListaSession (v_LISTA OUT cursorlista)
   IS
      var_lista   cursorlista;
   BEGIN
      OPEN var_lista FOR
         SELECT PERS_EMPRESA_N_PK pPers_empresa_n_pk,
                PERS_PERSONA_N_PK pPers_persona_n_pk,
                SESS_IDSESSION_N_PK pSess_idsession_n_pk,
                SESS_FECHAREGISTRO_D pSess_fecharegistro_d,
                SESS_FECHATERMINO_D pSess_fechatermino_d,
                SUCU_IDSUCURSAL_N pSess_idsucursal_n,
                SESS_ACCESOREMOTO_N pSess_accesoremoto_n,
                SESS_IDESTADOSESSION_N pSess_idestadosession_n,
                SESS_IDWEBSESSION_N pSess_idwebsession_n,
                SESS_SID_N pSess_sid_v,
                SESS_MACADDRESS_V pSess_macaddress_v,
                SESS_INDCABINA_N pSess_indcabina_n
           FROM SEG_V_SESSION;

      v_LISTA := var_lista;
   END getListaSession;

   PROCEDURE getListaPorPk (
      v_LISTA                  OUT cursorlista,
      V_PERS_EMPRESA_N_PK   IN     SEG_V_SESSION.PERS_EMPRESA_N_PK%TYPE,
      V_PERSONA_N_PK        IN     SEG_V_SESSION.PERS_PERSONA_N_PK%TYPE,
      V_SESSION_N_PK        IN     SEG_V_SESSION.SESS_IDSESSION_N_PK%TYPE)
   IS
      var_lista   cursorlista;
   BEGIN
      OPEN var_lista FOR
         SELECT PERS_EMPRESA_N_PK pPers_empresa_n_pk,
                PERS_PERSONA_N_PK pPers_persona_n_pk,
                SESS_IDSESSION_N_PK pSess_idsession_n_pk,
                SESS_FECHAREGISTRO_D pSess_fecharegistro_d,
                SESS_FECHATERMINO_D pSess_fechatermino_d,
                SUCU_IDSUCURSAL_N pSess_idsucursal_n,
                SESS_ACCESOREMOTO_N pSess_accesoremoto_n,
                SESS_IDESTADOSESSION_N pSess_idestadosession_n,
                SESS_IDWEBSESSION_N pSess_idwebsession_n,
                SESS_SID_N pSess_sid_v,
                SESS_MACADDRESS_V pSess_macaddress_v,
                SESS_INDCABINA_N pSess_indcabina_n
           FROM SEG_V_SESSION SES
          WHERE     SES.PERS_EMPRESA_N_PK = V_PERS_EMPRESA_N_PK
                AND SES.PERS_PERSONA_N_PK = V_PERSONA_N_PK
                AND SES.SESS_IDSESSION_N_PK = V_SESSION_N_PK;

      v_LISTA := var_lista;
   END getListaPorPk;

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
      V_INDCABINA_N         IN OUT SEG_V_SESSION.SESS_INDCABINA_N%TYPE)
   IS
   BEGIN
      SELECT SEQ_SEG_V_SESSION.NEXTVAL INTO V_SESSION_N_PK FROM DUAL;

      INSERT INTO SEG_V_SESSION (PERS_EMPRESA_N_PK,
                                 PERS_PERSONA_N_PK,
                                 SESS_IDSESSION_N_PK,
                                 SESS_FECHAREGISTRO_D,
                                 SESS_FECHATERMINO_D,
                                 SUCU_IDSUCURSAL_N,
                                 SESS_ACCESOREMOTO_N,
                                 SESS_IDESTADOSESSION_N,
                                 SESS_IDWEBSESSION_N,
                                 SESS_SID_N,
                                 SESS_MACADDRESS_V,
                                 SESS_INDCABINA_N)
           VALUES (V_PERS_EMPRESA_N_PK,
                   V_PERSONA_N_PK,
                   V_SESSION_N_PK,
                   V_FECHAREGISTRO_D,
                   V_FECHATERMINO_D,
                   V_SUCURSAL_N,
                   V_ACCESO_REMOTO_N,
                   V_ID_ESTADOSESION_N,
                   V_ID_WEBSESSION_N,
                   V_ID_SID_N,
                   V_MACADDRESS_V,
                   V_INDCABINA_N);
   END grabar;

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
      V_INDCABINA_N         IN OUT SEG_V_SESSION.SESS_INDCABINA_N%TYPE)
   IS
   BEGIN
      UPDATE SEG_V_SESSION
         SET SESS_FECHAREGISTRO_D = V_FECHAREGISTRO_D,
             SESS_FECHATERMINO_D = V_FECHATERMINO_D,
             SUCU_IDSUCURSAL_N = V_SUCURSAL_N,
             SESS_ACCESOREMOTO_N = V_ACCESO_REMOTO_N,
             SESS_IDESTADOSESSION_N = V_ID_ESTADOSESION_N,
             SESS_IDWEBSESSION_N = V_ID_WEBSESSION_N,
             SESS_SID_N = V_ID_SID_N,
             SESS_MACADDRESS_V = V_MACADDRESS_V,
             SESS_INDCABINA_N = V_INDCABINA_N
       WHERE     PERS_EMPRESA_N_PK = V_PERS_EMPRESA_N_PK
             AND PERS_PERSONA_N_PK = V_PERSONA_N_PK
             AND SESS_IDSESSION_N_PK = V_SESSION_N_PK;
   END modificar;

   PROCEDURE getValidSessionByUser (
      V_PERSONA_N_PK   IN     SEG_V_SESSION.PERS_PERSONA_N_PK%TYPE,
      V_ACTSESSION_N      OUT NUMBER)
   IS
   BEGIN
      SELECT COUNT (1)
        INTO V_ACTSESSION_N
        FROM SEG_V_SESSION SES
       WHERE                     --SES.PERS_EMPRESA_N_PK = V_PERS_EMPRESA_N_PK
            SES.PERS_PERSONA_N_PK = V_PERSONA_N_PK
             AND SES.SESS_IDESTADOSESSION_N = PKG_CONSTANTE.C_ESTADO_ACTIVO;
   END getValidSessionByUser;

   PROCEDURE getListByUser (
      v_LISTA             OUT cursorlista,
      V_PERSONA_N_PK   IN     SEG_V_SESSION.PERS_PERSONA_N_PK%TYPE)
   IS
      var_lista   cursorlista;
   BEGIN
      OPEN var_lista FOR
         SELECT PERS_EMPRESA_N_PK pPers_empresa_n_pk,
                PERS_PERSONA_N_PK pPers_persona_n_pk,
                SESS_IDSESSION_N_PK pSess_idsession_n_pk,
                SESS_FECHAREGISTRO_D pSess_fecharegistro_d,
                SESS_FECHATERMINO_D pSess_fechatermino_d,
                SUCU_IDSUCURSAL_N pSess_idsucursal_n,
                SESS_ACCESOREMOTO_N pSess_accesoremoto_n,
                SESS_IDESTADOSESSION_N pSess_idestadosession_n,
                SESS_IDWEBSESSION_N pSess_idwebsession_n,
                SESS_SID_N pSess_sid_v,
                SESS_MACADDRESS_V pSess_macaddress_v,
                SESS_INDCABINA_N pSess_indcabina_n
           FROM SEG_V_SESSION SES
          WHERE SES.PERS_PERSONA_N_PK = V_PERSONA_N_PK
                AND SES.SESS_IDESTADOSESSION_N =
                       PKG_CONSTANTE.C_ESTADO_ACTIVO;

      v_LISTA := var_lista;
   END getListByUser;
   
   -------------------------------------------------------------------------------------------------------------------------
   PROCEDURE getListSessionWeb(
      v_LISTA OUT cursorlista,
      v_pers_empresa_n_pk   IN seg_v_session.pers_empresa_n_pk%TYPE,
      v_sucu_idsucursal_n IN seg_m_sucursal.pers_persona_n_pk%TYPE,
      v_pers_natu_nombres IN persona.per_natural.natu_nombres_v%TYPE,
      v_sess_fecharegistro_d IN seg_v_session.sess_fecharegistro_d%TYPE,
      v_sess_fechatermino_d IN seg_v_session.sess_fechatermino_d%TYPE,
      v_sess_idestadosession_n IN seg_v_session.sess_idestadosession_n%TYPE)
   IS
      var_lista   cursorlista;
   BEGIN
     OPEN var_lista FOR
         select sv.sess_idsession_n_pk,
               sv.pers_persona_n_pk as cod_persona, 
               (pn0.natu_nombres_v ||'-'||pn0.natu_apellidopaterno_v||'-'||pn0.natu_apellidopaterno_v) as tx_nombres,
               DECODE(sv.sess_idestadosession_n,1,'ACTIVO',2,'INACTIVO','NONE') as tx_estado,
               sv.pers_empresa_n_pk,
               pj0.juri_razonsocial_v,
               pj0.juri_siglas_v,
               sc.pers_persona_n_pk,
               pj1.juri_razonsocial_v,
               pj1.juri_siglas_v,
               sv.sess_fecharegistro_d,
               sv.sess_fechatermino_d,
               sv.sess_macaddress_v
        from seg_v_session sv
        left join persona.per_juridica pj0 on sv.pers_empresa_n_pk = pj0.pers_persona_n
        left join persona.per_natural pn0 on sv.pers_persona_n_pk = pn0.pers_persona_n
        left join seg_m_sucursal sc on sc.sucu_idsucursal_n = sv.sucu_idsucursal_n
        left join persona.per_juridica pj1 on sc.pers_persona_n_pk = pj1.pers_persona_n
        where v_pers_empresa_n_pk is null or sv.pers_empresa_n_pk = v_pers_empresa_n_pk
          and v_sucu_idsucursal_n is null or sc.pers_persona_n_pk = v_sucu_idsucursal_n
          and v_pers_natu_nombres is null or upper(pn0.natu_nombres_v ||'-'||pn0.natu_apellidopaterno_v||'-'||pn0.natu_apellidopaterno_v) like '%'||upper(v_pers_natu_nombres)||'%'
          and v_sess_fecharegistro_d is null or sv.sess_fecharegistro_d <= v_sess_fecharegistro_d
          and v_sess_fechatermino_d is null or sv.sess_fechatermino_d <= v_sess_fechatermino_d
          and v_sess_idestadosession_n is null or sv.sess_idestadosession_n <= v_sess_idestadosession_n
        order by 1 asc;
      v_LISTA := var_lista;
   END getListSessionWeb;
  
  PROCEDURE getListBlockDB(
      v_LISTA OUT cursorlista,
      v_schema IN VARCHAR2,
      v_program IN VARCHAR2,
      v_object IN VARCHAR2
  )
  IS
      var_lista   cursorlista;
   BEGIN
    OPEN var_lista FOR
        SELECT s.SID,
          s.SERIAL#,
          S.OSUSER PC_USER,
          S.USERNAME BD_USER,
          DECODE(L.TYPE,'TM','TABLE','TX','RECORDS') TYPE_LOCK,
          S.PROCESS PROCESS_LOCKER,
          s.schemaname,
          O.OBJECT_NAME OBJECT_NAME,
          O.OBJECT_TYPE OBJECT_TYPE,
          DECODE(CONCAT('',S.PROGRAM),'JDBC Thin Client','ERP TUMI',CONCAT('',S.PROGRAM)) PROGRAM,
          O.OWNER OWNER,
          vs.sql_text
        FROM v$lock l,
          dba_objects o,
          v$session s,
          v$sqlarea vs
        WHERE l.ID1 = o.OBJECT_ID
        AND s.SID   =l.SID
        AND l.TYPE IN ('TM','TX')
        AND s.sql_id=vs.sql_id
        AND (v_schema is null or s.schemaname like '%'||v_schema||'%')
        AND (v_program is null or (DECODE(CONCAT('',S.PROGRAM),'JDBC Thin Client','ERP TUMI',CONCAT('',S.PROGRAM))) like '%'||v_program||'%')
        AND (v_object is null or (O.OBJECT_NAME) like '%'||v_object||'%');
      v_LISTA := var_lista;
   END getListBlockDB;
  
  PROCEDURE getListSessionDB(
      v_LISTA OUT cursorlista,
      v_schema IN VARCHAR2,
      v_program IN VARCHAR2
  )
  IS
      var_lista   cursorlista;
   BEGIN
     OPEN var_lista FOR
         SELECT S.SID,
          S.SERIAL#  AS SERIAL,
          S.OSUSER   AS PC_USER,
          S.USERNAME AS BD_USER,
          DECODE(CONCAT('',S.PROGRAM),'JDBC Thin Client','ERP TUMI',CONCAT('',S.PROGRAM)) PROGRAM,
          S.PROCESS PROCESS_LOCKER,
          S.MACHINE MAQUINA,
          s.schemaname,
          S.SQL_ID,
          VS.SQL_TEXT
        FROM V$SESSION S
        left join  V$SQLAREA VS on S.SQL_ID=VS.SQL_ID
        WHERE S.OSUSER <> 'SYSTEM'
        AND S.OSUSER <> 'SYS'
        AND (v_schema is null or s.schemaname like '%'||v_schema||'%')
        AND (v_program is null or DECODE(CONCAT('',S.PROGRAM),'JDBC Thin Client','ERP TUMI',CONCAT('',S.PROGRAM)) like '%'||v_program||'%')
        --AND S.SQL_ID is not null
        order by S.OSUSER asc;
      v_LISTA := var_lista;
   END getListSessionDB;
  -------------------------------------------------------------------------------------------------------------------------
END PKG_SESSION;