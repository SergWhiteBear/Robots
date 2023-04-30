package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import java.io.FileInputStream;
import java.util.Properties;
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

import localization.LanguageInterface;
import log.Logger;


/**
 * Что требуется сделать: 1. Метод создания меню перегружен функционалом и трудно читается. Следует
 * разделить его на серию более простых методов (или вообще выделить отдельный класс).
 */
public class MainApplicationFrame extends JFrame {

  private final JDesktopPane desktopPane = new JDesktopPane();

  private final GameWindow gameWindow;
  private final LogWindow logWindow;
  private final Properties cfg = new Properties();
  public MainApplicationFrame(LanguageInterface language) {
    //Make the big window be indented 50 pixels from each edge
    //of the screen.

    String cfgPath = "config.properties";
    try (FileInputStream cfgInput = new FileInputStream(cfgPath)) {
      cfg.load(cfgInput);
    } catch (Exception e) {
      e.printStackTrace();
    }

    language.setLanguage();
    int inset = 50;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    setBounds(inset, inset, screenSize.width - inset * 2, screenSize.height - inset * 2);

    setContentPane(desktopPane);

    logWindow = createLogWindow();
    gameWindow = createGameWindow();
    addWindow(logWindow);
    addWindow(gameWindow);

    loadWindows();

    setJMenuBar(generateMenuBar());
    setDefaultCloseOperation(EXIT_ON_CLOSE);
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
    GameWindow gameWindow = new GameWindow();
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

  private JMenuBar generateMenuBar() {
    JMenuBar menuBar = new JMenuBar();
    menuBar.add(generateCloseMenu());
    menuBar.add(generateMappingMenu());
    menuBar.add(generateTestMenu());
    return menuBar;
  }

  private JMenu generateMappingMenu() {
    JMenu lookAndFeelMenu = new JMenu("Режим отображения");
    lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
    lookAndFeelMenu.getAccessibleContext()
        .setAccessibleDescription("Управление режимом отображения приложения");
    lookAndFeelMenu.add(generateMenuItem("Системная схема", KeyEvent.VK_S, event -> {
      setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      invalidate();
    }));
    lookAndFeelMenu.add(generateMenuItem("Универсальная схема", KeyEvent.VK_S, event -> {
      setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
      invalidate();
    }));
    return lookAndFeelMenu;
  }

  private JMenu generateTestMenu() {
    JMenu testMenu = new JMenu("Тесты");
    testMenu.setMnemonic(KeyEvent.VK_T);
    testMenu.getAccessibleContext().setAccessibleDescription("Тестовые команды");
    testMenu.add(generateMenuItem("Сообщение в лог", KeyEvent.VK_S, event -> {
      Logger.debug("Новая строка");
    }));
    return testMenu;
  }

  private JMenu generateCloseMenu() {
    JMenu closeMenu = new JMenu("Приложение");
    closeMenu.setMnemonic(KeyEvent.VK_ESCAPE);
    closeMenu.getAccessibleContext().setAccessibleDescription("Закрыть");
    closeMenu.add(generateMenuItem("Выход", KeyEvent.VK_ESCAPE, event -> {
      showConfirmDialog();
    }));
    return closeMenu;
  }

  public void showConfirmDialog() {
    int select = JOptionPane.showConfirmDialog(this, "Вы уверены?",
        "Окно подтверждения",
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
