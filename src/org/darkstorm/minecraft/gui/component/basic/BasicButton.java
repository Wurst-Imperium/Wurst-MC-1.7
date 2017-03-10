package org.darkstorm.minecraft.gui.component.basic;

import org.darkstorm.minecraft.gui.component.AbstractComponent;
import org.darkstorm.minecraft.gui.component.Button;
import org.darkstorm.minecraft.gui.component.ButtonGroup;
import org.darkstorm.minecraft.gui.listener.ButtonListener;
import org.darkstorm.minecraft.gui.listener.ComponentListener;

public class BasicButton extends AbstractComponent implements Button {
	protected String text = "";
	protected ButtonGroup group;
	private String description;

	public BasicButton() {
	}

	public BasicButton(String text, String description) {
		this.text = text;
		this.description = description;
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public void setText(String text) {
		this.text = text;
	}
	
	public String getDescription()
	{
		return description;
	}

	public void setDescription(String desccription)
	{
		this.description = desccription;
	}

	@Override
	public void press() {
		for(ComponentListener listener : getListeners())
			((ButtonListener) listener).onButtonPress(this);
	}

	@Override
	public void addButtonListener(ButtonListener listener) {
		addListener(listener);
	}

	@Override
	public void removeButtonListener(ButtonListener listener) {
		removeListener(listener);
	}

	@Override
	public ButtonGroup getGroup() {
		return group;
	}

	@Override
	public void setGroup(ButtonGroup group) {
		this.group = group;
	}
}
