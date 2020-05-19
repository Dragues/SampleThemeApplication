# SampleThemeApplication
Application for theme testing and design system implementation guide.

## Заводим тестовый проект с поддержкой тем.

Создаем тестовый проект с пустой Activity. Для работы с темами лучше использовать Android API 21 (5.0 Loliloop) поскольку поддержка тем идет в полной мере. Ниже версии не могут правильно работать со стилизованными в темы селекторами (об этом упомянул ранее).

![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/59a9b488-6619-45c9-b0ef-7ac9dee4d59c/Untitled.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/59a9b488-6619-45c9-b0ef-7ac9dee4d59c/Untitled.png)

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

В результате получаем при использовании тем следующие результаты:

![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/651b555a-e513-4e15-a767-46fb29ec509e/Untitled.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/651b555a-e513-4e15-a767-46fb29ec509e/Untitled.png)

TestTheme

![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/c06529d1-d11f-4e64-be55-c00233d28777/Untitled.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/c06529d1-d11f-4e64-be55-c00233d28777/Untitled.png)

TestTheme2

Чтобы иметь возможность применять любую из набора тем, у Activity есть метод `setTheme()`

При этом важно чтобы тема применялась до показа контента (см. пример ниже, до setContentView).

```kotlin
setTheme(if (false) R.style.TestTheme else R.style.TestTheme2)
setContentView(R.layout.activity_main)
```
