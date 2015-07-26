# friendrent-widget-task
Данный виджет позволяет:
1) Показывать объявления, в которых есть следующая информация:
	1. Тип (Квартира или Комната)
	2. Цена
	3. Описание(любое, но у меня передается адрес)
	4. Дата подачи объявления
	5. Ссылка на объявление(при нажатии на надпись Квартира/комната)
2) Данные генерируются на сервере случайным образом исходя из заданных параметров.
3) По нажатию на "Изменить" появляется окно с формой для фильтра объявлений.
4) Фильтр можно настроить по:
	1. Городу
	2. Цене
	3. Типу жилья (квартира или комната)
5) Поля для ввода цены позволяют вводить только цифры. Максимальная длина строки 6 символов. 
6) В полях для ввода цены изначально присутствует подсказка в виде серой строки "цена от"/"до" 
7) При нажатии кнопки "Сбросить", все объекты формы возвращаются в состояние по умолчанию. 
8) Кнопка в форме фильтра "Применить" применяет данные настройки, удаляет старые объявления и загружает новые с сервера. Также, изменяется url страницы (добавляются GET параметры), а окно с фильтром закрывается.
9) Кнопка "Больше" в конце всех объявлений загружает новые объявления с сервера. 
10) Когда объявления на сервере заканчиваются кнопка исчезает. Данную функциональность можно проверить загружая объявления городов: Москва(25 объявлений), Самара(8 объявлений), Казань(30 объявлений)
11) Когда объявлений на сервере совсем нет, то появляется надпись "По данному запросу объявлений нет"
	1. У города Уфа нет объявлений
	2. Если "цена от" будет больше чем "цена до", то объявлений тоже не будет
12) Страница с виджетом принимает GET параметры, которые могут позволить передавать настройки по умолчанию. 
