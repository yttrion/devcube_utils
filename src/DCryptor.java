
import java.util.*;
import java.util.Map.Entry;
import java.io.*;
//import java.net.URLDecoder;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class DCryptor {
	public static void main(String args[]) throws Exception {
		
		Scanner scanner = new Scanner(System.in);
		int magic = 2019;
		String version = "0.0.3";
		System.out.println("gDCryptor " + version);
		System.out.println("----------------------");
		System.out.println("1 - Decrypt");
		System.out.println("2 - Encrypt");
		System.out.print("> ");
		File dc_file; 
		String filename; //.dc filename
		String path; //Absolute path to .dc
		int choice = scanner.nextInt();
		if (choice == 1) {
			
			System.out.println("[*] - Decrypt mode.");
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Devcube archive", "dc");
			chooser.setFileFilter(filter);
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			int returnVal = chooser.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				dc_file = new File(chooser.getSelectedFile().getAbsolutePath());
				filename = chooser.getSelectedFile().getName();
				System.out.println(dc_file);
				// File to str
				// String str = FileUtils.readFileToString(file, "UTF-8");
				path = chooser.getCurrentDirectory().toString();

				// Décryptage et récupération du zip
				Map<String, byte[]> zipEntries = Zip.readEntries(dc_file, magic);

				System.out.println("[*] - Unzipped.");
				System.out.println("[*] - Extracting.");

				new File(path + "\\" + filename + "_extracted").mkdirs();
				System.out.println("[*] - _extracted folder created.");

				// Boucle de lecture et de récupération des fichiers
				Set<Entry<String, byte[]>> setZip = zipEntries.entrySet();
				Iterator<Entry<String, byte[]>> it = setZip.iterator();
				while (it.hasNext()) {
					Entry<String, byte[]> e = it.next();
					String current_file = e.getKey();
					System.out.println("[*] - Extracting " + current_file + "...");
					byte[] data = (byte[]) zipEntries.get(current_file);
					try (FileOutputStream fos = new FileOutputStream(
							path + "\\" + filename + "_extracted\\" + current_file)) {
						fos.write(data);
					}
				}
				System.out.println("[!] - Completed. Happy hacking!");
			}
			else {
				System.out.println("[!] - Operation aborted");
			}
		}
		else if (choice ==2){
			System.out.println("[*] - Encrypt mode.");
			System.out.println("[*] - Is there a 'package.xml' file in folder?");
			System.out.println("1 - Yes");
			System.out.println("2 - No");
			System.out.print("> ");
			int ans = scanner.nextInt();
			if (ans==1){
				System.out.println("[*] - Encrypter");
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setAcceptAllFileFilterUsed(false);
				chooser.setDialogTitle("Choose directory of decompressed project.");
				int returnVal = chooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) { 
					//System.out.println(chooser.getSelectedFile());
					//Returns selected folder
					
					filename = chooser.getSelectedFile().getAbsolutePath();
					System.out.println(filename);
					path = filename + "_compressed.zip";
					System.out.println(path);
					Unzip zipp = new Unzip(path, filename, magic);
					zipp.DoIt();
				}
				  else {
					System.out.println("[!] - No directory selected ");
					}
				   }
			}
			else {
				System.out.println("[*] - XML Editor");
				System.out.println("[*] - NOT YET IMPLEMENTED");
			}

		scanner.close();	
	}

}