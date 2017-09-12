package com.litovchenko.carsapp.mySql;

import com.litovchenko.carsapp.dao.OrderDAO;
import com.litovchenko.carsapp.model.Order;

import javax.persistence.PersistenceException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.litovchenko.carsapp.model.Constants.*;

public class MySqlOrderDAO extends MySqlGenericDAO<Order> implements OrderDAO {

    private static final String SQL_INSERT_QUERY;
    private static final String SQL_UPDATE_QUERY;
    private static final String SQL_DELETE_QUERY;
    private static final String SQL_SELECT_ALL_QUERY;
    private static final String SQL_SELECT_QUERY;

    private static final String SELECT_BY_TEMPLATE;

    static {

        SQL_SELECT_ALL_QUERY = "SELECT * FROM " + ORDERS;
        SELECT_BY_TEMPLATE = SQL_SELECT_ALL_QUERY + " WHERE ";
        SQL_SELECT_QUERY = SELECT_BY_TEMPLATE + ID + EQ_PARAM;
        SQL_DELETE_QUERY = "DELETE FROM " + ORDERS + " WHERE " + ID + EQ_PARAM;

        SQL_INSERT_QUERY = "INSERT INTO " + ORDERS + "(" + ID + COMA + USER_ID + COMA +
                CAR_ID + COMA + START_DATE + COMA + END_DATE + COMA + ORDER_DATE + COMA +
                DRIVER + COMA + TOTAL_PRICE + COMA + ORDER_STATUS_ID + COMA + ORDER_COMMENT +
                ") VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, DEFAULT, ?)";

        SQL_UPDATE_QUERY = "UPDATE " + ORDERS + " SET " + USER_ID + EQ_COMA +
                CAR_ID + EQ_COMA + START_DATE + EQ_COMA + END_DATE + EQ_COMA + ORDER_DATE + EQ_COMA +
                DRIVER + EQ_COMA + TOTAL_PRICE + EQ_COMA + ORDER_STATUS_ID + EQ_COMA + ORDER_COMMENT + EQ_PARAM +
                " WHERE " + ID + EQ_PARAM;
    }

    public MySqlOrderDAO(Connection con) {
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
    protected List<Order> parseResultSet(ResultSet rs) {
        List<Order> list = new ArrayList<>();
        try {
            while (rs.next()) {
                MySqlCarDAO carDAO = new MySqlCarDAO(con);
                MySqlUserDAO userDAO = new MySqlUserDAO(con);
                MySqlStatusDAO statusDAO = new MySqlStatusDAO(con);
                Order order = new Order(rs.getInt(USER_ID), rs.getInt(CAR_ID), rs.getDate(START_DATE),
                        rs.getDate(END_DATE), rs.getDate(ORDER_DATE), rs.getBoolean(DRIVER),
                        rs.getDouble(TOTAL_PRICE), rs.getInt(ORDER_STATUS_ID));
                if (rs.getString(ORDER_COMMENT) != null) {
                    order.setManagerComment(rs.getString(ORDER_COMMENT));
                }
                order.setStatus(statusDAO.getById(rs.getInt(ORDER_STATUS_ID)).getStatus());
                order.setCarName(carDAO.getById(rs.getInt(CAR_ID)).getFullName());
                order.setUserLogin(userDAO.getById(rs.getInt(USER_ID)).getLogin());

                list.add(order);
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
    protected void prepareStatementForInsert(PreparedStatement st, Order object) {
        try {
            st.setInt(1, object.getUserId());
            st.setInt(2, object.getCarId());
            st.setDate(3, object.getStartDate());
            st.setDate(4, object.getEndDate());
            st.setDate(5, object.getOrderDate());
            st.setBoolean(6, object.isDriver());
            st.setDouble(7, object.getTotalPrice());
            st.setString(8, object.getManagerComment());
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement st, Order object) {
        try {
            prepareStatementForInsert(st, object);
            st.setInt(8, object.getStatusId());
            st.setString(9, object.getManagerComment());
            st.setInt(10, object.getId());
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }


    @Override
    public List<Order> getByUserId(int user_id) {
        List<Order> list;
        String sql = SELECT_BY_TEMPLATE + USER_ID + EQ_PARAM;
        list = getByIntParam(sql, user_id);
        return list;
    }

    @Override
    public List<Order> getByCarId(int car_id) {
        List<Order> list;
        String sql = SELECT_BY_TEMPLATE + CAR_ID + EQ_PARAM;
        list = getByIntParam(sql, car_id);
        return list;
    }

    @Override
    public List<Order> getByStatus(String status) {
        List<Order> list;
        String sql = SELECT_BY_TEMPLATE + ORDER_STATUS_ID + " = (SELECT " + ID +
                " FROM " + STATUSES + " WHERE " + ORDER_STATUS + " LIKE ?)";
        list = getByStringParam(sql, status);
        return list;
    }

    @Override
    public boolean deleteAllByStatus(String status) {
        String sql = "DELETE FROM " + ORDERS + " WHERE " + ORDER_STATUS_ID + " = (SELECT " + ID +
                " FROM " + STATUSES + " WHERE " + ORDER_STATUS + " LIKE ?)";
        try (PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setString(1, status);
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
        return true;
    }
}
