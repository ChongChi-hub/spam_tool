import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Clipboard;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class AutoTyper extends JFrame {

    private volatile boolean isRunning = false;

    public AutoTyper() {
        // Thiết lập giao diện native
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Auto Tool Pro");
        setSize(480, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true); // Để cửa sổ luôn hiển thị trên cùng, giúp dễ dàng bấm Dừng lại

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(new Color(240, 248, 255));

        JLabel titleLabel = new JLabel("CÔNG CỤ TỰ ĐỘNG", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 22));
        titleLabel.setForeground(new Color(41, 128, 185));
        titleLabel.setBorder(new EmptyBorder(0, 0, 10, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
        
        // Tab 1: Auto Typer
        tabbedPane.addTab("Tự động gõ (Auto Typer)", createTyperPanel());
        
        // Tab 2: Auto Clicker
        tabbedPane.addTab("Tự động Click (Auto Clicker)", createClickerPanel());

        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        add(mainPanel);
    }

    private JPanel createTyperPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Helvetica Neue", Font.BOLD, 14);
        Font inputFont = new Font("Helvetica Neue", Font.PLAIN, 14);
        Color labelColor = new Color(52, 73, 94);

        // Nội dung
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblContent = new JLabel("Nội dung:");
        lblContent.setFont(labelFont); lblContent.setForeground(labelColor);
        formPanel.add(lblContent, gbc);
        
        JTextField textInput = new JTextField("chan bo may de", 20);
        textInput.setFont(inputFont);
        gbc.gridx = 1; gbc.gridy = 0;
        formPanel.add(textInput, gbc);

        // Số lần lặp
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel lblCount = new JLabel("Số lần lặp:");
        lblCount.setFont(labelFont); lblCount.setForeground(labelColor);
        formPanel.add(lblCount, gbc);
        
        JSpinner countSpinner = new JSpinner(new SpinnerNumberModel(100, 1, 10000, 1));
        countSpinner.setFont(inputFont);
        gbc.gridx = 1; gbc.gridy = 1;
        formPanel.add(countSpinner, gbc);

        // Khoảng nghỉ
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel lblDelay = new JLabel("Khoảng nghỉ (ms):");
        lblDelay.setFont(labelFont); lblDelay.setForeground(labelColor);
        formPanel.add(lblDelay, gbc);
        
        JSpinner delaySpinner = new JSpinner(new SpinnerNumberModel(100, 1, 10000, 10));
        delaySpinner.setFont(inputFont);
        gbc.gridx = 1; gbc.gridy = 2;
        formPanel.add(delaySpinner, gbc);

        // Đợi trước khi chạy
        gbc.gridx = 0; gbc.gridy = 3;
        JLabel lblInitDelay = new JLabel("Đợi trước khi chạy (s):");
        lblInitDelay.setFont(labelFont); lblInitDelay.setForeground(labelColor);
        formPanel.add(lblInitDelay, gbc);
        
        JSpinner initialDelaySpinner = new JSpinner(new SpinnerNumberModel(3, 1, 60, 1));
        initialDelaySpinner.setFont(inputFont);
        gbc.gridx = 1; gbc.gridy = 3;
        formPanel.add(initialDelaySpinner, gbc);

        panel.add(formPanel, BorderLayout.CENTER);

        // Nút Start
        JButton actionButton = createActionButton();
        panel.add(actionButton, BorderLayout.SOUTH);

        actionButton.addActionListener(e -> {
            if (isRunning) {
                isRunning = false; // Bấm lần 2 sẽ dừng lại
                return;
            }

            String text = textInput.getText();
            int count = (int) countSpinner.getValue();
            int delay = (int) delaySpinner.getValue();
            int initialDelay = (int) initialDelaySpinner.getValue();

            startTyperTask(actionButton, text, count, delay, initialDelay);
        });

        return panel;
    }

    private JPanel createClickerPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Helvetica Neue", Font.BOLD, 14);
        Font inputFont = new Font("Helvetica Neue", Font.PLAIN, 14);
        Color labelColor = new Color(52, 73, 94);

        // Chuột trái/phải/giữa
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblButton = new JLabel("Chuột:");
        lblButton.setFont(labelFont); lblButton.setForeground(labelColor);
        formPanel.add(lblButton, gbc);
        
        String[] buttons = {"Chuột Trái", "Chuột Phải", "Chuột Giữa"};
        JComboBox<String> mouseButtonCombo = new JComboBox<>(buttons);
        mouseButtonCombo.setFont(inputFont);
        gbc.gridx = 1; gbc.gridy = 0;
        formPanel.add(mouseButtonCombo, gbc);

        // Số lần lặp
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel lblCount = new JLabel("Số lần click:");
        lblCount.setFont(labelFont); lblCount.setForeground(labelColor);
        formPanel.add(lblCount, gbc);
        
        JSpinner countSpinner = new JSpinner(new SpinnerNumberModel(100, 1, 10000, 1));
        countSpinner.setFont(inputFont);
        gbc.gridx = 1; gbc.gridy = 1;
        formPanel.add(countSpinner, gbc);

        // Khoảng nghỉ
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel lblDelay = new JLabel("Khoảng nghỉ (ms):");
        lblDelay.setFont(labelFont); lblDelay.setForeground(labelColor);
        formPanel.add(lblDelay, gbc);
        
        JSpinner delaySpinner = new JSpinner(new SpinnerNumberModel(100, 1, 10000, 10));
        delaySpinner.setFont(inputFont);
        gbc.gridx = 1; gbc.gridy = 2;
        formPanel.add(delaySpinner, gbc);

        // Đợi trước khi chạy
        gbc.gridx = 0; gbc.gridy = 3;
        JLabel lblInitDelay = new JLabel("Đợi trước khi chạy (s):");
        lblInitDelay.setFont(labelFont); lblInitDelay.setForeground(labelColor);
        formPanel.add(lblInitDelay, gbc);
        
        JSpinner initialDelaySpinner = new JSpinner(new SpinnerNumberModel(3, 1, 60, 1));
        initialDelaySpinner.setFont(inputFont);
        gbc.gridx = 1; gbc.gridy = 3;
        formPanel.add(initialDelaySpinner, gbc);

        panel.add(formPanel, BorderLayout.CENTER);

        JButton actionButton = createActionButton();
        panel.add(actionButton, BorderLayout.SOUTH);

        actionButton.addActionListener(e -> {
            if (isRunning) {
                isRunning = false; // Bấm lần 2 sẽ dừng lại
                return;
            }

            int btnIndex = mouseButtonCombo.getSelectedIndex();
            int count = (int) countSpinner.getValue();
            int delay = (int) delaySpinner.getValue();
            int initialDelay = (int) initialDelaySpinner.getValue();

            startClickerTask(actionButton, btnIndex, count, delay, initialDelay);
        });

        return panel;
    }

    private JButton createActionButton() {
        JButton btn = new JButton("BẮT ĐẦU CHẠY");
        btn.setFont(new Font("Helvetica Neue", Font.BOLD, 16));
        btn.setBackground(new Color(46, 204, 113));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(200, 45));
        return btn;
    }

    private void startTyperTask(JButton btn, String text, int count, int delay, int initialDelay) {
        isRunning = true;
        setButtonStateRunning(btn);

        new Thread(() -> {
            try {
                StringSelection stringSelection = new StringSelection(text);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, stringSelection);

                Robot robot = new Robot();
                
                for (int i = initialDelay; i > 0 && isRunning; i--) {
                    final int time = i;
                    SwingUtilities.invokeLater(() -> btn.setText("BẮT ĐẦU SAU " + time + " GIÂY (Bấm vào đây để DỪNG)"));
                    Thread.sleep(1000);
                }

                if (!isRunning) {
                    resetButtonState(btn);
                    return;
                }

                SwingUtilities.invokeLater(() -> btn.setText("ĐANG CHẠY... BẤM VÀO ĐÂY ĐỂ DỪNG LẠI"));

                String os = System.getProperty("os.name").toLowerCase();
                int modifierKey = os.contains("mac") ? KeyEvent.VK_META : KeyEvent.VK_CONTROL;

                for (int i = 0; i < count && isRunning; i++) {
                    robot.keyPress(modifierKey);
                    robot.keyPress(KeyEvent.VK_V);
                    robot.keyRelease(KeyEvent.VK_V);
                    robot.keyRelease(modifierKey);

                    Thread.sleep(20);

                    robot.keyPress(KeyEvent.VK_ENTER);
                    robot.keyRelease(KeyEvent.VK_ENTER);

                    Thread.sleep(delay);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                isRunning = false;
                resetButtonState(btn);
            }
        }).start();
    }

    private void startClickerTask(JButton btn, int btnIndex, int count, int delay, int initialDelay) {
        isRunning = true;
        setButtonStateRunning(btn);

        new Thread(() -> {
            try {
                Robot robot = new Robot();
                int mouseMask = InputEvent.BUTTON1_DOWN_MASK; // Chuột trái
                if (btnIndex == 1) mouseMask = InputEvent.BUTTON3_DOWN_MASK; // Chuột phải
                else if (btnIndex == 2) mouseMask = InputEvent.BUTTON2_DOWN_MASK; // Chuột giữa
                
                for (int i = initialDelay; i > 0 && isRunning; i--) {
                    final int time = i;
                    SwingUtilities.invokeLater(() -> btn.setText("BẮT ĐẦU SAU " + time + " GIÂY (Bấm vào đây để DỪNG)"));
                    Thread.sleep(1000);
                }

                if (!isRunning) {
                    resetButtonState(btn);
                    return;
                }

                SwingUtilities.invokeLater(() -> btn.setText("ĐANG CLICK... BẤM VÀO ĐÂY ĐỂ DỪNG LẠI"));

                for (int i = 0; i < count && isRunning; i++) {
                    robot.mousePress(mouseMask);
                    robot.mouseRelease(mouseMask);
                    Thread.sleep(delay);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                isRunning = false;
                resetButtonState(btn);
            }
        }).start();
    }

    private void setButtonStateRunning(JButton btn) {
        SwingUtilities.invokeLater(() -> {
            btn.setBackground(new Color(231, 76, 60)); // Màu đỏ (báo hiệu có thể dừng)
        });
    }

    private void resetButtonState(JButton btn) {
        SwingUtilities.invokeLater(() -> {
            btn.setText("BẮT ĐẦU CHẠY");
            btn.setBackground(new Color(46, 204, 113)); // Trở lại xanh lá
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AutoTyper().setVisible(true);
        });
    }
}
