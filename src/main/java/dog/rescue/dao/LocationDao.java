package dog.rescue.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import dog.rescue.enity.Location;

public interface LocationDao extends JpaRepository<Location, Long> {

}
