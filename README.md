### Задание
Случилось то, что обычно случается ближе к дедлайну: никто ничего не успевает и винит во всём остальных.

Разработчикам особо не до вас, им ведь нужно пилить новые фичи, поэтому они подготовили сборку, работающую с СУБД, и даже приложили схему базы данных (см. файл schema.sql). Но при этом сказали: «Остальное вам нужно сделать самим, там несложно»

Что вам нужно сделать:

1. Внимательно изучить схему.
2. Создать Docker container на базе MySQL 8, прописать создание базы данных, пользователя, пароля.
3. Запустить SUT (app-deadline.jar). Для указания параметров подключения к базе данных можно использовать:
* либо переменные окружения DB_URL, DB_USER, DB_PASS;
* либо указать их через флаги командной строки при запуске: -P:jdbc.url=..., -P:jdbc.user=..., -P:jdbc.password=.... Внимание: при запуске флаги не нужно указывать через запятую. Приложение не использует файл application.properties в качестве конфигурации, конфигурационный файл находится внутри JAR-архива;
* либо можете схитрить и попробовать подобрать значения, зашитые в саму SUT.
А дальше выясняется куча забавных вещей.

Проблема первая: SUT не стартует

Проблема вторая: SUT валится при повторном перезапуске

Проблема третья (опционально): пароли
Если вы решите вдруг генерировать пользователей, чтобы под ними тестировать вход в приложение, то не должны удивляться тому, что в базе данных пароль пользователя хранится в зашифрованном виде.

Попытка его записать туда в открытом виде ни к чему хорошему не приведёт.

Настойчивые требования к разработчикам раскрыть алгоритм генерации пароля тоже ни к чему не привели.

Если вы добрались до этого шага и всё-таки успешно запустили SUT, вы уже герой.

Но теперь выяснилась забавная информация: разработчики фронтенда поругались с разработчиками бэкенда, и вы можете протестировать только вход в систему.

Внимательно посмотрите, как и куда сохраняются коды генерации в СУБД, и напишите тест, который, взяв информацию из БД о сгенерированном коде, позволит вам протестировать вход в систему через веб-интерфейс.

P.S. Неплохо бы ещё проверить, что при трёхкратном неверном вводе пароля система блокируется.

Итого в результате у вас должно получиться:

* docker-compose.yml*,
* app-deadline.jar,
* schema.sql,
* код ваших автотестов.