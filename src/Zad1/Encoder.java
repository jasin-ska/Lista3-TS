package Zad1;

import java.util.zip.CRC32;

public class Encoder {
    public static String code(String input, int frameSize) {
        StringBuilder codedOutput = new StringBuilder();
        String frame, crc, splitter = "01111110";

        System.out.println("Input size: " + input.length() +", input: " + input);
        System.out.println("\nKODOWANIE:\n");

        int i = 0;
        while (i < input.length()) {
            if (input.length() - i >= frameSize) //TODO: >= dalam, ale > tez ok
                frame = input.substring(i, i + frameSize);
            else
                frame = input.substring(i);

            System.out.print((i/frameSize + 1) + ") data: " + frame + "\n\t");
            crc = Crc.getCrc(frame);
            System.out.print("CRC: " + crc + "\n\t");


            frame += crc;
            frame = frame.replace("11111", "111110");
            frame = splitter + frame + splitter;
            System.out.println("Frame: " + frame + " ");

            codedOutput.append(frame);
            i += frameSize;
        }

        return codedOutput.toString();
    }


}
