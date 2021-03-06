PGDMP     '                     v         
   distro_pos    9.6.5    9.6.5 V    �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                       false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                       false            �           1262    18259 
   distro_pos    DATABASE     �   CREATE DATABASE distro_pos WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'Indonesian_Indonesia.1252' LC_CTYPE = 'Indonesian_Indonesia.1252';
    DROP DATABASE distro_pos;
             postgres    false                        2615    2200    public    SCHEMA        CREATE SCHEMA public;
    DROP SCHEMA public;
             postgres    false            �           0    0    SCHEMA public    COMMENT     6   COMMENT ON SCHEMA public IS 'standard public schema';
                  postgres    false    3                        3079    12387    plpgsql 	   EXTENSION     ?   CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;
    DROP EXTENSION plpgsql;
                  false            �           0    0    EXTENSION plpgsql    COMMENT     @   COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';
                       false    1            �            1259    18418    cart    TABLE     �   CREATE TABLE cart (
    id_item character varying(30) NOT NULL,
    username character varying(20) NOT NULL,
    quantity integer NOT NULL,
    item_name text,
    subtotal double precision NOT NULL
);
    DROP TABLE public.cart;
       public         postgres    false    3            �            1259    18260    sec_disc    SEQUENCE     l   CREATE SEQUENCE sec_disc
    START WITH 101
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
    DROP SEQUENCE public.sec_disc;
       public       postgres    false    3            �            1259    18262    discount    TABLE     �  CREATE TABLE discount (
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
       public         postgres    false    185    3            �            1259    18283    item    TABLE       CREATE TABLE item (
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
       public         postgres    false    3            �            1259    18339 
   item_color    TABLE     �   CREATE TABLE item_color (
    id_item_color character varying(3) NOT NULL,
    name_item_color character varying(25) NOT NULL,
    status_color character varying(25) DEFAULT 'Aktif'::character varying
);
    DROP TABLE public.item_color;
       public         postgres    false    3            �            1259    18353 	   item_merk    TABLE     �   CREATE TABLE item_merk (
    id_item_merk character varying(3) NOT NULL,
    name_item_merk character varying(25) NOT NULL,
    status_merk character varying(25) DEFAULT 'Aktif'::character varying
);
    DROP TABLE public.item_merk;
       public         postgres    false    3            �            1259    18346 	   item_type    TABLE     �   CREATE TABLE item_type (
    id_item_type character varying(3) NOT NULL,
    name_item_type character varying(25) NOT NULL,
    status_type character varying(25) DEFAULT 'Aktif'::character varying
);
    DROP TABLE public.item_type;
       public         postgres    false    3            �            1259    18294    sec_orl    SEQUENCE     w   CREATE SEQUENCE sec_orl
    START WITH 100000000000001
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
    DROP SEQUENCE public.sec_orl;
       public       postgres    false    3            �            1259    18296 	   orderline    TABLE     M  CREATE TABLE orderline (
    id_orderline character varying(20) DEFAULT ('ORL-'::text || nextval('sec_orl'::regclass)) NOT NULL,
    id_trans character varying(12) NOT NULL,
    id_item character varying(30) NOT NULL,
    item_price double precision NOT NULL,
    quantity integer NOT NULL,
    subtotal double precision NOT NULL
);
    DROP TABLE public.orderline;
       public         postgres    false    190    3            �            1259    18305    sec_outcome    SEQUENCE     o   CREATE SEQUENCE sec_outcome
    START WITH 101
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 "   DROP SEQUENCE public.sec_outcome;
       public       postgres    false    3            �            1259    18307    outcome    TABLE     �  CREATE TABLE outcome (
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
       public         postgres    false    192    3            �            1259    18281    sec_item    SEQUENCE     l   CREATE SEQUENCE sec_item
    START WITH 101
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
    DROP SEQUENCE public.sec_item;
       public       postgres    false    3            �            1259    18330 	   sec_trans    SEQUENCE     v   CREATE SEQUENCE sec_trans
    START WITH 100000000001
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
     DROP SEQUENCE public.sec_trans;
       public       postgres    false    3            �            1259    18332    transaction    TABLE     �  CREATE TABLE transaction (
    id_trans character varying(12) DEFAULT nextval('sec_trans'::regclass) NOT NULL,
    id_disc character varying(10),
    username character varying(20) NOT NULL,
    customer_trans character varying(50) NOT NULL,
    total_trans double precision NOT NULL,
    date_trans date NOT NULL,
    status_trans character varying(15) DEFAULT 'Aktif'::character varying NOT NULL
);
    DROP TABLE public.transaction;
       public         postgres    false    196    3            �            1259    18317 
   user_roles    TABLE     �   CREATE TABLE user_roles (
    user_role_id integer NOT NULL,
    username character varying(20) NOT NULL,
    role character varying(20) NOT NULL
);
    DROP TABLE public.user_roles;
       public         postgres    false    3            �            1259    18315    user_roles_user_role_id_seq    SEQUENCE     }   CREATE SEQUENCE user_roles_user_role_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 2   DROP SEQUENCE public.user_roles_user_role_id_seq;
       public       postgres    false    3    195            �           0    0    user_roles_user_role_id_seq    SEQUENCE OWNED BY     M   ALTER SEQUENCE user_roles_user_role_id_seq OWNED BY user_roles.user_role_id;
            public       postgres    false    194            �            1259    18271    users    TABLE     5  CREATE TABLE users (
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
       public         postgres    false    3                       2604    18320    user_roles user_role_id    DEFAULT     t   ALTER TABLE ONLY user_roles ALTER COLUMN user_role_id SET DEFAULT nextval('user_roles_user_role_id_seq'::regclass);
 F   ALTER TABLE public.user_roles ALTER COLUMN user_role_id DROP DEFAULT;
       public       postgres    false    195    194    195            �          0    18418    cart 
   TABLE DATA               I   COPY cart (id_item, username, quantity, item_name, subtotal) FROM stdin;
    public       postgres    false    201   �c       �          0    18262    discount 
   TABLE DATA               u   COPY discount (id_disc, username, name_disc, percentage, start_date, end_date, status_disc, description) FROM stdin;
    public       postgres    false    186   d       �          0    18283    item 
   TABLE DATA               �   COPY item (id_item, username, name_item, price_item, stock_item, image_item, merk_item, color_item, size_item, type_item, status_item) FROM stdin;
    public       postgres    false    189   �d       �          0    18339 
   item_color 
   TABLE DATA               K   COPY item_color (id_item_color, name_item_color, status_color) FROM stdin;
    public       postgres    false    198   *f       �          0    18353 	   item_merk 
   TABLE DATA               G   COPY item_merk (id_item_merk, name_item_merk, status_merk) FROM stdin;
    public       postgres    false    200   `f       �          0    18346 	   item_type 
   TABLE DATA               G   COPY item_type (id_item_type, name_item_type, status_type) FROM stdin;
    public       postgres    false    199   �f       �          0    18296 	   orderline 
   TABLE DATA               ]   COPY orderline (id_orderline, id_trans, id_item, item_price, quantity, subtotal) FROM stdin;
    public       postgres    false    191   �f       �          0    18307    outcome 
   TABLE DATA               q   COPY outcome (id_outcome, username, amount_out, quantity_out, title_out, desc_out, date_out, status) FROM stdin;
    public       postgres    false    193   @h       �           0    0    sec_disc    SEQUENCE SET     1   SELECT pg_catalog.setval('sec_disc', 104, true);
            public       postgres    false    185            �           0    0    sec_item    SEQUENCE SET     1   SELECT pg_catalog.setval('sec_item', 112, true);
            public       postgres    false    188            �           0    0    sec_orl    SEQUENCE SET     <   SELECT pg_catalog.setval('sec_orl', 100000000000033, true);
            public       postgres    false    190            �           0    0    sec_outcome    SEQUENCE SET     4   SELECT pg_catalog.setval('sec_outcome', 103, true);
            public       postgres    false    192            �           0    0 	   sec_trans    SEQUENCE SET     ;   SELECT pg_catalog.setval('sec_trans', 100000000029, true);
            public       postgres    false    196            �          0    18332    transaction 
   TABLE DATA               r   COPY transaction (id_trans, id_disc, username, customer_trans, total_trans, date_trans, status_trans) FROM stdin;
    public       postgres    false    197   �h       �          0    18317 
   user_roles 
   TABLE DATA               ;   COPY user_roles (user_role_id, username, role) FROM stdin;
    public       postgres    false    195   j       �           0    0    user_roles_user_role_id_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('user_roles_user_role_id_seq', 3, true);
            public       postgres    false    194            �          0    18271    users 
   TABLE DATA               c   COPY users (namalengkap, username, password, alamat, ktp, telp, jeniskelamin, enabled) FROM stdin;
    public       postgres    false    187   Zj                  2606    18268    discount pk_discount 
   CONSTRAINT     P   ALTER TABLE ONLY discount
    ADD CONSTRAINT pk_discount PRIMARY KEY (id_disc);
 >   ALTER TABLE ONLY public.discount DROP CONSTRAINT pk_discount;
       public         postgres    false    186    186            #           2606    18291    item pk_item 
   CONSTRAINT     H   ALTER TABLE ONLY item
    ADD CONSTRAINT pk_item PRIMARY KEY (id_item);
 6   ALTER TABLE ONLY public.item DROP CONSTRAINT pk_item;
       public         postgres    false    189    189            9           2606    18344    item_color pk_item_color 
   CONSTRAINT     Z   ALTER TABLE ONLY item_color
    ADD CONSTRAINT pk_item_color PRIMARY KEY (id_item_color);
 B   ALTER TABLE ONLY public.item_color DROP CONSTRAINT pk_item_color;
       public         postgres    false    198    198            ?           2606    18358    item_merk pk_item_merk 
   CONSTRAINT     W   ALTER TABLE ONLY item_merk
    ADD CONSTRAINT pk_item_merk PRIMARY KEY (id_item_merk);
 @   ALTER TABLE ONLY public.item_merk DROP CONSTRAINT pk_item_merk;
       public         postgres    false    200    200            <           2606    18351    item_type pk_item_type 
   CONSTRAINT     W   ALTER TABLE ONLY item_type
    ADD CONSTRAINT pk_item_type PRIMARY KEY (id_item_type);
 @   ALTER TABLE ONLY public.item_type DROP CONSTRAINT pk_item_type;
       public         postgres    false    199    199            (           2606    26444    orderline pk_orderline 
   CONSTRAINT     W   ALTER TABLE ONLY orderline
    ADD CONSTRAINT pk_orderline PRIMARY KEY (id_orderline);
 @   ALTER TABLE ONLY public.orderline DROP CONSTRAINT pk_orderline;
       public         postgres    false    191    191            ,           2606    18312    outcome pk_outcome 
   CONSTRAINT     Q   ALTER TABLE ONLY outcome
    ADD CONSTRAINT pk_outcome PRIMARY KEY (id_outcome);
 <   ALTER TABLE ONLY public.outcome DROP CONSTRAINT pk_outcome;
       public         postgres    false    193    193            5           2606    18337    transaction pk_transaction 
   CONSTRAINT     W   ALTER TABLE ONLY transaction
    ADD CONSTRAINT pk_transaction PRIMARY KEY (id_trans);
 D   ALTER TABLE ONLY public.transaction DROP CONSTRAINT pk_transaction;
       public         postgres    false    197    197                       2606    18279    users pk_users 
   CONSTRAINT     K   ALTER TABLE ONLY users
    ADD CONSTRAINT pk_users PRIMARY KEY (username);
 8   ALTER TABLE ONLY public.users DROP CONSTRAINT pk_users;
       public         postgres    false    187    187            .           2606    18322    user_roles user_roles_pkey 
   CONSTRAINT     [   ALTER TABLE ONLY user_roles
    ADD CONSTRAINT user_roles_pkey PRIMARY KEY (user_role_id);
 D   ALTER TABLE ONLY public.user_roles DROP CONSTRAINT user_roles_pkey;
       public         postgres    false    195    195            0           2606    18324 '   user_roles user_roles_username_role_key 
   CONSTRAINT     e   ALTER TABLE ONLY user_roles
    ADD CONSTRAINT user_roles_username_role_key UNIQUE (username, role);
 Q   ALTER TABLE ONLY public.user_roles DROP CONSTRAINT user_roles_username_role_key;
       public         postgres    false    195    195    195                       1259    18270    add_discount_fk    INDEX     A   CREATE INDEX add_discount_fk ON discount USING btree (username);
 #   DROP INDEX public.add_discount_fk;
       public         postgres    false    186                       1259    18293    add_item_fk    INDEX     9   CREATE INDEX add_item_fk ON item USING btree (username);
    DROP INDEX public.add_item_fk;
       public         postgres    false    189            1           1259    18361    add_order_fk    INDEX     A   CREATE INDEX add_order_fk ON transaction USING btree (username);
     DROP INDEX public.add_order_fk;
       public         postgres    false    197            @           1259    26429    cart_id_item_uindex    INDEX     G   CREATE UNIQUE INDEX cart_id_item_uindex ON cart USING btree (id_item);
 '   DROP INDEX public.cart_id_item_uindex;
       public         postgres    false    201            2           1259    26446    datetrans_index    INDEX     F   CREATE INDEX datetrans_index ON transaction USING btree (date_trans);
 #   DROP INDEX public.datetrans_index;
       public         postgres    false    197                       1259    18269    discount_pk    INDEX     C   CREATE UNIQUE INDEX discount_pk ON discount USING btree (id_disc);
    DROP INDEX public.discount_pk;
       public         postgres    false    186            )           1259    18314    emphasoutcome_fk    INDEX     A   CREATE INDEX emphasoutcome_fk ON outcome USING btree (username);
 $   DROP INDEX public.emphasoutcome_fk;
       public         postgres    false    193                       1259    18280    employee_pk    INDEX     A   CREATE UNIQUE INDEX employee_pk ON users USING btree (username);
    DROP INDEX public.employee_pk;
       public         postgres    false    187            3           1259    18360    has_fk    INDEX     :   CREATE INDEX has_fk ON transaction USING btree (id_disc);
    DROP INDEX public.has_fk;
       public         postgres    false    197                       1259    18362    item_color_fk    INDEX     =   CREATE INDEX item_color_fk ON item USING btree (color_item);
 !   DROP INDEX public.item_color_fk;
       public         postgres    false    189            7           1259    18345    item_color_pk    INDEX     M   CREATE UNIQUE INDEX item_color_pk ON item_color USING btree (id_item_color);
 !   DROP INDEX public.item_color_pk;
       public         postgres    false    198                       1259    18364    item_merk_fk    INDEX     ;   CREATE INDEX item_merk_fk ON item USING btree (merk_item);
     DROP INDEX public.item_merk_fk;
       public         postgres    false    189            =           1259    18359    item_merk_pk    INDEX     J   CREATE UNIQUE INDEX item_merk_pk ON item_merk USING btree (id_item_merk);
     DROP INDEX public.item_merk_pk;
       public         postgres    false    200                        1259    18292    item_pk    INDEX     ;   CREATE UNIQUE INDEX item_pk ON item USING btree (id_item);
    DROP INDEX public.item_pk;
       public         postgres    false    189            !           1259    18363    item_type_fk    INDEX     ;   CREATE INDEX item_type_fk ON item USING btree (type_item);
     DROP INDEX public.item_type_fk;
       public         postgres    false    189            :           1259    18352    item_type_pk    INDEX     J   CREATE UNIQUE INDEX item_type_pk ON item_type USING btree (id_item_type);
     DROP INDEX public.item_type_pk;
       public         postgres    false    199            $           1259    18303    itemhasorderline_fk    INDEX     E   CREATE INDEX itemhasorderline_fk ON orderline USING btree (id_item);
 '   DROP INDEX public.itemhasorderline_fk;
       public         postgres    false    191            %           1259    18304    orderhasorderline_fk    INDEX     G   CREATE INDEX orderhasorderline_fk ON orderline USING btree (id_trans);
 (   DROP INDEX public.orderhasorderline_fk;
       public         postgres    false    191            &           1259    26445    orderline_pk    INDEX     J   CREATE UNIQUE INDEX orderline_pk ON orderline USING btree (id_orderline);
     DROP INDEX public.orderline_pk;
       public         postgres    false    191            *           1259    18313 
   outcome_pk    INDEX     D   CREATE UNIQUE INDEX outcome_pk ON outcome USING btree (id_outcome);
    DROP INDEX public.outcome_pk;
       public         postgres    false    193            6           1259    18338    transaction_pk    INDEX     J   CREATE UNIQUE INDEX transaction_pk ON transaction USING btree (id_trans);
 "   DROP INDEX public.transaction_pk;
       public         postgres    false    197            A           2606    18365 '   discount fk_discount_add_disco_employee    FK CONSTRAINT     �   ALTER TABLE ONLY discount
    ADD CONSTRAINT fk_discount_add_disco_employee FOREIGN KEY (username) REFERENCES users(username) ON UPDATE RESTRICT ON DELETE RESTRICT;
 Q   ALTER TABLE ONLY public.discount DROP CONSTRAINT fk_discount_add_disco_employee;
       public       postgres    false    2076    187    186            B           2606    18370    item fk_item_add_item_employee    FK CONSTRAINT     �   ALTER TABLE ONLY item
    ADD CONSTRAINT fk_item_add_item_employee FOREIGN KEY (username) REFERENCES users(username) ON UPDATE RESTRICT ON DELETE RESTRICT;
 H   ALTER TABLE ONLY public.item DROP CONSTRAINT fk_item_add_item_employee;
       public       postgres    false    189    187    2076            C           2606    18375    item fk_item_color    FK CONSTRAINT     �   ALTER TABLE ONLY item
    ADD CONSTRAINT fk_item_color FOREIGN KEY (color_item) REFERENCES item_color(id_item_color) ON UPDATE RESTRICT ON DELETE RESTRICT;
 <   ALTER TABLE ONLY public.item DROP CONSTRAINT fk_item_color;
       public       postgres    false    198    2105    189            E           2606    18385    item fk_item_merk    FK CONSTRAINT     �   ALTER TABLE ONLY item
    ADD CONSTRAINT fk_item_merk FOREIGN KEY (merk_item) REFERENCES item_merk(id_item_merk) ON UPDATE RESTRICT ON DELETE RESTRICT;
 ;   ALTER TABLE ONLY public.item DROP CONSTRAINT fk_item_merk;
       public       postgres    false    189    2111    200            D           2606    18380    item fk_item_type    FK CONSTRAINT     �   ALTER TABLE ONLY item
    ADD CONSTRAINT fk_item_type FOREIGN KEY (type_item) REFERENCES item_type(id_item_type) ON UPDATE RESTRICT ON DELETE RESTRICT;
 ;   ALTER TABLE ONLY public.item DROP CONSTRAINT fk_item_type;
       public       postgres    false    2108    189    199            F           2606    18390 $   orderline fk_orderlin_itemhasor_item    FK CONSTRAINT     �   ALTER TABLE ONLY orderline
    ADD CONSTRAINT fk_orderlin_itemhasor_item FOREIGN KEY (id_item) REFERENCES item(id_item) ON UPDATE RESTRICT ON DELETE RESTRICT;
 N   ALTER TABLE ONLY public.orderline DROP CONSTRAINT fk_orderlin_itemhasor_item;
       public       postgres    false    191    2083    189            G           2606    18395 (   orderline fk_orderlin_orderhaso_transact    FK CONSTRAINT     �   ALTER TABLE ONLY orderline
    ADD CONSTRAINT fk_orderlin_orderhaso_transact FOREIGN KEY (id_trans) REFERENCES transaction(id_trans) ON UPDATE RESTRICT ON DELETE RESTRICT;
 R   ALTER TABLE ONLY public.orderline DROP CONSTRAINT fk_orderlin_orderhaso_transact;
       public       postgres    false    2101    191    197            H           2606    18400 %   outcome fk_outcome_emphasout_employee    FK CONSTRAINT     �   ALTER TABLE ONLY outcome
    ADD CONSTRAINT fk_outcome_emphasout_employee FOREIGN KEY (username) REFERENCES users(username) ON UPDATE RESTRICT ON DELETE RESTRICT;
 O   ALTER TABLE ONLY public.outcome DROP CONSTRAINT fk_outcome_emphasout_employee;
       public       postgres    false    193    2076    187            J           2606    18405 *   transaction fk_transact_add_order_employee    FK CONSTRAINT     �   ALTER TABLE ONLY transaction
    ADD CONSTRAINT fk_transact_add_order_employee FOREIGN KEY (username) REFERENCES users(username) ON UPDATE RESTRICT ON DELETE RESTRICT;
 T   ALTER TABLE ONLY public.transaction DROP CONSTRAINT fk_transact_add_order_employee;
       public       postgres    false    187    197    2076            K           2606    18410 $   transaction fk_transact_has_discount    FK CONSTRAINT     �   ALTER TABLE ONLY transaction
    ADD CONSTRAINT fk_transact_has_discount FOREIGN KEY (id_disc) REFERENCES discount(id_disc) ON UPDATE RESTRICT ON DELETE RESTRICT;
 N   ALTER TABLE ONLY public.transaction DROP CONSTRAINT fk_transact_has_discount;
       public       postgres    false    186    197    2073            I           2606    18325 #   user_roles user_roles_username_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY user_roles
    ADD CONSTRAINT user_roles_username_fkey FOREIGN KEY (username) REFERENCES users(username) ON UPDATE CASCADE ON DELETE CASCADE;
 M   ALTER TABLE ONLY public.user_roles DROP CONSTRAINT user_roles_username_fkey;
       public       postgres    false    195    2076    187            �   X   x�340���v����ҍ��,N,�LLI,J4�4�t�y�
�Y��I�&\�f��.��N^��������99����ff\1z\\\ |J�      �   �   x��α
�0����)��ܥ*�b*�Ҏ.�����������~�]Y�4�Q�C�.�m�)2��ZkZ}O&ض1\�᩿��"$C���s���	��/��}��\*��b�_i	��)�X�ih��g�Q�(7uw����Q      �   X  x����n�0���S�0-�Kɖ-�z�3sɒ�,TVŏ���_)�l"$��s~9�Oat��g��LL���X�`�B���L����Q
<�Tn��+� ϠtLv��x�r�4Ҳ�Q�5�&b����
�����LW� �P�L�!0νI�n��J3��[$-��D��H64v��&�<��[�7K�ܩ�%~C���{�ȩ���l��,�xuK�N�/���I"H���4ِ!m3
�V�gc�I�اBf�:��25�͞-��Rܑ��#KK�^����c&?���������ݖ�l�Oı0�_T�����Z2MM����D]�O�ϋ�}����y���?      �   &   x�����M-J��t�.�L�r

�t�,*�rc���� �,
      �   (   x���v����N�t�.�L�rt	�tL�LI,�
��qqq �
B      �   '   x������N�M�J�t�.�L�r�
�tJ�*�rc���� �C
Q      �   a  x����j�0E��I�dG�7��6[ae����w�v�Pd�-�pcs�{�K��^?���秗��|��oΟ���QH"I|t��Aº���%�tmj���v�$b�с�¡�s��+�*���k��
��>�
��G\�c]E[�����G\�����v�����R
��J���q��r_�N/��Z�~���	S����M���{�:�X���kE��r��wX�ls-9܋���*9�\,��!Ojp�N&���b��lյ/n��ڃ�jMI�~�����<t�-܌�R~��X����[4쒕5�����ԥ��U$��u� �-�v
F��e�h�<'Zx�>M���Y�      �   �   x�u�A
�0EדS�"����Tܸ5���m(��D*v�ß����������_�gX�<@�j���q��O��J���`�Gw�'N��Q�����!e�hC�f@Q,��Y@*U�
�#^�lq���'������Er���63��B�eA`      �     x��Աn� ��y"ۀ��C�.��HU�����o_%G
$'��-��Cx{�_�k��Lo��`����1�<�Q)bC�.׏��J��h��`¦ �$qc�B�I:�@�2/&��+�}�/x�A$��hw �]�����.��
t]X�j��@߅u�V(J�)Z�θw<\秾IٹI�c�B�x/;3��g� �'Ko0���:<�C��G��^�G��Cd�Q��n��������ov�$	�uBK��e��#�4tz~=m���cf1_9����0��_C      �   6   x�3�,N,�LLI,J��u�stw�2�,JL�,�D�!Tp:;{xc���� p�
      �     x�u�?{�@���̖�Ci�zT+�H}�D�@�x����c�I�_�AT��$�R���A3�d�ȫ�1�\\�wۨ����^G�C�8�1�3C_�pA�:����!r�����	UC��Q�)&h����c�����,��'S�������a8��xv�L�.�qM*�$��o�e��{����}G��Z)w�R��A������2�X˓�B��2�6Z<֋�`s�4��S��u���e�c����Z>T������"�6�#}�%I�mml�     