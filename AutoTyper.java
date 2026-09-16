import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Clipboard;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class AutoTyper extends JFrame {

    private JTextField textInput;
    private JSpinner countSpinner;
    private JSpinner delaySpinner;
    private JSpinner initialDelaySpinner;
    private JButton startButton;

    public AutoTyper() {
        // Thiết lập giao diện native của hệ điều hành
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Auto Typer Pro");
        setSize(450, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Panel chính với viền và màu nền
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 248, 255)); // Màu xanh nhạt (Alice Blue)
        
        // Tiêu đề
        JLabel titleLabel = new JLabel("CÔNG CỤ TỰ ĐỘNG GÕ", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 22));
        titleLabel.setForeground(new Color(41, 128, 185)); // Xanh dương đậm
        titleLabel.setBorder(new EmptyBorder(0, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Panel chứa các trường nhập liệu
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false); // Xuyên thấu để thấy màu nền
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 10, 12, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        Font labelFont = new Font("Helvetica Neue", Font.BOLD, 14);
        Font inputFont = new Font("Helvetica Neue", Font.PLAIN, 14);
        Color labelColor = new Color(52, 73, 94);

        // Nội dung tin nhắn
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblContent = new JLabel("Nội dung:");
        lblContent.setFont(labelFont);
        lblContent.setForeground(labelColor);
        formPanel.add(lblContent, gbc);
        
        textInput = new JTextField("chan bo may de", 20);
        textInput.setFont(inputFont);
        textInput.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1, true),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        gbc.gridx = 1; gbc.gridy = 0;
        formPanel.add(textInput, gbc);

        // Số lần lặp lại
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel lblCount = new JLabel("Số lần lặp:");
        lblCount.setFont(labelFont);
        lblCount.setForeground(labelColor);
        formPanel.add(lblCount, gbc);
        
        countSpinner = new JSpinner(new SpinnerNumberModel(100, 1, 10000, 1));
        countSpinner.setFont(inputFont);
        gbc.gridx = 1; gbc.gridy = 1;
        formPanel.add(countSpinner, gbc);

        // Khoảng nghỉ (ms)
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel lblDelay = new JLabel("Khoảng nghỉ (ms):");
        lblDelay.setFont(labelFont);
        lblDelay.setForeground(labelColor);
        formPanel.add(lblDelay, gbc);
        
        delaySpinner = new JSpinner(new SpinnerNumberModel(100, 1, 10000, 10));
        delaySpinner.setFont(inputFont);
        gbc.gridx = 1; gbc.gridy = 2;
        formPanel.add(delaySpinner, gbc);

        // Thời gian chờ ban đầu (giây)
        gbc.gridx = 0; gbc.gridy = 3;
        JLabel lblInitDelay = new JLabel("Đợi trước khi chạy (s):");
        lblInitDelay.setFont(labelFont);
        lblInitDelay.setForeground(labelColor);
        formPanel.add(lblInitDelay, gbc);
        
        initialDelaySpinner = new JSpinner(new SpinnerNumberModel(5, 1, 60, 1));
        initialDelaySpinner.setFont(inputFont);
        gbc.gridx = 1; gbc.gridy = 3;
        formPanel.add(initialDelaySpinner, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Nút Start
        startButton = new JButton("BẮT ĐẦU CHẠY");
        startButton.setFont(new Font("Helvetica Neue", Font.BOLD, 16));
        startButton.setBackground(new Color(46, 204, 113)); // Xanh lá cây
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        startButton.setOpaque(true); // Cần thiết trên Mac để hiện màu nền
        startButton.setBorderPainted(false); // Xoá viền mặc định của Mac
        startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Thêm padding cho nút bằng cách đặt nó vào panel
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder(15, 0, 0, 0));
        startButton.setPreferredSize(new Dimension(200, 45));
        buttonPanel.add(startButton, BorderLayout.CENTER);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);

        // Xử lý sự kiện nút
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textInput.getText();
                int count = (int) countSpinner.getValue();
                int delay = (int) delaySpinner.getValue();
                int initialDelay = (int) initialDelaySpinner.getValue();

                // Đổi trạng thái giao diện
                startButton.setEnabled(false);
                startButton.setBackground(new Color(231, 76, 60)); // Chuyển sang đỏ
                
                Thread worker = new Thread(() -> {
                    try {
                        StringSelection stringSelection = new StringSelection(text);
                        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                        clipboard.setContents(stringSelection, stringSelection);

                        Robot robot = new Robot();
                        
                        // Đếm ngược
                        for (int i = initialDelay; i > 0; i--) {
                            final int time = i;
                            SwingUtilities.invokeLater(() -> startButton.setText("BẮT ĐẦU SAU " + time + " GIÂY..."));
                            Thread.sleep(1000);
                        }

                        SwingUtilities.invokeLater(() -> startButton.setText("ĐANG GỬI TIN NHẮN..."));

                        String os = System.getProperty("os.name").toLowerCase();
                        int modifierKey = os.contains("mac") ? KeyEvent.VK_META : KeyEvent.VK_CONTROL;

                        for (int i = 0; i < count; i++) {
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
                        JOptionPane.showMessageDialog(AutoTyper.this, "Có lỗi xảy ra: " + ex.getMessage());
                    } finally {
                        SwingUtilities.invokeLater(() -> {
                            startButton.setEnabled(true);
                            startButton.setText("BẮT ĐẦU CHẠY");
                            startButton.setBackground(new Color(46, 204, 113));
                        });
                    }
                });
                worker.start();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AutoTyper().setVisible(true);
        });
    }
}
