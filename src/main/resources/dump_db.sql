--
-- PostgreSQL database dump
--

-- Dumped from database version 16.4 (Postgres.app)
-- Dumped by pg_dump version 16.4

-- Started on 2024-10-14 18:06:08 +07

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 4 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: pg_database_owner
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO pg_database_owner;

--
-- TOC entry 3635 (class 0 OID 0)
-- Dependencies: 4
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: pg_database_owner
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 216 (class 1259 OID 16408)
-- Name: currencies; Type: TABLE; Schema: public; Owner: matvejodincov
--

CREATE TABLE public.currencies (
    id integer NOT NULL,
    code character varying(100) NOT NULL,
    fullname character varying(100) NOT NULL,
    sign character varying(100) NOT NULL,
    CONSTRAINT code_length CHECK ((length((code)::text) <= 3))
);


ALTER TABLE public.currencies OWNER TO matvejodincov;

--
-- TOC entry 215 (class 1259 OID 16407)
-- Name: currencies_id_seq; Type: SEQUENCE; Schema: public; Owner: matvejodincov
--

CREATE SEQUENCE public.currencies_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.currencies_id_seq OWNER TO matvejodincov;

--
-- TOC entry 3636 (class 0 OID 0)
-- Dependencies: 215
-- Name: currencies_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: matvejodincov
--

ALTER SEQUENCE public.currencies_id_seq OWNED BY public.currencies.id;


--
-- TOC entry 218 (class 1259 OID 16415)
-- Name: exchangerates; Type: TABLE; Schema: public; Owner: matvejodincov
--

CREATE TABLE public.exchangerates (
    id integer NOT NULL,
    basecurrencyid integer NOT NULL,
    targetcurrencyid integer NOT NULL,
    rate numeric(10,6) NOT NULL
);


ALTER TABLE public.exchangerates OWNER TO matvejodincov;

--
-- TOC entry 217 (class 1259 OID 16414)
-- Name: exchangerates_id_seq; Type: SEQUENCE; Schema: public; Owner: matvejodincov
--

CREATE SEQUENCE public.exchangerates_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.exchangerates_id_seq OWNER TO matvejodincov;

--
-- TOC entry 3637 (class 0 OID 0)
-- Dependencies: 217
-- Name: exchangerates_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: matvejodincov
--

ALTER SEQUENCE public.exchangerates_id_seq OWNED BY public.exchangerates.id;


--
-- TOC entry 3470 (class 2604 OID 16411)
-- Name: currencies id; Type: DEFAULT; Schema: public; Owner: matvejodincov
--

ALTER TABLE ONLY public.currencies ALTER COLUMN id SET DEFAULT nextval('public.currencies_id_seq'::regclass);


--
-- TOC entry 3471 (class 2604 OID 16418)
-- Name: exchangerates id; Type: DEFAULT; Schema: public; Owner: matvejodincov
--

ALTER TABLE ONLY public.exchangerates ALTER COLUMN id SET DEFAULT nextval('public.exchangerates_id_seq'::regclass);


--
-- TOC entry 3627 (class 0 OID 16408)
-- Dependencies: 216
-- Data for Name: currencies; Type: TABLE DATA; Schema: public; Owner: matvejodincov
--

INSERT INTO public.currencies (id, code, fullname, sign) VALUES (1, 'AFN', 'Afghani', '؋');
INSERT INTO public.currencies (id, code, fullname, sign) VALUES (2, 'ALL', 'Lek', 'L');
INSERT INTO public.currencies (id, code, fullname, sign) VALUES (3, 'DZD', 'Algerian Dinar', '.د.ج');
INSERT INTO public.currencies (id, code, fullname, sign) VALUES (4, 'PLN', 'Zloty', 'zł');
INSERT INTO public.currencies (id, code, fullname, sign) VALUES (5, 'ZWL', 'Zimbabwe Dollar', 'Z$');
INSERT INTO public.currencies (id, code, fullname, sign) VALUES (6, 'UZS', 'Uzbekistan Sum', 'Soʻm');
INSERT INTO public.currencies (id, code, fullname, sign) VALUES (7, 'USD', 'US Dollar', '$');
INSERT INTO public.currencies (id, code, fullname, sign) VALUES (8, 'RUB', 'Russian Ruble', '₽');
INSERT INTO public.currencies (id, code, fullname, sign) VALUES (9, 'ETB', 'Ethiopian Birr', 'Br');
INSERT INTO public.currencies (id, code, fullname, sign) VALUES (28, 'AUD', 'Australian Dollar', 'A$');
INSERT INTO public.currencies (id, code, fullname, sign) VALUES (29, 'EUR', 'Euro', '€');
INSERT INTO public.currencies (id, code, fullname, sign) VALUES (31, 'XPF', 'CFP Franc', 'F');
INSERT INTO public.currencies (id, code, fullname, sign) VALUES (32, 'DKK', 'Danish Krone', 'kr');


--
-- TOC entry 3629 (class 0 OID 16415)
-- Dependencies: 218
-- Data for Name: exchangerates; Type: TABLE DATA; Schema: public; Owner: matvejodincov
--

INSERT INTO public.exchangerates (id, basecurrencyid, targetcurrencyid, rate) VALUES (11, 8, 7, 0.010000);
INSERT INTO public.exchangerates (id, basecurrencyid, targetcurrencyid, rate) VALUES (12, 9, 7, 0.008200);
INSERT INTO public.exchangerates (id, basecurrencyid, targetcurrencyid, rate) VALUES (13, 1, 9, 1.800000);
INSERT INTO public.exchangerates (id, basecurrencyid, targetcurrencyid, rate) VALUES (14, 8, 1, 0.690000);
INSERT INTO public.exchangerates (id, basecurrencyid, targetcurrencyid, rate) VALUES (15, 1, 2, 1.330000);
INSERT INTO public.exchangerates (id, basecurrencyid, targetcurrencyid, rate) VALUES (16, 8, 2, 0.920000);
INSERT INTO public.exchangerates (id, basecurrencyid, targetcurrencyid, rate) VALUES (18, 6, 2, 0.007000);
INSERT INTO public.exchangerates (id, basecurrencyid, targetcurrencyid, rate) VALUES (19, 6, 7, 0.000078);
INSERT INTO public.exchangerates (id, basecurrencyid, targetcurrencyid, rate) VALUES (17, 9, 8, 0.800000);
INSERT INTO public.exchangerates (id, basecurrencyid, targetcurrencyid, rate) VALUES (20, 28, 29, 0.620000);
INSERT INTO public.exchangerates (id, basecurrencyid, targetcurrencyid, rate) VALUES (21, 31, 29, 0.008300);
INSERT INTO public.exchangerates (id, basecurrencyid, targetcurrencyid, rate) VALUES (22, 31, 32, 0.062000);
INSERT INTO public.exchangerates (id, basecurrencyid, targetcurrencyid, rate) VALUES (23, 8, 32, 0.071000);


--
-- TOC entry 3638 (class 0 OID 0)
-- Dependencies: 215
-- Name: currencies_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matvejodincov
--

SELECT pg_catalog.setval('public.currencies_id_seq', 32, true);


--
-- TOC entry 3639 (class 0 OID 0)
-- Dependencies: 217
-- Name: exchangerates_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matvejodincov
--

SELECT pg_catalog.setval('public.exchangerates_id_seq', 23, true);


--
-- TOC entry 3474 (class 2606 OID 16413)
-- Name: currencies currencies_pkey; Type: CONSTRAINT; Schema: public; Owner: matvejodincov
--

ALTER TABLE ONLY public.currencies
    ADD CONSTRAINT currencies_pkey PRIMARY KEY (id);


--
-- TOC entry 3478 (class 2606 OID 16420)
-- Name: exchangerates exchangerates_pkey; Type: CONSTRAINT; Schema: public; Owner: matvejodincov
--

ALTER TABLE ONLY public.exchangerates
    ADD CONSTRAINT exchangerates_pkey PRIMARY KEY (id);


--
-- TOC entry 3476 (class 2606 OID 16437)
-- Name: currencies unique_currency_code; Type: CONSTRAINT; Schema: public; Owner: matvejodincov
--

ALTER TABLE ONLY public.currencies
    ADD CONSTRAINT unique_currency_code UNIQUE (code);


--
-- TOC entry 3480 (class 2606 OID 16439)
-- Name: exchangerates unique_currency_pair; Type: CONSTRAINT; Schema: public; Owner: matvejodincov
--

ALTER TABLE ONLY public.exchangerates
    ADD CONSTRAINT unique_currency_pair UNIQUE (basecurrencyid, targetcurrencyid);


--
-- TOC entry 3481 (class 2606 OID 16421)
-- Name: exchangerates exchangerates_basecurrencyid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: matvejodincov
--

ALTER TABLE ONLY public.exchangerates
    ADD CONSTRAINT exchangerates_basecurrencyid_fkey FOREIGN KEY (basecurrencyid) REFERENCES public.currencies(id);


--
-- TOC entry 3482 (class 2606 OID 16426)
-- Name: exchangerates exchangerates_targetcurrencyid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: matvejodincov
--

ALTER TABLE ONLY public.exchangerates
    ADD CONSTRAINT exchangerates_targetcurrencyid_fkey FOREIGN KEY (targetcurrencyid) REFERENCES public.currencies(id);


-- Completed on 2024-10-14 18:06:08 +07

--
-- PostgreSQL database dump complete
--

