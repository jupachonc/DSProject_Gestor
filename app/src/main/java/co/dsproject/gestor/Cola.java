package co.dsproject.gestor;

public class Cola<T> {

private NodeGeneric<T> front, rear;
private int size;

    public Cola(){
        front = null;
        rear = null;
    }

    public void enqueue(T item ){
        NodeGeneric<T> newp= new NodeGeneric<T>(item);
        if(rear != null){
            rear.setNext(newp);
        }
        else{
            front = newp;
        }
        rear = newp;
        size += 1;
    }

    public boolean empty() { return (front == null); }

    public T dequeue(){
        T item = null;
        if(!empty()){
           item = front.getData();
           front = front.getNext();
        }
        size -= 1;
        return item;

    }

    public int getSize() {
        return size;
    }
}
//public int dequeue() {
//int item=-1;
//if(!empty()){
//item=front.getData();
//front=front.getNext();
//
//}
//return item;
//}