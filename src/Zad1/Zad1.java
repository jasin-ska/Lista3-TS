package Zad1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Zad1 {
    public static void main(String[] args) throws IOException {
        String filePath = "./src/Zad1/";
        String fileName = "input";
        String fileExt = ".txt";
        int frameSize = 30;

        byte[] byteArray; String input; File outputFile; FileOutputStream outputStream;

        // coding
        byteArray = Files.readAllBytes(Paths.get(filePath + fileName + fileExt));
        input = new String(byteArray);
        String coded = Encoder.code(input, frameSize);

        System.out.println("Coded: " + coded);

        // printing to file
        outputFile = new File(filePath + fileName + "-coded" + fileExt);
        outputFile.createNewFile();
        outputStream = new FileOutputStream(outputFile);
        outputStream.write(coded.getBytes());

        // decoding
        byteArray = Files.readAllBytes(Paths.get(filePath + fileName + "-coded" + fileExt));
        input = new String(byteArray);
        String decoded = Decoder.decode(input);

        System.out.println("Deoded: " + decoded);

        // printing to file
        outputFile = new File(filePath + fileName + "-decoded" + fileExt);
        outputFile.createNewFile();
        outputStream = new FileOutputStream(outputFile);
        outputStream.write(decoded.getBytes());

        System.out.println(isDecodedEqual(filePath, fileName, fileExt)? "\nDECODED = ORIGINAL" : "\nDECODED != ORIGINAL");

    }

    private static boolean isDecodedEqual(String path, String name, String ext) throws IOException {
        String filePath = path + name + ext;
        String decodedFilePath = path + name + "-decoded" + ext;

        byte[] fileBefore = Files.readAllBytes(Paths.get(filePath));
        byte[] fileDecoded = Files.readAllBytes(Paths.get(decodedFilePath));
        return Arrays.equals(fileBefore, fileDecoded);
    }
}
