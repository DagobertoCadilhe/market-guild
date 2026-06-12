package com.marketguild.player_service.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "balance")
    private Double balance;

    @ElementCollection
    @CollectionTable(name = "player_bag", joinColumns = @JoinColumn(name = "player_id"))
    @Column(name = "item_id")
    private List<String> bag = new ArrayList<>();

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void insertIntoBag(String itemId){
        bag.add(itemId);
    }

    public List<String> getBag() {
        return bag;
    }

}
