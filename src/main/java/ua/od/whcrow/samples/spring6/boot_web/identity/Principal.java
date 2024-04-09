package ua.od.whcrow.samples.spring6.boot_web.identity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.od.whcrow.samples.spring6.boot_web._commons.model.UuidRevisionableModel;

import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Principal extends UuidRevisionableModel {
	
	public abstract String getName();
	
	@Nonnull
	public abstract Set<String> getAuthorities();
	
}
