# SampleThemeApplication
Гайд по созданию приложения поддерживающего множество тем и создание модуля с компонентами дизайн системы, которые оддерживают темы.


## Заводим тестовый проект с поддержкой тем.

Создаем тестовый проект с пустой Activity. Для работы с темами лучше использовать Android API 21 (5.0 Loliloop) поскольку поддержка тем идет в полной мере. Ниже версии не могут правильно работать со стилизованными в темы селекторами (об этом упомянул ранее).

Пустая шаблонная Activity имеет такой вид:

```kotlin
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
```

Верстка **activity_main.xml** выглядит так: 

```xml
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

</androidx.constraintlayout.widget.ConstraintLayout>
```

Создаем файл **attrs.theme.xml** чтобы объявить атрибуты темы:

```xml
<resources>
    <attr name="theme_background" format="color"/>
</resources>
```

Создаем файл с темой **theme.xml** 

На этом этапе работ мы уже можем определить атрибуты темы объявленные в `attrs_theme.xml` ****Чтобы иметь возможность работать с атрибутами темы нужно объявить атрибут (см. `theme_background`)  и определить значение в теме. Определено может быть все что позволяет format="xxx" при определении атрибута (color, reference, string, dimension and etc.)

```xml
<resources>
    <style name="TestTheme" parent="Theme.AppCompat.Light">
        <item name="theme_background">#123456</item>
    </style>
    <style name="TestTheme.NoActionBar">
        <item name="windowActionBar">false</item>
        <item name="windowNoTitle">true</item>
    </style>
</resources>
```

Чтобы иметь возможность ссылаться на атрибут необходимо чтобы экраны наследовались от темы.

```xml
<!-- Base application theme. -->
<style name="AppTheme" parent="TestTheme">
    <!-- Customize your theme here. -->
    <item name="colorPrimary">@color/colorPrimary</item>
    <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
    <item name="colorAccent">@color/colorAccent</item>
</style>
```

Итак, мы создали тему, объявили атрибут темы и определили его значение внутри темы. Теперь можем ссылаться на него прямо из верстки:

```xml
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="?attr/theme_background"
    tools:context=".MainActivity">

</androidx.constraintlayout.widget.ConstraintLayout>
```

Aтрибут theme_background будет затянут непосредственно из темы.

Дальше аналогичным образом описываются и все остальные темы (создаем **theme2.xml** и ссылаемся на вторую тему)

```xml
<style name="TestTheme2" parent="Theme.AppCompat.Light">
    <item name="theme_background">#654321</item>
</style>

<style name="TestTheme2.NoActionBar">
    <item name="windowActionBar">false</item>
    <item name="windowNoTitle">true</item>
</style>
```

Чтобы иметь возможность применять любую из набора тем, у Activity есть метод `setTheme()`

При этом важно чтобы тема применялась до показа контента (см. пример ниже, до setContentView).

```kotlin
setTheme(if (false) R.style.TestTheme else R.style.TestTheme2)
setContentView(R.layout.activity_main)
```

## Создаем модуль с  компонентами дизайн системы

Подготовительные шаги:

- Создаем отдельным модулем дизайн систему `:designsystem` в виде Android library.
- Создаем палитру внутри модуля colors.xml
- Определение стилей компонентов и тем переносим на  модуль, т.к компоненты дизайн системы должны на них ссылаться.

### Создадим простенький текстовый компонент

Назовем его  TextComponent. Создадим его класс:

```kotlin
class TextComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.ds_text_component_style
) : AppCompatTextView(context, attrs, defStyleAttr)
```

`attrs` → Атрибуты приходящие из определения `.xml` (в том числе кастомные атрибуты view) 

`defStyleAttr` → дефолтный стиль `view` 

`**defStyleAttr` здесь мы используем чтобы определить дефолтный стиль View.**

`ds_text_component_style`  это один из стилей темы, определим его в `attrs_theme.xml` 

там же где и определяли цвет `theme_background.`  

```xml
 <resources>
    <attr name="theme_background" format="color"/>
    <attr name="ds_text_component_style" format="reference"/>
</resources>
```

Конкретные `ds_text_component_style` мы определим отдельно в каждой из тем (`theme.xml` и `theme2.xml`)

Вот определение стиля текстового компонента в теме. Аналогично будет и во второй из тем.

```xml
<style name="TestTheme" parent="Theme.AppCompat.Light">
    <item name="theme_background">#123456</item>
    <item name="ds_text_component_style">@style/TextComponent.FistTheme</item>
</style>
```

Сами стили по каждому из компонентов дизайн системы делать в отдельных файликах `attrs_component_name.xml` чтобы потом не запутаться в куче стилей.

Вот пример attrs_text.xml:

```xml
<resources>
    <style name="BaseTextWidget" parent="android:Widget.TextView"/>
    <style name="TextComponent" parent="BaseTextWidget">
        <item name="android:textSize">28sp</item>
        <item name="lineHeight">34sp</item>
        <item name="fontFamily">@font/something_font</item>
    </style>
    <style name="TextComponent.FistTheme">
        <item name="android:textColor">@color/component_red</item>
    </style>
    <style name="TextComponent.SecondTheme">
        <item name="android:textColor">@color/component_green</item>
    </style>
</resources>
```

 Работу с темами можно сравнить с определением интерфейса и его конкретными реализациями, здесь все то же самое. Ниже отображение добавленного текстового компонента при переключении между темами.
