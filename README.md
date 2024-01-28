# **Название работы: "Утилита фильтрации содержимого файлов"**


## Описание проекта:
При запуске утилиты в командной строке подается несколько файлов, содержащих в перемешку разные типы данных в строках. Задача утилиты записать разные типы данных в разные файлы. По умолчанию файлы с результатами располагаются в текущей папке с именами, на пример: integers.txt, ﬂoats.txt, strings.txt и перезаписваются при повторном вызове. В процессе фильтрации данных производится сбор статистики по каждому типу данных.


## Инструкция по запуску и использованию:
Входящие файлы для фильтрации необходимо расположить в корне проекта. Для примера и запуска работы приложения, в корне проекта уже имеются 2 исходных фала, с которыми будет производиться работа, это: in1.txt и in2.txt

Пример командной строки для запуска утилиты:
     java -jar files-filtering-utility.jar -s -f -a -p r_ in1.txt in2.txt
Файлы будут располагатьсяпо пути: C:\...\files-filtering-utility\src\main\java\home

При использовании опции "-о", пример командной строки:
     java -jar files-filtering-utility.jar -о /some/path in1.txt in2.txt
Файлы будут располагатьсяпо пути: C:/some/path, даже если каталога(ов) для выгрузки не существует, то он/они будут созданы автоматически.

Для просмотра всех доступных опций и их описания необходимо ввести в командную строку:
java -jar files-filtering-utility.jar -h


## Выполнены следующие функциональные требования для проекта:
Из командной строки можно вызывать и реализовывать следующие опции:
- опция "-о"-задает путь для результирующих файлов, пример: -о /some/path
- опция "-р"-задает префикс к имени файла, пример: -р pref_
- опция "-а"-задает режим добавления в существующие файлы
- опция "-s"-выводит в консоль данные по краткой статистике, которая содержит название файла с определенным типом данных и количество элементов записанных в исходящий файл
- опция "-f"-выводит в консоль данные полной статистики, которая помимо краткой статистики дополнительно содержит: для чисел-минимальное и максимальное значения, сумма и среднее; для строк-размер самой короткой строки и самой длинной


## Используемые технологии и библиотеки:
Язык: Java 17

Система сборки: Maven (Version 4.0.0)

Дополнительные библиотеки:
- Apache Commons CLI (Version 1.4)
<dependency>
    <groupId>commons-cli</groupId>
    <artifactId>commons-cli</artifactId>
    <version>1.4</version>
</dependency>
- Lombok (Version 1.18.26)
<dependency>
     <groupId>org.projectlombok</groupId>
     <artifactId>lombok</artifactId>
     <version>1.18.26</version>
</dependency>

Тестирование: JUnit (Version 4.13.2)
<dependency>
     <groupId>junit</groupId>
     <artifactId>junit</artifactId>
     <version>4.13.2</version>
     <scope>test</scope>
     </dependency>