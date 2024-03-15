package ua.od.whcrow.samples.spring6.boot_web.identity.persistence;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.od.whcrow.samples.spring6.boot_web.identity.Identity;

import java.util.Optional;
import java.util.UUID;

@Repository
interface IdentityRepository extends JpaRepository<Identity,UUID>, IdentityCustomRepository {
	
	@Nonnull
	Optional<Identity> findByUsername(@Nullable String username);
	
}
