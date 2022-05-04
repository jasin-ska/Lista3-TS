package Zad1;

import java.util.zip.CRC32;

public class Crc {
    static CRC32 crc32 = new CRC32();

    public static String getCrc(String frame) {
        crc32.reset();
        crc32.update(frame.getBytes());
        StringBuilder crc = new StringBuilder(Long.toBinaryString(crc32.getValue()));
        while(crc.length() < 32) {
            crc.insert(0, "0");
        }
        return crc.toString();
    }
}
