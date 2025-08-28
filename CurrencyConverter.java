import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CurrencyConverter extends JFrame implements ActionListener {

    private JLabel lblTitle, lblAmount, lblResult, lblTime;
    private JTextField txtAmount;
    private JComboBox<String> comboFrom, comboTo;
    private JButton btnConvert, btnClear, btnSwap;
    private JTextArea historyArea;

    private Map<String, Double> exchangeRates;
    private Map<String, String> currencyNames;
    private Map<String, String> currencyFlags;

    public CurrencyConverter() {
        setTitle("💱 Currency Converter Pro");
        setSize(750, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initializeCurrencyData();
        createUI();
        setVisible(true);
    }

    private void initializeCurrencyData() {
        exchangeRates = new HashMap<>();
        exchangeRates.put("USD", 1.0);
        exchangeRates.put("INR", 82.0);
        exchangeRates.put("EUR", 0.92);
        exchangeRates.put("GBP", 0.81);
        exchangeRates.put("AUD", 1.50);
        exchangeRates.put("CAD", 1.35);
        exchangeRates.put("JPY", 145.0);

        currencyNames = new HashMap<>();
        currencyNames.put("USD", "United States Dollar");
        currencyNames.put("INR", "Indian Rupee");
        currencyNames.put("EUR", "Euro");
        currencyNames.put("GBP", "British Pound Sterling");
        currencyNames.put("AUD", "Australian Dollar");
        currencyNames.put("CAD", "Canadian Dollar");
        currencyNames.put("JPY", "Japanese Yen");

        currencyFlags = new HashMap<>();
        currencyFlags.put("USD", "🇺🇸");
        currencyFlags.put("INR", "🇮🇳");
        currencyFlags.put("EUR", "🇪🇺");
        currencyFlags.put("GBP", "🇬🇧");
        currencyFlags.put("AUD", "🇦🇺");
        currencyFlags.put("CAD", "🇨🇦");
        currencyFlags.put("JPY", "🇯🇵");
    }

    private void createUI() {
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(240, 248, 255)); // light pastel blue
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Top panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(240, 248, 255));

        lblTitle = new JLabel("CURRENCY CONVERTER", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI Semibold", Font.BOLD, 26));
        lblTitle.setForeground(new Color(20, 20, 60));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        lblTime = new JLabel(getCurrentTime(), JLabel.CENTER);
        lblTime.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblTime.setForeground(new Color(120, 120, 120));

        topPanel.add(lblTitle, BorderLayout.NORTH);
        topPanel.add(lblTime, BorderLayout.SOUTH);

        // Center panel
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(245, 250, 255));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Amount
        lblAmount = new JLabel("Enter Amount:");
        lblAmount.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblAmount.setForeground(new Color(50, 50, 50));
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(lblAmount, gbc);

        txtAmount = new JTextField(15);
        txtAmount.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtAmount.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 200, 200), 1),
                new EmptyBorder(8, 10, 8, 10)
        ));
        gbc.gridx = 1;
        gbc.gridy = 0;
        centerPanel.add(txtAmount, gbc);

        // From currency
        JLabel lblFrom = new JLabel("From Currency:");
        lblFrom.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblFrom.setForeground(new Color(50, 50, 50));
        gbc.gridx = 0;
        gbc.gridy = 1;
        centerPanel.add(lblFrom, gbc);

        comboFrom = new JComboBox<>();
        for (String code : exchangeRates.keySet()) {
            comboFrom.addItem(currencyFlags.get(code) + "  " + code + " - " + currencyNames.get(code));
        }
        comboFrom.setSelectedItem(currencyFlags.get("USD") + "  USD - " + currencyNames.get("USD"));
        comboFrom.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboFrom.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                return label;
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 1;
        centerPanel.add(comboFrom, gbc);

        // To currency
        JLabel lblTo = new JLabel("To Currency:");
        lblTo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTo.setForeground(new Color(50, 50, 50));
        gbc.gridx = 0;
        gbc.gridy = 2;
        centerPanel.add(lblTo, gbc);

        comboTo = new JComboBox<>();
        for (String code : exchangeRates.keySet()) {
            comboTo.addItem(currencyFlags.get(code) + "  " + code + " - " + currencyNames.get(code));
        }
        comboTo.setSelectedItem(currencyFlags.get("EUR") + "  EUR - " + currencyNames.get("EUR"));
        comboTo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboTo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                return label;
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 2;
        centerPanel.add(comboTo, gbc);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(245, 250, 255));

        btnConvert = createProButton("Convert", new Color(0, 150, 255));
        btnClear = createProButton("Clear", new Color(100, 200, 255));
        btnSwap = createProButton("Swap", new Color(50, 180, 255));

        buttonPanel.add(btnConvert);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnSwap);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        centerPanel.add(buttonPanel, gbc);

        // Result
        JLabel lblResultTitle = new JLabel("Conversion Result:");
        lblResultTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblResultTitle.setForeground(new Color(50, 50, 50));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        centerPanel.add(lblResultTitle, gbc);

        lblResult = new JLabel("Enter amount to convert", JLabel.CENTER);
        lblResult.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblResult.setForeground(new Color(50, 50, 50));
        lblResult.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(180, 200, 255), 2),
                new EmptyBorder(15, 15, 15, 15)
        ));
        lblResult.setOpaque(true);
        lblResult.setBackground(new Color(230, 240, 255));
        gbc.gridx = 1;
        gbc.gridy = 4;
        centerPanel.add(lblResult, gbc);

        // History panel
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(240, 248, 255));
        bottomPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                "Conversion History",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 12),
                new Color(50, 50, 50)
        ));

        historyArea = new JTextArea(6, 40);
        historyArea.setEditable(false);
        historyArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        historyArea.setBackground(new Color(245, 250, 255));
        historyArea.setForeground(new Color(50, 50, 50));
        historyArea.setBorder(new EmptyBorder(5, 5, 5, 5));

        JScrollPane scrollPane = new JScrollPane(historyArea);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(new Color(245, 250, 255));

        bottomPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        btnConvert.addActionListener(this);
        btnClear.addActionListener(e -> clearFields());
        btnSwap.addActionListener(e -> swapCurrencies());

        txtAmount.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (!txtAmount.getText().trim().isEmpty()) {
                    convertCurrency();
                } else {
                    lblResult.setText("Enter amount to convert");
                }
            }
        });
    }

    private JButton createProButton(String text, Color baseColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(baseColor);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(baseColor.darker(), 1),
                new EmptyBorder(10, 20, 10, 20)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(baseColor.brighter());
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(baseColor);
            }
        });

        return button;
    }

    private String getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, MMM d, yyyy | hh:mm:ss a");
        return formatter.format(new Date());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnConvert) {
            convertCurrency();
        }
    }

    private void convertCurrency() {
        try {
            double amount = Double.parseDouble(txtAmount.getText());
            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Please enter a positive amount", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String from = comboFrom.getSelectedItem().toString().split(" ")[1];
            String to = comboTo.getSelectedItem().toString().split(" ")[1];

            double amountInUSD = amount / exchangeRates.get(from);
            double convertedAmount = amountInUSD * exchangeRates.get(to);

            String result = String.format("%,.2f %s = %,.2f %s", amount, from, convertedAmount, to);
            lblResult.setText("<html><div style='text-align:center;'>" + result + "</div></html>");

            historyArea.append(result + " (" + getCurrentTime() + ")\n");
            historyArea.setCaretPosition(historyArea.getDocument().getLength());

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        txtAmount.setText("");
        lblResult.setText("Enter amount to convert");
        historyArea.setText("");
    }

    private void swapCurrencies() {
        Object fromSelection = comboFrom.getSelectedItem();
        comboFrom.setSelectedItem(comboTo.getSelectedItem());
        comboTo.setSelectedItem(fromSelection);

        if (!txtAmount.getText().trim().isEmpty()) {
            convertCurrency();
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new CurrencyConverter());
    }
}
