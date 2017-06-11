package com.yishuailuo.zerobased.service;

import com.yishuailuo.projects.myframework.annotation.Service;
import com.yishuailuo.zerobased.helper.DatabaseHelper;
import com.yishuailuo.zerobased.model.Customer;
import com.yishuailuo.zerobased.util.PropsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by luoyishuai on 17/6/4.
 */
@Service
public class CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    static {
        Properties conf = PropsUtil.loadProps("config.properties");
        DRIVER = conf.getProperty("jdbc.driver");
        URL = conf.getProperty("jdbc.url");
        USERNAME = conf.getProperty("jdbc.username");
        PASSWORD = conf.getProperty("jdbc.password");

        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            LOGGER.error("can not load jdbc driver", e);
        }
    }

    public List<Customer> getCustomerWithoutDatabaseHelper() {
        Connection conn = null;
        try {
            List<Customer> customerList = new ArrayList<Customer>();
            String sql = "SELECT * FROM zerobased_customer";
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            Customer customer = null;
            while (rs.next()) {
                customer = new Customer();
                customer.setId(rs.getLong("id"));
                customer.setName(rs.getString("name"));
                customer.setContact(rs.getString("contact"));
                customer.setTelephone(rs.getString("telephone"));
                customer.setEmail(rs.getString("email"));
                customer.setRemark(rs.getString("remark"));
                customerList.add(customer);
            }
            return customerList;
        } catch (SQLException e) {
            LOGGER.error("execute sql failure", e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error("close connection failure", e);
                }
            }
        }
        return null;
    }

    public List<Customer> getCustomerList() {
        String sql = "SELECT * FROM zerobased_customer";
        return DatabaseHelper.queryEntityList(Customer.class, sql);
    }

    public Customer getCustomer(Long id) {
        String sql = "SELECT * FROM zerobased_customer where id = ?";
        return DatabaseHelper.queryEntity(Customer.class, sql, id);
    }

    public boolean createCustomer(Map<String, Object> fieldMap) {
        return DatabaseHelper.insertEntity(Customer.class, fieldMap);
    }

    public boolean updateCustomer(Long id, Map<String, Object> fieldMap) {
        return DatabaseHelper.updateEntity(Customer.class, id, fieldMap);
    }

    public boolean deleteCustomer(Long id) {
        return DatabaseHelper.deleteEntity(Customer.class, id);
    }
}
