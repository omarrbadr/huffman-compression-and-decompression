import java.util.HashMap;
import java.util.PriorityQueue;

public class HuffmanTree {
	PriorityQueue<HuffmanNode> huffmanQueue;
	HashMap<Character, String> huffmanEncodingMap = new HashMap<Character, String>();

	public HuffmanTree(PriorityQueue<HuffmanNode> huffmanQueue) {
		this.huffmanQueue = huffmanQueue;
	}

	public HashMap build() {
		while (huffmanQueue.size() != 1) {
			HuffmanNode leftChildNode = huffmanQueue.remove();
			HuffmanNode rightChildNode = huffmanQueue.remove();
			HuffmanNode parentNode = new HuffmanNode(leftChildNode.getFreq() + rightChildNode.getFreq(), 'Ë');
			parentNode.setLeft(leftChildNode);
			parentNode.setRight(rightChildNode);
			huffmanQueue.add(parentNode);
		}
		printHuffmanTree(huffmanQueue.remove());
		return huffmanEncodingMap;
		
	}

	private void printHuffmanTree(HuffmanNode root) {
		//System.out.println(root.getFreq());

		if (root.getLeft() != null){
			root.getLeft().appendBit(root.getCode());
			root.getLeft().appendBit("0");
			printHuffmanTree(root.getLeft());
		}
		if (root.getRight() != null){
			root.getRight().appendBit(root.getCode());
			root.getRight().appendBit("1");
			printHuffmanTree(root.getRight());
		}
//		System.out.println(root.getCharacter() + " " + root.getCode());
		if(root.getCharacter()!='Ë'){
		huffmanEncodingMap.put(root.getCharacter(), root.getCode());
	}}

}
