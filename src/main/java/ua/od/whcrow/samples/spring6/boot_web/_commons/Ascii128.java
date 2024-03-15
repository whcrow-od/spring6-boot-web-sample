package ua.od.whcrow.samples.spring6.boot_web._commons;

import jakarta.annotation.Nonnull;
import ua.od.whcrow.samples.spring6.boot_web._commons.util.Assert;
import ua.od.whcrow.samples.spring6.boot_web._commons.util.Msg;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public enum Ascii128 {
	
	// ---------------------------- CONTROL ----------------------------
	NULL(Type.CONTROL, 0),
	START_OF_HEADING(Type.CONTROL, 1),
	START_OF_TEXT(Type.CONTROL, 2),
	END_OF_TEXT(Type.CONTROL, 3),
	END_OF_TRANSMISSION(Type.CONTROL, 4),
	ENQUIRY(Type.CONTROL, 5),
	ACKNOWLEDGE(Type.CONTROL, 6),
	BEL(Type.CONTROL, 7),
	BACKSPACE(Type.CONTROL, 8),
	CHARACTER_TABULATION(Type.CONTROL, 9),
	LINE_FEED_LF(Type.CONTROL, 10),
	LINE_TABULATION(Type.CONTROL, 11),
	FORM_FEED_FF(Type.CONTROL, 12),
	CARRIAGE_RETURN_CR(Type.CONTROL, 13),
	SHIFT_OUT(Type.CONTROL, 14),
	SHIFT_IN(Type.CONTROL, 15),
	DATA_LINK_ESCAPE(Type.CONTROL, 16),
	DEVICE_CONTROL_ONE(Type.CONTROL, 17),
	DEVICE_CONTROL_TWO(Type.CONTROL, 18),
	DEVICE_CONTROL_THREE(Type.CONTROL, 19),
	DEVICE_CONTROL_FOUR(Type.CONTROL, 20),
	NEGATIVE_ACKNOWLEDGE(Type.CONTROL, 21),
	SYNCHRONOUS_IDLE(Type.CONTROL, 22),
	END_OF_TRANSMISSION_BLOCK(Type.CONTROL, 23),
	CANCEL(Type.CONTROL, 24),
	END_OF_MEDIUM(Type.CONTROL, 25),
	SUBSTITUTE(Type.CONTROL, 26),
	ESCAPE(Type.CONTROL, 27),
	INFORMATION_SEPARATOR_FOUR(Type.CONTROL, 28),
	INFORMATION_SEPARATOR_THREE(Type.CONTROL, 29),
	INFORMATION_SEPARATOR_TWO(Type.CONTROL, 30),
	INFORMATION_SEPARATOR_ONE(Type.CONTROL, 31),
	DELETE(Type.CONTROL, 127),
	
	// ---------------------------- PUNCTUATION ----------------------------
	SPACE(Type.PUNCTUATION, 32),
	EXCLAMATION_MARK(Type.PUNCTUATION, 33),
	QUOTATION_MARK(Type.PUNCTUATION, 34),
	NUMBER_SIGN(Type.PUNCTUATION, 35),
	PERCENT_SIGN(Type.PUNCTUATION, 37),
	AMPERSAND(Type.PUNCTUATION, 38),
	APOSTROPHE(Type.PUNCTUATION, 39),
	LEFT_PARENTHESIS(Type.PUNCTUATION, 40),
	RIGHT_PARENTHESIS(Type.PUNCTUATION, 41),
	ASTERISK(Type.PUNCTUATION, 42),
	COMMA(Type.PUNCTUATION, 44),
	HYPHEN_MINUS(Type.PUNCTUATION, 45),
	FULL_STOP(Type.PUNCTUATION, 46),
	SOLIDUS(Type.PUNCTUATION, 47),
	COLON(Type.PUNCTUATION, 58),
	SEMICOLON(Type.PUNCTUATION, 59),
	QUESTION_MARK(Type.PUNCTUATION, 63),
	COMMERCIAL_AT(Type.PUNCTUATION, 64),
	LEFT_SQUARE_BRACKET(Type.PUNCTUATION, 91),
	REVERSE_SOLIDUS(Type.PUNCTUATION, 92),
	RIGHT_SQUARE_BRACKET(Type.PUNCTUATION, 93),
	LOW_LINE(Type.PUNCTUATION, 95),
	LEFT_CURLY_BRACKET(Type.PUNCTUATION, 123),
	RIGHT_CURLY_BRACKET(Type.PUNCTUATION, 125),
	
	// ---------------------------- SYMBOL ----------------------------
	DOLLAR_SIGN(Type.SYMBOL, 36),
	PLUS_SIGN(Type.SYMBOL, 43),
	LESS_THAN_SIGN(Type.SYMBOL, 60),
	EQUALS_SIGN(Type.SYMBOL, 61),
	GREATER_THAN_SIGN(Type.SYMBOL, 62),
	CIRCUMFLEX_ACCENT(Type.SYMBOL, 94),
	GRAVE_ACCENT(Type.SYMBOL, 96),
	VERTICAL_LINE(Type.SYMBOL, 124),
	TILDE(Type.SYMBOL, 126),
	
	// ---------------------------- DIGIT ----------------------------
	DIGIT_ZERO(Type.DIGIT, 48),
	DIGIT_ONE(Type.DIGIT, 49),
	DIGIT_TWO(Type.DIGIT, 50),
	DIGIT_THREE(Type.DIGIT, 51),
	DIGIT_FOUR(Type.DIGIT, 52),
	DIGIT_FIVE(Type.DIGIT, 53),
	DIGIT_SIX(Type.DIGIT, 54),
	DIGIT_SEVEN(Type.DIGIT, 55),
	DIGIT_EIGHT(Type.DIGIT, 56),
	DIGIT_NINE(Type.DIGIT, 57),
	
	// ---------------------------- UPPERCASE ----------------------------
	UPPERCASE_A(Type.UPPERCASE, 65),
	UPPERCASE_B(Type.UPPERCASE, 66),
	UPPERCASE_C(Type.UPPERCASE, 67),
	UPPERCASE_D(Type.UPPERCASE, 68),
	UPPERCASE_E(Type.UPPERCASE, 69),
	UPPERCASE_F(Type.UPPERCASE, 70),
	UPPERCASE_G(Type.UPPERCASE, 71),
	UPPERCASE_H(Type.UPPERCASE, 72),
	UPPERCASE_I(Type.UPPERCASE, 73),
	UPPERCASE_J(Type.UPPERCASE, 74),
	UPPERCASE_K(Type.UPPERCASE, 75),
	UPPERCASE_L(Type.UPPERCASE, 76),
	UPPERCASE_M(Type.UPPERCASE, 77),
	UPPERCASE_N(Type.UPPERCASE, 78),
	UPPERCASE_O(Type.UPPERCASE, 79),
	UPPERCASE_P(Type.UPPERCASE, 80),
	UPPERCASE_Q(Type.UPPERCASE, 81),
	UPPERCASE_R(Type.UPPERCASE, 82),
	UPPERCASE_S(Type.UPPERCASE, 83),
	UPPERCASE_T(Type.UPPERCASE, 84),
	UPPERCASE_U(Type.UPPERCASE, 85),
	UPPERCASE_V(Type.UPPERCASE, 86),
	UPPERCASE_W(Type.UPPERCASE, 87),
	UPPERCASE_X(Type.UPPERCASE, 88),
	UPPERCASE_Y(Type.UPPERCASE, 89),
	UPPERCASE_Z(Type.UPPERCASE, 90),
	
	// ---------------------------- LOWERCASE ----------------------------
	LOWERCASE_A(Type.LOWERCASE, 97),
	LOWERCASE_B(Type.LOWERCASE, 98),
	LOWERCASE_C(Type.LOWERCASE, 99),
	LOWERCASE_D(Type.LOWERCASE, 100),
	LOWERCASE_E(Type.LOWERCASE, 101),
	LOWERCASE_F(Type.LOWERCASE, 102),
	LOWERCASE_G(Type.LOWERCASE, 103),
	LOWERCASE_H(Type.LOWERCASE, 104),
	LOWERCASE_I(Type.LOWERCASE, 105),
	LOWERCASE_J(Type.LOWERCASE, 106),
	LOWERCASE_K(Type.LOWERCASE, 107),
	LOWERCASE_L(Type.LOWERCASE, 108),
	LOWERCASE_M(Type.LOWERCASE, 109),
	LOWERCASE_N(Type.LOWERCASE, 110),
	LOWERCASE_O(Type.LOWERCASE, 111),
	LOWERCASE_P(Type.LOWERCASE, 112),
	LOWERCASE_Q(Type.LOWERCASE, 113),
	LOWERCASE_R(Type.LOWERCASE, 114),
	LOWERCASE_S(Type.LOWERCASE, 115),
	LOWERCASE_T(Type.LOWERCASE, 116),
	LOWERCASE_U(Type.LOWERCASE, 117),
	LOWERCASE_V(Type.LOWERCASE, 118),
	LOWERCASE_W(Type.LOWERCASE, 119),
	LOWERCASE_X(Type.LOWERCASE, 120),
	LOWERCASE_Y(Type.LOWERCASE, 121),
	LOWERCASE_Z(Type.LOWERCASE, 122),
	;
	
	private final Type type;
	private final Character character;
	
	public enum Type {
		CONTROL,
		PUNCTUATION,
		SYMBOL,
		DIGIT,
		UPPERCASE,
		LOWERCASE
	}
	
	Ascii128(@Nonnull Type type, int codePoint) {
		Assert.notNull(type, "char type");
		this.type = type;
		this.character = (char) codePoint;
	}
	
	@Nonnull
	public Type getType() {
		return type;
	}
	
	@Nonnull
	public Character getCharacter() {
		return character;
	}
	
	@Nonnull
	public String getString() {
		return character.toString();
	}
	
	public char getChar() {
		return character;
	}
	
	public int getCodePoint() {
		return character;
	}
	
	@Nonnull
	public static Stream<Ascii128> findAllAsStream(@Nonnull Predicate<Ascii128> predicate) {
		Assert.notNull(predicate, "predicate");
		return Arrays.stream(values()).filter(predicate);
	}
	
	@Nonnull
	public static List<Ascii128> findAll(@Nonnull Predicate<Ascii128> predicate) {
		return findAllAsStream(predicate).toList();
	}
	
	@Nonnull
	public static Optional<Ascii128> findFirst(@Nonnull Predicate<Ascii128> predicate) {
		return findAllAsStream(predicate).findFirst();
	}
	
	@Nonnull
	public static Optional<Ascii128> findLast(@Nonnull Predicate<Ascii128> predicate) {
		return findAllAsStream(predicate)
				.reduce((first, second) -> second);
	}
	
	@Nonnull
	public static Ascii128 getFirst(@Nonnull Predicate<Ascii128> predicate) {
		return findFirst(predicate).orElseThrow(() -> new IllegalArgumentException(
				Msg.format("No matching {} enum constant found", Ascii128.class.getSimpleName())));
	}
	
	@Nonnull
	public static Ascii128 getLast(@Nonnull Predicate<Ascii128> predicate) {
		return findLast(predicate).orElseThrow(() -> new IllegalArgumentException(
				Msg.format("No matching {} enum constant found", Ascii128.class.getSimpleName())));
	}
	
	@Nonnull
	public static Ascii128 valueOf(Character character) {
		return getFirst(ascii128 -> ascii128.getCharacter().equals(character));
	}
	
	@Nonnull
	public static Ascii128 valueOf(char character) {
		return getFirst(ascii128 -> ascii128.getChar() == character);
	}
	
	@Nonnull
	public static Ascii128 valueOf(int codePoint) {
		return getFirst(ascii128 -> ascii128.getCodePoint() == codePoint);
	}
	
}
