package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
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
    private final GameWindow gameWindow;
    private final LogWindow logWindow;
    private final CoordinateWindow coordinateWindow;
    private final Properties cfg = new Properties();
    private final JDesktopPane desktopPane = new JDesktopPane();
    public List<JMenuItem> MenuItem = new ArrayList<>();

    public MainApplicationFrame() {

        //Make the big window be indented 50 pixels from each edge
        //of the screen.

        String cfgPath = "robots/src/config.properties";
        try (FileInputStream cfgInput = new FileInputStream(cfgPath)) {
            cfg.load(cfgInput);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset, screenSize.width - inset * 2, screenSize.height - inset * 2);

        setContentPane(desktopPane);

        coordinateWindow = createCoordinateWindow();
        addWindow(coordinateWindow);
        logWindow = createLogWindow();
        addWindow(logWindow);
        gameWindow = createGameWindow();
        addWindow(gameWindow);

        loadWindows();
        setJMenuBar(generateMenuBar());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        bundle = ResourceBundle.getBundle("resources.MyResources", new Locale("ru", "RU"));
    }

    private CoordinateWindow createCoordinateWindow() {
        CoordinateWindow coordinateWindow = new CoordinateWindow();
        coordinateWindow.setLocation(100, 50);
        setMinimumSize(coordinateWindow.getSize());
        coordinateWindow.pack();
        return coordinateWindow;
    }

    protected LogWindow createLogWindow() {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.setLocation(10, 10);
        logWindow.setSize(300, 800);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug("Протокол работает");
        return logWindow;
    }

    protected GameWindow createGameWindow(){
        GameWindow gameWindow = new GameWindow(coordinateWindow);
        gameWindow.setVisible(true);
        gameWindow.setLocation(20, 20);
        gameWindow.setSize(400,400);
        setMinimumSize(gameWindow.getSize());
        return gameWindow;
    }

    private void saveWindows() {
        if (Boolean.parseBoolean(cfg.getProperty("isGameWindowSerializable"))) {
            gameWindow.save(cfg.getProperty("gameWindowOutPath"));
        }
        if (Boolean.parseBoolean(cfg.getProperty("isLogWindowSerializable"))) {
            logWindow.save(cfg.getProperty("logWindowOutPath"));
        }
    }
    private void loadWindows() {
        if (Boolean.parseBoolean(cfg.getProperty("isGameWindowSerializable"))) {
            gameWindow.load(cfg.getProperty("gameWindowOutPath"));
        }
        if (Boolean.parseBoolean(cfg.getProperty("isLogWindowSerializable"))) {
            logWindow.load(cfg.getProperty("logWindowOutPath"));
        }
        this.invalidate();
    }

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
        pack();
    }

//    protected JMenuBar createMenuBar() {
//        JMenuBar menuBar = new JMenuBar();
//
//        //Set up the lone menu.
//        JMenu menu = new JMenu("Document");
//        menu.setMnemonic(KeyEvent.VK_D);
//        menuBar.add(menu);
//
//        //Set up the first menu item.
//        JMenuItem menuItem = new JMenuItem("New");
//        menuItem.setMnemonic(KeyEvent.VK_N);
//        menuItem.setAccelerator(KeyStroke.getKeyStroke(
//                KeyEvent.VK_N, ActionEvent.ALT_MASK));
//        menuItem.setActionCommand("new");
////        menuItem.addActionListener(this);
//        menu.add(menuItem);
//
//        //Set up the second menu item.
//        menuItem = new JMenuItem("Quit");
//        menuItem.setMnemonic(KeyEvent.VK_Q);
//        menuItem.setAccelerator(KeyStroke.getKeyStroke(
//                KeyEvent.VK_Q, ActionEvent.ALT_MASK));
//        menuItem.setActionCommand("quit");
////        menuItem.addActionListener(this);
//        menu.add(menuItem);
//
//        return menuBar;
//    }


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
            saveWindows();
            dispose();
            System.exit(0);
        }
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
