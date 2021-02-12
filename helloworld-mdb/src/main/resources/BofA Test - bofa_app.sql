select sys_context('userenv', 'con_name') from dual;


create table received_msg (
  id NUMBER GENERATED AS IDENTITY,
  body varchar2(256),
  correlation_id varchar2(128),
  message_id varchar2(128),
  timestamp number
  );

drop table received_msg;


describe received_msg;


select * from received_msg order by ID desc;
