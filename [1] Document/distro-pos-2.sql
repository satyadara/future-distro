PGDMP                          v         
   distro_pos    9.6.5    9.6.5 V    �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                       false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                       false            �           1262    35318 
   distro_pos    DATABASE     �   CREATE DATABASE distro_pos WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'Indonesian_Indonesia.1252' LC_CTYPE = 'Indonesian_Indonesia.1252';
    DROP DATABASE distro_pos;
             postgres    false                        2615    2200    public    SCHEMA        CREATE SCHEMA public;
    DROP SCHEMA public;
             postgres    false            �           0    0    SCHEMA public    COMMENT     6   COMMENT ON SCHEMA public IS 'standard public schema';
                  postgres    false    3                        3079    12387    plpgsql 	   EXTENSION     ?   CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;
    DROP EXTENSION plpgsql;
                  false            �           0    0    EXTENSION plpgsql    COMMENT     @   COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';
                       false    1            �            1259    35319    cart    TABLE     �   CREATE TABLE cart (
    id_item character varying(30) NOT NULL,
    username character varying(20) NOT NULL,
    quantity integer NOT NULL,
    item_name text,
    subtotal double precision NOT NULL
);
    DROP TABLE public.cart;
       public         postgres    false    3            �            1259    35325    sec_disc    SEQUENCE     l   CREATE SEQUENCE sec_disc
    START WITH 101
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
    DROP SEQUENCE public.sec_disc;
       public       postgres    false    3            �            1259    35327    discount    TABLE     �  CREATE TABLE discount (
    id_disc character varying(10) DEFAULT ('DISC-'::text || nextval('sec_disc'::regclass)) NOT NULL,
    username character varying(20) NOT NULL,
    name_disc character varying(254) NOT NULL,
    percentage double precision NOT NULL,
    start_date date NOT NULL,
    end_date date NOT NULL,
    status_disc character varying(15) DEFAULT 'Aktif'::character varying,
    description text NOT NULL
);
    DROP TABLE public.discount;
       public         postgres    false    186    3            �            1259    35335    item    TABLE       CREATE TABLE item (
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
    DROP TABLE public.item;
       public         postgres    false    3            �            1259    35342 
   item_color    TABLE     �   CREATE TABLE item_color (
    id_item_color character varying(3) NOT NULL,
    name_item_color character varying(25) NOT NULL,
    status_color character varying(25) DEFAULT 'Aktif'::character varying
);
    DROP TABLE public.item_color;
       public         postgres    false    3            �            1259    35346 	   item_merk    TABLE     �   CREATE TABLE item_merk (
    id_item_merk character varying(3) NOT NULL,
    name_item_merk character varying(25) NOT NULL,
    status_merk character varying(25) DEFAULT 'Aktif'::character varying
);
    DROP TABLE public.item_merk;
       public         postgres    false    3            �            1259    35350 	   item_type    TABLE     �   CREATE TABLE item_type (
    id_item_type character varying(3) NOT NULL,
    name_item_type character varying(25) NOT NULL,
    status_type character varying(25) DEFAULT 'Aktif'::character varying
);
    DROP TABLE public.item_type;
       public         postgres    false    3            �            1259    35354    sec_orl    SEQUENCE     w   CREATE SEQUENCE sec_orl
    START WITH 100000000000001
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
    DROP SEQUENCE public.sec_orl;
       public       postgres    false    3            �            1259    35356 	   orderline    TABLE     M  CREATE TABLE orderline (
    id_orderline character varying(20) DEFAULT ('ORL-'::text || nextval('sec_orl'::regclass)) NOT NULL,
    id_trans character varying(12) NOT NULL,
    id_item character varying(30) NOT NULL,
    item_price double precision NOT NULL,
    quantity integer NOT NULL,
    subtotal double precision NOT NULL
);
    DROP TABLE public.orderline;
       public         postgres    false    192    3            �            1259    35360    sec_outcome    SEQUENCE     o   CREATE SEQUENCE sec_outcome
    START WITH 101
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 "   DROP SEQUENCE public.sec_outcome;
       public       postgres    false    3            �            1259    35362    outcome    TABLE     �  CREATE TABLE outcome (
    id_outcome character varying(10) DEFAULT ('OUT-'::text || nextval('sec_outcome'::regclass)) NOT NULL,
    username character varying(10) NOT NULL,
    amount_out double precision NOT NULL,
    quantity_out integer NOT NULL,
    title_out character varying(100) NOT NULL,
    desc_out text NOT NULL,
    date_out date DEFAULT now(),
    status character varying(15) DEFAULT 'Aktif'::character varying NOT NULL
);
    DROP TABLE public.outcome;
       public         postgres    false    194    3            �            1259    35371    sec_item    SEQUENCE     l   CREATE SEQUENCE sec_item
    START WITH 101
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
    DROP SEQUENCE public.sec_item;
       public       postgres    false    3            �            1259    35373 	   sec_trans    SEQUENCE     v   CREATE SEQUENCE sec_trans
    START WITH 100000000001
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
     DROP SEQUENCE public.sec_trans;
       public       postgres    false    3            �            1259    35375    transaction    TABLE     �  CREATE TABLE transaction (
    id_trans character varying(12) DEFAULT nextval('sec_trans'::regclass) NOT NULL,
    id_disc character varying(10),
    username character varying(20) NOT NULL,
    customer_trans character varying(50) NOT NULL,
    total_trans double precision NOT NULL,
    date_trans date NOT NULL,
    status_trans character varying(15) DEFAULT 'Aktif'::character varying NOT NULL
);
    DROP TABLE public.transaction;
       public         postgres    false    197    3            �            1259    35380 
   user_roles    TABLE     �   CREATE TABLE user_roles (
    user_role_id integer NOT NULL,
    username character varying(20) NOT NULL,
    role character varying(20) NOT NULL
);
    DROP TABLE public.user_roles;
       public         postgres    false    3            �            1259    35383    user_roles_user_role_id_seq    SEQUENCE     }   CREATE SEQUENCE user_roles_user_role_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 2   DROP SEQUENCE public.user_roles_user_role_id_seq;
       public       postgres    false    199    3            �           0    0    user_roles_user_role_id_seq    SEQUENCE OWNED BY     M   ALTER SEQUENCE user_roles_user_role_id_seq OWNED BY user_roles.user_role_id;
            public       postgres    false    200            �            1259    35385    users    TABLE     5  CREATE TABLE users (
    namalengkap text NOT NULL,
    username character varying(20) NOT NULL,
    password text NOT NULL,
    alamat text NOT NULL,
    ktp character varying(16) NOT NULL,
    telp character varying(12) NOT NULL,
    jeniskelamin character(1),
    enabled boolean DEFAULT false NOT NULL
);
    DROP TABLE public.users;
       public         postgres    false    3                       2604    35392    user_roles user_role_id    DEFAULT     t   ALTER TABLE ONLY user_roles ALTER COLUMN user_role_id SET DEFAULT nextval('user_roles_user_role_id_seq'::regclass);
 F   ALTER TABLE public.user_roles ALTER COLUMN user_role_id DROP DEFAULT;
       public       postgres    false    200    199            �          0    35319    cart 
   TABLE DATA               I   COPY cart (id_item, username, quantity, item_name, subtotal) FROM stdin;
    public       postgres    false    185   	d       �          0    35327    discount 
   TABLE DATA               u   COPY discount (id_disc, username, name_disc, percentage, start_date, end_date, status_disc, description) FROM stdin;
    public       postgres    false    187   fd       �          0    35335    item 
   TABLE DATA               �   COPY item (id_item, username, name_item, price_item, stock_item, image_item, merk_item, color_item, size_item, type_item, status_item) FROM stdin;
    public       postgres    false    188   �d       �          0    35342 
   item_color 
   TABLE DATA               K   COPY item_color (id_item_color, name_item_color, status_color) FROM stdin;
    public       postgres    false    189   f       �          0    35346 	   item_merk 
   TABLE DATA               G   COPY item_merk (id_item_merk, name_item_merk, status_merk) FROM stdin;
    public       postgres    false    190   |f       �          0    35350 	   item_type 
   TABLE DATA               G   COPY item_type (id_item_type, name_item_type, status_type) FROM stdin;
    public       postgres    false    191   �f       �          0    35356 	   orderline 
   TABLE DATA               ]   COPY orderline (id_orderline, id_trans, id_item, item_price, quantity, subtotal) FROM stdin;
    public       postgres    false    193   �f       �          0    35362    outcome 
   TABLE DATA               q   COPY outcome (id_outcome, username, amount_out, quantity_out, title_out, desc_out, date_out, status) FROM stdin;
    public       postgres    false    195   �g       �           0    0    sec_disc    SEQUENCE SET     2   SELECT pg_catalog.setval('sec_disc', 109, false);
            public       postgres    false    186            �           0    0    sec_item    SEQUENCE SET     2   SELECT pg_catalog.setval('sec_item', 123, false);
            public       postgres    false    196            �           0    0    sec_orl    SEQUENCE SET     =   SELECT pg_catalog.setval('sec_orl', 100000000000049, false);
            public       postgres    false    192            �           0    0    sec_outcome    SEQUENCE SET     5   SELECT pg_catalog.setval('sec_outcome', 106, false);
            public       postgres    false    194            �           0    0 	   sec_trans    SEQUENCE SET     <   SELECT pg_catalog.setval('sec_trans', 100000000040, false);
            public       postgres    false    197            �          0    35375    transaction 
   TABLE DATA               r   COPY transaction (id_trans, id_disc, username, customer_trans, total_trans, date_trans, status_trans) FROM stdin;
    public       postgres    false    198    h       �          0    35380 
   user_roles 
   TABLE DATA               ;   COPY user_roles (user_role_id, username, role) FROM stdin;
    public       postgres    false    199   �h       �           0    0    user_roles_user_role_id_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('user_roles_user_role_id_seq', 3, true);
            public       postgres    false    200            �          0    35385    users 
   TABLE DATA               c   COPY users (namalengkap, username, password, alamat, ktp, telp, jeniskelamin, enabled) FROM stdin;
    public       postgres    false    201   �h                  2606    35493    cart cart_id_item_username_pk 
   CONSTRAINT     ^   ALTER TABLE ONLY cart
    ADD CONSTRAINT cart_id_item_username_pk UNIQUE (id_item, username);
 G   ALTER TABLE ONLY public.cart DROP CONSTRAINT cart_id_item_username_pk;
       public         postgres    false    185    185    185                       2606    35394    discount pk_discount 
   CONSTRAINT     P   ALTER TABLE ONLY discount
    ADD CONSTRAINT pk_discount PRIMARY KEY (id_disc);
 >   ALTER TABLE ONLY public.discount DROP CONSTRAINT pk_discount;
       public         postgres    false    187    187            "           2606    35396    item pk_item 
   CONSTRAINT     H   ALTER TABLE ONLY item
    ADD CONSTRAINT pk_item PRIMARY KEY (id_item);
 6   ALTER TABLE ONLY public.item DROP CONSTRAINT pk_item;
       public         postgres    false    188    188            %           2606    35398    item_color pk_item_color 
   CONSTRAINT     Z   ALTER TABLE ONLY item_color
    ADD CONSTRAINT pk_item_color PRIMARY KEY (id_item_color);
 B   ALTER TABLE ONLY public.item_color DROP CONSTRAINT pk_item_color;
       public         postgres    false    189    189            (           2606    35400    item_merk pk_item_merk 
   CONSTRAINT     W   ALTER TABLE ONLY item_merk
    ADD CONSTRAINT pk_item_merk PRIMARY KEY (id_item_merk);
 @   ALTER TABLE ONLY public.item_merk DROP CONSTRAINT pk_item_merk;
       public         postgres    false    190    190            +           2606    35402    item_type pk_item_type 
   CONSTRAINT     W   ALTER TABLE ONLY item_type
    ADD CONSTRAINT pk_item_type PRIMARY KEY (id_item_type);
 @   ALTER TABLE ONLY public.item_type DROP CONSTRAINT pk_item_type;
       public         postgres    false    191    191            0           2606    35404    orderline pk_orderline 
   CONSTRAINT     W   ALTER TABLE ONLY orderline
    ADD CONSTRAINT pk_orderline PRIMARY KEY (id_orderline);
 @   ALTER TABLE ONLY public.orderline DROP CONSTRAINT pk_orderline;
       public         postgres    false    193    193            4           2606    35406    outcome pk_outcome 
   CONSTRAINT     Q   ALTER TABLE ONLY outcome
    ADD CONSTRAINT pk_outcome PRIMARY KEY (id_outcome);
 <   ALTER TABLE ONLY public.outcome DROP CONSTRAINT pk_outcome;
       public         postgres    false    195    195            9           2606    35408    transaction pk_transaction 
   CONSTRAINT     W   ALTER TABLE ONLY transaction
    ADD CONSTRAINT pk_transaction PRIMARY KEY (id_trans);
 D   ALTER TABLE ONLY public.transaction DROP CONSTRAINT pk_transaction;
       public         postgres    false    198    198            A           2606    35410    users pk_users 
   CONSTRAINT     K   ALTER TABLE ONLY users
    ADD CONSTRAINT pk_users PRIMARY KEY (username);
 8   ALTER TABLE ONLY public.users DROP CONSTRAINT pk_users;
       public         postgres    false    201    201            <           2606    35412    user_roles user_roles_pkey 
   CONSTRAINT     [   ALTER TABLE ONLY user_roles
    ADD CONSTRAINT user_roles_pkey PRIMARY KEY (user_role_id);
 D   ALTER TABLE ONLY public.user_roles DROP CONSTRAINT user_roles_pkey;
       public         postgres    false    199    199            >           2606    35414 '   user_roles user_roles_username_role_key 
   CONSTRAINT     e   ALTER TABLE ONLY user_roles
    ADD CONSTRAINT user_roles_username_role_key UNIQUE (username, role);
 Q   ALTER TABLE ONLY public.user_roles DROP CONSTRAINT user_roles_username_role_key;
       public         postgres    false    199    199    199                       1259    35415    add_discount_fk    INDEX     A   CREATE INDEX add_discount_fk ON discount USING btree (username);
 #   DROP INDEX public.add_discount_fk;
       public         postgres    false    187                       1259    35416    add_item_fk    INDEX     9   CREATE INDEX add_item_fk ON item USING btree (username);
    DROP INDEX public.add_item_fk;
       public         postgres    false    188            5           1259    35417    add_order_fk    INDEX     A   CREATE INDEX add_order_fk ON transaction USING btree (username);
     DROP INDEX public.add_order_fk;
       public         postgres    false    198            6           1259    35419    datetrans_index    INDEX     F   CREATE INDEX datetrans_index ON transaction USING btree (date_trans);
 #   DROP INDEX public.datetrans_index;
       public         postgres    false    198                       1259    35420    discount_pk    INDEX     C   CREATE UNIQUE INDEX discount_pk ON discount USING btree (id_disc);
    DROP INDEX public.discount_pk;
       public         postgres    false    187            1           1259    35421    emphasoutcome_fk    INDEX     A   CREATE INDEX emphasoutcome_fk ON outcome USING btree (username);
 $   DROP INDEX public.emphasoutcome_fk;
       public         postgres    false    195            ?           1259    35422    employee_pk    INDEX     A   CREATE UNIQUE INDEX employee_pk ON users USING btree (username);
    DROP INDEX public.employee_pk;
       public         postgres    false    201            7           1259    35423    has_fk    INDEX     :   CREATE INDEX has_fk ON transaction USING btree (id_disc);
    DROP INDEX public.has_fk;
       public         postgres    false    198                       1259    35424    item_color_fk    INDEX     =   CREATE INDEX item_color_fk ON item USING btree (color_item);
 !   DROP INDEX public.item_color_fk;
       public         postgres    false    188            #           1259    35425    item_color_pk    INDEX     M   CREATE UNIQUE INDEX item_color_pk ON item_color USING btree (id_item_color);
 !   DROP INDEX public.item_color_pk;
       public         postgres    false    189                       1259    35426    item_merk_fk    INDEX     ;   CREATE INDEX item_merk_fk ON item USING btree (merk_item);
     DROP INDEX public.item_merk_fk;
       public         postgres    false    188            &           1259    35427    item_merk_pk    INDEX     J   CREATE UNIQUE INDEX item_merk_pk ON item_merk USING btree (id_item_merk);
     DROP INDEX public.item_merk_pk;
       public         postgres    false    190                       1259    35428    item_pk    INDEX     ;   CREATE UNIQUE INDEX item_pk ON item USING btree (id_item);
    DROP INDEX public.item_pk;
       public         postgres    false    188                        1259    35429    item_type_fk    INDEX     ;   CREATE INDEX item_type_fk ON item USING btree (type_item);
     DROP INDEX public.item_type_fk;
       public         postgres    false    188            )           1259    35430    item_type_pk    INDEX     J   CREATE UNIQUE INDEX item_type_pk ON item_type USING btree (id_item_type);
     DROP INDEX public.item_type_pk;
       public         postgres    false    191            ,           1259    35431    itemhasorderline_fk    INDEX     E   CREATE INDEX itemhasorderline_fk ON orderline USING btree (id_item);
 '   DROP INDEX public.itemhasorderline_fk;
       public         postgres    false    193            -           1259    35432    orderhasorderline_fk    INDEX     G   CREATE INDEX orderhasorderline_fk ON orderline USING btree (id_trans);
 (   DROP INDEX public.orderhasorderline_fk;
       public         postgres    false    193            .           1259    35433    orderline_pk    INDEX     J   CREATE UNIQUE INDEX orderline_pk ON orderline USING btree (id_orderline);
     DROP INDEX public.orderline_pk;
       public         postgres    false    193            2           1259    35434 
   outcome_pk    INDEX     D   CREATE UNIQUE INDEX outcome_pk ON outcome USING btree (id_outcome);
    DROP INDEX public.outcome_pk;
       public         postgres    false    195            :           1259    35435    transaction_pk    INDEX     J   CREATE UNIQUE INDEX transaction_pk ON transaction USING btree (id_trans);
 "   DROP INDEX public.transaction_pk;
       public         postgres    false    198            B           2606    35436 '   discount fk_discount_add_disco_employee    FK CONSTRAINT     �   ALTER TABLE ONLY discount
    ADD CONSTRAINT fk_discount_add_disco_employee FOREIGN KEY (username) REFERENCES users(username) ON UPDATE RESTRICT ON DELETE RESTRICT;
 Q   ALTER TABLE ONLY public.discount DROP CONSTRAINT fk_discount_add_disco_employee;
       public       postgres    false    187    2113    201            C           2606    35441    item fk_item_add_item_employee    FK CONSTRAINT     �   ALTER TABLE ONLY item
    ADD CONSTRAINT fk_item_add_item_employee FOREIGN KEY (username) REFERENCES users(username) ON UPDATE RESTRICT ON DELETE RESTRICT;
 H   ALTER TABLE ONLY public.item DROP CONSTRAINT fk_item_add_item_employee;
       public       postgres    false    188    2113    201            D           2606    35446    item fk_item_color    FK CONSTRAINT     �   ALTER TABLE ONLY item
    ADD CONSTRAINT fk_item_color FOREIGN KEY (color_item) REFERENCES item_color(id_item_color) ON UPDATE RESTRICT ON DELETE RESTRICT;
 <   ALTER TABLE ONLY public.item DROP CONSTRAINT fk_item_color;
       public       postgres    false    189    2085    188            E           2606    35451    item fk_item_merk    FK CONSTRAINT     �   ALTER TABLE ONLY item
    ADD CONSTRAINT fk_item_merk FOREIGN KEY (merk_item) REFERENCES item_merk(id_item_merk) ON UPDATE RESTRICT ON DELETE RESTRICT;
 ;   ALTER TABLE ONLY public.item DROP CONSTRAINT fk_item_merk;
       public       postgres    false    190    2088    188            F           2606    35456    item fk_item_type    FK CONSTRAINT     �   ALTER TABLE ONLY item
    ADD CONSTRAINT fk_item_type FOREIGN KEY (type_item) REFERENCES item_type(id_item_type) ON UPDATE RESTRICT ON DELETE RESTRICT;
 ;   ALTER TABLE ONLY public.item DROP CONSTRAINT fk_item_type;
       public       postgres    false    191    2091    188            G           2606    35461 $   orderline fk_orderlin_itemhasor_item    FK CONSTRAINT     �   ALTER TABLE ONLY orderline
    ADD CONSTRAINT fk_orderlin_itemhasor_item FOREIGN KEY (id_item) REFERENCES item(id_item) ON UPDATE RESTRICT ON DELETE RESTRICT;
 N   ALTER TABLE ONLY public.orderline DROP CONSTRAINT fk_orderlin_itemhasor_item;
       public       postgres    false    188    2082    193            H           2606    35466 (   orderline fk_orderlin_orderhaso_transact    FK CONSTRAINT     �   ALTER TABLE ONLY orderline
    ADD CONSTRAINT fk_orderlin_orderhaso_transact FOREIGN KEY (id_trans) REFERENCES transaction(id_trans) ON UPDATE RESTRICT ON DELETE RESTRICT;
 R   ALTER TABLE ONLY public.orderline DROP CONSTRAINT fk_orderlin_orderhaso_transact;
       public       postgres    false    198    2105    193            I           2606    35471 %   outcome fk_outcome_emphasout_employee    FK CONSTRAINT     �   ALTER TABLE ONLY outcome
    ADD CONSTRAINT fk_outcome_emphasout_employee FOREIGN KEY (username) REFERENCES users(username) ON UPDATE RESTRICT ON DELETE RESTRICT;
 O   ALTER TABLE ONLY public.outcome DROP CONSTRAINT fk_outcome_emphasout_employee;
       public       postgres    false    195    201    2113            J           2606    35476 *   transaction fk_transact_add_order_employee    FK CONSTRAINT     �   ALTER TABLE ONLY transaction
    ADD CONSTRAINT fk_transact_add_order_employee FOREIGN KEY (username) REFERENCES users(username) ON UPDATE RESTRICT ON DELETE RESTRICT;
 T   ALTER TABLE ONLY public.transaction DROP CONSTRAINT fk_transact_add_order_employee;
       public       postgres    false    201    198    2113            K           2606    35481 $   transaction fk_transact_has_discount    FK CONSTRAINT     �   ALTER TABLE ONLY transaction
    ADD CONSTRAINT fk_transact_has_discount FOREIGN KEY (id_disc) REFERENCES discount(id_disc) ON UPDATE RESTRICT ON DELETE RESTRICT;
 N   ALTER TABLE ONLY public.transaction DROP CONSTRAINT fk_transact_has_discount;
       public       postgres    false    2075    198    187            L           2606    35486 #   user_roles user_roles_username_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY user_roles
    ADD CONSTRAINT user_roles_username_fkey FOREIGN KEY (username) REFERENCES users(username) ON UPDATE CASCADE ON DELETE CASCADE;
 M   ALTER TABLE ONLY public.user_roles DROP CONSTRAINT user_roles_username_fkey;
       public       postgres    false    201    2113    199            �   M   x�344��u�
���,N,�LLI,J4�4��N�/V.-(J�MU(-���445 .CCS�4yd�$��4��qqq �b      �   �   x�s�v�540�,N,�LLI,J�t�,���S04P�44�420��50"8�ؐ�1�$3�3 5'1/==1O!75/%� �$�N���r��n��tS���n
7��t#�ۍ(p���XL�݈��s��qqq �k/      �     x����n�0��O��xp*\NMF,D\�Œ�U;D���~�d'�����M�~��"�E�Z�e�"2�e�%sYIX��<I7�y^�?
7U�Z!�h�.�^�#��>/{0+X�� ��E�wzH���!�*`z����-Fb1<:f�`i�s�C=������ҵX<�Z"�<�nv�T�Tnzm���� f�&4f� =c�SU���;~�%����s��LB�ȒL�`Q2	7�������� \b��UK����+؟�`ҡ�~Wx9�� 0�&      �   P   x�s

�t�,*�t�.�L�r
rs�Ss���A���E�P~��7g@f^6���PZ�	������,I̅�c���� ��      �   3   x��.-(J�M�t�.�L��vq���LI)�OΆ	E�p�0����� ���      �   (   x�s�
�tJ�*�t�.�L�r���tN�I�K�
��qqq �
C      �   }   x����@0�s�.��ۂ+N�����?��2D��.��k��H2(��5���Ǣ�2�3��w�y����772���tCg¦�����7���͡^!�+\E���p�,����)[����!f��U      �   l   x���540�,N,�LLI,J�46 NCN�ԜL�Ԣ��bGC���\N���$�xi^Ii�BvjRiIiFb�BrjIb�B^~I"����������1�cvIfW� cy�      �   z   x�34�cKN�`g]CS��Ē�ĔĢDN�ԢJNC�N#C]C]cN���4.C�fs,�8�3K29��	귀�7C�ܱ"5'�����vClnJL�,�410E���;F��� �`=�      �   6   x�3�,N,�LLI,J��u�stw�2�,JL�,�D�!Tp:;{xc���� p�
      �     x�u�?{�@���̖�Ci�zT+�H}�D�@�x����c�I�_�AT��$�R���A3�d�ȫ�1�\\�wۨ����^G�C�8�1�3C_�pA�:����!r�����	UC��Q�)&h����c�����,��'S�������a8��xv�L�.�qM*�$��o�e��{����}G��Z)w�R��A������2�X˓�B��2�6Z<֋�`s�4��S��u���e�c����Z>T������"�6�#}�%I�mml�     