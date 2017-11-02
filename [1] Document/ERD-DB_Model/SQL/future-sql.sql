/*==============================================================*/
/* DBMS name:      PostgreSQL 9.x                               */
/* Created on:     15/10/2017 10.28.26                          */
/*==============================================================*/

/*==============================================================*/
/* Table: DISCOUNT                                              */
/*==============================================================*/
CREATE SEQUENCE sec_disc START 101;
CREATE TABLE DISCOUNT (
  ID_DISC     VARCHAR(10) DEFAULT 'DISC-' || nextval('sec_disc'),
  ID_EMP      VARCHAR(10)  NOT NULL,
  NAME_DISC   VARCHAR(254) NOT NULL,
  PERCENTAGE  FLOAT8       NOT NULL,
  START_DATE  DATE         NOT NULL,
  END_DATE    DATE         NOT NULL,
  STATUS_DISC VARCHAR(15)  DEFAULT 'Aktif',
  CONSTRAINT PK_DISCOUNT PRIMARY KEY (ID_DISC)
);

/*==============================================================*/
/* Index: DISCOUNT_PK                                           */
/*==============================================================*/
CREATE UNIQUE INDEX DISCOUNT_PK
  ON DISCOUNT (
    ID_DISC
  );

/*==============================================================*/
/* Index: ADD_DISCOUNT_FK                                       */
/*==============================================================*/
CREATE INDEX ADD_DISCOUNT_FK
  ON DISCOUNT (
    ID_EMP
  );

/*==============================================================*/
/* Table: EMPLOYEE                                              */
/*==============================================================*/
CREATE SEQUENCE sec_emp START 1001;

CREATE TABLE EMPLOYEE (
  ID_EMP    VARCHAR(10) DEFAULT 'EMP-' || nextval('sec_emp'),
  ID_ROLE   INT          NOT NULL,
  USERNAME  VARCHAR(15)  NOT NULL,
  PASSWORD  VARCHAR(64)  NOT NULL,
  NOKTP     VARCHAR(35)  NOT NULL,
  PHOTO     TEXT         NOT NULL,
  FULL_NAME VARCHAR(60)  NOT NULL,
  ADDRESS   VARCHAR(256) NOT NULL,
  GENDER    CHAR(1)      NOT NULL,
  TELEPHONE VARCHAR(15)  NOT NULL,
  STATUS    VARCHAR(15)  NOT NULL,
  CONSTRAINT PK_EMPLOYEE PRIMARY KEY (ID_EMP)
);

/*==============================================================*/
/* Index: EMPLOYEE_PK                                           */
/*==============================================================*/
CREATE UNIQUE INDEX EMPLOYEE_PK
  ON EMPLOYEE (
    ID_EMP
  );

/*==============================================================*/
/* Index: EMPHASROLE_FK                                         */
/*==============================================================*/
CREATE INDEX EMPHASROLE_FK
  ON EMPLOYEE (
    ID_ROLE
  );

/*==============================================================*/
/* Table: ITEM                                                  */
/*==============================================================*/
CREATE SEQUENCE sec_item START 101;

CREATE TABLE ITEM (
  ID_ITEM     VARCHAR(30)  NOT NULL,
  ID_EMP      VARCHAR(10)  NOT NULL,
  NAME_ITEM   VARCHAR(255) NOT NULL,
  PRICE_ITEM  FLOAT8       NOT NULL,
  STOCK_ITEM  INT4         NOT NULL,
  IMAGE_ITEM  TEXT         NOT NULL,
  MERK_ITEM   VARCHAR(3)   NOT NULL,
  COLOR_ITEM  VARCHAR(3)   NOT NULL,
  SIZE_ITEM   VARCHAR(5)   NOT NULL,
  TYPE_ITEM   VARCHAR(3)   NOT NULL,
  STATUS_ITEM VARCHAR(15)  DEFAULT 'Aktif',
  CONSTRAINT PK_ITEM PRIMARY KEY (ID_ITEM)
);

/*==============================================================*/
/* Index: ITEM_PK                                               */
/*==============================================================*/
CREATE UNIQUE INDEX ITEM_PK
  ON ITEM (
    ID_ITEM
  );

/*==============================================================*/
/* Index: ADD_ITEM_FK                                           */
/*==============================================================*/
CREATE INDEX ADD_ITEM_FK
  ON ITEM (
    ID_EMP
  );

/*==============================================================*/
/* Table: ORDERLINE                                             */
/*==============================================================*/
CREATE SEQUENCE sec_orl START 100000000000001;

CREATE TABLE ORDERLINE (
  ID_ORDERLINE VARCHAR(15) DEFAULT 'ORL-' || nextval('sec_orl'),
  ID_TRANS     VARCHAR(12) NOT NULL,
  ID_ITEM      VARCHAR(30) NOT NULL,
  ITEM_PRICE   FLOAT8      NOT NULL,
  QUANTITY     INT4        NOT NULL,
  SUBTOTAL     FLOAT8      NOT NULL,
  CONSTRAINT PK_ORDERLINE PRIMARY KEY (ID_ORDERLINE)
);

/*==============================================================*/
/* Index: ORDERLINE_PK                                          */
/*==============================================================*/
CREATE UNIQUE INDEX ORDERLINE_PK
  ON ORDERLINE (
    ID_ORDERLINE
  );

/*==============================================================*/
/* Index: ITEMHASORDERLINE_FK                                   */
/*==============================================================*/
CREATE INDEX ITEMHASORDERLINE_FK
  ON ORDERLINE (
    ID_ITEM
  );

/*==============================================================*/
/* Index: ORDERHASORDERLINE_FK                                  */
/*==============================================================*/
CREATE INDEX ORDERHASORDERLINE_FK
  ON ORDERLINE (
    ID_TRANS
  );

/*==============================================================*/
/* Table: OUTCOME                                               */
/*==============================================================*/
CREATE SEQUENCE sec_outcome START 101;

CREATE TABLE OUTCOME (
  ID_OUTCOME   VARCHAR(10) DEFAULT 'OUT-' || nextval('sec_outcome'),
  ID_EMP       VARCHAR(10) NOT NULL,
  AMOUNT_OUT   FLOAT8      NOT NULL,
  QUANTITY_OUT INT4        NOT NULL,
  TITLE_OUT    DATE        NOT NULL,
  DESC_OUT     VARCHAR(1)  NOT NULL,
  CONSTRAINT PK_OUTCOME PRIMARY KEY (ID_OUTCOME)
);

/*==============================================================*/
/* Index: OUTCOME_PK                                            */
/*==============================================================*/
CREATE UNIQUE INDEX OUTCOME_PK
  ON OUTCOME (
    ID_OUTCOME
  );

/*==============================================================*/
/* Index: EMPHASOUTCOME_FK                                      */
/*==============================================================*/
CREATE INDEX EMPHASOUTCOME_FK
  ON OUTCOME (
    ID_EMP
  );

/*==============================================================*/
/* Table: ROLE                                                  */
/*==============================================================*/
CREATE TABLE ROLE (
  ID_ROLE   SERIAL,
  ROLE_NAME VARCHAR(20) NOT NULL,
  CONSTRAINT PK_ROLE PRIMARY KEY (ID_ROLE)
);

/*==============================================================*/
/* Index: ROLE_PK                                               */
/*==============================================================*/
CREATE UNIQUE INDEX ROLE_PK
  ON ROLE (
    ID_ROLE
  );

/*==============================================================*/
/* Table: TRANSACTION                                           */
/*==============================================================*/
CREATE SEQUENCE sec_trans START 100000000001;

CREATE TABLE TRANSACTION (
  ID_TRANS       VARCHAR(12) DEFAULT nextval('sec_trans'),
  ID_DISC        VARCHAR(10) NOT NULL,
  ID_EMP         VARCHAR(10) NOT NULL,
  CUSTOMER_TRANS VARCHAR(50) NOT NULL,
  TOTAL_TRANS    FLOAT8      NOT NULL,
  DATE_TRANS     DATE        NOT NULL,
  CONSTRAINT PK_TRANSACTION PRIMARY KEY (ID_TRANS)
);

/*==============================================================*/
/* Index: TRANSACTION_PK                                        */
/*==============================================================*/
CREATE UNIQUE INDEX TRANSACTION_PK
  ON TRANSACTION (
    ID_TRANS
  );

/*==============================================================*/
/* Table: ITEM_COLOR                                            */
/*==============================================================*/
CREATE TABLE ITEM_COLOR (
  ID_ITEM_COLOR   VARCHAR(3)  NOT NULL,
  NAME_ITEM_COLOR VARCHAR(25) NOT NULL,
  STATUS_COLOR    VARCHAR(25) DEFAULT 'Aktif',
  CONSTRAINT PK_ITEM_COLOR PRIMARY KEY (ID_ITEM_COLOR)
);

/*==============================================================*/
/* Index: ITEM_COLOR_PK                                         */
/*==============================================================*/
CREATE UNIQUE INDEX ITEM_COLOR_PK
  ON ITEM_COLOR (
    ID_ITEM_COLOR
  );

/*==============================================================*/
/* Table: ITEM_TYPE                                             */
/*==============================================================*/
CREATE TABLE ITEM_TYPE (
  ID_ITEM_TYPE   VARCHAR(3)  NOT NULL,
  NAME_ITEM_TYPE VARCHAR(25) NOT NULL,
  STATUS_TYPE    VARCHAR(25) DEFAULT 'Aktif',
  CONSTRAINT PK_ITEM_TYPE PRIMARY KEY (ID_ITEM_TYPE)
);

/*==============================================================*/
/* Index: ITEM_TYPE_PK                                          */
/*==============================================================*/
CREATE UNIQUE INDEX ITEM_TYPE_PK
  ON ITEM_TYPE (
    ID_ITEM_TYPE
  );

/*==============================================================*/
/* Table: ITEM_MERK                                             */
/*==============================================================*/
CREATE TABLE ITEM_MERK (
  ID_ITEM_MERK   VARCHAR(3)  NOT NULL,
  NAME_ITEM_MERK VARCHAR(25) NOT NULL,
  STATUS_MERK    VARCHAR(25) DEFAULT 'Aktif',
  CONSTRAINT PK_ITEM_MERK PRIMARY KEY (ID_ITEM_MERK)
);

/*==============================================================*/
/* Index: ITEM_MERK_PK                                         */
/*==============================================================*/
CREATE UNIQUE INDEX ITEM_MERK_PK
  ON ITEM_MERK (
    ID_ITEM_MERK
  );

/*==============================================================*/
/* Index: HAS_FK                                                */
/*==============================================================*/
CREATE INDEX HAS_FK
  ON TRANSACTION (
    ID_DISC
  );

/*==============================================================*/
/* Index: ADD_ORDER_FK                                          */
/*==============================================================*/
CREATE INDEX ADD_ORDER_FK
  ON TRANSACTION (
    ID_EMP
  );

/*==============================================================*/
/* Index: ITEM_COLOR_FK                                         */
/*==============================================================*/
CREATE INDEX ITEM_COLOR_FK
  ON ITEM (
    COLOR_ITEM
  );

/*==============================================================*/
/* Index: ITEM_TYPE_FK                                          */
/*==============================================================*/
CREATE INDEX ITEM_TYPE_FK
  ON ITEM (
    TYPE_ITEM
  );

/*==============================================================*/
/* Index: ITEM_MERK_FK                                          */
/*==============================================================*/
CREATE INDEX ITEM_MERK_FK
  ON ITEM (
    MERK_ITEM
  );

ALTER TABLE DISCOUNT
  ADD CONSTRAINT FK_DISCOUNT_ADD_DISCO_EMPLOYEE FOREIGN KEY (ID_EMP)
REFERENCES EMPLOYEE (ID_EMP)
ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE EMPLOYEE
  ADD CONSTRAINT FK_EMPLOYEE_EMPHASROL_ROLE FOREIGN KEY (ID_ROLE)
REFERENCES ROLE (ID_ROLE)
ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE ITEM
  ADD CONSTRAINT FK_ITEM_ADD_ITEM_EMPLOYEE FOREIGN KEY (ID_EMP)
REFERENCES EMPLOYEE (ID_EMP)
ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE ITEM
  ADD CONSTRAINT FK_ITEM_COLOR FOREIGN KEY (COLOR_ITEM)
REFERENCES ITEM_COLOR (ID_ITEM_COLOR)
ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE ITEM
  ADD CONSTRAINT FK_ITEM_TYPE FOREIGN KEY (TYPE_ITEM)
REFERENCES ITEM_TYPE (ID_ITEM_TYPE)
ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE ITEM
  ADD CONSTRAINT FK_ITEM_MERK FOREIGN KEY (MERK_ITEM)
REFERENCES ITEM_MERK (ID_ITEM_MERK)
ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE ORDERLINE
  ADD CONSTRAINT FK_ORDERLIN_ITEMHASOR_ITEM FOREIGN KEY (ID_ITEM)
REFERENCES ITEM (ID_ITEM)
ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE ORDERLINE
  ADD CONSTRAINT FK_ORDERLIN_ORDERHASO_TRANSACT FOREIGN KEY (ID_TRANS)
REFERENCES TRANSACTION (ID_TRANS)
ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE OUTCOME
  ADD CONSTRAINT FK_OUTCOME_EMPHASOUT_EMPLOYEE FOREIGN KEY (ID_EMP)
REFERENCES EMPLOYEE (ID_EMP)
ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE TRANSACTION
  ADD CONSTRAINT FK_TRANSACT_ADD_ORDER_EMPLOYEE FOREIGN KEY (ID_EMP)
REFERENCES EMPLOYEE (ID_EMP)
ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE TRANSACTION
  ADD CONSTRAINT FK_TRANSACT_HAS_DISCOUNT FOREIGN KEY (ID_DISC)
REFERENCES DISCOUNT (ID_DISC)
ON DELETE RESTRICT ON UPDATE RESTRICT;

INSERT INTO ITEM_TYPE (ID_ITEM_TYPE, NAME_ITEM_TYPE) VALUES ('BJU', 'Baju');
INSERT INTO ITEM_TYPE (ID_ITEM_TYPE, NAME_ITEM_TYPE) VALUES ('KMJ', 'Kemeja');

INSERT INTO ITEM_MERK (ID_ITEM_MERK, NAME_ITEM_MERK) VALUES ('NKE', 'Nike');
INSERT INTO ITEM_MERK (ID_ITEM_MERK, NAME_ITEM_MERK) VALUES ('ADS', 'Adidas');

INSERT INTO ITEM_COLOR(ID_ITEM_COLOR, NAME_ITEM_COLOR) VALUES ('MRH', 'Merah');
INSERT INTO ITEM_COLOR(ID_ITEM_COLOR, NAME_ITEM_COLOR) VALUES ('BRU', 'Biru');
