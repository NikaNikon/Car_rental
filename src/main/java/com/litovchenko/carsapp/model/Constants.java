package com.litovchenko.carsapp.model;

public interface Constants {

    /**
     * Databases
     */
    String DB = "car_rental_db";
    String TEST_DB = "cars_test";


    /**
     * Tables
     */

    String USERS = "users";
    String PASSPORT_DATA = "passport_data";
    String ROLES = "roles";
    String CARS = "cars";
    String CAR_CLASSES = "car_classes";
    String ORDERS = "orders";
    String STATUSES = "statuses";
    String REPAIRMENT_CHECKS = "repairment_checks";


    /**
     * Id columns (Primary key)
     */

    String ID = "id";


    /**
     * Foreign keys
     */

    String USER_ROLE_ID = "roleId";
    String CAR_ID = "carId";
    String USER_ID = "userId";
    String ORDER_STATUS_ID = "statusId";
    String CAR_CLASS_ID = "carClassId";


    /**
     * Other fields
     */

    String CAR_CLASS_NAME = "carClassName";
    String CAR_MODEL = "model";
    String CAR_PRICE = "price";
    String CAR_FULL_NAME = "fullName";
    String DESCRIPTION = "description";
    String CAR_STATUS = "status";
    String DRIVER_PRICE = "driverPrice";
    String START_DATE = "startDate";
    String END_DATE = "endDate";
    String ORDER_DATE = "orderDate";
    String DRIVER = "driver";
    String TOTAL_PRICE = "totalPrice";
    String ORDER_COMMENT = "managerComment";
    String LOGIN = "login";
    String PASSWORD = "password";
    String EMAIL = "email";
    String BLOCKED = "blocked";
    String FIRST_NAME = "firstName";
    String MIDDLE_NAME = "middleName";
    String LAST_NAME = "lastName";
    String DATE_OF_BIRTH = "dateOfBirth";
    String PHONE = "phone";
    String ORDER_ID = "orderId";
    String CHECK_DATE = "date";
    String REPAIRMENT_PRICE = "price";
    String CHECK_COMMENT = "comment";
    String CHECK_STATUS = "status";
    String ROLE_NAME = "roleName";
    String ORDER_STATUS = "status";

}
