/*     */ package devcube_utils;
/*     */ 
/*     */ //import devcube.model.ProjectFileException;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.security.spec.AlgorithmParameterSpec;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipException;
/*     */ import java.util.zip.ZipInputStream;
/*     */ import java.util.zip.ZipOutputStream;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.CipherInputStream;
/*     */ import javax.crypto.CipherOutputStream;
/*     */ import javax.crypto.SecretKey;
/*     */ import javax.crypto.spec.IvParameterSpec;
/*     */ import javax.crypto.spec.SecretKeySpec;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Zip
/*     */ {
/*     */   private static Cipher cipher;
/*     */   private static AlgorithmParameterSpec paramSpec;
/*     */   
/*     */   static
/*     */   {
/*     */     try
/*     */     {
/*  38 */       byte[] iv = { Byte.MIN_VALUE, 20, 53, -102, 7, 82, 99, 91 };
/*  39 */       paramSpec = new IvParameterSpec(iv);
/*  40 */       cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
/*     */     }
/*     */     catch (Exception e) {
/*  43 */       //ExceptionLogger.log(e);
				System.out.println(e);
/*     */     }
/*     */   }
/*     */   
/*     */   private static SecretKey getSecretKey(int magicNumber) {
/*     */     try {
/*  49 */       String data = String.valueOf(magicNumber);
/*  50 */       byte[] bytes = (data + data).getBytes("UTF8");
/*  51 */       byte[] key = new byte[8];
/*  52 */       for (int i = 0; i < bytes.length; i++) {
/*  53 */         int tmp46_45 = (i % 8); byte[] tmp46_40 = key;tmp46_40[tmp46_45] = ((byte)(tmp46_40[tmp46_45] + bytes[i])); }
/*  54 */       return new SecretKeySpec(key, "DES");
/*     */     }
/*     */     catch (UnsupportedEncodingException e) {
/*  57 */       //ExceptionLogger.log(e); 
				}
/*  58 */     return null;
/*     */   }
/*     */   
/*     */   private static ZipInputStream createZipInputStream(File zipFile, int magicNumber)
/*     */   {
/*     */     try {
/*  64 */       InputStream inputStream = new FileInputStream(zipFile);
/*  65 */       if (magicNumber > 999) {
/*  66 */         cipher.init(2, getSecretKey(magicNumber), paramSpec);
/*  67 */         inputStream = new CipherInputStream(inputStream, cipher);
/*     */       }
/*  69 */       return new ZipInputStream(inputStream);
/*     */     }
/*     */     catch (Exception e) {
/*  72 */      // ExceptionLogger.log(e); 
/*  73 */     return null;
				}
/*     */   }
/*     */   
/*     */   public static Map<String, byte[]> readEntries(File zipFile, int magicNumber) throws Exception
/*     */   {
/*  78 */     HashMap<String, byte[]> entries = new HashMap();
/*  79 */     ZipInputStream zipInput = null;
/*     */     try {
/*  81 */       zipInput = createZipInputStream(zipFile, magicNumber);
/*  82 */       ZipEntry zipEntry = zipInput.getNextEntry();
/*  83 */       while (zipEntry != null) {
/*  84 */         if (!zipEntry.isDirectory()) {
/*  85 */           byte[] data = Stream.readBytes(zipInput);
/*  86 */           entries.put(zipEntry.getName(), data);
/*     */         }
/*  88 */         zipEntry = zipInput.getNextEntry();
/*     */       }
/*     */     }
/*     */     catch (FileNotFoundException e) {
/*  92 */       throw new Exception("La notice " + zipFile.getAbsolutePath() + " n'existe pas.");
/*     */     }
/*     */     catch (ZipException e) {
/*  95 */       throw new Exception("La notice " + zipFile.getAbsolutePath() + " a un format non valide.");
/*     */     }
/*     */     catch (IOException e) {
/*  98 */       //ExceptionLogger.log(e);
				System.out.println(e);
/*     */     }
/* 100 */     return entries;
/*     */   }
/*     */   
/*     */   public static ZipOutputStream createZipOutputStream(File zipFile, int magicNumber) {
/*     */     try {
/* 105 */       OutputStream outputStream = new FileOutputStream(zipFile);
/* 106 */       if (magicNumber > 999) {
/* 107 */         cipher.init(1, getSecretKey(magicNumber), paramSpec);
/* 108 */         outputStream = new CipherOutputStream(outputStream, cipher);
/*     */       }
/* 110 */       return new ZipOutputStream(outputStream);
/*     */     }
/*     */     catch (Exception e) {
/* 113 */       //ExceptionLogger.log(e); 
				System.out.println(e);
				}
/* 114 */     return null;
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public static void writeEntries(File zipFile, int magicNumber, Map<String, byte[]> entries)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aconst_null
/*     */     //   1: astore_3
/*     */     //   2: aload_0
/*     */     //   3: iload_1
/*     */     //   4: invokestatic 212	devcube/model/io/Zip:createZipOutputStream	(Ljava/io/File;I)Ljava/util/zip/ZipOutputStream;
/*     */     //   7: astore_3
/*     */     //   8: aload_2
/*     */     //   9: invokeinterface 214 1 0
/*     */     //   14: invokeinterface 220 1 0
/*     */     //   19: astore 5
/*     */     //   21: goto +58 -> 79
/*     */     //   24: aload 5
/*     */     //   26: invokeinterface 226 1 0
/*     */     //   31: checkcast 232	java/util/Map$Entry
/*     */     //   34: astore 4
/*     */     //   36: new 145	java/util/zip/ZipEntry
/*     */     //   39: dup
/*     */     //   40: aload 4
/*     */     //   42: invokeinterface 234 1 0
/*     */     //   47: checkcast 52	java/lang/String
/*     */     //   50: invokespecial 237	java/util/zip/ZipEntry:<init>	(Ljava/lang/String;)V
/*     */     //   53: astore 6
/*     */     //   55: aload_3
/*     */     //   56: aload 6
/*     */     //   58: invokevirtual 238	java/util/zip/ZipOutputStream:putNextEntry	(Ljava/util/zip/ZipEntry;)V
/*     */     //   61: aload 4
/*     */     //   63: invokeinterface 242 1 0
/*     */     //   68: checkcast 96	[B
/*     */     //   71: aload_3
/*     */     //   72: invokestatic 245	devcube/model/io/Stream:writeBytes	([BLjava/io/OutputStream;)V
/*     */     //   75: aload_3
/*     */     //   76: invokevirtual 249	java/util/zip/ZipOutputStream:closeEntry	()V
/*     */     //   79: aload 5
/*     */     //   81: invokeinterface 252 1 0
/*     */     //   86: ifne -62 -> 24
/*     */     //   89: goto +110 -> 199
/*     */     //   92: astore 4
/*     */     //   94: aload 4
/*     */     //   96: invokestatic 30	devcube/model/io/ExceptionLogger:log	(Ljava/lang/Exception;)V
/*     */     //   99: aload_3
/*     */     //   100: ifnull +117 -> 217
/*     */     //   103: aload_3
/*     */     //   104: invokevirtual 255	java/util/zip/ZipOutputStream:close	()V
/*     */     //   107: goto +110 -> 217
/*     */     //   110: astore 8
/*     */     //   112: aload 8
/*     */     //   114: invokestatic 30	devcube/model/io/ExceptionLogger:log	(Ljava/lang/Exception;)V
/*     */     //   117: goto +100 -> 217
/*     */     //   120: astore 4
/*     */     //   122: aload 4
/*     */     //   124: invokestatic 30	devcube/model/io/ExceptionLogger:log	(Ljava/lang/Exception;)V
/*     */     //   127: aload_3
/*     */     //   128: ifnull +89 -> 217
/*     */     //   131: aload_3
/*     */     //   132: invokevirtual 255	java/util/zip/ZipOutputStream:close	()V
/*     */     //   135: goto +82 -> 217
/*     */     //   138: astore 8
/*     */     //   140: aload 8
/*     */     //   142: invokestatic 30	devcube/model/io/ExceptionLogger:log	(Ljava/lang/Exception;)V
/*     */     //   145: goto +72 -> 217
/*     */     //   148: astore 4
/*     */     //   150: aload 4
/*     */     //   152: invokestatic 30	devcube/model/io/ExceptionLogger:log	(Ljava/lang/Exception;)V
/*     */     //   155: aload_3
/*     */     //   156: ifnull +61 -> 217
/*     */     //   159: aload_3
/*     */     //   160: invokevirtual 255	java/util/zip/ZipOutputStream:close	()V
/*     */     //   163: goto +54 -> 217
/*     */     //   166: astore 8
/*     */     //   168: aload 8
/*     */     //   170: invokestatic 30	devcube/model/io/ExceptionLogger:log	(Ljava/lang/Exception;)V
/*     */     //   173: goto +44 -> 217
/*     */     //   176: astore 7
/*     */     //   178: aload_3
/*     */     //   179: ifnull +17 -> 196
/*     */     //   182: aload_3
/*     */     //   183: invokevirtual 255	java/util/zip/ZipOutputStream:close	()V
/*     */     //   186: goto +10 -> 196
/*     */     //   189: astore 8
/*     */     //   191: aload 8
/*     */     //   193: invokestatic 30	devcube/model/io/ExceptionLogger:log	(Ljava/lang/Exception;)V
/*     */     //   196: aload 7
/*     */     //   198: athrow
/*     */     //   199: aload_3
/*     */     //   200: ifnull +17 -> 217
/*     */     //   203: aload_3
/*     */     //   204: invokevirtual 255	java/util/zip/ZipOutputStream:close	()V
/*     */     //   207: goto +10 -> 217
/*     */     //   210: astore 8
/*     */     //   212: aload 8
/*     */     //   214: invokestatic 30	devcube/model/io/ExceptionLogger:log	(Ljava/lang/Exception;)V
/*     */     //   217: return
/*     */     // Line number table:
/*     */     //   Java source line #119	-> byte code offset #0
/*     */     //   Java source line #121	-> byte code offset #2
/*     */     //   Java source line #122	-> byte code offset #8
/*     */     //   Java source line #123	-> byte code offset #36
/*     */     //   Java source line #124	-> byte code offset #55
/*     */     //   Java source line #125	-> byte code offset #61
/*     */     //   Java source line #126	-> byte code offset #75
/*     */     //   Java source line #122	-> byte code offset #79
/*     */     //   Java source line #128	-> byte code offset #89
/*     */     //   Java source line #129	-> byte code offset #92
/*     */     //   Java source line #130	-> byte code offset #94
/*     */     //   Java source line #139	-> byte code offset #99
/*     */     //   Java source line #141	-> byte code offset #103
/*     */     //   Java source line #142	-> byte code offset #107
/*     */     //   Java source line #143	-> byte code offset #110
/*     */     //   Java source line #144	-> byte code offset #112
/*     */     //   Java source line #132	-> byte code offset #120
/*     */     //   Java source line #133	-> byte code offset #122
/*     */     //   Java source line #139	-> byte code offset #127
/*     */     //   Java source line #141	-> byte code offset #131
/*     */     //   Java source line #142	-> byte code offset #135
/*     */     //   Java source line #143	-> byte code offset #138
/*     */     //   Java source line #144	-> byte code offset #140
/*     */     //   Java source line #135	-> byte code offset #148
/*     */     //   Java source line #136	-> byte code offset #150
/*     */     //   Java source line #139	-> byte code offset #155
/*     */     //   Java source line #141	-> byte code offset #159
/*     */     //   Java source line #142	-> byte code offset #163
/*     */     //   Java source line #143	-> byte code offset #166
/*     */     //   Java source line #144	-> byte code offset #168
/*     */     //   Java source line #138	-> byte code offset #176
/*     */     //   Java source line #139	-> byte code offset #178
/*     */     //   Java source line #141	-> byte code offset #182
/*     */     //   Java source line #142	-> byte code offset #186
/*     */     //   Java source line #143	-> byte code offset #189
/*     */     //   Java source line #144	-> byte code offset #191
/*     */     //   Java source line #146	-> byte code offset #196
/*     */     //   Java source line #139	-> byte code offset #199
/*     */     //   Java source line #141	-> byte code offset #203
/*     */     //   Java source line #142	-> byte code offset #207
/*     */     //   Java source line #143	-> byte code offset #210
/*     */     //   Java source line #144	-> byte code offset #212
/*     */     //   Java source line #147	-> byte code offset #217
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	218	0	zipFile	File
/*     */     //   0	218	1	magicNumber	int
/*     */     //   0	218	2	entries	Map<String, byte[]>
/*     */     //   1	203	3	zipOutput	ZipOutputStream
/*     */     //   34	28	4	entry	java.util.Map.Entry<String, byte[]>
/*     */     //   92	3	4	e	FileNotFoundException
/*     */     //   120	3	4	e	ZipException
/*     */     //   148	3	4	e	IOException
/*     */     //   19	61	5	localIterator	java.util.Iterator
/*     */     //   53	4	6	zipEntry	ZipEntry
/*     */     //   176	21	7	localObject	Object
/*     */     //   110	3	8	e	IOException
/*     */     //   138	3	8	e	IOException
/*     */     //   166	3	8	e	IOException
/*     */     //   189	3	8	e	IOException
/*     */     //   210	3	8	e	IOException
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   2	89	92	java/io/FileNotFoundException
/*     */     //   103	107	110	java/io/IOException
/*     */     //   2	89	120	java/util/zip/ZipException
/*     */     //   131	135	138	java/io/IOException
/*     */     //   2	89	148	java/io/IOException
/*     */     //   159	163	166	java/io/IOException
/*     */     //   2	99	176	finally
/*     */     //   120	127	176	finally
/*     */     //   148	155	176	finally
/*     */     //   182	186	189	java/io/IOException
/*     */     //   203	207	210	java/io/IOException
/*     */   }
/*     */   
/*     */   public static void zip(File zipFile, int magicNumber, File unzipDirectory, Set<String> entryNames)
/*     */     throws Exception
/*     */   {
/* 151 */     HashMap<String, byte[]> entries = new HashMap();
/* 152 */     for (String entryName : entryNames) {
/* 153 */       File entryFile = new File(unzipDirectory, entryName);
/* 154 */       byte[] entryData = null;
/* 155 */       entryData = Stream.readBytes(entryFile);
/* 156 */       entries.put(entryName, entryData);
/*     */     }
/* 158 */     writeEntries(zipFile, magicNumber, entries);
/*     */   }
/*     */ }


/* Location:              C:\Users\DEBLY marie laure\Desktop\Travail\S5\Projet Informatique\devcube-5.6.1.jar!\devcube\model\io\Zip.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */