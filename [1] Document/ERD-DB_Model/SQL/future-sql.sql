/*==============================================================*/
/* DBMS name:      PostgreSQL 9.x                               */
/* Created on:     15/10/2017 10.28.26                          */
/*==============================================================*/

/*==============================================================*/
/* Table: DISCOUNT                                              */
/*==============================================================*/
create sequence disc START 1001;
create table DISCOUNT (
   ID_DISC              VARCHAR(10)	     DEFAULT 'DISC-' || nextval('disc'),
   ID_EMP               VARCHAR(10)          not null,
   NAME_DISC            VARCHAR(254)         not null,
   PERCENTAGE           FLOAT8               not null,
   START_DATE           DATE                 not null,
   END_DATE             DATE                 not null,
   STATUS_DISC          VARCHAR(15)          not null,
   constraint PK_DISCOUNT primary key (ID_DISC)
);

/*==============================================================*/
/* Index: DISCOUNT_PK                                           */
/*==============================================================*/
create unique index DISCOUNT_PK on DISCOUNT (
ID_DISC
);

/*==============================================================*/
/* Index: ADD_DISCOUNT_FK                                       */
/*==============================================================*/
create  index ADD_DISCOUNT_FK on DISCOUNT (
ID_EMP
);

/*==============================================================*/
/* Table: EMPLOYEE                                              */
/*==============================================================*/
create sequence emp START 1001;

create table EMPLOYEE (
   ID_EMP               VARCHAR(10)          DEFAULT 'EMP-' || nextval('emp'),
   ID_ROLE              VARCHAR(10)          not null,
   USERNAME             VARCHAR(15)          not null,
   PASSWORD             VARCHAR(64)          not null,
   NOKTP                VARCHAR(35)          not null,
   FULL_NAME            VARCHAR(60)          not null,
   ADDRESS              VARCHAR(256)         not null,
   GENDER               CHAR(1)              not null,
   TELEPHONE            VARCHAR(15)          not null,
   STATUS_EMPLOYEE      VARCHAR(15)          not null,
   constraint PK_EMPLOYEE primary key (ID_EMP)
);

/*==============================================================*/
/* Index: EMPLOYEE_PK                                           */
/*==============================================================*/
create unique index EMPLOYEE_PK on EMPLOYEE (
ID_EMP
);

/*==============================================================*/
/* Index: EMPHASROLE_FK                                         */
/*==============================================================*/
create  index EMPHASROLE_FK on EMPLOYEE (
ID_ROLE
);

/*==============================================================*/
/* Table: ITEM                                                  */
/*==============================================================*/
create sequence item START 1001;

create table ITEM (
   ID_ITEM              VARCHAR(30)          not null,
   ID_EMP               VARCHAR(10)          not null,
   NAME_ITEM            VARCHAR(255)         not null,
   PRICE_ITEM           FLOAT8               not null,
   COLOR_ITEM           VARCHAR(25)          not null,
   SIZE_ITEM            VARCHAR(5)           not null,
   TYPE_ITEM            VARCHAR(50)          not null,
   STATUS_ITEM          VARCHAR(15)          not null,
   constraint PK_ITEM primary key (ID_ITEM)
);

/*==============================================================*/
/* Index: ITEM_PK                                               */
/*==============================================================*/
create unique index ITEM_PK on ITEM (
ID_ITEM
);

/*==============================================================*/
/* Index: ADD_ITEM_FK                                           */
/*==============================================================*/
create  index ADD_ITEM_FK on ITEM (
ID_EMP
);

/*==============================================================*/
/* Table: ORDERLINE                                             */
/*==============================================================*/
create sequence orl START 1001;

create table ORDERLINE (
   ID_ORDERLINE         VARCHAR(15)          DEFAULT 'ORL-' || nextval('orl'),
   ID_TRANS             VARCHAR(12)          not null,
   ID_ITEM              VARCHAR(30)          not null,
   ITEM_PRICE           FLOAT8               not null,
   QUANTITY             INT4                 not null,
   SUBTOTAL             FLOAT8               not null,
   constraint PK_ORDERLINE primary key (ID_ORDERLINE)
);

/*==============================================================*/
/* Index: ORDERLINE_PK                                          */
/*==============================================================*/
create unique index ORDERLINE_PK on ORDERLINE (
ID_ORDERLINE
);

/*==============================================================*/
/* Index: ITEMHASORDERLINE_FK                                   */
/*==============================================================*/
create  index ITEMHASORDERLINE_FK on ORDERLINE (
ID_ITEM
);

/*==============================================================*/
/* Index: ORDERHASORDERLINE_FK                                  */
/*==============================================================*/
create  index ORDERHASORDERLINE_FK on ORDERLINE (
ID_TRANS
);

/*==============================================================*/
/* Table: OUTCOME                                               */
/*==============================================================*/
create sequence outcome START 1001;

create table OUTCOME (
   ID_OUTCOME           VARCHAR(10)          DEFAULT 'OUT-' || nextval('outcome'),
   ID_EMP               VARCHAR(10)          not null,
   AMOUNT_OUT           FLOAT8               not null,
   QUANTITY_OUT         INT4                 not null,
   TITLE_OUT            DATE                 not null,
   DESC_OUT             VARCHAR(1)           not null,
   constraint PK_OUTCOME primary key (ID_OUTCOME)
);

/*==============================================================*/
/* Index: OUTCOME_PK                                            */
/*==============================================================*/
create unique index OUTCOME_PK on OUTCOME (
ID_OUTCOME
);

/*==============================================================*/
/* Index: EMPHASOUTCOME_FK                                      */
/*==============================================================*/
create  index EMPHASOUTCOME_FK on OUTCOME (
ID_EMP
);

/*==============================================================*/
/* Table: ROLE                                                  */
/*==============================================================*/
create table ROLE (
   ID_ROLE              VARCHAR(10)          SERIAL,
   ROLE_NAME            VARCHAR(20)          not null,
   constraint PK_ROLE primary key (ID_ROLE)
);

/*==============================================================*/
/* Index: ROLE_PK                                               */
/*==============================================================*/
create unique index ROLE_PK on ROLE (
ID_ROLE
);

/*==============================================================*/
/* Table: TRANSACTION                                           */
/*==============================================================*/
create sequence trans START 100000000001;

create table TRANSACTION (
   ID_TRANS             VARCHAR(12)          DEFAULT nextval('trans'),
   ID_DISC              VARCHAR(10)          not null,
   ID_EMP               VARCHAR(10)          not null,
   CUSTOMER_TRANS       VARCHAR(50)          not null,
   TOTAL_TRANS          FLOAT8               not null,
   DATE_TRANS           DATE                 not null,
   constraint PK_TRANSACTION primary key (ID_TRANS)
);

/*==============================================================*/
/* Index: TRANSACTION_PK                                        */
/*==============================================================*/
create unique index TRANSACTION_PK on TRANSACTION (
ID_TRANS
);

/*==============================================================*/
/* Index: HAS_FK                                                */
/*==============================================================*/
create  index HAS_FK on TRANSACTION (
ID_DISC
);

/*==============================================================*/
/* Index: ADD_ORDER_FK                                          */
/*==============================================================*/
create  index ADD_ORDER_FK on TRANSACTION (
ID_EMP
);

alter table DISCOUNT
   add constraint FK_DISCOUNT_ADD_DISCO_EMPLOYEE foreign key (ID_EMP)
      references EMPLOYEE (ID_EMP)
      on delete restrict on update restrict;

alter table EMPLOYEE
   add constraint FK_EMPLOYEE_EMPHASROL_ROLE foreign key (ID_ROLE)
      references ROLE (ID_ROLE)
      on delete restrict on update restrict;

alter table ITEM
   add constraint FK_ITEM_ADD_ITEM_EMPLOYEE foreign key (ID_EMP)
      references EMPLOYEE (ID_EMP)
      on delete restrict on update restrict;

alter table ORDERLINE
   add constraint FK_ORDERLIN_ITEMHASOR_ITEM foreign key (ID_ITEM)
      references ITEM (ID_ITEM)
      on delete restrict on update restrict;

alter table ORDERLINE
   add constraint FK_ORDERLIN_ORDERHASO_TRANSACT foreign key (ID_TRANS)
      references TRANSACTION (ID_TRANS)
      on delete restrict on update restrict;

alter table OUTCOME
   add constraint FK_OUTCOME_EMPHASOUT_EMPLOYEE foreign key (ID_EMP)
      references EMPLOYEE (ID_EMP)
      on delete restrict on update restrict;

alter table TRANSACTION
   add constraint FK_TRANSACT_ADD_ORDER_EMPLOYEE foreign key (ID_EMP)
      references EMPLOYEE (ID_EMP)
      on delete restrict on update restrict;

alter table TRANSACTION
   add constraint FK_TRANSACT_HAS_DISCOUNT foreign key (ID_DISC)
      references DISCOUNT (ID_DISC)
      on delete restrict on update restrict;

