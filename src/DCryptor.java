
import java.util.*;
import java.util.Map.Entry;
import java.io.*;
import java.net.URLDecoder;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class DCryptor {
	public static void main(String args[]) throws Exception {
		
		Scanner scanner = new Scanner(System.in);
		int magic = 2019;
		String version = "0.0.2";
		System.out.println("gDCryptor " + version);
		System.out.println("----------------------");
		System.out.println("1 - Decrypt");
		System.out.println("2 - Encrypt");
		System.out.print("Choice> ");
		int choice = scanner.nextInt();
		scanner.close();
		if (choice == 1) {
			File dc_file;
			String filename;
			String path;
			System.out.println("[*] - Encrypt mode.");
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Devcube archive", "dc");
			chooser.setFileFilter(filter);
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			int returnVal = chooser.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				dc_file = new File(chooser.getSelectedFile().getAbsolutePath());
				filename = chooser.getSelectedFile().getName();
				// File to str
				// String str = FileUtils.readFileToString(file, "UTF-8");
				path = chooser.getCurrentDirectory().toString();

				// Décryptage et récupération du zip
				Map<String, byte[]> zipEntries = Zip.readEntries(dc_file, magic);

				System.out.println("[*] - Zipped.");

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
			System.out.println("[!] - TODO");
		}
		
	}

	public void decrypt(String args[]) {

	}

	public void encrypt() {

	}
}