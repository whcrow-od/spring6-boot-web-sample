package ua.od.whcrow.samples.spring6.boot_web._commons.security;

import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

// TODO: Change static pwd constants to application properties
@Lazy
@Service
class PasswordProviderImpl implements PasswordProvider {
	
	private static final int MIN_LENGTH = 8;
	private static final int MAX_LENGTH = 16;
	private static final int UPPERCASE_MIN_COUNT = 1;
	private static final int LOWERCASE_MIN_COUNT = 1;
	private static final int DIGITS_MIN_COUNT = 1;
	private static final int SPECIAL_CHARS_MIN_COUNT = 1;
	private static final String SPECIAL_CHARS = "!\"#%&'()*,-./:;?@[\\]_{}$+<=>^`|~";
	private static final String HINT = MIN_LENGTH + " to " + MAX_LENGTH + " characters, at least "
			+ UPPERCASE_MIN_COUNT + " uppercase letter" + (UPPERCASE_MIN_COUNT > 1 ? "s" : "") + ", "
			+ LOWERCASE_MIN_COUNT + " lowercase letter" + (LOWERCASE_MIN_COUNT > 1 ? "s" : "") + ", "
			+ DIGITS_MIN_COUNT + " digit" + (DIGITS_MIN_COUNT > 1 ? "s" : "") + " and "
			+ SPECIAL_CHARS_MIN_COUNT + " special character" + (SPECIAL_CHARS_MIN_COUNT > 1 ? "s" : "")
			+ " (" + SPECIAL_CHARS + ")";
	private static final String REGEX = "(?=^.{" + MIN_LENGTH + "," + MAX_LENGTH + "}$)"
			+ "(?=(.*[A-Z]){" + UPPERCASE_MIN_COUNT + "})"
			+ "(?=(.*[a-z]){" + LOWERCASE_MIN_COUNT + "})"
			+ "(?=(.*\\d){" + DIGITS_MIN_COUNT + "})"
			+ "(?=(.*[\\Q" + SPECIAL_CHARS + "\\E]){" + SPECIAL_CHARS_MIN_COUNT + "})"
			+ "(?!.*[\\s])^.*";
	private final PasswordBuilderProvider passwordBuilderProvider;
	
	@Autowired
	public PasswordProviderImpl(@Nonnull PasswordBuilderProvider passwordBuilderProvider) {
		this.passwordBuilderProvider = passwordBuilderProvider;
	}
	
	@Nonnull
	@Override
	public String getHint() {
		return HINT;
	}
	
	@Nonnull
	@Override
	public String random() {
		return passwordBuilderProvider.random()
				.minLength(MIN_LENGTH)
				.maxLength(MAX_LENGTH)
				.uppercaseLettersMinCount(UPPERCASE_MIN_COUNT)
				.lowercaseLettersMinCount(LOWERCASE_MIN_COUNT)
				.digitsMinCount(DIGITS_MIN_COUNT)
				.specialCharsMinCount(SPECIAL_CHARS_MIN_COUNT)
				.specialChars(SPECIAL_CHARS.toCharArray())
				.build();
	}
	
}
