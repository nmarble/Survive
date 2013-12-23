package survive;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public class SpriteStore implements java.io.Serializable
{
  private static SpriteStore single = new SpriteStore();
  
  public static SpriteStore get()
  {
    return single;
  }
  
  private Map<String, Sprite> sprites = new HashMap<String, Sprite>();
  
  public Sprite getSprite(String ref)
  {
    if (sprites.containsKey(ref)) 
    {
      return sprites.get(ref);
    }
    BufferedImage sourceImage = null;
    try
    {
      URL url = getClass().getClassLoader().getResource(ref);
      if (url == null) {
        fail("Can't fine ref: " + ref);
      }
      sourceImage = ImageIO.read(url);
    }
    catch (IOException e) {}
    GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
    Image image = gc.createCompatibleImage(sourceImage.getWidth(), sourceImage.getHeight(), 2);
    

    image.getGraphics().drawImage(sourceImage, 0, 0, null);
    

    Sprite sprite = new Sprite(image);
    sprites.put(ref, sprite);
    
    return sprite;
  }
  
  private void fail(String message)
  { 
    System.err.println(message);
    System.exit(0);
  }
}









