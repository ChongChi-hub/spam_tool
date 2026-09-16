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
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Auto Tool Pro");
        setSize(480, 500);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                isRunning = false; // Dừng lập tức việc gõ/click
                System.exit(0); // Tắt hoàn toàn chương trình (tương đương Ctrl+C)
            }
        });
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);

        // Panel nền chính
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(new Color(245, 247, 250)); // Nền xám nhạt hiện đại

        JLabel titleLabel = new JLabel("CÔNG CỤ TỰ ĐỘNG", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 22));
        titleLabel.setForeground(new Color(44, 62, 80));
        titleLabel.setBorder(new EmptyBorder(0, 0, 10, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
        
        tabbedPane.addTab("Tự động gõ (Auto Typer)", createTyperPanel());
        tabbedPane.addTab("Tự động Click (Auto Clicker)", createClickerPanel());

        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        add(mainPanel);
    }

    // --- CÁC COMPONENT TÙY CHỈNH BO GÓC ---

    // Nút bấm bo góc
    private JButton createRoundedButton(String text, Color bgColor) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25); // Bo tròn 25px
                super.paintComponent(g);
                g2.dispose();
            }
        };
        btn.setFont(new Font("Helvetica Neue", Font.BOLD, 16));
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false); 
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(200, 45));
        return btn;
    }

    // Ô nhập text bo góc
    private JTextField createRoundedTextField(String text) {
        JTextField textField = new JTextField(text, 20) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                super.paintComponent(g);
                g2.dispose();
            }
            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(200, 200, 200));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
                g2.dispose();
            }
        };
        textField.setOpaque(false);
        textField.setBorder(new EmptyBorder(8, 12, 8, 12));
        textField.setFont(new Font("Helvetica Neue", Font.PLAIN, 14));
        return textField;
    }

    // Panel bo góc bọc các form
    private JPanel createRoundedPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE); // Màu nền trắng cho form
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // Bo góc panel
                super.paintComponent(g);
                g2.dispose();
            }
        };
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        return panel;
    }

    // ----------------------------------------

    private JPanel createTyperPanel() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.setBorder(new EmptyBorder(10, 5, 10, 5));

        JPanel panel = createRoundedPanel();
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 10, 12, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Helvetica Neue", Font.BOLD, 14);
        Font inputFont = new Font("Helvetica Neue", Font.PLAIN, 14);
        Color labelColor = new Color(52, 73, 94);

        // Nội dung
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblContent = new JLabel("Nội dung:");
        lblContent.setFont(labelFont); lblContent.setForeground(labelColor);
        formPanel.add(lblContent, gbc);
        
        JTextField textInput = createRoundedTextField("chan bo may de");
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
        JButton actionButton = createRoundedButton("BẮT ĐẦU CHẠY", new Color(46, 204, 113));
        
        JPanel btnPanel = new JPanel(new BorderLayout());
        btnPanel.setOpaque(false);
        btnPanel.setBorder(new EmptyBorder(15, 0, 0, 0));
        btnPanel.add(actionButton, BorderLayout.CENTER);
        
        panel.add(btnPanel, BorderLayout.SOUTH);
        wrapper.add(panel, BorderLayout.CENTER);

        actionButton.addActionListener(e -> {
            if (isRunning) {
                isRunning = false;
                return;
            }

            String text = textInput.getText();
            int count = (int) countSpinner.getValue();
            int delay = (int) delaySpinner.getValue();
            int initialDelay = (int) initialDelaySpinner.getValue();

            startTyperTask(actionButton, text, count, delay, initialDelay);
        });

        return wrapper;
    }

    private JPanel createClickerPanel() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.setBorder(new EmptyBorder(10, 5, 10, 5));

        JPanel panel = createRoundedPanel();
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

        JButton actionButton = createRoundedButton("BẮT ĐẦU CHẠY", new Color(46, 204, 113));
        
        JPanel btnPanel = new JPanel(new BorderLayout());
        btnPanel.setOpaque(false);
        btnPanel.setBorder(new EmptyBorder(15, 0, 0, 0));
        btnPanel.add(actionButton, BorderLayout.CENTER);
        
        panel.add(btnPanel, BorderLayout.SOUTH);
        wrapper.add(panel, BorderLayout.CENTER);

        actionButton.addActionListener(e -> {
            if (isRunning) {
                isRunning = false;
                return;
            }

            int btnIndex = mouseButtonCombo.getSelectedIndex();
            int count = (int) countSpinner.getValue();
            int delay = (int) delaySpinner.getValue();
            int initialDelay = (int) initialDelaySpinner.getValue();

            startClickerTask(actionButton, btnIndex, count, delay, initialDelay);
        });

        return wrapper;
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
                    SwingUtilities.invokeLater(() -> btn.setText("BẮT ĐẦU SAU " + time + "s (Bấm để HỦY)"));
                    Thread.sleep(1000);
                }

                if (!isRunning) {
                    resetButtonState(btn);
                    return;
                }

                SwingUtilities.invokeLater(() -> btn.setText("ĐANG CHẠY... BẤM ĐỂ DỪNG"));

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
                int mouseMask = InputEvent.BUTTON1_DOWN_MASK; 
                if (btnIndex == 1) mouseMask = InputEvent.BUTTON3_DOWN_MASK; 
                else if (btnIndex == 2) mouseMask = InputEvent.BUTTON2_DOWN_MASK; 
                
                for (int i = initialDelay; i > 0 && isRunning; i--) {
                    final int time = i;
                    SwingUtilities.invokeLater(() -> btn.setText("BẮT ĐẦU SAU " + time + "s (Bấm để HỦY)"));
                    Thread.sleep(1000);
                }

                if (!isRunning) {
                    resetButtonState(btn);
                    return;
                }

                SwingUtilities.invokeLater(() -> btn.setText("ĐANG CLICK... BẤM ĐỂ DỪNG"));

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
            btn.setBackground(new Color(231, 76, 60)); // Màu đỏ
            btn.repaint();
        });
    }

    private void resetButtonState(JButton btn) {
        SwingUtilities.invokeLater(() -> {
            btn.setText("BẮT ĐẦU CHẠY");
            btn.setBackground(new Color(46, 204, 113)); // Màu xanh
            btn.repaint();
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AutoTyper().setVisible(true);
        });
    }
}
