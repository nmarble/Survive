package survive;



import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;

import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.awt.image.BufferStrategy;

import java.util.ArrayList;
import java.util.List;
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
  private int playerX = xRes / 2;
  private int playerY = yRes / 2;
  private int treeLikely = 10;
  private int boulderLikely = 20;
  
  private boolean gameRunning = true;
  private boolean inventoryOpen = false;
  private boolean craftingOpen = false;
  private boolean craftingStructure = false;
  
  private boolean logWallReceipe = false;
  
  private List<LowerLayer> lowerLayers = new ArrayList<LowerLayer>();
  private List<MiddleLayer> middleLayers = new ArrayList<MiddleLayer>();
  private List<Hud> huds = new ArrayList<Hud>();
  private List<Inventory> inventorys = new ArrayList<Inventory>();
  private ArrayList removeList = new ArrayList();
  
  public Survive()
  {
   
    JFrame container = new JFrame("Survive");
    

    JPanel panel = (JPanel)container.getContentPane();
    panel.setPreferredSize(new Dimension(xRes, yRes));
    panel.setLayout(null);
    

    setBounds(0, 0, xRes, yRes);
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
    strategy = getBufferStrategy();
    
    initEntities();
    checkMissingFloorAll();
  }
  
  private void startGame()
  {
    lowerLayers.clear();
    initEntities();
    
    leftPressed = false;
    rightPressed = false;
    spacePressed = false;
  }
  
  private void initEntities()
  {
    player = new PlayerEntity(this, "sprites/PlayerN.png", playerX, playerY, "player", 20);
    huds.add(player);
    
    structureButton = new ButtonEntity(this, "sprites/StructureButton.jpg", 0, (yRes - 100), "Structure", 100); 
    huds.add(structureButton);
    
    toolButton = new ButtonEntity(this, "sprites/ToolButton.jpg", 110,(yRes - 100), "Tool",100); 
    huds.add(toolButton);
    
    consumableButton = new ButtonEntity(this, "sprites/ConsumableButton.jpg", 220, (yRes - 100), "Consumable",100); 
    huds.add(consumableButton);
    
    decorativeButton = new ButtonEntity(this, "sprites/DecorativeButton.jpg", 330,(yRes - 100), "Decorative",100); 
    huds.add(decorativeButton);
    
    
    for (int y = 0; y < 5; y++) {
      for (int x = 0; x < 5; x++)
      {
        grass = new GrassEntity(this, "sprites/grass.gif", x * 20, y * 20, "grass");
        lowerLayers.add(grass);
      }
    }
    addFloor(playerX, playerY);
  }
  
  private void checkMissingFloorTop()
  {
    int entityx = 0;
    int entityy = 0;
    
    boolean match = true;
    for (int x = 0; x < xRes / 20; x++)
    {
      match = false; 
      for (LowerLayer lowerLayer : lowerLayers)
      {
       
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
    for (int x = 0; x < xRes / 20; x++)
    {
      match = false;
      for (LowerLayer lowerLayer : lowerLayers)
      {     
        entityx = lowerLayer.getX();
        entityy = lowerLayer.getY();
        if ((entityx == x * 20) && (entityy == yRes)) {
          match = true;
        }
      }
      if (!match)
      {
        int y = yRes;
        addFloor(x * 20, y);
      }
    }
  }
  
  private void checkMissingFloorLeft()
  {
    int entityx = 0;
    int entityy = 0;
    
    boolean match = true;
    for (int y = 0; y < yRes / 20; y++)
    {
      match = false;
      for (LowerLayer lowerLayer : lowerLayers)
      {
        
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
    for (int y = 0; y < yRes / 20; y++)
    {
      match = false;
      for (LowerLayer lowerLayer : lowerLayers)
      {      
        entityx = lowerLayer.getX();
        entityy = lowerLayer.getY();
        if ((entityx == xRes) && (entityy == y * 20)) {
          match = true;
        }
      }
      if (!match)
      {
        int x = xRes;
        addFloor(x, y * 20);
      }
    }
  }
  
  private void checkMissingFloorAll()
  {
    int entityx = 0;
    int entityy = 0;
    
    boolean match = true;
    for (int x = 0; x < xRes / 20; x++) {
      for (int y = 0; y < yRes / 20; y++)
      {
        match = false;
        for (LowerLayer lowerLayer : lowerLayers)
         {
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
  
   for (MiddleLayer middleLayer : middleLayers)
        
       {
      //MiddleLayer object = (MiddleLayer)middleLayers.get(i);
      if ((direction == "up") && (playerX == middleLayer.getX() + middleLayer.getModifiedX()) && (playerY == middleLayer.getY() + middleLayer.getModifiedY() + 20)) {
        middleLayer.interact();
      }
      if ((direction == "down") && (playerX == middleLayer.getX() + middleLayer.getModifiedX()) && (playerY == middleLayer.getY() + middleLayer.getModifiedY() - 20)) {
        middleLayer.interact();
      }
      if ((direction == "left") && (playerX == middleLayer.getX() + middleLayer.getModifiedX() + 20) && (playerY == middleLayer.getY() + middleLayer.getModifiedY())) {
        middleLayer.interact();
      }
      if ((direction == "right") && (playerX == middleLayer.getX() + middleLayer.getModifiedX() - 20) && (playerY == middleLayer.getY() + middleLayer.getModifiedY())) {
        middleLayer.interact();
      }
    }
  }
  public String mouseInteract(int x, int y)
  {
      String type = "none";
      //Check if button is there
      for (Hud hud : huds)
      {
        if (hud instanceof PlayerEntity)
        {
            continue;
        }
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
    removeList.add(object);
  }
  public void removeButton(Hud object)
  {
    removeList.add(object);
  }
  public void addToInventory(int itemCode, int quantity)
  {
    if (inventorys.isEmpty())
    {
      log = new Inventory("sprites/log.png", 1, 0);
      inventorys.add(log);
      stone = new Inventory("sprites/stone.png", 2, 0);
      inventorys.add(stone);
      logWall = new Inventory("sprites/LogWall.gif", 3, 0);
      inventorys.add(logWall);
    }
    for (Inventory inventory : inventorys)
      {
      if (inventory.getItemCode() == itemCode)
      {
        
        inventory.addQuantity(quantity);
   
      }
    }
  }
  public void removeFromInventory (int itemCode, int quantity)
  {
    for (Inventory inventory : inventorys)
      {
      if (inventory.getItemCode() == itemCode)
      {
        inventory.removeQuantity(quantity);
     
      }
    }  
  }
  public void checkCollisionObject(String direction)
  {
    for (MiddleLayer middleLayer : middleLayers)
      {
      String type = middleLayer.getType();
      int modifiedX = middleLayer.getModifiedX();
      int modifiedY = middleLayer.getModifiedY();
      if (middleLayer.collideWith(middleLayer.getX() + modifiedX, middleLayer.getY() + modifiedY, playerX, playerY) == true)
      {
        if ("left".equals(direction)) {
          moveAll("right");
        }
        if ("right".equals(direction)) {
          moveAll("left");
        }
        if ("down".equals(direction)) {
          moveAll("up");
        }
        if ("up".equals(direction)) {
          moveAll("down");
        }
      }
    }
  }
  
  public void addFloor(int x, int y)
  {
    int grassChance = 2;
    int gravelChance = 0;
    for (LowerLayer lowerLayer : lowerLayers)
    {
      int entityx = lowerLayer.getX();
      int entityy = lowerLayer.getY();
      if ((entityx >= x - 20) && (entityx <= x + 20) && (entityy >= y - 20) && (entityy <= y + 20))
      {
        String type = lowerLayer.getType();
        if ("grass".equals(type)) {
          grassChance++;
        }
        if ("gravel".equals(type)) {
          gravelChance++;
        }
      }
    }
    int randomGrass = (int)(Math.random() * grassChance);
    int randomGravel = (int)(Math.random() * gravelChance);
    if (randomGrass > randomGravel)
    {
      grass = new GrassEntity(this, "sprites/grass.gif", x, y, "grass");
      lowerLayers.add(grass);
      
      int Chance = (int)(Math.random() * treeLikely);
      if (Chance == 1)
      {
        tree = new TreeEntity(this, "sprites/tree.png", x, y - 20, "tree");
        middleLayers.add(tree);
      }
 
    }
    else
    {
      gravel = new GravelEntity(this, "sprites/gravel.gif", x, y, "gravel");
      lowerLayers.add(gravel);
      
      int Chance = (int)(Math.random() * boulderLikely);
      if (Chance == 1)
      {
        boulder = new BoulderEntity(this, "sprites/boulder.png", x, y, "boulder");
        middleLayers.add(boulder);
      }
    }
  }
  
  public void moveAll(String direction)
  {
    for (MiddleLayer object : middleLayers)
    {
      
      if ("left".equals(direction)) {
        object.moveLeft(movementSpeed);
      }
      if ("right".equals(direction)) {
        object.moveRight(movementSpeed);
      }
      if ("up".equals(direction)) {
        object.moveUp(movementSpeed);
      }
      if ("down".equals(direction)) {
        object.moveDown(movementSpeed);
      }
    }
    for (LowerLayer lowerLayer : lowerLayers)
    {
    
      if ("left".equals(direction)) {
        lowerLayer.moveLeft(movementSpeed);
      }
      if ("right".equals(direction)) {
        lowerLayer.moveRight(movementSpeed);
      }
      if ("up".equals(direction)) {
        lowerLayer.moveUp(movementSpeed);
      }
      if ("down".equals(direction)) {
        lowerLayer.moveDown(movementSpeed);
      }
    }
  }
  public void checkButtonPushed ()
  {
      if (cPressed)
      {
          craftingOpen = !craftingOpen;
          if (craftingStructure = true)
          {
              craftingStructure = false;
          }
      }
      if (iPressed)
      {
          inventoryOpen = !inventoryOpen;
      }
      if (leftPressed)
      {
        direction = "left";
            
        moveAll(direction);
        
        for (int i = 0; i < 1; i++)
        {
          Hud hud = huds.get(i);
          hud.changeFrame(2);
        }
        checkMissingFloorLeft();
        checkCollisionObject(direction);
      }
      if (rightPressed)
      {
        direction = "right";
        checkMissingFloorRight();
        
        moveAll(direction);
        for (int i = 0; i < 1; i++)
        {
          Hud hud = huds.get(i);
          hud.changeFrame(3);
        }
        checkCollisionObject(direction);
      }
      if (upPressed)
      {
        direction = "up";
        
        moveAll(direction);
        for (int i = 0; i < 1; i++)
        {
          Hud hud = huds.get(i);
          hud.changeFrame(0);
        }
        checkMissingFloorTop();
        checkCollisionObject(direction);
      }
      if (downPressed)
      {
        direction = "down";
        checkMissingFloorBottom();
        
        moveAll(direction);
        for (int i = 0; i < 1; i++)
        {
          Hud hud = huds.get(i);
          hud.changeFrame(1);
        }
        checkCollisionObject(direction);
      }
      if (spacePressed) {
        interact();
      }  
  }
  public void drawInventory()
  {
      //Draw Inventory screen
      Graphics2D g = (Graphics2D)strategy.getDrawGraphics();
      g.setColor(Color.GRAY);
      g.fill3DRect(xRes - (xRes / 5), 0, yRes, xRes, true);
      g.setColor(Color.BLACK);
      g.drawLine(xRes - (xRes / 5), (yRes / 2), xRes, (yRes / 2));
      g.dispose();
      
      
     
  }
  public void removeAllButtons()
  {

      for (int i = 5; i < huds.size(); i++)
      {
          Hud hud = huds.get(i);
          removeButton(hud);
      }
  }
  public void findAvailReceipe()
  {
      int logQuantity = 0;
      int stoneQuantity = 0;
      int x = 0;
      int y = yRes - 30;
      for (Inventory inventory : inventorys)      
      {
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
          
          structureButton = new ButtonEntity(this, "sprites/logWall.gif", x, y, "LogWall", 20); 
          huds.add(structureButton);
          x = x + 25;
      }
      
  }
  
  public void gameLoop()
  {
    
    long lastLoopTime = System.currentTimeMillis();
    while (gameRunning)
    {
      long delta = System.currentTimeMillis() - lastLoopTime;
      lastLoopTime = System.currentTimeMillis();
      

      
      Graphics2D g = (Graphics2D)strategy.getDrawGraphics();
      g.setColor(Color.black);
      g.fillRect(0, 0, xRes, yRes);
     
      
      checkButtonPushed();
      for (LowerLayer lowerLayer : lowerLayers)
      {
        int entityX = lowerLayer.getX();
        int entityY = lowerLayer.getY();
        if ((entityX >= 0) && (entityY >= 0) && (entityX <= xRes) && (entityY <= yRes)) {
          lowerLayer.draw(g);
        }
      }
      middleLayers.removeAll(removeList);
      huds.removeAll(removeList);
      
      removeList.clear();
      huds.get(0).draw(g);
      
      
      for (MiddleLayer middleLayer : middleLayers)
      {
        middleLayer.draw(g);
      }
      
      if (craftingOpen == true)
      {    
      for (int i = 1; i <= 4; i++)
      {
        Hud hud = huds.get(i);
        hud.draw(g);
      }
         
      }
      
      
      if (craftingStructure == true)
      {
          int x = 0;
          removeAllButtons();
          findAvailReceipe();
          for (Hud hud : huds)
            {
                if (hud.getType() == "LogWall" && logWallReceipe == true)
                {
                hud.draw(g);
                x = x + 25;
                }
            }
      
          
      }
      if (waitingForKeyPress)
      {
        g.setColor(Color.white);
        g.drawString(message, (xRes - g.getFontMetrics().stringWidth(message)) / 2, 250);
        g.drawString("Press any key", (xRes - g.getFontMetrics().stringWidth("Press space to continue")) / 2, (yRes / 2));
      }
      if (inventoryOpen == true)
      {    
          drawInventory();
          int col = -20;
          int row = 1;
          for (Inventory inventory : inventorys)
            {
            if (inventory.getQuantity() > 0)
            {
                col = col + 25;
            
            
            inventory.changeX(xRes - (xRes / 5) + (col));
            inventory.changeY((yRes / 2) + (15 * row));
            
            inventory.draw(g);
            String quantity = String.valueOf(inventory.getQuantity());
            g.drawString(quantity, inventory.getX(), inventory.getY());
            }
         }
      }
      strategy.show();
      
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
          
          if (craftingOpen == true && !"none".equals(what))
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
      if (waitingForKeyPress) {
        return;
      }
      switch(e.getKeyCode()) {
          
          case 32:
              spacePressed = true;
              break;
          case 37:
              leftPressed = true;
              break;
          case 38:
              upPressed = true;
              break;
          case 39:
              rightPressed = true;
              break;
          case 40:
              downPressed = true;
              break;
          case 67:
              cPressed = true;
              break;
          case 73:
              iPressed = true;
              break;
      }
     
    }
    
    public void keyReleased(KeyEvent e)
    {
      if (waitingForKeyPress) {
        return;
      }
      switch(e.getKeyCode()) {
          
          case 32:
              spacePressed = false;
              break;
          case 37:
              leftPressed = false;
              break;
          case 38:
              upPressed = false;
              break;
          case 39:
              rightPressed = false;
              break;
          case 40:
              downPressed = false;
              break;
          case 67:
              cPressed = false;
              break;
          case 73:
              iPressed = false;
              break;
      }
    }
    
    public void keyTyped(KeyEvent e)
    {
      if (waitingForKeyPress) {
        if (pressCount == 1)
        {
          waitingForKeyPress = false;
          
          pressCount = 0;
        }
        else
        {
          pressCount += 1;
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









