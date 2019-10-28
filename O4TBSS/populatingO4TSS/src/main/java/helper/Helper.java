package helper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Helper {
	
	//Main method
	public static void main(String[] args) {
		
	}
	
	public static String writeInstances2Onto(String filePath, String instances) {
		String data = "";
		FileReader fr;
		BufferedReader br;
		try {
			fr = new FileReader(filePath);
			br = new BufferedReader(fr);
			for (String line; (line = br.readLine()) != null;) {
				if (line.trim().matches("</Ontology>")) {
//					System.out.println("---------------Writing the instances-------------------");
					data+=instances;
//					System.out.println("---------------End of writing the instances------------");
				}
				data += line.toString()+"\n";
//				data += line.toString();
//				System.out.println(data);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return data;
	}

	
	public static void writeOntoToFile(String filePATH, String instances) {
		FileWriter fw = null;
		PrintWriter pw = null;
		String ontology="";
		//Get the knowledge from the ontology
		ontology=writeInstances2Onto(filePATH, instances);
		try {
			fw = new FileWriter(filePATH, false);
//			fw = new FileWriter(filePATH, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		pw = new PrintWriter(fw);
		pw.write(ontology);
		pw.close();
	}


}
