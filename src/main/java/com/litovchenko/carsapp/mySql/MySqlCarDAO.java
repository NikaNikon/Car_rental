package com.litovchenko.carsapp.mySql;

import com.litovchenko.carsapp.dao.CarClassDAO;
import com.litovchenko.carsapp.dao.CarDAO;
import com.litovchenko.carsapp.model.Car;

import javax.persistence.PersistenceException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.litovchenko.carsapp.model.Constants.*;

public class MySqlCarDAO extends MySqlGenericDAO<Car> implements CarDAO {

    private static final String SQL_INSERT_QUERY;
    private static final String SQL_UPDATE_QUERY;
    private static final String SQL_DELETE_QUERY;
    private static final String SQL_SELECT_ALL_QUERY;
    private static final String SQL_SELECT_QUERY;

    private static final String SELECT_BY_TEMPLATE;

    static {
        SQL_INSERT_QUERY = "INSERT INTO" + CARS + " (" + ID + COMA + CAR_MODEL + COMA + CAR_CLASS_ID +
                COMA + CAR_PRICE + COMA + CAR_FULL_NAME + COMA + DESCRIPTION + COMA + CAR_STATUS +
                COMA + DRIVER_PRICE + ") VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?";

        SQL_UPDATE_QUERY = "UPDATE " + CARS + "SET " + CAR_MODEL + EQ_COMA + CAR_CLASS_ID + EQ_COMA +
                CAR_PRICE + EQ_COMA + CAR_FULL_NAME + EQ_COMA + DESCRIPTION + EQ_COMA + CAR_STATUS +
                EQ_COMA + DRIVER_PRICE + EQ_PARAM + "WHERE " + ID + EQ_PARAM;

        SQL_DELETE_QUERY = "DELETE FROM " + CARS + " WHERE " + ID + EQ_PARAM;
        SQL_SELECT_ALL_QUERY = "SELECT * FROM " + CARS;
        SELECT_BY_TEMPLATE = SQL_SELECT_ALL_QUERY + " WHERE ";
        SQL_SELECT_QUERY = SELECT_BY_TEMPLATE + ID + EQ_PARAM;
    }


    public MySqlCarDAO(Connection con) {
        super(con);
    }

    @Override
    protected String getInsertQuery() {
        return SQL_INSERT_QUERY;
    }

    @Override
    protected String getDeleteQuery() {
        return SQL_DELETE_QUERY;
    }

    @Override
    protected String getSelectAllQuery() {
        return SQL_SELECT_ALL_QUERY;
    }

    @Override
    protected String getSelectQuery() {
        return SQL_SELECT_QUERY;
    }

    @Override
    protected String getUpdateQuery() {
        return SQL_UPDATE_QUERY;
    }

    @Override
    protected List<Car> parseResultSet(ResultSet rs) {
        List<Car> list = new ArrayList<>();
        try {
            while (rs.next()) {
                CarClassDAO carClassDAO = new MySqlCarClassDAO(con);
                String car_class = carClassDAO.getById(rs.getInt(CAR_CLASS_ID)).getCarClassName();
                Car car = new Car(rs.getString(CAR_MODEL), rs.getInt(CAR_CLASS_ID), rs.getDouble(CAR_PRICE),
                        rs.getString(CAR_FULL_NAME), rs.getString(DESCRIPTION),
                        Car.Status.valueOf(rs.getString(CAR_STATUS)), rs.getDouble(DRIVER_PRICE));
                car.setCarClassName(car_class);
                list.add(car);
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
        if (list.isEmpty()) {
            return null;
        }
        return list;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement st, Car object) {
        try {
            st.setString(1, object.getModel());
            st.setInt(2, object.getCarClassId());
            st.setDouble(3, object.getPrice());
            st.setString(4, object.getFullName());
            st.setString(5, object.getDescription());
            st.setString(6, object.getStatus().toString());
            st.setDouble(6, object.getDriverPrice());
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement st, Car object) {
        prepareStatementForInsert(st, object);
        try {
            st.setInt(7, object.getId());
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<Car> getByModel(String model) {
        List<Car> list;
        String sql = SELECT_BY_TEMPLATE + CAR_MODEL + " LIKE ?";
        list = getByStringParam(sql, model);
        return list;
    }

    @Override
    public List<Car> getByClass(String car_class) {
        List<Car> list;
        String sql = "SELECT * FROM " + CARS + COMA + CAR_CLASSES + " WHERE " +
                CAR_CLASSES + "." + ID + " = " + CAR_CLASS_ID + " AND " + CAR_CLASS_NAME + " LIKE ? ";
        list = getByStringParam(sql, car_class);
        return list;
    }

    @Override
    public List<Car> getByPrice(int min, int max) {
        List<Car> list;
        String sql = SELECT_BY_TEMPLATE + CAR_PRICE + " BETWEEN ? AND ?";
        try (PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setInt(1, min);
            pstm.setInt(2, max);
            ResultSet rs = pstm.executeQuery();
            list = parseResultSet(rs);
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
        return list;
    }

    @Override
    public List<Car> getByStatus(Car.Status status) {
        List<Car> list;
        String sql = SELECT_BY_TEMPLATE + CAR_STATUS + " LIKE ?";
        list = getByStringParam(sql, status.toString());
        return list;
    }

    @Override
    public boolean deleteAllByClass(String car_class) {
        String sql = "DELETE FROM " + CARS + COMA + CAR_CLASSES + " WHERE " +
                CAR_CLASSES + "." + ID + " = " + CAR_CLASS_ID + " AND " + CAR_CLASS_NAME + " LIKE ? ";
        try (PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setString(1, car_class);
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
        return true;
    }

}
