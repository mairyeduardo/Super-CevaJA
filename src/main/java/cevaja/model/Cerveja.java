package cevaja.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "Cerveja_Tb")
@Data
public class Cerveja {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String tipo;
    @Column
    private BigDecimal valor;
    @Column
    private BigDecimal quantidade;

}