package client.core.gui.components;

import java.awt.Dimension;

import javax.swing.JRadioButton;

import client.core.gui.ToolTipInterface;

public class ExtendedRadioButton extends JRadioButton implements ToolTipInterface {
	
	private static final long serialVersionUID = 1L;
	
	private String tooltip = "";
	
	private static final int DEFAULT_WIDTH = 17;
	
	public ExtendedRadioButton() {
		setPreferredSize(new Dimension(ExtendedRadioButton.DEFAULT_WIDTH, ExtendedRadioButton.DEFAULT_WIDTH));
	}
	
	public void select(boolean select) {
		setSelected(select);
	}
	
	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}
	
	public String getTooltip() {
		return this.tooltip;
	}
}