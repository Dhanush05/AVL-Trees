/*
Author: Dhanush Pakanati
*/
import java.io.*;
import java.util.ArrayList;
public class avltree {
    //1. Write class to initialize node definition
    public static class Node{
        //node with left and right child
        Node leftNode;
        Node rightNode;
        int key;
        //each node has height parameter - Distance to leaf
        int height;
        Node(int val){
            this.key  = val;
        }
    }
    //we should always have access to root - even after balancing. 
    //so having global variable accessible only in this class
    private static Node root;
    
    //2. write function for insert
    public static void insert(int key){
        root = insert(key, root);
    }

    public static Node insert(int key, Node node){
        if(node == null) return new Node(key);
        if(node.key > key){
            node.leftNode  = insert(key, node.leftNode);
        }
        else if (node.key < key){
            node.rightNode = insert(key, node.rightNode);
        }
        else{
            System.out.println("Duplicate key - throw exception - Not inserted - Ignored");
        }
        return balanceTree(node);

    }
    //3. write function for rebalance
    //need not be accessible to other files - declaring private
    //will return the root node reference
    private static Node balanceTree(Node node){
        //first get the height of that node
        calculateHeight(node);
        //get the balance factor of that node
        int bf = balanceFactor(node);
        //write functions to choose rotation - LL/LR/RR/RL
        if(bf < -1){
            //right subtree tree height is more 
            //check how many rotations are needed
            if(getHeight(node.rightNode.rightNode)>getHeight(node.rightNode.leftNode)){
                node = rLeft(node);
            }
            else{
                node.rightNode= rRight(node.rightNode);
                node = rLeft(node);
            }
        }
        else if (bf > 1){
            //left subtree heigh is more
            //check R/RL rotation 
            if(getHeight(node.leftNode.leftNode)>getHeight(node.leftNode.rightNode)){
                node = rRight(node);
            }
            else{
                node.leftNode = rLeft(node.leftNode);
                node = rRight(node);
            }
            
        }
        return node;
    }
    //4. write function for right rotation(RR)
    private static Node rRight(Node node){
        Node A = node.leftNode;
        Node C = A.rightNode;
        A.rightNode  = node;
        node.leftNode = C;
        calculateHeight(node);
        calculateHeight(A);
        return A;

    } 
    //5. write function for left rotation(LL)
    private static  Node rLeft(Node node){
        Node A = node.rightNode;
        Node C  = A.leftNode;
        A.leftNode = node;
        node.rightNode = C;
        calculateHeight(node);
        calculateHeight(A);
        return A;

    }

    //6. get height
    private static void calculateHeight(Node node){
        node.height = Math.max(getHeight(node.leftNode),getHeight(node.rightNode))+1;
    }
    private static int getHeight(Node node){
        if(node == null) return -1;
        else return node.height;
    }
    //7. calculate the balance factor of that node
    private static int balanceFactor(Node node){
        if(node == null) return 0;
        else return getHeight(node.leftNode) - getHeight(node.rightNode);
    }
    
    //8. write function to search node
    public static Node find(int val){
        //System.out.println("Root is "+root.key);
        Node curr = root;
        //boolean found  =false;
        while(curr!=null){
            if(curr.key == val){
                //found = true;
                break;
            }
            if(curr.key<val){
                curr = curr.rightNode;
            }
            else{
                curr = curr.leftNode;
            }
            
        }
        //code below to print output in terminal - ignore - output visible in output.txt
        // if(found){
        //     System.out.println("Found: "+curr.key);
        // }
        // else{
        //     System.out.println("Not Found");
        // }
        return curr;
    }
    //9. write function to delete
    public static void delete(int key){
        root = delete(root,key);
    }
    private static Node delete(Node node, int key){
        if(node == null){
            return node;
        }
        else if(node.key > key){
            //recursively find the node to be deleted in left subtree
            node.leftNode = delete(node.leftNode,key);
        }
        else if(node.key < key){
            //recursively find the node to be deleted in right subtree.
            node.rightNode = delete(node.rightNode, key);
        }
        else{
            if(node.leftNode == null||node.rightNode == null){
                //check code here later
                if(node.leftNode == null){
                    node = node.rightNode;
                }
                else{
                    node = node.leftNode;
                }
            }
            else{
                Node leftmost = leftMostChild(node.rightNode);
                node.key = leftmost.key;
                node.rightNode = delete(node.rightNode, node.key);
            }
        }
        if(node!=null){
            node = balanceTree(node);
        }
        return node;
    }
    //10. Write function to find left most child
    private static Node leftMostChild(Node node){
        Node curr = node;
        while(curr.leftNode!=null){
            curr =  curr.leftNode;
        }
        return curr;
    }
    //11. write function to search in a range
    private static void search(int k1, int k2, ArrayList<Integer> list){
        searchRange(root, k1, k2,list);
        //System.out.println("List size: "+list.size());
    }
    private static void searchRange(Node node, int key1, int key2, ArrayList<Integer> list){
        
        if(node == null){
            return;
        }
        if(key1<node.key){
            searchRange(node.leftNode, key1, key2,list);
        }
        if(key1 <= node.key && key2 >= node.key){
            //System.out.println("Range: "+node.key);
            list.add(node.key);
        }
        searchRange(node.rightNode, key1, key2,list);
    }
    public static void initialize(){
        //Initialize();
    }
    
    public static void main(String[] args) throws IOException{
        //get file name from command line
        String inputfilename  = args[0];
        //use file reader/buffer reader to read file
        File file = new File(inputfilename+".txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        File newfile = new File("output_file.txt");
        newfile.createNewFile();
        //use file writer/buffer writer to write into file
        BufferedWriter bw = new BufferedWriter(new FileWriter(newfile));
        String input;
        while((input = br.readLine()) !=null){
            //direct the input lines to respective functions
            if(input.startsWith("Initialize()")){
                initialize();
            }
            else if(input.startsWith("Insert")){
                insert(Integer.parseInt(input.substring(input.indexOf("(")+1,input.indexOf(")"))));
            }
            else if(input.startsWith("Delete")){
                delete(Integer.parseInt(input.substring(input.indexOf("(")+1,input.indexOf(")"))));
            }
            else if(input.startsWith("Search")){
                String nums = input.substring(input.indexOf("(")+1,input.indexOf(")"));
                String[] temp = nums.split(",");
                //System.out.print("check 1");
                if(temp.length == 1){
                    int num = Integer.parseInt(temp[0]);
                    Node node = find(num);
                    if(node!=null){
                        System.out.println(node.key);
                        bw.write(String.valueOf(node.key));
                        bw.write("\n");
                    }
                    else{
                        System.out.println("NULL");
                        //buffer writer
                        bw.write("NULL\n");
                    }
                }
                else{
                    ArrayList<Integer> list = new ArrayList<>();
                    search(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]),list);
                    String result = "";
                    for(int i:list){
                        result+=i+", ";
                        System.out.print(i+", ");
                    }
                    //write to buffer
                    bw.write(result.substring(0,result.length()-2));
                    bw.write("\n");
                }
            }
        }
        //close the buffer reader adn writer to prevent resource leaking
        br.close();
        bw.close();
        
    }
}
