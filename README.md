```markdown
# Шифр Цезаря и Виженера / Caesar and Vigenere Cipher

![Java](https://img.shields.io/badge/Java-17%2B-orange?style=for-the-badge&logo=java)
![Swing](https://img.shields.io/badge/GUI-Swing-blue?style=for-the-badge)

## О проекте / About

**Русский**  
Десктопное приложение с графическим интерфейсом для шифрования и дешифрования текста с помощью шифров Цезаря и Виженера. Программа поддерживает полный набор символов кириллицы, латиницы, цифры и специальные знаки. Разработана на чистой Java с использованием библиотеки Swing.

**English**  
A desktop application with a graphical interface for encrypting and decrypting text using Caesar and Vigenere ciphers. The program supports a full set of Cyrillic, Latin, numbers, and special characters. Developed in pure Java using the Swing library.

## Возможности / Features

### Основные / Core
| Функция | Описание |
|---------|----------|
| **Шифрование** | Преобразование текста с заданным ключом (сдвиг для Цезаря, ключевое слово для Виженера) |
| **Дешифрование** | Восстановление исходного текста |
| **Загрузка файлов** | Открытие текстовых файлов для обработки |
| **Сохранение результатов** | Экспорт в текстовый файл |
| **Выбор шифра** | Переключение между шифром Цезаря и шифром Виженера |

### Поддерживаемые символы / Supported characters
<details>
<summary>Полный список / Full list (167 символов)</summary>

| Категория | Символы |
|-----------|---------|
| **Строчные русские** | а б в г д е ё ж з и й к л м н о п р с т у ф х ц ч ш щ ъ ы ь э ю я |
| **Прописные русские** | А Б В Г Д Е Ё Ж З И Й К Л М Н О П Р С Т У Ф Х Ц Ч Ш Щ Ъ Ы Ь Э Ю Я |
| **Строчные латинские** | a b c d e f g h i j k l m n o p q r s t u v w x y z |
| **Прописные латинские** | A B C D E F G H I J K L M N O P Q R S T U V W X Y Z |
| **Цифры** | 0 1 2 3 4 5 6 7 8 9 |
| **Знаки препинания и спецсимволы** | . , ! ? : ; - — … « » ( ) [ ] { } @ # $ % ^ & * + = \\ / ~ ` № \| " ' _ |
| **Дополнительно** | пробел, перевод строки, кавычки |

</details>

### Интерфейс / Interface
- Две текстовые области (ввод/вывод)
- Выбор типа шифра (Цезарь / Виженер)
- Поле ввода ключа сдвига (0–114) для шифра Цезаря
- Поле ввода ключевого слова для шифра Виженера
- Удобная навигация с помощью кнопок
- Поддержка UTF-8 для корректного отображения символов

## Быстрый старт / Quick Start

### 📋 Требования / Requirements
- Java Runtime Environment (JRE) 17 или выше
- Любая ОС: Windows / Linux / macOS

### ⚙️ Установка и запуск / Installation & Launch

```bash
# Клонировать репозиторий / Clone repository
git clone https://github.com/Daniil-Rybin/Caesar-Cipher-Encoder-Decoder.git

# Перейти в директорию / Navigate to directory
cd Caesar-Cipher-Encoder-Decoder

# Скомпилировать / Compile
javac -encoding UTF-8 Caesar.java

# Запустить / Run
java Caesar
```

## Инструкция по использованию / Usage Guide

### 🇷🇺 Русский
1. **Запустите программу**
2. **Выберите шифр**: Цезаря или Виженера
3. **Введите текст** в левое поле или нажмите "Загрузить файл"
4. **Укажите ключ**:
   - Для Цезаря: число от 0 до 114
   - Для Виженера: ключевое слово (только буквы)
5. **Выберите действие:**
   - Нажмите "Зашифровать" для кодирования
   - Нажмите "Расшифровать" для декодирования
6. **Результат** появится в правом поле
7. **Сохраните результат** кнопкой "Сохранить результат"

### 🇬🇧 English
1. **Launch the application**
2. **Select cipher**: Caesar or Vigenere
3. **Enter text** in the left field or click "Load file"
4. **Specify the key**:
   - For Caesar: number from 0 to 114
   - For Vigenere: keyword (letters only)
5. **Choose action:**
   - Click "Encrypt" to encode
   - Click "Decrypt" to decode
6. **Result** appears in the right field
7. **Save result** with "Save result" button

## Примеры работы / Examples

### Шифр Цезаря / Caesar Cipher

#### Пример 1: Шифрование / Encryption
```
Исходный текст:  Привет, мир!
Ключ:            3
Результат:       Тулезх, плу!
```

#### Пример 2: Дешифрование / Decryption
```
Исходный текст:  Тулезх, плу!
Ключ:            3
Результат:       Привет, мир!
```

#### Пример 3: С цифрами и латиницей / With numbers and Latin
```
Исходный текст:  Hello 2024!
Ключ:            5
Результат:       Mjqqt 7579&
```

### Шифр Виженера / Vigenere Cipher

#### Пример 1: Шифрование / Encryption
```
Исходный текст:    Привет, мир!
Ключевое слово:    ключ
Результат:         Чщкйэ, эшц!
```

#### Пример 2: Дешифрование / Decryption
```
Исходный текст:    Чщкйэ, эшц!
Ключевое слово:    ключ
Результат:         Привет, мир!
```

#### Пример 3: С латиницей / With Latin
```
Исходный текст:    Hello World!
Ключевое слово:    key
Результат:         Rovvy Gybvn!
```

## Архитектура / Architecture

```
Caesar (JFrame)
├── Поля класса
│   ├── alphabet[] - алфавит символов (167 символов: кириллица, латиница, цифры, спецсимволы)
│   ├── key - ключ сдвига (для Цезаря)
│   ├── keyword - ключевое слово (для Виженера)
│   └── nameIn/nameOut - имена файлов
├── GUI компоненты
│   ├── JTextArea (ввод/вывод)
│   ├── JTextField (ключ сдвига / ключевое слово)
│   ├── JComboBox (выбор шифра)
│   └── JButton (кнопки управления)
├── Методы шифрования
│   ├── shifrovanieText() / deshifrovanieText() - для Цезаря
│   ├── vigenereEncrypt() / vigenereDecrypt() - для Виженера
│   └── findCharIndex() - поиск символа в алфавите
└── Работа с файлами
    ├── loadFile()
    └── saveFile()
```

## Алгоритм работы / Algorithm

### Шифр Цезаря / Caesar Cipher
**Шифрование / Encryption:**  
`новый_индекс = (старый_индекс + ключ) % размер_алфавита`

**Дешифрование / Decryption:**  
`новый_индекс = (старый_индекс - ключ) % размер_алфавита`  
*(если индекс < 0: индекс += размер_алфавита)*

### Шифр Виженера / Vigenere Cipher
**Шифрование / Encryption:**  
`C_i = (P_i + K_j) mod N`  
где `C_i` — зашифрованный символ, `P_i` — исходный символ, `K_j` — символ ключа, `N` — размер алфавита

**Дешифрование / Decryption:**  
`P_i = (C_i - K_j + N) mod N`

## Обработка ошибок / Error Handling

| Ситуация | Действие |
|----------|----------|
| Пустой текст | Предупреждение пользователю |
| Неверный ключ для Цезаря (>114 или <0) | Сообщение об ошибке |
| Пустое ключевое слово для Виженера | Сообщение об ошибке |
| Недопустимые символы в ключевом слове | Сообщение об ошибке |
| Неверный формат ключа | Ошибка ввода |
| Ошибка чтения файла | Сообщение с деталями |
| Ошибка сохранения | Уведомление об ошибке |

## Технологии / Technologies

- **Java 17+** - основной язык программирования
- **Swing** - графический интерфейс
- **UTF-8** - кодировка символов
- **JFileChooser** - диалоги выбора файлов

## Структура репозитория / Repository Structure

```
Caesar-Cipher-Encoder-Decoder/
├── Caesar.java              # Исходный код приложения
├── README.md                # Документация
└── examples/                # Примеры файлов для тестирования
```

## Вклад в проект / Contributing

1. Форкните репозиторий / Fork the repository
2. Создайте ветку / Create a branch (`git checkout -b feature/amazing`)
3. Зафиксируйте изменения / Commit changes (`git commit -m 'Add amazing feature'`)
4. Отправьте изменения / Push to branch (`git push origin feature/amazing`)
5. Откройте Pull Request / Open a Pull Request

## Контакты / Contact

- **Автор** - Daniil Rybin
- **Email** - danya.danya.rus31@gmail.com
- **GitHub** - [@Daniil-Rybin](https://github.com/Daniil-Rybin)

## Поддержка проекта / Support

Если вам понравился проект, поставьте звездочку на GitHub! Это очень мотивирует :)

If you like this project, please give it a star on GitHub! It really motivates :)
