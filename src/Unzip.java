
//Import all needed packages
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class Unzip {

    private List<String> fileList;
    private String OUTPUT_ZIP_FILE;// = "Folder.zip";
    private String SOURCE_FOLDER; // = "C:\\"; // SourceFolder path
    private int magic;

    public Unzip(String output, String source, int magic) {
        fileList = new ArrayList<String>();
        this.OUTPUT_ZIP_FILE = output;
        this.SOURCE_FOLDER = source;
        this.magic = magic;
    }

    public void DoIt() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IOException {
        Unzip appZip = new Unzip(this.OUTPUT_ZIP_FILE, this.SOURCE_FOLDER, this.magic);
        appZip.generateFileList(new File(SOURCE_FOLDER));
        appZip.zipIt(OUTPUT_ZIP_FILE);
    }

    public void zipIt(String zipFile) throws InvalidKeyException,
    IOException, NoSuchAlgorithmException, NoSuchPaddingException{
        
        ByteBuffer bf = ByteBuffer.allocate(8);
        bf.putInt(magic);
        byte[] result = bf.array();

        System.out.println("[*] - Magic array created");
        // Length is 16 byte
        SecretKeySpec sks = new SecretKeySpec(result, "DES");
        // Create cipher
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, sks);

        byte[] buffer = new byte[8];
        //String source = new File(SOURCE_FOLDER).getName();
        FileOutputStream fos = null;
        //BufferedOutputStream zos = null;
        System.out.println("[*] - Correct magic used.");
        try {
            fos = new FileOutputStream(zipFile);
            
            CipherOutputStream cos = new CipherOutputStream(fos, cipher);
            System.out.println("[*] - Output to Zip : " + zipFile);
            FileInputStream in = null;
            BufferedOutputStream zos = new BufferedOutputStream(cos); //new ZipOutputStream(cos);

            for (String file: this.fileList) {
                System.out.println("[*] - File Added : " + file);
                //ZipEntry ze = new ZipEntry(source + File.separator + file);
                //zos.putNextEntry(ze);
                try {
                    in = new FileInputStream(SOURCE_FOLDER + File.separator + file);
                    int len;
                    while ((len = in .read(buffer)) != -1) {
                        zos.write(buffer, 0, len);
                    }
                } finally {
                    in.close();
                }
            }
            zos.flush();
            zos.close();
            //zos.closeEntry();
            System.out.println("[*] - Folder successfully compressed.");

        } catch (IOException ex) {
            ex.printStackTrace();
        } 
        //finally {
        //    try {
        //        //zos.close();
        //        System.out.print("");
        //    } catch (IOException e) {
        //        e.printStackTrace();
        //    }
        //}
    }

    public void generateFileList(File node) {
        // add file only
        if (node.isFile()) {
            fileList.add(generateZipEntry(node.toString()));
        }

        if (node.isDirectory()) {
            String[] subNote = node.list();
            for (String filename: subNote) {
                generateFileList(new File(node, filename));
            }
        }
    }

    private String generateZipEntry(String file) {
        return file.substring(SOURCE_FOLDER.length() + 1, file.length());
    }
}