package co.dsproject.gestor;

public class BstCar {

    //Inner Class: Node
    private class Node {
        private Node left;
        private Car data;
        private Node right;
        public Node(){
            this(null);
        }
        public Node(Car data) {
            left = null;
            this.data = data;
            right = null;
        }
    }

    private Node root;
    public BstCar() {
        root = null;
    }
    public void insertBST(Car num) {
        root = insert(num,root);
    }

    private Node insert(Car data, Node p) {
        if(p == null)
            p = new Node(data);
        else {
            if (data.getPlaca().compareTo(p.data.getPlaca()) < 0) {
                p.left = insert(data, p.left);
            }else {
                if (data.getPlaca().compareTo(p.data.getPlaca()) > 0) {
                    p.right = insert(data, p.right);
                }else {
                    System.out.println("Item in tree and not inserted.");
                }
            }
        }
        return p;
    }

    public Node remove(String placa, Node p){
        if(p!= null) {
            if (placa.compareTo(p.data.getPlaca()) < 0) {
                p.left = remove(placa, p.left);
            }else if (placa.compareTo(p.data.getPlaca()) > 0) {
                p.right = remove(placa, p.right);
            }else if (p.left == null && p.right == null) {
                p = null;
            }else if (p.left == null) {
                p = p.right;
            }else if (p.right == null) {
                p = p.left;
            }else {
                Node t = findMin(p.right);
                p.data = t.data;
                p.right = remove(p.data.getPlaca(), p.right);
            }

        }else{
            System.out.println("Item not in tree and not removed");
        }
        return p;
    }
    private Node findMin(Node p) {
        if(p!= null) {
            while (p.left != null) {
                p = p.left;
            }
        }
        return p;
    }
    public void traverseBST() {
        System.out.print("The tree is:");
        if(root!= null){
            traverse(root);
        }else{
            System.out.print(" " + "Empty");
            System.out.println();
        }
    }
    private void traverse(Node ptr) {
        if(ptr.left != null) {
            traverse(ptr.left);
        }
        System.out.print(" " + ptr.data.getPlaca());
        if(ptr.right!= null){
            traverse(ptr.right);
        }
    }
    public Node getRoot(){

        return this.root;

    }

}
