package com.litovchenko.carsapp.mySql;

import com.litovchenko.carsapp.dao.RepairmentCheckDAO;
import com.litovchenko.carsapp.model.RepairmentCheck;

import javax.persistence.PersistenceException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.litovchenko.carsapp.model.Constants.*;

public class MySqlRepairmentCheckDAO extends MySqlGenericDAO<RepairmentCheck> implements RepairmentCheckDAO {

    private static final String SQL_INSERT_QUERY;
    private static final String SQL_UPDATE_QUERY;
    private static final String SQL_DELETE_QUERY;
    private static final String SQL_SELECT_ALL_QUERY;
    private static final String SQL_SELECT_QUERY;

    private static final String SELECT_BY_TEMPLATE;

    static {
        SQL_SELECT_ALL_QUERY = "SELECT * FROM " + REPAIRMENT_CHECKS;
        SELECT_BY_TEMPLATE = SQL_SELECT_ALL_QUERY + " WHERE ";
        SQL_SELECT_QUERY = SELECT_BY_TEMPLATE + ID + EQ_PARAM;
        SQL_DELETE_QUERY = "DELETE FROM " + REPAIRMENT_CHECKS + " WHERE " + ID + EQ_PARAM;

        SQL_INSERT_QUERY = "INSERT INTO " + REPAIRMENT_CHECKS + "(" + ID + COMA + USER_ID + COMA +
                CAR_ID + COMA + CHECK_DATE + COMA + REPAIRMENT_PRICE + COMA + CHECK_COMMENT + COMA +
                CHECK_STATUS + ") VALUES (DEFAULT, ?, ?, ?, ?, ?, ?)";

        SQL_UPDATE_QUERY = "UPDATE " + REPAIRMENT_CHECKS + " SET " + USER_ID + EQ_COMA + CAR_ID + EQ_COMA +
                CHECK_DATE + EQ_COMA + REPAIRMENT_PRICE + EQ_COMA + CHECK_COMMENT + EQ_COMA +
                CHECK_STATUS + EQ_PARAM + " WHERE " + ID + EQ_PARAM;


    }

    public MySqlRepairmentCheckDAO(Connection con) {
        super(con);
    }

    @Override
    public List<RepairmentCheck> getByUserId(int id) {
        List<RepairmentCheck> list;
        String sql = SELECT_BY_TEMPLATE + USER_ID + EQ_PARAM;
        list = getByIntParam(sql, id);
        return list;
    }

    @Override
    public List<RepairmentCheck> getByCarId(int id) {
        List<RepairmentCheck> list;
        String sql = SELECT_BY_TEMPLATE + CAR_ID + EQ_PARAM;
        list = getByIntParam(sql, id);
        return list;
    }

    @Override
    public List<RepairmentCheck> getByStatus(RepairmentCheck.Status status) {
        List<RepairmentCheck> list;
        String sql = SELECT_BY_TEMPLATE + CHECK_STATUS + EQ_PARAM;
        list = getByStringParam(sql, status.toString());
        return list;
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
    protected List<RepairmentCheck> parseResultSet(ResultSet rs) {
        List<RepairmentCheck> list = new ArrayList<>();
        try {
            while (rs.next()) {
                MySqlCarDAO carDAO = new MySqlCarDAO(con);
                MySqlUserDAO userDAO = new MySqlUserDAO(con);
                RepairmentCheck check = new RepairmentCheck(rs.getInt(ID), rs.getInt(USER_ID),
                        rs.getInt(CAR_ID), rs.getDate(CHECK_DATE), rs.getDouble(REPAIRMENT_PRICE),
                        rs.getString(CHECK_COMMENT), RepairmentCheck.Status.valueOf(rs.getString(CHECK_STATUS)));
                check.setCarName(carDAO.getById(rs.getInt(CAR_ID)).getFullName());
                check.setUserLogin(userDAO.getById(rs.getInt(USER_ID)).getLogin());
                list.add(check);
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
    protected void prepareStatementForInsert(PreparedStatement st, RepairmentCheck object) {
        try {
            st.setInt(1, object.getUserId());
            st.setInt(2, object.getCarId());
            st.setDate(3, object.getDate());
            st.setDouble(4, object.getPrice());
            st.setString(5, object.getComment());
            st.setString(6, object.getStatus().toString());
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement st, RepairmentCheck object) {
        try {
            prepareStatementForInsert(st, object);
            st.setInt(7, object.getId());
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }

    }
}
