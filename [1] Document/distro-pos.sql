--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.5
-- Dumped by pg_dump version 9.6.5

-- Started on 2017-12-22 10:47:31

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 12387)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2264 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 201 (class 1259 OID 18418)
-- Name: cart; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE cart (
    id_item character varying(30) NOT NULL,
    username character varying(20) NOT NULL,
    quantity integer NOT NULL,
    item_name text,
    subtotal double precision NOT NULL
);


ALTER TABLE cart OWNER TO postgres;

--
-- TOC entry 185 (class 1259 OID 18260)
-- Name: sec_disc; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE sec_disc
    START WITH 101
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sec_disc OWNER TO postgres;

--
-- TOC entry 186 (class 1259 OID 18262)
-- Name: discount; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE discount (
    id_disc character varying(10) DEFAULT ('DISC-'::text || nextval('sec_disc'::regclass)) NOT NULL,
    username character varying(20) NOT NULL,
    name_disc character varying(254) NOT NULL,
    percentage double precision NOT NULL,
    start_date date NOT NULL,
    end_date date NOT NULL,
    status_disc character varying(15) DEFAULT 'Aktif'::character varying,
    description text NOT NULL
);


ALTER TABLE discount OWNER TO postgres;

--
-- TOC entry 189 (class 1259 OID 18283)
-- Name: item; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE item (
    id_item character varying(30) NOT NULL,
    username character varying(20) NOT NULL,
    name_item character varying(255) NOT NULL,
    price_item double precision NOT NULL,
    stock_item integer NOT NULL,
    image_item text NOT NULL,
    merk_item character varying(3) NOT NULL,
    color_item character varying(3) NOT NULL,
    size_item character varying(5) NOT NULL,
    type_item character varying(3) NOT NULL,
    status_item character varying(15) DEFAULT 'Aktif'::character varying
);


ALTER TABLE item OWNER TO postgres;

--
-- TOC entry 198 (class 1259 OID 18339)
-- Name: item_color; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE item_color (
    id_item_color character varying(3) NOT NULL,
    name_item_color character varying(25) NOT NULL,
    status_color character varying(25) DEFAULT 'Aktif'::character varying
);


ALTER TABLE item_color OWNER TO postgres;

--
-- TOC entry 200 (class 1259 OID 18353)
-- Name: item_merk; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE item_merk (
    id_item_merk character varying(3) NOT NULL,
    name_item_merk character varying(25) NOT NULL,
    status_merk character varying(25) DEFAULT 'Aktif'::character varying
);


ALTER TABLE item_merk OWNER TO postgres;

--
-- TOC entry 199 (class 1259 OID 18346)
-- Name: item_type; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE item_type (
    id_item_type character varying(3) NOT NULL,
    name_item_type character varying(25) NOT NULL,
    status_type character varying(25) DEFAULT 'Aktif'::character varying
);


ALTER TABLE item_type OWNER TO postgres;

--
-- TOC entry 190 (class 1259 OID 18294)
-- Name: sec_orl; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE sec_orl
    START WITH 100000000000001
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sec_orl OWNER TO postgres;

--
-- TOC entry 191 (class 1259 OID 18296)
-- Name: orderline; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE orderline (
    id_orderline character varying(20) DEFAULT ('ORL-'::text || nextval('sec_orl'::regclass)) NOT NULL,
    id_trans character varying(12) NOT NULL,
    id_item character varying(30) NOT NULL,
    item_price double precision NOT NULL,
    quantity integer NOT NULL,
    subtotal double precision NOT NULL
);


ALTER TABLE orderline OWNER TO postgres;

--
-- TOC entry 192 (class 1259 OID 18305)
-- Name: sec_outcome; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE sec_outcome
    START WITH 101
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sec_outcome OWNER TO postgres;

--
-- TOC entry 193 (class 1259 OID 18307)
-- Name: outcome; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE outcome (
    id_outcome character varying(10) DEFAULT ('OUT-'::text || nextval('sec_outcome'::regclass)) NOT NULL,
    username character varying(10) NOT NULL,
    amount_out double precision NOT NULL,
    quantity_out integer NOT NULL,
    title_out character varying(100) NOT NULL,
    desc_out text NOT NULL,
    date_out date DEFAULT now(),
    status character varying(15) DEFAULT 'Aktif'::character varying NOT NULL
);


ALTER TABLE outcome OWNER TO postgres;

--
-- TOC entry 188 (class 1259 OID 18281)
-- Name: sec_item; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE sec_item
    START WITH 101
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sec_item OWNER TO postgres;

--
-- TOC entry 196 (class 1259 OID 18330)
-- Name: sec_trans; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE sec_trans
    START WITH 100000000001
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sec_trans OWNER TO postgres;

--
-- TOC entry 197 (class 1259 OID 18332)
-- Name: transaction; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE transaction (
    id_trans character varying(12) DEFAULT nextval('sec_trans'::regclass) NOT NULL,
    id_disc character varying(10),
    username character varying(20) NOT NULL,
    customer_trans character varying(50) NOT NULL,
    total_trans double precision NOT NULL,
    date_trans date NOT NULL,
    status_trans character varying(15) DEFAULT 'Aktif'::character varying NOT NULL
);


ALTER TABLE transaction OWNER TO postgres;

--
-- TOC entry 195 (class 1259 OID 18317)
-- Name: user_roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE user_roles (
    user_role_id integer NOT NULL,
    username character varying(20) NOT NULL,
    role character varying(20) NOT NULL
);


ALTER TABLE user_roles OWNER TO postgres;

--
-- TOC entry 194 (class 1259 OID 18315)
-- Name: user_roles_user_role_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE user_roles_user_role_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE user_roles_user_role_id_seq OWNER TO postgres;

--
-- TOC entry 2265 (class 0 OID 0)
-- Dependencies: 194
-- Name: user_roles_user_role_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE user_roles_user_role_id_seq OWNED BY user_roles.user_role_id;


--
-- TOC entry 187 (class 1259 OID 18271)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE users (
    namalengkap text NOT NULL,
    username character varying(20) NOT NULL,
    password text NOT NULL,
    alamat text NOT NULL,
    ktp character varying(16) NOT NULL,
    telp character varying(12) NOT NULL,
    jeniskelamin character(1),
    enabled boolean DEFAULT false NOT NULL
);


ALTER TABLE users OWNER TO postgres;

--
-- TOC entry 2064 (class 2604 OID 18320)
-- Name: user_roles user_role_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY user_roles ALTER COLUMN user_role_id SET DEFAULT nextval('user_roles_user_role_id_seq'::regclass);


--
-- TOC entry 2257 (class 0 OID 18418)
-- Dependencies: 201
-- Data for Name: cart; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY cart (id_item, username, quantity, item_name, subtotal) FROM stdin;
\.


--
-- TOC entry 2242 (class 0 OID 18262)
-- Dependencies: 186
-- Data for Name: discount; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY discount (id_disc, username, name_disc, percentage, start_date, end_date, status_disc, description) FROM stdin;
DISC-102	satyadara	Diskon 1	100	2017-12-16	2017-12-21	Aktif	Diskon Nomor 1
101TEST	satyadara	Diskon 2	15	2017-12-12	2017-12-22	Aktif	Diskon Nomor 2
DISC-103	satyadara	DIskon 3 edited	10	2017-12-19	2017-12-21	Aktif	Diskon Nomor 3
DISC-104	satyadara	Diskon 4	-1	2017-12-21	2017-12-21	Aktif	Mantap Jiwa
\.


--
-- TOC entry 2245 (class 0 OID 18283)
-- Dependencies: 189
-- Data for Name: item; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY item (id_item, username, name_item, price_item, stock_item, image_item, merk_item, color_item, size_item, type_item, status_item) FROM stdin;
102-ADS-BJU-S	satyadara	Kemeja Nike M Biru 1000 40	1000	29	default	NKE	BRU	M	KMJ	Aktif
106-ADS-BJU-S	satyadara	WellYeah	6886	80	default	NKE	MRH	XL	KMJ	Aktif
103-ADS-KMJ-S	satyadara	Naisu	4000	80	default	ADS	BRU	S	KMJ	Aktif
101-ADS-BJU-S	satyadara	Mantap Jiwa	2000	8	default	NKE	MRH	XL	KMJ	Aktif
108-NKE-KMJ-L	satyadara	Kemeja Mantap	1000	1	default	NKE	MRH	L	KMJ	Aktif
107-ADS-BJU-S	satyadara	wowowowo	19830	0	default	ADS	BRU	S	BJU	Tidak Aktif
105-NKE-BJU-M	satyadara	Barang Murah	5000	0	default	NKE	MRH	M	BJU	Aktif
104-NKE-KMJ-XL	satyadara	Barang Ajaib	100	80	default	NKE	MRH	XL	KMJ	Aktif
\.


--
-- TOC entry 2254 (class 0 OID 18339)
-- Dependencies: 198
-- Data for Name: item_color; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY item_color (id_item_color, name_item_color, status_color) FROM stdin;
MRH	Merah	Aktif
BRU	Biru	Aktif
\.


--
-- TOC entry 2256 (class 0 OID 18353)
-- Dependencies: 200
-- Data for Name: item_merk; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY item_merk (id_item_merk, name_item_merk, status_merk) FROM stdin;
NKE	Nike	Aktif
ADS	Adidas	Aktif
\.


--
-- TOC entry 2255 (class 0 OID 18346)
-- Dependencies: 199
-- Data for Name: item_type; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY item_type (id_item_type, name_item_type, status_type) FROM stdin;
BJU	Baju	Aktif
KMJ	Kemeja	Aktif
\.


--
-- TOC entry 2247 (class 0 OID 18296)
-- Dependencies: 191
-- Data for Name: orderline; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY orderline (id_orderline, id_trans, id_item, item_price, quantity, subtotal) FROM stdin;
ORL-100000000000007	100000000007	102-ADS-BJU-S	0.0080000003799796104	90	0.72000002861022949
ORL-100000000000008	100000000007	101-ADS-BJU-S	2000	17	34000
ORL-100000000000009	100000000007	103-ADS-KMJ-S	4000	20	80000
ORL-100000000000010	100000000008	102-ADS-BJU-S	0.0080000003799796104	90	0.72000002861022949
ORL-100000000000011	100000000008	101-ADS-BJU-S	2000	17	34000
ORL-100000000000012	100000000008	103-ADS-KMJ-S	4000	20	80000
ORL-100000000000013	100000000009	102-ADS-BJU-S	0.0080000003799796104	90	0.72000002861022949
ORL-100000000000014	100000000009	101-ADS-BJU-S	2000	17	34000
ORL-100000000000015	100000000009	103-ADS-KMJ-S	4000	20	80000
ORL-100000000000016	100000000010	102-ADS-BJU-S	0.0080000003799796104	21	0.16800001263618469
ORL-100000000000017	100000000011	101-ADS-BJU-S	2000	9	18000
ORL-100000000000018	100000000024	102-ADS-BJU-S	0.0080000003799796104	5	0.040000002831220627
ORL-100000000000019	100000000024	103-ADS-KMJ-S	4000	1	4000
ORL-100000000000020	100000000025	104-NKE-KMJ-XL	100	10	1000
ORL-100000000000021	100000000025	103-ADS-KMJ-S	4000	6	24000
ORL-100000000000022	100000000025	102-ADS-BJU-S	1000	11	11000
ORL-100000000000023	100000000025	101-ADS-BJU-S	2000	7	14000
ORL-100000000000024	100000000026	106-ADS-BJU-S	6886	10	68860
ORL-100000000000025	100000000026	103-ADS-KMJ-S	4000	10	40000
ORL-100000000000026	100000000026	101-ADS-BJU-S	2000	2	4000
\.


--
-- TOC entry 2249 (class 0 OID 18307)
-- Dependencies: 193
-- Data for Name: outcome; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY outcome (id_outcome, username, amount_out, quantity_out, title_out, desc_out, date_out, status) FROM stdin;
OUT-102	satyadara	50000	100	Beli Pakaian	wkwk	2017-12-21	Aktif
OUT-101	satyadara	30	0	Pengeluaran WellNan	Well Nan	2017-12-21	Aktif
OUT-103	satyadara	707070	0	Beli Boncabe Super1	Boncabe untuk makan siang 1	2017-12-23	Aktif
\.


--
-- TOC entry 2266 (class 0 OID 0)
-- Dependencies: 185
-- Name: sec_disc; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('sec_disc', 104, true);


--
-- TOC entry 2267 (class 0 OID 0)
-- Dependencies: 188
-- Name: sec_item; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('sec_item', 108, true);


--
-- TOC entry 2268 (class 0 OID 0)
-- Dependencies: 190
-- Name: sec_orl; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('sec_orl', 100000000000026, true);


--
-- TOC entry 2269 (class 0 OID 0)
-- Dependencies: 192
-- Name: sec_outcome; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('sec_outcome', 103, true);


--
-- TOC entry 2270 (class 0 OID 0)
-- Dependencies: 196
-- Name: sec_trans; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('sec_trans', 100000000026, true);


--
-- TOC entry 2253 (class 0 OID 18332)
-- Dependencies: 197
-- Data for Name: transaction; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY transaction (id_trans, id_disc, username, customer_trans, total_trans, date_trans, status_trans) FROM stdin;
100000000007	\N	satyadara0	-	114000.72000002861	2017-12-18	Aktif
100000000008	\N	satyadara0	-	114000.72000002861	2017-12-18	Aktif
100000000009	\N	satyadara0	-	114000.72000002861	2017-12-18	Aktif
100000000010	\N	satyadara0	-	0.16800001263618469	2017-12-18	Aktif
100000000011	\N	satyadara0	-	18000	2017-12-19	Aktif
100000000012	\N	satyadara0	wkwkwk	50000	2017-01-11	Aktif
100000000013	\N	satyadara0	wkwkwk	52000	2017-02-11	Aktif
100000000014	\N	satyadara0	wkwkwk	52000	2017-02-11	Aktif
100000000015	\N	satyadara0	wkwkwk	53000	2017-03-11	Aktif
100000000016	\N	satyadara0	wkwkwk	54000	2017-04-11	Aktif
100000000017	\N	satyadara0	wkwkwk	55000	2017-05-11	Aktif
100000000018	\N	satyadara0	wkwkwk	56000	2017-06-11	Aktif
100000000019	\N	satyadara0	wkwkwk	1002000	2017-02-11	Aktif
100000000020	\N	satyadara0	wkwkwk	50000	2016-01-11	Aktif
100000000021	\N	satyadara0	wkwkwk	50000	2016-12-11	Aktif
100000000022	\N	satyadara0	wkwkwk	50000	2016-12-21	Aktif
100000000023	\N	satyadara0	wkwkwk1	99999	2015-07-23	Aktif
100000000024	\N	satyadara0	-	4000.0400000028312	2017-12-20	Aktif
100000000025	\N	satyadara0	-	50000	2017-12-21	Aktif
100000000026	\N	satyadara0	-	112860	2017-12-21	Aktif
\.


--
-- TOC entry 2251 (class 0 OID 18317)
-- Dependencies: 195
-- Data for Name: user_roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY user_roles (user_role_id, username, role) FROM stdin;
1	satyadara	MANAGER
2	satyadara0	CASHIER
\.


--
-- TOC entry 2271 (class 0 OID 0)
-- Dependencies: 194
-- Name: user_roles_user_role_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('user_roles_user_role_id_seq', 2, true);


--
-- TOC entry 2243 (class 0 OID 18271)
-- Dependencies: 187
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY users (namalengkap, username, password, alamat, ktp, telp, jeniskelamin, enabled) FROM stdin;
Satya Syahputra	satyadara	$2a$10$ZzCCMuUwNURxc4NPx415DupB.oyhOXETgVg1zkJDbB1r2lzVeNVoC	Jalan Via	0123456789	321312	L	t
Satya Syahputra	satyadara0	$2a$10$7WpSeYjoG8v6rpd/clr4MeUgYHRTpQvYgyfB0lAl2ty2hzJVIQZqq	Jalan Vallen	01232	0321312	L	t
\.


--
-- TOC entry 2073 (class 2606 OID 18268)
-- Name: discount pk_discount; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY discount
    ADD CONSTRAINT pk_discount PRIMARY KEY (id_disc);


--
-- TOC entry 2083 (class 2606 OID 18291)
-- Name: item pk_item; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY item
    ADD CONSTRAINT pk_item PRIMARY KEY (id_item);


--
-- TOC entry 2105 (class 2606 OID 18344)
-- Name: item_color pk_item_color; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY item_color
    ADD CONSTRAINT pk_item_color PRIMARY KEY (id_item_color);


--
-- TOC entry 2111 (class 2606 OID 18358)
-- Name: item_merk pk_item_merk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY item_merk
    ADD CONSTRAINT pk_item_merk PRIMARY KEY (id_item_merk);


--
-- TOC entry 2108 (class 2606 OID 18351)
-- Name: item_type pk_item_type; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY item_type
    ADD CONSTRAINT pk_item_type PRIMARY KEY (id_item_type);


--
-- TOC entry 2088 (class 2606 OID 26444)
-- Name: orderline pk_orderline; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY orderline
    ADD CONSTRAINT pk_orderline PRIMARY KEY (id_orderline);


--
-- TOC entry 2092 (class 2606 OID 18312)
-- Name: outcome pk_outcome; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY outcome
    ADD CONSTRAINT pk_outcome PRIMARY KEY (id_outcome);


--
-- TOC entry 2101 (class 2606 OID 18337)
-- Name: transaction pk_transaction; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY transaction
    ADD CONSTRAINT pk_transaction PRIMARY KEY (id_trans);


--
-- TOC entry 2076 (class 2606 OID 18279)
-- Name: users pk_users; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY users
    ADD CONSTRAINT pk_users PRIMARY KEY (username);


--
-- TOC entry 2094 (class 2606 OID 18322)
-- Name: user_roles user_roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY user_roles
    ADD CONSTRAINT user_roles_pkey PRIMARY KEY (user_role_id);


--
-- TOC entry 2096 (class 2606 OID 18324)
-- Name: user_roles user_roles_username_role_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY user_roles
    ADD CONSTRAINT user_roles_username_role_key UNIQUE (username, role);


--
-- TOC entry 2070 (class 1259 OID 18270)
-- Name: add_discount_fk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX add_discount_fk ON discount USING btree (username);


--
-- TOC entry 2077 (class 1259 OID 18293)
-- Name: add_item_fk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX add_item_fk ON item USING btree (username);


--
-- TOC entry 2097 (class 1259 OID 18361)
-- Name: add_order_fk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX add_order_fk ON transaction USING btree (username);


--
-- TOC entry 2112 (class 1259 OID 26429)
-- Name: cart_id_item_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX cart_id_item_uindex ON cart USING btree (id_item);


--
-- TOC entry 2098 (class 1259 OID 26446)
-- Name: datetrans_index; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX datetrans_index ON transaction USING btree (date_trans);


--
-- TOC entry 2071 (class 1259 OID 18269)
-- Name: discount_pk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX discount_pk ON discount USING btree (id_disc);


--
-- TOC entry 2089 (class 1259 OID 18314)
-- Name: emphasoutcome_fk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX emphasoutcome_fk ON outcome USING btree (username);


--
-- TOC entry 2074 (class 1259 OID 18280)
-- Name: employee_pk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX employee_pk ON users USING btree (username);


--
-- TOC entry 2099 (class 1259 OID 18360)
-- Name: has_fk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX has_fk ON transaction USING btree (id_disc);


--
-- TOC entry 2078 (class 1259 OID 18362)
-- Name: item_color_fk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX item_color_fk ON item USING btree (color_item);


--
-- TOC entry 2103 (class 1259 OID 18345)
-- Name: item_color_pk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX item_color_pk ON item_color USING btree (id_item_color);


--
-- TOC entry 2079 (class 1259 OID 18364)
-- Name: item_merk_fk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX item_merk_fk ON item USING btree (merk_item);


--
-- TOC entry 2109 (class 1259 OID 18359)
-- Name: item_merk_pk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX item_merk_pk ON item_merk USING btree (id_item_merk);


--
-- TOC entry 2080 (class 1259 OID 18292)
-- Name: item_pk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX item_pk ON item USING btree (id_item);


--
-- TOC entry 2081 (class 1259 OID 18363)
-- Name: item_type_fk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX item_type_fk ON item USING btree (type_item);


--
-- TOC entry 2106 (class 1259 OID 18352)
-- Name: item_type_pk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX item_type_pk ON item_type USING btree (id_item_type);


--
-- TOC entry 2084 (class 1259 OID 18303)
-- Name: itemhasorderline_fk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX itemhasorderline_fk ON orderline USING btree (id_item);


--
-- TOC entry 2085 (class 1259 OID 18304)
-- Name: orderhasorderline_fk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX orderhasorderline_fk ON orderline USING btree (id_trans);


--
-- TOC entry 2086 (class 1259 OID 26445)
-- Name: orderline_pk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX orderline_pk ON orderline USING btree (id_orderline);


--
-- TOC entry 2090 (class 1259 OID 18313)
-- Name: outcome_pk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX outcome_pk ON outcome USING btree (id_outcome);


--
-- TOC entry 2102 (class 1259 OID 18338)
-- Name: transaction_pk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX transaction_pk ON transaction USING btree (id_trans);


--
-- TOC entry 2113 (class 2606 OID 18365)
-- Name: discount fk_discount_add_disco_employee; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY discount
    ADD CONSTRAINT fk_discount_add_disco_employee FOREIGN KEY (username) REFERENCES users(username) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2114 (class 2606 OID 18370)
-- Name: item fk_item_add_item_employee; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY item
    ADD CONSTRAINT fk_item_add_item_employee FOREIGN KEY (username) REFERENCES users(username) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2115 (class 2606 OID 18375)
-- Name: item fk_item_color; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY item
    ADD CONSTRAINT fk_item_color FOREIGN KEY (color_item) REFERENCES item_color(id_item_color) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2117 (class 2606 OID 18385)
-- Name: item fk_item_merk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY item
    ADD CONSTRAINT fk_item_merk FOREIGN KEY (merk_item) REFERENCES item_merk(id_item_merk) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2116 (class 2606 OID 18380)
-- Name: item fk_item_type; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY item
    ADD CONSTRAINT fk_item_type FOREIGN KEY (type_item) REFERENCES item_type(id_item_type) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2118 (class 2606 OID 18390)
-- Name: orderline fk_orderlin_itemhasor_item; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY orderline
    ADD CONSTRAINT fk_orderlin_itemhasor_item FOREIGN KEY (id_item) REFERENCES item(id_item) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2119 (class 2606 OID 18395)
-- Name: orderline fk_orderlin_orderhaso_transact; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY orderline
    ADD CONSTRAINT fk_orderlin_orderhaso_transact FOREIGN KEY (id_trans) REFERENCES transaction(id_trans) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2120 (class 2606 OID 18400)
-- Name: outcome fk_outcome_emphasout_employee; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY outcome
    ADD CONSTRAINT fk_outcome_emphasout_employee FOREIGN KEY (username) REFERENCES users(username) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2122 (class 2606 OID 18405)
-- Name: transaction fk_transact_add_order_employee; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY transaction
    ADD CONSTRAINT fk_transact_add_order_employee FOREIGN KEY (username) REFERENCES users(username) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2123 (class 2606 OID 18410)
-- Name: transaction fk_transact_has_discount; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY transaction
    ADD CONSTRAINT fk_transact_has_discount FOREIGN KEY (id_disc) REFERENCES discount(id_disc) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2121 (class 2606 OID 18325)
-- Name: user_roles user_roles_username_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY user_roles
    ADD CONSTRAINT user_roles_username_fkey FOREIGN KEY (username) REFERENCES users(username) ON UPDATE CASCADE ON DELETE CASCADE;


-- Completed on 2017-12-22 10:47:32

--
-- PostgreSQL database dump complete
--

