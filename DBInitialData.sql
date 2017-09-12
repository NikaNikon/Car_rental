INSERT	INTO statuses (status) VALUES
	("NEW"),				#just created order
    ("APPROVED"),		#manager checked order info and approved the order
    ("PENDING"),			#awaiting for payment from the buyer
    ("CONFIRMING"),		#buyer sent a payment, waiting for confirmaition from manager
    ("PAID"),			#manager confird payment
    ("REJECTED"),		#payment wasn't successfully received, manager rejected the order
    ("EXPIRED"),			#buyer didn't pay within an hour
    ("CANCELED");		#buyer canceled the order (available only before payment)
    
    

INSERT INTO roles (roleName) VALUES
	("CUSTOMER"),
    ("MANAGER"),
    ("ADMIN");
    
    
    
INSERT INTO car_classes (id, carClassName) VALUES
	(DEFAULT, "ECONOM"), (DEFAULT, "MEDIUM"), (DEFAULT, "BUSINESS"), (DEFAULT, "PREMIUM");

    
INSERT INTO cars (model, carClassId, price, fullName, description, status, driverPrice) VALUES
	("Peugeot", "1", 40, "Peugeot 301", 
    "The explicit contours, in harmony with PEUGEOT’s latest stylistic codes, elegantly sculpt the sleek bodywork. The new Peugeot 301 saloon combines strength, spacious cabin comfort and dynamic handling.", 
    "available", 30),
    ("Daewoo", "1", 30, "Daewoo Lanos", 
    "Lanos is the smallest member of the trio of passenger cars that emerged in 1999 from Daewoo, the third South Korean automaker to enter the U.S. market. ",
    "available", 25),
    ("Infiniti", "3", 80, "Infiniti Q50", 
    "The 2014 Infiniti Q50 is the latest generation of the Infiniti G37 sedan, which changed its name to fit into the luxury brand’s new nomenclature.",
    "available", 30),
    ("Mercedes", "4", 180, "Mercedes-Benz S500", 
    "Magnificent, isn’t it? The all-new Mercedes S-Class Cabriolet is obviously just a soft-top, two-door S-Class, but it looks so much grander than the already fabulous hard-top; a proper land-yacht.",
    "available", 50);
    
INSERT INTO users (id, roleId, login, password, email, blocked) VALUES
	(DEFAULT, 3, "admin", "21232f297a57a5a743894a0e4a801fc3", "admin@gmail.com", 0),
    (DEFAULT, 2, "manager", "1d0258c2440a8d19e716292b231e3190", "manager@gmail.com", 0),
    (DEFAULT, 1, "Veronika07", "191671e9b59fb2f0f24ca29f3a810d35", "nikon@gmail.com", 0),
    (DEFAULT, 1, "Deptor", "ef86f37b44add77c782cf0ddf6fa2f88", "deptor@gmail.com", 0),
    (DEFAULT, 1, "user123", "6ad14ba9986e3615423dfca256d04e3f", "user@gmail.com", 0),
    (DEFAULT, 1, "simpleUser", "c755fe1256b92148bfd60fa50ea5328a", "simpleUser@gmail.com", DEFAULT);
    
INSERT INTO orders (id, userId, carId, startDate, endDate, orderDate, driver, totalPrice) 
VALUES (DEFAULT, 5,3,'2017-09-15','2017-09-25','2017-09-11',1,0);
    
INSERT INTO repairment_checks (id, userId, carId, date, price, comment, status) 
	VALUES (DEFAULT, 4, 2, current_date(), 50, "comment", "UNPAYED"); 
    
    
    
    
    
    
    
    