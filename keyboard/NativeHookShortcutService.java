package keyboard;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.jnativehook.keyboard.NativeKeyEvent;

public class NativeHookShortcutService extends AbstractShortcutService {

	@Override
	public void initButtonNames() {
		setButtonName(AbstractShortcutService.Button.a, NativeKeyEvent.getKeyText(NativeKeyEvent.VC_A));
		setButtonName(AbstractShortcutService.Button.b, NativeKeyEvent.getKeyText(NativeKeyEvent.VC_B));
		setButtonName(AbstractShortcutService.Button.c, NativeKeyEvent.getKeyText(NativeKeyEvent.VC_C));
		setButtonName(AbstractShortcutService.Button.d, NativeKeyEvent.getKeyText(NativeKeyEvent.VC_D));
		setButtonName(AbstractShortcutService.Button.e, NativeKeyEvent.getKeyText(NativeKeyEvent.VC_E));
		setButtonName(AbstractShortcutService.Button.f, NativeKeyEvent.getKeyText(NativeKeyEvent.VC_F));
		setButtonName(AbstractShortcutService.Button.g, NativeKeyEvent.getKeyText(NativeKeyEvent.VC_G));
		setButtonName(AbstractShortcutService.Button.h, NativeKeyEvent.getKeyText(NativeKeyEvent.VC_H));
		setButtonName(AbstractShortcutService.Button.i, NativeKeyEvent.getKeyText(NativeKeyEvent.VC_I));
		setButtonName(AbstractShortcutService.Button.j, NativeKeyEvent.getKeyText(NativeKeyEvent.VC_J));
		setButtonName(AbstractShortcutService.Button.k, NativeKeyEvent.getKeyText(NativeKeyEvent.VC_K));
		setButtonName(AbstractShortcutService.Button.l, NativeKeyEvent.getKeyText(NativeKeyEvent.VC_L));
		setButtonName(AbstractShortcutService.Button.m, NativeKeyEvent.getKeyText(NativeKeyEvent.VC_M));
		setButtonName(AbstractShortcutService.Button.n, NativeKeyEvent.getKeyText(NativeKeyEvent.VC_N));
		setButtonName(AbstractShortcutService.Button.o, NativeKeyEvent.getKeyText(NativeKeyEvent.VC_O));
		setButtonName(AbstractShortcutService.Button.p, NativeKeyEvent.getKeyText(NativeKeyEvent.VC_P));
		setButtonName(AbstractShortcutService.Button.q, NativeKeyEvent.getKeyText(NativeKeyEvent.VC_Q));
		setButtonName(AbstractShortcutService.Button.r, NativeKeyEvent.getKeyText(NativeKeyEvent.VC_R));
		setButtonName(AbstractShortcutService.Button.s, NativeKeyEvent.getKeyText(NativeKeyEvent.VC_S));
		setButtonName(AbstractShortcutService.Button.t, NativeKeyEvent.getKeyText(NativeKeyEvent.VC_T));
		setButtonName(AbstractShortcutService.Button.u, NativeKeyEvent.getKeyText(NativeKeyEvent.VC_U));
		setButtonName(AbstractShortcutService.Button.v, NativeKeyEvent.getKeyText(NativeKeyEvent.VC_V));
		setButtonName(AbstractShortcutService.Button.w, NativeKeyEvent.getKeyText(NativeKeyEvent.VC_W));
		setButtonName(AbstractShortcutService.Button.x, NativeKeyEvent.getKeyText(NativeKeyEvent.VC_X));
		setButtonName(AbstractShortcutService.Button.y, NativeKeyEvent.getKeyText(NativeKeyEvent.VC_Y));
		setButtonName(AbstractShortcutService.Button.z, NativeKeyEvent.getKeyText(NativeKeyEvent.VC_Z));
		setButtonName(AbstractShortcutService.Button.One, NativeKeyEvent.getKeyText(NativeKeyEvent.VC_1));
		setButtonName(AbstractShortcutService.Button.Two, NativeKeyEvent.getKeyText(NativeKeyEvent.VC_2));
		setButtonName(AbstractShortcutService.Button.Three, NativeKeyEvent.getKeyText(NativeKeyEvent.VC_3));
		setButtonName(AbstractShortcutService.Button.Four, NativeKeyEvent.getKeyText(NativeKeyEvent.VC_4));
		setButtonName(AbstractShortcutService.Button.Five, NativeKeyEvent.getKeyText(NativeKeyEvent.VC_5));
		setButtonName(AbstractShortcutService.Button.Six, NativeKeyEvent.getKeyText(NativeKeyEvent.VC_6));
		setButtonName(AbstractShortcutService.Button.Seven, NativeKeyEvent.getKeyText(NativeKeyEvent.VC_7));
		setButtonName(AbstractShortcutService.Button.Eight, NativeKeyEvent.getKeyText(NativeKeyEvent.VC_8));
		setButtonName(AbstractShortcutService.Button.Nine, NativeKeyEvent.getKeyText(NativeKeyEvent.VC_9));
		setButtonName(AbstractShortcutService.Button.Zero, NativeKeyEvent.getKeyText(NativeKeyEvent.VC_0));
		setButtonName(AbstractShortcutService.Button.Slash, NativeKeyEvent.getKeyText(NativeKeyEvent.VC_SLASH));
		setButtonName(AbstractShortcutService.Button.Backslash,
				NativeKeyEvent.getKeyText(NativeKeyEvent.VC_BACK_SLASH));
		setButtonName(AbstractShortcutService.Button.Space, NativeKeyEvent.getKeyText(NativeKeyEvent.VC_SPACE));
		setButtonName(AbstractShortcutService.Button.Enter, NativeKeyEvent.getKeyText(NativeKeyEvent.VC_ENTER));
		setButtonName(AbstractShortcutService.Button.End, NativeKeyEvent.getKeyText(NativeKeyEvent.VC_END));
		setButtonName(AbstractShortcutService.Button.Escape, NativeKeyEvent.getKeyText(NativeKeyEvent.VC_ESCAPE));
		setButtonName(AbstractShortcutService.Button.Tab, NativeKeyEvent.getKeyText(NativeKeyEvent.VC_TAB));
		setButtonName(AbstractShortcutService.Button.Minus, NativeKeyEvent.getKeyText(NativeKeyEvent.VC_MINUS));
		setButtonName(AbstractShortcutService.Button.Equals, NativeKeyEvent.getKeyText(NativeKeyEvent.VC_EQUALS));
		setButtonName(AbstractShortcutService.Button.Delete, NativeKeyEvent.getKeyText(NativeKeyEvent.VC_DELETE));
		setButtonName(AbstractShortcutService.Button.Home, NativeKeyEvent.getKeyText(NativeKeyEvent.VC_HOME));
		setButtonName(AbstractShortcutService.Button.Backspace, NativeKeyEvent.getKeyText(NativeKeyEvent.VC_BACKSPACE));
		setButtonName(AbstractShortcutService.Button.Control, NativeKeyEvent.getKeyText(NativeKeyEvent.VC_CONTROL));
		setButtonName(AbstractShortcutService.Button.Alt, NativeKeyEvent.getKeyText(NativeKeyEvent.VC_ALT));
		setButtonName(AbstractShortcutService.Button.Shift, NativeKeyEvent.getKeyText(NativeKeyEvent.VC_SHIFT));
	}

	@Override
	public String getShortcutFor(String delimeter, Button ... buttons) {
		
		List<Button> buttonsList = Arrays.asList(buttons);
		buttonsList.sort(new Comparator<Button>() {
			@Override
			public int compare(Button button1, Button button2) {
				return button1.compareTo(button2);
			}
		});

		StringBuilder shortcutBuilder = new StringBuilder();

		for (Button button : buttonsList) {
			shortcutBuilder.append(button.NAME);
			shortcutBuilder.append(delimeter);
		}
		if (shortcutBuilder.length() > 0) {
			shortcutBuilder.deleteCharAt(shortcutBuilder.length() - 1);
		}
		
		return shortcutBuilder.toString();
	}

}
