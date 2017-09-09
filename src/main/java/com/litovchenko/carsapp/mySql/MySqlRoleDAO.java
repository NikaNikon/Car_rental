package com.litovchenko.carsapp.mySql;

import com.litovchenko.carsapp.dao.RoleDAO;
import com.litovchenko.carsapp.model.Role;

import javax.persistence.PersistenceException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.litovchenko.carsapp.model.Constants.*;

public class MySqlRoleDAO extends MySqlGenericDAO<Role> implements RoleDAO {

    private static final String SQL_INSERT_QUERY;
    private static final String SQL_UPDATE_QUERY;
    private static final String SQL_DELETE_QUERY;
    private static final String SQL_SELECT_ALL_QUERY;
    private static final String SQL_SELECT_QUERY;

    static {
        SQL_SELECT_ALL_QUERY = "SELECT * FROM " + ROLES;
        SQL_SELECT_QUERY = SQL_SELECT_ALL_QUERY + " WHERE " + ID + EQ_PARAM;
        SQL_DELETE_QUERY = "DELETE FROM " + ROLES + " WHERE " + ID + EQ_PARAM;
        SQL_INSERT_QUERY = "INSERT INTO " + ROLES + "(" + ROLE_NAME + ") VALUES (?)";
        SQL_UPDATE_QUERY = "UPDATE " + ROLES + " SET " + ROLE_NAME + EQ_PARAM + " WHERE " + ID + EQ_PARAM;
    }

    public MySqlRoleDAO(Connection con) {
        super(con);
    }

    @Override
    public Role getByRoleName(String roleName) {
        List<Role> list;
        String sql = SQL_SELECT_ALL_QUERY + " WHERE " + ROLE_NAME + EQ_PARAM;
        list = getByStringParam(sql, roleName);
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
    protected List<Role> parseResultSet(ResultSet rs) {
        List<Role> list = new ArrayList<>();
        try {
            while (rs.next()) {
                Role role = new Role(rs.getInt(ID), rs.getString(ROLE_NAME));
                list.add(role);
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
    protected void prepareStatementForInsert(PreparedStatement st, Role object) {
        try {
            st.setString(1, object.getRoleName());
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement st, Role object) {
        try {
            st.setString(1, object.getRoleName());
            st.setInt(2, object.getId());
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }
}
