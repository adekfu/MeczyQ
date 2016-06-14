package pl.qnt.meczyq.repository;

import pl.qnt.meczyq.domain.Typ;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Typ entity.
 */
@SuppressWarnings("unused")
public interface TypRepository extends JpaRepository<Typ,Long> {

    @Query("select typ from Typ typ where typ.user.login = ?#{principal.username}")
    List<Typ> findByUserIsCurrentUser();

    @Query("select typ from Typ typ where typ.mecz.wynikDruzyna1 is not null and typ.mecz.wynikDruzyna2 is not null and typ.mecz.dataMeczu <= now()")
    List<Typ> znajdzWszystkieZWynikiem();

}
