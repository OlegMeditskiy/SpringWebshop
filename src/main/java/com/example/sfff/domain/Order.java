package com.example.sfff.domain;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "ORDR")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Order() {
    }

    public Order(String userAddress){
        userAddress=this.userAddress;
    }

    @Enumerated(EnumType.ORDINAL)
    private OrderStatus orderStatus=OrderStatus.checking;

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    Set<OrderProduct> orderProducts;

    public Set<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public Set<OrderProduct> getSortedOrderProducts(){
        List<OrderProduct> sorted = new ArrayList<>(getOrderProducts());
        Collections.sort(sorted, (p1,p2)->{return (int) (p1.getId()-p2.getId());});
        Set<OrderProduct> sortedOrderProducts = new LinkedHashSet<>(sorted);
        return sortedOrderProducts;
    }

    public void setOrderProducts(Set<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    private String userAddress;

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public int getTotalSum(){
        Set<OrderProduct> products = getOrderProducts();
        int summ=0;
        for (OrderProduct product: products){
            summ+=product.getQuantity()*product.getProduct().getPrice();
        }
        return summ;
    }
    public List<OrderStatus> getCategoryExcept(OrderStatus orderStatus){
        List<OrderStatus> orderStatuses = new ArrayList<>();
        for (OrderStatus orderStatus1: OrderStatus.values()){
            if(!orderStatus1.equals(orderStatus)){
                orderStatuses.add(orderStatus1);
            }
        }
        return orderStatuses;
    }
}
