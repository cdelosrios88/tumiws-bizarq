
/*
 * 
 */ 
update SEGURIDAD.SEG_M_PERFIL
  set PERF_HASTA_D = sysdate + 30,
  PERF_IDESTADO_N = 1
  where PERS_EMPRESA_N_PK = 2
  and PERF_IDPERFIL_N in (4, 15, 36);