import java.io.File;
import java.io.IOException;
import java.util.*;


public class Ahmet_Basbug_2020510135 {


    public static void main(String[] args){

        System.out.println("-------------------------------------------------------------------------");
        ArrayList<Piece> allPieces = new ArrayList<>();

        long startTime;
        long finalTime;

        Scanner scanner = new Scanner(System.in);

        System.out.println();

        System.out.print("Please enter the amount of gold:");
        int gold_amount = scanner.nextInt();
        System.out.print("Please enter the number of max level allowed:");
        int max_level_allowed = scanner.nextInt();
        System.out.print("Please enter the number of available pieces per level:");
        int number_of_available_pieces_per_level = scanner.nextInt();

        readFile(allPieces);


        ArrayList<Integer> eachPieceTypeArray = new ArrayList<>();//it contains the types in number
        //if max level allowed is 3 and available pieces per level is 2
        //[0,0,1,1,2,2] ==> 0 is Pawn; 1 is Rook; 2 is Archer
        //it will be used in Dynamic Programming to prevent taking a type more than once

        //List that keeps the pieces
        //e.g if max level allowed is 3 and available pieces per level is 2
        //[Pawn James, Pawn Ashley, Eilean Donan,Edinburgh Castle, Robin Hood, Huang Zhong]
        ArrayList<Piece> piecesWillBeUsedDynamic = new ArrayList<>();
        ArrayList<Piece> piecesWillBeUsedGreedy = new ArrayList<>();
        ArrayList<Piece> piecesWillBeUsedRandom = new ArrayList<>();



        //with this loop we will determine how many elements exist in each type
        //thus, whether the input file has 10 elements in each type or 25 elements
        //this program will run properly
        //I chose Pawn to check it is not important actually
        int howManyPieceExistInEachType  = 0;
        for(int i = 0;i<allPieces.size();i++){
            if(Objects.equals(allPieces.get(i).type, "Pawn")){
                howManyPieceExistInEachType++;
            }
        }

        getThePieces(howManyPieceExistInEachType,max_level_allowed,number_of_available_pieces_per_level,allPieces,piecesWillBeUsedDynamic,piecesWillBeUsedGreedy,piecesWillBeUsedRandom);

        //filling the eachPieceTypeArray
        for (int i = 0; i < max_level_allowed; i++) {
            for (int k = 0; k < number_of_available_pieces_per_level; k++) {
                eachPieceTypeArray.add(i);
            }
        }

        System.out.println();
        int userScore;
        int computerScore;


        System.out.println("Trial #1 --- User: Dynamic Programming // Computer: Greedy Approach");

        System.out.println(">>>>>>>USER<<<<<<<");
        startTime = System.currentTimeMillis();
        userScore = dynamicProgrammingApproach(gold_amount, piecesWillBeUsedDynamic, eachPieceTypeArray, max_level_allowed, number_of_available_pieces_per_level);
        finalTime = System.currentTimeMillis() - startTime;
        System.out.println("Dynamic Programming Approach: Execution time in millisecond(s) : " + finalTime + " in second(s) : " + (double)finalTime/1000);
        System.out.println();
        System.out.println(">>>>>>>COMPUTER<<<<<<<");
        startTime = System.currentTimeMillis();;
        computerScore = greedyApproach(gold_amount, piecesWillBeUsedGreedy);
        finalTime = System.currentTimeMillis() - startTime;
        System.out.println("Greedy Approach: Execution time in millisecond(s) : " + finalTime + " in second(s) : " + (double)finalTime/1000);
        findTheWinner(userScore,computerScore);

        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println("Trial #2 --- User: Dynamic Programming // Computer: Random Approach");

        System.out.println(">>>>>>>USER<<<<<<<");
        startTime = System.currentTimeMillis();;
        userScore = dynamicProgrammingApproach(gold_amount, piecesWillBeUsedDynamic, eachPieceTypeArray, max_level_allowed, number_of_available_pieces_per_level);
        finalTime = System.currentTimeMillis() - startTime;
        System.out.println("Dynamic Programming Approach: Execution time in millisecond(s) : " + finalTime + " in second(s) : " + (double)finalTime/1000);
        System.out.println();
        System.out.println(">>>>>>>COMPUTER<<<<<<<");
        startTime = System.currentTimeMillis();
        computerScore = randomApproach(gold_amount, piecesWillBeUsedRandom, max_level_allowed, number_of_available_pieces_per_level);
        finalTime = System.currentTimeMillis() - startTime;
        System.out.println("Random Approach: Execution time in millisecond(s) : " + finalTime + " in second(s) : " + (double)finalTime/1000);

        findTheWinner(userScore,computerScore);


    }

    public static void getThePieces(int howManyPieceExistInEachType, int max_level_allowed,int number_of_available_pieces_per_level,ArrayList<Piece> allPieces,ArrayList<Piece> piecesWillBeUsedDynamic,ArrayList<Piece> piecesWillBeUsedGreedy,ArrayList<Piece> piecesWillBeUsedRandom){
        //via this method, we can select elements according to number of available pieces per level
        //for instance if max_level_allowed is 3, and number of available pieces per level is 2
        //then get first two Pawn after that add 8, and get first two of Rook ....
        //if each type has 10 element in input csv file
        int j = 0;
        for (int i = 0; i < max_level_allowed * number_of_available_pieces_per_level; i++) {
            if (i % number_of_available_pieces_per_level == 0 && i != 0) {
                j = j + (howManyPieceExistInEachType - number_of_available_pieces_per_level);
            }
            piecesWillBeUsedDynamic.add(allPieces.get(j));
            piecesWillBeUsedGreedy.add(allPieces.get(j));
            piecesWillBeUsedRandom.add(allPieces.get(j));

            j++;
        }
    }


    public static void findTheWinner(int userScore, int computerScore){//This function finds the winner according to attack points
        System.out.println();
        if(userScore>computerScore){
            System.out.println("RESULT ========> USER HAS WON THE GAME!");
        }else if(computerScore>userScore){
            System.out.println("RESULT ========> COMPUTER HAS WON THE GAME!");
        }else{
            System.out.println("RESULT ========> DRAW!");
        }
    }



    public static int greedyApproach(int goldAmount, ArrayList<Piece> piecesWillBeUsedGreedy){

        ArrayList<Piece> piecesSelected = new ArrayList<>();//list that keeps selected elements
        int usedGold = 0;//gold amount used, will be updated below
        int sumOfAttackPoints = 0;//sum of attack points, will be updated in below

        while(piecesWillBeUsedGreedy.size() !=0 && usedGold<goldAmount){ //if list is not empty and not pass the gold amount limit
            Piece pieceThatHasMaxAPpC =findPieceThatHasMaxAttPerCost(piecesWillBeUsedGreedy);//attack point per cost
            piecesWillBeUsedGreedy.remove(pieceThatHasMaxAPpC);//we will remove it whether we select it or not
            if(pieceThatHasMaxAPpC.goldCost <= goldAmount-usedGold){//if gold is enough
                piecesSelected.add(pieceThatHasMaxAPpC);//adding the element in selected list
                sumOfAttackPoints += pieceThatHasMaxAPpC.attackPoint;//updating
                usedGold +=pieceThatHasMaxAPpC.goldCost;//updating
                for(int i = 0; i< piecesWillBeUsedGreedy.size(); i++){//this for loop in order to delete other elements in same type
                    if(Objects.equals(piecesWillBeUsedGreedy.get(i).type, pieceThatHasMaxAPpC.type)){
                        piecesWillBeUsedGreedy.remove(piecesWillBeUsedGreedy.get(i));
                        i--;//we need to i--, if we do not we cannot delete some elements in same type,
                        //since when an element is removed, its index will be index of next of it.
                    }
                }
            }else{
                continue;
            }

        }



        //printing results
        System.out.println("------------------------------------------------------------------------------------------");
        System.out.println("Total attack point obtained with Greedy Approach: " + sumOfAttackPoints);
        System.out.println("Total amount of gold used with Greedy Approach: " + usedGold);
        System.out.println();
        System.out.println("Selected Pieces:");
        for (Piece value : piecesSelected) {
            System.out.println("Piece Type:" + value.type + " / Piece Name:" + value.name + " / Piece Cost:" + value.goldCost + " / Piece Attack Point:" + value.attackPoint);
        }

        System.out.println("------------------------------------------------------------------------------------------");


        return sumOfAttackPoints;//will be used determining the winner


    }

    public static Piece findPieceThatHasMaxAttPerCost(ArrayList<Piece> piecesWillBeUsedInGreedy){//this function will be called in greedyApproach
        //to find element that has attack point per cost
        double min = 0;
        Piece maxPiece = null;

        for(int i = 0; i< piecesWillBeUsedInGreedy.size(); i++){
            if(piecesWillBeUsedInGreedy.get(i).attackPointPerCost >= min){
                maxPiece = piecesWillBeUsedInGreedy.get(i);
                min = piecesWillBeUsedInGreedy.get(i).attackPointPerCost;
            }
        }

        return maxPiece;
    }


    public static int randomApproach(int goldAmount, ArrayList<Piece> piecesWillBeUsedRandom, int max_level_allowed, int number_of_available_pieces_per_level){
        ArrayList<Piece> piecesSelected = new ArrayList<>();////list that keeps selected elements
        int sumOfAttackPoints = 0;//gold amount used, will be updated below
        int usedGold = 0;//sum of attack points, will be updated in below
        int i = 0;
        int boundForRandomNumber = max_level_allowed *number_of_available_pieces_per_level;//it will be used in determining random number
        while(i< max_level_allowed){
            Random random = new Random();
            int randomChoiceIndex = random.nextInt(boundForRandomNumber);//boundForRandomNumber is not included
            Piece pieceDeleted = piecesWillBeUsedRandom.remove(randomChoiceIndex);//we will remove it whether we select it or not
            boundForRandomNumber--;//since we delete one element bound should be decreased
            if(pieceDeleted.goldCost <= goldAmount-usedGold){//if gold is enough
                piecesSelected.add(pieceDeleted);//add this element to pieceSelected
                sumOfAttackPoints += pieceDeleted.attackPoint;//update values
                usedGold +=pieceDeleted.goldCost;//update values
                for(int j = 0;j<piecesWillBeUsedRandom.size();j++){//this for loop in order to delete other elements in same type
                    if(Objects.equals(piecesWillBeUsedRandom.get(j).type, pieceDeleted.type)){
                        piecesWillBeUsedRandom.remove(piecesWillBeUsedRandom.get(j));
                        boundForRandomNumber--;//we will decrease bound when we remove each element
                        j--;//we need to i--, if we do not we cannot delete some elements in same type,
                        //since when an element is removed, its index will be index of next of it.
                    }
                }
            }


            i++;
        }
        //Printing results
        System.out.println("------------------------------------------------------------------------------------------");
        System.out.println("Total attack point obtained with Random Approach: " + sumOfAttackPoints);
        System.out.println("Total amount of gold used with Random Approach: " + usedGold);
        System.out.println();
        System.out.println("Selected Pieces:");
        for (Piece value : piecesSelected) {
            System.out.println("Piece Type:" + value.type + " / Piece Name:" + value.name + " / Piece Cost:" + value.goldCost + " / Piece Attack Point:" + value.attackPoint);
        }

        System.out.println("------------------------------------------------------------------------------------------");

        return sumOfAttackPoints;
    }





    public static int dynamicProgrammingApproach(int goldAmount, ArrayList<Piece> piecesWillBeUsedDynamic, ArrayList<Integer> eachPieceTypeArray, int max_level_allowed, int number_of_available_pieces_per_level){
        int lengthOfPiecesDynamicArray = piecesWillBeUsedDynamic.size();
        int usedGold = 0;

        int[][] table = new int[lengthOfPiecesDynamicArray +1][goldAmount +1];//table that keeps results of subproblems

        for(int attackPoint = 0; attackPoint < lengthOfPiecesDynamicArray +1; attackPoint++){
            for(int gold = 0; gold < goldAmount +1; gold++){
                if(attackPoint == 0 || gold == 0){ // if one of them is zero
                    table[attackPoint][gold] = 0;
                }
                else if(piecesWillBeUsedDynamic.get(attackPoint -1).goldCost <= gold){//if gold is enough to get
                    //the gold variable here will be 0-- 1,2,3,4,5,6,7..... after that it will be 0---1,2,3,4,5,6... when attackPoint increase again
                    //Thus we will fill the table
                    int numberWillBeAdded = 0;
                    int lastGroup = eachPieceTypeArray.get(attackPoint -1)-1;//this reference keeps previous group
                    int innerEachCheckIndex = 1;
                    //with this loop block we prevents taking an type more than once.
                    while(innerEachCheckIndex < lengthOfPiecesDynamicArray +1){
                        if(eachPieceTypeArray.get(innerEachCheckIndex -1) == lastGroup){
                            if(table[innerEachCheckIndex][gold-piecesWillBeUsedDynamic.get(attackPoint-1).goldCost] > numberWillBeAdded){
                                numberWillBeAdded = table[innerEachCheckIndex][gold-piecesWillBeUsedDynamic.get(attackPoint-1).goldCost];
                            }
                        }
                        innerEachCheckIndex++;
                    }

                    //we would write "table[attackPoint-1][gold-piecesWillBeUsedDynamic.get(attackPoint-1).goldCost]" and delete while loop
                    //however it will allow taking an type more than once, we do not want it.
                    //detailed explanation and analyze is placed in report.
                    table[attackPoint][gold] = Math.max(numberWillBeAdded + piecesWillBeUsedDynamic.get(attackPoint -1).attackPoint, table[attackPoint -1][gold]);
                }else{//if it is not enough then, we will get the number above of it
                    table[attackPoint][gold] = table[attackPoint -1][gold];
                }
                
            }
        }

        //Printing results
        System.out.println("------------------------------------------------------------------------------------------");
        System.out.println("Total attack point obtained with Dynamic Programming Approach: "+ table[lengthOfPiecesDynamicArray][goldAmount]);
        //with backtracking function we are able to see which elements is selected
        ArrayList<Piece> piecesSelected = backTracking(table, goldAmount, lengthOfPiecesDynamicArray, piecesWillBeUsedDynamic, max_level_allowed, number_of_available_pieces_per_level);

        //Finding total used gold amount
        for (Piece eachPiece : piecesSelected) {
            usedGold = usedGold + eachPiece.goldCost;
        }

        System.out.println("Total amount of gold used with Dynamic Programming Approach: " + usedGold);

        System.out.println();
        System.out.println("Selected Pieces:");
        Collections.reverse(piecesSelected);
        for (Piece eachPiece : piecesSelected) {
            System.out.println("Piece Type:" + eachPiece.type + " / Piece Name:" + eachPiece.name + " / Piece Cost:" + eachPiece.goldCost + " / Piece Attack Point:" + eachPiece.attackPoint);
        }

        System.out.println("------------------------------------------------------------------------------------------");
        return table[lengthOfPiecesDynamicArray][goldAmount];
    }


    public static ArrayList<Piece> backTracking(int[][] dp, int goldAmountIndex, int piecesSizeIndex, ArrayList<Piece> piecesWillBeUsedDynamic, int max_level_allowed, int number_of_available_pieces_per_level){

        ArrayList<Piece> piecesSelected = new ArrayList<>();//list that keeps selected items

        int diagonalIndex = max_level_allowed * number_of_available_pieces_per_level - number_of_available_pieces_per_level;//if we cannot go up or left then we should check diagonal entry
        //this diagonalIndex keeps the available one
        int defaultMaxGold = goldAmountIndex;
        int usedGold = 0;

        while(piecesSizeIndex !=0 && usedGold != defaultMaxGold && goldAmountIndex != 0 ) {//if pieces size which means row number is not 0
            //gold limit is not reached

            //checking up and left, up step is takes priority
            if (dp[piecesSizeIndex][goldAmountIndex] == dp[piecesSizeIndex][goldAmountIndex - 1] || dp[piecesSizeIndex][goldAmountIndex] == dp[piecesSizeIndex - 1][goldAmountIndex]) {

                if(dp[piecesSizeIndex][goldAmountIndex] == dp[piecesSizeIndex - 1][goldAmountIndex]){
                    piecesSizeIndex--;//one row up
                }
                else if(dp[piecesSizeIndex][goldAmountIndex] == dp[piecesSizeIndex][goldAmountIndex - 1]){
                    goldAmountIndex--;//one column left
                }
                //checking diagonal entry.
                //We will go one step above, after that, need to go left.
                //We can find our last column by subtracting the our cost number(for this row) from the column number we are currently in.
                /*
                If we could choose as many of each type as we wanted, we would not need this variable. Because while backtracking,
                when an element of the same type is taken, the other cannot be retrieved,
                so looking at the element in the upper diagonal (if the current element in table is not same with element above it or element left of it) can put it in an infinite loop.
                Every time we go diagonally (when we throw the piece corresponding to that line in our bag),
                we keep the last row of the other type of stone in the “diagonalIndex” variable, without looking at it,
                since the element above it will be of the same type as itself.
                */
            }else if(dp[diagonalIndex][goldAmountIndex - piecesWillBeUsedDynamic.get(piecesSizeIndex -1).goldCost] + piecesWillBeUsedDynamic.get(piecesSizeIndex -1).attackPoint == dp[piecesSizeIndex][goldAmountIndex]){
                goldAmountIndex -= piecesWillBeUsedDynamic.get(piecesSizeIndex -1).goldCost;
                usedGold = usedGold + piecesWillBeUsedDynamic.get(piecesSizeIndex -1).goldCost;//updating used gold
                piecesSelected.add(piecesWillBeUsedDynamic.get(piecesSizeIndex -1));
                piecesSizeIndex -=(piecesSizeIndex - diagonalIndex);
                diagonalIndex -= number_of_available_pieces_per_level;
            }

            if(diagonalIndex >= piecesSizeIndex){//if left or up condition is met then diagonalIndex cannot be decreased we need the check it in every loop iteration.
                diagonalIndex = diagonalIndex - number_of_available_pieces_per_level;
            }


        }
        return piecesSelected;
    }

    public static void readFile(ArrayList<Piece> pieces){//reads the input_1.csv file
        //takes an arraylist which keeps Piece objects in it
        File file = new File("input_1.csv");
        Scanner input = null;
        try {
            input = new Scanner(file);
            String str = input.nextLine();//this line is used for passing line 1 in input_1.csv which is "Hero,Piece Type,Gold,Attack Points"
            while (input.hasNextLine()) {
                str = input.nextLine();
                String[] eachLine = str.split(",");//splitting according to ","
                Piece piece = new Piece(eachLine[0],eachLine[1],Integer.parseInt(eachLine[2]),Integer.parseInt(eachLine[3]));//creating object
                pieces.add(piece);//adding this piece
            }

        } catch (IOException exp) {
            exp.printStackTrace();
        }

        input.close();

    }
}

class Piece {

    public String name;//Hero
    public String type;//Piece Type
    public int goldCost;//Gold
    public int attackPoint;//Attack Points
    public double attackPointPerCost;//will be used in greedy approach

    //constructor
    public Piece(String name, String type,int goldCost,int attackPoint) {
        this.name = name;
        this.type = type;
        this.goldCost = goldCost;
        this.attackPoint = attackPoint;
        this.attackPointPerCost = (double)this.attackPoint/this.goldCost;
    }
}

