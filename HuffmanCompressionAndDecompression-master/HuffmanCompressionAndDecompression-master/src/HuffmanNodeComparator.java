import java.util.Comparator;

public class HuffmanNodeComparator implements Comparator<HuffmanNode> {

	@Override
	public int compare(HuffmanNode node1, HuffmanNode node2) {
		if (node1.getFreq() < node2.getFreq())
			return -1;
		if (node1.getFreq() > node2.getFreq())
			return 1;
		return 0;
	}

}
