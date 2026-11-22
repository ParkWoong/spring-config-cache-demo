CREATE TABLE client_backend_api (client_id VARCHAR(80), backend_api VARCHAR(1000));
CREATE TABLE bypass_option (client_id VARCHAR(80), token_key_id VARCHAR(80), access_mode VARCHAR(1));
CREATE TABLE token_key (id VARCHAR(80), rsa_public_key VARCHAR(4000), hs256_secret VARCHAR(4000));
INSERT INTO client_backend_api VALUES ('sample-client', 'http://localhost:9090');
INSERT INTO bypass_option VALUES ('sample-client', 'sample-key', 'N');
