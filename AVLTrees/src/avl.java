public class avl {
    //write class to initialize node definition
    public class Node{
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
    private Node root;
    
    //write function for insert
    public void insert(int key){
        root = insert(key, root);
    }
    public Node insert(int key, Node node){
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
        return null;

    }
    //write funciotn for rebalance
    //need not be accessible to other files - declaring private
    //change name from rebalance to something
    private Node rebalance(Node node){
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
            
        }
        return node;
    }
    //rotate right
    private Node rRight(Node node){
        Node A = node.leftNode;
        Node C = A.rightNode;
        A.rightNode  = node;
        node.leftNode = C;
        calculateHeight(node);
        calculateHeight(A);
        return A;

    } 
    //rotate left
    private Node rLeft(Node node){
        Node A = node.rightNode;
        Node C  = A.leftNode;
        A.leftNode = node;
        node.rightNode = C;
        return A;

    }

    //get height
    private void calculateHeight(Node node){
        node.height = Math.max(getHeight(node.leftNode),getHeight(node.rightNode))+1;
    }
    private int getHeight(Node node){
        if(node == null) return -1;
        else return node.height;
    }
    //calculate the balance factor of that node
    private int balanceFactor(Node node){
        if(node == null) return 0;
        else return getHeight(node.rightNode) - getHeight(node.leftNode);
    }
    //write function for left rotation(LL)
    //write function for right rotation(RR)
    //write function to choose rotation
    
    //write function to search node
    //write function to delete
    public void delete(){

    }
    public static void main(String[] args) {
        System.out.println("Hello world");
    
    }
}
