import java.math.BigInteger;

public class unpack {
    private int pointer = 0;
    public BigInteger[] unpack(byte[] inputByteArray) {
        //unpack into array of bigintegers, then change to true values right at render stage.
    }

    private BigInteger readNext(byte[] arrayToRead, int numberOfBitsToRead) {
        // read, reverse then return from little to big endian
        byte[] smallArray = new byte[numberOfBitsToRead];
        for (int i=numberOfBitsToRead; i>0; i--) {
            smallArray[numberOfBitsToRead - i] = arrayToRead[pointer+i];
        }

        pointer += numberOfBitsToRead;

        return new BigInteger(smallArray);
    }
}
