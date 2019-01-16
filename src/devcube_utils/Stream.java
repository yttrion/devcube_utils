/*    */ package devcube_utils;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class Stream
/*    */ {
/*    */   public static byte[] readBytes(InputStream input)
/*    */   {
/*    */     try
/*    */     {
/* 16 */       byte[] data = new byte['?'];
/* 17 */       Vector<Byte> buffer = new Vector();
/* 18 */       for (int length = 0; length != -1; length = input.read(data))
/* 19 */         for (int i = 0; i < length; i++)
/* 20 */           buffer.add(Byte.valueOf(data[i]));
/* 21 */       data = new byte[buffer.size()];
/* 22 */       for (int i = 0; i < data.length; i++)
/* 23 */         data[i] = ((Byte)buffer.get(i)).byteValue();
/* 24 */       return data;
/*    */     }
/*    */     catch (IOException e) {
/* 27 */       System.out.println(e); }
/* 28 */     return null;
/*    */   }
/*    */   
/*    */   public static byte[] readBytes(File file)
/*    */   {
/*    */     try {
/* 34 */       InputStream input = new FileInputStream(file);
/* 35 */       byte[] data = readBytes(input);
/* 36 */       input.close();
/* 37 */       return data;
/*    */     }
/*    */     catch (Exception e) {
/* 40 */       System.out.println(e); }
/* 41 */     return null;
/*    */   }
/*    */   
/*    */   public static String readString(File file)
/*    */   {
/*    */     try {
/* 47 */       InputStream input = new FileInputStream(file);
/* 48 */       byte[] data = readBytes(input);
/* 49 */       input.close();
/* 50 */       return new String(data);
/*    */     }
/*    */     catch (Exception e) {
/* 53 */       System.out.println(e); }
/* 54 */     return null;
/*    */   }
/*    */   
/*    */   public static void writeBytes(byte[] data, OutputStream output)
/*    */   {
/*    */     try {
/* 60 */       output.write(data, 0, data.length);
/*    */     }
/*    */     catch (IOException e) {
/* 63 */       System.out.println(e);
/*    */     }
/*    */   }
/*    */   
/*    */   public static void writeBytes(byte[] data, File file) {
/*    */     try {
/* 69 */       OutputStream output = new java.io.FileOutputStream(file);
/* 70 */       writeBytes(data, output);
/* 71 */       output.close();
/*    */     }
/*    */     catch (java.io.FileNotFoundException e) {
/* 74 */       System.out.println(e);
/*    */     }
/*    */     catch (IOException e) {
/* 77 */       System.out.println(e);
/*    */     }
/*    */   }
/*    */   
/*    */   public static void writeString(String text, File file) {
/* 82 */     writeBytes(text.getBytes(), file);
/*    */   }
/*    */ }


/* Location:              C:\Users\DEBLY marie laure\Desktop\Travail\S5\Projet Informatique\devcube-5.6.1.jar!\devcube\model\io\Stream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */