public class DoubleLinkedList
{
    private Node head;
    private Node tail;
    private int length;

    // tanto al nodo siguiente (next) como al anterior (prev)
    class Node
    {
        int value;
        Node next;
        Node prev;

        Node(int value)
        {
            this.value = value;
        }
    }

    public DoubleLinkedList(int value)
    {
        Node newNode = new Node(value);
        this.length = 1;
        this.head = newNode;
        this.tail = newNode;
    }

    public Node getHead()
    {
        return this.head;
    }

    // Asigna manualmente el nodo cabeza
    public void setHead(Node head)
    {
        this.head = head;
    }

    // Asigna manualmente el nodo cola
    public void setTail(Node tail)
    {
        this.tail = tail;
    }

    // Regresa el último nodo (o null si la lista está vacía)
    public Node getTail()
    {
        return this.tail;
    }

    // Regresa la cantidad de nodos de la lista
    public int getLength()
    {
        return this.length;
    }

    // Si la lista está vacía, el nuevo nodo es head y tail.
    // Si no, se enlaza en ambos sentidos con tail: tail.next -> nuevo, nuevo.prev -> tail.
    public void append(int value)
    {
        Node newNode = new Node(value);
        if (length == 0)
        {
            this.head = newNode;
            this.tail = newNode;
        }
        else
        {
            this.tail.next = newNode;
            newNode.prev = this.tail;
            this.tail = newNode;
        }
        length++;
    }

    // Si la lista está vacía, el nuevo nodo es head y tail.
    // Si no, se enlaza en ambos sentidos con head: nuevo.next -> head, head.prev -> nuevo.
    public void prepend(int value)
    {
        Node newNode = new Node(value);
        if (length == 0)
        {
            this.head = newNode;
            this.tail = newNode;
        }
        else
        {
            newNode.next = this.head;
            this.head.prev = newNode;
            this.head = newNode;
        }
        length++;
    }

    // Regresa false si el índice es inválido (menor que 0 o mayor que length).
    public boolean insert(int index, int value)
    {
        if (index < 0 || index > length) return false;
        if (index == 0)
        {
            prepend(value);
            return true;
        }
        if (index == length)
        {
            append(value);
            return true;
        }
        Node newNode = new Node(value);
        Node before = getNode(index - 1);
        Node after = before.next;

        newNode.prev = before;
        newNode.next = after;
        before.next = newNode;
        after.prev = newNode;
        length++;
        return true;
    }

    // head avanza al siguiente nodo, y a ese nuevo head se le borra el prev.
    // El nodo removido se desconecta por completo. Si la lista queda vacía, tail = null.
    public Node removeFirst()
    {
        if (length == 0) return null;
        Node temp = this.head;
        if (length == 1)
        {
            this.head = null;
            this.tail = null;
        }
        else
        {
            this.head = this.head.next;
            this.head.prev = null;
            temp.next = null;
        }
        length--;
        return temp;
    }

    // Gracias a "prev" ya no hace falta recorrer la lista: tail retrocede un nodo
    // y a ese nuevo tail se le borra el next. Se ejecuta en tiempo constante.
    public Node removeLast()
    {
        if (length == 0) return null;
        Node temp = this.tail;
        if (length == 1)
        {
            this.head = null;
            this.tail = null;
        }
        else
        {
            this.tail = this.tail.prev;
            this.tail.next = null;
            temp.prev = null;
        }
        length--;
        return temp;
    }

    // Elimina y regresa el nodo en la posición "index"
    public Node remove(int index)
    {
        if (index < 0 || index >= length) return null;
        if (index == 0) return removeFirst();
        if (index == length - 1) return removeLast();

        Node temp = getNode(index);
        temp.prev.next = temp.next;
        temp.next.prev = temp.prev;
        temp.next = null;
        temp.prev = null;
        length--;
        return temp;
    }

    // regresa el nodo en la posición "index" (null si es inválido).
    // Optimización típica de la lista doble: si el índice está en la primera mitad
    // se recorre desde head hacia adelante; si está en la segunda, desde tail hacia atrás.
    public Node getNode(int index)
    {
        if (index < 0 || index >= length) return null;
        Node temp;
        if (index < length / 2)
        {
            temp = this.head;
            for (int i = 0; i < index; i++)
            {
                temp = temp.next;
            }
        }
        else
        {
            temp = this.tail;
            for (int i = length - 1; i > index; i--)
            {
                temp = temp.prev;
            }
        }
        return temp;
    }

    // cambia el valor del nodo en la posición "index".
    // Regresa true si lo logró, false si el índice es inválido.
    public boolean setNode(int index, int value)
    {
        Node temp = getNode(index);
        if (temp == null) return false;
        temp.value = value;
        return true;
    }

    // "slow" avanza de uno en uno y "fast" de dos en dos. Cuando fast llega al final,
    // slow está en la mitad. Si la cantidad de nodos es par, regresa el de la derecha.
    public Node medio()
    {
        if (head == null) return null;
        Node slow = this.head;
        Node fast = this.head;
        while (fast != null && fast.next != null)
        {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    // si hay ciclo, fast alcanza a slow, y si no, fast llega a null.
    public boolean esCiclo()
    {
        Node slow = this.head;
        Node fast = this.head;
        while (fast != null && fast.next != null)
        {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) return true;
        }
        return false;
    }

    // En cada nodo se intercambian sus referencias next y prev; después se
    // intercambian head y tail. Tras el intercambio, el siguiente nodo a visitar
    public void reversa()
    {
        Node current = this.head;
        Node temp = null;
        while (current != null)
        {
            temp = current.prev;
            current.prev = current.next;
            current.next = temp;
            current = current.prev;
        }
        temp = this.head;
        this.head = this.tail;
        this.tail = temp;
    }

    // Se utiliza el método para detectar listas cicladas antes de poder imprimirla
    public void printList()
    {
        if(!esCiclo())
        {
            Node temp = this.head;
            while (temp != null)
            {
                System.out.print(temp.value + " ");
                temp = temp.next;
            }
            System.out.println();
        }
        else
        {
            System.out.println("La lista está ciclada");
        }
    }
}