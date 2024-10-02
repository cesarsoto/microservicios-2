SPRIG_BOOT_DATA_JPA:
######################################################################################################################################
Proyecto de clientes y facturación en Spring boot y Java, (no microservicio), carga de imagen (se puede grabar en el disco o en la base de datos) actualmente corre sobre base de datos H2 (in memory)
######################################################################################################################################


CREATE TABLE persona (
id_persona number(3),
nombre varchar2(40),
apellido varchar2(40),
email varchar2(40),
telefono varchar2(40),
primary key (id_persona))

CREATE SEQUENCE persona_seq
  MINVALUE 1
  MAXVALUE 9999999999
  START WITH 1
  INCREMENT BY 1;

@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERS_SEQ")
@SequenceGenerator(sequenceName = "persona_seq", allocationSize = 1, name = "PERS_SEQ")

CREATE TABLE usuario (
id_usuario number(11) not null,
username varchar2(45),
password varchar2(128),
primary key (id_usuario));

CREATE SEQUENCE usuario_seq
  MINVALUE 1
  MAXVALUE 9999999999
  START WITH 1
  INCREMENT BY 1;

@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ")
@SequenceGenerator(sequenceName = "usuario_seq", allocationSize = 1, name = "USER_SEQ")

CREATE TABLE rol (
id_rol number(11) not null,
nombre varchar2(45),
id_usuario number(11) not null,
primary key (id_rol),
Constraint id_rol_usuario
foreign key (id_usuario)
references usuario (id_usuario));

CREATE SEQUENCE rol_seq
  MINVALUE 1
  MAXVALUE 9999999999
  START WITH 1
  INCREMENT BY 1;

@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROLES_SEQ")
@SequenceGenerator(sequenceName = "rol_seq", allocationSize = 1, name = "ROLES_SEQ")

insert into usuario(id_usuario, username, password) 
values (usuario_seq.nextval,'admin','123');

insert into usuario(id_usuario, username, password) 
values (usuario_seq.nextval,'user','123');

insert into rol(id_rol, nombre, id_usuario)
values (rol_seq.nextval, 'ROLE_ADMIN', 1)

insert into rol(id_rol, nombre, id_usuario)
values (rol_seq.nextval, 'ROLE_USER', 2)

insert into rol(id_rol, nombre, id_usuario)
values (rol_seq.nextval, 'ROLE_USER', 2)

update usuario set password = 
'$2a$10$OD8LZoJG4mwnQfVzBaJakexLtzObf6ocmzPtKAKeLKid/075r3Q3a'
where id_usuario = 1;

update usuario set password = 
'$2a$10$buXGID2gAWiN8UKmQejG9eGJSO1KLJkcTvGZ6nYo9/vA2j7G/.8bC'
where id_usuario = 2;

CREATE SEQUENCE clientes_seq
  MINVALUE 1
  MAXVALUE 9999999999
  START WITH 1
  INCREMENT BY 1;


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERS_SEQ")
    @SequenceGenerator(sequenceName = "persona_seq", allocationSize = 1, name = "PERS_SEQ")
    private Long Id_persona;

################################################## CLIENTES - FACTURAS - PRODUCTOS #####################################33

CREATE TABLE clientes (
id number(11) not null,
nombre varchar2(255),
apellido varchar2(255),
email varchar2(255),
create_at date,
primary key (id_persona))

CREATE SEQUENCE clientes_seq
  MINVALUE 1
  MAXVALUE 9999999999
  START WITH 1
  INCREMENT BY 1;

Create table facturas(
id number(11) not null,
descripcion varchar2(255) not null,
observacion varchar2(255),
create_at date,
cliente_id number(11),
constraint facturas_pk primary key (id),
constraint facturas_fk1 foreign key(cliente_id) references clientes (id));

CREATE SEQUENCE facturas_seq
  MINVALUE 1
  MAXVALUE 9999999999
  START WITH 1
  INCREMENT BY 1;

Create table productos(
id number(11) not null,
nombre varchar2(255),
precio number(16,3),
create_at date,
constraint productos_pk primary key(id));

CREATE SEQUENCE productos_seq
  MINVALUE 1
  MAXVALUE 9999999999
  START WITH 1
  INCREMENT BY 1;


create table facturas_items(
id number(11) not null,
cantidad number(11),
producto_id number(11),
factura_id number(11),
constraint facturas_items_pk primary key (id),
constraint facturas_items_fk1 foreign key(producto_id) references productos(id),
constraint facturas_items_fk2 foreign key(factura_id) references facturas(id));

CREATE SEQUENCE facturas_items_seq
  MINVALUE 1
  MAXVALUE 9999999999
  START WITH 1
  INCREMENT BY 1;

################################################### SPRING BOOT SECURITY ############################################

Create table users (
id number(11) not null,     -- Autoincrement
username varchar2(45) not null, 
password varchar2(60) not null,
enabled varchar2(1) default 1 not null ,
constraint users_pk primary key(id),
constraint users_uniq unique(username)

CREATE SEQUENCE users_seq
  MINVALUE 1
  MAXVALUE 9999999999
  START WITH 1
  INCREMENT BY 1;

Create table authorities (
id number(11) not null,      -- Autoincrement
user_id number(11) not null,
authority varchar2(45) not null,
constraint authorities_pk primary key (id),
constraint authorities_users_fk1 foreign key (user_id) references users (id)
on delete cascade);

create unique index auth_userid_uniq on authorities (user_id, authority)

CREATE SEQUENCE authorities_seq
  MINVALUE 1
  MAXVALUE 9999999999
  START WITH 1
  INCREMENT BY 1;

create or replace trigger cascade_update
after update of id on users
for each row
begin
   update authorities
   set user_id = :new.id
   where user_id = :old.id;
end;
/

insert into users (id, username, password, enabled) 
values (users_seq.nextval, 'andres', '$2a$10$KKOe4AKzmacgxSpbdFIh2O62HdKmqsNGi1jxfLOa0fUR0c931o/xq', 1);
insert into users (id, username, password, enabled)
values (users_seq.nextval, 'admin', '$2a$10$.PGCP5KEiG.oHZCjWIy2ceBwhr2evRH7JY/2ZW3vmbkcCoctRf5Xu', 1);

insert into authorities (id, user_id, authority) values (authorities_seq.nextval, 1, 'ROLE_USER');
insert into authorities (id, user_id, authority) values (authorities_seq.nextval, 2, 'ROLE_USER');
insert into authorities (id, user_id, authority) values (authorities_seq.nextval, 2, 'ROLE_ADMIN');
C:\Cursos\Spring5\workspace\spring-boot-tomcat\target\spring-boot-tomcat-0.0.1-SNAPSHOT.war

https://spring-boot211-heroku-v2.herokuapp.com/


mysql://b02e5f86c7fefa:058868cb@us-cdbr-east-06.cleardb.net/heroku_0e2adef5aa65e2b?reconnect=true

proyectos de spring 
spring-boot-data-jpa-spring  (no funciona)
spring-boot-data-jpa-factura (version anterios sin lenguajes)
