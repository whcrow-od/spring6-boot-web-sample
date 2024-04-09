package ua.od.whcrow.samples.spring6.boot_web.identity.persistence;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.od.whcrow.samples.spring6.boot_web.identity.Identity;

import java.util.Optional;
import java.util.UUID;

// TODO: Re-check whether Spring Boot (v. 3.2.0) still searches for a custom repo implementation under
//  this repo's package/subpackages only. If such limitation is not a thing anymore,
//  we can rid of IdentityCustomRepository extending (and IdentityCustomRepositoryImpl)
//  and directly extend EntityMetaProvider<Identity>
//  (since IdentityCustomRepository and IdentityCustomRepositoryImpl exist only to workaround that limitation).
@Repository
interface IdentityRepository extends JpaRepository<Identity,UUID>, IdentityCustomRepository {
	
	@Nonnull
	Optional<Identity> findByUsername(@Nullable String username);
	
}
