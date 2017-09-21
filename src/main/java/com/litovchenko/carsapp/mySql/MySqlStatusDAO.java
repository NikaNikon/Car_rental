package com.litovchenko.carsapp.mySql;

import com.litovchenko.carsapp.dao.StatusDAO;
import com.litovchenko.carsapp.model.Status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.litovchenko.carsapp.model.Constants.*;

public class MySqlStatusDAO extends MySqlGenericDAO<Status> implements StatusDAO {

    private static final String SQL_INSERT_QUERY;
    private static final String SQL_UPDATE_QUERY;
    private static final String SQL_DELETE_QUERY;
    private static final String SQL_SELECT_ALL_QUERY;
    private static final String SQL_SELECT_QUERY;

    static {
        SQL_SELECT_ALL_QUERY = "SELECT * FROM " + STATUSES;
        SQL_SELECT_QUERY = SQL_SELECT_ALL_QUERY + " WHERE " + ID + EQ_PARAM;
        SQL_DELETE_QUERY = "DELETE FROM " + STATUSES + " WHERE " + ID + EQ_PARAM;
        SQL_INSERT_QUERY = "INSERT INTO " + STATUSES + "(" + ID + COMA + ORDER_STATUS + ") VALUES (DEFAULT, ?)";
        SQL_UPDATE_QUERY = "UPDATE " + STATUSES + " SET " + ORDER_STATUS + EQ_PARAM + " WHERE " + ID + EQ_PARAM;
    }

    public MySqlStatusDAO(Connection con) {
        super(con);
    }

    @Override
    public Status getByName(String statusName) throws SQLException {
        List<Status> list;
        String sql = "SELECT * FROM " + STATUSES + " WHERE " + ORDER_STATUS + EQ_PARAM;
        list = getByStringParam(sql, statusName);
        if (list == null) {
            return null;
        }
        return list.get(0);
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
    protected List<Status> parseResultSet(ResultSet rs) throws SQLException {
        List<Status> list = new ArrayList<>();
        while (rs.next()) {
            Status status = new Status(rs.getInt(ID), rs.getString(ORDER_STATUS));
            list.add(status);
        }
        if (list.isEmpty()) {
            list = null;
        }
        return list;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement st, Status object) throws SQLException {
        st.setString(1, object.getStatus());
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement st, Status object) throws SQLException {
        st.setString(1, object.getStatus());
        st.setInt(2, object.getId());
    }
}
