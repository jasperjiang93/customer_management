package gm.service;

import gm.dao.CustomerDAO;
import gm.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Jj on 4/21/17.
 */
@RestController
public class CustomerService {
    @Autowired
    CustomerDAO customerDAO;

    @RequestMapping(value="/getAllCustomers",method= RequestMethod.GET,produces="application/json")
    public List<Customer> getAllCustomers()throws SQLException, ClassNotFoundException{
        List<Customer> customers =customerDAO.getAllCustomers();
        return customers;
    }
    @RequestMapping(value="/getCustomerById/{id}",method = RequestMethod.GET,produces = "application/json")
    public Customer getCustomerById(@PathVariable int id)throws SQLException, ClassNotFoundException{
        return customerDAO.getCustomerById(id);
    }


    @RequestMapping(value="/initialCustomer",method=RequestMethod.GET,produces="application/json")
    public Customer initialCustomer()throws SQLException, ClassNotFoundException{return new Customer();}

    @Transactional
    @RequestMapping(value="/addCustomer",method=RequestMethod.POST,consumes="application/json")
    public void addCustomer(@RequestBody Customer customer)throws SQLException, ClassNotFoundException{
        customerDAO.addCustomer(customer);}
    @Transactional
    @RequestMapping(value="/updateCustomer",method=RequestMethod.POST,consumes="application/json")
    public void updateCustomer(@RequestBody Customer customer)throws SQLException, ClassNotFoundException{
         customerDAO.updateCustomer(customer);
    }
    @Transactional
    @RequestMapping(value="/deleteCustomer/{id}",method=RequestMethod.GET,produces="application/json")
    public void deleteCustomer(@PathVariable int id)throws SQLException, ClassNotFoundException{
        customerDAO.deleteCustomer(id);
    }

    @RequestMapping(value="/searchCustomer/{name}",method=RequestMethod.GET,produces = "application/json")
    public void searchCustomer(@PathVariable String name)throws SQLException, ClassNotFoundException{

    }


}
