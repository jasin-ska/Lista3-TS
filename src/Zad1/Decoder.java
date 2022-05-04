package Zad1;

import java.util.zip.CRC32;

public class Decoder {
    public static String decode(String input) {
        String splitter = "01111110";
        StringBuilder decodedOutput = new StringBuilder();

        input = input.replace(splitter + splitter, ",");
        input = input.replace(splitter, "");
        String[] frames = input.split(",");



        System.out.println("\nDEKODOWANIE:\n");
        for (int i = 0; i < frames.length; i++) {
            frames[i] = frames[i].replace("111110", "11111");

            System.out.println("Ramka nr " + (i+1));
            String crcRead = frames[i].substring(frames[i].length() - 32);
            String frame = frames[i].substring(0, frames[i].length() - 32);
            String crcCalculated = Crc.getCrc(frame);

            System.out.println("crc read: " + crcRead + ", crc calculated: " + crcCalculated);

            if (crcRead.equals(crcCalculated)) {
                System.out.println("Decoding - success: " + frame);
                decodedOutput.append(frame);
            } else {
                System.out.println("Decoding - FAIL.");
            }

        }
        return decodedOutput.toString();
    }
}
