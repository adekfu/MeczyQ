package pl.qnt.meczyq.repository;

import pl.qnt.meczyq.domain.Mecz;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Mecz entity.
 */
@SuppressWarnings("unused")
public interface MeczRepository extends JpaRepository<Mecz,Long> {

}
