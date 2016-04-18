/*
 * BookTxt.java        1.0 Apr 17, 2016
 *
 * Copyright 2016 Manuel Martínez <ManuMtz@icloud.com> / <ManuMtz@hotmail.co.uk>
 *
 * This is free software, licensed under the GNU General Public License v3.
 * See http://www.gnu.org/licenses/gpl.html for more information.
 */

import java.io.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

public class BookTxt {

	// Filename and its directory
	private String filePath;

	// Quantity of Words in a Book
	private int countWords;

	// Word ocurrences in a Book
	private int countAWord;

	// Quantity of Chars in a Book
	private int countChars;

	// Char ocurrences in a Book
	private int countAChar;

	// Book title
	private String bookTitle;

	// Book author
	private String bookAuthor;

	// Book Lines
	private int countLinesBook;

	// Book Lines in ArrayList
	private ArrayList<String> bookLines = new ArrayList<String>(800);

	/**
	 * BookTxt Constructor
	 *
	 * @param filePath
	 */

	public BookTxt(String filePath) {
		this.filePath = filePath;

		try (BufferedReader br = new BufferedReader(new FileReader(this.filePath))) {
			String srtCurrentLine;
			while ((srtCurrentLine = br.readLine()) != null) {
				bookLines.add(srtCurrentLine);
				countWords += srtCurrentLine.trim().split("[^\\w]").length;
				countChars += srtCurrentLine.length();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Reads a Book
	 */

	public void showFile() {
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String strCurrentLine;
			while ((strCurrentLine = br.readLine()) != null) {
				System.out.print(strCurrentLine + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return Full Counted Lines
	 */

	public int countLines() {
		return bookLines.size();
	}

	/**
	 * @return Full Counted the number of Words
	 */

	public int countWords() {
		return countWords;
	}

	/**
	 * @return Full Counted the number of Chars
	 */

	public int countChars() {
		return countChars;
	}

	/**
	 * Copies a Book
	 *
	 * @param outputFilePath
	 */

	public void copy(String outputFilePath) {
		try (BufferedReader br = new BufferedReader(new FileReader(filePath));
			 PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(outputFilePath)))) {
			String strCurrentLine;
			while ((strCurrentLine = br.readLine()) != null) {
				pw.println(strCurrentLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Counts a word
	 *
	 * @param word
	 * @return
	 */

	public int countAWord(String word) {
		Iterator<String> iter = bookLines.iterator();
		while (iter.hasNext()) {
			StringTokenizer strTk = new StringTokenizer(iter.next());
			while (strTk.hasMoreTokens()) {
				if (strTk.nextToken().equalsIgnoreCase(word)) {
					countAWord++;
				}
			}
		}
		return countAWord;
	}

	/**
	 * Counts a char
	 *
	 * @param c
	 * @return String
	 */

	public int countAChar(char c) {
		for (String linea : bookLines) {
			int i = 0;
			while (i < linea.length()) {
				if (linea.charAt(i) == c) {
					countAChar++;
				}
				i++;
			}
		}
		return countAChar;
	}

	/**
	 * Gets book title
	 *
	 * @return String
	 */

	public String getTitle() {
		String CurrentLine;
		for (String s : bookLines) {
			StringTokenizer strTk = new StringTokenizer(s);
			String title = "";
			while (strTk.hasMoreTokens()) {
				title = strTk.nextToken();
				if (title.equalsIgnoreCase("title:")) {
					bookTitle = s.substring("title:".length()).trim();
					break;
				}
			}
		}
		return bookTitle;
	}

	/**
	 * Gets book author
	 *
	 * @return String
	 */

	public String getAuthor() {
		String CurrentLine;
		for (String s : bookLines) {
			StringTokenizer strTk = new StringTokenizer(s);
			String author = "";
			while (strTk.hasMoreTokens()) {
				author = strTk.nextToken();
				if (author.equalsIgnoreCase("Author:")) {
					bookAuthor = s.substring("Author:".length()).trim();
					break;
				}
			}
		}
		return bookAuthor;
	}

	/**
	 * @return Book Counted Lines
	 */

	public int countLinesBook() {
		boolean start = false;
		boolean end = false;
		for (String s : bookLines) {
			if (s.length() > 10) {
				String st = (s.substring(0, 9)+s.substring(s.length()-3, s.length())).replace(" ", "");
				if (st.equals("***START***")) {
					start = true;
				}
				String ed = (s.substring(0, 7)+s.substring(s.length()-3, s.length())).replace(" ", "");
				if (ed.equals("***END***")) {
					end = true;
				}
				if (start && !end) {
					countLinesBook++;
				}
			}
		}
		return countLinesBook;
	}
}
