import java.math.BigInteger;

public class unpack {
    private int pointer;
    private int packetType;
    private int playerArrayPosition;
    private byte[] inputByteArray;
    private final int[][] bytesReference = {
            {4,4,4,4,4,4,2,2,2,2,2,2,4,4,4,4,4,4},//Motion Packet (this is repeated for a total of 22 times, plus an extra (4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4)
            {1,1,1,1,2,1,1,1,2,2,1,1,1,1,1,1,//Session Data Packet
            //then marshal zones
            1,1,
            //then weather forecast samples
            },
            {4,4,2,2,4,1,2,2,2,2,1,2,1,2,1,4,4,4,1,1,1,1,1,1,1,1,1},//Lap Data Packet - this 22 times, that's it
            {},//Event Packet - don't bother, as nothing useful is gained. say how you could add elements that could've used this in evaluation (i.e. a congrats message if they won / being able to show exactly when DRS is enabled, etc)
            {},//Participants Packet - don't bother, like with event packet anaylse how you could use this and why you didn't (i.e. could display live leaderboard with names,etc)
            {},//Car Setup Packet - don't bother, do same as with participants and event packet. (i.e. could be used to save your setups externally so they can be organised and compared easier).
            {},//Car Telemetry Packet
            {},//Car Status Packet
            {},//Final Classification packet - don't bother
            {}//Lobby info packet - don't bother
    };
    public BigInteger[] unpack(byte[] arrayIn) {
        inputByteArray = arrayIn;

        pointer = 5;
        packetType = getDataPacketType();

        pointer = 22;
        playerArrayPosition = getPlayerArrayPosition();
        System.out.println(playerArrayPosition);

        pointer = 24;

        switch (packetType) {
            case 0 -> {
                return unpackMotion();
            }
            case 1 -> {
                return unpackSession();
            }
            case 2 -> {
                return unpackLap();
            }
            case 6 -> {
                return unpackCarTelemetry();
            }
            case 7 -> {
                return unpackCarStatus();
            }
            default -> {
                return null;
            }
        }
    }

    private BigInteger readNext(byte[] arrayToRead, int numberOfBytesToRead) {
        // read, reverse then return from little to big endian
        byte[] smallArray = new byte[numberOfBytesToRead];
        for (int i=numberOfBytesToRead; i>0; i--) {
            smallArray[numberOfBytesToRead - i] = arrayToRead[pointer+i-1]; //loop through the next number of bits to be read backwards and assign it to smallArray.
        }

        pointer += numberOfBytesToRead; //set the pointer to be after the block just read.

        return new BigInteger(smallArray);
    }

    private int getDataPacketType() {
        BigInteger typeBinaryNumber = readNext(inputByteArray, 1);

        return Integer.parseInt(typeBinaryNumber.toString(2),2);
    }

    private int getPlayerArrayPosition() {
        BigInteger playerArrayPositionBinaryNumber = readNext(inputByteArray, 1);

        return Integer.parseInt(playerArrayPositionBinaryNumber.toString(2), 2);
    }
    private BigInteger[] unpackMotion() {

        return null;
    }

    private BigInteger[] unpackSession() {

        return null;
    }

    private BigInteger[] unpackLap() {
        int[] toLoop = {4,4,2,2,4,1,2,2,2,2,1,2,1,2,1,4,4,4,1,1,1,1,1,1,1,1,1};
        BigInteger[] outArray = new BigInteger[27];

        pointer += (53*(playerArrayPosition-1));

        for (int i=0; i<toLoop.length; i++) {
            outArray[i] = readNext(inputByteArray, toLoop[i]);
        }
        return outArray;
    }

    private BigInteger[] unpackCarTelemetry() {

        return null;
    }

    private BigInteger[] unpackCarStatus() {

        return null;
    }
}
