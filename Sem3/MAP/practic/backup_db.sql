PGDMP  *                     |            practic    16.0    16.0 
    �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    16463    practic    DATABASE     �   CREATE DATABASE practic WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_United States.1252';
    DROP DATABASE practic;
                postgres    false            �            1259    16464    clienti    TABLE     k   CREATE TABLE public.clienti (
    id bigint,
    username character varying,
    name character varying
);
    DROP TABLE public.clienti;
       public         heap    postgres    false            �            1259    16474    tichete    TABLE     �   CREATE TABLE public.tichete (
    id bigint,
    username character varying,
    flightid bigint,
    purchasetime timestamp without time zone
);
    DROP TABLE public.tichete;
       public         heap    postgres    false            �            1259    16469    zboruri    TABLE     �   CREATE TABLE public.zboruri (
    id bigint,
    departuretime timestamp without time zone,
    landingtime timestamp without time zone,
    seats integer,
    dela character varying,
    panala character varying
);
    DROP TABLE public.zboruri;
       public         heap    postgres    false            �          0    16464    clienti 
   TABLE DATA           5   COPY public.clienti (id, username, name) FROM stdin;
    public          postgres    false    215   g	       �          0    16474    tichete 
   TABLE DATA           G   COPY public.tichete (id, username, flightid, purchasetime) FROM stdin;
    public          postgres    false    217   �	       �          0    16469    zboruri 
   TABLE DATA           V   COPY public.zboruri (id, departuretime, landingtime, seats, dela, panala) FROM stdin;
    public          postgres    false    216   �
       �   H   x��I
�0�s�c��xi�fL">_�Z3��r��+���4��t@�Ts�Ⱥ]�0�>�A�����D�1n�      �   �   x�}�;NAD��Sp�iU�����d%V�����B�~�~r�+�b���u��y~?�7n��E^��~���صdE�0���c"̬���W(�{���-m�
/�)�.�����qz����=&�Vj,F�`vC� ��8s�Ҫܳ�4�^W>�(�Қr�]���\Ia]���� �l��ڎ=��������f���6��@����u\!���B6&�J+������N��2^���Tu&      �   t   x�3�4202�50�52T04�20�25�33650�@H�`Hs:�&���dr:%�q�� �2Ő25�t�)���K,�ON��L,��2&�]F�RP� \�eB���N��.F��� ��BH     