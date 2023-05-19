package serialization;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.Point;
import java.io.*;

public abstract class AbstractSeriazationInternalFrame extends JInternalFrame implements State {

  public Dimension dim;
  public int x, y;
  public boolean resizable, closable, maximizable, iconifiable;
  public String path;
  public AbstractSeriazationInternalFrame(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable, String outPath){
    super(title,resizable,closable,maximizable,iconifiable);
    setPath(outPath);
  }

  public void setLocation(){
    setLocation(this.x, this.y);
  }

  public void setSize(){
    setSize(getDimension());
  }

  public Dimension getDimension(){
    return this.dim;
  }

  public String getPath(){
    return path;
  }

  public void setPath(String path){
    this.path = path;
  }



  @Override
  public void saveState(JInternalFrame window) {
    String path = getPath();
    try (OutputStream os = new FileOutputStream(path)) {
      try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(os))) {
        Point point = window.getLocation();
        oos.writeObject(point.x);
        oos.writeObject(point.y);
        oos.writeObject(window.isIcon());
        oos.writeObject(window.getSize());
        oos.flush();
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  @Override
  public void loadState() {
    String path = getPath();
    try (InputStream is = new FileInputStream(path)) {
      try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(is))) {
        this.x = (int) ois.readObject();
        this.y = (int) ois.readObject();
        this.isIcon = (boolean) ois.readObject();
        this.dim = (Dimension) ois.readObject();

      } catch (ClassNotFoundException ex) {
        ex.printStackTrace();
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
}
