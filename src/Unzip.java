
//Import all needed packages
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
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


        //Cryptage  DES with magic
        //ByteBuffer bf = ByteBuffer.allocate(8);
        //bf.putInt(magic);
        //byte[] result = bf.array();
        //System.out.println("[*] - Magic array created");
        //SecretKeySpec sks = new SecretKeySpec(result, "DES");
        //Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        //cipher.init(Cipher.ENCRYPT_MODE, sks);


        byte[] buffer = new byte[8];
        String source = new File(SOURCE_FOLDER).getName();
        System.out.println("[*] - Source folder: "+source);
        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        System.out.println("[*] - Correct magic used.");

        try {
            fos = new FileOutputStream(zipFile);
            //CipherOutputStream cos = new CipherOutputStream(fos, cipher);
            System.out.println("[*] - Output to Zip : " + zipFile);
            FileInputStream in = null;
            zos = new ZipOutputStream(fos);
            //zos = new ZipOutputStream(cos);

            for (String file: this.fileList) {
                System.out.println("[*] - File Added : " + file);
                ZipEntry ze = new ZipEntry(file);
                zos.putNextEntry(ze);
                try {
                    in = new FileInputStream(SOURCE_FOLDER + File.separator + file);
                    int len;
                    while ((len = in.read(buffer)) != -1) {
                        zos.write(buffer, 0, len);
                    }
                } finally {
                    in.close();
                }
            }
            //zos.flush();
            //zos.close();
            //zos.closeEntry();
            System.out.println("[*] - Folder successfully compressed into zip file.");

        } catch (IOException ex) {
            ex.printStackTrace();
        } 
        finally {
            zos.close();
            System.out.print("");
        }

        //rename to.dc
        //File zipped = new File(this.OUTPUT_ZIP_FILE);
        //File dced   = new File(this.OUTPUT_ZIP_FILE.substring(0, this.OUTPUT_ZIP_FILE.length() -3) + "dc");
        //if (zipped.renameTo(dced)){
        //    System.out.println("[*] - DC compressed.");
        //}
        //else {
        //    System.out.println("[!] - An error occurred");
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
        return file.substring(SOURCE_FOLDER.length() +1 , file.length());
    }
}