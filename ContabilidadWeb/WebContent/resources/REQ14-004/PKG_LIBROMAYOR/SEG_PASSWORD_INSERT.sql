--SQL Statement which produced this data:
--
--  SELECT 
--     ROWID, PERS_EMPRESA_N_PK, TRAN_IDTRANSACCION_N, PASS_IDPASSWORD_N, 
--     PASS_DETALLE_V, PASS_CONTRASENA_V, PASS_FECHAREGISTRO_D, 
--     PARA_ESTADO_N_COD, PASS_FECHAELIMINACION_D
--  FROM SEGURIDAD.SEG_PASSWORD
--
Insert into SEGURIDAD.SEG_PASSWORD
   (PERS_EMPRESA_N_PK, 
    TRAN_IDTRANSACCION_N, PASS_IDPASSWORD_N, PASS_DETALLE_V, PASS_CONTRASENA_V, PASS_FECHAREGISTRO_D, 
    PARA_ESTADO_N_COD, PASS_FECHAELIMINACION_D)
 Values
   (2, 143, 4, 'Mayorización', '123456', 
    TO_TIMESTAMP('14/09/2014 12:00:00.000000 AM','DD/MM/YYYY HH12:MI:SS.FF AM'), 1, NULL);
COMMIT;
