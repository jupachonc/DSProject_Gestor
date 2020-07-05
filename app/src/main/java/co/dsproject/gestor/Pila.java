package co.dsproject.gestor;


public class Pila<T extends Comparable> {
    private NodeGeneric<T> top;

    public Pila() {
        top = null;
    }

    public boolean empty() {
        return (top == null);
    }

    public void push(T data){
        NodeGeneric next = new NodeGeneric<T>(data);
        if (top != null) {
            next.setNext(top);
        }
        top = next;

    }
    public T pop(){
        if(empty()) return null;
        T data = top.getData();
        top = top.getNext();
        return data;
    }
}



