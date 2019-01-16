package devcube_utils;
import org.eclipse.swt.widgets.*;

import devcube_utils.Stream;
import devcube_utils.Zip;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.zip.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;


public class DevCubeUtils {

	public static void main(String[] args) throws Exception {
		// Version graphique pour plus tard
		/*
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("DevCube Utils");
		shell.open();
		
		while(!shell.isDisposed())
			if (!display.readAndDispatch())
				display.sleep();
		display.dispose();
		*/
		
		//Version textuelle dans un premier temps
		Scanner scanner = new Scanner(System.in);
		int magic = 2018;		//Cryptage fort en effet
		
		//Introduction
		System.out.println("DevCubeUtils 0.0.1\n------------------\n\n");
		System.out.println("Saisissez le nom du fichier .dc : ");
		
		String ogPath = new File(DevCubeUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParent();
		String path = URLDecoder.decode(ogPath, "UTF-8");
		
		String filename = scanner.next();
		scanner.close();
		String filepath = path+"\\"+filename+".dc";
		File dc_file = new File(filepath);
		
		//Décryptage et récupération du zip
		Map<String, byte[]> zipEntries = Zip.readEntries(dc_file, magic);
		new File(path+"\\"+filename+"_extracted").mkdirs();
		
		//Boucle de lecture et de récupération des fichiers
		Set<Entry<String, byte[]>> setZip = zipEntries.entrySet();
		Iterator<Entry<String, byte[]>> it = setZip.iterator();
		while(it.hasNext()) {
			Entry<String, byte[]> e = it.next();
			String current_file = e.getKey();
			System.out.println("Extraction de "+current_file+"...");
			byte[] data = (byte[])zipEntries.get(current_file);
			try (FileOutputStream fos = new FileOutputStream(path+"\\"+filename+"_extracted\\"+current_file)) {
				   fos.write(data);
			}
		}	
		
	}
}

