------------LIBRO MAYOR HISTORICO---------------
ALTER TABLE CONTABILIDAD.CON_LIBROMAYOR_HISTORICO
 DROP PRIMARY KEY CASCADE;

DROP TABLE CONTABILIDAD.CON_LIBROMAYOR_HISTORICO CASCADE CONSTRAINTS;

------------LIBRO MAYOR HISTORICO DETALLE---------------
ALTER TABLE CONTABILIDAD.CON_LIBROMAYORDETALLE_HIST
 DROP PRIMARY KEY CASCADE;

DROP TABLE CONTABILIDAD.CON_LIBROMAYORDETALLE_HIST CASCADE CONSTRAINTS;