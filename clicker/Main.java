package clicker;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Robot;
import java.awt.Window.Type;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyAdapter;
import org.jnativehook.keyboard.NativeKeyEvent;

import keyboard.AbstractShortcutService;
import keyboard.NativeHookShortcutService;
import keyboard.AbstractShortcutService.Button;

public class Main {

	private static volatile JTextField timeField, delayField, exitShortcutField, restartShortcutField;

	private static volatile JFrame frame;

	private static JLabel startedLabel;

	private static JButton startButton;

	private static volatile boolean running;

	private static volatile AbstractShortcutService shortcutService;

	private static NativeKeyAdapter mainListener, exitListener, restartListener;
	private static NativeKeyAdapter currentListener;

	private static final Runnable CLICKING_RUNNABLE = () -> {
		clickLikeDaun();
		if (running)
			exit();
	};
	private static Thread clickingThread;

	public static void main(String args[]) {
		try {
			initNativeHook();
			
			// Initializing variables
			shortcutService = new NativeHookShortcutService();
			shortcutService.initButtonNames();
			running = false;
			frame = new JFrame("Clicker");
			startButton = new JButton("GO");
			JLabel timeLabel = new JLabel("Time:");
			JLabel delayLabel = new JLabel("Delay:");
			JLabel restartShortcutLabel = new JLabel("GO by:");
			JLabel exitShortcutLabel = new JLabel("Exit by:");
			timeField = new JTextField(7);
			delayField = new JTextField(7);
			restartShortcutField = new JTextField(11);
			exitShortcutField = new JTextField(11);

			Font imba = new Font("Arial", Font.PLAIN, 30);
			Font imbaSmall = new Font("Arial", Font.PLAIN, 20);
			final Color ORANGE = new Color(220, 160, 0), VERY_DARK_GREY = new Color(50, 50, 50);

			frame.getContentPane().setLayout(new FlowLayout());

			// Time block's properties
			timeLabel.setFont(imba);
			timeLabel.setForeground(ORANGE);
			timeLabel.setFocusable(true);
			frame.getContentPane().add(timeLabel);

			timeField.setText("50");
			timeField.setFont(imba);
			timeField.setHorizontalAlignment(JTextField.CENTER);
			timeField.setPreferredSize(new Dimension(120, 40));
			timeField.setBackground(VERY_DARK_GREY);
			timeField.setForeground(Color.LIGHT_GRAY);
			timeField.setBorder(BorderFactory.createLoweredBevelBorder());
			timeField.setCaretColor(Color.LIGHT_GRAY);
			frame.getContentPane().add(timeField);

			// Delay block's properties
			delayLabel.setFont(imba);
			delayLabel.setForeground(ORANGE);
			delayLabel.setFocusable(true);
			frame.getContentPane().add(delayLabel);

			delayField.setText("5");
			delayField.setFont(imba);
			delayField.setHorizontalAlignment(JTextField.CENTER);
			delayField.setPreferredSize(new Dimension(120, 40));
			delayField.setBackground(VERY_DARK_GREY);
			delayField.setForeground(Color.LIGHT_GRAY);
			delayField.setBorder(BorderFactory.createLoweredBevelBorder());
			delayField.setCaretColor(Color.LIGHT_GRAY);
			frame.getContentPane().add(delayField);

			// Restart shortcut block's properties
			restartShortcutLabel.setFont(imbaSmall);
			restartShortcutLabel.setForeground(ORANGE);
			restartShortcutLabel.setPreferredSize(timeLabel.getPreferredSize());
			restartShortcutLabel.setFocusable(true);
			frame.getContentPane().add(restartShortcutLabel);

			restartShortcutField.setText(shortcutService.getShortcutFor("+", Button.Enter));
			restartShortcutField.setFont(imbaSmall);
			restartShortcutField.setHorizontalAlignment(SwingConstants.CENTER);
			restartShortcutField.setEditable(false);
			restartShortcutField.setForeground(timeField.getForeground());
			restartShortcutField.setBackground(delayField.getBackground());
			restartShortcutField.setBorder(delayField.getBorder());
			restartShortcutField.addFocusListener(new FocusListener() {

				@Override
				public void focusLost(FocusEvent arg0) {
					restartShortcutField.setForeground(Color.LIGHT_GRAY);

					GlobalScreen.removeNativeKeyListener(currentListener);
					currentListener = mainListener;
					GlobalScreen.addNativeKeyListener(currentListener);
				}

				@Override
				public void focusGained(FocusEvent arg0) {
					restartShortcutField.setForeground(Color.YELLOW);

					GlobalScreen.removeNativeKeyListener(currentListener);
					currentListener = restartListener;
					GlobalScreen.addNativeKeyListener(currentListener);
				}
			});
			frame.getContentPane().add(restartShortcutField);

			// Exit shortcut block's properties
			exitShortcutLabel.setFont(imbaSmall);
			exitShortcutLabel.setForeground(ORANGE);
			exitShortcutLabel.setPreferredSize(timeLabel.getPreferredSize());
			exitShortcutLabel.setFocusable(true);
			frame.getContentPane().add(exitShortcutLabel);

			exitShortcutField.setText(shortcutService.getShortcutFor("+", Button.Escape));
			exitShortcutField.setFont(imbaSmall);
			exitShortcutField.setHorizontalAlignment(SwingConstants.CENTER);
			exitShortcutField.setEditable(false);
			exitShortcutField.setForeground(timeField.getForeground());
			exitShortcutField.setBackground(delayField.getBackground());
			exitShortcutField.setBorder(delayField.getBorder());
			exitShortcutField.addFocusListener(new FocusListener() {

				@Override
				public void focusLost(FocusEvent arg0) {
					exitShortcutField.setForeground(Color.LIGHT_GRAY);

					GlobalScreen.removeNativeKeyListener(currentListener);
					currentListener = mainListener;
					GlobalScreen.addNativeKeyListener(currentListener);
				}

				@Override
				public void focusGained(FocusEvent arg0) {
					exitShortcutField.setForeground(Color.YELLOW);

					GlobalScreen.removeNativeKeyListener(currentListener);
					currentListener = exitListener;
					GlobalScreen.addNativeKeyListener(currentListener);
				}
			});
			frame.getContentPane().add(exitShortcutField);

			// Button's properties
			startButton.setFont(imba);
			startButton.setPreferredSize(new Dimension(270, 60));
			startButton.setFocusable(true);
			frame.getContentPane().add(startButton);
			startButton.addActionListener((a) -> {
				changeState();
			});

			startButton.setBackground(VERY_DARK_GREY);
			startButton.setForeground(Color.LIGHT_GRAY);
			startButton.setBorder(BorderFactory.createRaisedBevelBorder());

			startedLabel = new JLabel("WELL DONE");
			startedLabel.setPreferredSize(startButton.getPreferredSize());
			startedLabel.setFont(imba);
			startedLabel.setForeground(startButton.getForeground());
			startedLabel.setHorizontalAlignment(SwingConstants.HORIZONTAL);

			frame.getContentPane().setBackground(Color.DARK_GRAY);
			frame.setType(Type.UTILITY);
			frame.setPreferredSize(new Dimension(300, 280));
			frame.pack();

			// Technique details
			// Exiting properly on clicking "Cross"
			frame.addWindowListener(new WindowAdapter() {

				@Override
				public void windowClosing(WindowEvent e) {
					exit();
				}

				@Override
				public void windowClosed(WindowEvent e) {
					exit();
				}
			});

			frame.setResizable(false);
			frame.setLocationRelativeTo(null);
			frame.setFocusable(true);
			frame.getContentPane().addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					super.mouseClicked(e);
					frame.requestFocusInWindow();
				}

			});

			frame.setVisible(true);
			frame.setAlwaysOnTop(true);

			// Normalizing labels
			int maxWidth = delayLabel.getWidth();
			maxWidth = timeLabel.getWidth() > maxWidth ? timeLabel.getWidth() : maxWidth;
			maxWidth = restartShortcutLabel.getWidth() > maxWidth ? restartShortcutLabel.getWidth() : maxWidth;
			maxWidth = exitShortcutLabel.getWidth() > maxWidth ? exitShortcutLabel.getWidth() : maxWidth;
			timeLabel.setPreferredSize(new Dimension(maxWidth, timeLabel.getHeight()));
			restartShortcutLabel.setPreferredSize(new Dimension(maxWidth, restartShortcutLabel.getHeight()));
			exitShortcutLabel.setPreferredSize(new Dimension(maxWidth, exitShortcutLabel.getHeight()));
			timeLabel.setSize(delayLabel.getSize());
			repaint();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}

	}

	private static void exit() {
		try {
			GlobalScreen.unregisterNativeHook();
		} catch (NativeHookException e) {
			e.printStackTrace();
		}
		System.runFinalization();
		System.exit(0);
	}

	private static void repaint() {
		frame.revalidate();
		frame.repaint();
	}

	private static void changeState() {
		if (!running) {
			boolean mistaken = false;
			delayField.setForeground(Color.LIGHT_GRAY);
			timeField.setForeground(Color.LIGHT_GRAY);

			try {
				Integer.parseInt(delayField.getText().trim());
			} catch (IllegalArgumentException e) {
				mistaken = true;
				delayField.setForeground(Color.red);
			}
			try {
				Integer.parseInt(timeField.getText().trim());
			} catch (IllegalArgumentException e) {
				mistaken = true;
				timeField.setForeground(Color.red);
			}

			if (mistaken)
				return;

			frame.getContentPane().remove(startButton);
			frame.getContentPane().add(startedLabel);
			repaint();

			timeField.setEnabled(false);
			delayField.setEnabled(false);

			running = true;

			clickingThread = new Thread(CLICKING_RUNNABLE);
			clickingThread.start();

		} else {
			try {
				running = false;
				clickingThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			frame.setTitle("Clicker");

			timeField.setEnabled(true);
			delayField.setEnabled(true);

			frame.getContentPane().remove(startedLabel);
			frame.getContentPane().add(startButton);
			repaint();
		}
	}

	private static void clickLikeDaun() {
		long time = Integer.parseInt(timeField.getText().trim()) * 1000L;
		try {
			Robot clicker = new Robot();
			long startTime = System.currentTimeMillis();
			long goneTime = System.currentTimeMillis() - startTime;
			while (time > goneTime && running) {
				goneTime = System.currentTimeMillis() - startTime;
				try {
					frame.setTitle((time - goneTime) / 1000 + 1 + "");

					clicker.mousePress(InputEvent.BUTTON1_MASK);
					Thread.sleep(Integer.parseInt(delayField.getText()));
					clicker.mouseRelease(InputEvent.BUTTON1_MASK);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	private static void processShortcut() {
		String currentShortcut = shortcutService.getShortcut("+");
		if (currentShortcut.equals(exitShortcutField.getText())) {
			exit();
		}
		if (currentShortcut.equals(restartShortcutField.getText())) {
			changeState();
		}
	}

	private static void initListeners() {
		mainListener = new NativeKeyAdapter() {
			@Override
			public void nativeKeyReleased(NativeKeyEvent e) {
				shortcutService
						.unpressButton(shortcutService.getButtonOfName(NativeKeyEvent.getKeyText(e.getKeyCode())));
			}

			@Override
			public void nativeKeyPressed(NativeKeyEvent e) {
				shortcutService.pressButton(shortcutService.getButtonOfName(NativeKeyEvent.getKeyText(e.getKeyCode())));
				processShortcut();
			}
		};

		exitListener = new NativeKeyAdapter() {

			@Override
			public void nativeKeyReleased(NativeKeyEvent e) {
				shortcutService
						.unpressButton(shortcutService.getButtonOfName(NativeKeyEvent.getKeyText(e.getKeyCode())));
			}

			@Override
			public void nativeKeyPressed(NativeKeyEvent e) {
				shortcutService.pressButton(shortcutService.getButtonOfName(NativeKeyEvent.getKeyText(e.getKeyCode())));
				exitShortcutField.setText(shortcutService.getShortcut("+"));
			}
		};

		restartListener = new NativeKeyAdapter() {

			@Override
			public void nativeKeyReleased(NativeKeyEvent e) {
				shortcutService
						.unpressButton(shortcutService.getButtonOfName(NativeKeyEvent.getKeyText(e.getKeyCode())));
			}

			@Override
			public void nativeKeyPressed(NativeKeyEvent e) {
				shortcutService.pressButton(shortcutService.getButtonOfName(NativeKeyEvent.getKeyText(e.getKeyCode())));
				restartShortcutField.setText(shortcutService.getShortcut("+"));
			}
		};
	}
	
	private static void initNativeHook() {
		initListeners();
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException exxx) {
			JOptionPane.showMessageDialog(null, exxx);
		}

		currentListener = mainListener;
		GlobalScreen.addNativeKeyListener(currentListener);
	}
}
