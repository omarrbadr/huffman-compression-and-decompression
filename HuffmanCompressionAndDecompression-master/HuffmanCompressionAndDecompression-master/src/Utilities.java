import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Utilities {
	String compressedLine = "", fileString;
	StringBuilder decompressedFile ;
	HashMap<Character, String> huffmanEncodingMap = new HashMap<>();
	HashMap<String, String> huffmanDecodingMap = new HashMap<>();
	ArrayList<Character> fileCharacters = new ArrayList<>();
	ArrayList<Integer> fileCharactersFrequency = new ArrayList<>();
	Comparator<HuffmanNode> huffmanComparator = new HuffmanNodeComparator();
	PriorityQueue<HuffmanNode> huffmanQueue = new PriorityQueue<HuffmanNode>(5, huffmanComparator);
	HuffmanDecodedTree huffmanDecodedTree = new HuffmanDecodedTree();
	int fileStringLength;

	public void readBinaryFile(String filename) throws IOException {

		try (InputStream inputStream = new FileInputStream(filename);

		) {

			long fileSize = new File(filename).length();

			byte[] allBytes = new byte[(int) fileSize];
			fileString = "";
			inputStream.read(allBytes);
			String inputBinary = new String(allBytes);
			System.out.println(inputBinary);
			String test;
			for (int i = 0; i < inputBinary.length();) {
				if (i + 8 > inputBinary.length()) {
					test = inputBinary.substring(i, inputBinary.length());
					System.out.println(test);
					i = inputBinary.length();
				} else {
					test = inputBinary.substring(i, i + 8);
					i += 8;
				}
				char x = (char) Integer.parseInt(test, 2);
				System.out.println(String.valueOf(x));
				fileString += x;
				System.out.println(fileString);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	public void readEncodedFile(String fileName) {
		File outputFile = new File(fileName);
		Scanner scanner;
		try {
			scanner = new Scanner(outputFile);
			while (scanner.hasNextLine()) {
				String keyCharacter = scanner.next();
				if(keyCharacter.equals("^"))
					keyCharacter = " ";

				if (keyCharacter.equals("Ë"))
					break;
				String codeValue = scanner.next();
				scanner.nextLine();
				huffmanDecodedTree.build(keyCharacter, codeValue);
			}
			scanner.nextLine();
			while (scanner.hasNextLine())
				compressedLine += scanner.nextLine();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getCompressedLine() {
		return compressedLine;
	}

	public HashMap<Character, String> getHuffmanEncodingMap() {
		return huffmanEncodingMap;
	}

	public void readInputFile(String fileName) {
		fileString = new String();
		File inputFile = new File(fileName);
		Scanner scanner;
		try {
			scanner = new Scanner(inputFile);
			while (scanner.hasNextLine()) {
				fileString += (scanner.nextLine());
				fileString += "{";
			}

			fileString = fileString.substring(0, fileString.length() - 1);
			// System.out.println(fileString);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getFileString() {
		return fileString;
	}

	public void setCharactersAndFrequencies() {
		fileStringLength = fileString.length();
		for (int i = 0; i < fileStringLength; i++) {
			if (!fileCharacters.contains(fileString.charAt(i))) {
				fileCharacters.add(fileString.charAt(i));
				fileCharactersFrequency.add(1);
			} else {
				int index = fileCharacters.indexOf(fileString.charAt(i));
				int characterFrequency = fileCharactersFrequency.get(index);
				characterFrequency++;
				fileCharactersFrequency.set(index, characterFrequency);
			}
		}
	}

	public ArrayList<Character> getFileCharacters() {
		return fileCharacters;
	}

	public ArrayList<Integer> getFileCharactersFrequency() {
		return fileCharactersFrequency;
	}

	public void buildHuffmanQueue() {
		int fileCharactersSize = fileCharacters.size();
		for (int i = 0; i < fileCharactersSize; i++) {
			huffmanQueue.add(new HuffmanNode(fileCharactersFrequency.get(i), fileCharacters.get(i)));
		}
	}

	public void buildHuffmanTree() {
		HuffmanTree huffmanTreeBuilder = new HuffmanTree(huffmanQueue);
		huffmanEncodingMap = huffmanTreeBuilder.build();
	}

	public void writeEncodedFile() throws IOException {
		String encodedOutput = new String();
		String bitsSubString;
		char correspondingCharacter;

		// get the input file as sequence of 0s and 1s
		for (int i = 0; i < fileStringLength; i++)
			encodedOutput += huffmanEncodingMap.get(fileString.charAt(i));

		BufferedWriter outputFile = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("compressedFile.txt")));
		outputFile.write("");

		// write each character and its code in the output file
		for (int i = 0; i < huffmanEncodingMap.size(); i++) {
			char mapCharacter = fileCharacters.get(i);
			if (mapCharacter == ' ')
				outputFile.append("^" + " " + huffmanEncodingMap.get(mapCharacter));
			else
				outputFile.append(mapCharacter + " " + huffmanEncodingMap.get(mapCharacter));
			outputFile.newLine();
		}

		outputFile.append('Ë');
		outputFile.newLine();

		// convert each 8 digits to their corresponding asci character and write
		// it in the output file
		// if the corresponding asci character is \n, then write ä in the output
		// file
		int encodedOutputLength = encodedOutput.length();
		for (int i = 0; i < encodedOutputLength;) {
			if (i + 6 > encodedOutputLength) {
				bitsSubString = encodedOutput.substring(i, encodedOutputLength);
				// System.out.println(test);
				i = encodedOutputLength;
			} else {
				bitsSubString = encodedOutput.substring(i, i + 6);
				i += 6;
			}

			correspondingCharacter = (char) (Integer.parseInt(bitsSubString, 2) + 33);
			outputFile.append(correspondingCharacter);

		}

		outputFile.close();

	}

	public void writeDecodedFile() throws IOException {
		decompressedFile = huffmanDecodedTree.decodeFile(compressedLine);
		writeDecompressedFile(decompressedFile);
	}

	private void writeDecompressedFile(StringBuilder decompressedFile) throws IOException {
		BufferedWriter outputFile = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("decompressedFile.txt")));
		outputFile.write("");
		outputFile.write(decompressedFile.toString());
		outputFile.close();
	}

}
