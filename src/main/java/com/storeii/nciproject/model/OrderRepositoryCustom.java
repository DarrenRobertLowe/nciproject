/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Main
 */
public interface OrderRepositoryCustom {
    ArrayList<OrderItem> findOrderItems(Order order);
}
