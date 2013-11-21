package survive;



import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
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

import survive.Entities.*;

public class Survive
  extends Canvas
{
  private BufferStrategy strategy;
  
  int totalRandom = 3;
  int[] randomChance = new int[totalRandom];
 
  
  
  private LowerLayer grass;
  private LowerLayer gravel;
  private LowerLayer water;
  
  private MiddleLayer tree;
  private MiddleLayer boulder;
  private MiddleLayer LogWall;
  private MiddleLayer Log;
  private MiddleLayer Stone;
  
  private EnemyLayer zombie;
  
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
  
  private int treeLikely = 10;
  private int boulderLikely = 20;

  
  private int itemSelection = 0;
  private int zombieChance = 100;
  private boolean gameRunning = true;
  private boolean inventoryOpen = false;
  private boolean craftingOpen = false;
  private boolean craftingStructure = false;
  private boolean holdingItem = false;
  
  private boolean logWallReceipe = false;
  
  private List<LowerLayer> lowerLayers = new ArrayList<LowerLayer>();
  private List<MiddleLayer> middleLayers = new ArrayList<MiddleLayer>();
  private List<Hud> huds = new ArrayList<Hud>();
  private List<Inventory> inventorys = new ArrayList<Inventory>();
  private List<EnemyLayer> enemyLayers = new ArrayList<EnemyLayer>();
  private ArrayList removeList = new ArrayList();
  
  
  
  public Survive()
  {
    JFrame container = new JFrame("Survive");
    
    JPanel panel = (JPanel)container.getContentPane();
    panel.setPreferredSize(new Dimension(Global.xRes, Global.yRes));
    panel.setLayout(null);
    
    setBounds(0, 0, Global.xRes, Global.yRes);
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
    //Add player entity
    player = new PlayerEntity(this, "sprites/PlayerN.png", Global.playerX, Global.playerY, "player", 20);
    huds.add(player);
    //Add Crafting buttons
    structureButton = new ButtonEntity(this, "sprites/StructureButton.jpg", 0, (Global.yRes - 100), "Structure", 100); 
    huds.add(structureButton);
    
    toolButton = new ButtonEntity(this, "sprites/ToolButton.jpg", 110,(Global.yRes - 100), "Tool",100); 
    huds.add(toolButton);
    
    consumableButton = new ButtonEntity(this, "sprites/ConsumableButton.jpg", 220, (Global.yRes - 100), "Consumable",100); 
    huds.add(consumableButton);
    
    decorativeButton = new ButtonEntity(this, "sprites/DecorativeButton.jpg", 330,(Global.yRes - 100), "Decorative",100); 
    huds.add(decorativeButton);
    
    //Add starting grass
    for (int y = 0; y < 5; y++) {
      for (int x = 0; x < 5; x++)
      {
        grass = new GrassEntity(this, "sprites/grass.gif", x * 20, y * 20, "grass");
        lowerLayers.add(grass);
      }
    }
    addFloor(Global.playerX, Global.playerY);
  }
  
  //Set cursor type
  public void getCursorType (int type)
  {
      switch (type)
      {
          case 1:
          setCursor(
                new Cursor(Cursor.DEFAULT_CURSOR));
              break;
          case 2:
          setCursor(
                new Cursor(Cursor.MOVE_CURSOR));
              break;
      }
  }

  private void checkMissingFloorTop()
  {
    int entityx = 0;
    int entityy = 0; 
    boolean match = true;
    
    for (int x = 0; x < Global.xRes / 20; x++)
    {
      match = false; 
      
      for (LowerLayer lowerLayer : lowerLayers)
      {
        entityx = lowerLayer.getX();
        entityy = lowerLayer.getY();
        
        if ((entityx == x * 20) && (entityy == 0)) 
        {
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
    
    for (int x = 0; x < Global.xRes / 20; x++)
    {
      match = false;
  
      for (LowerLayer lowerLayer : lowerLayers)
      {     
        entityx = lowerLayer.getX();
        entityy = lowerLayer.getY();
        
        if ((entityx == x * 20) && (entityy == Global.yRes)) 
        {
          match = true;
        }
      }
      
      if (!match)
      {
        int y = Global.yRes;
        addFloor(x * 20, y);
      }
    }
  }
  
  private void checkMissingFloorLeft()
  {
    int entityx = 0;
    int entityy = 0;
    boolean match = true;
    
    for (int y = 0; y < Global.yRes / 20; y++)
    {
      match = false;
      
      for (LowerLayer lowerLayer : lowerLayers)
      {
        entityx = lowerLayer.getX();
        entityy = lowerLayer.getY();
        
        if ((entityx == 0) && (entityy == y * 20)) 
        {
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
    for (int y = 0; y < Global.yRes / 20; y++)
    {
      match = false;
      
      for (LowerLayer lowerLayer : lowerLayers)
      {      
        entityx = lowerLayer.getX();
        entityy = lowerLayer.getY();
        
        if ((entityx == Global.xRes) && (entityy == y * 20)) 
        {
          match = true;
        }
      }
      if (!match)
      {
        int x = Global.xRes;
        addFloor(x, y * 20);
      }
    }
  }
  
  private void checkMissingFloorAll()
  {
    int entityx = 0;
    int entityy = 0;
    boolean match = true;
    
    for (int x = 0; x < Global.xRes / 20; x++) {
      for (int y = 0; y < Global.yRes / 20; y++)
      {
        match = false;
        
        for (LowerLayer lowerLayer : lowerLayers)
         {
          entityx = lowerLayer.getX();
          entityy = lowerLayer.getY();
          if ((entityx == x * 20) && (entityy == y * 20)) 
          {
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
      
      if (("up".equals(direction)) && (Global.playerX == middleLayer.getX() + middleLayer.getModifiedX()) && (Global.playerY == middleLayer.getY() + middleLayer.getModifiedY() + 20)) {
        middleLayer.interact();
      }
      if (("down".equals(direction)) && (Global.playerX == middleLayer.getX() + middleLayer.getModifiedX()) && (Global.playerY == middleLayer.getY() + middleLayer.getModifiedY() - 20)) {
        middleLayer.interact();
      }
      if (("left".equals(direction)) && (Global.playerX == middleLayer.getX() + middleLayer.getModifiedX() + 20) && (Global.playerY == middleLayer.getY() + middleLayer.getModifiedY())) {
        middleLayer.interact();
      }
      if (("right".equals(direction)) && (Global.playerX == middleLayer.getX() + middleLayer.getModifiedX() - 20) && (Global.playerY == middleLayer.getY() + middleLayer.getModifiedY())) {
        middleLayer.interact();
      }
    }
   for (EnemyLayer enemyLayer : enemyLayers) 
   {
      
      if (("up".equals(direction)) && (Global.playerX == enemyLayer.getX() + enemyLayer.getModifiedX()) && (Global.playerY == enemyLayer.getY() + enemyLayer.getModifiedY() + 20)) {
        enemyLayer.interact();
      }
      if (("down".equals(direction)) && (Global.playerX == enemyLayer.getX() + enemyLayer.getModifiedX()) && (Global.playerY == enemyLayer.getY() + enemyLayer.getModifiedY() - 20)) {
        enemyLayer.interact();
      }
      if (("left".equals(direction)) && (Global.playerX == enemyLayer.getX() + enemyLayer.getModifiedX() + 20) && (Global.playerY == enemyLayer.getY() + enemyLayer.getModifiedY())) {
        enemyLayer.interact();
      }
      if (("right".equals(direction)) && (Global.playerX == enemyLayer.getX() + enemyLayer.getModifiedX() - 20) && (Global.playerY == enemyLayer.getY() + enemyLayer.getModifiedY())) {
        enemyLayer.interact();
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
      //Check if inventory is there
      for (Inventory inventory : inventorys)
      {
          int xLimit = inventory.getX() + 20;
          int yLimit = inventory.getY() + 20;
          if (x >= inventory.getX() && y >= inventory.getY() && x <= xLimit && y <= yLimit)
          {
              type = String.valueOf(inventory.getItemCode());
              
          }
      }
     return type;
  }
  public void removeMiddleLayer(MiddleLayer object)
  {
    removeList.add(object);
  }
  public void removeEnemyLayer(EnemyLayer object)
  {
    removeList.add(object);
  }
  public void removeButton(Hud object)
  {
    removeList.add(object);
  }
  public void addToInventory(int itemCode, int quantity)
  {
    //Add entities if none are available
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
 
      if (middleLayer.collideWithPlayer() == true && middleLayer.passable() == false)
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
  public boolean checkForMoreItem(int itemCode)
  {
      boolean hasIt = false;
      for (Inventory inventory : inventorys)
      {
      if (inventory.getItemCode() == itemCode)
      {
        if (inventory.getQuantity() > 0)
        {
            hasIt = true;
        }
        else
        {
            hasIt = false;
        }
      }
    } 
      return hasIt;
  }
  public void setItemDown(int x, int y)
  {
      switch (itemSelection)
      {
          case 1:
              Log = new LogEntity(this, "sprites/log.png", x, y, "log");
              middleLayers.add(Log);
              removeFromInventory(1,1);
              break;
          case 2:
              Stone = new StoneEntity(this, "sprites/stone.png", x, y, "stone");
              middleLayers.add(Stone);
              removeFromInventory(2,1);
              break;
          case 3:
              LogWall = new LogWallEntity(this, "sprites/logWall.gif", x, y, "logWall");
              middleLayers.add(LogWall);
              removeFromInventory(3,1);
              break;
  
      }
     
  }
  public void addFloor(int x, int y)
  {
    
    for (LowerLayer lowerLayer : lowerLayers)
    {
      int entityx = lowerLayer.getX();
      int entityy = lowerLayer.getY();
      if ((entityx >= x - 20) && (entityx <= x + 20) && (entityy >= y - 20) && (entityy <= y + 20))
      {
        String type = lowerLayer.getType();
        if ("grass".equals(type)) {
          randomChance[0]++;
          
        }
        if ("gravel".equals(type)) {
          randomChance[1]++;
          
        }
        if ("water".equals(type)) {
          randomChance[2]++;
          
        }
      }
    }
    
    int chance = 0;
    switch (getRandomGround())
    {
        case 0:
            grass = new GrassEntity(this, "sprites/grass.gif", x, y, "grass");
            lowerLayers.add(grass);
      
            chance = getRandomNum(treeLikely);
            if (chance == 1)
            {
                tree = new TreeEntity(this, "sprites/tree.png", x, y - 20, "tree");
                middleLayers.add(tree);
            }
            break;
        case 1:
            gravel = new GravelEntity(this, "sprites/gravel.gif", x, y, "gravel");
            lowerLayers.add(gravel);
      
            chance = getRandomNum(boulderLikely);
            if (chance == 1)
            {
                boulder = new BoulderEntity(this, "sprites/boulder.png", x, y, "boulder");
                middleLayers.add(boulder);
            } 
            break;
        case 2:
            water = new WaterEntity(this, "sprites/water.gif", x, y, "water");
            lowerLayers.add(water);
            break;
            
          
    }
 
  }
  public int getRandomGround()
  {
      randomChance[0] = 50;    //Grass
      randomChance[1] = 25;    //Gravel
      randomChance[2] = 10;    //Water
     
      int choice = 0;
      int choiceNum = 0;
      
      for (int i = 0; i < totalRandom; i++ )
      {
          int chance = (int)(Math.random() * randomChance[i]);
          
          if (chance > choice)
          {
              choice = chance;
              choiceNum = i;
          }
      }
      
      return choiceNum;
  }
  public int getRandomNum(int numRange)
  {
      int num = (int)(Math.random() * numRange);
      return num;
  }
  public void moveAll(String direction)
  {
    for (EnemyLayer enemyLayer : enemyLayers)
    {
    
      if ("left".equals(direction)) {
        enemyLayer.moveLeft(movementSpeed);
      }
      if ("right".equals(direction)) {
        enemyLayer.moveRight(movementSpeed);
      }
      if ("up".equals(direction)) {
        enemyLayer.moveUp(movementSpeed);
      }
      if ("down".equals(direction)) {
        enemyLayer.moveDown(movementSpeed);
      }
    }
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
      g.fill3DRect(Global.xRes - (Global.xRes / 5), 0, Global.yRes, Global.xRes, true);
      g.setColor(Color.BLACK);
      g.drawLine(Global.xRes - (Global.xRes / 5), (Global.yRes / 2), Global.xRes, (Global.yRes / 2));
      g.dispose();
      
      
     
  }
  //Removes all craftable item buttons
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
      //Set location to put icons
      int x = 0;
      int y = Global.yRes - 30;
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
      //Log wall receipe
      if (logQuantity >= 4)   
      {
          logWallReceipe = true;
          
          structureButton = new ButtonEntity(this, "sprites/logWall.gif", x, y, "LogWall", 20); 
          huds.add(structureButton);
          x = x + 25;
      }
  }
  
  public long moveEnemy(long loopTime)
  {
     for (EnemyLayer enemyLayer : enemyLayers)
      {
          if (10 ==  loopTime)
          {
          //Get location of enemy
          int enemyXLoc = enemyLayer.getX();
          int enemyYLoc = enemyLayer.getY();
          //Get location of Player
          int endXLoc = Global.playerX;
          int endYLoc = Global.playerY;
          // Int a bunch of stuff
          int xLocCoor[] = new int[9];  //the x location of a possible move
          int yLocCoor[] = new int[9];  //the y lovation of a possible move
          boolean pasLocCoor[] = new boolean[9]; //Stores if the location is passable
          int xSector = -20; 
          int ySector = -20;
          int bT = 9999; //The least distance from a block
          int nextX = 0; //What the next location will be
          int nextY = 0; 
          int secX = 0;  //The secondary location should x fail
          int secY = 0;     
          int dT, xD, yD, sT;
          for (int i = 0; i <= 8; i++)
          {
              xLocCoor[i] = enemyXLoc + xSector;
              yLocCoor[i] = enemyYLoc + ySector;
              xSector = xSector + 20;
              if (xSector > 20)
              {
                  xSector = -20;
                  ySector = ySector + 20;
              }
              for (LowerLayer lowerLayer : lowerLayers)
              {
                  if (xLocCoor[i] == lowerLayer.getX() && yLocCoor[i] == lowerLayer.getY())
                  {
                      pasLocCoor[i] = lowerLayer.passable();
                  }
              }
              for (MiddleLayer middleLayer : middleLayers)
              {
                  if (xLocCoor[i] == (middleLayer.getX() + middleLayer.getModifiedX()) && yLocCoor[i] == (middleLayer.getY() + middleLayer.getModifiedY()))
                  {
                      pasLocCoor[i] = middleLayer.passable();
                  }
              }
              for (EnemyLayer tempEnemy : enemyLayers)
              {
                  if (xLocCoor[i] == tempEnemy.getX() && yLocCoor[i] == tempEnemy.getY())
                  {
                      pasLocCoor[i] = tempEnemy.passable();
                  }
              }   
          }
          // Checks for best location that is passable
          for (int i = 0; i <=8; i++)
          {
              if (pasLocCoor[i] == true && i != 4)
              {
              xD = Math.abs(xLocCoor[i] - Global.playerX); 
              yD = Math.abs(yLocCoor[i] - Global.playerY); 
              
              dT = (xD + yD);
              if (dT <= bT)
              {
                  sT = bT;
                  secX = nextX;
                  secY = nextY;
                  bT = dT;
                   
                  nextX = xLocCoor[i];
                  nextY = yLocCoor[i];
              }
              }
             
          }
          // Change graphics of enemy
          if (nextX > enemyLayer.getX())
          {
              enemyLayer.changeDirection("right");
          }
          if (nextX < enemyLayer.getX())
          {
              enemyLayer.changeDirection("left");
          }
          if (nextY > enemyLayer.getY())
          {
              enemyLayer.changeDirection("down");
          }
          if (nextY < enemyLayer.getY())
          {
              enemyLayer.changeDirection("up");
          }
          
          // Sets next location to secondary if it was the last one
          if (enemyLayer.getLastX() == nextX && enemyLayer.getLastY() == nextY)
          {
              nextX = secX;
              nextY = secY;         
          }
          // Doesnt allow map jump
          if (nextX == 0 && nextY == 0)
          {
              nextX = enemyLayer.getLastX();
              nextY = enemyLayer.getLastY();
          }
          // Sets last Location and moves to new
          enemyLayer.setLast(enemyLayer.getX(), enemyLayer.getY());
          enemyLayer.setXY(nextX, nextY);
          if (enemyLayer.getX() == Global.playerX && enemyLayer.getY() == Global.playerY)
          {
             System.exit(0);
          }
          }                      
      }
     
     return loopTime;
  }
  //Loop of main game
  public void gameLoop()
  {
    long lastLoopTime = System.currentTimeMillis();
    long loopTime = 0;
    
    while (gameRunning)
    {
      long delta = System.currentTimeMillis() - lastLoopTime;
      loopTime ++;
      lastLoopTime = System.currentTimeMillis();
     

      
      Graphics2D g = (Graphics2D)strategy.getDrawGraphics();
      g.setColor(Color.black);
      g.fillRect(0, 0, Global.xRes, Global.yRes);
      // Moves applicable enemy within its speed compared to loop time
      loopTime = moveEnemy(loopTime);

      //Chance to add zombie
      int randomZombie = (int)(Math.random() * zombieChance);
      if (randomZombie == 1)
      {
        zombie = new ZombieEntity(this, "sprites/ZombieN1.png", 0, Math.round((int) (Math.random() * Global.xRes)/20) * 20, "zombie", "N");
        enemyLayers.add(zombie);   
      }
      
      checkButtonPushed();
      //Draw all ground that is on screen
      for (LowerLayer lowerLayer : lowerLayers)
      {
        int entityX = lowerLayer.getX();
        int entityY = lowerLayer.getY();
        if ((entityX >= 0) && (entityY >= 0) && (entityX <= Global.xRes) && (entityY <= Global.yRes)) {
          lowerLayer.draw(g);
        }
      }
      //What is to be removed gets removed
      middleLayers.removeAll(removeList);
      huds.removeAll(removeList);
      enemyLayers.removeAll(removeList);
      
      //Resets what is to be removed
      removeList.clear();
      
      //Draws Player
      huds.get(0).draw(g);
      
      //Draw all Enemys
      for (EnemyLayer enemyLayer : enemyLayers)
      {
          enemyLayer.draw(g);
      }
      //Draw all Objects
      for (MiddleLayer middleLayer : middleLayers)
      {
        middleLayer.draw(g);
      }
      //When Crafting is open
      if (craftingOpen == true)
      {    
      for (int i = 1; i <= 4; i++)
      {
        Hud hud = huds.get(i);
        hud.draw(g);
      }     
      }
      //When Crafting for structures is open
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
      //Change holding status if nothing is there
      if (holdingItem == true && checkForMoreItem(itemSelection) == false)
      {
          itemSelection = 0;
          holdingItem = false;
      }
      //Change cursor for holding item
      if (holdingItem == true)
      {
          getCursorType(2);
      }
      //Change cursor for not holding item
      if (holdingItem == false)
      {
          getCursorType(1);
      }
      
      if (waitingForKeyPress)
      {
        g.setColor(Color.white);
        g.drawString(message, (Global.xRes - g.getFontMetrics().stringWidth(message)) / 2, 250);
        g.drawString("Press any key", (Global.xRes - g.getFontMetrics().stringWidth("Press space to continue")) / 2, (Global.yRes / 2));
      }
      //Draws inventory background and items
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
                inventory.changeX(Global.xRes - (Global.xRes / 5) + (col));
                inventory.changeY((Global.yRes / 2) + (15 * row));
            
                inventory.draw(g);
                String quantity = String.valueOf(inventory.getQuantity());
                g.drawString(quantity, inventory.getX(), inventory.getY());
            }
         }
      }
      
      strategy.show();
      if (loopTime > 10)
      {
          loopTime = 0;
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
          int whatNumber = 0;
          try {
          whatNumber = Integer.parseInt(what);
          }
          catch (NumberFormatException a)
          {
              System.err.println("Nothing there");
          }
          if (whatNumber > 0 && holdingItem == false)
          {
              itemSelection = whatNumber;
              holdingItem = true;
          }
          if ("none".equals(what) && holdingItem == true)
          {
              int x = Math.round(e.getX()/20) * 20;
              int y = Math.round(e.getY()/20) * 20;
              setItemDown(x,y);
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









