package com.springframework.csvtodbapp.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name="inventory_data")
public class InventoryData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="code")
    private String code;

    @Column(name="product_name")
    private String name;

    @Column(name="batch")
    private String batch;

    @Column(name="stock")
    private Integer stock;

    @Column(name="deal")
    private Integer deal;

    @Column(name="free")
    private Integer free;

    @Column(name="mrp")
    private float mrp;

    @Column(name="rate")
    private float rate;

    @Column(name="expiry")
    private Date expiry;

    @Column(name="company")
    private String company;

    @Column(name="supplier")
    private String supplier;

    public InventoryData(Long id,String code, String name, String batch, Integer stock, Integer deal, Integer free, float mrp, float rate, Date expiry, String company, String supplier) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.batch = batch;
        this.stock = stock;
        this.deal = deal;
        this.free = free;
        this.mrp = mrp;
        this.rate = rate;
        this.expiry = expiry;
        this.company = company;
        this.supplier = supplier;
    }
}
