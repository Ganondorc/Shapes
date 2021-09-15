/*
 * Name: Michael Frake
 * Project: CMSC 335 Project 2
 * Date: Sep 14, 2021
 * Description: used to restrict jtext elements to a double between min and max
 */

package shapes.utility;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;


public class DoubleFilter extends DocumentFilter {
	private double min, max;
	
	public DoubleFilter(double min, double max) {
		this.min = min;
		this.max = max;
	}
	
	@Override
	public void insertString(FilterBypass filterBypass, int offset, String string, AttributeSet attrSet)
			throws BadLocationException {

		Document doc = filterBypass.getDocument();
		StringBuilder sb = new StringBuilder();
		sb.append(doc.getText(0, doc.getLength()));
		sb.insert(offset, string);

		if (test(sb.toString())) {
			super.insertString(filterBypass, offset, string, attrSet);
		} else {
			// warn the user and don't allow the insert
		}
	}

	// doubles are composed of either integers or periods
	private boolean test(String text) {
		try {
			double d = Double.parseDouble(text);
			if (text.length() < 7 && d < max && d > min)
			return true;
		} catch (NumberFormatException e) {
			if (text.equals("")) return true;
			return false;
		}
		return false;
	}

	@Override
	public void replace(FilterBypass filterBypass, int offset, int length, String text, AttributeSet attrSet)
			throws BadLocationException {

		Document doc = filterBypass.getDocument();
		StringBuilder sb = new StringBuilder();
		sb.append(doc.getText(0, doc.getLength()));
		sb.replace(offset, offset + length, text);

		if (test(sb.toString())) {
			super.replace(filterBypass, offset, length, text, attrSet);
		} else {
			// warn the user and don't allow the insert
		}

	}

	@Override
	public void remove(FilterBypass filterBypass, int offset, int length) throws BadLocationException {
		Document doc = filterBypass.getDocument();
		StringBuilder sb = new StringBuilder();
		sb.append(doc.getText(0, doc.getLength()));
		sb.delete(offset, offset + length);

		if (test(sb.toString())) {
			super.remove(filterBypass, offset, length);
		} else {
			// warn the user and don't allow the insert
		}

	}
}
