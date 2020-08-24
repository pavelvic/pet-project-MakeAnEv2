Что это
=====================
Это мой pet-project на Java EE. 
Это веб-сайт для организации частных мероприятий. На сайте можно зарегистрироваться, поэтому каждый пользователь имеет свой профиль, который может редактировать. Некоторые пользователи наделены правами создавать мероприятия и управлять карточками других пользователей (привилегированные пользователи).
Обычные пользователи могут искать мероприятия, просматривать их, регистрироваться для участия (или отказываться от участия).
Информация о пользователях и мероприятиях хранится в базе данных.
Имеется лаконичный пользовательский интерфейс.

Технологии
-----------------------------------
* Веб Java EE: Servlets (JSP) + JSTL;
* БД: MySQL 8.0 + JDBC;
* Веб-сервер: Tomcat 9;
* CSS: Bootstrap 4;
* Тесты: JUnit 4.12;
* Сборка: Maven;
* VCS: Git.

Возможности
-----------------------------------
#### Управление пользователями:
* Форма регистрации пользователя;
* Хранение и актуализация данных о пользователях в БД MySQL;
* Аутентификация и авторизация пользователей;
* Функция "Запомнить меня" при авторизации;
* Верификация полей при регистрации / редактировании пользователей (проверка на уникальность, проверка надежности пароля, проверка имени пользователя на соответствие требованиям);
* Реализация модели полномочий в соответствии с ролевой моделью;
#### Управление мероприятиями:
* Создание мероприятий (для привилегированных пользователей);
* Поиск мероприятий по ключевым параметрам (даты, описание, статусы);
* Просмотр карточки мероприятия и его параметров (что? где? когда?);
* Возможность работы в разных таймзонах;
* Регистрация в мероприятии для участия и отказ от участия;
* Формирование списков участников;
* Просмотр своих мероприятий (как участник и/или как организатор);
* Система статусов для мероприятий (Панируется, Запланировано, Отменено) и связанные ограничения на регистрацию в мероприятиях;
* Возможность открывать и закрывать регистрацию на мероприятие.


Ролевая модель
-----------------------------------
#### Роли "Менеджер" и "Пользователь", базовые привилегии (досутпны всем не заблокированным пользователям):
* смотреть общие страницы сайта;
* смотреть / редактировать данные о себе (кроме ключевых полей: username, tel, e-mail);
* изменять свой пароль;
* удалять своего пользователя;
* искать мероприятия (по датам, по автору, по статусу, по текстовым полям);
* регистрироваться для участия в мероприятии (или отказываться от участия);
* смотреть список мероприятий, в которых пользователь участвует;
#### Роль "Администратор", дополнительные привилегии к базовым:
* смотреть список других пользователей;
* смотреть персональные данные любого пользователя;
* редактировать не ключевые поля любого пользователя;
* менять права доступа другим пользователям в рамках ролей;
* создавать мероприятия;
#### Роль "Суперпользователь", дополнительные привилегии к админским:
* смотреть список других пользователей;
* смотреть / корректировать любые персональные данные любого пользователя (все поля);
* изменять / сбрасывать (на дефолтное значение) пароль любого пользователя;
* менять права доступа другим пользователям в рамках ролей;
* удалять любого пользователя;
#### Роль "Заблокирован":
* привилегий нет;
* не может входить в систему и пользоваться сайтом.

Структура
-----------------------------------
`src/main/java`
* `com.mycompany.makeanev2.User` класс для манипуляциями с пользователями
* `com.mycompany.makeanev2.UserGroup` класс для манипуляциями с группами полномочий
* `com.mycompany.makeanev2.Event` класс для работы с мероприятиями
* `com.mycompany.makeanev2.EventStatus` класс для работы со статусами мероприятия (Планируется, Запланировано, Отменено)
* `com.mycompany.makeanev2.EventRegStatus` класс для работы со статусами регистрации (Открыта, Закрыта)
* `com.mycompany.makeanev2.Participant` класс для манипуляции с участниками мероприятия
* `com.mycompany.makeanev2.ParticipantStatus` класс для работы со статусами участников (Основной, Запасной)
* `com.mycompany.makeanev2.WeekOfMonth` вспомогательный класс для формирования календаря на месяц из объектов LocalDate (используется для календарня на главной странице)
* `com.mycompany.makeanev2.Exceptions` пакет с классами для обработки собственных исключений приложения (UserException, EventException, ParticipantException, SearchException);
* `com.mycompany.makeanev2.Filters.User` пакет с классами Servlet-фильтров, выполняющих необходимые проверки на наличие полномочий для доступа к отдельным веб-страницам, а также управляет выдачей веб-страниц в зависимости от полномочий пользователей
* `com.mycompany.makeanev2.Filters.Event` пакет с классами Servlet-фильтров, реализующих диспетчеризацию вызовов веб-страниц в зависимости от роли пользователя
* `com.mycompany.makeanev2.Servlets.User` классы сервлетов, формирующие данные о пользователях и передающие их на веб-страницы;
* `com.mycompany.makeanev2.Servlets.Event` классы сервлетов, формирующие данные о мероприятиях и передающие их на веб-страницы;
* `com.mycompany.makeanev2.MakeAnEv2ContextListener` класс с контекстом приложения (используется для определения таймзоны для приложения)
* `com.mycompany.makeanev2.Utils` классы вспомогательных утилит для работы с базой данных (соединение + запросы к БД), классы для проверки полномочий на доступ (CheckPermission), для работы с http-сессиями и пользователями при реализации механизма авторизации (AuthUtil), для работы с внутренним календарем (CalendarUtils).

`src/test/java`
* `com.mycompany.makeanev2` тесты классов с бизнес-логикой (User, UserGroup, Event, EventRegStatus, EventStatus, Participant, ParticipantStatus, WeekOfMonth);
* `com.mycompany.makeanev2.Exceptions` тесты для классов внутренних исключений приложения (UserException);
* `com.mycompany.makeanev2.Utils` тесты механизма проверки полномочий и календаря (CheckPermission, CalendarUtils);

`src/main/webapp/` (View)
* `WEB-INF` общие веб-страницы для всех пользователей (регистрация, редактирование пароля, страница результата действия, главная, подменю для поиска, модули подключения CSS и JS);
* `WEB-INF/adminview` веб-страницы для Администратора
* `WEB-INF/managerview` веб-страницы для Менеджера
* `WEB-INF/ownerview` веб-страницы для Суперпользователя
* `WEB-INF/userview` веб-страницы для Пользователя

База данных
-----------------------------------
![database_schema](https://j6aqqg.db.files.1drv.com/y4mJidPyp8fL8uj-XIDsQhhJ0KirtFELCS7VwuNrO06mJq1LNEaaACtApjOZkNHSnQRZJZUoDIQdsusKr79i1AuGG740BVgW4s1q6O8KzWFgYfDXYDIQ0uRjTwdIkh67lJ-TmcjEjNdRo4ywVGt0A4YyKyo8kDMSOj0Z2jEj0-iKzb85UvZo38v4F62qVn6Xc75N4j1WCqZwv4esq8oouJuJg?width=760&height=659&cropmode=none)

[Скрипт](https://github.com/pavelvic/pet-project-MakeAnEv2/blob/master/_db-schema/script.sql)


Тесты
-----------------------------------
Покрытие бизнес-логики >80%
![tests](https://gka5qg.db.files.1drv.com/y4m3pK28iwC8IpmD01jexLksf6Y8Db2-SmQZptycJxxXSosOdeXlVVyj91vZl2W8X88riIIoiEuadGpqCa3r5ncpQR7AM3y4TFzRvu08_SwAGGUcAX4jB3ZEkSeQRwICiCRxNmOPDo6cx_DwhHmyDpRojneqrvnuPNsGGUZcGGHqgAyV8-ipigPKDh-ZDqJweTSAvDYGa3B5PWIh8p0F9Rbqg?width=838&height=304&cropmode=none)

Скриншоты
-----------------------------------
### Главная
![main_page](https://j6a9qg.db.files.1drv.com/y4mf2nM2BTlW-FIgYc6NQfR6jEkjjZ1UDk11-UQNJj_AcDQdS-F_hrW4nWHQTnXzMm1uJzu_-8BBA4EnESonplzaTZ-kmaqpzWbzx1DIR96s7FmwZ4pxGMs98kj1i1oYuEvsZfoY0V-XxhEZK8AsgTcnzmbj_k9rUxlNXql-iQNrecm3YVBqPTk_AO2oqdplMQfUt0TEiEc8HOFoMvMCboKXg?width=1521&height=976&cropmode=none)

### Логин
![login_page](https://gaa9qg.db.files.1drv.com/y4mmnYYF9LKxV2WBDWBvN_Taulc_fqVmnoRWC-oyeddQYF2HxA-kiv4Dxly6WH7yM8WgGvLkrp1R3IGqV2f1LIF0MbZQiBpbaixeVwBS3VvvCZ290hxlytTmWkdZ15LG2NUTL2bpRlYw2kuRjl4vV4xTJ-wT7uuzX-qlVkuud8G1pBgljgRr6uo1__CchB-20oXgvUgBuchp9mRcpm7YYEJqw?width=636&height=423&cropmode=none)

### Профиль
![card_page](https://gaa8qg.db.files.1drv.com/y4msZaFAQdS67Am5-SCuY_dqBOsTWFELjpBJ9gtmWPCQk8izjzFAlYHfKHSlmF7nogbXEf43rnVY192k7ltNZSfwWBS4Nl8PgqH0n0I-tP63HT6bl2oTuNUujnsV_99mHXerDnZxoS3MU4vgVVr-BUm-hpHmuOVC5w9YHnFBOZ47vHC5Et-QJwQcfhdYshjHg4M985CJHNwVGJVdAFyTlSJhA?width=1005&height=429&cropmode=none)

### Регистрация
![register_page](https://gaaqqg.db.files.1drv.com/y4m_PUy_GmQ9qpDPxdWzsFWlWC59_4YB5v_lkGwEkhMBqQSl4nX7xDlnpokz0PSvqHnE4oEbEjSbuZuPta1e_430NTXULOTcVTAlw0JLhgX_2yUXfVOpMfOTp4KfShBOoaTAuv9jtOVQObwW_sA1mgrGnEq_o1udQYnsbe160HOL_gYVEqBrZRztXnb9-4WYqxtcxX0WMIUIiV9LNNxczyTfw?width=489&height=710&cropmode=none)

### Список пользователей
![userlist_page](https://gabcqg.db.files.1drv.com/y4mqjwMeWFqLpZYXK40M6qvHc6hPj8DZnqGyx_nPcIC9iXKX28-ZVBE7gw1COY-M9ZrWthyeeFp0ArFB3nM7RN8weHAM2zHFWsmWs_4qh99fIyy1rk8N3rdm8FQlfrBNm6dXy65xW1KSlSxFHPf4T7MZp8X_EL0WcZSRr_e9s_ZngOwHm4nyIupMv5EVmP7vQGc2inUqc4Ir0E3cU2f2ZXCFg?width=1013&height=511&cropmode=none)

### Редактирование профиля
![edit_page](https://gaa4qg.db.files.1drv.com/y4mxj-SimW3F_CozyDeju9JV224zsogzux88mAunVfMYYQ6Sso40s-HosAHTlxAr9Elhq7ho7BLgNRUX6eEWSgTQbIGweobuEDn6fZ_0nZqKO5oOgzQAmtlWKZSLk7Kzt76MQNOMh7EssthNpCsiy-FGvR2FTynBdH46aw2Z8lbBDwyhrd5bgb4euMsZh18EDGYvRqf9S6cXGKq_iwFaAlODg?width=1004&height=861&cropmode=none)

### Создание мероприятия
![createEvent_page](https://j6bbqg.db.files.1drv.com/y4m4K7bK0yMlk6eFOq4pHZ8_debgE-_KwUEG9s0HQJTFW27G2sS9FPcwtT9QScfDWIGtkiD593fJU8a_8bi8purJbC2B5G12ztcG36LI4_afyLxk8UPX73BPYpG99ITevn99lQNeZ0amKUk2shDlC4VkAI2BNy0FikKhXw2e1naitEQQ-FK_b22vChdnI_DQ68szKvCGNLAONfULdSaEjY6-A?width=724&height=864&cropmode=none)

### Просмотр мероприятия
![event_page](https://j6bcqg.db.files.1drv.com/y4mZny-KxTiw6xlRuSoSh0xR-Dhj52PgKVhyZU27lLZ_011OA-nDVMOcVePDQMXqeVgTK3ngekZDCp18-T3jxK6CxeZDBaFxGT3J3GkhEkzz7ZP3axab9FsGgMEAz-25IoKKa8ve-F-6ZmYKEGuIBNp6gwpQgGdQ-rFB2d35lx7UbMebeQEbvgkaLcmCy8MIDJZwB6JuQPXF_WWHi4nRILrNA?width=1345&height=859&cropmode=none)

### Список мероприятий
![eventList_page](https://j6apqg.db.files.1drv.com/y4mTjP2tVgE2HkTcLBxoSYmM5WrC1tLT5EBNGTreAdTjAfC-gE1gLvRvC5BWwbA4sZVgGUocsblj-HVUOL60uiZQ5Jh3F3oAP1F9i12uSarzhug6VZfstiqb5kuiH_gA6_vdF0C4giQSxHLaEYGnWE9rjl5NB734wuV3AXvFKhGMjrUCx-Rwd3Lx1d9WWo9ycdmHzouvR0LSVogEky07HHEZw?width=1515&height=970&cropmode=none)

### Поиск мероприятия + календарь
![searchformAndCalendar_pages](https://j6a8qg.db.files.1drv.com/y4mDD1JHYLxeTdyVwptN6Cmc-XQxxixM9pI9y603AZP9JKiHp60_i4CcOt363eH03nvexkACLkHiWUwJ4GqgAaDobcbjSbXhwgr8h9A8b1jGZuYPWSBIOrSq_0shM_NuPDHvwR2Q70U75GwsfnLcDU4C3j28JiJvpsE8bsH00_doZbuSNqysx3MEe2vCQApoWooGobxVdI54g6B5xwl8etzkw?width=1481&height=514&cropmode=none)