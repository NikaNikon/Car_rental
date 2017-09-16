package com.litovchenko.carsapp.mySql;

import com.litovchenko.carsapp.dao.UserDAO;
import com.litovchenko.carsapp.model.Role;
import com.litovchenko.carsapp.model.User;

import javax.persistence.PersistenceException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.litovchenko.carsapp.model.Constants.*;

public class MySqlUserDAO extends MySqlGenericDAO<User> implements UserDAO {

    private static final String SQL_INSERT_QUERY;
    private static final String SQL_UPDATE_QUERY;
    private static final String SQL_DELETE_QUERY;
    private static final String SQL_SELECT_ALL_QUERY;
    private static final String SQL_SELECT_QUERY;

    private static final String SELECT_BY_TEMPLATE;

    static {
        SQL_SELECT_ALL_QUERY = "SELECT * FROM " + USERS;
        SELECT_BY_TEMPLATE = SQL_SELECT_ALL_QUERY + " WHERE ";
        SQL_SELECT_QUERY = SELECT_BY_TEMPLATE + ID + EQ_PARAM;
        SQL_DELETE_QUERY = "DELETE FROM " + USERS + " WHERE " + ID + EQ_PARAM;

        SQL_INSERT_QUERY = "INSERT INTO " + USERS + "(" + ID + COMA + USER_ROLE_ID + COMA + LOGIN + COMA +
                PASSWORD + COMA + EMAIL + COMA + BLOCKED + ") VALUES (DEFAULT, DEFAULT, ?, ?, ?, DEFAULT)";

        SQL_UPDATE_QUERY = "UPDATE " + USERS + " SET " + USER_ROLE_ID + EQ_COMA + LOGIN + EQ_COMA + PASSWORD +
                EQ_COMA + EMAIL + EQ_COMA + BLOCKED + EQ_PARAM + " WHERE " + ID + EQ_PARAM;
    }

    public MySqlUserDAO(Connection con) {
        super(con);
    }

    @Override
    public User getByLogin(String login, String password) {
        User user = null;
        String sql = SELECT_BY_TEMPLATE + LOGIN + " LIKE ?" + " AND " + PASSWORD + " LIKE ? ";
        try (PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setString(1, login);
            pstm.setString(2, password);
            ResultSet rs = pstm.executeQuery();
            List<User> list = parseResultSet(rs);
            if(list!=null){
                user = list.get(0);
            }
            /*if (rs.next()) {
                MySqlPassportDataDAO passportDataDAO = new MySqlPassportDataDAO(con);
                //get role dao here
                user = new User(rs.getInt(ID), rs.getInt(USER_ROLE_ID), rs.getString(LOGIN),
                        rs.getString(PASSWORD), rs.getString(EMAIL), rs.getBoolean(BLOCKED));
                user.setPassportData(passportDataDAO.getByUserLogin(login));
            }*/
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
        return user;
    }

    @Override
    public List<User> getByRole(String role) {
        List<User> list;
        String sql = SQL_SELECT_ALL_QUERY + COMA + ROLES + " WHERE " + ROLES + "." + ID + " = " + USER_ROLE_ID +
                " AND " + ROLE_NAME + " LIKE ?";
        list = getByStringParam(sql, role);
        return list;
    }

    @Override
    public List<User> getDeptors() {
        List<User> list;
        String sql = SQL_SELECT_ALL_QUERY + COMA + REPAIRMENT_CHECKS + " WHERE " +
                USER_ID + " = " + USERS + "." + ID + " AND " + CHECK_STATUS + " LIKE \"UNPAYED\"";
        try (Statement stm = con.createStatement()) {
            ResultSet rs = stm.executeQuery(sql);
            list = parseResultSet(rs);
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
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
    protected List<User> parseResultSet(ResultSet rs) {
        List<User> list = new ArrayList<>();
        try {
            while (rs.next()) {
                MySqlPassportDataDAO passportDataDAO = new MySqlPassportDataDAO(con);
                MySqlRoleDAO roleDAO = new MySqlRoleDAO(con);
                User user = new User(rs.getInt(ID), rs.getInt(USER_ROLE_ID), rs.getString(LOGIN),
                        rs.getString(PASSWORD), rs.getString(EMAIL), rs.getBoolean(BLOCKED));
                user.setPassportData(passportDataDAO.getById(rs.getInt(ID)));
                Role role = roleDAO.getById(rs.getInt(USER_ROLE_ID));
                if(role!=null) {
                    user.setRole(role.getRoleName());
                }
                list.add(user);
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
    protected void prepareStatementForInsert(PreparedStatement st, User object) {
        try {
            st.setString(1, object.getLogin());
            st.setString(2, object.getPassword());
            st.setString(3, object.getEmail());
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement st, User object) {
        try {
            st.setInt(1, object.getRoleId());
            st.setString(2, object.getLogin());
            st.setString(3, object.getPassword());
            st.setString(4, object.getEmail());
            st.setBoolean(5, object.isBlocked());
            st.setInt(6, object.getId());
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public boolean updateBlockedStatus(boolean isBlocked, int id) {
        int blocked = isBlocked == true? 1 : 0;
        String sql = "UPDATE " + USERS + " SET " + BLOCKED + " = " + blocked +
                " WHERE " + ID + " = " + id;
        try(PreparedStatement pstm = con.prepareStatement(sql)){
            if(pstm.executeUpdate() == 1){
                return true;
            }
        } catch (SQLException e){
            throw new PersistenceException(e);
        }
        return false;
    }

    @Override
    public boolean insertWithRole(User user) {
        String sql = "INSERT INTO " + USERS + "(" + ID + COMA + USER_ROLE_ID + COMA + LOGIN + COMA
                + PASSWORD + COMA + EMAIL + COMA + BLOCKED + ") VALUES (DEFAULT, ?, ?, ?, ?, DEFAULT)";
        try(PreparedStatement pstm = con.prepareStatement(sql)){
            pstm.setInt(1, user.getRoleId());
            pstm.setString(2, user.getLogin());
            pstm.setString(3, user.getPassword());
            pstm.setString(4, user.getEmail());
            if(pstm.executeUpdate() == 1){
                return true;
            }
        } catch (SQLException e){
            throw new PersistenceException(e);
        }
        return false;
    }
}
