package gm.dao;

import gm.entity.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by Jj on 4/21/17.
 */
public class CustomerDAO implements ResultSetExtractor<List<Customer>>{

    @Autowired
    JdbcTemplate template;

    public List<Customer> getAllCustomers()throws SQLException, ClassNotFoundException{
        return template.query("select * from customer order by firstName",this);}

    public Customer getCustomerById(int id)throws SQLException, ClassNotFoundException{
        List<Customer> customers= template.query("select * from customer where customerId=?", new Object[]{id},this);
        if(customers!=null&&!customers.isEmpty())
            return customers.get(0);
        return null;
    }

    public void addCustomer(Customer customer)throws SQLException, ClassNotFoundException{
        template.update("INSERT INTO CUSTOMER (FIRSTNAME,LASTNAME,EMAIL,PHONE,ADDRESS) VALUES(?,?,?,?,?)",
                new Object[]{customer.getFirstName(),customer.getLastName(),customer.getEmail(),customer.getPhone(),customer.getAddress()});
        System.out.println("customer added");
    }

    public void updateCustomer(Customer customer)throws SQLException, ClassNotFoundException{
        template.update("UPDATE CUSTOMER SET FIRSTNAME=?,LASTNAME=?,EMAIL=?,PHONE=?,ADDRESS=? WHERE CUSTOMERID=?",
                new Object[]{customer.getFirstName(),customer.getLastName(),customer.getEmail(),customer.getPhone(),customer.getAddress(),customer.getCustomerId()});
        System.out.println("customer updated");
    }

    public void deleteCustomer(int id)throws SQLException, ClassNotFoundException{
        template.update("DELETE FROM CUSTOMER WHERE CUSTOMERID=?",new Object[]{id});
        System.out.printf("customer deleted");
    }


    @Override
    public List<Customer> extractData(ResultSet resultSet) throws SQLException {
        List<Customer> customers=new ArrayList<Customer>();
        while(resultSet.next()){
            Customer a=new Customer();
            a.setFirstName(resultSet.getString("firstName"));
            a.setLastName(resultSet.getString("lastName"));
            a.setEmail(resultSet.getString("email"));
            a.setAddress(resultSet.getString("address"));
            a.setPhone(resultSet.getObject("phone")!=null?resultSet.getInt("phone"):null);
            a.setCustomerId(resultSet.getInt("customerId"));
            customers.add(a);
        }
        return customers;
    }
}
