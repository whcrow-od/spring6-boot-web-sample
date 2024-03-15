package ua.od.whcrow.samples.spring6.boot_web._commons.security;

import ua.od.whcrow.samples.spring6.boot_web._commons.Ascii128;
import jakarta.annotation.Nonnull;
import org.apache.commons.lang3.RandomStringUtils;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class BasicRandomPasswordBuilder implements RandomPasswordBuilder {
	
	public static final int MIN_LENGTH = 8;
	public static final int MAX_LENGTH = 32;
	public static final String SPECIAL_CHARS = Ascii128.findAll(ascii128 ->
					(ascii128.getType() == Ascii128.Type.PUNCTUATION || ascii128.getType() == Ascii128.Type.SYMBOL)
							&& ascii128 != Ascii128.SPACE)
			.stream()
			.map(Ascii128::getString)
			.collect(Collectors.joining());
	
	private static final char[] _SPECIAL_CHARS = SPECIAL_CHARS.toCharArray();
	
	private Integer minLength;
	private Integer maxLength;
	private Integer uppercaseLettersMinCount;
	private Integer lowercaseLettersMinCount;
	private Integer digitsMinCount;
	private Integer specialCharsMinCount;
	private char[] specialChars;
	private Supplier<Random> randomizer;
	
	@Override
	public BasicRandomPasswordBuilder minLength(Integer minLength) {
		this.minLength = minLength;
		return this;
	}
	
	@Override
	public Integer minLength() {
		return minLength;
	}
	
	@Override
	public BasicRandomPasswordBuilder maxLength(Integer maxLength) {
		this.maxLength = maxLength;
		return this;
	}
	
	@Override
	public Integer maxLength() {
		return maxLength;
	}
	
	@Override
	public BasicRandomPasswordBuilder uppercaseLettersMinCount(Integer uppercaseLettersMinCount) {
		this.uppercaseLettersMinCount = uppercaseLettersMinCount;
		return this;
	}
	
	@Override
	public Integer uppercaseLettersMinCount() {
		return uppercaseLettersMinCount;
	}
	
	@Override
	public BasicRandomPasswordBuilder lowercaseLettersMinCount(Integer lowercaseLettersMinCount) {
		this.lowercaseLettersMinCount = lowercaseLettersMinCount;
		return this;
	}
	
	@Override
	public Integer lowercaseLettersMinCount() {
		return lowercaseLettersMinCount;
	}
	
	@Override
	public BasicRandomPasswordBuilder digitsMinCount(Integer digitsMinCount) {
		this.digitsMinCount = digitsMinCount;
		return this;
	}
	
	@Override
	public Integer digitsMinCount() {
		return digitsMinCount;
	}
	
	@Override
	public BasicRandomPasswordBuilder specialCharsMinCount(Integer specialCharsMinCount) {
		this.specialCharsMinCount = specialCharsMinCount;
		return this;
	}
	
	@Override
	public Integer specialCharsMinCount() {
		return specialCharsMinCount;
	}
	
	@Override
	public BasicRandomPasswordBuilder specialChars(char[] specialChars) {
		this.specialChars = specialChars;
		return this;
	}
	
	@Override
	public char[] specialChars() {
		return specialChars;
	}
	
	@Override
	public BasicRandomPasswordBuilder randomizer(Supplier<Random> random) {
		this.randomizer = random;
		return this;
	}
	
	@Override
	public Supplier<Random> randomizer() {
		return randomizer;
	}
	
	@Nonnull
	protected Random _getRandom() {
		return Optional.ofNullable(randomizer()).orElse(SecureRandom::new).get();
	}
	
	protected int _getMinLength() {
		return Optional.ofNullable(minLength()).orElse(MIN_LENGTH);
	}
	
	protected int _getMaxLength() {
		return Optional.ofNullable(maxLength()).orElse(MAX_LENGTH);
	}
	
	protected int _getLength() {
		int minLength = _getMinLength();
		int maxLength = _getMaxLength();
		return _getRandom().ints(minLength, maxLength + 1).findFirst().orElse(maxLength);
	}
	
	protected int _getUppercaseLettersMinCount() {
		return Optional.ofNullable(uppercaseLettersMinCount()).orElse(0);
	}
	
	protected int _getLowercaseLettersMinCount() {
		return Optional.ofNullable(lowercaseLettersMinCount()).orElse(0);
	}
	
	protected int _getDigitsMinCount() {
		return Optional.ofNullable(digitsMinCount()).orElse(0);
	}
	
	protected int _getSpecialCharsMinCount() {
		return Optional.ofNullable(specialCharsMinCount()).orElse(0);
	}
	
	@Nonnull
	protected char[] _getSpecialChars() {
		return Optional.ofNullable(specialChars()).orElse(_SPECIAL_CHARS);
	}
	
	@Nonnull
	protected String _getRequiredUppercaseLetters() {
		return RandomStringUtils.random(_getUppercaseLettersMinCount(), 'A', 'Z', true, false, null, _getRandom());
	}
	
	@Nonnull
	protected String _getRequiredLowercaseLetters() {
		return RandomStringUtils.random(_getLowercaseLettersMinCount(), 'a', 'z', true, false, null, _getRandom());
	}
	
	@Nonnull
	protected String _getRequiredDigits() {
		return RandomStringUtils.random(_getDigitsMinCount(), '0', '9', false, true, null, _getRandom());
	}
	
	@Nonnull
	protected String _getRequiredSpecialChars() {
		char[] specialChars = _getSpecialChars();
		return RandomStringUtils.random(_getSpecialCharsMinCount(), 0, specialChars.length, false, false, specialChars,
				_getRandom());
	}
	
	@Nonnull
	protected String _getRestOfChars(int length) {
		return RandomStringUtils.random(length, 33, 126, true, true, null, _getRandom());
	}
	
	@Nonnull
	@Override
	public String build() {
		int length = _getLength();
		String uppercase = _getRequiredUppercaseLetters();
		String lowercase = _getRequiredLowercaseLetters();
		String numbers = _getRequiredDigits();
		String specials = _getRequiredSpecialChars();
		String requiredChars = uppercase.concat(lowercase).concat(numbers).concat(specials);
		String restOfChars = _getRestOfChars(length - requiredChars.length());
		List<Character> pwdChars = requiredChars.concat(restOfChars).chars()
				.mapToObj(c -> (char) c)
				.collect(Collectors.toCollection(ArrayList::new));
		Collections.shuffle(pwdChars);
		return pwdChars.stream()
				.map(Object::toString)
				.collect(Collectors.joining());
	}
	
}
