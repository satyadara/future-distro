/*==============================================================*/
/* DBMS name:      PostgreSQL 9.x                               */
/* Created on:     08/10/2017 08.02.31                          */
/*==============================================================*/


drop index ADD_DISCOUNT_FK;

drop index DISCOUNT_PK;

drop table DISCOUNT;

drop index EMPLOYEE_PK;

drop table EMPLOYEE;

drop index ADD_ITEM_FK;

drop index ITEM_PK;

drop table ITEM;

drop index ORDERHASORDERLINE_FK;

drop index ITEMHASORDERLINE_FK;

drop index ORDERLINE_PK;

drop table ORDERLINE;

drop index HAS_OUTCOME_FK;

drop index OUTCOME_PK;

drop table OUTCOME;

drop index ADD_ORDER_FK;

drop index HAS_FK;

drop index TRANSACTION_PK;

drop table TRANSACTION;

/*==============================================================*/
/* Table: DISCOUNT                                              */
/*==============================================================*/
create table DISCOUNT (
   ID_DISC              INT4                 not null,
   EMP_ID               INT4                 not null,
   NAME_DISC            VARCHAR(255)         null,
   PERCENTAGE           FLOAT8               null,
   START_DATE           DATE                 null,
   END_DATE             DATE                 null,
   STATUS_DISC          VARCHAR(15)          null,
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
EMP_ID
);

/*==============================================================*/
/* Table: EMPLOYEE                                              */
/*==============================================================*/
create table EMPLOYEE (
   EMP_ID               INT4                 not null,
   USERNAME             VARCHAR(15)          null,
   PASSWORD             VARCHAR(64)          null,
   ROLE                 VARCHAR(20)          null,
   NOKTP                VARCHAR(35)          null,
   FULL_NAME            VARCHAR(60)          null,
   ADDRESS              VARCHAR(256)         null,
   GENDER               CHAR(1)              null,
   TELEPHONE            VARCHAR(15)          null,
   STATUS_EMPLOYEE      VARCHAR(15)          null,
   constraint PK_EMPLOYEE primary key (EMP_ID)
);

/*==============================================================*/
/* Index: EMPLOYEE_PK                                           */
/*==============================================================*/
create unique index EMPLOYEE_PK on EMPLOYEE (
EMP_ID
);

/*==============================================================*/
/* Table: ITEM                                                  */
/*==============================================================*/
create table ITEM (
   ID_ITEM              INT4                 not null,
   EMP_ID               INT4                 not null,
   NAME_ITEM            VARCHAR(255)         null,
   PRICE_ITEM           FLOAT8               null,
   COLOR_ITEM           VARCHAR(25)          null,
   SIZE_ITEM            VARCHAR(5)           null,
   TYPE_ITEM            VARCHAR(50)          null,
   STATUS_ITEM          VARCHAR(15)          null,
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
EMP_ID
);

/*==============================================================*/
/* Table: ORDERLINE                                             */
/*==============================================================*/
create table ORDERLINE (
   ID_ORDERLINE         INT4                 not null,
   ID_TRANS             INT4                 not null,
   ID_ITEM              INT4                 not null,
   ITEM_PRICE           FLOAT8               null,
   QUANTITY             INT4                 null,
   SUBTOTAL             FLOAT8               null,
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
create table OUTCOME (
   ID_OUTCOME           INT4                 not null,
   ID_ITEM              INT4                 not null,
   AMOUNT_OUT           FLOAT8               null,
   QUANTITY_OUT         INT4                 null,
   TITLE_OUT            DATE                 null,
   DESC_OUT             VARCHAR(1024)        null,
   constraint PK_OUTCOME primary key (ID_OUTCOME)
);

/*==============================================================*/
/* Index: OUTCOME_PK                                            */
/*==============================================================*/
create unique index OUTCOME_PK on OUTCOME (
ID_OUTCOME
);

/*==============================================================*/
/* Index: HAS_OUTCOME_FK                                        */
/*==============================================================*/
create  index HAS_OUTCOME_FK on OUTCOME (
ID_ITEM
);

/*==============================================================*/
/* Table: TRANSACTION                                           */
/*==============================================================*/
create table TRANSACTION (
   ID_TRANS             INT4                 not null,
   ID_DISC              INT4                 not null,
   EMP_ID               INT4                 not null,
   CUSTOMER_TRANS       VARCHAR(50)          null,
   TOTAL_TRANS          FLOAT8               null,
   DATE_TRANS           DATE                 null,
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
EMP_ID
);

alter table DISCOUNT
   add constraint FK_DISCOUNT_ADD_DISCO_EMPLOYEE foreign key (EMP_ID)
      references EMPLOYEE (EMP_ID)
      on delete restrict on update restrict;

alter table ITEM
   add constraint FK_ITEM_ADD_ITEM_EMPLOYEE foreign key (EMP_ID)
      references EMPLOYEE (EMP_ID)
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
   add constraint FK_OUTCOME_HAS_OUTCO_ITEM foreign key (ID_ITEM)
      references ITEM (ID_ITEM)
      on delete restrict on update restrict;

alter table TRANSACTION
   add constraint FK_TRANSACT_ADD_ORDER_EMPLOYEE foreign key (EMP_ID)
      references EMPLOYEE (EMP_ID)
      on delete restrict on update restrict;

alter table TRANSACTION
   add constraint FK_TRANSACT_HAS_DISCOUNT foreign key (ID_DISC)
      references DISCOUNT (ID_DISC)
      on delete restrict on update restrict;

