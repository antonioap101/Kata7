package software.ulpgc.kata7;

import org.apache.commons.imaging.Imaging;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;


public class EggBreakingGame {

    private static final int TOTAL_CLICKS = 25;
    private static final Map<Double, ImageIcon> stageIcons = new HashMap<>();

    public static void main(String[] args) {
        loadImages();
        createAndShowGUI();
    }

    private static void loadImages() {
        try {
            stageIcons.put(25.0, new ImageIcon(Imaging.getBufferedImage(EggBreakingGame.class.getResource("/stage1.png").openStream())));
            stageIcons.put(50.0, new ImageIcon(Imaging.getBufferedImage(EggBreakingGame.class.getResource("/stage2.png").openStream())));
            stageIcons.put(75.0, new ImageIcon(Imaging.getBufferedImage(EggBreakingGame.class.getResource("/stage3.png").openStream())));
            stageIcons.put(99.0, new ImageIcon(Imaging.getBufferedImage(EggBreakingGame.class.getResource("/stage4.png").openStream())));
            stageIcons.put(100.0, new ImageIcon(Imaging.getBufferedImage(EggBreakingGame.class.getResource("/stage5.png").openStream())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Egg Breaking Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(250, 270);
        frame.setResizable(false);

        JPanel panel = new JPanel(new BorderLayout());
        frame.getContentPane().add(panel);

        JLabel countLabel = new JLabel("Remaining Clicks: " + TOTAL_CLICKS);
        countLabel.setFont(new Font("Arial", Font.BOLD, 18));
        countLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(countLabel, BorderLayout.NORTH);

        JButton button = new JButton(stageIcons.get(25.0));
        button.setBorderPainted(false);
        panel.add(button, BorderLayout.CENTER);

        button.addActionListener(e -> updateGame(button, countLabel));

        frame.setVisible(true);
    }

    private static void updateGame(JButton button, JLabel countLabel) {
        Integer clickCount = (Integer) button.getClientProperty("clickCount");
        clickCount = (clickCount == null) ? 1 : clickCount + 1;
        button.putClientProperty("clickCount", clickCount);

        if (clickCount >= TOTAL_CLICKS) countLabel.setText("Congratulations!");
        else countLabel.setText("Remaining Clicks: " + Math.max(TOTAL_CLICKS - clickCount, 0));

        double pctCompleted = (clickCount / (double) TOTAL_CLICKS) * 100.0;
        updateButtonIcon(button, pctCompleted);
    }

    private static void updateButtonIcon(JButton button, double pctCompleted) {
        for (Double key : stageIcons.keySet().stream().sorted().toList()) {
            if (pctCompleted <= key) {
                button.setIcon(stageIcons.get(key));
                break;
            }
        }
    }
}
