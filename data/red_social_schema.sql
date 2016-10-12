--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.4
-- Dumped by pg_dump version 9.5.4

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

--
-- Name: investigador_seq; Type: SEQUENCE; Schema: public; Owner: red_social
--

CREATE SEQUENCE investigador_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE investigador_seq OWNER TO red_social;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: investigadores; Type: TABLE; Schema: public; Owner: red_social
--

CREATE TABLE investigadores (
    investigador_id bigint DEFAULT nextval('investigador_seq'::regclass) NOT NULL,
    cargo character varying,
    nombre character varying(255),
    es_especialista boolean DEFAULT false
);


ALTER TABLE investigadores OWNER TO red_social;

--
-- Name: investigadores_lugar; Type: TABLE; Schema: public; Owner: red_social
--

CREATE TABLE investigadores_lugar (
    investigador_id bigint NOT NULL,
    lugar_de_trabajo_id bigint NOT NULL
);


ALTER TABLE investigadores_lugar OWNER TO red_social;

--
-- Name: investigadores_publicaciones; Type: TABLE; Schema: public; Owner: red_social
--

CREATE TABLE investigadores_publicaciones (
    publicacion_id bigint NOT NULL,
    investigador_id bigint NOT NULL
);


ALTER TABLE investigadores_publicaciones OWNER TO red_social;

--
-- Name: lugar_seq; Type: SEQUENCE; Schema: public; Owner: red_social
--

CREATE SEQUENCE lugar_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE lugar_seq OWNER TO red_social;

--
-- Name: lugares_de_trabajo; Type: TABLE; Schema: public; Owner: red_social
--

CREATE TABLE lugares_de_trabajo (
    id bigint DEFAULT nextval('lugar_seq'::regclass) NOT NULL,
    direccion character varying(255),
    nombre character varying(255)
);


ALTER TABLE lugares_de_trabajo OWNER TO red_social;

--
-- Name: publicaciones; Type: TABLE; Schema: public; Owner: red_social
--

CREATE TABLE publicaciones (
    publicacion_id bigint NOT NULL,
    titulo character varying(255)
);


ALTER TABLE publicaciones OWNER TO red_social;

--
-- Name: investigadores_lugar_pkey; Type: CONSTRAINT; Schema: public; Owner: red_social
--

ALTER TABLE ONLY investigadores_lugar
    ADD CONSTRAINT investigadores_lugar_pkey PRIMARY KEY (investigador_id, lugar_de_trabajo_id);


--
-- Name: investigadores_pkey; Type: CONSTRAINT; Schema: public; Owner: red_social
--

ALTER TABLE ONLY investigadores
    ADD CONSTRAINT investigadores_pkey PRIMARY KEY (investigador_id);


--
-- Name: investigadores_publicaciones_pkey; Type: CONSTRAINT; Schema: public; Owner: red_social
--

ALTER TABLE ONLY investigadores_publicaciones
    ADD CONSTRAINT investigadores_publicaciones_pkey PRIMARY KEY (publicacion_id, investigador_id);


--
-- Name: lugares_de_trabajo_pkey; Type: CONSTRAINT; Schema: public; Owner: red_social
--

ALTER TABLE ONLY lugares_de_trabajo
    ADD CONSTRAINT lugares_de_trabajo_pkey PRIMARY KEY (id);


--
-- Name: publicaciones_pkey; Type: CONSTRAINT; Schema: public; Owner: red_social
--

ALTER TABLE ONLY publicaciones
    ADD CONSTRAINT publicaciones_pkey PRIMARY KEY (publicacion_id);


--
-- Name: fk564bb0f3292b0eb3; Type: FK CONSTRAINT; Schema: public; Owner: red_social
--

ALTER TABLE ONLY investigadores_publicaciones
    ADD CONSTRAINT fk564bb0f3292b0eb3 FOREIGN KEY (publicacion_id) REFERENCES publicaciones(publicacion_id);


--
-- Name: fk564bb0f3b7d0dc21; Type: FK CONSTRAINT; Schema: public; Owner: red_social
--

ALTER TABLE ONLY investigadores_publicaciones
    ADD CONSTRAINT fk564bb0f3b7d0dc21 FOREIGN KEY (investigador_id) REFERENCES investigadores(investigador_id);


--
-- Name: fkd8f32057b0f1adf; Type: FK CONSTRAINT; Schema: public; Owner: red_social
--

ALTER TABLE ONLY investigadores_lugar
    ADD CONSTRAINT fkd8f32057b0f1adf FOREIGN KEY (lugar_de_trabajo_id) REFERENCES lugares_de_trabajo(id);


--
-- Name: fkd8f32057b7d0dc21; Type: FK CONSTRAINT; Schema: public; Owner: red_social
--

ALTER TABLE ONLY investigadores_lugar
    ADD CONSTRAINT fkd8f32057b7d0dc21 FOREIGN KEY (investigador_id) REFERENCES investigadores(investigador_id);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- Name: investigadores; Type: ACL; Schema: public; Owner: red_social
--

REVOKE ALL ON TABLE investigadores FROM PUBLIC;
REVOKE ALL ON TABLE investigadores FROM red_social;
GRANT ALL ON TABLE investigadores TO red_social;
GRANT ALL ON TABLE investigadores TO postgres;


--
-- Name: investigadores_lugar; Type: ACL; Schema: public; Owner: red_social
--

REVOKE ALL ON TABLE investigadores_lugar FROM PUBLIC;
REVOKE ALL ON TABLE investigadores_lugar FROM red_social;
GRANT ALL ON TABLE investigadores_lugar TO red_social;
GRANT ALL ON TABLE investigadores_lugar TO postgres;


--
-- Name: investigadores_publicaciones; Type: ACL; Schema: public; Owner: red_social
--

REVOKE ALL ON TABLE investigadores_publicaciones FROM PUBLIC;
REVOKE ALL ON TABLE investigadores_publicaciones FROM red_social;
GRANT ALL ON TABLE investigadores_publicaciones TO red_social;
GRANT ALL ON TABLE investigadores_publicaciones TO postgres;


--
-- Name: lugares_de_trabajo; Type: ACL; Schema: public; Owner: red_social
--

REVOKE ALL ON TABLE lugares_de_trabajo FROM PUBLIC;
REVOKE ALL ON TABLE lugares_de_trabajo FROM red_social;
GRANT ALL ON TABLE lugares_de_trabajo TO red_social;
GRANT ALL ON TABLE lugares_de_trabajo TO postgres;


--
-- Name: publicaciones; Type: ACL; Schema: public; Owner: red_social
--

REVOKE ALL ON TABLE publicaciones FROM PUBLIC;
REVOKE ALL ON TABLE publicaciones FROM red_social;
GRANT ALL ON TABLE publicaciones TO red_social;
GRANT ALL ON TABLE publicaciones TO postgres;


--
-- PostgreSQL database dump complete
--

