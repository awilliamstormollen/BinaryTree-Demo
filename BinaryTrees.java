/**
* Binary Trees
* <p>
* This class takes file input of State Objects puts it into a binary tree.
* Then the data is manipulated with traverals and deletion.
* <p>
*
* @author Alexander Williams 
* @version 21 Nov, 2017
*/



import java.util.*;
import java.io.*;
import java.text.*;
import java.lang.*;

public class BinaryTrees
{

       /**
        * Main method, reads from a CSV file, then sorts data into stack and queue using a Binary Search Tree
        *
        * @paramn String[] args
        *
        * @return void
        */
        public static void main(String[] args)
        {
                // local declarations
                String name = "str";
                String capital = "str";
                String abbre = "str";        // abbreviation
                int population = 0;
                String region = "str";
                int seats = 0;               // U.S. house seats
                int nElems = 0;

                String stringPopulation = "str";
                String stringSeats = "str";

                BinarySearchTree  tree = new BinarySearchTree ();

                Scanner scannerObject = null;

                try
                {
                    scannerObject = new Scanner(new File("States4.csv"));
                }

                catch (FileNotFoundException e)
                {
                    System.out.println("Error opening the file");  // print error if cant open
                    System.exit(0);
                }

                scannerObject.useDelimiter(",|\n");

              String word1 = scannerObject.next();
                String word2 = scannerObject.next();
                String word3 = scannerObject.next();
                String word4 = scannerObject.next();
                String word5 = scannerObject.next();
                String word6 = scannerObject.next();

                int i = 0;



                do
                {
                    //scannerObject.nextLine();
                    name =             scannerObject.next();
                    capital =          scannerObject.next();
                    abbre =            scannerObject.next();
                    stringPopulation = scannerObject.next();
                    region =           scannerObject.next();
                    stringSeats =      scannerObject.next();

                    State state = new State (name, capital, abbre, population, region, seats);


                    try
                    {
                        population = Integer.valueOf(stringPopulation);  // converts strings to int
                        seats = Integer.valueOf(stringSeats);

                        tree.insert(name, population);
                        i++;

                    }

                    catch (NumberFormatException e)
                    {

                    }

                  }while(scannerObject.hasNext() == true);


                  tree.traverse(1);
                  tree.traverse(2);
                  tree.traverse(3);


        }


}

/**
* Node class creates a Node to be used in the Binary Search Tree.
*
* @author Alexander Williams
* @version 21 Nov, 2017
*/
class Node
{
  String stateName;
  int statePopulation;
  Node leftChild;
  Node rightChild;

  /**
  * Node Constructor. creates node
  *
  * @paramn String state, int population
  *
  * @return void
  */
  public Node(String state, int population)
  {
    stateName = state;
    statePopulation = population;
  }

  /**
  * Prints node
  *
  * @paramn none
  *
  * @return void
  */
  public void printNode()
  {
    System.out.printf("%-25s%,10d\n", stateName, statePopulation);
  }
}

/**
* BinarySearchTree  class creates a Binary Search Tree.
*
* @author Alexander Williams
* @version 21 Nov, 2017
*/
class BinarySearchTree
{
  private Node root;

  /**
  * Tree constructor. Creates tree.
  *
  * @paramn none
  *
  * @return void
  */
  public BinarySearchTree()
  { root = null; }

  /**
  * Finds a node in the tree.
  *
  * @paramn String key
  *
  * @return null or current node.
  */
  public Node find(String key)
  {
    Node current = root;

    while(current.stateName != key)
    {
      if(key.compareTo(current.stateName) <= 0)
      {
        current = current.leftChild;
      }
      else
      {
        current = current.rightChild;
      }
      if (current == null)
      {
        return null;
      }
    }
    return current;
  } // end find

  /**
  * Inserts a node into the tree.
  *
  * @paramn state name and state population
  *
  * @return void
  */
  public void insert(String state, int population)
  {
    Node newNode = new Node(state, population);
    newNode.stateName = state;
    newNode.statePopulation = population;

    if(root == null)
    {
      root = newNode;
    }
    else
    {
      Node current = root;
      Node parent;
      while(true)
      {
        parent = current;
        if(state.compareTo(current.stateName) <= 0) // state < current.stateName
        {
          current = current.leftChild;
          if(current == null)
          {
            parent.leftChild = newNode;
            return;
          }
        }
        else
        {
          current = current.rightChild;
          if (current == null)
          {
            parent.rightChild = newNode;
            return;
          }

        }
      }
    }
  } // end insert

  /**
  * Deletes a node in the tree.
  *
  * @paramn State name as key.
  *
  * @return bolean.
  */
  public boolean delete(String key)
  {
    Node current = root;
    Node parent = root;
    boolean isLeftChild = true;

    while(current.stateName != key)
    {
      parent = current;
      if (key.compareTo(current.stateName) <= 0) // key < current.stateName
      {
        isLeftChild = true;
        current = current.leftChild;
      }
      else
      {
        isLeftChild = false;
        current = current.rightChild;
      }
      if(current == null)
      return false;
    } // found node to delete

    if(current.leftChild == null && current.rightChild == null)
    {
      if(current == root)
      {
        root = null;
      }
      else if(isLeftChild)
      {
        parent.leftChild = null;
      }
      else
      {
        parent.rightChild = null;
      }
    }

    // if no right child replace with left subtree
    else if(current.rightChild == null)
    if(current == root)
      root = current.leftChild;
    else if(isLeftChild)
      parent.leftChild = current.leftChild;
    else
      parent.rightChild = current.leftChild;

    // if no left child, replace with right subtree
    else if(current.leftChild == null)
    if(current == root)
      root = current.rightChild;
    else if(isLeftChild)
      parent.leftChild = current.rightChild;
    else
      parent.rightChild = current.rightChild;

    else //two children so replace with inorder successor
    {
      Node successor = getSuccessor(current);

      if(current == root)
        root = successor;
      else if (isLeftChild)
        parent.leftChild = successor;
      else
        parent.rightChild = successor;

      successor.leftChild = current.leftChild;
    }

    return true;
  } // end delete

  /**
  * Gets node to success node that is deleted/
  *
  * @paramn node to be deleted.
  *
  * @return successor.
  */
  private Node getSuccessor(Node delNode)
  {
    Node successorParent = delNode;
    Node successor = delNode;
    Node current = delNode.rightChild;

    while(current != null)
    {
      successorParent = successor;
      successor = current;
      current = current.leftChild;
    }

    if(successor != delNode.rightChild)
    {
      successorParent.leftChild = successor.rightChild;
      successor.rightChild = delNode.rightChild;
    }
    return successor;
  }

  /**
  * Call from another method to choose traversal type. Traverses then gives output.
  *
  * @paramn 1, 2 or 3 to choose traveral type.
  *
  * @return void
  */
  public void traverse(int traverseType)
  {
    switch(traverseType)
    {
      case 1: System.out.print("\nPreorder traversal:\n");
              preOrder(root);
              break;
      case 2: System.out.print("\nInorder traversal:\n");
              inOrder(root);
              break;
      case 3: System.out.print("\nPostorder traversal:\n");
              postOrder(root);
              break;
    }
    System.out.println();
  }

  /**
  * traverses the tree through preorder routine
  *
  * @paramn local rootnode
  *
  * @return void
  */
  private void preOrder(Node localRoot)
  {
    if(localRoot != null)
    {
      localRoot.printNode();
      preOrder(localRoot.leftChild);
      preOrder(localRoot.rightChild);
    }
  }

  /**
  * traverses the tree through inorder routine
  *
  * @paramn local rootnode
  *
  * @return void
  */
  private void inOrder(Node localRoot)
  {
    if(localRoot != null)
    {
      inOrder(localRoot.leftChild);
      localRoot.printNode();
      inOrder(localRoot.rightChild);
    }
  }

  /**
  * traverses the tree through postorder routine
  *
  * @paramn local rootnode
  *
  * @return void
  */
  private void postOrder(Node localRoot)
  {
    if(localRoot != null)
    {
      postOrder(localRoot.leftChild);
      postOrder(localRoot.rightChild);
      localRoot.printNode();
    }
  }

}

/**
* State class is divided into several attributes to be used in the creation of a State object.
*
* @author Alexander Williams
* @version 21 Nov, 2017
*/
class State
{
    public String name;
    public String capital;
    public String abbre;     // abbreviation
    public int population;
    public String region;
    public int seats;

    public State(String name, String capital, String abbre, int population, String region, int seats)
    {
        this.name =       name;
        this.capital =    capital;
        this.abbre =      abbre;
        this.population = population;
        this.region =     region;
        this.seats =      seats;
    }

    /**
    * Getters, get attribute stored in object array
    *
    * @paramn
    *
    * @return name
    */
    public String getName()
    {
        return name;
    }

    /**
    * Getters, get attribute stored in object array
    *
    * @paramn
    *
    * @return capital
    */
    public String getCapital()
    {
        return capital;
    }

    /**
    * Getters, get attribute stored in object array
    *
    * @paramn
    *
    * @return abbreviation
    */
    public String getAbbre()
    {
        return abbre;
    }

    /**
    * Getters, get attribute stored in object array
    *
    * @paramn
    *
    * @return population
    */
    public int getPopulation()
    {
        return population;
    }

    /**
    * Getters, get attribute stored in object array
    *
    * @paramn
    *
    * @return region
    */
    public String getRegion()
    {
        return region;
    }

    /**
    * Getters, get attribute stored in object array
    *
    * @paramn
    *
    * @return seats
    */
    public int getSeats()
    {
        return seats;
    }

    /**
    * Setters, set attribute stored in object array
    *
    * @paramn state name
    *
    * @return void
    */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
    * Setters, set attribute stored in object array
    *
    * @paramn state capital
    *
    * @return void
    */
    public void setCapital(String capital)
    {
        this.capital = capital;
    }

    /**
    * Setters, set attribute stored in object array
    *
    * @paramn state abbreviation
    *
    * @return void
    */
    public void setAbbre(String abbre)
    {
        this.abbre = abbre;
    }

    /**
    * Setters, set attribute stored in object array
    *
    * @paramn state population
    *
    * @return void
    */
    public void setPopulation(int population)
    {
        this.population = population;
    }

    /**
    * Setters, set attribute stored in object array
    *
    * @paramn state region
    *
    * @return void
    */
    public void setRegion(String region)
    {
        this.region = region;
    }

    /**
    * Setters, set attribute stored in object array
    *
    * @paramn state seats
    *
    * @return void
    */
    public void setSeats(int seats)
    {
        this.seats = seats;
    }

    /**
    * compares strings
    *
    * @paramn two strings to compare
    *
    * @return value of comparison
    */
    public int compareTo(String a, String b)
    {
        return a.compareTo(b);
    }

    /**
    * Displays a state in the state object.  Loop for full database
    *
    * @paramn
    *
    * @return void
    */
    public void displayState()
    {
        System.out.printf(name + "          " + capital + "          " + abbre + "          " + population + "          " + region + "          " + seats );
        System.out.printf("\n");

        System.out.printf("\n");
    }


    /**
    * displays attributes in state object
    *
    * @paramn
    *
    * @return String of state object
    */
    public String info()
    {
        return "State Name: " + name + "\n" + "Capital City: " + capital + "\n" + "State Abbr: " + abbre + "\n" +
        "State Population: " + population + "\n" + "Region: " + region + "\n" + "U.S. House Seats: " + seats + "\n";
    }
} // end program
