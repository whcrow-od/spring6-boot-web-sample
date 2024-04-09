package ua.od.whcrow.samples.spring6.boot_web._commons.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.Transient;
import lombok.NoArgsConstructor;
import ua.od.whcrow.samples.spring6.boot_web._commons.persistence.EntityUtils;
import ua.od.whcrow.samples.spring6.boot_web._commons.persistence.Stringable;

import java.util.Objects;

@NoArgsConstructor
public abstract class AbstractModel<ID> extends Stringable implements Model<ID> {
	
	@Stringable.Ignore
	@Transient
	public boolean isNewInstance() {
		return getId() == null;
	}
	
	@Override
	public final boolean equals(@Nullable Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (getId() == null) {
			return false;
		}
		if (_getEffectiveType() != EntityUtils.getEntityType(obj)) {
			return false;
		}
		return Objects.equals(getId(), ((AbstractModel<?>) obj).getId());
	}
	
	@Override
	public final int hashCode() {
		return _getEffectiveType().hashCode();
	}
	
}
