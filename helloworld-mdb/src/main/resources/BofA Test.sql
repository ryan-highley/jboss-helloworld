
select sys_context('userenv', 'con_name') from dual;

alter session set container=BOFAAPP;
create user bofa_app identified by "Passw0rd";
grant connect, resource to bofa_app;
alter user bofa_app quota unlimited on users;

select pdb_id, pdb_name, status from dba_pdbs;

describe dba_pdbs;
