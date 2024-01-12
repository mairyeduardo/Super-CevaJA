package cevaja.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "Pedido_Tb")
@Data
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String nome;
    @Column
    private String email;
    @Column
    private Long idUsuario;
    @Column
    private List<Cerveja> cervejaList;
    @Column
    private Long quantidade;

}
