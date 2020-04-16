package co.dsproject.gestor;

public class Pila<T> {

    private NodeGeneric<T> next;
    private int top;

    public Pila() {
        top=0;
    }

    public boolean empty() {
        return (top == 0);
    }
    public void pop2(){
        this.next=null;
        this.top=0;
    }
    public void push(T data){
        next= new NodeGeneric<T> (data);
        top++;

    }
    public T pop(){
        if(empty()){
            throw new RuntimeException("Stack is empty");
        }
        T data=next.getData();
        next=next.getNext();
        top--;
        if (top==0){
            pop2();
        }
        return data;
    }
}



