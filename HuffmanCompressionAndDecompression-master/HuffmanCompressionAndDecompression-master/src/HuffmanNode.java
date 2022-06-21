
public class HuffmanNode {
	private int freq;
	private char character;
	private HuffmanNode left;
	private HuffmanNode right;
	private String code;

	public HuffmanNode(Integer freq, Character character) {
		this.freq = freq;
		this.character = character;
		left = null;
		right = null;
		code = new String();
	}

	public int getFreq() {
		return freq;
	}

	public char getCharacter() {
		return character;
	}

	public String getCode() {
		return code;
	}

	public HuffmanNode getLeft() {
		return left;
	}

	public void setLeft(HuffmanNode left) {
		this.left = left;
	}

	public HuffmanNode getRight() {
		return right;
	}

	public void setRight(HuffmanNode right) {
		this.right = right;
	}
	
	public void appendBit(String string){
		code += string;
	}
	
	
	
}
