package springcloud.outh2.project.common.utils;

import java.io.*;

public class StreamUtils {
    public StreamUtils() {
    }

    public void streamSaveAsFile(InputStream is, File outfile) {
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(outfile);
            byte[] buffer = new byte[1024];

            int len;
            while((len = is.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
        } catch (Exception var13) {
            var13.printStackTrace();
            throw new RuntimeException(var13);
        } finally {
            try {
                is.close();
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception var12) {
                var12.printStackTrace();
                throw new RuntimeException(var12);
            }

        }

    }

    public static String streamToString(InputStream in) throws IOException {
        StringBuilder out = new StringBuilder();
        byte[] b = new byte[4096];

        int n;
        while((n = in.read(b)) != -1) {
            out.append(new String(b, 0, n));
        }

        return out.toString();
    }

    public static byte[] stream2Byte(InputStream is) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int len = 0;
        byte[] b = new byte[1024];

//        int len;
        while((len = is.read(b, 0, b.length)) != -1) {
            byteArrayOutputStream.write(b, 0, len);
        }

        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] inputStream2Byte(InputStream inStream) throws Exception {
        int count;
        for(count = 0; count == 0; count = inStream.available()) {
            ;
        }

        byte[] b = new byte[count];
        inStream.read(b);
        return b;
    }

    public static InputStream byte2InputStream(byte[] b) throws Exception {
        InputStream is = new ByteArrayInputStream(b);
        return is;
    }

    public static byte[] shortToByte(short number) {
        int temp = number;
        byte[] b = new byte[2];

        for(int i = 0; i < b.length; ++i) {
            b[i] = (new Integer(temp & 255)).byteValue();
            temp >>= 8;
        }

        return b;
    }

    public static short byteToShort(byte[] b) {
        short s = 0;
        short s0 = (short)(b[0] & 255);
        short s1 = (short)(b[1] & 255);
        s1 = (short)(s1 << 8);
        s = (short)(s0 | s1);
        return s;
    }

    public static byte[] intToByte(int i) {
        byte[] bt = new byte[]{(byte)(255 & i), (byte)(('\uff00' & i) >> 8), (byte)((16711680 & i) >> 16), (byte)((-16777216 & i) >> 24)};
        return bt;
    }

    public static int bytesToInt(byte[] bytes) {
        int num = bytes[0] & 255;
        num |= bytes[1] << 8 & '\uff00';
        num |= bytes[2] << 16 & 16711680;
        num |= bytes[3] << 24 & -16777216;
        return num;
    }

    public static byte[] longToByte(long number) {
        long temp = number;
        byte[] b = new byte[8];

        for(int i = 0; i < b.length; ++i) {
            b[i] = (new Long(temp & 255L)).byteValue();
            temp >>= 8;
        }

        return b;
    }

    public static long byteToLong(byte[] b) {
        long s = 0L;
        long s0 = (long)(b[0] & 255);
        long s1 = (long)(b[1] & 255);
        long s2 = (long)(b[2] & 255);
        long s3 = (long)(b[3] & 255);
        long s4 = (long)(b[4] & 255);
        long s5 = (long)(b[5] & 255);
        long s6 = (long)(b[6] & 255);
        long s7 = (long)(b[7] & 255);
        s1 <<= 8;
        s2 <<= 16;
        s3 <<= 24;
        s4 <<= 32;
        s5 <<= 40;
        s6 <<= 48;
        s7 <<= 56;
        s = s0 | s1 | s2 | s3 | s4 | s5 | s6 | s7;
        return s;
    }
}
