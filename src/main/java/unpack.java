import java.math.BigInteger;

public class unpack {
    private int pointer;
    private int packetType;
    private int playerArrayPosition;
    private byte[] inputByteArray;

    public BigInteger[] unpack(byte[] arrayIn, BigInteger[] lastArray) {
        if (lastArray == null) {
            lastArray = new BigInteger[301];
        }
        inputByteArray = arrayIn;

        pointer = 5;
        packetType = getDataPacketType();

        pointer = 22;
        playerArrayPosition = getPlayerArrayPosition();

        pointer = 24;

        BigInteger[] response;
        int offset;

        switch (packetType) {
            case 0 -> {
                int[] toLoop = {4,4,4,4,4,4,2,2,2,2,2,2,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4};
                response = unpackLapMotionCarTelemetryCarStatus(toLoop);
                offset = 0;
            }
            case 1 -> {
                response = unpackSession();
                offset = 48;
            }
            case 2 -> {
                int[] toLoop = {4,4,2,2,4,1,2,2,2,2,1,2,1,2,1,4,4,4,1,1,1,1,1,1,1,1,1};
                response = unpackLapMotionCarTelemetryCarStatus(toLoop);
                offset = 209;
            }
            case 6 -> {
                int[] toLoop = {2,4,4,4,1,1,2,1,1,2,2,2,2,1,1,1,1,1,1,1,1,2,4,4,4,4,1,1,1,1};
                response = unpackLapMotionCarTelemetryCarStatus(toLoop);
                offset = 236;
            }
            case 7 -> {
                int[] toLoop = {1,1,1,1,1,4,4,4,2,2,1,1,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,4,1,4,4,4};
                response = unpackLapMotionCarTelemetryCarStatus(toLoop);
                offset = 266;
            }
            default -> {
                return lastArray;
            }
        }

        BigInteger[] toReturn = insertIntoArray(offset, response, lastArray);
        //for (int i=0; i< toReturn.length; i++) {
        //    System.out.print(toReturn[i] + ",");
        //}

        return toReturn;
    }

    private BigInteger[] insertIntoArray(int offset, BigInteger[] response, BigInteger[] insertInto) {
        for (int i=0; i<response.length-1; i++) {
            insertInto[offset + i] = response[i];
        }

        return insertInto;
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

    private BigInteger[] unpackSession() {
        BigInteger[] outArray = new BigInteger[161];
        int[] toLoop1 = {1,1,1,1,2,1,1,1,2,2,1,1,1,1,1,1}; //last item is numMarshallZones
        int[] toLoopMarshall = {4,1};
        int[] toLoop2 = {1,1,1}; //last item is numWeatherForecastSamples
        int[] toLoopForecast = {1,1,1,1,1};
        int pointer = 0;

        for (int i=0; i<toLoop1.length; i++) { // loop up until the marshall zones arrays
            outArray[i] = readNext(inputByteArray, toLoop1[i]);
            pointer += 1;
        }

        int numMarshall = Integer.parseInt(outArray[pointer - 1].toString(2), 2);
        for (int i=0; i<21; i++) { // loop through the max number of times
            for (int j=0; j<toLoopMarshall.length; j++) {
                if (i < numMarshall) { // if an array exists (i.e. still in range) then add it to the out array
                    outArray[pointer] = readNext(inputByteArray, toLoopMarshall[j]);
                    pointer += 1;
                } else { // else just increment the counter to keep pointer in correct place
                    pointer += 1;
                }
            }
        }

        for (int i=0; i<toLoop2.length; i++) { // loop up until the forecast sample arrays
            outArray[pointer] = readNext(inputByteArray, toLoop2[i]);
            pointer += 1;
        }

        int numForecast = Integer.parseInt(outArray[pointer - 1].toString(2), 2);
        for (int i=0; i<20; i++) { // loop rthrough the max number of times
            for (int j=0; j<toLoopForecast.length; j++) {
                if (i < numForecast) { // if still in range, thus data exists, add it to the out array
                    outArray[pointer] = readNext(inputByteArray, toLoopForecast[j]);
                    pointer += 1;
                } // no else as this is end of packet if not in range, so pointer is not needed to be incremented anymore
            }
        }

        return outArray;
    }

    private BigInteger[] unpackLapMotionCarTelemetryCarStatus(int[] toLoop) {
        BigInteger[] outArray = new BigInteger[toLoop.length];

        if (playerArrayPosition != 0) { //only increment to the correct spot for the pointer if player array position is not 0 (as pointer will already be in correct spot).
            pointer += (arrayTotal(toLoop)*(playerArrayPosition-1));
        }

        for (int i=0; i<toLoop.length; i++) {
            outArray[i] = readNext(inputByteArray, toLoop[i]); //add each byte in turn into the output array.
        }
        return outArray;
    }

    private int arrayTotal(int[] array) {
        int total = 0;

        for (int i=0; i<array.length;i++) {
            total += array[i];
        }

        return total;
    }
}
