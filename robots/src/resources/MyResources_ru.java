package resources;

import java.util.ListResourceBundle;
import javax.swing.ImageIcon;

public class MyResources_ru extends ListResourceBundle {

    @Override
    protected Object[][] getContents() {

        return resources;
    }

    private final Object[][] resources = {

            {"Язык", "Язык"},
            {"Langue", "Язык"},
            {"Русский", "Русский"},
            {"Français", "Français"},
            {"Приложение", "Приложение"},
            {"L'annexe", "Приложение"},
            {"Выход", "Выход"},
            {"Sortie", "Выход"},
            {"Режим отображения", "Режим отображения"},
            {"Mode d'affichage", "Режим отображения"},
            {"Системная схема", "Системная схема"},
            {"Schéma du système", "Системная схема"},
            {"Универсальная схема", "Универсальная схема"},
            {"Circuit universel", "Универсальная схема"},
            {"Тесты", "Тесты"},
            {"Tests", "Тесты"},
            {"Сообщение в лог", "Сообщение в лог"},
            {"Message au journal de bord", "Сообщение в лог"},
            {"Да", "Да"},
            {"Oui", "Да"},
            {"Нет", "Нет"},
            {"Non", "Нет"},
            {"confirm_message", "Вы уверены?"},
            {"confirm_window", "Окно подтверждения"},
            {"Игровое поле", "Игровое поле"},
            {"Игровое поле на фр", "Игровое поле"},
            {"Протокол работает", "Протокол работает"},
            {"Протокол работат на фр", "Протокол работает"}

    };
}