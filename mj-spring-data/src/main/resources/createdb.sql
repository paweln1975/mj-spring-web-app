CREATE DATABASE mj_prod;
CREATE USER 'mj_prod_user'@'%' IDENTIFIED BY 'Mj1234!.';
use mj_prod;
GRANT SELECT ON mj_prod.* to 'mj_prod_user'@'%';
GRANT INSERT ON mj_prod.* to 'mj_prod_user'@'%';
GRANT UPDATE ON mj_prod.* to 'mj_prod_user'@'%';
GRANT DELETE ON mj_prod.* to 'mj_prod_user'@'%';
commit;