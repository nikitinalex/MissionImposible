package it.sevenbits;

/**
 * Class for some constants.
 */
final class Constants {
    /**
     * @value String for stream corruption.
     */
    public static final String STREAM_IS_NOT_AVAILABLE =
        "Stream is not available";
    /**
     * Length of construction "@for", where @ is any acceptable symbol.
     */
    public static final int FOR_LENGTH = 4;
    /**
     * Symbol for offset.
     */
    public static final char OFFSET_SYMBOL = ' ';
    /**
     * Repeats of offset symbol.
     */
    public static final short OFFSET_SIZE = 4;
    /**
     * Close constructor.
     */
    private Constants() {
    }
}
