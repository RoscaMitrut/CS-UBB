PGDMP  (    3                 |            postgres    16.0    16.0     �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    5    postgres    DATABASE     �   CREATE DATABASE postgres WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_United States.1252';
    DROP DATABASE postgres;
                postgres    false            �           0    0    DATABASE postgres    COMMENT     N   COMMENT ON DATABASE postgres IS 'default administrative connection database';
                   postgres    false    4793                        3079    16384 	   adminpack 	   EXTENSION     A   CREATE EXTENSION IF NOT EXISTS adminpack WITH SCHEMA pg_catalog;
    DROP EXTENSION adminpack;
                   false            �           0    0    EXTENSION adminpack    COMMENT     M   COMMENT ON EXTENSION adminpack IS 'administrative functions for PostgreSQL';
                        false    2            �            1259    16421    friendships    TABLE     �   CREATE TABLE public.friendships (
    date timestamp with time zone,
    first_user character varying,
    second_user character varying,
    status character varying
);
    DROP TABLE public.friendships;
       public         heap    postgres    false            �            1259    16426    messages    TABLE     �   CREATE TABLE public.messages (
    text character varying,
    "timestamp" timestamp with time zone,
    sender character varying,
    receiver character varying,
    id character varying
);
    DROP TABLE public.messages;
       public         heap    postgres    false            �            1259    16416    users    TABLE     �   CREATE TABLE public.users (
    username character varying,
    first_name character varying,
    last_name character varying,
    email character varying,
    password character varying
);
    DROP TABLE public.users;
       public         heap    postgres    false            �          0    16421    friendships 
   TABLE DATA           L   COPY public.friendships (date, first_user, second_user, status) FROM stdin;
    public          postgres    false    217   P       �          0    16426    messages 
   TABLE DATA           K   COPY public.messages (text, "timestamp", sender, receiver, id) FROM stdin;
    public          postgres    false    218   �       �          0    16416    users 
   TABLE DATA           Q   COPY public.users (username, first_name, last_name, email, password) FROM stdin;
    public          postgres    false    216   O       �      x���;�0���>=�jw�k;>M�X�+�q�P�����4?:�bp�����zkӵ�{�[k[.�챗���$�Y�2������}	7�s]u��c�X��'�׾�Q2pB_~jO�� S�O�      �   `   x�+N�)-Q�4202�50�54U02�2��2�г00007�60�,-N-�K�ME�bp!�!hf�pedfdbZcj�gj``bdH�5p3@���qqq u8o      �   �   x���ͪ�@����Z�B�� Vԫ���X�:�L��Թާ�UKq%�H�	|'9�fJ�`Pq�5�A�g/�/՘l����u�-=���4�-{���NV�M�Ij��p	���p���7N.�A1�C%�Cy'2�{ԋ03J�QRJ���������^�>m��S�XM�0���l�#�)A���#t���t�y�C�����9��:��Ey�[     