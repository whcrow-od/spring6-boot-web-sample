package ua.od.whcrow.samples.spring6.boot_web._commons.security;

import jakarta.annotation.Nonnull;

import java.util.Random;
import java.util.function.Supplier;

public interface RandomPasswordBuilder {
	
	RandomPasswordBuilder minLength(Integer minLength);
	
	Integer minLength();
	
	RandomPasswordBuilder maxLength(Integer maxLength);
	
	Integer maxLength();
	
	RandomPasswordBuilder uppercaseLettersMinCount(Integer uppercaseLettersMinCount);
	
	Integer uppercaseLettersMinCount();
	
	RandomPasswordBuilder lowercaseLettersMinCount(Integer lowercaseLettersMinCount);
	
	Integer lowercaseLettersMinCount();
	
	RandomPasswordBuilder digitsMinCount(Integer digitsMinCount);
	
	Integer digitsMinCount();
	
	RandomPasswordBuilder specialCharsMinCount(Integer specialCharsMinCount);
	
	Integer specialCharsMinCount();
	
	RandomPasswordBuilder specialChars(char[] specialChars);
	
	char[] specialChars();
	
	RandomPasswordBuilder randomizer(Supplier<Random> random);
	
	Supplier<Random> randomizer();
	
	@Nonnull
	String build();
	
}
