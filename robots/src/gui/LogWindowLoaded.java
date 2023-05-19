package gui;

import log.LogEntry;
import log.LogWindowSource;

import javax.swing.*;
import java.awt.*;

public class LogWindowLoaded extends LogWindow {
    private final LogWindowSource m_logSource;
    private TextArea m_logContent;

    public LogWindowLoaded(LogWindowSource logSource) {
        super(logSource);
        setPath("C:\\OOP\\robots\\src\\serialization\\logWindow.dat");

        m_logSource = logSource;
        m_logSource.registerListener(this);

        m_logContent = new TextArea("");
        loadState();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_logContent, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }



    private void updateLogContent() {
        StringBuilder content = new StringBuilder();
        for (LogEntry entry : m_logSource.all()) {
            content.append(entry.getMessage()).append("\n");
        }
        m_logContent.setText(content.toString());
        m_logContent.invalidate();
    }

    @Override
    public void onLogChanged() {
        EventQueue.invokeLater(this::updateLogContent);
    }

    public void setSize() {
        m_logContent.setSize(getDimension());
    }

}
