insert into r_forms(id, form_number, name, order_number, status, created_by, created_on,
                    modified_by, modified_on, href, parent_id)
VALUES (100, 100, row ('Форма', 'Форма', 'Forma', 'Form', 'Forma'), 100, 'ACTIVE', null, now(), null, null, null, null);

insert into r_forms(id, form_number, name, order_number,  status, created_by, created_on,
                    modified_by, modified_on, href, parent_id)
VALUES (1, 1,
        row ('Стоимость корма', 'Ем-хашак харажатлари', 'Yem-xashak xarajatlari', 'Feed costs', 'Yem-xashak xarajatlari'),
        1, 'ACTIVE', null, now(), null, null, '/forms/1', 100);

insert into r_forms(id, form_number, name, order_number,  status, created_by, created_on,
                    modified_by, modified_on, href, parent_id)
VALUES (4, 4,
        row ('Стоимость лекарств', 'Дори-дармон харажатлари', 'Dori-darmon xarajatlari', 'Drug costs', 'Dori-darmon xarajatlari'),
        4, 'ACTIVE', null, now(), null, null, '/forms/4', 100);

insert into r_forms(id, form_number, name, order_number,  status, created_by, created_on,
                    modified_by, modified_on, href, parent_id)
VALUES (5, 5,
        row ('Коммунальные расходы', 'Коммунал харажатлар', 'Kommunal xarajatlari', 'Communal costs', 'Kommunal xarajatlari'),
        5, 'ACTIVE', null, now(), null, null, '/forms/5', 100);

insert into r_forms(id, form_number, name, order_number,  status, created_by, created_on,
                    modified_by, modified_on, href, parent_id)
VALUES (3, 3,
        row ('Расходы на питание', 'Озиқ-овқат харажатлари', 'Oziq-ovqat xarajatlari', 'Food costs', 'Oziq-ovqat xarajatlari'),
        3, 'ACTIVE', null, now(), null, null, '/forms/3', 100);

insert into r_forms(id, form_number, name, order_number,  status, created_by, created_on,
                    modified_by, modified_on, href, parent_id)
VALUES (6, 6,
        row ('Транспортные расходы', 'Транспорт харажатлари', 'Transport xarajatlari', 'Transportation costs', 'Transport xarajatlari'),
        6, 'ACTIVE', null, now(), null, null, '/forms/6', 100);

insert into r_forms(id, form_number, name, order_number,  status, created_by, created_on,
                    modified_by, modified_on, href, parent_id)
VALUES (2, 2,
        row ('Зарплата', 'Иш ҳақи', 'Ish haqi', 'Salary', 'Ish haqi'),
        2, 'ACTIVE', null, now(), null, null, '/forms/2', 100);

insert into r_forms(id, form_number, name, order_number,  status, created_by, created_on,
                    modified_by, modified_on, href, parent_id)
VALUES (7, 7,
        row ('Запасная часть', 'Эхтиёт қисм', 'Extiyot qism', 'Spare part', 'Extiyot qism'),
        7, 'ACTIVE', null, now(), null, null, '/forms/7', 100);

insert into r_forms(id, form_number, name, order_number,  status, created_by, created_on,
                    modified_by, modified_on, href, parent_id)
VALUES (8, 8,
        row ('Расходы на топливо', 'Ёқилги харажатлари', 'Yoqilg’i xarajatlari', 'Fuel costs', 'Yoqilg’i xarajatlari'),
        8, 'ACTIVE', null, now(), null, null, '/forms/8', 100);

insert into r_forms(id, form_number, name, order_number,  status, created_by, created_on,
                    modified_by, modified_on, href, parent_id)
VALUES (9, 9,
        row ('Проценты по кредиту', 'Кредит фоизлари', 'Kredit foizlari', 'Percent of credit', 'Kredit foizlari'),
        9, 'ACTIVE', null, now(), null, null, '/forms/9', 100);

insert into r_forms(id, form_number, name, order_number,  status, created_by, created_on,
                    modified_by, modified_on, href, parent_id)
VALUES (10, 10,
        row ('Измерение веса', 'Вазн ўлчаш', 'Vazn o`lchash', 'Weighing', 'Vazn o`lchash'),
        10, 'ACTIVE', null, now(), null, null, '/forms/10', 100);

insert into r_forms(id, form_number, name, order_number,  status, created_by, created_on,
                    modified_by, modified_on, href, parent_id)
VALUES (11, 11,
        row ('Продажа', 'Сотиш','Sotish', 'Selling', 'Sotish'),
        11, 'ACTIVE', null, now(), null, null, '/forms/11', 100);

insert into r_forms(id, form_number, name, order_number,  status, created_by, created_on,
                    modified_by, modified_on, href, parent_id)
VALUES (12, 12,
        row ('Прочие расходы', 'Бошқа харажатлар', 'Boshqa xarajatlar', 'Other costs', 'Boshqa xarajatlar'),
        12, 'ACTIVE', null, now(), null, null, '/forms/12', 100);

insert into r_forms(id, form_number, name, order_number,  status, created_by, created_on,
                    modified_by, modified_on, href, parent_id)
VALUES (13, 13,
        row ('Амортизация', 'Амортизация', 'Amortizatsiya', 'Depreciation', 'Amortizatsiya'),
        13, 'ACTIVE', null, now(), null, null, '/forms/13', 100);

insert into r_forms(id, form_number, name, order_number,  status, created_by, created_on,
                    modified_by, modified_on, href, parent_id)
VALUES (14, 14,
        row ('Банковские сервисы', 'Банк хизматлари', 'Bank xizmatlari', 'Services of bank', 'Bank xizmatlari'),
        14, 'ACTIVE', null, now(), null, null, '/forms/14', 100);

insert into r_forms(id, form_number, name, order_number,  status, created_by, created_on,
                    modified_by, modified_on, href, parent_id)
VALUES (15, 15,
        row ('Затраты на убой', 'Сўйиш харажатлари', 'So`yish xarajatlari', 'Slaughter costs', 'So`yish xarajatlari'),
        15, 'ACTIVE', null, now(), null, null, '/forms/15', 100);

insert into r_forms(id, form_number, name, order_number,  status, created_by, created_on,
                    modified_by, modified_on, href, parent_id)
VALUES (16, 16,
        row ('Доход от забитых животных', 'Сўйилган моллардан олинган даромад', 'So`yilgan mollardan olingan daromad', 'Income from slaughtered animals', 'So`yilgan mollardan olingan daromad'),
        15, 'ACTIVE', null, now(), null, null, '/forms/16', 100);

-----------------------------------------------------------------------------------------

insert into r_forms(id, form_number, name, order_number, status, created_by, created_on,
                    modified_by, modified_on, href, parent_id)
VALUES (101, 101, row ('Отчеты', 'Хисобот', 'Hisobot', 'Report', 'Report'), 101, 'ACTIVE', null, now(), null, null, null,
        null);

insert into r_forms(id, form_number, name, order_number, status, created_by, created_on,
                    modified_by, modified_on, href, parent_id)
VALUES (102, 102,
        row ('ОТЧЕТ О РАСХОДАХ', 'ХАРАЖАТЛАР ХИСОБОТИ', 'XARAJATLAR XISOBOTI', 'COSTS REPORT', 'XARAJATLAR XISOBOTI'),
        102, 'ACTIVE', null, now(), null, null, '/monthly-report', 101);

insert into r_forms(id, form_number, name, order_number, status, created_by, created_on,
                    modified_by, modified_on, href, parent_id)
VALUES (103, 103,
        row ('ЗАКЛЮЧИТЕЛЬНЫЙ ОТЧЕТ', 'ЯКУНИЙ ХИСОБОТИ', 'YAKUNIY XISOBOTI', 'END REPORT', 'YAKUNIY XISOBOTI'),
        103, 'ACTIVE', null, now(), null, null, '/end-report', 101);

insert into r_forms(id, form_number, name, order_number, status, created_by, created_on,
                    modified_by, modified_on, href, parent_id)
VALUES (104, 104,
        row ('ОТЧЕТ О ДОХОДАХ', 'ДАРОМАДЛАР ХИСОБОТИ', 'DAROMADLAR XISOBOTI', 'INCOME REPORT', 'DAROMADLAR XISOBOTI'),
        104, 'ACTIVE', null, now(), null, null, '/monthly-income', 101);

insert into r_forms(id, form_number, name, order_number, status, created_by, created_on,
                    modified_by, modified_on, href, parent_id)
VALUES (105, 105,
        row ('ОТЧЕТ О ПРОДАЖЕ СКОТА', 'СОТИЛГАН ҚОРАМОЛЛАР ҲАҚИДА ХИСОБОТ', 'SOTILGAN QORAMOLLAR HAQIDA XISOBOT', 'REPORT ON CATTLE SOLD', 'SOTILGAN QORAMOLLAR HAQIDA XISOBOT'),
        105, 'ACTIVE', null, now(), null, null, '/sold-cattle-report', 101);

insert into r_forms(id, form_number, name, order_number, status, created_by, created_on,
                    modified_by, modified_on, href, parent_id)
VALUES (106, 106,
        row ('ОИНФОРМАЦИЯ О КАЖДОМ ПРОДАНОМ КРС', 'XАР БИР СОТИЛГАН ҚОРАМОЛ ҲАҚИДА МАЛУМОТ', 'HAR BIR SOTILGAN QORAMOL HAQIDA MALUMOT', 'INFORMATION ON EACH CATTLE SOLD', 'HAR BIR SOTILGAN QORAMOL HAQIDA MALUMOT'),
        106, 'ACTIVE', null, now(), null, null, '/per-sold-cattle-report', 101);

----------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------

insert into r_forms(id, form_number, name, order_number, status, created_by, created_on,
                    modified_by, modified_on, href, parent_id)
VALUES (110, 110, row ('Информации', 'Маълумотлар', 'Malumotlar', 'References', 'References'), 110, 'ACTIVE', null, now(), null, null,
        null, null);

insert into r_forms(id, form_number, name, order_number, status, created_by, created_on,
                    modified_by, modified_on, href, parent_id)
VALUES (111, 111,
        row ('АНАЛОГИЧНАЯ ИНФОРМАЦИЯ', 'O''XSHASH MA''LUMOTLAR', 'O''XSHASH MA''LUMOTLAR', 'Similar information', 'O''XSHASH MA''LUMOTLAR'),
        111, 'ACTIVE', null, now(), null, null, '/references/def_references', 110);

insert into r_forms(id, form_number, name, order_number, status, created_by, created_on,
                    modified_by, modified_on, href, parent_id)
VALUES (112, 112,
        row ('Информация о животноводстве', 'Чорва моллари маълумотлари', 'Chorva mollari ma''lumotlari', 'LIST OF LIVESTOCK', 'Chorva mollari ma''lumotlari'),
        112, 'ACTIVE', null, now(), null, null, '/references/party-cattles', 110);

insert into r_forms(id, form_number, name, order_number, status, created_by, created_on,
                    modified_by, modified_on, href, parent_id)
VALUES (113, 113, row ('СПИСОК СКОТА ПО ЖУРНАЛУ', 'ПАРТИЯ БЎЙИЧА ҚОРАМОЛЛАР РЎЙХАТИ', 'PARTIYA BO''YICHA QORAMOLLAR RO''YXATI', 'Cattles list', 'PARTIYA BO''YICHA QORAMOLLAR RO''YXATI'),
        113, 'ACTIVE', null, now(), null, null, '/references/cattles', 110);

insert into r_forms(id, form_number, name, order_number, status, created_by, created_on,
                    modified_by, modified_on, href, parent_id)
VALUES (114, 114, row ('СПИСОК ФОРМ', 'FORMALAR RO''YXATI', 'FORMALAR RO''YXATI', 'Forms list', 'FORMALAR RO''YXATI'),
        114, 'ACTIVE', null, now(), null, null, '/references/forms', 110);

insert into r_forms(id, form_number, name, order_number, status, created_by, created_on,
                    modified_by, modified_on, href, parent_id)
VALUES (115, 115, row ('Тизимдаги лавозимлар', 'Тизимдаги лавозимлар', 'Tizimdagi lavozimlar', 'Positions in the system', 'Tizimdagi lavozimlar'), 115, 'ACTIVE', null, now(), null, null,
        '/references/roles', 110);

insert into r_forms(id, form_number, name, order_number, status, created_by, created_on,
                    modified_by, modified_on, href, parent_id)
VALUES (116, 116, row ('Солиқлар', 'Soliqlar', 'Soliqlar', 'Taxes', 'Soliqlar'), 116, 'ACTIVE', null, now(), null, null,
        '/references/tax', 110);

-------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------
insert into r_forms(id, form_number, name, order_number, status, created_by, created_on,
                    modified_by, modified_on, href, parent_id)
VALUES (130, 130, row ('АДМИН ПАНЕЛЬ', 'АДМИН ПАНЕЛ', 'Admin panel', 'Admin panel', 'Admin panel'), 130, 'ACTIVE', null,
        now(), null, null, '/user-list', null);
-------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------





