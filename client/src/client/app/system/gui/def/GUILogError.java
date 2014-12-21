package client.app.system.gui.def;

import share.core.Constants;
import client.core.gui.components.ExtendedTextArea;
import client.core.gui.components.ExtendedButton;

public class GUILogError {

	public static final String PATH = Constants.GUI_BASE_PATH + "system/gui/xml/log_error";

	public enum Literals {
		
	}

	public ExtendedTextArea error = null;
	public ExtendedButton accept = null;
	public ExtendedButton copy = null;

}