INSERT INTO drug_application (application_number) VALUES ('BLA761037');
INSERT INTO drug_application (application_number) VALUES ('NDA214313');
INSERT INTO drug_application (application_number) VALUES ('NDA215395');

INSERT INTO drug_application_manufacturer (application_number, manufacturer_name) VALUES ( 'BLA761037', 'sanofi-aventis U.S. LLC');
INSERT INTO drug_application_manufacturer (application_number, manufacturer_name) VALUES ('NDA214313', 'Baxter Healthcare Corporation');
INSERT INTO drug_application_manufacturer (application_number, manufacturer_name) VALUES ('NDA215395', 'Cipla USA Inc.');
INSERT INTO drug_application_manufacturer (application_number, manufacturer_name) VALUES ('NDA215395', 'Exelan Pharmaceuticals, Inc.');

INSERT INTO drug_application_product (application_number, product_number) VALUES ('BLA761037', '001');
INSERT INTO drug_application_product (application_number, product_number) VALUES ('BLA761037', '002');
INSERT INTO drug_application_product (application_number, product_number) VALUES ('NDA214313', '001');
INSERT INTO drug_application_product (application_number, product_number) VALUES ('NDA214313', '002');
INSERT INTO drug_application_product (application_number, product_number) VALUES ('NDA215395', '001');
INSERT INTO drug_application_product (application_number, product_number) VALUES ('NDA215395', '002');
INSERT INTO drug_application_product (application_number, product_number) VALUES ('NDA215395', '003');

INSERT INTO drug_application_substance (application_number, substance_name) VALUES ('BLA761037', 'SARILUMAB');
INSERT INTO drug_application_substance (application_number, substance_name) VALUES ('NDA214313', 'NOREPINEPHRINE BITARTRATE');
INSERT INTO drug_application_substance (application_number, substance_name) VALUES ('NDA215395', 'LANREOTIDE ACETATE');
