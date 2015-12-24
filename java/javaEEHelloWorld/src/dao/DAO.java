package dao;

import javaBean.Bean;
import servlet.MyDataSource;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by zzt on 12/23/15.
 * <p>
 * Usage:
 */
public abstract class DAO {

    protected Connection connection;

    public DAO() throws SQLException, NamingException {
        connection = MyDataSource.getConnection();
    }

    /**
     * @param t The key
     *
     * @return The sql statement which is a select statement using a unique/foreign key
     */
    public abstract <T> PreparedStatement getKeySelect(T t) throws SQLException;


    public abstract String getUpdate();

    public abstract String getInsert();

    /**
     * set key and select from table
     *
     * @param t   key
     * @param <T> key type
     * @param <E> result java bean
     *
     * @return java bean list
     */
    public <T, E extends Bean> ArrayList<E> keySelect(T t) throws SQLException {
        ArrayList<E> res = new ArrayList<>();
        PreparedStatement preparedStatement = getKeySelect(t);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            selectRes(resultSet, res);
        }
        return res;
    }

    public void close() throws SQLException {
        connection.close();
    }

    protected abstract <E extends Bean> void selectRes(ResultSet resultSet, ArrayList<E> res) throws SQLException;


}
