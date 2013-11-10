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
import survive.Entities.ButtonEntity;
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
  
  private MiddleLayer tree;
  private MiddleLayer boulder;
  private MiddleLayer LogWall;
  
  private Hud player;
  private Hud structureButton;
  private Hud toolButton;
  private Hud consumableButton;
  private Hud decorativeButton;
  
  private Inventory log;
  private Inventory stone;
  private Inventory logWall;
  
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
  private boolean cPressed = false;
  
  private int xRes = 800;
  private int yRes = 600;
  private int playerX = this.xRes / 2;
  private int playerY = this.yRes / 2;
  private int treeLikely = 10;
  private int boulderLikely = 20;
  
  private boolean gameRunning = true;
  private boolean inventoryOpen = false;
  private boolean craftingOpen = false;
  private boolean craftingStructure = false;
  
  private boolean logWallReceipe = false;
  
  private ArrayList lowerLayers = new ArrayList();
  private ArrayList middleLayers = new ArrayList();
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
    this.player = new PlayerEntity(this, "sprites/PlayerN.png", this.playerX, this.playerY, "player", 20);
    this.huds.add(this.player);
    
    this.structureButton = new ButtonEntity(this, "sprites/StructureButton.jpg", 0, (yRes - 100), "Structure", 100); 
    this.huds.add(this.structureButton);
    
    this.toolButton = new ButtonEntity(this, "sprites/ToolButton.jpg", 110,(yRes - 100), "Tool",100); 
    this.huds.add(this.toolButton);
    
    this.consumableButton = new ButtonEntity(this, "sprites/ConsumableButton.jpg", 220, (yRes - 100), "Consumable",100); 
    this.huds.add(this.consumableButton);
    
    this.decorativeButton = new ButtonEntity(this, "sprites/DecorativeButton.jpg", 330,(yRes - 100), "Decorative",100); 
    this.huds.add(this.decorativeButton);
    
    
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
  
   for (int i = 0; i < this.middleLayers.size(); i++)
      
      {
        MiddleLayer middleLayer = (MiddleLayer)this.middleLayers.get(i);
    
      
      //MiddleLayer object = (MiddleLayer)this.middleLayers.get(i);
      if ((this.direction == "up") && (this.playerX == middleLayer.getX() + middleLayer.getModifiedX()) && (this.playerY == middleLayer.getY() + middleLayer.getModifiedY() + 20)) {
        middleLayer.interact();
      }
      if ((this.direction == "down") && (this.playerX == middleLayer.getX() + middleLayer.getModifiedX()) && (this.playerY == middleLayer.getY() + middleLayer.getModifiedY() - 20)) {
        middleLayer.interact();
      }
      if ((this.direction == "left") && (this.playerX == middleLayer.getX() + middleLayer.getModifiedX() + 20) && (this.playerY == middleLayer.getY() + middleLayer.getModifiedY())) {
        middleLayer.interact();
      }
      if ((this.direction == "right") && (this.playerX == middleLayer.getX() + middleLayer.getModifiedX() - 20) && (this.playerY == middleLayer.getY() + middleLayer.getModifiedY())) {
        middleLayer.interact();
      }
    }
  }
  public String mouseInteract(int x, int y)
  {
      String type = "none";
      //Check if button is there
      for (int i = 1; i < this.huds.size(); i++)
      {
        Hud hud = (Hud)this.huds.get(i);
        int xLimit = (hud.getX() + hud.getImageSize());
        int yLimit = (hud.getY() + hud.getImageSize());
        
        if ((x >= hud.getX()) && (y >= hud.getY()) && (x <= xLimit) && (y <= yLimit))
        {
            type = hud.getType();
           
        }
        
      }
     return type;
  }
  public void removeMiddleLayer(MiddleLayer object)
  {
    this.removeList.add(object);
  }
  public void removeButton(Hud object)
  {
    this.removeList.add(object);
  }
  public void addToInventory(int itemCode, int quantity)
  {
    if (this.inventorys.size() == 0)
    {
      this.log = new Inventory("sprites/log.png", 1, 0);
      this.inventorys.add(this.log);
      this.stone = new Inventory("sprites/stone.png", 2, 0);
      this.inventorys.add(this.stone);
      this.logWall = new Inventory("sprites/LogWall.gif", 3, 0);
      this.inventorys.add(this.logWall);
    }
    for (int i = 0; i < this.inventorys.size(); i++)
    {
      Inventory inventory = (Inventory)this.inventorys.get(i);
      if (inventory.getItemCode() == itemCode)
      {
        inventory.addQuantity(quantity);
   
      }
    }
  }
  public void removeFromInventory (int itemCode, int quantity)
  {
    for (int i = 0; i < this.inventorys.size(); i++)
    {
      Inventory inventory = (Inventory)this.inventorys.get(i);
      if (inventory.getItemCode() == itemCode)
      {
        inventory.removeQuantity(quantity);
     
      }
    }  
  }
  public void checkCollisionObject(String direction)
  {
    for (int i = 0; i < this.middleLayers.size(); i++)
    {
      MiddleLayer middleLayer = (MiddleLayer)this.middleLayers.get(i);
      String type = middleLayer.getType();
      int modifiedX = middleLayer.getModifiedX();
      int modifiedY = middleLayer.getModifiedY();
      if (middleLayer.collideWith(middleLayer.getX() + modifiedX, middleLayer.getY() + modifiedY, this.playerX, this.playerY) == true)
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
        this.middleLayers.add(this.tree);
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
        this.middleLayers.add(this.boulder);
      }
    }
  }
  
  public void moveAll(String direction)
  {
    for (int i = 0; i < this.middleLayers.size(); i++)
    {
      MiddleLayer object = (MiddleLayer)this.middleLayers.get(i);
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
  public void checkButtonPushed ()
  {
      if (this.cPressed)
      {
          craftingOpen = !craftingOpen;
          if (craftingStructure = true)
          {
              craftingStructure = false;
          }
      }
      if (this.iPressed)
      {
          inventoryOpen = !inventoryOpen;
      }
      if (this.leftPressed)
      {
        this.direction = "left";
            
        moveAll(this.direction);
        for (int i = 0; i < 1; i++)
        {
          Hud hud = (Hud)this.huds.get(i);
          hud.changeFrame(2);
        }
        checkMissingFloorLeft();
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
        
        moveAll(this.direction);
        for (int i = 0; i < 1; i++)
        {
          Hud hud = (Hud)this.huds.get(i);
          hud.changeFrame(0);
        }
        checkMissingFloorTop();
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
  public void removeAllButtons()
  {
      for (int i = 5; i < this.huds.size(); i++)
      {
          Hud hud = (Hud)this.huds.get(i);
          removeButton(hud);
      }
  }
  public void findAvailReceipe()
  {
      int logQuantity = 0;
      int stoneQuantity = 0;
      int x = 0;
      int y = yRes - 30;
      for (int i = 0; i < this.inventorys.size(); i++)
            
        {
            Inventory inventory = (Inventory)this.inventorys.get(i);
            
            if (inventory.getQuantity() > 0)
            {
                switch (inventory.getItemCode()) 
                {
                    case 1:
                        logQuantity = inventory.getQuantity();
                        break;
                    case 2:
                        stoneQuantity = inventory.getQuantity();
                        break;
                }
            }
        }
      if (logQuantity >= 4)   
      {
          logWallReceipe = true;
          
          this.structureButton = new ButtonEntity(this, "sprites/logWall.gif", x, y, "LogWall", 20); 
          this.huds.add(this.structureButton);
          x = x + 25;
      }
      
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
     
      
      checkButtonPushed();
      for (int i = 0; i < this.lowerLayers.size(); i++)
      {
        LowerLayer lowerLayer = (LowerLayer)this.lowerLayers.get(i);
        

        int entityX = lowerLayer.getX();
        int entityY = lowerLayer.getY();
        if ((entityX >= 0) && (entityY >= 0) && (entityX <= this.xRes) && (entityY <= this.yRes)) {
          lowerLayer.draw(g);
        }
      }
      this.middleLayers.removeAll(this.removeList);
      this.huds.removeAll(this.removeList);
      
      this.removeList.clear();
      
      for (int i = 0; i < 1; i++)
      {
        Hud hud = (Hud)this.huds.get(i);
        hud.draw(g);
      }
      
      for (int i = 0; i < this.middleLayers.size(); i++)
      {
        MiddleLayer object = (MiddleLayer)this.middleLayers.get(i);
        object.draw(g);
      }
      
      if (craftingOpen == true)
      {    
      for (int i = 1; i <= 4; i++)
      {
        Hud hud = (Hud)this.huds.get(i);
        hud.draw(g);
      }
         
      }
      
      
      if (craftingStructure == true)
      {
          int x = 0;
          removeAllButtons();
          findAvailReceipe();
          for (int i = 1; i < this.huds.size(); i++)
            {
                Hud hud = (Hud)this.huds.get(i);
                if (hud.getType() == "LogWall" && logWallReceipe == true)
                {
                
                hud.draw(g);
                x = x + 25;
                
                }
            }
      
          
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
          int col = -20;
          int row = 1;
          for (int i = 0; i < this.inventorys.size(); i++)
            
            {
            Inventory inventory = (Inventory)this.inventorys.get(i);
        
            
            if (inventory.getQuantity() > 0)
            {
                col = col + 25;
            }
            inventory.changeX(xRes - (xRes / 5) + (col));
            inventory.changeY((yRes / 2) + (15 * row));
            
            
        
            if (inventory.getQuantity() > 0)
            {
                inventory.draw(g);
                String quantity = String.valueOf(inventory.getQuantity());
                g.drawString(quantity, inventory.getX(), inventory.getY());
            }
         }
      }
      this.strategy.show();
      
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
          //Get what is clicked
          String what = mouseInteract(e.getX(), e.getY());
          
          if (craftingOpen == true && what != "none")
          {    
          craftingOpen = false;
          craftingStructure = true;
          }
          if (craftingStructure = true)
          {
              switch (what){
                  case "LogWall":
                    addToInventory(3, 1);
                    removeFromInventory(1, 4);
                    break;
              }
          }
          
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
      switch(e.getKeyCode()) {
          
          case 32:
              Survive.this.spacePressed = true;
              break;
          case 37:
              Survive.this.leftPressed = true;
              break;
          case 38:
              Survive.this.upPressed = true;
              break;
          case 39:
              Survive.this.rightPressed = true;
              break;
          case 40:
              Survive.this.downPressed = true;
              break;
          case 67:
              Survive.this.cPressed = true;
              break;
          case 73:
              Survive.this.iPressed = true;
              break;
      }
     
    }
    
    public void keyReleased(KeyEvent e)
    {
      if (Survive.this.waitingForKeyPress) {
        return;
      }
      switch(e.getKeyCode()) {
          
          case 32:
              Survive.this.spacePressed = false;
              break;
          case 37:
              Survive.this.leftPressed = false;
              break;
          case 38:
              Survive.this.upPressed = false;
              break;
          case 39:
              Survive.this.rightPressed = false;
              break;
          case 40:
              Survive.this.downPressed = false;
              break;
          case 67:
              Survive.this.cPressed = false;
              break;
          case 73:
              Survive.this.iPressed = false;
              break;
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









