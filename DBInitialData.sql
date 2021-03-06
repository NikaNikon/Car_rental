INSERT	INTO statuses (status) VALUES
	  ("NEW"),			#just created order
    ("REJECTED"),		#there are some reasons why order can't be made
    ("CONFIRMED"),   #manager confirmed the order
    ("CLOSED");     #car returned
    

INSERT INTO roles (roleName) VALUES
	("CUSTOMER"),
    ("MANAGER"),
    ("ADMIN");
    
    
    
INSERT INTO car_classes (id, carClassName) VALUES
	(DEFAULT, "ECONOMY"), (DEFAULT, "MEDIUM"), (DEFAULT, "BUSINESS"), (DEFAULT, "PREMIUM");

    
INSERT INTO cars (licensePlate, model, carClassId, price, fullName, description, status, driverPrice) VALUES
	("AS1234DF", "Peugeot", 1, 40, "Peugeot 301",
    "The explicit contours, in harmony with PEUGEOT’s latest stylistic codes, elegantly sculpt the sleek bodywork. The new Peugeot 301 saloon combines strength, spacious cabin comfort and dynamic handling.", 
    "available", 30),
    ("AS1235DF", "Daewoo", 1, 30, "Daewoo Lanos",
    "Lanos is the smallest member of the trio of passenger cars that emerged in 1999 from Daewoo, the third South Korean automaker to enter the U.S. market. ",
    "available", 25),
    ("AS1236DF", "Chevrolet", 1, 40, "Chevrolet Spark", "The Chevrolet Spark is a city car produced by GM Korea, originally marketed prominently as the Daewoo Matiz.",
    "available", 30),
    ("AS1237DF", "Chevrolet", 1, 45, "Chevrolet Lacetti", "The maximum speed of the Chevrolet Lacetti makes 217 kilometers per hour. You will see such information on a speedometer of model named Lacetti WTCC R+ (2005).",
    "available", 35),
    ("AS1238DF", "Toyota", 2, 55, "Toyota Yaris iA", "The Toyota Yaris is a subcompact car produced by Toyota since 1999 , replacing the Starlet.",
    "available", 40),
    ("AS1239DF", "Ford", 2, 55, "Ford Fiesta", "The Ford Fiesta is a supermini marketed by Ford since 1976 over seven generations and manufactured globally.",
    "available", 40),
    ("AS1230DF", "Honda", 2, 60, "Honda Fit", "The Honda Fit is a five-door, front-engine, front-wheel drive B-segment subcompact car manufactured and marketed by Honda since 2001 and now in its third generation.",
    "available", 40),
    ("AS1244DF", "Chevrolet", 2, 60, "Chevrolet Sonic", "The Chevy Sonic represents a quantum leap forward from the Aveo it replaced.",
    "available", 40),
    ("AS1254DF", "Infiniti", 3, 80, "Infiniti Q50",
    "The 2014 Infiniti Q50 is the latest generation of the Infiniti G37 sedan, which changed its name to fit into the luxury brand’s new nomenclature.",
    "available", 30),
    ("AS1264DF", "Mercedes", 3, 160, "Mercedes-Benz W211",
    "Mercedes-Benz W211 - the third generation of E-class cars German brand Mercedes-Benz, first released in 2002.", 
    "available", 50),
    ("AS1274DF", "Toyota", 3, 150, "Toyota Mark X",
    "The Toyota Mark X is a mid-sized, luxury car manufactured by Toyota, and is primarily aimed at the Japanese market and sold in other select Asian markets.",
    "available", 50),
    ("AS1284DF", "Hundai", 3, 160, "Hundai Grandeur",
    "The Hyundai Grandeur is a car made by Hyundai Motor Company. It was first sold in 1986. In North America the car was called Hyundai XG (third generation) and Hyundai Azera (fourth generation).", 
    "available", 50),
    ("AS1294DF", "Mercedes", 4, 180, "Mercedes-Benz S500",
    "Magnificent, isn’t it? The all-new Mercedes S-Class Cabriolet is obviously just a soft-top, two-door S-Class, but it looks so much grander than the already fabulous hard-top; a proper land-yacht.",
    "available", 50),
    ("AS1204DF", "BMW", 4, 200, "BMW 7-Series (E38)", "The BMW E38 is the third generation of the BMW 7 Series, which was produced from 1994 to 2001. ",
    "available", 50),
    ("AS1334DF", "Audi", 4, 200, "Audi A8", "The Audi A8 is a four-door, full-size, luxury sedan manufactured and marketed by the German automaker Audi since 1994. ",
    "available", 50),
    ("AS1434DF", "Lexus", 4, 210, "Lexus LS", "The Lexus LS is a full-size luxury car (F-segment in Europe) serving as the flagship model of Lexus, the luxury division of Toyota.",
    "available", 50);
    
INSERT INTO users (id, roleId, login, password, email, blocked) VALUES
	(DEFAULT, 3, "admin", "21232f297a57a5a743894a0e4a801fc3", "admin@gmail.com", 0),
    (DEFAULT, 2, "manager", "1d0258c2440a8d19e716292b231e3190", "manager@gmail.com", 0),
	(DEFAULT, 2, "manager2", "8df5127cd164b5bc2d2b78410a7eea0c", "manager2@gmail.com", 0),
    (DEFAULT, 2, "manager3", "2d3a5db4a2a9717b43698520a8de57d0", "manager3@gmail.com", 0),
    (DEFAULT, 1, "Veronika07", "191671e9b59fb2f0f24ca29f3a810d35", "nikon123@gmail.com", 0),
    (DEFAULT, 1, "Deptor", "ef86f37b44add77c782cf0ddf6fa2f88", "deptor@gmail.com", 0),
    (DEFAULT, 1, "user123", "6ad14ba9986e3615423dfca256d04e3f", "user@gmail.com", 0),
    (DEFAULT, 1, "simpleUser", "c755fe1256b92148bfd60fa50ea5328a", "simpleUser@gmail.com", DEFAULT);
    
INSERT INTO passport_data (userId,firstName,middleName,lastName,dateOfBirth,phone)
VALUES  (5,"Veronika","Yurievna","Litovchenko",'1997-07-07',"+380990372226"),
		(7, "Ivan", "Ivanovich", "Ivanov", '1990-11-05', "+380951234902"),
		(8, "Dmitriy", "Ivanovich", "Petrov", '1993-09-08', "+380950466567"),
		(6, "Petr", "Petrovich", "Petrov", '1991-07-30', "+380951234567");
    
INSERT INTO orders (id, userId, carId, startDate, endDate, orderDate, driver, totalPrice, statusId) 
VALUES  (DEFAULT, 7,3,'2017-09-17','2017-09-25','2017-09-11',1,0, default),
		(DEFAULT, 8,1,'2017-09-20','2017-09-25','2017-09-11',1,0, default),
        (DEFAULT, 6,2,'2017-09-10','2017-09-12','2017-09-09',1,0, 4);
    
INSERT INTO repairment_checks (id, orderId, userId, carId, date, price, comment, status)
	VALUES (DEFAULT, 3, 6, 2, current_date(), 50, "comment", "UNPAYED");
    
    
    
    
    
    
    
    