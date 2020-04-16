package co.dsproject.gestor;

public class Pila<T> {

private NodeGeneric<T> top;

    public Pila() {
        top=null;
    }

    public boolean empty() {
        return (top == null);
    }

    public void push(T item){
        NodeGeneric<T> newp= new NodeGeneric<T>(item);
        if (top!=null){
            top.setNext(newp);
        }
        else{
            top=newp;
        }
    }
    public T pop(){
        T item=null;
        if(!empty()){
            item=top.getData();
            top=top.getNext();
        }
        return item;

    }
}



