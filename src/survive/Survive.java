package survive;


import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.awt.image.BufferStrategy;
import java.io.PrintStream;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import survive.Entities.BoulderEntity;
import survive.Entities.GrassEntity;
import survive.Entities.GravelEntity;
import survive.Entities.PlayerEntity;
import survive.Entities.TreeEntity;

public class Survive
  extends Canvas
{
  private BufferStrategy strategy;
  
  private LowerLayer grass;
  private LowerLayer gravel;
  
  private Object tree;
  private Object boulder;
  
  private Hud player;
  
  private Inventory log;
  private Inventory stone;
  
  private String direction = "up";
  private double movementSpeed = 20;
  private String message = "";
  
  private boolean waitingForKeyPress = true;
  private boolean leftPressed = false;
  private boolean rightPressed = false;
  private boolean upPressed = false;
  private boolean downPressed = false;
  private boolean spacePressed = false;
  private boolean iPressed = false;
  
  private int xRes = 800;
  private int yRes = 600;
  private int playerX = this.xRes / 2;
  private int playerY = this.yRes / 2;
  private int treeLikely = 10;
  private int boulderLikely = 20;
  
  private boolean gameRunning = true;
  private boolean inventoryOpen = false;
  
  private ArrayList lowerLayers = new ArrayList();
  private ArrayList objects = new ArrayList();
  private ArrayList huds = new ArrayList();
  private ArrayList inventorys = new ArrayList();
  private ArrayList removeList = new ArrayList();
  
  public Survive()
  {
   
    JFrame container = new JFrame("Survive");
    

    JPanel panel = (JPanel)container.getContentPane();
    panel.setPreferredSize(new Dimension(this.xRes, this.yRes));
    panel.setLayout(null);
    

    setBounds(0, 0, this.xRes, this.yRes);
    panel.add(this);
    

    setIgnoreRepaint(true);
    

    container.pack();
    container.setResizable(false);
    container.setVisible(true);
    

    container.addWindowListener(new WindowAdapter()
    {
      public void windowClosing(WindowEvent e)
      {
        System.exit(0);
      }
    });
    addKeyListener(new KeyInputHandler());
    addMouseListener(new MouseInputHandler());
    
    

    requestFocus();
    
    createBufferStrategy(2);
    this.strategy = getBufferStrategy();
    
    initEntities();
    checkMissingFloorAll();
  }
  
  private void startGame()
  {
    this.lowerLayers.clear();
    initEntities();
    
    this.leftPressed = false;
    this.rightPressed = false;
    this.spacePressed = false;
  }
  
  private void initEntities()
  {
    this.player = new PlayerEntity(this, "sprites/PlayerN.png", this.playerX, this.playerY, "player");
    this.huds.add(this.player);
    for (int y = 0; y < 5; y++) {
      for (int x = 0; x < 5; x++)
      {
        this.grass = new GrassEntity(this, "sprites/grass.gif", x * 20, y * 20, "grass");
        this.lowerLayers.add(this.grass);
      }
    }
    addFloor(this.playerX, this.playerY);
  }
  
  private void checkMissingFloorTop()
  {
    int entityx = 0;
    int entityy = 0;
    
    boolean match = true;
    for (int x = 0; x < this.xRes / 20; x++)
    {
      match = false;
      for (int i = 0; i < this.lowerLayers.size(); i++)
      {
        LowerLayer lowerLayer = (LowerLayer)this.lowerLayers.get(i);
        
        entityx = lowerLayer.getX();
        entityy = lowerLayer.getY();
        if ((entityx == x * 20) && (entityy == 0)) {
          match = true;
        }
      }
      if (!match)
      {
        int y = 0;
        addFloor(x * 20, y);
      }
    }
  }
  
  private void checkMissingFloorBottom()
  {
    int entityx = 0;
    int entityy = 0;
    
    boolean match = true;
    for (int x = 0; x < this.xRes / 20; x++)
    {
      match = false;
      for (int i = 0; i < this.lowerLayers.size(); i++)
      {
        LowerLayer lowerLayer = (LowerLayer)this.lowerLayers.get(i);
        
        entityx = lowerLayer.getX();
        entityy = lowerLayer.getY();
        if ((entityx == x * 20) && (entityy == this.yRes)) {
          match = true;
        }
      }
      if (!match)
      {
        int y = this.yRes;
        addFloor(x * 20, y);
      }
    }
  }
  
  private void checkMissingFloorLeft()
  {
    int entityx = 0;
    int entityy = 0;
    
    boolean match = true;
    for (int y = 0; y < this.yRes / 20; y++)
    {
      match = false;
      for (int i = 0; i < this.lowerLayers.size(); i++)
      {
        LowerLayer lowerLayer = (LowerLayer)this.lowerLayers.get(i);
        
        entityx = lowerLayer.getX();
        entityy = lowerLayer.getY();
        if ((entityx == 0) && (entityy == y * 20)) {
          match = true;
        }
      }
      if (!match)
      {
        int x = 0;
        addFloor(x, y * 20);
      }
    }
  }
  
  private void checkMissingFloorRight()
  {
    int entityx = 0;
    int entityy = 0;
    
    boolean match = true;
    for (int y = 0; y < this.yRes / 20; y++)
    {
      match = false;
      for (int i = 0; i < this.lowerLayers.size(); i++)
      {
        LowerLayer lowerLayer = (LowerLayer)this.lowerLayers.get(i);
        
        entityx = lowerLayer.getX();
        entityy = lowerLayer.getY();
        if ((entityx == this.xRes) && (entityy == y * 20)) {
          match = true;
        }
      }
      if (!match)
      {
        int x = this.xRes;
        addFloor(x, y * 20);
      }
    }
  }
  
  private void checkMissingFloorAll()
  {
    int entityx = 0;
    int entityy = 0;
    
    boolean match = true;
    for (int x = 0; x < this.xRes / 20; x++) {
      for (int y = 0; y < this.yRes / 20; y++)
      {
        match = false;
        for (int i = 0; i < this.lowerLayers.size(); i++)
        {
          LowerLayer lowerLayer = (LowerLayer)this.lowerLayers.get(i);
          
          entityx = lowerLayer.getX();
          entityy = lowerLayer.getY();
          if ((entityx == x * 20) && (entityy == y * 20)) {
            match = true;
          }
        }
        if (!match) {
          addFloor(x * 20, y * 20);
        }
      }
    }
  }
  
  public void interact()
  {
    for (int i = 0; i < this.objects.size(); i++)
    {
      Object object = (Object)this.objects.get(i);
      if ((this.direction == "up") && (this.playerX == object.getX() + object.getModifiedX()) && (this.playerY == object.getY() + object.getModifiedY() + 20)) {
        object.interact();
      }
      if ((this.direction == "down") && (this.playerX == object.getX() + object.getModifiedX()) && (this.playerY == object.getY() + object.getModifiedY() - 20)) {
        object.interact();
      }
      if ((this.direction == "left") && (this.playerX == object.getX() + object.getModifiedX() + 20) && (this.playerY == object.getY() + object.getModifiedY())) {
        object.interact();
      }
      if ((this.direction == "right") && (this.playerX == object.getX() + object.getModifiedX() - 20) && (this.playerY == object.getY() + object.getModifiedY())) {
        object.interact();
      }
    }
  }
  
  public void removeObject(Object object)
  {
    this.removeList.add(object);
  }
  
  public void addToInventory(int itemCode)
  {
    if (this.inventorys.size() == 0)
    {
      this.log = new Inventory(1, 0);
      this.inventorys.add(this.log);
      this.stone = new Inventory(2, 0);
      this.inventorys.add(this.stone);
    }
    for (int i = 0; i < this.inventorys.size(); i++)
    {
      Inventory inventory = (Inventory)this.inventorys.get(i);
      if (inventory.getItemCode() == itemCode)
      {
        inventory.addQuantity();
        System.err.println(inventory.getQuantity());
      }
    }
  }
  
  public void checkCollisionObject(String direction)
  {
    for (int i = 0; i < this.objects.size(); i++)
    {
      Object object = (Object)this.objects.get(i);
      String type = object.getType();
      int modifiedX = object.getModifiedX();
      int modifiedY = object.getModifiedY();
      if (object.collideWith(object.getX() + modifiedX, object.getY() + modifiedY, this.playerX, this.playerY) == true)
      {
        if (direction == "left") {
          moveAll("right");
        }
        if (direction == "right") {
          moveAll("left");
        }
        if (direction == "down") {
          moveAll("up");
        }
        if (direction == "up") {
          moveAll("down");
        }
      }
    }
  }
  
  public void addFloor(int x, int y)
  {
    int grassChance = 2;
    int gravelChance = 0;
    for (int i = 0; i < this.lowerLayers.size(); i++)
    {
      LowerLayer lowerLayer = (LowerLayer)this.lowerLayers.get(i);
      int entityx = lowerLayer.getX();
      int entityy = lowerLayer.getY();
      if ((entityx >= x - 20) && (entityx <= x + 20) && (entityy >= y - 20) && (entityy <= y + 20))
      {
        String type = lowerLayer.getType();
        if (type == "grass") {
          grassChance++;
        }
        if (type == "gravel") {
          gravelChance++;
        }
      }
    }
    int randomGrass = (int)(Math.random() * grassChance);
    int randomGravel = (int)(Math.random() * gravelChance);
    if (randomGrass > randomGravel)
    {
      this.grass = new GrassEntity(this, "sprites/grass.gif", x, y, "grass");
      this.lowerLayers.add(this.grass);
      
      int Chance = (int)(Math.random() * this.treeLikely);
      if (Chance == 1)
      {
        this.tree = new TreeEntity(this, "sprites/tree.png", x, y - 20, "tree");
        this.objects.add(this.tree);
      }
      Chance = (int)(Math.random() * this.boulderLikely);
    }
    else
    {
      this.gravel = new GravelEntity(this, "sprites/gravel.gif", x, y, "gravel");
      this.lowerLayers.add(this.gravel);
      
      int Chance = (int)(Math.random() * this.boulderLikely);
      if (Chance == 1)
      {
        this.boulder = new BoulderEntity(this, "sprites/boulder.png", x, y, "boulder");
        this.objects.add(this.boulder);
      }
    }
  }
  
  public void moveAll(String direction)
  {
    for (int i = 0; i < this.objects.size(); i++)
    {
      Object object = (Object)this.objects.get(i);
      if (direction == "left") {
        object.moveLeft(this.movementSpeed);
      }
      if (direction == "right") {
        object.moveRight(this.movementSpeed);
      }
      if (direction == "up") {
        object.moveUp(this.movementSpeed);
      }
      if (direction == "down") {
        object.moveDown(this.movementSpeed);
      }
    }
    for (int i = 0; i < this.lowerLayers.size(); i++)
    {
      LowerLayer lowerLayer = (LowerLayer)this.lowerLayers.get(i);
      if (direction == "left") {
        lowerLayer.moveLeft(this.movementSpeed);
      }
      if (direction == "right") {
        lowerLayer.moveRight(this.movementSpeed);
      }
      if (direction == "up") {
        lowerLayer.moveUp(this.movementSpeed);
      }
      if (direction == "down") {
        lowerLayer.moveDown(this.movementSpeed);
      }
    }
  }
  public void drawInventory()
  {
      //Draw Inventory screen
      Graphics2D g = (Graphics2D)this.strategy.getDrawGraphics();
      g.setColor(Color.GRAY);
      g.fill3DRect(xRes - (xRes / 5), 0, yRes, xRes, true);
      g.setColor(Color.BLACK);
      g.drawLine(xRes - (xRes / 5), (yRes / 2), xRes, (yRes / 2));
      g.dispose();
  }
  
  public void gameLoop()
  {
    
    long lastLoopTime = System.currentTimeMillis();
    while (this.gameRunning)
    {
      long delta = System.currentTimeMillis() - lastLoopTime;
      lastLoopTime = System.currentTimeMillis();
      

      
      Graphics2D g = (Graphics2D)this.strategy.getDrawGraphics();
      g.setColor(Color.black);
      g.fillRect(0, 0, xRes, yRes);
     
      
      
      for (int i = 0; i < this.lowerLayers.size(); i++)
      {
        LowerLayer lowerLayer = (LowerLayer)this.lowerLayers.get(i);
        

        int entityX = lowerLayer.getX();
        int entityY = lowerLayer.getY();
        if ((entityX >= 0) && (entityY >= 0) && (entityX <= this.xRes) && (entityY <= this.yRes)) {
          lowerLayer.draw(g);
        }
      }
      this.objects.removeAll(this.removeList);
      this.removeList.clear();
      
      
      
      for (int i = 0; i < this.objects.size(); i++)
      {
        Object object = (Object)this.objects.get(i);
        object.draw(g);
      }
      for (int i = 0; i < this.huds.size(); i++)
      {
        Hud hud = (Hud)this.huds.get(i);
        hud.draw(g);
      }
      if (this.waitingForKeyPress)
      {
        g.setColor(Color.white);
        g.drawString(this.message, (xRes - g.getFontMetrics().stringWidth(this.message)) / 2, 250);
        g.drawString("Press any key", (xRes - g.getFontMetrics().stringWidth("Press space to continue")) / 2, (yRes / 2));
      }
      if (inventoryOpen == true)
      {    
          drawInventory();
      }
      this.strategy.show();
      if (this.iPressed)
      {
          inventoryOpen = !inventoryOpen;
      }
      if (this.leftPressed)
      {
        this.direction = "left";
        checkMissingFloorLeft();
        
        moveAll(this.direction);
        for (int i = 0; i < 1; i++)
        {
          Hud hud = (Hud)this.huds.get(i);
          hud.changeFrame(2);
        }
        checkCollisionObject(this.direction);
      }
      if (this.rightPressed)
      {
        this.direction = "right";
        checkMissingFloorRight();
        
        moveAll(this.direction);
        for (int i = 0; i < 1; i++)
        {
          Hud hud = (Hud)this.huds.get(i);
          hud.changeFrame(3);
        }
        checkCollisionObject(this.direction);
      }
      if (this.upPressed)
      {
        this.direction = "up";
        checkMissingFloorTop();
        
        moveAll(this.direction);
        for (int i = 0; i < 1; i++)
        {
          Hud hud = (Hud)this.huds.get(i);
          hud.changeFrame(0);
        }
        checkCollisionObject(this.direction);
      }
      if (this.downPressed)
      {
        this.direction = "down";
        checkMissingFloorBottom();
        
        moveAll(this.direction);
        for (int i = 0; i < 1; i++)
        {
          Hud hud = (Hud)this.huds.get(i);
          hud.changeFrame(1);
        }
        checkCollisionObject(this.direction);
      }
      if (this.spacePressed) {
        interact();
      }
      try
      {
        Thread.sleep(100L);
      }
      catch (Exception e) {}
    }
  }
  private class MouseInputHandler
    extends MouseAdapter
  {
      public void mouseClicked(MouseEvent e)
      {
         
          System.err.println(e.getX());
          System.err.println(e.getY());
      }
  }
  
  private class KeyInputHandler
    extends KeyAdapter
  {
    private int pressCount = 1;
    
    private KeyInputHandler() {}
    
    public void keyPressed(KeyEvent e)
    {
      if (Survive.this.waitingForKeyPress) {
        return;
      }
      if (e.getKeyCode() == 37) {
        Survive.this.leftPressed = true;
      }
      if (e.getKeyCode() == 39) {
        Survive.this.rightPressed = true;
      }
      if (e.getKeyCode() == 38) {
        Survive.this.upPressed = true;
      }
      if (e.getKeyCode() == 40) {
        Survive.this.downPressed = true;
      }
      if (e.getKeyCode() == 32) {
        Survive.this.spacePressed = true;
      }
      if (e.getKeyCode() == 73) {
          Survive.this.iPressed = true;
   
      }
    }
    
    public void keyReleased(KeyEvent e)
    {
      if (Survive.this.waitingForKeyPress) {
        return;
      }
      if (e.getKeyCode() == 37) {
        Survive.this.leftPressed = false;
      }
      if (e.getKeyCode() == 39) {
        Survive.this.rightPressed = false;
      }
      if (e.getKeyCode() == 38) {
        Survive.this.upPressed = false;
      }
      if (e.getKeyCode() == 40) {
        Survive.this.downPressed = false;
      }
      if (e.getKeyCode() == 32) {
        Survive.this.spacePressed = false;
      }
      if (e.getKeyCode() == 73) {
        Survive.this.iPressed = false;
      }
    }
    
    public void keyTyped(KeyEvent e)
    {
      if (Survive.this.waitingForKeyPress) {
        if (this.pressCount == 1)
        {
          Survive.this.waitingForKeyPress = false;
          
          this.pressCount = 0;
        }
        else
        {
          this.pressCount += 1;
        }
      }
      if (e.getKeyChar() == '\033') {
        System.exit(0);
      }
    }
  }
  
  public static void main(String[] args)
  {
    Survive g = new Survive();
    
    g.gameLoop();
  }
}









