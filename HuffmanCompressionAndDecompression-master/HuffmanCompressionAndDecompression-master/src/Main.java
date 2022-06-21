import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Utilities utilities = new Utilities();
		String inputFile = new String();
		int option;
		
				
		System.out.println("****** Huffman Project ******");
		System.out.println("Press 1 for Compression OR press 2 for Decompression");
		option = scanner.nextInt();
		scanner.nextLine();
		if(option == 1){
			System.out.print("File name : ");
			inputFile = scanner.nextLine();
			utilities.readInputFile(inputFile);
			utilities.setCharactersAndFrequencies();
			utilities.buildHuffmanQueue();
			utilities.buildHuffmanTree();
			try {
				utilities.writeEncodedFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("File is compressed succesfully");
		}
		else if(option == 2){
			System.out.println("File name : ");
			inputFile = scanner.nextLine();
			utilities.readEncodedFile(inputFile);
			try {
				utilities.writeDecodedFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("\nFile is decompressed succesfully");
		}
		else{
			System.out.println("Please pick 1 or 2");
		}
			
		
	}
}
