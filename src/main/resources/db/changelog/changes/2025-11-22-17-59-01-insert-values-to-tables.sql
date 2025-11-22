-- liquibase formatted SQL

--changeset kuat:1
INSERT INTO brands(id, name)
VALUES (1, 'Adidas'),
       (2, 'Nike'),
       (3, 'Puma'),
       (4, 'Reebok'),
       (5, 'Under Armour');

INSERT INTO categories(id, name)
VALUES (1, 'SHOES'),
       (2, 'CLOTHING'),
       (3, 'ACCESSORIES'),
       (4, 'SPORTS EQUIPMENT'),
       (5, 'OUTDOOR GEAR');