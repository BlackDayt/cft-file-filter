# CFT File Filter / Фильтрация строк по типам

---

## RU

Консольная утилита читает строки из входных файлов и раскладывает их по трём выходным файлам:

- `integers.txt` — целые числа
- `floats.txt` — числа с плавающей точкой
- `strings.txt` — строки

Файлы создаются только если в данных встретился соответствующий тип.

### Требования

- Java 17+
- Gradle 9.1.0

### Troubleshooting (Java)

Для запуска утилиты требуется установленная Java (JDK/JRE) версии 17 или выше.

Проверь, что Java доступна в системе:

```bash
java -version
```

Если команда не найдена или Gradle сообщает `JAVA_HOME is not set`, необходимо:
- установить JDK (например, Temurin JDK 17),
- убедиться, что `java` доступна в `PATH`,
- либо что переменная окружения `JAVA_HOME` указывает на каталог установки Java.

### Сборка (fat-jar)

```
./gradlew clean shadowJar
```

#### Результат сборки:
`build/libs/cft-file-filter-1.0.0-all.jar`

### Запуск

```
java -jar build/libs/cft-file-filter-1.0.0-all.jar [options] <inputFiles...>
```

### Опции

- `-o <dir>` — директория для выходных файлов (по умолчанию текущая)
- `-p <prefix>` — префикс имён выходных файлов (по умолчанию пустой)
- `-a` — дописывать в существующие файлы (append)
- `-s` — краткая статистика (только количество)
- `-f` — полная статистика (min / max / sum / avg для чисел, min / max длины для строк)

### Пример

```
java -jar build/libs/cft-file-filter-1.0.0-all.jar -s -a -o out -p result_ in1.txt in2.txt
```

Выходные файлы (создаются только при наличии данных):

- `out/result_integers.txt`
- `out/result_floats.txt`
- `out/result_strings.txt`

### Примечания

- Входные файлы обрабатываются в порядке перечисления в командной строке
- Строки читаются построчно и записываются без изменения содержимого
- При ошибке чтения одного входного файла обработка продолжается со следующими
- Кодировка ввода/вывода: UTF-8
- Exit codes: `0` успех, `1` ошибка выполнения, `2` ошибка аргументов

---

## EN

Console utility reads lines from input files and splits them into three output files:

- `integers.txt` — integers
- `floats.txt` — floating-point numbers
- `strings.txt` — strings

Files are created only if the corresponding type is present in the input data.

### Requirements

- Java 17+
- Gradle 9.1.0

### Troubleshooting (Java)

Java (JDK/JRE) version 17 or higher is required to run the application.

Verify that Java is available on your system:

```bash
java -version
```

If the command is not found or Gradle reports `JAVA_HOME is not set`, please:
- install a JDK (for example, Temurin JDK 17),
- ensure that `java` is available in `PATH`,
- or that the `JAVA_HOME` environment variable points to the Java installation directory.

### Build (fat-jar)
```
./gradlew clean shadowJar
```
#### Artifact:

`build/libs/cft-file-filter-1.0.0-all.jar`

### Run
```
java -jar build/libs/cft-file-filter-1.0.0-all.jar [options] <inputFiles...>
```
### Options

- `-o <dir>` — output directory (default: current directory)
- `-p <prefix>` — output file name prefix (default: empty)
- `-a` — append to existing output files
- `-s` — print short statistics (counts only)
- `-f` — print full statistics (min / max / sum / avg for numbers, min / max length for strings)

### Example
```
java -jar build/libs/cft-file-filter-1.0.0-all.jar -s -a -o out -p result_ in1.txt in2.txt
```
Output files (created only if data exists):

- `out/result_integers.txt`
- `out/result_floats.txt`
- `out/result_strings.txt`

### Notes

- Input files are processed in the order specified in the command line
- Lines are read line-by-line and written as-is
- If one input file cannot be read, processing continues with the remaining files
- I/O encoding: UTF-8
- Exit codes: `0` success, `1` runtime error, `2` invalid arguments