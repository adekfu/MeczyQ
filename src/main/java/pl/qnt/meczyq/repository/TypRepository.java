package pl.qnt.meczyq.repository;

import pl.qnt.meczyq.domain.Typ;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Typ entity.
 */
@SuppressWarnings("unused")
public interface TypRepository extends JpaRepository<Typ,Long> {

}
