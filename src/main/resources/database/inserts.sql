insert into r_gender(id, name)
values (1, row('мужчина', 'эркак', 'erkak')::t_nls);
insert into r_gender(id, name)
values (2, row('женщина', 'аёл', 'ayol')::t_nls);

-- ilmiy darajalarni tog'ri kiritish kerk
insert into r_degrees(id, name)
values (1, row('Ma''lumoti o''rta', '', '', '', '')::t_nls);
insert into r_degrees(id, name)
values (2, row('Ma''lumoti Oliy', '', '', '', '')::t_nls);
insert into r_degrees(id, name)
values (3, row('Ma''lumoti Professor ', '', '', '', '')::t_nls);

-- lavozimlar ro'yxati
insert into r_positions(id, name, status)
values (1, row('Bosh bugalter', '', '', '', '')::t_nls, 'A');
insert into r_positions(id, name, status)
values (2, row('yordamchi bugalter', '', '', '', '')::t_nls, 'A');
insert into r_positions(id, name, status)
values (3, row('texnik xodim', '', '', '', '')::t_nls, 'A');

insert into r_identity_document_types(id, name, status)
values (1, row('биометрический паспорт', '', '', '', '')::t_nls, 'A');
insert into r_identity_document_types(id, name, status)
values (2, row('загранпаспорт', '', '', '', '')::t_nls, 'A');
insert into r_identity_document_types(id, name, status)
values (3, row('id карта', '', '', '', '')::t_nls, 'A');

