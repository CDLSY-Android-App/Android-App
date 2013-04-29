package cdlsy.bu.octorock;

import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

/*
 * Parses a song map in XML of the following format:
 * 
 * <song>
 *     <time>
 *         <t>time where notes start</t>
 *         <note1> 1 if is outputted here, 0 if not</note1>
 *                            .
 *                            .
 *                            .
 *         <note6> 1 or 0 </note6>
 *     </time>
 *     .
 *     .
 *     .
 * </song>
 */

public class SongParser {

	private XmlPullParser parser;

	// "is" is what song the user would like to open
	public SongParser(InputStream is) throws XmlPullParserException, IOException {
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(true);
		parser = factory.newPullParser();
		parser.setInput(is, null);
	}

	// Returns a number corresponding to the next time notes should be released.
	// Returns -1 if no more times are in the file.
	public double getNextTime() throws XmlPullParserException, IOException {

		String name = parser.getName();
		int eventType = parser.getEventType();

		// First locate the next instance of <t>
		while (name == null || !name.equals("t") || eventType == XmlPullParser.END_TAG) {

			if (eventType == XmlPullParser.END_DOCUMENT)
				return -1;

			parser.next();
			name = parser.getName();
			eventType = parser.getEventType();
		}

		parser.next(); // Move to text following <t>
		return Double.parseDouble(parser.getText());
	}

	// Returns a 6 element array containing 1 or 0 if the note should be played.
	// Values are -1 if no more notes are in the file.
	public int[] getNextNotes() throws XmlPullParserException, IOException {
		int[] notes = new int[6];

		String name = parser.getName();
		int eventType = parser.getEventType();
		// First locate the next instance of <note-i>
		for (int i = 1; i <= 6; i++) {
			while (name == null || !name.equals("note" + i) || eventType == XmlPullParser.END_TAG) {
				if (eventType == XmlPullParser.END_DOCUMENT) {
					int[] arr = { -1, -1, -1, -1, -1, -1 };
					return arr;
				}
				parser.next();
				name = parser.getName();
				eventType = parser.getEventType();
			}
			parser.next();
			notes[i - 1] = Integer.parseInt(parser.getText());
		}

		return notes;
	}

}
