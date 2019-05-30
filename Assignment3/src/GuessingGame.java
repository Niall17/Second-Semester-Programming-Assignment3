import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;
public class GuessingGame
{


	
	public static void main(String[] args) 
	{
		load();
	}
	
	public static void createTree(BinaryTree<String> tree)
	{
		//initial leaves
		BinaryTree<String> dog= new BinaryTree<String>("Dog");
		BinaryTree<String> monkey= new BinaryTree<String>("Monkey");
		BinaryTree<String> budgie= new BinaryTree<String>("Budgie");
		BinaryTree<String> snake= new BinaryTree<String>("Snake");
		BinaryTree<String> chevvy= new BinaryTree<String>("Chevvy Chase");
		BinaryTree<String> ford = new BinaryTree<String>("Ford Focus");
		//subtrees
		BinaryTree<String> domestic= new BinaryTree<String>("is it domestic?",dog,monkey);
		BinaryTree<String> bird= new BinaryTree<String>("Is it a bird?",budgie,snake);
		BinaryTree<String> mammal= new BinaryTree<String>("Is it a Mammal?",domestic,bird);
		BinaryTree<String> actor = new BinaryTree<String>("Are you thinking of an actor?",chevvy,ford);
		//root node
		tree.setTree("Are you thinking of an animal?",mammal,actor);
	}
	
	public static void beginGame(BinaryTree<String> tree)
	{
		while(true) 
		{
			
			BinaryNodeInterface<String> currentNode = tree.getRootNode();
		
			while(!currentNode.isLeaf())
			{
					// This will loop while it is not currently at a leaf
					//If the answer is yes the right child is next, if no then left child
					if (JOptionPane.showConfirmDialog(null,currentNode.getData())==0)
					{
						currentNode = currentNode.getLeftChild();
					}
					else 
					{
						currentNode = currentNode.getRightChild();
					}
					
			}
			
			if(JOptionPane.showConfirmDialog(null, "Are you thinking of " + currentNode.getData() + "?") == 0)
			{					//a guess is made at the end and if correct it will ask to play again or store tree or exit 		
				
				if(JOptionPane.showConfirmDialog(null,  "Would you like to play again?")==0)	{												
					load();   		
				}
				
				if(JOptionPane.showConfirmDialog(null,  "Do you want to store this tree")==0) {
					treeStorage(tree);
					System.exit(0);
					}
				else 
					System.exit(0);
				
			}
			
			else 
			{ 	
				String newData = JOptionPane.showInputDialog("What is the correct answer?");				
				//Create a new leaf if the guess was wrong
				String oldData = currentNode.getData();																	
				String newQuestion = JOptionPane.showInputDialog("Please enter a new question where the answer is YES for " + newData + " and NO for " + oldData);		
				//A new question node is added to differentiate the new answer
				newQuestion= newQuestion.trim();    																													
				if (! newQuestion.endsWith("?")) 
				{
					//this adds a question mark if the user forgot
					newQuestion += "?";	
				}
				
																																								
				currentNode.setData(newQuestion);
				currentNode.setLeftChild(new BinaryNode<String>(newData));												
				//Sets the left node
				currentNode.setRightChild(new BinaryNode<String>(oldData));												
				//sets the right node
				if(JOptionPane.showConfirmDialog(null, "Play Again, with the new tree?")==0) {}						
				//Ask the user to play with their new updated tree
				else 
				{
					if(JOptionPane.showConfirmDialog(null, "Do you want to store your new tree?")==0) 
					{
					treeStorage(tree);																				
					//Stores tree for user if they want
					System.exit(0);																					
					}																										
				}
			}
		}
	}
	
	public static void load() 
	{
		Object[] choose = {"Load a stored game","Start a new game"}; //choices for user in beginning
		int n = JOptionPane.showOptionDialog(null,"Would you like to Use a stored game or start a new one?","Create your game",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null, choose,choose[0]); 
		if (n==0) 
		{
			BinaryTree<String> Prev = new BinaryTree<String>(); //this will load a previously stored tree and play again
			Prev=loadTree();																						
			beginGame(Prev);
		}																						
		
		else 
		{
			BinaryTree<String> Game = new BinaryTree<String>(); //this will start a new game if the user wants
			createTree(Game);																					
			beginGame(Game);
		}
	}
	
	public static BinaryTree<String> loadTree() 
	{
		BinaryTree<String> oldTree = new BinaryTree<String>(); //here we will load a previous tree
		
		try {
	        FileInputStream fileLoad = new FileInputStream("C:\\storedTree\\tree.ser"); //checks file location
	        ObjectInputStream loaded = new ObjectInputStream(fileLoad); //loads it in to the system
	        oldTree = (BinaryTree<String>) loaded.readObject(); //assigns it to tree
	        loaded.close();
	        fileLoad.close();
	        return oldTree; //return the previous tree
	    } 
		catch (IOException x) { //close if no trees found
	        x.printStackTrace();
	        return null;
	     } 
		catch (ClassNotFoundException y) {
	        
	        y.printStackTrace();
	        return null;
			}
	}
	public static void treeStorage(BinaryTree<String> tree) 
	{					//here the tree is serialized and stored on the system as a .ser file
		
		try {
	        FileOutputStream store = new FileOutputStream("C:\\storedTree\\tree.ser");
	        ObjectOutputStream send = new ObjectOutputStream(store);
	        //the tree is sent out to the file here
	        send.writeObject(tree);
	        send.close();
	        store.close();
	        System.out.printf("The tree is now saved in C:\\storedTree");}
	        
			catch (IOException i) {
	        i.printStackTrace();
	     }
		
	}

}
