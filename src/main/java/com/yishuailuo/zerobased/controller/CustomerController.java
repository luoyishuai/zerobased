package com.yishuailuo.zerobased.controller;

import com.yishuailuo.projects.myframework.annotation.Action;
import com.yishuailuo.projects.myframework.annotation.Controller;
import com.yishuailuo.projects.myframework.annotation.Inject;
import com.yishuailuo.projects.myframework.bean.Param;
import com.yishuailuo.projects.myframework.bean.View;
import com.yishuailuo.zerobased.model.Customer;
import com.yishuailuo.zerobased.service.CustomerService;

import java.util.List;

/**
 * Created by luoyishuai on 17/6/11.
 */
@Controller
public class CustomerController {

    @Inject
    private CustomerService customerService;

    @Action("get:/customer")
    public View index(Param param) {
        List<Customer> customerList = customerService.getCustomerList();
        return new View("customer.jsp").addModel("customerList", customerList);
    }
}
