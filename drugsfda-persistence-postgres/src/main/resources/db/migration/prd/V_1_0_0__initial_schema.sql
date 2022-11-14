CREATE TABLE DRUG_APPLICATION
(
    APPLICATION_NUMBER VARCHAR(256) NOT NULL
        CONSTRAINT DRUG_APPLICATION_PK PRIMARY KEY
);

CREATE TABLE DRUG_APPLICATION_SUBSTANCE
(
    ID                 BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    APPLICATION_NUMBER VARCHAR(256) NOT NULL,
    SUBSTANCE_NAME     VARCHAR(256) NOT NULL,
    CONSTRAINT DRUG_APPLICATION_SUBSTANCE_PK PRIMARY KEY (ID),
    CONSTRAINT SUBSTANCE_DRUG_APPLICATION_FK FOREIGN KEY (APPLICATION_NUMBER) REFERENCES DRUG_APPLICATION (APPLICATION_NUMBER)
);

CREATE TABLE DRUG_APPLICATION_MANUFACTURER
(
    ID                 BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    APPLICATION_NUMBER VARCHAR(256) NOT NULL,
    MANUFACTURER_NAME  VARCHAR(256) NOT NULL,
    CONSTRAINT DRUG_APPLICATION_MANUFACTURER_PK PRIMARY KEY (ID),
    CONSTRAINT MANUFACTURER_DRUG_APPLICATION_FK FOREIGN KEY (APPLICATION_NUMBER) REFERENCES DRUG_APPLICATION (APPLICATION_NUMBER)
);

CREATE TABLE DRUG_APPLICATION_PRODUCT
(
    ID                 BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    APPLICATION_NUMBER VARCHAR(256) NOT NULL,
    PRODUCT_NUMBER       VARCHAR(256) NOT NULL,
    CONSTRAINT DRUG_APPLICATION_PRODUCT_PK PRIMARY KEY (ID),
    CONSTRAINT PRODUCT_DRUG_APPLICATION_FK FOREIGN KEY (APPLICATION_NUMBER) REFERENCES DRUG_APPLICATION (APPLICATION_NUMBER)
);

