package keyboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractShortcutService {

	protected final ArrayList<Button> pressedButtons;
	protected final Map<String, Button> dictionary;

	public AbstractShortcutService() {
		pressedButtons = new ArrayList<>();
		dictionary = new HashMap<>();
	}

	public synchronized void pressButton(Button button) {
		if (button == null)
			return;
		if (!pressedButtons.contains(button))
			pressedButtons.add(button);
	}

	public synchronized void unpressButton(Button button) {
		if (pressedButtons.contains(button))
			pressedButtons.remove(button);
	}

	protected void setButtonName(Button button, String name) {
		dictionary.put(name, button);
	}

	public Button getButtonOfName(String name) {
		if (dictionary.containsKey(name))
			return dictionary.get(name);
		return null;
	}

	public abstract String getShortcutFor(String delimeter, Button... buttons);

	public synchronized String getShortcut(String delimeter) {
		if (pressedButtons.isEmpty())
			return "";

		Button[] buttons = (Button[]) pressedButtons.toArray(new Button[pressedButtons.size()]);
		return getShortcutFor(delimeter, buttons);
	}

	public abstract void initButtonNames();

	public enum Button {
		Control("Ctrl"), Shift("Shift"), Alt("Alt"), Delete("Del"), Escape("Esc"), Enter("Enter"), Space("Space"), q(
				"Q"), w("W"), e("E"), r("R"), t("T"), y("Y"), u("U"), i("I"), o("O"), p("P"), a("A"), s("S"), d("D"), f(
						"F"), g("G"), h("H"), j("J"), k("K"), l("L"), z("Z"), x("X"), c("C"), v("V"), b("B"), n("N"), m(
								"M"), Slash("/"), Backslash("\\"), Minus("-"), Equals("="), Backspace("Backspace"), End(
										"End"), Tab("Tab"), Home("Home"), One("1"), Two("2"), Three("3"), Four(
												"4"), Five("5"), Six("6"), Seven("7"), Eight("8"), Nine("9"), Zero("0");

		protected final String NAME;

		private Button(String name) {
			NAME = name;
		}
	}
}
