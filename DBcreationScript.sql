
DROP SCHEMA IF EXISTS `car_rental_db` ;

CREATE SCHEMA IF NOT EXISTS `car_rental_db` DEFAULT CHARACTER SET utf8 ;
USE `car_rental_db` ;

CREATE TABLE IF NOT EXISTS `car_classes` (
	`id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
    `carClassName` VARCHAR(20) NOT NULL,
    PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


CREATE TABLE IF NOT EXISTS `cars` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `model` VARCHAR(20) NOT NULL,
  `carClassId` INT(11) UNSIGNED NOT NULL,
  `price` DECIMAL(10,0) NOT NULL,
  `fullName` VARCHAR(100) NOT NULL,
  `description` LONGTEXT NULL DEFAULT NULL,
  `status` ENUM('AVAILABLE', 'IN_RENT', 'BEING_REPAIRED') NOT NULL,
  `driverPrice` DECIMAL(10,0) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_cars_car_classes`
	FOREIGN KEY (`carClassId`)
    REFERENCES `car_classes` (`id`)
    ON UPDATE CASCADE
    ON DELETE RESTRICT)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


CREATE TABLE IF NOT EXISTS `roles` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `roleName` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `role_name_UNIQUE` (`roleName` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


CREATE TABLE IF NOT EXISTS `users` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `roleId` INT(11) UNSIGNED NOT NULL DEFAULT '1',
  `login` VARCHAR(20) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `email` VARCHAR(50) NOT NULL,
  `blocked` TINYINT(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  INDEX `fk_users_roles_idx` (`roleId` ASC),
  CONSTRAINT `fk_users_roles`
    FOREIGN KEY (`roleId`)
    REFERENCES `roles` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


CREATE TABLE IF NOT EXISTS `statuses` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `status` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `status_UNIQUE` (`status` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


CREATE TABLE IF NOT EXISTS `orders` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `userId` INT(11) UNSIGNED NOT NULL,
  `carId` INT(11) UNSIGNED NOT NULL,
  `startDate` DATE NOT NULL,
  `endDate` DATE NOT NULL,
  `orderDate` DATE NOT NULL,
  `driver` BINARY(1) NOT NULL DEFAULT '0',
  `totalPrice` DECIMAL(10,0) NOT NULL,
  `statusId` INT(11) UNSIGNED NOT NULL DEFAULT '1',
  `managerComment` MEDIUMTEXT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_orders_users1_idx` (`userId` ASC),
  INDEX `fk_orders_cars1_idx` (`carId` ASC),
  INDEX `fk_orders_statuses1_idx` (`statusId` ASC),
  CONSTRAINT `fk_orders_users1`
    FOREIGN KEY (`userId`)
    REFERENCES `users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_orders_cars1`
    FOREIGN KEY (`carId`)
    REFERENCES `cars` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_orders_statuses1`
    FOREIGN KEY (`statusId`)
    REFERENCES `statuses` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


CREATE TABLE IF NOT EXISTS `passport_data` (
  `userId` INT(11) UNSIGNED NOT NULL,
  `passportCode` VARCHAR(12) NOT NULL,
  `firstName` VARCHAR(30) NOT NULL,
  `middleName` VARCHAR(30) NOT NULL,
  `lastName` VARCHAR(30) NOT NULL,
  `dateOfBirth` DATE NOT NULL,
  `phone` VARCHAR(13) NOT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE INDEX `passport_code_UNIQUE` (`passportCode` ASC),
  CONSTRAINT `fk_passport_data_users1`
    FOREIGN KEY (`userId`)
    REFERENCES `users` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


CREATE TABLE IF NOT EXISTS `repairment_checks` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `userId` INT(11) UNSIGNED NOT NULL,
  `carId` INT(11) UNSIGNED NOT NULL,
  `date` DATE NOT NULL,
  `price` DECIMAL(10,0) NOT NULL,
  `comment` MEDIUMTEXT NULL DEFAULT NULL,
  `status` ENUM('UNPAYED', 'PAYED') NOT NULL DEFAULT 'UNPAYED',
  PRIMARY KEY (`id`),
  INDEX `fk_rapairment_checks_users1_idx` (`userId` ASC),
  INDEX `fk_rapairment_checks_cars1_idx` (`carId` ASC),
  CONSTRAINT `fk_rapairment_checks_users1`
    FOREIGN KEY (`userId`)
    REFERENCES `users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_rapairment_checks_cars1`
    FOREIGN KEY (`carId`)
    REFERENCES `cars` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

DELIMITER //
CREATE PROCEDURE findByModel
	(IN car_model VARCHAR(20))
    BEGIN
		SELECT * FROM cars WHERE model LIKE car_model;
    END //
    
CREATE PROCEDURE findByClass
	(IN car_class VARCHAR(20))
    BEGIN
		SELECT * FROM cars, car_classes 
        WHERE car_classes.id = cars.carClassId AND carClassName LIKE car_class;
    END //
    
CREATE PROCEDURE findOrdersByStatus (ord_stat VARCHAR(45))
	BEGIN
		SELECT * from orders, statuses
        WHERE statuses.id = orders.statusId AND status LIKE ord_stat;
    END //
    
CREATE FUNCTION abilityToMakeOrders (user INT(11))
	RETURNS BINARY(1)
    BEGIN
		DECLARE ability BINARY(1);
		IF EXISTS (select * from repairment_checks where userId = user AND status LIKE 'unpayed')
			THEN SET ability = 0;
		ELSE SET ability = 1;
        END IF;
	RETURN ability;
    END //
    
CREATE TRIGGER before_car_ordered 
	BEFORE INSERT ON orders
    FOR EACH ROW
    BEGIN
		DECLARE msg VARCHAR(50);
        IF
			EXISTS (SELECT * FROM repairment_checks WHERE userId = NEW.userID AND status LIKE 'unpayed')
        THEN
			SET msg = 'This user cannot make orders';
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
        END IF;
		IF 
			NEW.startDate <= (SELECT max(endDate) FROM
							(SELECT endDate FROM orders WHERE carId = NEW.carId) dates)
		THEN 
			SET msg = 'Dates are not available';
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
		ELSE 
			IF
				NEW.driver = 1
			THEN
				SET NEW.totalPrice = (SELECT price+driverPrice FROM cars WHERE id = NEW.carId) * 
                (NEW.endDate - NEW.startDate);
			ELSE 
				SET NEW.totalPrice = (SELECT price FROM cars WHERE id = NEW.carId) * 
                (NEW.endDate - NEW.startDate);
            END IF;
		END IF;
    END //
    
    CREATE TRIGGER before_car_deleted
	BEFORE DELETE ON cars
    FOR EACH ROW
    BEGIN
		DECLARE msg VARCHAR(50);
		IF OLD.status LIKE 'IN_RENT'
        THEN SET msg = 'This car is in rent. You can not delete it.';
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
        END IF;
    END //
    
    CREATE TRIGGER change_car_status_before_order
    BEFORE INSERT ON orders
    FOR EACH ROW
    BEGIN
		UPDATE cars SET cars.status = "IN_RENT" WHERE cars.id = NEW.carId;
    END //
    
    CREATE TRIGGER change_car_status_after_order_updated
    AFTER UPDATE ON orders
    FOR EACH ROW
    BEGIN
		IF
			NEW.statusId IN 
            (SELECT id FROM statuses s WHERE s.status LIKE "CLOSED" OR s.status LIKE "REJECTED" OR
            s.status LIKE "EXPIRED" OR s.status LIKE "CANCELED")
		THEN 
			UPDATE cars SET cars.status = "AVAILABLE" where cars.id = NEW.carId;
        END IF;
    END //
    
DELIMITER ;
