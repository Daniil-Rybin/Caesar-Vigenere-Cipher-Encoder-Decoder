import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Caesar extends JFrame
{
    protected static char[] alphabet;
    protected int key = 0;
    protected String keyword = "";
    protected String nameIn;
    protected String nameOut;

    private JTextArea inputTextArea;
    private JTextArea outputTextArea;
    private JTextField keyField;
    private JTextField keywordField;
    private JComboBox<String> cipherTypeCombo;
    private JButton encryptButton;
    private JButton decryptButton;
    private JButton loadFileButton;
    private JButton saveFileButton;
    private JButton clearButton;
    private JFileChooser fileChooser;

    public Caesar()
    {
        alphabet = createAlphabet();
        setTitle("Шифры Цезаря и Виженера");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        createComponents();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createComponents()
    {
        JPanel topPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        JPanel cipherPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cipherPanel.add(new JLabel("Выберите шифр:"));
        cipherTypeCombo = new JComboBox<>(new String[]{"Шифр Цезаря", "Шифр Виженера"});
        cipherTypeCombo.addActionListener(e -> updateInputFields());
        cipherPanel.add(cipherTypeCombo);

        //ключ для шифра Цезаря
        JPanel caesarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        caesarPanel.add(new JLabel("Ключ сдвига (0-114):"));
        keyField = new JTextField(5);
        keyField.setText("0");
        caesarPanel.add(keyField);

        //ключевое слово для шифра Виженера
        JPanel vigenerePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        vigenerePanel.add(new JLabel("Ключевое слово:"));
        keywordField = new JTextField(15);
        keywordField.setText("ключ");
        keywordField.setEnabled(false);
        vigenerePanel.add(keywordField);

        topPanel.add(cipherPanel);
        topPanel.add(caesarPanel);
        topPanel.add(vigenerePanel);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        loadFileButton = new JButton("Загрузить файл");
        saveFileButton = new JButton("Сохранить результат");
        encryptButton = new JButton("Зашифровать");
        decryptButton = new JButton("Расшифровать");
        clearButton = new JButton("Очистить");

        buttonPanel.add(loadFileButton);
        buttonPanel.add(saveFileButton);
        buttonPanel.add(encryptButton);
        buttonPanel.add(decryptButton);
        buttonPanel.add(clearButton);

        inputTextArea = new JTextArea(15, 40);
        inputTextArea.setLineWrap(true);
        inputTextArea.setWrapStyleWord(true);
        JScrollPane inputScrollPane = new JScrollPane(inputTextArea);
        inputScrollPane.setBorder(BorderFactory.createTitledBorder("Исходный текст"));

        outputTextArea = new JTextArea(15, 40);
        outputTextArea.setLineWrap(true);
        outputTextArea.setWrapStyleWord(true);
        outputTextArea.setEditable(false);
        JScrollPane outputScrollPane = new JScrollPane(outputTextArea);
        outputScrollPane.setBorder(BorderFactory.createTitledBorder("Результат"));

        JPanel textPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        textPanel.add(inputScrollPane);
        textPanel.add(outputScrollPane);

        add(topPanel, BorderLayout.NORTH);
        add(textPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        fileChooser = new JFileChooser();
        addEventHandlers();
    }

    private void updateInputFields() {
        boolean isCaesar = cipherTypeCombo.getSelectedIndex() == 0;
        keyField.setEnabled(isCaesar);
        keywordField.setEnabled(!isCaesar);
    }

    private void addEventHandlers()
    {
        encryptButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                processText(true);
            }
        });

        decryptButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                processText(false);
            }
        });

        loadFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                loadFile();
            }
        });

        saveFileButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFile();
            }
        });

        clearButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputTextArea.setText("");
                outputTextArea.setText("");
            }
        });
    }

    private void processText(boolean encrypt)
    {
        String inputText = inputTextArea.getText();
        if (inputText.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Введите текст или загрузите файл!",
                    "Предупреждение",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean isCaesar = cipherTypeCombo.getSelectedIndex() == 0;

        try {
            String result;
            //шифр Цезаря
            if (isCaesar) {
                int shiftKey = Integer.parseInt(keyField.getText());

                if (shiftKey < 0 || shiftKey > 114) {
                    JOptionPane.showMessageDialog(this,
                            "Ключ должен быть в диапазоне от 0 до 114!",
                            "Ошибка",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                this.key = shiftKey;

                if (encrypt) {
                    result = shifrovanieText(inputText);
                } else {
                    result = deshifrovanieText(inputText);
                }
            } else {
                //шифр Виженера
                keyword = keywordField.getText().trim();

                if (keyword.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Введите ключевое слово для шифра Виженера!",
                            "Ошибка",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                //проверка ключевого слова на алфавит
                for (char c : keyword.toCharArray()) {
                    if (findCharIndex(c) == -1) {
                        JOptionPane.showMessageDialog(this,
                                "Ключевое слово должно содержать только символы русского алфавита!",
                                "Ошибка",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                if (encrypt) {
                    result = vigenereEncrypt(inputText);
                } else {
                    result = vigenereDecrypt(inputText);
                }
            }

            outputTextArea.setText(result);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Введите корректное число для ключа!",
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    //шифрование Цезаря
    public String shifrovanieText(String text)
    {
        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray())
        {
            int index = findCharIndex(c);
            if (index != -1) {
                int newIndex = (index + key) % alphabet.length;
                result.append(alphabet[newIndex]);
            } else {
                //если нет в алфавите - без изменений
                result.append(c);
            }
        }
        return result.toString();
    }
    //Дешифрование Цезаря
    public String deshifrovanieText(String text)
    {
        StringBuilder result = new StringBuilder();

        for (char c : text.toCharArray()) {
            int index = findCharIndex(c);
            if (index != -1)
            {
                int newIndex = (index - key) % alphabet.length;
                if (newIndex < 0) {
                    newIndex += alphabet.length;
                }
                result.append(alphabet[newIndex]);
            } else {
                result.append(c);
            }
        }

        return result.toString();
    }

    //шифрование Виженера
    public String vigenereEncrypt(String text) {
        StringBuilder result = new StringBuilder();
        int keywordLength = keyword.length();
        int keyIndex = 0;

        for (char c : text.toCharArray()) {
            int charIndex = findCharIndex(c);

            if (charIndex != -1) {
                // Получаем символ ключа
                char keyChar = keyword.charAt(keyIndex % keywordLength);
                int keyCharIndex = findCharIndex(keyChar);

                // Шифрование: (charIndex + keyCharIndex) mod alphabet.length
                int newIndex = (charIndex + keyCharIndex) % alphabet.length;
                result.append(alphabet[newIndex]);

                keyIndex++;
            } else {
                // Если символ не из алфавита, оставляем без изменений
                result.append(c);
            }
        }

        return result.toString();
    }

    //дешифрование Виженера
    public String vigenereDecrypt(String text) {
        StringBuilder result = new StringBuilder();
        int keywordLength = keyword.length();
        int keyIndex = 0;

        for (char c : text.toCharArray()) {
            int charIndex = findCharIndex(c);

            if (charIndex != -1) {
                // Получаем символ ключа
                char keyChar = keyword.charAt(keyIndex % keywordLength);
                int keyCharIndex = findCharIndex(keyChar);

                int newIndex = (charIndex - keyCharIndex + alphabet.length) % alphabet.length;
                if (newIndex < 0) {
                    newIndex += alphabet.length;
                }
                result.append(alphabet[newIndex]);

                keyIndex++;
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    private int findCharIndex(char c)
    {
        for (int i = 0; i < alphabet.length; i++) {
            if (alphabet[i] == c) {
                return i;
            }
        }
        return -1;
    }

    public static char[] createAlphabet()
    {
        char[] alphabet = new char[167];
        alphabet[0] = 'а';
        alphabet[1] = 'б';
        alphabet[2] = 'в';
        alphabet[3] = 'г';
        alphabet[4] = 'д';
        alphabet[5] = 'е';
        alphabet[6] = 'ё';
        alphabet[7] = 'ж';
        alphabet[8] = 'з';
        alphabet[9] = 'и';
        alphabet[10] = 'й';
        alphabet[11] = 'к';
        alphabet[12] = 'л';
        alphabet[13] = 'м';
        alphabet[14] = 'н';
        alphabet[15] = 'о';
        alphabet[16] = 'п';
        alphabet[17] = 'р';
        alphabet[18] = 'с';
        alphabet[19] = 'т';
        alphabet[20] = 'у';
        alphabet[21] = 'ф';
        alphabet[22] = 'х';
        alphabet[23] = 'ц';
        alphabet[24] = 'ч';
        alphabet[25] = 'ш';
        alphabet[26] = 'щ';
        alphabet[27] = 'ъ';
        alphabet[28] = 'ы';
        alphabet[29] = 'ь';
        alphabet[30] = 'э';
        alphabet[31] = 'ю';
        alphabet[32] = 'я';
        alphabet[33] = 'А';
        alphabet[34] = 'Б';
        alphabet[35] = 'В';
        alphabet[36] = 'Г';
        alphabet[37] = 'Д';
        alphabet[38] = 'Е';
        alphabet[39] = 'Ё';
        alphabet[40] = 'Ж';
        alphabet[41] = 'З';
        alphabet[42] = 'И';
        alphabet[43] = 'Й';
        alphabet[44] = 'К';
        alphabet[45] = 'Л';
        alphabet[46] = 'М';
        alphabet[47] = 'Н';
        alphabet[48] = 'О';
        alphabet[49] = 'П';
        alphabet[50] = 'Р';
        alphabet[51] = 'С';
        alphabet[52] = 'Т';
        alphabet[53] = 'У';
        alphabet[54] = 'Ф';
        alphabet[55] = 'Х';
        alphabet[56] = 'Ц';
        alphabet[57] = 'Ч';
        alphabet[58] = 'Ш';
        alphabet[59] = 'Щ';
        alphabet[60] = 'Ъ';
        alphabet[61] = 'Ы';
        alphabet[62] = 'Ь';
        alphabet[63] = 'Э';
        alphabet[64] = 'Ю';
        alphabet[65] = 'Я';
        alphabet[66] = '0';
        alphabet[67] = '1';
        alphabet[68] = '2';
        alphabet[69] = '3';
        alphabet[70] = '4';
        alphabet[71] = '5';
        alphabet[72] = '6';
        alphabet[73] = '7';
        alphabet[74] = '8';
        alphabet[75] = '9';
        alphabet[76] = ' ';
        alphabet[77] = '!';
        alphabet[78] = '"';
        alphabet[79] = '#';
        alphabet[80] = '$';
        alphabet[81] = '%';
        alphabet[82] = '&';
        alphabet[83] = '\'';
        alphabet[84] = '(';
        alphabet[85] = ')';
        alphabet[86] = '*';
        alphabet[87] = '+';
        alphabet[88] = ',';
        alphabet[89] = '-';
        alphabet[90] = '.';
        alphabet[91] = '/';
        alphabet[92] = ':';
        alphabet[93] = ';';
        alphabet[94] = '<';
        alphabet[95] = '=';
        alphabet[96] = '>';
        alphabet[97] = '?';
        alphabet[98] = '@';
        alphabet[99] = '[';
        alphabet[100] = '\\';
        alphabet[101] = ']';
        alphabet[102] = '^';
        alphabet[103] = '_';
        alphabet[104] = '`';
        alphabet[105] = '{';
        alphabet[106] = '|';
        alphabet[107] = '}';
        alphabet[108] = '~';
        alphabet[109] = '№';
        alphabet[110] = '\n';
        alphabet[111] = '«';
        alphabet[112] = '»';
        alphabet[113] = '—';
        alphabet[114] = '…';
        alphabet[115] = 'A';
        alphabet[116] = 'B';
        alphabet[117] = 'C';
        alphabet[118] = 'D';
        alphabet[119] = 'E';
        alphabet[120] = 'F';
        alphabet[121] = 'G';
        alphabet[122] = 'H';
        alphabet[123] = 'I';
        alphabet[124] = 'J';
        alphabet[125] = 'K';
        alphabet[126] = 'L';
        alphabet[127] = 'M';
        alphabet[128] = 'N';
        alphabet[129] = 'O';
        alphabet[130] = 'P';
        alphabet[131] = 'Q';
        alphabet[132] = 'R';
        alphabet[133] = 'S';
        alphabet[134] = 'T';
        alphabet[135] = 'U';
        alphabet[136] = 'V';
        alphabet[137] = 'W';
        alphabet[138] = 'X';
        alphabet[139] = 'Y';
        alphabet[140] = 'Z';
        alphabet[141] = 'a';
        alphabet[142] = 'b';
        alphabet[143] = 'c';
        alphabet[144] = 'd';
        alphabet[145] = 'e';
        alphabet[146] = 'f';
        alphabet[147] = 'g';
        alphabet[148] = 'h';
        alphabet[149] = 'i';
        alphabet[150] = 'j';
        alphabet[151] = 'k';
        alphabet[152] = 'l';
        alphabet[153] = 'm';
        alphabet[154] = 'n';
        alphabet[155] = 'o';
        alphabet[156] = 'p';
        alphabet[157] = 'q';
        alphabet[158] = 'r';
        alphabet[159] = 's';
        alphabet[160] = 't';
        alphabet[161] = 'u';
        alphabet[162] = 'v';
        alphabet[163] = 'w';
        alphabet[164] = 'x';
        alphabet[165] = 'y';
        alphabet[166] = 'z';
        return alphabet;
    }

    private void loadFile()
    {
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            nameIn = selectedFile.getAbsolutePath();

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(selectedFile), "UTF-8"))) {
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                String text = content.toString();
                if (text.endsWith("\n")) {
                    text = text.substring(0, text.length() - 1);
                }
                inputTextArea.setText(text);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                        "Ошибка при чтении файла: " + ex.getMessage(),
                        "Ошибка",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveFile()
    {
        String outputText = outputTextArea.getText();
        if (outputText.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Нет результата для сохранения!",
                    "Предупреждение",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int returnValue = fileChooser.showSaveDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            nameOut = selectedFile.getAbsolutePath();

            if (!nameOut.toLowerCase().endsWith(".txt")) {
                nameOut += ".txt";
                selectedFile = new File(nameOut);
            }

            try (BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(selectedFile), "UTF-8"))) {
                writer.write(outputText);
                JOptionPane.showMessageDialog(this,
                        "Файл успешно сохранен: " + selectedFile.getName(),
                        "Успех",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                        "Ошибка при сохранении файла: " + ex.getMessage(),
                        "Ошибка",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                new Caesar();
            }
        });
    }
}