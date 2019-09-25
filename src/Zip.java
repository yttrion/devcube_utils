
//import devcube.model.ProjectFileException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Zip {
    private static Cipher cipher;
    private static AlgorithmParameterSpec paramSpec;

    static {
        try {
            byte[] iv = { Byte.MIN_VALUE, 20, 53, -102, 7, 82, 99, 91 };
            paramSpec = new IvParameterSpec(iv);
            cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        } catch (Exception e) {
            // ExceptionLogger.log(e);
            System.out.println(e);
        }
    }

    private static SecretKey getSecretKey(int magicNumber) {
        try {
            String data = String.valueOf(magicNumber);
            byte[] bytes = (data + data).getBytes("UTF8");
            byte[] key = new byte[8];
            for (int i = 0; i < bytes.length; i++) {
                int tmp46_45 = (i % 8);
                byte[] tmp46_40 = key;
                tmp46_40[tmp46_45] = ((byte) (tmp46_40[tmp46_45] + bytes[i]));
            }
            return new SecretKeySpec(key, "DES");
        } catch (UnsupportedEncodingException e) {
            // ExceptionLogger.log(e);
        }
        return null;
    }

    private static ZipInputStream createZipInputStream(File zipFile, int magicNumber) {
        try {
            InputStream inputStream = new FileInputStream(zipFile);
            if (magicNumber > 999) {
                cipher.init(2, getSecretKey(magicNumber), paramSpec);
                inputStream = new CipherInputStream(inputStream, cipher);
            }
            return new ZipInputStream(inputStream);
        } catch (Exception e) {
            // ExceptionLogger.log(e);
            return null;
        }
    }

    public static Map<String, byte[]> readEntries(File zipFile, int magicNumber) throws Exception {
        HashMap<String, byte[]> entries = new HashMap<String, byte[]>();
        ZipInputStream zipInput = null;
        
        try {
            zipInput = createZipInputStream(zipFile, magicNumber);
            System.out.println("Works 'till here");
            ZipEntry zipEntry = zipInput.getNextEntry();
            while (zipEntry != null) {
                if (!zipEntry.isDirectory()) {
                    byte[] data = Stream.readBytes(zipInput);
                    entries.put(zipEntry.getName(), data);
                }
                zipEntry = zipInput.getNextEntry();
            }
        } catch (FileNotFoundException e) {
            throw new Exception("La notice " + zipFile.getAbsolutePath() + " n'existe pas.");
        } catch (ZipException e) {
            throw new Exception("La notice " + zipFile.getAbsolutePath() + " a un format non valide.");
        } catch (IOException e) {
            // ExceptionLogger.log(e);
            
            System.out.println(e);
            System.out.println("IOException");
        }
        return entries;
    }

    public static ZipOutputStream createZipOutputStream(File zipFile, int magicNumber) {
        try {
            OutputStream outputStream = new FileOutputStream(zipFile);
            if (magicNumber > 999) {
                cipher.init(1, getSecretKey(magicNumber), paramSpec);
                outputStream = new CipherOutputStream(outputStream, cipher);
            }
            return new ZipOutputStream(outputStream);
        } catch (Exception e) {
            // ExceptionLogger.log(e);
            System.out.println(e);
        }
        return null;
    }

    public static void writeEntries(File zipFile, int magicNumber, Map<String, byte[]> entries) {
    }

    public static void zip(File zipFile, int magicNumber, File unzipDirectory, Set<String> entryNames)
            throws Exception {
        HashMap<String, byte[]> entries = new HashMap<String, byte[]>();
        for (String entryName : entryNames) {
            File entryFile = new File(unzipDirectory, entryName);
            byte[] entryData = null;
            entryData = Stream.readBytes(entryFile);
            entries.put(entryName, entryData);
        }
        writeEntries(zipFile, magicNumber, entries);
    }
}