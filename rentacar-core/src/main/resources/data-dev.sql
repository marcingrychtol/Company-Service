INSERT INTO car
    (id, brand, cancelled, car_category, fuel, inspection_expiration, insurance_expiration, mileage, model, registration)
VALUES (1, N'Dacia', 0, N'COMPANY', null, null, null, 270253, N'Dokker', N'SL 98765');
INSERT INTO car
    (id, brand, cancelled, car_category, fuel, inspection_expiration, insurance_expiration, mileage, model, registration)
    VALUES (2, N'Skoda', 0, N'COMPANY', null, null, null, 30000, N'Octavia', N'NN 8524');
INSERT INTO car
    (id, brand, cancelled, car_category, fuel, inspection_expiration, insurance_expiration, mileage, model, registration)
    VALUES (3, N'Audi', 0, N'COMPANY', null, null, null, 300000, N'A4', N'WZ 6545');
INSERT INTO car
    (id, brand, cancelled, car_category, fuel, inspection_expiration, insurance_expiration, mileage, model, registration)
    VALUES (4, N'VW', 0, N'COMPANY', null, null, null, 789, N'LT', N'KT 7777');
INSERT INTO car
    (id, brand, cancelled, car_category, fuel, inspection_expiration, insurance_expiration, mileage, model, registration)
    VALUES (5, N'Honda', 0, N'COMPANY', null, null, null, 4654, N'Civic', N'KT 354345');


INSERT INTO employee
    (id, cancelled, email, name, phone_number, second_name)
    VALUES (1, 0, N'adam@mdj.pl', N'Adam', N'+48 123 456 789', N'Adamski');
INSERT INTO employee
    (id, cancelled, email, name, phone_number, second_name)
    VALUES (2, 0, N'wojciech@mdj.pl', N'Wojciech', N'+48 123 456 789', N'Cieszyński');
INSERT INTO employee
    (id, cancelled, email, name, phone_number, second_name)
    VALUES (3, 0, N'marcing@mdj.pl', N'Marcin', N'+48 123 456 789', N'Madagaskar');
INSERT INTO employee
    (id, cancelled, email, name, phone_number, second_name)
    VALUES (4, 0, N'norbert@mdj.pl', N'Norbert', N'+48 123 456 789', N'Bezpośredni');


INSERT INTO trip
    (id, additional_message, cancelled, cancelled_time, created_time, ending_date, last_modified_time, starting_date, car_id, employee_id)
    VALUES (1, N'asdf', 0, null, N'2020-04-27 15:09:27.1023693', N'2020-04-28', N'2020-04-27 15:09:27.1023693', N'2020-04-27', 2, 2);
INSERT INTO trip
    (id, additional_message, cancelled, cancelled_time, created_time, ending_date, last_modified_time, starting_date, car_id, employee_id)
    VALUES (2, N'', 0, null, N'2020-04-27 15:09:27.2089643', N'2020-05-01', N'2020-04-27 15:09:27.2089643', N'2020-04-29', 2, 1);
INSERT INTO trip
    (id, additional_message, cancelled, cancelled_time, created_time, ending_date, last_modified_time, starting_date, car_id, employee_id)
    VALUES (3, N'', 0, null, N'2020-04-27 15:09:27.2699282', N'2020-05-06', N'2020-04-27 15:09:27.2699282', N'2020-05-02', 4, 4);
INSERT INTO trip
    (id, additional_message, cancelled, cancelled_time, created_time, ending_date, last_modified_time, starting_date, car_id, employee_id)
    VALUES (4, N'', 0, null, N'2020-04-27 15:09:27.3448749', N'2020-05-01', N'2020-04-27 15:09:27.3448749', N'2020-04-29', 3, 3);
INSERT INTO trip
    (id, additional_message, cancelled, cancelled_time, created_time, ending_date, last_modified_time, starting_date, car_id, employee_id)
    VALUES (5, N'', 1, null, N'2020-04-27 15:09:27.3978198', N'2020-05-09', N'2020-04-27 15:09:27.3978198', N'2020-05-07', 4, 4);


INSERT INTO "day" (id) VALUES (N'2020-04-27');
INSERT INTO "day" (id) VALUES (N'2020-04-28');
INSERT INTO "day" (id) VALUES (N'2020-04-29');
INSERT INTO "day" (id) VALUES (N'2020-04-30');
INSERT INTO "day" (id) VALUES (N'2020-05-01');
INSERT INTO "day" (id) VALUES (N'2020-05-02');
INSERT INTO "day" (id) VALUES (N'2020-05-03');
INSERT INTO "day" (id) VALUES (N'2020-05-04');
INSERT INTO "day" (id) VALUES (N'2020-05-05');
INSERT INTO "day" (id) VALUES (N'2020-05-06');
INSERT INTO "day" (id) VALUES (N'2020-05-07');
INSERT INTO "day" (id) VALUES (N'2020-05-08');
INSERT INTO "day" (id) VALUES (N'2020-05-09');


INSERT INTO day_trip (day_id, trip_id) VALUES (N'2020-04-27', 1);
INSERT INTO day_trip (day_id, trip_id) VALUES (N'2020-04-28', 1);
INSERT INTO day_trip (day_id, trip_id) VALUES (N'2020-04-29', 2);
INSERT INTO day_trip (day_id, trip_id) VALUES (N'2020-04-30', 2);
INSERT INTO day_trip (day_id, trip_id) VALUES (N'2020-05-01', 2);
INSERT INTO day_trip (day_id, trip_id) VALUES (N'2020-05-02', 3);
INSERT INTO day_trip (day_id, trip_id) VALUES (N'2020-05-03', 3);
INSERT INTO day_trip (day_id, trip_id) VALUES (N'2020-05-04', 3);
INSERT INTO day_trip (day_id, trip_id) VALUES (N'2020-05-05', 3);
INSERT INTO day_trip (day_id, trip_id) VALUES (N'2020-05-06', 3);
INSERT INTO day_trip (day_id, trip_id) VALUES (N'2020-04-29', 4);
INSERT INTO day_trip (day_id, trip_id) VALUES (N'2020-04-30', 4);
INSERT INTO day_trip (day_id, trip_id) VALUES (N'2020-05-01', 4);
INSERT INTO day_trip (day_id, trip_id) VALUES (N'2020-05-07', 5);
INSERT INTO day_trip (day_id, trip_id) VALUES (N'2020-05-08', 5);
INSERT INTO day_trip (day_id, trip_id) VALUES (N'2020-05-09', 5);