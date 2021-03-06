INSERT INTO apartment (id, name, description, price, size) VALUES (1, 'Apartament 1', 'Z widokiem na morze', 100, 30);
INSERT INTO apartment (id, name, description, price, size) VALUES (2, 'Apartament 2', 'Blisko centrum', 150, 40);
INSERT INTO apartment (id, name, description, price, size) VALUES (3, 'Apartament 3', 'Pod lasem', 200, 80);
INSERT INTO tenant (id, name) VALUES (1, 'Julia Zielińska');
INSERT INTO tenant (id, name) VALUES (2, 'Jan Kowalski');
INSERT INTO tenant (id, name) VALUES (3, 'Ewa Nowak');
INSERT INTO tenant (id, name) VALUES (4, 'Katarzyna Adamczyk');
INSERT INTO reservation (id, date_from, date_to, cost, apartment_id, tenant_id) VALUES (1, '2022-05-01', '2022-05-15', 1000, 1, 1);
INSERT INTO reservation (id, date_from, date_to, cost, apartment_id, tenant_id) VALUES (2, '2022-06-04', '2022-06-15', 1100, 1, 4);
INSERT INTO reservation (id, date_from, date_to, cost, apartment_id, tenant_id) VALUES (3, '2022-07-01', '2022-07-15', 2100, 2, 3);