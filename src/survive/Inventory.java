 package survive;
 
 public class Inventory
 {
   protected int itemCode;
   protected int quantity;
   
   public Inventory(int itemCode, int quantity)
   {
     this.itemCode = itemCode;
     this.quantity = quantity;
   }
   
   public int getItemCode()
   {
     return this.itemCode;
   }
   
   public int getQuantity()
   {
     return this.quantity;
   }
   
   public void addQuantity()
   {
     this.quantity += 1;
   }
 }









