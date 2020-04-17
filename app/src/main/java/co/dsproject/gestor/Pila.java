package co.dsproject.gestor;

public class Pila<T extends Comparable> {
    private DoubleNode<T> top;

    public Pila() {
        top = null;
    }

    public boolean empty() {
        return (top == null);
    }

    public void push(T data){
        DoubleNode next = new DoubleNode<T>(data);
        if (top != null) {
            top.setNext(next);
            next.setBack(top);
        }
        top = next;

    }
    public T pop(){
        if(empty()) throw new RuntimeException("Stack is empty");
        T data = top.getData();
        top = top.getBack();
        return data;
    }
}



