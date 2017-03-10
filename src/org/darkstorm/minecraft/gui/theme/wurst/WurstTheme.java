package org.darkstorm.minecraft.gui.theme.wurst;

import java.awt.Font;

import net.minecraft.client.gui.FontRenderer;

import org.darkstorm.minecraft.gui.theme.AbstractTheme;

import tk.wurst_client.font.UnicodeFontRenderer;

public class WurstTheme extends AbstractTheme {
	private final FontRenderer fontRenderer;

	public WurstTheme() {
		fontRenderer = new UnicodeFontRenderer(new Font("Segoe UI", Font.PLAIN, 15));

		installUI(new WurstFrameUI(this));
		installUI(new WurstPanelUI(this));
		installUI(new WurstLabelUI(this));
		installUI(new WurstButtonUI(this));
		installUI(new WurstCheckButtonUI(this));
		installUI(new WurstComboBoxUI(this));
		installUI(new WurstSliderUI(this));
		installUI(new WurstProgressBarUI(this));
	}

	public FontRenderer getFontRenderer() {
		return fontRenderer;
	}
}
