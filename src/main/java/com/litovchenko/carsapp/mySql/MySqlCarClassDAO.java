package com.litovchenko.carsapp.mySql;

import com.litovchenko.carsapp.dao.CarClassDAO;
import com.litovchenko.carsapp.model.CarClass;

import javax.persistence.PersistenceException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.litovchenko.carsapp.model.Constants.*;

public class MySqlCarClassDAO extends MySqlGenericDAO<CarClass> implements CarClassDAO {

    private static final String SQL_SELECT_QUERY;
    private static final String SQL_INSERT_QUERY;
    private static final String SQL_UPDATE_QUERY;
    private static final String SQL_SELECT_ALL_QUERY;
    private static final String SQL_DELETE_QUERY;

    static{
        SQL_SELECT_QUERY = "SELECT * FROM " + CAR_CLASSES + " WHERE " + ID + EQ_PARAM;
        SQL_SELECT_ALL_QUERY = "SELECT * FROM " + CAR_CLASSES;
        SQL_UPDATE_QUERY = "UPDATE " + CAR_CLASSES + " SET " + CAR_CLASS_NAME + " = ? WHERE " + ID + EQ_PARAM;
        SQL_INSERT_QUERY = "INSERT INTO " + CAR_CLASSES + "(" + ID + ", " + CAR_CLASS_NAME + ") VALUES (DEFAULT, ?)";
        SQL_DELETE_QUERY = "DELETE FROM " + CAR_CLASSES + "WHERE " + ID + EQ_PARAM;
    }

    public MySqlCarClassDAO(Connection con) {
        super(con);
    }

    @Override
    protected String getSelectQuery() {
        return SQL_SELECT_QUERY;
    }

    @Override
    protected String getSelectAllQuery() {
        return SQL_SELECT_ALL_QUERY;
    }

    @Override
    protected String getUpdateQuery() {
        return SQL_UPDATE_QUERY;
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
    protected List<CarClass> parseResultSet(ResultSet rs) {
        List<CarClass> list = new ArrayList<>();
        try{
            while(rs.next()){
                CarClass cc = new CarClass(rs.getInt(ID), rs.getString(CAR_CLASS_NAME));

                list.add(cc);
            }
        } catch (SQLException e){
            throw new PersistenceException(e);
        }
        if(list.isEmpty()){
            throw new PersistenceException("Data not found");
        }
        return list;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement st, CarClass object) {
        try{
            st.setString(1, object.getCarClassName());
        } catch (SQLException e){
            throw new PersistenceException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement st, CarClass object) {
        try{
            st.setString(1, object.getCarClassName());
            st.setInt(2, object.getId());
        } catch (SQLException e){
            throw new PersistenceException(e);
        }
    }


    @Override
    public boolean removeByClassName(String class_name) {
        String sql = "DELETE FROM " + CAR_CLASSES + "WHERE " + CAR_CLASS_NAME + "LIKE ?";
        try(PreparedStatement pstm = con.prepareStatement(sql)){
            pstm.setString(1, class_name);
            pstm.executeUpdate();
        } catch (SQLException e){
            System.out.println("Cannot delete car class" + line_sep + e);
            return false;
        }
        return true;
    }
}
