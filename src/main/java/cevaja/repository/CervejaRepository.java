package cevaja.repository;

import cevaja.model.Cerveja;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CervejaRepository extends JpaRepository<Cerveja, Long> {

    public Cerveja findByTipo(String tipo);

}
