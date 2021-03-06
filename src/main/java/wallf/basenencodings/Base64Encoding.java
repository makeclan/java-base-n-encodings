package wallf.basenencodings;

/**
 * Represents a Base64 encoding. <br />
 * Default constructor will create a standard Base64 encoding(RFC 4648).
 */
public class Base64Encoding extends BaseEncoding {

    /**
     * Standard Alphabet.
     */
    public static final String STANDARD_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    /**
     * Standard Padding.
     */
    public static final char STANDARD_PADDING = '=';
    /**
     * Default Encoding Name.
     */
    public static final String DEFAULT_NAME = "Standard Base64 Encoding";

    private final char[] alphabet;
    private final char padding;
    private final String encodingName;
    private final Base64 b;

    /**
     * Initializes a new instance that is a standard Base64 encoding(<a href="http://tools.ietf.org/rfc/rfc4648.txt">RFC 4648</a>).
     */
    public Base64Encoding() {
        this(STANDARD_ALPHABET.toCharArray(), STANDARD_PADDING, DEFAULT_NAME, false);
    }

    /**
     * Initializes a new instance of the Base64Encoding class. Parameters specify the alphabet and the padding character of encoding.
     *
     * @param alphabet Alphabet for current encoding.
     * @param padding  Padding character for current encoding.
     * @throws IllegalArgumentException Arguments error, see the source code.
     */
    public Base64Encoding(char[] alphabet, char padding) {
        this(alphabet, padding, "Customized Base64 Encoding", true);
    }

    /**
     * Initializes a new instance of the Base64Encoding class. Parameters specify the alphabet and the padding character and the name of encoding.
     *
     * @param alphabet     Alphabet for current encoding.
     * @param padding      Padding character for current encoding.
     * @param encodingName Name for current encoding.
     * @throws IllegalArgumentException Arguments error, see the source code.
     */
    public Base64Encoding(char[] alphabet, char padding, String encodingName) {
        this(alphabet, padding, encodingName, true);
    }

    Base64Encoding(char[] alphabet, char padding, String encodingName, boolean verify) {
        if (verify) {
            if (alphabet == null)
                throw new IllegalArgumentException("alphabet is null");
            if (encodingName == null)
                throw new IllegalArgumentException("encodingName is null");
            if (alphabet.length != 64)
                throw new IllegalArgumentException("size of alphabet is not 64");
            if (ArrayFunctions.isArrayDuplicate(alphabet))
                throw new IllegalArgumentException("alphabet contains duplicated items");
        }
        this.alphabet = alphabet.clone();
        this.padding = padding;
        this.encodingName = encodingName;
        this.b = new Base64(this.alphabet, this.padding);
    }


    /**
     * Gets the human-readable description of the current encoding.
     */
    @Override
    public String getEncodingName() {
        return encodingName;
    }

    /**
     * Gets the being used alphabet of the current encoding.
     */
    @Override
    public char[] getAlphabet() {
        return alphabet.clone();
    }

    /**
     * Return values is always true for the Base64 Encoding.
     */
    @Override
    public boolean isPaddingRequired() {
        return true;
    }

    /**
     * Gets the being used padding character of the current encoding.
     */
    @Override
    public char getPaddingCharacter() {
        return padding;
    }

    /**
     * @see int BaseEncoding.getEncodeCountWithoutArgumentsValidation(int length)
     */
    @Override
    protected int getEncodeCountWithoutArgumentsValidation(int length) {
        return b.encodeSize(length);
    }

    /**
     * @see char[] BaseEncoding.encodeWithoutArgumentsValidation(byte[] bytes, int offset, int length)
     */
    @Override
    protected char[] encodeWithoutArgumentsValidation(byte[] bytes, int offset, int length) {
        char[] r = new char[b.encodeSize(length)];
        b.encode(bytes, offset, length, r, 0, r.length);
        return r;
    }

    /**
     * @see int BaseEncoding.encodeWithoutArgumentsValidation(byte[] bytesIn, int offsetIn, int lengthIn, char[] charsOut, int offsetOut)
     */
    @Override
    protected int encodeWithoutArgumentsValidation(byte[] bytesIn, int offsetIn, int lengthIn, char[] charsOut, int offsetOut) {
        return b.encode(bytesIn, offsetIn, lengthIn, charsOut, offsetOut);
    }

    /**
     * @see int BaseEncoding.getDecodeCountWithoutArgumentsValidation(char[] chars, int offset, int length)
     */
    @Override
    protected int getDecodeCountWithoutArgumentsValidation(char[] chars, int offset, int length) {
        return b.decodeSize(chars, offset, length, new TypeWrapper<Integer>());
    }

    /**
     * @see byte[] BaseEncoding.decodeWithoutArgumentsValidation(char[] chars, int offset, int length)
     */
    @Override
    protected byte[] decodeWithoutArgumentsValidation(char[] chars, int offset, int length) {
        TypeWrapper<Integer> paddingNumWrapper = new TypeWrapper<Integer>();
        byte[] r = new byte[b.decodeSize(chars, offset, length, paddingNumWrapper)];
        b.decode(chars, offset, length, r, 0, r.length, paddingNumWrapper.getValue());
        return r;
    }

    /**
     * @see int BaseEncoding.decodeWithoutArgumentsValidation(char[] charsIn, int offsetIn, int lengthIn, byte[] bytesOut, int offsetOut)
     */
    @Override
    protected int decodeWithoutArgumentsValidation(char[] charsIn, int offsetIn, int lengthIn, byte[] bytesOut, int offsetOut) {
        return b.decode(charsIn, offsetIn, lengthIn, bytesOut, offsetOut);
    }

    /**
     * @see boolean BaseEncoding.isValidBaseSequenceWithoutArgumentsValidation(char[] chars, int offset, int length)
     */
    @Override
    protected boolean isValidBaseSequenceWithoutArgumentsValidation(char[] chars, int offset, int length) {
        return b.isValidBaseSequence(chars, offset, length);
    }
}