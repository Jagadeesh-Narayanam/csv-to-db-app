package com.springframework.csvtodbapp.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Data
public class InventoryData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private String name;
    private String batch;
    private Integer stock;
    private Integer deal;
    private Integer free;
    private float mrp;
    private float rate;
    private Date expiry;
    private String company;
    private String supplier;

}
