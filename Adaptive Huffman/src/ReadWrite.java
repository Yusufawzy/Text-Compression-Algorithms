import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
public class ReadWrite {

    // Buffer of bits to write to output.
    private int bitBuffer = 0;
    private int counter = 0;
    private OutputStream out;
    private InputStream input;
    public ReadWrite(OutputStream o) {
        if(o == null) {
            System.out.println("Output stream is invalid.");
        }
        out = o;
    }

    private int nextBits;
    private int numBitsRemaining;
    private boolean isEndOfStream;
    public ReadWrite(InputStream in) {
        if (in == null)
            throw new NullPointerException("Argument is null");
        input = in;
        numBitsRemaining = 0;
        isEndOfStream = false;
    }

//IMPORTANT==>Some of the Following methods are pulled from the Internet
    public void writeBit(boolean bit) {
        // Add bit to buffer.
        bitBuffer <<= 1;
        if (bit) bitBuffer |= 1;

        // Keep track of how many bits in buffer.
        counter++;

        // If buffer full, flush.
        if (counter == 8) flush();
    }
    /**
     * Write the value x to output stream.
     *
     * If buffer is empty, then just write x straight
     * to the output. Otherwise, write the contents of
     * buffer to output as well as the first n bits of x
     * then fill buffer with remaining bits of x.
     *
     * @param x - byte to write to output.
     */
    public void writeByte(int x) {
        if( x <= 0 && x > 256) {
            throw new IllegalArgumentException("Value not in range.");
        }

        if (counter == 0) {
            // If buffer empty just write x to out.
            try {
                out.write(x);
            }
            catch (IOException e) {
                System.err.println("Write Error");
                e.printStackTrace();
            }
            return;
        }

        for (int i = 0; i < 8; i++) {
            boolean bit = ((x >>> (8 - i - 1)) & 1) == 1;
            writeBit(bit);
        }
    }
    /**
     * Write the buffer to the output stream.
     *
     * If buffer is not full (8 bits) then fill it in \
     * with trailing 0s and write to output.
     *
     * Then clear buffer and reset.
     */
    public void flush() {
        if (counter == 0) {
            return;
        }
        if (counter > 0) {
            // Fill empty space in buffer with 0s.
            bitBuffer <<= (8 - counter);
        }
        try {
            out.write(bitBuffer);
        }
        catch (IOException e) {
            System.err.println("Write Error");
            e.printStackTrace();
        }
        // Reset buffer.
        counter = 0;
        bitBuffer = 0;
    }
    // Reads a bit from the stream. Returns 0 or 1 if a bit is available, or -1 if the end of stream is reached. The end of stream always occurs on a byte boundary.
    public int read() throws IOException {
        if (isEndOfStream)
            return -1;
        if (numBitsRemaining == 0) {
            nextBits = input.read();
            if (nextBits == -1) {
                isEndOfStream = true;
                return -1;
            }
            numBitsRemaining = 8;
        }
        numBitsRemaining--;
        return (nextBits >>> numBitsRemaining) & 1;
    }
    public void close() throws IOException {
       if (out!=null)out.close();
       else input.close();
    }

}
