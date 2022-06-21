import java.util.ArrayList;
import java.util.HashMap;

public class HuffmanDecodedTree {
	HuffmanNode root = new HuffmanNode(1, 'Ë');

	public void build(String character, String code) {
		HuffmanNode nodePointer = root;
		for (int i = 0; i < code.length() - 1; i++) {
			if (code.charAt(i) == '0') {
				if (nodePointer.getLeft() == null) {
					HuffmanNode left = new HuffmanNode(1, 'Ë');
					nodePointer.setLeft(left);
				}
				nodePointer = nodePointer.getLeft();
			} else {
				if (nodePointer.getRight() == null) {
					HuffmanNode right = new HuffmanNode(1, 'Ë');
					nodePointer.setRight(right);
				}
				nodePointer = nodePointer.getRight();
			}
		}

		if (code.charAt(code.length() - 1) == '1') {
			HuffmanNode right = new HuffmanNode(1, character.charAt(0));
			nodePointer.setRight(right);
		} else {
			HuffmanNode left = new HuffmanNode(1, character.charAt(0));
			nodePointer.setLeft(left);
		}

	}

	public StringBuilder decodeFile(String compressedLine) {
		String binaryCompressedString = convertStringToBinary(compressedLine);
		// String binaryCompressedString = compressedLine;
		int binaryCompressedStringLength = binaryCompressedString.length();
		StringBuilder decompressedFile = new StringBuilder();
		HuffmanNode nodePointer = root;
		for (int i = 0; i < binaryCompressedStringLength; i++) {
			
			if (binaryCompressedString.charAt(i) == '0') {
				nodePointer = nodePointer.getLeft();
				if (nodePointer.getLeft() == null && nodePointer.getRight() == null) {
					char nodeCharacter = nodePointer.getCharacter();
					if (nodeCharacter == '{') {
						decompressedFile.append(System.getProperty("line.separator"));
					} else
						decompressedFile.append(nodeCharacter);
					nodePointer = root;
				}
			} else if (binaryCompressedString.charAt(i) == '1') {
				nodePointer = nodePointer.getRight();
				if (nodePointer.getLeft() == null && nodePointer.getRight() == null) {
					char nodeCharacter = nodePointer.getCharacter();
					if (nodeCharacter == '{') {
						decompressedFile.append(System.getProperty("line.separator"));
					} else
						decompressedFile.append(nodeCharacter);
					nodePointer = root;
				}
			}
		}
		return decompressedFile;

	}

	private String convertStringToBinary(String compressedLine) {
		String binaryCompressedString = "";
		char compressedCharacter;
		int decimalOfcompressedCharacter = 0;
		int compressedLineLength = compressedLine.length();
		for (int i = 0; i < compressedLineLength - 1; i++) {
			compressedCharacter = compressedLine.charAt(i);
			decimalOfcompressedCharacter = (int) compressedCharacter - 33;
			binaryCompressedString += String.format("%06d",
					Integer.parseInt(Integer.toBinaryString(decimalOfcompressedCharacter)));

		}

		decimalOfcompressedCharacter = (int) compressedLine.charAt(compressedLineLength - 1) - 33;
		binaryCompressedString += Integer.toBinaryString(decimalOfcompressedCharacter);

		return binaryCompressedString;

	}

}
