/*
Author: Dhanush Pakanati
*/

import java.io.*;
import java.util.ArrayList;
public class avltree {
    //write class to initialize node definition
    public static class Node{
        //node with left and right child
        int key;
        Node leftNode;
        Node rightNode;
        int height;
        Node(int val){
            this.key  = val;
        }
    }
    //we should always have access to root - even after rebalance. 
    //so having global variable accessible only in this file
    private static Node root;
    public Node getRoot() {
        return root;
    }
    
    //write function for insert
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
            System.out.println("Duplicate key - throw exception");
        }
        return rebalance(node);

    }
    //write funciotn for rebalance
    //need not be accessible to other files - declaring private
    //change name from rebalance to something
    //write function to choose rotation
    private static Node rebalance(Node node){
        //first get the height of that node
        calculateHeight(node);
        //get the balance factor of that node
        int bf = balanceFactor(node);
        //////////////here balance factor formula is reverse change it later
        if(bf > 1){
            //right subtree tree height is more 
            if(getHeight(node.rightNode.rightNode)>getHeight(node.rightNode.leftNode)){
                node = rLeft(node);
            }
            else{
                node.rightNode= rRight(node.rightNode);
                node = rLeft(node);
            }
        }
        else if (bf < -1){
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
    //write function for right rotation(RR)
    private static Node rRight(Node node){
        Node A = node.leftNode;
        Node C = A.rightNode;
        A.rightNode  = node;
        node.leftNode = C;
        calculateHeight(node);
        calculateHeight(A);
        return A;

    } 
    ////write function for left rotation(LL)
    private static  Node rLeft(Node node){
        Node A = node.rightNode;
        Node C  = A.leftNode;
        A.leftNode = node;
        node.rightNode = C;
        calculateHeight(node);
        calculateHeight(A);
        return A;

    }

    //get height
    private static void calculateHeight(Node node){
        node.height = Math.max(getHeight(node.leftNode),getHeight(node.rightNode))+1;
    }
    private static int getHeight(Node node){
        if(node == null) return -1;
        else return node.height;
    }
    //calculate the balance factor of that node
    private static int balanceFactor(Node node){
        if(node == null) return 0;
        else return getHeight(node.rightNode) - getHeight(node.leftNode);
    }
    
    //write function to search node
    public static Node find(int val){
        //System.out.println("Root is "+root.key);
        Node curr = root;
        boolean found  =false;
        while(curr!=null){
            if(curr.key == val){
                found = true;
                break;
            }
            ///change here code
            curr = curr.key < val ? curr.rightNode:curr.leftNode;
        }
        // if(found){
        //     System.out.println("Found: "+curr.key);
        // }
        // else{
        //     System.out.println("Not Found");
        // }
        return curr;
    }
    //write function to delete
    public static void delete(int key){
        root = delete(root,key);
    }
    private static Node delete(Node node, int key){
        if(node == null){
            return node;
        }
        else if(node.key > key){
            node.leftNode = delete(node.leftNode,key);
        }
        else if(node.key < key){
            node.rightNode = delete(node.rightNode, key);
        }
        else{
            if(node.leftNode == null||node.rightNode == null){
                //check code here later
                node = (node.leftNode == null)? node.rightNode:node.leftNode;
            }
            else{
                Node leftmost = leftMostChild(node.rightNode);
                node.key = leftmost.key;
                node.rightNode = delete(node.rightNode, node.key);
            }
        }
        if(node!=null){
            node = rebalance(node);
        }
        return node;
    }
    private static Node leftMostChild(Node node){
        Node curr = node;
        while(curr.leftNode!=null){
            curr =  curr.leftNode;
        }
        return curr;
    }
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
        //System.out.println("sdfsdf");
        String inputfilename  = args[0];
        File file = new File(inputfilename+".txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        File newfile = new File("output.txt");
        newfile.createNewFile();
        BufferedWriter bw = new BufferedWriter(new FileWriter(newfile));
        String input;
        while((input = br.readLine()) !=null){
            if(input.startsWith("Initialize()")){
                initialize();
            }
            else if(input.startsWith("Insert")){
                int num = Integer.parseInt(input.substring(input.indexOf("(")+1,input.indexOf(")")));
                insert(num);
                // String answer = st.substring(st.indexOf("(")+1, st.indexOf(")"));
            }
            else if(input.startsWith("Delete")){
                int num = Integer.parseInt(input.substring(input.indexOf("(")+1,input.indexOf(")")));
                delete(num);
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
            else if(input.startsWith("Delete")){
                int num = Integer.parseInt(input.substring(input.indexOf("(")+1,input.indexOf(")")));
                delete(num);
            }

        }
        br.close();
        bw.close();
        
    }
}
