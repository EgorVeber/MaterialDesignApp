## MaterialDesignApp.
Разработка приложения велась с целью изучать и применить основы Android, Material design, Анимаций и Material Components.  
Все экраны приложения не связаны между собой.  
Часть экранов используется только для тестирования и не содержит никакой логики тк это приложения для обучения.  
Данные загружаются с помощью NASA API.  
Архитектура MVVM (ViewModel и LiveData).  

В приложении использовал/тестировал/изучал:  

Widgets(Основные):
- BottomNavigation.
- BottomSheetDialogFragment.
- BottomSheetBehaviors.
- Custom Behaviors (Написал несколько, реагируют на движение google behavior).
- ViewPager, Tabs и ViewPager2 (использует для 3 элементов 1 макет 1 класс 1 viewModel, меняются только аргументы).
- YouTubePlayerView (может быть элементом recycler).
- GuideView (Туториалы).
- CustomView. 
- RecyclerView + ItemTouchHelper + DiffUtil и Payload. 
- Так же AdapterRecycler может надуть разные макет в зависимости от ViewType.  

Style:  
- Добавил разные темы и поддержку Dynamic Color.
- Сustom attribute.
- Для всех material components переопределил textAppearance.
- shapeAppearance для small widgets. 
- Поддержка ночного режима через код AppCompatDelegate 
- Разные шрифты для разных тем + использовал Assets.

Анимации:  
- Анимации переходов между фрагментами.
- ObjectAnimator и View.animate.
- TransitionSet (ChangeImageTransform, ChangeBounds,Fade,Slide и т д).
- ConstraintSet

Spannable:  
- Использовал разные (BulletSpan, ImageSpan ,TypefaceSpan и тд)
- Связка BufferType  с Spannable.

Дополнительно:  
- Splash Activity.
- Базовый класс для всех фрагментов что постоянно не переопределять binding, onCreateView onDestroyView

---
Функционал (кратко).  
- Подключаемся к NASA API с помощью Retrofit получаем данные и показываем в Recycler
- Загружаем фото дня, на разных экранах отображаем по-разному и используем разные анимации.
- Можно получить список фотографий задав диапазон дат.
- Выбор темы приложения.  

Экраны.В BottomNavigation 5 вкладок:  
- Фото дня для сегодняшней, вчерашней и позавчерашней фотографии ViewPager2  
- Фото дня для Земли и Марса ViewPager  
- Фото дня с календарем + CustomBehavior.  
- Список фотографий с выбором диапазона дат.  
- Выбор темы.  

---
### Фото
[Вкладка 1 фото дня](https://github.com/EgorVeber/MaterialDesignApp/blob/master/app/src/main/assets/Image/1.png?raw=true)  
[Вкладка 2 планеты](https://github.com/EgorVeber/MaterialDesignApp/blob/master/app/src/main/assets/Image/2.png?raw=true)  
[Вкладка 3 Behavior виден](https://github.com/EgorVeber/MaterialDesignApp/blob/master/app/src/main/assets/Image/3.png?raw=true)  
[Вкладка 3 Behavio скрыт](https://github.com/EgorVeber/MaterialDesignApp/blob/master/app/src/main/assets/Image/4.png?raw=true)  
[Вкладка 3 Behavio открыт + spanable](https://github.com/EgorVeber/MaterialDesignApp/blob/master/app/src/main/assets/Image/5.png?raw=true)  
[Вкладка 4 Список фотографий](https://github.com/EgorVeber/MaterialDesignApp/blob/master/app/src/main/assets/Image/6.png?raw=true)  
[Вкладка 4 открывае описание](https://github.com/EgorVeber/MaterialDesignApp/blob/master/app/src/main/assets/Image/7.png?raw=true)  
[Вкладка 4 NASA прислала видео вместо фото](https://github.com/EgorVeber/MaterialDesignApp/blob/master/app/src/main/assets/Image/8.png?raw=true)  
[Вкладка 4 открывае фотографию](https://github.com/EgorVeber/MaterialDesignApp/blob/master/app/src/main/assets/Image/10.png?raw=true)  
[Динамическая тема](https://github.com/EgorVeber/MaterialDesignApp/blob/master/app/src/main/assets/Image/9.png?raw=true)  
