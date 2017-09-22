package com.litovchenko.carsapp.mySql;

import com.litovchenko.carsapp.dao.GenericDAO;
import com.litovchenko.carsapp.model.Identified;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class MySqlGenericDAO<T extends Identified> implements GenericDAO<T> {

    protected Connection con;

    static final Logger LOGGER = Logger.getLogger(MySqlGenericDAO.class);

    protected abstract String getInsertQuery();

    protected abstract String getDeleteQuery();

    protected abstract String getSelectAllQuery();

    protected abstract String getSelectQuery();

    protected abstract String getUpdateQuery();

    protected abstract List<T> parseResultSet(ResultSet rs) throws SQLException;

    protected abstract void prepareStatementForInsert(PreparedStatement st, T object) throws SQLException;

    protected abstract void prepareStatementForUpdate(PreparedStatement st, T object) throws SQLException;

    protected static final String line_sep = System.lineSeparator();

    protected static final String EQ_PARAM = " = ?";

    protected static final String EQ_COMA = " = ?,";

    protected static final String COMA = ", ";

    public MySqlGenericDAO(Connection con) {
        this.con = con;
    }

    @Override
    public T getById(Integer id) throws SQLException {
        T obj = null;
        PreparedStatement pstm = con.prepareStatement(getSelectQuery());
        pstm.setInt(1, id);
        ResultSet rs = pstm.executeQuery();
        List<T> list = parseResultSet(rs);
        if (list != null) {
            obj = list.get(0);
        }
        pstm.close();
        return obj;
    }

    @Override
    public boolean insert(T object) throws SQLException {
        PreparedStatement pstm = con.prepareStatement(getInsertQuery(),
                PreparedStatement.RETURN_GENERATED_KEYS);
        prepareStatementForInsert(pstm, object);
        LOGGER.trace("Statement to insert the car in database: " + pstm.toString());
        pstm.executeUpdate();
        ResultSet rs = pstm.getGeneratedKeys();
        if (rs.next()) {
            object.setId(rs.getInt(1));
            return true;
        }
        pstm.close();
        return false;
    }

    @Override
    public boolean update(T object) throws SQLException {
        PreparedStatement pstm = con.prepareStatement(getUpdateQuery());
        prepareStatementForUpdate(pstm, object);
        LOGGER.trace("Statement to update car in database: " + pstm.toString());
        if (pstm.executeUpdate() > 0) {
            return true;
        }
        pstm.close();
        return false;
    }

    @Override
    public boolean deleteById(int id) throws SQLException {
        PreparedStatement pstm = con.prepareStatement(getDeleteQuery());
        pstm.setInt(1, id);
        LOGGER.trace("Statement to delete car in database: " + pstm.toString());
        if (pstm.executeUpdate() == 1) {
            return true;
        }
        pstm.close();
        return false;
    }

    @Override
    public List<T> getAll() throws SQLException {
        List<T> list = null;
        PreparedStatement pstm = con.prepareStatement(getSelectAllQuery());
        ResultSet rs = pstm.executeQuery();
        list = parseResultSet(rs);
        pstm.close();
        return list;
    }

    protected List<T> getByStringParam(String sql, String value) throws SQLException {
        List<T> list;
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, value);
        LOGGER.trace("Statement to get car from database by parameter: " + pstm.toString());
        ResultSet rs = pstm.executeQuery();
        list = parseResultSet(rs);
        pstm.close();
        return list;
    }

    protected List<T> getByIntParam(String sql, int value) throws SQLException {
        List<T> list;
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setInt(1, value);
        LOGGER.trace("Statement to get car from database by parameter: " + pstm.toString());
        ResultSet rs = pstm.executeQuery();
        list = parseResultSet(rs);
        pstm.close();
        return list;
    }
}
