package resources;

import java.util.ListResourceBundle;
import javax.swing.ImageIcon;

public class MyResources_fr extends ListResourceBundle {

    @Override
    protected Object[][] getContents() {

        return resources;
    }

    private final Object[][] resources = {

            {"Язык", "Langue"},
            {"Langue", "Язык"},
            {"Русский", "Русский"},
            {"Français", "Français"},
            {"Приложение", "L'annexe"},
            {"L'annexe", "Приложение"},
            {"Выход", "Sortie"},
            {"Sortie", "Выход"},
            {"Режим отображения", "Mode d'affichage"},
            {"Mode d'affichage", "Режим отображения"},
            {"Системная схема", "Schéma du système"},
            {"Schéma du système", "Системная схема"},
            {"Универсальная схема", "Circuit universel"},
            {"Circuit universel", "Универсальная схема"},
            {"Тесты", "Tests"},
            {"Tests", "Тесты"},
            {"Сообщение в лог", "Message au journal de bord"},
            {"Message au journal de bord", "Сообщение в лог"},
            {"Да", "Oui"},
            {"Oui", "Да"},
            {"Нет", "Non"},
            {"Non", "Нет"},
            {"confirm_message", "Êtes-vous sûr ?"},
            {"confirm_window", "Fenêtre de confirmation"},
            {"Игровое поле на фр", "Игровое поле"},
            {"Игровое поле", "Игровое поле на фр"},
            {"Протокол работат на фр", "Протокол работает"},
            {"Протокол работает", "Протокол работат на фр"}

    };
}