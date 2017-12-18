import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Robot;
import java.awt.Window.Type;
import java.awt.event.InputEvent;
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
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class Main implements NativeKeyListener {

	private static volatile JTextField timeField, delayField;

	private static volatile JFrame frame;

	private static JLabel startedLabel;

	private static JButton startButton;

	private static boolean running;

	private static final Runnable CLICKING_RUNNABLE = () -> {
		clickLikeDaun();
		if (running)
			exit();
	};
	private static Thread clickingThread;

	static {
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException exxx) {
			JOptionPane.showMessageDialog(null, exxx);
		}

		GlobalScreen.addNativeKeyListener(new Main());
	}

	public static void main(String args[]) {
		try {
			frame = new JFrame("Clicker");

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

			frame.getContentPane().setLayout(new FlowLayout());

			Font imba = new Font("Arial", Font.PLAIN, 30);

			JLabel question = new JLabel("Time:");
			question.setFont(imba);
			frame.getContentPane().add(question);

			timeField = new JTextField(7);
			timeField.setText("50");
			timeField.setFont(imba);
			timeField.setHorizontalAlignment(JTextField.CENTER);
			timeField.setPreferredSize(new Dimension(120, 40));
			frame.getContentPane().add(timeField);

			JLabel delayQuestion = new JLabel("Delay:");
			delayQuestion.setFont(imba);
			question.setPreferredSize(delayQuestion.getPreferredSize());
			frame.getContentPane().add(delayQuestion);

			delayField = new JTextField(7);
			delayField.setText("5");
			delayField.setFont(imba);
			delayField.setHorizontalAlignment(JTextField.CENTER);
			delayField.setPreferredSize(new Dimension(120, 40));
			frame.getContentPane().add(delayField);

			running = false;
			startButton = new JButton("GO");
			startButton.setFont(imba);
			startButton.setPreferredSize(new Dimension(270, 60));
			startButton.setFocusable(true);
			frame.getContentPane().add(startButton);
			startButton.addActionListener((a) -> {
				changeState();
			});

			// Design
			frame.getContentPane().setBackground(Color.DARK_GRAY);
			startButton.setBackground(new Color(50, 50, 50));
			startButton.setForeground(Color.LIGHT_GRAY);
			startButton.setBorder(BorderFactory.createRaisedBevelBorder());

			delayQuestion.setForeground(new Color(220, 160, 0));
			question.setForeground(new Color(220, 160, 0));

			delayField.setBackground(new Color(50, 50, 50));
			delayField.setForeground(Color.LIGHT_GRAY);
			delayField.setBorder(BorderFactory.createLoweredBevelBorder());
			delayField.setCaretColor(Color.LIGHT_GRAY);

			timeField.setBackground(new Color(50, 50, 50));
			timeField.setForeground(Color.LIGHT_GRAY);
			timeField.setBorder(BorderFactory.createLoweredBevelBorder());
			timeField.setCaretColor(Color.LIGHT_GRAY);

			startedLabel = new JLabel("WELL DONE");
			startedLabel.setPreferredSize(startButton.getPreferredSize());
			startedLabel.setFont(imba);
			startedLabel.setForeground(startButton.getForeground());
			startedLabel.setHorizontalAlignment(SwingConstants.HORIZONTAL);

			// Technique details
			frame.setType(Type.UTILITY);
			frame.setPreferredSize(new Dimension(300, 200));
			frame.pack();
			frame.setResizable(false);
			frame.setLocationRelativeTo(null);

			frame.setVisible(true);
			frame.setAlwaysOnTop(true);
		} catch (

		Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}

	}

	private static void clickLikeDaun() {
		long time = Integer.parseInt(timeField.getText()) * 1000L;
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

	private static void exit() {
		try {
			GlobalScreen.unregisterNativeHook();
		} catch (NativeHookException e) {
			e.printStackTrace();
		}
		System.runFinalization();
		System.exit(0);
	}

	private static void changeState() {
		if (!running) {
			boolean mistaken = false;
			delayField.setForeground(Color.LIGHT_GRAY);
			timeField.setForeground(Color.LIGHT_GRAY);

			try {
				Integer.parseInt(delayField.getText());
			} catch (IllegalArgumentException e) {
				mistaken = true;
				delayField.setForeground(Color.red);
			}
			try {
				Integer.parseInt(timeField.getText());
			} catch (IllegalArgumentException e) {
				mistaken = true;
				timeField.setForeground(Color.red);
			}

			if (mistaken)
				return;

			frame.getContentPane().remove(startButton);
			frame.getContentPane().add(startedLabel);
			frame.revalidate();
			frame.repaint();

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
			frame.revalidate();
			frame.repaint();
		}
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent arg0) {
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent arg0) {
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
		if (nativeKeyEvent.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
			exit();
		}
		if (nativeKeyEvent.getKeyCode() == NativeKeyEvent.VC_ENTER) {
			changeState();
		}
	}
}
