package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.*;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


import gui.Windows.GameWindow;
import gui.Windows.GameWindowLoaded;
import gui.Windows.LogWindow;
import gui.Windows.LogWindowLoaded;
import log.Logger;


/**
 * Что требуется сделать: 1. Метод создания меню перегружен функционалом и трудно читается. Следует
 * разделить его на серию более простых методов (или вообще выделить отдельный класс).
 */
public class MainApplicationFrame extends JFrame {

    public ResourceBundle bundle;
    private JMenu langMenu;
    private JMenu VisionMenu;
    private JMenu TestMenu;
    private JMenu CloseMenu;

    private final JDesktopPane desktopPane = new JDesktopPane();

    private final GameWindow gameWindow;
    private final LogWindow logWindow;

    public List<JMenuItem> MenuItem = new ArrayList<>();

    public MainApplicationFrame() {

        //Make the big window be indented 50 pixels from each edge
        //of the screen.

        bundle = ResourceBundle.getBundle("resources.MyResources", new Locale("ru", "RU"));
        UIManager.put("OptionPane.yesButtonText", bundle.getString("Да"));
        UIManager.put("OptionPane.noButtonText", bundle.getString("Нет"));

        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset, screenSize.width - inset * 2, screenSize.height - inset * 2);

        int load = showLoadedConfirm();
        if (load == JOptionPane.YES_OPTION) {
            gameWindow = createGameWindowLoaded();
            logWindow = createLogWindowLoaded();
        } else {
            gameWindow = createGameWindow();
            logWindow = createLogWindow();
        }


        setContentPane(desktopPane);
        setJMenuBar(generateMenuBar());

        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    protected LogWindow createLogWindow() {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());

        logWindow.setVisible(true);

        logWindow.setLocation();
        logWindow.setSize();
        setMinimumSize(logWindow.getSize());
        desktopPane.add(logWindow);
        logWindow.pack();

        Logger.debug("Протокол работает");

        return logWindow;
    }

    protected LogWindow createLogWindowLoaded() {
        LogWindowLoaded logWindow = new LogWindowLoaded(Logger.getDefaultLogSource());

        logWindow.setVisible(true);

        logWindow.setLocation();
        logWindow.setSize();
        setMinimumSize(logWindow.getSize());
        desktopPane.add(logWindow);
        logWindow.pack();

        Logger.debug("Протокол работает");

        return logWindow;
    }

    protected GameWindow createGameWindow() {
        GameWindow gameWindow = new GameWindow();
        gameWindow.setVisible(true);
        gameWindow.setLocation();
        gameWindow.setSize();
        setMinimumSize(gameWindow.getSize());
        desktopPane.add(gameWindow);
        return gameWindow;
    }

    protected GameWindow createGameWindowLoaded() {
        GameWindowLoaded gameWindow = new GameWindowLoaded();
        gameWindow.setVisible(true);
        gameWindow.setLocation();
        gameWindow.setSize();
        setMinimumSize(gameWindow.getSize());
        desktopPane.add(gameWindow);
        return gameWindow;
    }

//    private void saveWindows() {
//        if (Boolean.parseBoolean(cfg.getProperty("isGameWindowSerializable"))) {
//            gameWindow.saveState(gameWindow);
//        }
//        if (Boolean.parseBoolean(cfg.getProperty("isLogWindowSerializable"))) {
//            logWindow.saveState(logWindow);
//        }
//    }
//    private void loadWindows() {
//        if (Boolean.parseBoolean(cfg.getProperty("isGameWindowSerializable"))) {
//            gameWindow = new GameWindowLoaded();
//        }
//        if (Boolean.parseBoolean(cfg.getProperty("isLogWindowSerializable"))) {
//            logWindow = new LogWindow(Logger.getDefaultLogSource());
//        }
//        this.invalidate();
//    }

    protected void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    private void updateLanguage(Locale locale) {
        //Читаем переводы слов из файла с нужным нам языком
        bundle = ResourceBundle.getBundle("resources.MyResources", locale);
        //Заменяем названия менюшек и прочего
        langMenu.setText(bundle.getString("Язык"));
        VisionMenu.setText(bundle.getString("Режим отображения"));
        TestMenu.setText(bundle.getString("Тесты"));
        CloseMenu.setText(bundle.getString("Приложение"));
        UIManager.put("OptionPane.yesButtonText", bundle.getString("Да"));
        UIManager.put("OptionPane.noButtonText", bundle.getString("Нет"));
        //Пробегаем по пунктам во всех вкладках и заменяем их
        for (JMenuItem item : MenuItem) {
            item.setText(bundle.getString(item.getText()));
        }
    }

    private JMenuBar generateMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(generateLangMenu());
        menuBar.add(generateCloseMenu());
        menuBar.add(generateMappingMenu());
        menuBar.add(generateTestMenu());
        return menuBar;
    }

    private JMenu generateLangMenu() {
        langMenu = new JMenu("Язык");

        langMenu.setMnemonic(KeyEvent.VK_F);
        langMenu.getAccessibleContext().setAccessibleDescription("Языки");
        langMenu.add(generateMenuItem("Русский", KeyEvent.VK_N, event -> {
            updateLanguage(new Locale("ru", "RU"));
            invalidate();
        }));

        langMenu.add(generateMenuItem("Français", KeyEvent.VK_N, event -> {
            updateLanguage(new Locale("fr", "FR"));
            invalidate();
        }));
        return langMenu;
    }

    private JMenu generateMappingMenu() {
        VisionMenu = new JMenu("Режим отображения");
        VisionMenu.setMnemonic(KeyEvent.VK_V);
        VisionMenu.getAccessibleContext()
                .setAccessibleDescription("Управление режимом отображения приложения");
        VisionMenu.add(generateMenuItem("Системная схема", KeyEvent.VK_S, event -> {
            setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            invalidate();
        }));
        VisionMenu.add(generateMenuItem("Универсальная схема", KeyEvent.VK_S, event -> {
            setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            invalidate();
        }));
        return VisionMenu;
    }

    private JMenu generateTestMenu() {
        TestMenu = new JMenu("Тесты");
        TestMenu.setMnemonic(KeyEvent.VK_T);
        TestMenu.getAccessibleContext().setAccessibleDescription("Тестовые команды");
        TestMenu.add(generateMenuItem("Сообщение в лог", KeyEvent.VK_S, event -> {
            Logger.debug("Новая строка");
        }));
        return TestMenu;
    }

    private JMenu generateCloseMenu() {
        CloseMenu = new JMenu("Приложение");
        CloseMenu.setMnemonic(KeyEvent.VK_ESCAPE);
        CloseMenu.getAccessibleContext().setAccessibleDescription("Закрыть");
        CloseMenu.add(generateMenuItem("Выход", KeyEvent.VK_ESCAPE, event -> {
            showConfirmDialog();
        }));
        return CloseMenu;
    }

    public void showConfirmDialog() {
        int select = JOptionPane.showConfirmDialog(this, bundle.getString("confirm_message"),
                bundle.getString("confirm_window"),
                JOptionPane.YES_NO_OPTION);

        if (select == JOptionPane.YES_OPTION) {
            this.logWindow.saveState(this.logWindow);
            this.gameWindow.saveState(this.gameWindow);
            dispose();
            System.exit(0);
        }
    }

    public int showLoadedConfirm() {
        return JOptionPane.showConfirmDialog(this, "Загрузить сохраненное состояние?",
                bundle.getString("confirm_window"),
                JOptionPane.YES_NO_OPTION);
    }

    private JMenuItem generateMenuItem(String name, int keyEvent, ActionListener actionListener) {
        JMenuItem item = new JMenuItem(name, keyEvent);
        item.addActionListener(actionListener);
        MenuItem.add(item);
        return item;
    }

    private void setLookAndFeel(String className) {
        try {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            // just ignore
        }
    }
}
