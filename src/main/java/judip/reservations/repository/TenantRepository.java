package judip.reservations.repository;

import judip.reservations.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepository extends JpaRepository<Tenant, Long> {
    Tenant findFirstByName(String name);
}
