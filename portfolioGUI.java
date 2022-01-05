package ePortfolio;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
/**class that holds the overall GUI */
public class portfolioGUI extends JFrame{
    // size of window
    public static final int WIDTH = 1900;
    public static final int HEIGHT = 1000;
    Stock newStock = null;
    Mutualfund newMutualfund = null;
    String[] typeBox = {"Stock", "Mutualfund"};
    String[] parse;
    MenuListener listener1 = new MenuListener();
    ButtonListener listener2 = new ButtonListener();
    boolean buy = false;
    boolean sell = false; 
    boolean search = false;
    boolean found = false;
    boolean exists = false;
    boolean clicked = false;
    boolean error;
    boolean emptySymbol;
    boolean emptyQuantity;
    boolean emptyName;
    boolean emptyPrice;
    boolean enable;
    boolean symbol;
    boolean name;
    boolean quantity;
    boolean price;
    boolean allowBuy;
    boolean success;
    Integer shares = 0;
    int i = 0;
    int j = 0;
    int count = 0;
    double sellPrice = 0.0;
    double currentGain = 0.0;
    double totalGain = 0.0;
    String share;
    String share2;
    String loss;
    String investmentName;
    ArrayList<investment> investments = new ArrayList<>();
    ArrayList<Integer> positions = null;
    ArrayList<Double> tempGain = new ArrayList<>();
    ArrayList<JTextField> textFields = new ArrayList<>();
    HashMap<String, ArrayList<Integer>> keyWords = new HashMap<String, ArrayList<Integer>>();
    private JPanel mainPanel;
    private JPanel panel = new JPanel();
    private JTextField inputSymbol;
    private JTextField inputName;
    private JTextField inputQuantity;
    private JTextField inputPrice;
    private JTextField sellSymbol;
    private JTextField sellQuantity;
    private JTextField sellPriceInput;
    private JTextField updateSymbol;
    private JTextField updateName;
    private JTextField updatePrice;
    private JTextField gain;
    private JTextField keywordsInput;
    private JTextField lowInput;
    private JTextField highInput;
    private JTextArea messages;
    private JLabel title;
    private JButton next;
    private JButton previous;
    private JComboBox<String> typeList;

    public portfolioGUI(){
        super();
        prepareGUI();
    }
    /**
     * Clears all the text fields in the "buy" menu option
     */
    public void clearBuy(){
        inputSymbol.setText("");
        inputName.setText("");
        inputQuantity.setText("");
        inputPrice.setText("");
    }
    /**
     * Clears all the text fields in the "sell" menu option
     */
    public void clearSell(){
        sellSymbol.setText("");
        sellQuantity.setText("");
        sellPriceInput.setText("");
    }
    /**
     * Clears all the text fields in the "search" menu option
     */
    public void clearSearchFields(){
        inputSymbol.setText("");
        inputName.setText("");
        keywordsInput.setText("");
        lowInput.setText("");
        highInput.setText("");
    }
    /**
     * Finds the intersection of ArrayLists
     */
    public static ArrayList<Integer> findIntersection(ArrayList<ArrayList<Integer>> inputList){
        ArrayList<ArrayList<Integer>> set = new ArrayList<>();
        ArrayList<Integer> intersection = new ArrayList<Integer>();
        ArrayList<Integer> returnSet = new ArrayList<>();
        int i = 0;
        while(i < inputList.size()){
            intersection.addAll(inputList.get(0));
            i++;
        }
        i = 0;
        while(i < inputList.size()){
            set.add(inputList.get(i));
            intersection.retainAll(set.get(i));
            i++;
        }
        i = 0;
        while(i < intersection.size()){
            int j = 0;
            while(j < i){
                if(intersection.get(i) == intersection.get(j)){
                    break;
                }
                j++;
            }
            if(i == j){
                returnSet.add(intersection.get(i));
            }
            i++;
        }
        return returnSet;
    }
    /**
     * sets up the GUI 
     */
    public void prepareGUI(){
        setTitle("ePortfolio");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout());
         
        Font titleFont = new Font(Font.SERIF, Font.BOLD, 16); //sets main page
        title = new JLabel("<html>Welcome to ePortfolio.<br><br><br><br><br><br>Choose a command from the \"Commands\" menu to buy or sell an investment, update prices for all investments, get gain for the portfolio, search for relevant investments, or quit the program.<html>");
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setVerticalAlignment(JLabel.NORTH);
        title.setFont(titleFont);

        JMenu commands = new JMenu("Commands"); //makiung a command bar
        JMenuItem buy = new JMenuItem("Buy");
        buy.addActionListener(listener1);
        commands.add(buy);

        JMenuItem sell = new JMenuItem("Sell");
        sell.addActionListener(listener1);
        commands.add(sell);

        JMenuItem update = new JMenuItem("Update");
        update.addActionListener(listener1);
        commands.add(update);

        JMenuItem getGain = new JMenuItem("Get gain");
        getGain.addActionListener(listener1);
        commands.add(getGain);

        JMenuItem search = new JMenuItem("Search");
        search.addActionListener(listener1);
        commands.add(search);

        JMenuItem exit = new JMenuItem("Quit");
        exit.addActionListener(listener1);
        commands.add(exit);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(commands);
        mainPanel.add(menuBar);

        setJMenuBar(menuBar);
        add(title);
    }
    /**
     * finds out what buttons is pressed using the menu bar
     */
    private class MenuListener implements ActionListener{
        /**
         * opens panels based on menu option
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            buy = false;
            sell = false;
            search = false;
            String menuOption = e.getActionCommand();
            Font labelFont = new Font(Font.SERIF, Font.PLAIN, 28);
            Font messageFont = new Font(Font.SERIF, Font.PLAIN, 30);
            Font headerFont = new Font(Font.SERIF, Font.BOLD, 32);
            Font textFont = new Font(Font.SERIF, Font.BOLD, 26);
            if(menuOption.equals("Buy")){
                emptySymbol = false; //makes buy panel
                emptyName = false;
                emptyQuantity = false;
                emptyPrice = false;
                allowBuy = false;
                buy = true;
                panel.setVisible(false);
                mainPanel.remove(panel);

                title.setText("");
                panel = new JPanel();
                panel.setLayout(null);
                panel.setBackground(Color.lightGray);

                JLabel header = new JLabel("Buying an investment");
                header.setBounds(20, 40, 400, 50);
                header.setFont(headerFont);
                panel.add(header);

                typeList = new JComboBox<>(typeBox);
                typeList.addActionListener(listener2);
                typeList.setBounds(300, 100, 330, 50);
                typeList.setFont(textFont);
                panel.add(typeList);

                JLabel typeLabel = new JLabel("Type");
                typeLabel.setBounds(20, 100, 220, 50);
                typeLabel.setFont(labelFont);
                panel.add(typeLabel);

                inputSymbol = new JTextField(100);
                inputSymbol.setBounds(300, 160, 330, 50);
                panel.add(inputSymbol);
                inputSymbol.setFont(textFont);
                textFields.add(inputSymbol);

                JLabel symLabel = new JLabel("Symbol");
                symLabel.setBounds(20, 160, 220, 50);
                symLabel.setFont(labelFont);
                panel.add(symLabel);

                inputName = new JTextField(100);
                inputName.setBounds(300, 220, 450, 50);
                panel.add(inputName);
                inputName.setFont(textFont);
                textFields.add(inputName);

                JLabel nameLabel = new JLabel("Name");
                nameLabel.setBounds(20, 220, 220, 50);
                nameLabel.setFont(labelFont);
                panel.add(nameLabel);

                inputQuantity = new JTextField(100);
                inputQuantity.setBounds(300, 280, 330, 50);
                panel.add(inputQuantity);
                inputQuantity.setFont(textFont);
                textFields.add(inputQuantity);

                JLabel quantityLabel = new JLabel("Quantity");
                quantityLabel.setBounds(20, 280, 220, 50);
                quantityLabel.setFont(labelFont);
                panel.add(quantityLabel);

                inputPrice = new JTextField(100);
                inputPrice.setBounds(300, 340, 330, 50);
                panel.add(inputPrice);
                inputPrice.setFont(textFont);
                textFields.add(inputPrice);

                JLabel priceLabel = new JLabel("Price");
                priceLabel.setBounds(20, 340, 220, 50);
                priceLabel.setFont(labelFont);
                panel.add(priceLabel);

                JLabel msgLabel = new JLabel("Messages");
                msgLabel.setBounds(20, 400, 220, 50);
                msgLabel.setFont(labelFont);
                panel.add(msgLabel);

                messages = new JTextArea();
                messages.setBounds(20, 460, 1800, 400);
                messages.setFont(messageFont);
                messages.setEditable(false);
                panel.add(messages);

                JScrollPane scrolledText = new JScrollPane(messages);
                scrolledText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                scrolledText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                scrolledText.setBounds(20, 460, 1800, 400);
                panel.add(scrolledText);

                JButton reset = new JButton("Reset");
                reset.setBounds(1400, 160, 220, 90);
                reset.addActionListener(listener2);
                panel.add(reset);

                JButton buy = new JButton("Buy");
                buy.setBounds(1400, 280 ,220, 90);
                buy.addActionListener(listener2);
                panel.add(buy);

                mainPanel.add(panel);
                add(mainPanel);
            }else if(menuOption.equals("Sell")){ //makes sell panel
                sell = true;
                panel.setVisible(false);
                mainPanel.remove(panel);

                title.setText("");
                panel = new JPanel();
                panel.setLayout(null);
                panel.setBackground(Color.lightGray);

                JLabel header = new JLabel("Selling an investment");
                header.setBounds(20, 40, 400, 50);
                header.setFont(headerFont);
                panel.add(header);

                sellSymbol = new JTextField(100);
                sellSymbol.setBounds(300, 100, 330, 50);
                panel.add(sellSymbol);

                JLabel symLabel = new JLabel("Symbol");
                symLabel.setBounds(20, 100, 220, 50);
                symLabel.setFont(labelFont);
                panel.add(symLabel);

                sellQuantity = new JTextField(100);
                sellQuantity.setBounds(300, 220, 330, 50);
                panel.add(sellQuantity);

                JLabel quanLabel = new JLabel("Quantity");
                quanLabel.setBounds(20, 220, 220, 50);
                quanLabel.setFont(labelFont);
                panel.add(quanLabel);

                sellPriceInput = new JTextField(100);
                sellPriceInput.setBounds(300, 340, 330, 50);
                panel.add(sellPriceInput);

                JLabel priceLabel = new JLabel("Price");
                priceLabel.setBounds(20, 340, 220, 50);
                priceLabel.setFont(labelFont);
                panel.add(priceLabel);

                JLabel msgLabel = new JLabel("Messages");
                msgLabel.setBounds(20, 400, 220, 50);
                msgLabel.setFont(labelFont);
                panel.add(msgLabel);

                messages = new JTextArea();
                messages.setBounds(20, 460, 1800, 400);
                messages.setFont(messageFont);
                messages.setEditable(false);
                panel.add(messages);

                JScrollPane scrolledText = new JScrollPane(messages);
                scrolledText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                scrolledText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                scrolledText.setBounds(20, 460, 1800, 400);
                panel.add(scrolledText);

                JButton reset = new JButton("Reset");
                reset.setBounds(1400, 160, 220, 90);
                reset.addActionListener(listener2);
                panel.add(reset);

                JButton sell = new JButton("Sell");
                sell.setBounds(1400, 280 ,220, 90);
                sell.addActionListener(listener2);
                panel.add(sell);

                if(investments.size() == 0){
                    messages.insert("No investments to sell\n", 0);
                    sell.setEnabled(false);
                }else{
                    sell.setEnabled(true);
                }
                mainPanel.add(panel);
                add(mainPanel);
            }else if(menuOption.equals("Update")){  //makes update panel
                panel.setVisible(false);
                mainPanel.remove(panel);

                title.setText("");
                panel = new JPanel();
                panel.setLayout(null);
                panel.setBackground(Color.lightGray);

                JLabel header = new JLabel("Updating investments");
                header.setBounds(20, 40, 400, 50);
                header.setFont(headerFont);
                panel.add(header);

                updateSymbol = new JTextField(100);
                updateSymbol.setBounds(300, 100, 330, 50);
                updateSymbol.setEditable(false);
                panel.add(updateSymbol);

                JLabel symLabel = new JLabel("Symbol");
                symLabel.setBounds(20, 100, 220, 50);
                symLabel.setFont(labelFont);
                panel.add(symLabel);

                updateName = new JTextField(100);
                updateName.setBounds(300, 220, 330, 50);
                updateName.setEditable(false);
                panel.add(updateName);

                JLabel nameLabel = new JLabel("Name");
                nameLabel.setBounds(20, 220, 220, 50);
                nameLabel.setFont(labelFont);
                panel.add(nameLabel);

                updatePrice = new JTextField(100);
                updatePrice.setBounds(300, 340, 330, 50);
                panel.add(updatePrice);

                JLabel priceLabel = new JLabel("Price");
                priceLabel.setBounds(20, 340, 220, 50);
                priceLabel.setFont(labelFont);
                panel.add(priceLabel);

                JLabel msgLabel = new JLabel("Messages");
                msgLabel.setBounds(20, 400, 220, 50);
                msgLabel.setFont(labelFont);
                panel.add(msgLabel);

                messages = new JTextArea();
                messages.setBounds(20, 460, 1800, 400);
                messages.setFont(messageFont);
                messages.setEditable(false);
                panel.add(messages);

                JScrollPane scrolledText = new JScrollPane(messages);
                scrolledText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                scrolledText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                scrolledText.setBounds(20, 460, 1800, 400);
                panel.add(scrolledText);

                previous = new JButton("Prev");
                previous.setBounds(1400, 100, 220, 90);
                previous.addActionListener(listener2);
                if(j == 0){
                    previous.setEnabled(false);
                }
                panel.add(previous);

                next = new JButton("Next");
                next.setBounds(1400, 220, 220, 90);
                if(j == investments.size() - 1){
                    next.setEnabled(true);
                }
                next.addActionListener(listener2);
                panel.add(next);

                JButton save = new JButton("Save");
                save.setBounds(1400, 350, 220, 90);
                save.addActionListener(listener2);
                panel.add(save);

                if(investments.size() == 1){
                    previous.setEnabled(false);
                    next.setEnabled(false);
                    if(investments.size() == 0){
                        messages.insert("You have no investments to update\n",0);
                    }
                }

                if(investments.size() == 0){
                    previous.setEnabled(false);
                    next.setEnabled(false);
                    if(investments.size() == 0){
                        messages.insert("You have no investments to update\n",0);
                    }
                }

                if(investments.size() > 0){
                    updateSymbol.setText(investments.get(0).getSymbol());
                    updateName.setText(investments.get(0).getName());
                }
                mainPanel.add(panel);
                add(mainPanel);
            }else if(menuOption.equals("Get gain")){ //makes get gain panel, functionality doesnt work
                panel.setVisible(false);
                mainPanel.remove(panel);

                title.setText("");
                panel = new JPanel();
                panel.setLayout(null);
                panel.setBackground(Color.lightGray);

                JLabel header = new JLabel("Getting total gain");
                header.setBounds(20, 30, 400, 50);
                header.setFont(headerFont);
                panel.add(header);

                JLabel totalGain = new JLabel("Total Gain");
                totalGain.setBounds(20, 100, 400, 50);
                totalGain.setFont(labelFont);
                panel.add(totalGain);

                gain = new JTextField(100);
                gain.setEditable(false);
                gain.setBounds(200, 100, 400, 50);
                panel.add(gain);

                JLabel individual = new JLabel("Individual Gains");
                individual.setBounds(20, 160, 400, 50);
                individual.setFont(labelFont);
                panel.add(individual);

                messages = new JTextArea();
                messages.setBounds(20, 200, 1800, 600);
                messages.setFont(messageFont);
                messages.setEditable(false);
                panel.add(messages);

                JScrollPane scrolledText = new JScrollPane(messages);
                scrolledText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                scrolledText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                scrolledText.setBounds(20, 200, 1800, 600);
                panel.add(scrolledText);

                mainPanel.add(panel);
                add(mainPanel);

            }else if(menuOption.equals("Search")){ //makes search panel
                search = true;
                panel.setVisible(false);
                mainPanel.remove(panel);

                title.setText("");
                panel = new JPanel();
                panel.setLayout(null);
                panel.setBackground(Color.lightGray);

                JLabel header = new JLabel("Search investments");
                header.setBounds(20, 40, 400, 50);
                header.setFont(headerFont);
                panel.add(header);

                inputSymbol = new JTextField(100);
                inputSymbol.addActionListener(listener2);
                inputSymbol.setBounds(400, 100, 330, 50);
                panel.add(inputSymbol);

                JLabel symLabel = new JLabel("Symbol");
                symLabel.setBounds(20, 100, 220, 50);
                symLabel.setFont(labelFont);
                panel.add(symLabel);

                keywordsInput = new JTextField(100);
                keywordsInput.setBounds(400, 160, 450, 50);
                panel.add(keywordsInput);

                JLabel nameLabel = new JLabel("Name keywords");
                nameLabel.setBounds(20, 160, 300, 50);
                nameLabel.setFont(labelFont);
                panel.add(nameLabel);

                lowInput = new JTextField(100);
                lowInput.setBounds(400, 220, 330, 50);
                panel.add(lowInput);

                JLabel lowLabel = new JLabel("Low");
                lowLabel.setBounds(20, 200, 220, 250);
                lowLabel.setFont(labelFont);
                panel.add(lowLabel);

                highInput = new JTextField(100);
                highInput.setBounds(400, 280, 330, 50);
                panel.add(highInput);
                
                JLabel highLabel = new JLabel("High");
                highLabel.setBounds(20, 220, 220, 50);
                highLabel.setFont(labelFont);
                panel.add(highLabel);

                JLabel resultsLabel = new JLabel("Search results");
                resultsLabel.setBounds(20, 380, 220, 50);
                resultsLabel.setFont(labelFont);
                panel.add(resultsLabel);

                messages = new JTextArea();
                messages.setBounds(20, 440, 1620, 380);
                messages.setEditable(false);
                panel.add(messages);

                JScrollPane scrolledText = new JScrollPane(messages);
                scrolledText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                scrolledText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                scrolledText.setBounds(20, 440, 1620, 380);
                panel.add(scrolledText);

                JButton reset = new JButton("Reset");
                reset.setBounds(1400, 100, 220, 90);
                reset.addActionListener(listener2);
                panel.add(reset);

                JButton search = new JButton("Search");
                search.setBounds(1400, 220, 220, 90);
                search.addActionListener(listener2);
                panel.add(search);

                mainPanel.add(panel);
                add(mainPanel);
            }else if(menuOption.equals("Quit")){ //quits when quit option is chosen
                System.exit(0);
            }else{
                System.out.println("Unexpected error");
            }
        }
    }
    /**performs tasks based on which buttons were pressed in the interfaces */
    private class ButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String button = e.getActionCommand();
            if(button.equals("Reset")){
                if(!sell && !search){ //clear fields based on which reset button is pressed
                    inputSymbol.setText("");
                    inputName.setText("");
                    inputQuantity.setText("");
                    inputPrice.setText("");
                }else if(!buy && !search){
                    sellSymbol.setText("");
                    sellQuantity.setText("");
                    sellPriceInput.setText("");
                }else if(!buy && !sell){
                    inputSymbol.setText("");
                    inputName.setText("");
                    keywordsInput.setText("");
                    lowInput.setText("");
                    highInput.setText("");
                }
            }else if(button.equals("Buy")){ //exception checks for buy, validates input
                error = false;
                String type; 
                String symbol = "";
                String quantity = "";
                String price = "";
                Double tempPrice = 0.0;
                Integer tempQuantity = 0;
                type = (String)typeList.getSelectedItem();
                if(inputSymbol.getText().isEmpty()){
                    messages.insert("Please enter a valid symbol\n",0);
                    error = true;
                }

                if(inputSymbol.getText().isBlank()){
                    messages.insert("Please enter a valid symbol\n",0);
                    error = true;
                }

                if(inputName.getText().isEmpty()){
                    messages.insert("Please enter a valid name\n",0);
                    error = true;
                }

                if(inputName.getText().isBlank()){
                    messages.insert("Please enter a valid name\n",0);
                    error = true;
                }

                if(inputQuantity.getText().isEmpty()){
                    messages.insert("Please enter a valid quantity\n",0);
                    error = true;
                }

                if(inputQuantity.getText().isBlank()){
                    messages.insert("Please enter a valid quantity\n",0);
                    error = true;
                }

                if(inputPrice.getText().isEmpty()){
                    messages.insert("Please enter a valid price\n",0);
                    error = true;
                }
                if(inputPrice.getText().isBlank()){
                    messages.insert("Please enter a valid price\n",0);
                    error = true;
                }else{
                    symbol = inputSymbol.getText();
                    if(symbol.contains(" ")){
                        symbol = symbol.replace(" ", "");
                    }
                    investmentName = inputName.getText();
                    if(investmentName.contains(" ")){
                        investmentName = investmentName.replace(" ", "");
                    }
                    quantity = inputQuantity.getText();
                    if(quantity.contains(" ")){
                        quantity = quantity.replace(" ", "");
                    }
                    price = inputPrice.getText();
                    if(price.contains(" ")){
                        price = price.replace(" ", "");
                    }
                    double bookValue = 0.0;
                    try{
                        tempPrice = Double.parseDouble(price);
                        tempQuantity = Integer.parseInt(quantity);
                    }catch(NumberFormatException nfe){
                        messages.insert("Please enter a valid price and quantity\n",0);
                        error = true;
                    }

                    if(tempPrice < 0.0){
                        messages.insert("Price and quantity must be above 0\n",0);
                        error = true;
                    }

                    if(tempQuantity < 0){
                        messages.insert("Price and quantity must be above 0\n",0);
                        error = true;
                    }

                    if(type.equals("Stock")){
                        found = false;
                        int i = 0;
                        while(i < investments.size()){
                            if(symbol.equalsIgnoreCase(investments.get(i).getSymbol())){
                                found = true;
                                break;
                            }
                            i++;
                        }
                        if(!found){
                            newStock = new Stock();
                            try{
                                newStock.setSymbol(symbol);
                            }catch(Exception r){
                                messages.insert(r.getMessage(),0);
                                error = true;
                            }
                            try{
                                newStock.setName(investmentName);
                            }catch(Exception r){
                                messages.insert(r.getMessage(),0);
                                error = true;
                            }
                            try{
                                newStock.setQuantity(tempQuantity);
                            }catch(NumberFormatException r){
                                messages.insert(r.getMessage(),0);
                                error = true;
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                            try{
                                newStock.setPrice(tempPrice);
                            }catch(NumberFormatException r){
                                messages.insert(r.getMessage(),0);
                                error = true;
                            }
                            try{
                                newStock.setBookValue(tempPrice, tempQuantity);
                            }catch(NumberFormatException r){
                                messages.insert("Error creating bookvalue\n",0);
                            }
                            if(!error){
                                investments.add(newStock);
                            }
                        }else{
                            if(investments.get(i) instanceof Mutualfund){
                                messages.insert("Symbol exists already\n",0);
                            }else{
                                try{
                                    bookValue = (tempPrice * tempQuantity) + 9.99;
                                    investments.get(i).buyMore(tempQuantity, tempPrice);
                                }catch(NumberFormatException nfe){
                                    messages.insert("Please enter a valid quantity and price\n",0);
                                    error = true;
                                }
                            }
                        }
                    }else{
                        found = false;
                        int i = 0;
                        while(i < investments.size()){
                            if(symbol.equalsIgnoreCase(investments.get(i).getSymbol())){
                                found = true;
                                break;
                            }
                            i++;
                        }
                        if(!found){
                            newMutualfund = new Mutualfund();
                            try{
                                newMutualfund.setSymbol(symbol);
                            }catch(Exception r){
                                messages.insert(r.getMessage(),0);
                                error = true;
                            }
                            try{
                                newMutualfund.setName(investmentName);
                            }catch(Exception r){
                                messages.insert(r.getMessage(),0);
                                error = true;
                            }
                            try{
                                newMutualfund.setQuantity(tempQuantity);
                            }catch(NumberFormatException r){
                                messages.insert(r.getMessage(),0);
                                error = true;
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                            try{
                                newMutualfund.setPrice(tempPrice);
                            }catch(NumberFormatException r){
                                messages.insert(r.getMessage(),0);
                                error = true;
                            }
                            try{
                                newMutualfund.setBookValue(tempPrice, tempQuantity);
                            }catch(NumberFormatException r){
                                messages.insert("Error creating bookvalue\n",0);
                            }
                            if(!error){
                                investments.add(newMutualfund);
                            }
                        }else{
                            if(investments.get(i) instanceof Stock){
                                messages.insert("Symbol exists in Stocks\n",0);
                            }else{
                                try{
                                    bookValue = (tempPrice * tempQuantity) + 9.99;
                                    investments.get(i).buyMore(tempQuantity, tempPrice);
                                }catch(NumberFormatException nfe){
                                    messages.insert("Please enter a valid quantity and price\n",0);
                                    error = true;
                                }
                            } 
                        }
                    }
                }
                if(!error){
                    keyWords.clear();
                    int i = 0;
                    while(i < investments.size()){
                        parse = investments.get(i).getName().toLowerCase().split("[ ]+");
                        int k = 0;
                        while(k < parse.length){
                            positions = new ArrayList<Integer>();
                            if(!keyWords.containsKey(parse[k])){
                                positions.add(i);
                                keyWords.put(parse[k], positions);
                            } else{
                                keyWords.get(parse[k]).add(i);
                            }
                            k++;
                        }
                        i++;
                    }
                    messages.insert("Succesfully Bought Stock\n",0);
                }
                clearBuy();
            }else if(button.equals("Sell")){  //error check for sell, checks validity of all fields
                error = false;
                String soldStock = "", priceToSell = "", soldQuantity = "";
                
                if(sellSymbol.getText().isEmpty()){
                    messages.insert("Please enter the symbol of the investment you would like to sell\n",0);
                    error = true;
                }

                if(sellSymbol.getText().isBlank()){
                    messages.insert("Please enter the symbol of the investment you would like to sell\n",0);
                    error = true;
                }

                if(sellQuantity.getText().isEmpty()){
                    messages.insert("Please enter how many shares you would like to sell\n",0);
                    error = true;
                }

                if(sellQuantity.getText().isBlank()){
                    messages.insert("Please enter how many shares you would like to sell\n",0);
                    error = true;
                }

                if(sellPriceInput.getText().isEmpty()){
                    messages.insert("Please enter how much you would like to sell each share for\n",0);
                    error = true;
                }

                if(sellPriceInput.getText().isBlank()){
                    messages.insert("Please enter how much you would like to sell each share for\n",0);
                    error = true;
                }else{
                    soldStock = sellSymbol.getText();
                    if(soldStock.contains(" ")){
                        soldStock = soldStock.replace(" ", "");
                    }
                    soldQuantity = sellQuantity.getText();
                    if(soldQuantity.contains(" ")){
                        soldQuantity = soldQuantity.replace(" ", "");
                    }
                    priceToSell = sellPriceInput.getText();
                    if(priceToSell.contains(" ")){
                        priceToSell = priceToSell.replace(" ", "");
                    }
                    exists = false;
                    i = 0;
                    while(i < investments.size()){
                        if(soldStock.equalsIgnoreCase(investments.get(i).getSymbol())){
                            exists = true;
                            break;
                        }
                        i++;
                    }
                    if(exists){
                        if(investments.get(i) instanceof Stock){
                            try{
                                shares = Integer.parseInt(soldQuantity);
                            }catch(NumberFormatException nfe){
                                messages.insert("Please enter a valid quantity\n",0);
                                error = true;
                            }
                            if(shares < 0){
                                messages.insert("Please enter a valid quantity\n",0);
                                error = true;
                            }
                            if(investments.get(i).getQuantity() < shares){
                                share = (investments.get(i).getQuantity()) == 1 ? "share" : "shares";
                                share2 = (shares == 1) ? "share" : "shares";
                                messages.insert("You cannot sell "+shares+" "+share2+". You only have "+investments.get(i).getQuantity()+" "+share+".\n",0);
                                error = true;
                            }else if(investments.get(i).getQuantity() > shares){
                                if(!error){
                                    try{
                                        sellPrice = Double.parseDouble(priceToSell);
                                    }catch(NumberFormatException nfe){
                                        messages.insert("Please enter a valid price\n",0);
                                        error = true;
                                    }
                                    if(sellPrice < 0.0){
                                        messages.insert("Please enter a valid price\n",0);
                                        error = true;
                                    }
                                    currentGain = ((Stock)investments.get(i)).sellPartial(shares, sellPrice);
                                    totalGain += currentGain;
                                    share2 = (shares == 1) ? "share" : "shares";
                                    loss = (currentGain < 0) ? "lost" : "gained";
                                    if(currentGain < 0)
                                        currentGain *= -1;
    
                                    messages.insert("You have "+loss+" $"+ String.format("%.2f",currentGain) + " from selling "+shares+" "+share2+".\n",0);
                                    messages.insert("The total gain of your portfolio right now is: $"+String.format("%.2f", totalGain) + "\n",0);
                                }
                            }else if(investments.get(i).getQuantity() == shares){
                                try{
                                    sellPrice = Double.parseDouble(priceToSell);
                                }catch(NumberFormatException nfe){
                                    messages.insert("Please enter a valid price\n",0);
                                    error = true;
                                }
                                if(sellPrice < 0.0){
                                    messages.insert("Please enter a valid price\n",0);
                                    error = true;
                                }
                                if(!error){
                                    currentGain = ((Stock)investments.get(i)).sellFull(shares);
                                    totalGain += currentGain;
                                    share2 = (shares == 1) ? "share" : "shares";
                                    loss = (currentGain < 0) ? "lost" : "gained";
                                    if(currentGain < 0)
                                        currentGain *= -1;

                                    messages.insert("You have "+loss+" $"+ String.format("%.2f",currentGain) + " from selling "+shares+" "+share2+".\n",0);
                                    messages.insert("The total gain of your portfolio right now is: $"+String.format("%.2f", totalGain) + "\n",0);
                                    investments.remove(i);
                                }
                                
                            }
                        }else if(investments.get(i) instanceof Mutualfund){
                            try{
                                shares = Integer.parseInt(soldQuantity);
                            }catch(NumberFormatException nfe){
                                messages.insert("Please enter a valid quantity\n",0);
                                error = true;
                            }
                            if(shares < 0){
                                messages.insert("Please enter a valid quantity\n",0);
                                error = true;
                            }
                            if(investments.get(i).getQuantity() < shares){
                                share = (investments.get(i).getQuantity()) == 1 ? "share" : "shares";
                                share2 = (shares == 1) ? "share" : "shares";
                                messages.insert("You cannot sell "+ shares +" "+ share2 + ". You only have " + investments.get(i).getQuantity() + " " + share + ".\n",0);
                                error = true;
                            }else if(investments.get(i).getQuantity() > shares){
                                try{
                                    sellPrice = Double.parseDouble(priceToSell);
                                }catch(NumberFormatException nfe){
                                    messages.insert("Price must be a number\n",0);
                                    error = true;
                                }
                                if(sellPrice < 0.0){
                                    messages.insert("Price must be greater than 0\n",0);
                                    error = true;
                                }
                                if(!error){
                                    currentGain = ((Mutualfund)investments.get(i)).sellPartial(shares, sellPrice);
                                    totalGain += currentGain;
                                    share2 = (shares == 1) ? "share" : "shares";
                                    loss = (currentGain < 0) ? "lost" : "gained";
                                    if(currentGain < 0)
                                        currentGain *= -1;
    
                                    messages.insert("You have "+ gain + " $" + String.format("%.2f",currentGain) + " from selling " + shares + " " + share2 + ".\n",0);
                                    messages.insert("The total gain of your portfolio right now is: $" + String.format("%.2f", totalGain) + "\n",0);
                                }
                            }else if(investments.get(i).getQuantity() == shares){
                                try{
                                    sellPrice = Double.parseDouble(priceToSell);
                                }catch(NumberFormatException nfe){
                                    messages.insert("Please enter a valid price\n",0);
                                    error = true;
                                }
                                if(sellPrice < 0.0){
                                    messages.insert("Price must be greater than 0\n",0);
                                    error = true;
                                }
                                if(!error){
                                    currentGain = ((Mutualfund)investments.get(i)).sellFull(shares);
                                    totalGain += currentGain;
                                    share2 = (shares == 1) ? "share" : "shares";
                                    loss = (currentGain < 0) ? "lost" : "gained";
                                    if(currentGain < 0)
                                        currentGain *= -1;
    
                                    messages.insert("You have "+gain+" $"+ String.format("%.2f",currentGain) + " from selling "+shares+" "+share2+".\n",0);
                                    messages.insert("The total gain of your portfolio right now is: $"+String.format("%.2f", totalGain) + "\n",0);
                                    investments.remove(i);
                                }
                            }
                        }
                    }else{
                        messages.insert("Symbol does not exist in your investments\n",0);
                        error = true;
                    }
                    keyWords.clear();
                    i = 0;
                    while(i < investments.size()){
                        parse = investments.get(i).getName().toLowerCase().split("[ ]+");
                        int k = 0;
                        while(k < parse.length){
                            positions = new ArrayList<Integer>();
                            if(!keyWords.containsKey(parse[k])){
                                positions.add(i);
                                keyWords.put(parse[k], positions);
                            } else{
                                keyWords.get(parse[k]).add(i);
                            }
                            k++;
                        }
                        i++;
                    }
                    if(!error){
                        messages.insert("Investment sold successfully!\n",0);
                    }
                }
                clearSell();
            }else if(button.equals("Prev")){  //part of update interface
                next.setEnabled(true);
                j--;
                if(j == 0){
                    previous.setEnabled(false);
                }
                updateSymbol.setText(investments.get(j).getSymbol());
                updateName.setText(investments.get(j).getName());
                updatePrice.setText("");
            }else if(button.equals("Next")){ //part of update interface
                previous.setEnabled(true);
                j++;
                if(j == investments.size() - 1){
                    next.setEnabled(false);
                }
                updateSymbol.setText(investments.get(j).getSymbol());
                updateName.setText(investments.get(j).getName());
                updatePrice.setText("");
            }else if(button.equals("Save")){  //part of update interface, checks validity of all fields
                if(updatePrice.getText().isEmpty()){
                    messages.insert("Please enter a valid price\n",0);
                }
                
                if(updatePrice.getText().isBlank()){
                    messages.insert("Please enter a valid price\n",0);
                }else{
                    String savePrice = updatePrice.getText();
                    if(savePrice.contains(" ")){
                        savePrice.trim();
                    }
                    try{
                        if(investments.get(j) instanceof Stock){
                            ((Stock)investments.get(j)).update(Double.parseDouble((savePrice)));
                        }else if(investments.get(j) instanceof Mutualfund){
                            ((Mutualfund)investments.get(j)).update(Double.parseDouble((savePrice)));
                        }
                        success = true;
                    }catch(NumberFormatException nfe){
                        messages.insert("Please enter a valid price\n",0);
                        success = false;
                    }
                    if(success){
                        messages.insert(investments.get(j).toString(),0);
                    }
                }
            }else if(button.equals("Search")){  //checks validity for fields and ensures fields are correct
                
                error = false;
                String symbolToSearch = "", keywordToSearch = "";
                int low = 0, high = 0, numWords = 0;
                boolean blank = false, blank2 = false, blank3 = false, blank4 = false;
                ArrayList<ArrayList<Integer>> idx = null;
                ArrayList<Integer> list = null;
                String[] parse;
                if(inputSymbol.getText().isBlank()){
                    blank2 = true;
                }else{
                    symbolToSearch = inputSymbol.getText();
                }
                if(keywordsInput.getText().isBlank()){
                    blank = true;
                }else{
                    keywordToSearch = keywordsInput.getText();
                }
                parse = keywordToSearch.split("[ ]+");
                if(lowInput.getText().isBlank()){
                    blank3 = true;
                }else{
                    low = Integer.parseInt(lowInput.getText());
                    if(low < 0){
                        messages.insert("Please enter a valid lower price\n",0);
                        error = true;
                    }
                }
                if(highInput.getText().isBlank()){
                    blank4 = true;
                }else{
                    high = Integer.parseInt(highInput.getText());
                    if(high < 0){
                        messages.insert("Please enter a valid upper number\n",0);
                        error = true;
                    }
                }
                if(!error){
                    if(!blank){
                        list = new ArrayList<Integer>();
                        idx = new ArrayList<ArrayList<Integer>>();
                        i = 0;
                        while(i < parse.length){
                            numWords++;
                            for(Map.Entry < String, ArrayList<Integer>> set : keyWords.entrySet()){
                                if(set.getKey().toLowerCase().contains(parse[i].toLowerCase())){
                                    idx.add(set.getValue());
                                    list = findIntersection(idx);
                                }
                            }
                            i++;
                        }
                        if(numWords == 1){ 
                            list = new ArrayList<Integer>();
                            i = 0;
                            while(i < idx.size()){
                                list.addAll(idx.get(i));
                                i++;
                            }
                            i = 0;
                            while(i < investments.size()){
                                j = 0;
                                while(j < list.size()){
                                    if(i == list.get(j)){
                                        if(blank2 && blank3 && blank4){
                                            messages.insert(investments.get(list.get(j)).toString()+"\n",0);
                                        }else if(!blank2){
                                            if (blank3 && blank4){
                                                if(investments.get(list.get(j)).getSymbol().equalsIgnoreCase(symbolToSearch)){
                                                    messages.insert(investments.get(list.get(j)).toString()+"\n",0);
                                                    break;
                                                }
                                            }
                                        }else if(!blank2 && !blank3){
                                            if (blank4){
                                                if(investments.get(list.get(j)).getSymbol().equalsIgnoreCase(symbolToSearch) && investments.get(j).getPrice() >= low){
                                                    messages.insert(investments.get(list.get(j)).toString()+"\n",0);
                                                    break;
                                                }
                                            }
                                        }else if(!blank2 && !blank4){
                                            if (blank3){ 
                                                if(investments.get(list.get(j)).getSymbol().equalsIgnoreCase(symbolToSearch) && investments.get(j).getPrice() <= high){
                                                    messages.insert(investments.get(list.get(j)).toString()+"\n",0);
                                                    break;
                                                }
                                            }
                                        }else if(!blank3 && !blank4){
                                            if(blank2){
                                                if(low <= investments.get(list.get(j)).getPrice() && investments.get(list.get(j)).getPrice() <= high){
                                                    messages.insert(investments.get(list.get(j)).toString()+"\n",0);
                                                    break;
                                                }
                                            }
                                        }else if(blank2 && blank3){
                                            if(!blank4){
                                                if(investments.get(list.get(j)).getPrice() <= high){
                                                    messages.insert(investments.get(list.get(j)).toString()+"\n",0);
                                                    break;
                                                }
                                            }
                                        }else if(blank2 && blank4){
                                            if (!blank3){
                                                if(low <= investments.get(list.get(j)).getPrice()){
                                                    messages.insert(investments.get(list.get(j)).toString()+"\n",0);
                                                    break;
                                                }
                                            }
                                        }else{
                                            if(investments.get(list.get(j)).getSymbol().equalsIgnoreCase(symbolToSearch) && low <= investments.get(list.get(j)).getPrice() && investments.get(list.get(j)).getPrice() <= high){
                                                messages.insert(investments.get(list.get(j)).toString()+"\n",0);
                                                break;
                                            }
                                        }
                                    }
                                    j++;
                                }
                                i++;
                            }
                        } else{
                            if(list.size() == 0){
                                messages.insert("There are no investments that match your search...\n",0);
                            }
                            i = 0;
                            while(i < investments.size()){
                                j = 0;
                                while(j < list.size()){
                                    if(i == list.get(j)){
                                        if(blank2 && blank3 && blank4){
                                            messages.insert(investments.get(list.get(j)).toString()+"\n",0);
                                        }else if(blank3 && blank4){
                                            if(!blank2){
                                                if(investments.get(list.get(j)).getSymbol().equalsIgnoreCase(symbolToSearch)){
                                                    messages.insert(investments.get(list.get(j)).toString()+"\n",0);
                                                }
                                            }
                                        }else if(!blank2 && !blank3){
                                            if(blank4){
                                                if(investments.get(list.get(j)).getSymbol().equalsIgnoreCase(symbolToSearch) && investments.get(j).getPrice() >= low){
                                                    messages.insert(investments.get(list.get(j)).toString()+"\n",0);
                                                }
                                            }
                                        }else if(!blank2 && !blank4){
                                            if(blank3){
                                                if(investments.get(list.get(j)).getSymbol().equalsIgnoreCase(symbolToSearch) && investments.get(j).getPrice() <= high){
                                                    messages.insert(investments.get(list.get(j)).toString()+"\n",0);
                                                }
                                            }                                           
                                        }else if(!blank3 && !blank4){
                                            if(blank2){
                                                if(low <= investments.get(list.get(j)).getPrice() && investments.get(list.get(j)).getPrice() <= high){
                                                    messages.insert(investments.get(list.get(j)).toString()+"\n",0);
                                                }
                                            }
                                        }else if(blank2 && blank3){
                                            if(!blank4){
                                                if(investments.get(list.get(j)).getPrice() <= high){
                                                    messages.insert(investments.get(list.get(j)).toString()+"\n",0);
                                                }
                                            }
                                        }else if(blank2 && blank4){
                                            if(!blank3){
                                                if(low <= investments.get(list.get(j)).getPrice()){
                                                    messages.insert(investments.get(list.get(j)).toString()+"\n",0);
                                                }
                                            }
                                        }else{
                                            if(investments.get(list.get(j)).getSymbol().equalsIgnoreCase(symbolToSearch) && low <= investments.get(list.get(j)).getPrice() && investments.get(list.get(j)).getPrice() <= high){
                                                messages.insert(investments.get(list.get(j)).toString()+"\n",0);
                                            }
                                        }
                                    }
                                    j++;
                                }
                                i++;
                            }
                        }
                    }else{
                        i = 0;
                        while(i < investments.size()){
                            if(blank2 && blank3 && blank4){
                                messages.insert(investments.get(i).toString()+"\n",0);
                            }else if(blank3 && blank4){
                                if(!blank2){
                                    if(investments.get(i).getSymbol().equalsIgnoreCase(symbolToSearch)){
                                        messages.insert(investments.get(i).toString()+"\n",0);
                                    }
                                }
                            }else if(!blank2 && !blank3){
                                if (blank4){
                                    if(investments.get(i).getSymbol().equalsIgnoreCase(symbolToSearch) && investments.get(i).getPrice() >= low){
                                        messages.insert(investments.get(i).toString()+"\n",0);
                                    }
                                }
                            }else if(!blank2 && !blank4){
                                if(blank3){
                                    if(investments.get(i).getSymbol().equalsIgnoreCase(symbolToSearch) && investments.get(i).getPrice() <= high){
                                        messages.insert(investments.get(i).toString()+"\n",0);
                                    }
                                }
                            }else if(!blank3 && !blank4){
                                if(blank2){
                                    if(low <= investments.get(i).getPrice() && investments.get(i).getPrice() <= high){
                                        messages.insert(investments.get(i).toString()+"\n",0);
                                    }
                                }
                            }else if(blank2 && blank3){
                                if(!blank4){
                                    if(investments.get(i).getPrice() <= high){
                                        messages.insert(investments.get(i).toString()+"\n",0);
                                    }
                                }
                            }else if(blank2 && blank4){
                                if(!blank3){
                                    if(low <= investments.get(i).getPrice()){
                                        messages.insert(investments.get(i).toString()+"\n",0);
                                    }
                                }
                            }else{
                                if(investments.get(i).getSymbol().equalsIgnoreCase(symbolToSearch) && low <= investments.get(i).getPrice() && investments.get(i).getPrice() <= high){
                                    messages.insert(investments.get(i).toString()+"\n",0);
                                }
                            }
                            i++;
                        }
                    }
                }
                clearSearchFields();
            }   
        }
    }
    public static void main(String[] args){
        portfolioGUI gui = new portfolioGUI();
        gui.setVisible(true);
    }
}