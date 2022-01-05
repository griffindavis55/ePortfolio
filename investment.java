package ePortfolio;
/**this class is the parent class of the childrens class mutual funds and stock. it holds all attributes that are similar to both classes */
public abstract class investment {

    protected String symbol;
    protected String name;
    protected int quantity;
    protected double price;
    protected double bookValue;

    /** method to set the symbol of a object*/
    public void setSymbol(String inputSymbol) throws Exception{
        if(inputSymbol.isEmpty()){
            throw new Exception("Symbol can not be empty\n");
        }else{
            this.symbol = inputSymbol;
        }
       
    }
    /** method to set the name of a object*/
    public void setName(String inputName) throws Exception{
        if(inputName.isEmpty()){
            throw new Exception("Name can not be empty\n");
        }else{
            this.name = inputName;
        }
    }
    /** method to set the quantity of a object*/
    public void setQuantity(Integer inputQuantity) throws Exception{ //need further testing
        if(!(inputQuantity instanceof Integer)){
            throw new Exception("quantity must be a numerical value and not empty\n");
        }else{
            this.quantity = inputQuantity;
        }
    }
    /** method to set the price of a object*/
    public void setPrice(Double inputPrice) throws NumberFormatException{
        if(!(inputPrice instanceof Double)){
            throw new NumberFormatException("price must be a numerical value and not empty\n");
        }else{
            this.price = inputPrice;
        }
    }
    /** method to set the book value of a object*/
    public abstract void setBookValue(double inputPrice, int inputQuantity);
    /** method to sell some of a investment */
    public abstract double sellPartial(int newQuantity, double newPrice);
    /** method to sell all of a investment */
    public abstract double sellFull(double newPrice);
    /** method to get the symbol of a object*/
    public String getSymbol(){
        return symbol;
    }
    /** method to get the name of a object*/
    public String getName(){
        return name;
    }
    /** method to get the quantity of a object*/
    public int getQuantity(){
        return quantity;
    }
    /** method to get the price of a object*/
    public double getPrice(){
        return price;
    }
    /** method to get the book value of a object*/
    public double getBookValue(){
       return bookValue;
    }

    /** This method is used during the getGain process of the command loop. When the user puts in a new price the gain
    from the book value is then calculated using the new price and a quantity and is then returned as a double*/
    public double gain(){
        double Gain;
        Gain = (this.quantity * this.price) - this.bookValue;
        return Gain;
    }

    /** This method is only used when you are buying more of a mutualFund unit
    the method takes a new quantity and a new price and adds the value of the new purchase to the prior
    book value of the object*/
    public void buyMore(int newQuantity, double newPrice){
        this.quantity += newQuantity;
        this.price = (newPrice);
        this.bookValue += (newQuantity * newPrice);
    }    

    /** This method turns the specified object into a output string to increase readability to the user*/
    public String toString(){
        return "name: " + this.getName() + "\nsymbol: " + this.getSymbol() + "\nquantity: " + this.getQuantity() + "\nprice: " + this.getPrice() + "\nBook Value: " + this.getBookValue();
    }
    
    /**method for file output */
    public String toOutput(String inputType){
        
        return "type = \"" + inputType +"\"\nsymbol = \"" + this.getSymbol() + "\"\nname = \"" + this.getName() + "\"\nquantity = \"" + this.getQuantity() + "\"\nprice = \"" + this.getPrice() + "\"\nBookvalue = \"" + this.getBookValue() + "\"\n";
    }
     /** this method checks if a given mutualFund is equal to the other mutualFund.*/
    public Boolean equals(investment input){
        if (this.name == input.name && this.symbol == input.symbol && this.quantity == input.quantity && this.price == input.price && this.bookValue == input.bookValue){
            return true;
        } else {
            return false;
        }
    }

}
/**this class extends the investments class it holds the attributes and methods that differ from both parent and the stock class */
class Mutualfund extends investment{
    public Mutualfund(){
        this.symbol = "";
        this.name = "";
        this.quantity = 0;
        this.price = 0.0;
        this.bookValue = 0.0;
    }
    public Mutualfund(Mutualfund mutualfund){
        this.symbol = mutualfund.symbol;
        this.name = mutualfund.name;
        this.quantity = mutualfund.quantity;
        this.price = mutualfund.price;
        this.bookValue = mutualfund.bookValue;
    }

    /** This method takes in a new price for an investment and changes the book value of that object */
    public void update(double newPrice) throws NumberFormatException{
        this.price = newPrice;
        this.bookValue = (this.quantity * newPrice) + 45;
    } 
 
     /** This method is used when the user only wants to sell part of their units. 
    The method takes in a quantity of units to sell and at what price and then calculates the gain they will get. 
    The book value is also updated for the specified object and the gain is returned as a double*/
    public double sellPartial(int newQuantity, double newPrice){
        double payment= (newQuantity * newPrice) - 45;
        double gain = payment - (this.bookValue * (newQuantity / this.quantity));
        this.bookValue = this.bookValue * (newQuantity / this.quantity);
        this.quantity -= newQuantity;
        return gain;
    } 
    /** This method is used when the user wants to sell all their units in a mutual Fund
    The method takes in the current price of the investment, calculates a payment based on price total quantity,
    then calculates and outputs the gain as a double to the user.*/
    public double sellFull(double newPrice){
        double payment = (this.quantity * newPrice) - 45;
        double gain = payment - this.bookValue;
        return gain;
    }
    /**sets book value for mutual fund */
    public void setBookValue(double inputPrice, int inputQuantity) throws NumberFormatException{
        this.bookValue = (inputPrice * inputQuantity);
    }
    @Override
    public boolean equals(Object investment){
        return (investment == null || this.getClass() != investment.getClass() || !this.symbol.equals(((Mutualfund)investment).symbol) || !this.name.equals(((Mutualfund)investment).name) || this.quantity != ((Mutualfund)investment).quantity || this.price != ((Mutualfund)investment).price || this.bookValue != ((Mutualfund)investment).bookValue) ? true : false;
    }

}

/**this class extends the investments class it holds the attributes and methods that differ from both parent and the mutualfund class */
class Stock extends investment{
    public boolean equals(Object investment){
        return (investment == null || this.getClass() != investment.getClass() || !this.symbol.equals(((Stock)investment).symbol) || !this.name.equals(((Stock)investment).name) || this.quantity != ((Stock)investment).quantity || this.price != ((Stock)investment).price || this.bookValue != ((Stock)investment).bookValue) ? true : false;
    }

    public Stock(){
        this.symbol = "";
        this.name = "";
        this.quantity = 0;
        this.price = 0.0;
        this.bookValue = 0.0;
    }
    
    public Stock(Stock stock){
        this.symbol = stock.symbol;
        this.name = stock.name;
        this.quantity = stock.quantity;
        this.price = stock.price;
        this.bookValue = stock.bookValue;
    }

      /** This method takes in a new price for an investment and changes the book value of that object */
      public void update(double newPrice){
          this.price = newPrice;
          this.bookValue = (this.quantity * newPrice) + 9.99;
      } 

    /** This method is used when the user only wants to sell part of their shares. 
    The method takes in a quantity of shares to sell and at what price and then calculates the gain they will get. 
    The book value is also updated for the specified object and the gain is returned as a double*/
    public double sellPartial(int newQuantity, double newPrice){
        double payment= (newQuantity * newPrice) - 9.99;
        double gain = payment - this.bookValue * (newQuantity / this.quantity);
        this.bookValue = this.bookValue * (newQuantity / this.quantity);
        this.quantity -= newQuantity;
        return gain;
    } 
    /** This method is used when the user wants to sell all their shares in a stock
    The method takes in the current price of the investment, calculates a payment based on price total quantity,
    then calculates and outputs the gain as a double to the user.*/
    public double sellFull(double newPrice){
        double payment= (this.quantity * newPrice) - 9.99;
        double gain = payment - this.bookValue;
        return gain;
    }
    /**sets book value for stocks */
    public void setBookValue(double inputPrice, int inputQuantity) throws NumberFormatException{
        this.bookValue = (inputPrice * inputQuantity) + 9.99;
    }
}
