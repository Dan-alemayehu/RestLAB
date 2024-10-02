CREATE TABLE product (
    id          INTEGER         NOT NULL AUTO_INCREMENT,
    sku         VARCHAR(128)    NOT NULL,
    description VARCHAR(512),

    PRIMARY KEY (id)
);