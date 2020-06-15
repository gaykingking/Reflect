package com.wanchao;

public class OrderServiceImpl implements OrderService {
    public String addOrder(String orderId, String orderName) {
        return "orderId为："+orderId+" orderName为："+orderName;
    }
}