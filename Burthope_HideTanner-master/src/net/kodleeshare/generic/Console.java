package net.kodleeshare.generic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import net.kodleeshare.Wrapper;

import org.powerbot.game.api.Manifest;

public class Console
{
	public static final Color TEXT_STATUS = Color.LIGHT_GRAY;
	public static final Color TEXT_INDENTMARKER = Color.CYAN;
	public static final Color TEXT_HEADER_KEY = Color.GREEN;
	public static final Color TEXT_HEADER_VALUE = Color.CYAN;
	public static final Color TEXT_WARNING = Color.YELLOW;

	private static SimpleAttributeSet indentAttSet = new SimpleAttributeSet();
	private static SimpleAttributeSet textAttSet = new SimpleAttributeSet();

	private static JFrame theConsole = null;
	private static JTextPane textArea = null;

	public Console()
	{
		if (Console.theConsole == null)
		{
			Console.theConsole = new JFrame("Fru1tstanD Console");
			Console.theConsole.setPreferredSize(new Dimension(780, 200));
			Console.theConsole.setMinimumSize(Console.theConsole.getPreferredSize());

			Console.textArea = new JTextPane();
			JScrollPane scrollPane = new JScrollPane(Console.textArea);
			Console.textArea.setBorder(new EmptyBorder(0, 10, 2, 10));
			Console.textArea.setEditable(false);
			Console.textArea.setBackground(Color.BLACK);
			Console.textArea.setCaretColor(Color.GREEN);
			Console.textArea.setForeground(Color.LIGHT_GRAY);
			Console.textArea.setFont(new Font("Verdana", Font.PLAIN, 12));
			Container content = Console.theConsole.getContentPane();
			content.setLayout(new BorderLayout());
			content.add(scrollPane);

			Console.theConsole.pack();
			Console.theConsole.setVisible(true);

			Console.textArea.setText("Welcome to the Fru1tstanD log. Here You'll find current statuses of scripts, warnings, alerts, etc.");
			hr();
			StyleConstants.setForeground(Console.indentAttSet, Console.TEXT_INDENTMARKER);
		}

		Manifest data = Wrapper.class.getAnnotation(Manifest.class);
		outputHeaders("Script Name", data.name());
		String authors = "";
		for(String author: data.authors())
		{
			authors += author + " ";
		}
		outputHeaders("Author", authors);
		outputHeaders("Version", Double.toString(data.version()));
		hr();
	}

	/**
	 * 
	 * @param key
	 *            The string used as the key
	 * @param value
	 *            The string used as the value
	 */
	private void outputHeaders(String key, String value)
	{
		try
		{
			StyleConstants.setForeground(Console.textAttSet, Console.TEXT_HEADER_KEY);
			Console.textArea.getDocument().insertString(Console.textArea.getDocument().getLength(), "\n"
					+ key
					+ ": ", Console.textAttSet);
			StyleConstants.setForeground(Console.textAttSet, Console.TEXT_HEADER_VALUE);
			Console.textArea.getDocument().insertString(Console.textArea.getDocument().getLength(), value, Console.textAttSet);
		} catch (BadLocationException e)
		{
			e.printStackTrace();
		}
	}

	private void hr()
	{
		try
		{
			Console.textArea.getDocument().insertString(Console.textArea.getDocument().getLength(), "\n- - -", null);
		} catch (BadLocationException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param s
	 *            The string to output to the console.
	 */
	public void addLog(String s)
	{
		addLog(s, Console.TEXT_STATUS);
	}

	/**
	 * 
	 * @param s
	 *            The string to output to the console.
	 * @param c
	 *            The color of the string
	 */
	public void addLog(String s, Color c)
	{
		StyleConstants.setForeground(Console.textAttSet, c);
		try
		{
			Console.textArea.getDocument().insertString(Console.textArea.getDocument().getLength(), "\n--> ", Console.indentAttSet);
			Console.textArea.getDocument().insertString(Console.textArea.getDocument().getLength(), s, Console.textAttSet);
		} catch (BadLocationException e)
		{
			e.printStackTrace();
		}
		scrollToBottom();
	}

	private void scrollToBottom()
	{
		Console.textArea.setCaretPosition(Console.textArea.getDocument().getLength());
	}

	public void killTheConsole()
	{
		Console.theConsole.dispose();
	}
}
