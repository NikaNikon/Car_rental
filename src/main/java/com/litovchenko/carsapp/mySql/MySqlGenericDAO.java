package com.litovchenko.carsapp.mySql;

import com.litovchenko.carsapp.dao.GenericDAO;
import com.litovchenko.carsapp.model.Car;
import com.litovchenko.carsapp.model.Identified;

import javax.persistence.PersistenceException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class MySqlGenericDAO<T extends Identified> implements GenericDAO<T> {

    protected Connection con;

    protected abstract String getInsertQuery();

    protected abstract String getDeleteQuery();

    protected abstract String getSelectAllQuery();

    protected abstract String getSelectQuery();

    protected abstract String getUpdateQuery();

    protected abstract List<T> parseResultSet(ResultSet rs);

    protected abstract void prepareStatementForInsert(PreparedStatement st, T object);

    protected abstract void prepareStatementForUpdate(PreparedStatement st, T object);

    protected static final String line_sep = System.lineSeparator();

    protected static final String EQ_PARAM = " = ?";

    protected static final String EQ_COMA = " = ?,";

    protected static final String COMA = ", ";

    public MySqlGenericDAO(Connection con) {
        this.con = con;
    }

    @Override
    public T getById(Integer id) {
        T obj = null;
        try (PreparedStatement pstm = con.prepareStatement(getSelectQuery())) {
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            List<T> list = parseResultSet(rs);
            if(list!=null) {
                obj = list.get(0);
            }
        } catch (SQLException e) {
            System.out.println("Cannot get object by id" + line_sep + e);
        }
        return obj;
    }

    @Override
    public boolean insert(T object) {
        try (PreparedStatement pstm = con.prepareStatement(getInsertQuery(),
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            prepareStatementForInsert(pstm, object);
            pstm.executeUpdate();
            ResultSet rs = pstm.getGeneratedKeys();
            if (rs.next()) {
                object.setId(rs.getInt(1));
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Cannot insert object" + line_sep + e);
        }
        return false;
    }

    @Override
    public boolean update(T object) {
        try (PreparedStatement pstm = con.prepareStatement(getUpdateQuery())) {
            prepareStatementForUpdate(pstm, object);
            if (pstm.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Cannot update object" + line_sep + e);
            return false;
        }
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        try (PreparedStatement pstm = con.prepareStatement(getDeleteQuery())) {
            pstm.setInt(1, id);
            if(pstm.executeUpdate()==1){
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Cannot delete object" + line_sep + e);
        }
        return false;
    }

    @Override
    public List<T> getAll() {
        List<T> list = null;
        try (PreparedStatement pstm = con.prepareStatement(getSelectAllQuery())) {
            ResultSet rs = pstm.executeQuery();
            list = parseResultSet(rs);
        } catch (SQLException e) {
            System.out.println("Cannot get all from table" + line_sep + e);
        }
        return list;
    }

    protected List<T> getByStringParam(String sql, String value) {
        List<T> list;
        try (PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setString(1, value);
            ResultSet rs = pstm.executeQuery();
            list = parseResultSet(rs);
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
        return list;
    }

    protected List<T> getByIntParam(String sql, int value) {
        List<T> list;
        try (PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setInt(1, value);
            ResultSet rs = pstm.executeQuery();
            list = parseResultSet(rs);
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
        return list;
    }
}
