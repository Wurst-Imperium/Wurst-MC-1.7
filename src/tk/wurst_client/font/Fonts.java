package tk.wurst_client.font;

import java.awt.Font;

public class Fonts
{
	public static UnicodeFontRenderer segoe22;
	public static UnicodeFontRenderer segoe18;
	
	public static void loadFonts()
	{
		segoe22 = new UnicodeFontRenderer(new Font("Segoe UI", Font.PLAIN, 22));
		segoe18 = new UnicodeFontRenderer(new Font("Segoe UI", Font.PLAIN, 18));
	}
}
