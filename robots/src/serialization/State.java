package serialization;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public interface State {
  void saveState(JInternalFrame window);
  void loadState();
}
