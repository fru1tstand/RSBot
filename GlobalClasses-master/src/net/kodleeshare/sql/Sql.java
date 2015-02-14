package net.kodleeshare.sql;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import net.kodleeshare.generic.Global;

import org.powerbot.game.api.util.Random;

public class Sql
{
	private static final String	KEY			= "version 1.0 released";
	public static final char[]	ALL_CHARS	= { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',

											'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',

											'0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

	public enum Url
	{
		SAFECRACK("roguesden_safecracker.php"),
		URNMAKE("urnmake.php"),
		DWARFMINE("dwarfmine.php"),
		TEST("istesting.php");

		private static final String	BASE	= "http://kodleeshare.net/rs/update/";

		private String				url;

		Url(String url)
		{
			this.url = url;
		}

		public String getUrl()
		{
			return Sql.Url.BASE
					+ this.url;
		}
	}

	public enum Action
	{
		UPDATE,
		ADD;
	}

	private Url		url;
	private String	key;

	public Sql(Url url)
	{
		this.url = url;
		this.key = String.valueOf(Sql.ALL_CHARS[Random.nextInt(0, Sql.ALL_CHARS.length)]);
		for (int i = 0; i < 44; i++)
			this.key += Sql.ALL_CHARS[Random.nextInt(0, Sql.ALL_CHARS.length)];
	}

	public void push(Action a, NameValuePair... nvp)
	{
		try
		{
			StringBuffer requestParams = new StringBuffer();

			requestParams.append("key=").append(URLEncoder.encode(Sql.KEY, "UTF-8"));
			requestParams.append("&sqlkey=").append(URLEncoder.encode(this.key, "UTF-8"));
			requestParams.append("&username=").append(URLEncoder.encode(Global.USERNAME, "UTF-8"));
			requestParams.append("&action=").append(URLEncoder.encode(((a == Action.UPDATE) ? "update" : "add"), "UTF-8"));

			for (NameValuePair thePair : nvp)
			{
				requestParams.append("&");
				requestParams.append(URLEncoder.encode(thePair.getName(), "UTF-8"));
				requestParams.append("=").append(URLEncoder.encode(thePair.getValue(), "UTF-8"));

			}

			URL url = new URL(this.url.getUrl());

			URLConnection urlConn = url.openConnection();
			urlConn.setDoOutput(true); // post

			OutputStreamWriter writer = new OutputStreamWriter(urlConn.getOutputStream());
			writer.write(requestParams.toString());
			writer.flush();

			BufferedReader rd = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

			System.out.println("Sending usage statistics...");
			String inLine;
			while ((inLine = rd.readLine()) != null)
				System.out.println(inLine);

			writer.close();
			rd.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	public String getKey()
	{
		return this.key;
	}
}
