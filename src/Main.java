public class Main
{
    public static void main(String[] args)
    {
        DoubleLinkedList list = new DoubleLinkedList(2);
        list.append(3);
        list.prepend(1);                       // 1 2 3
        list.insert(3, 5);                     // 1 2 3 5
        list.insert(3, 4);                     // 1 2 3 4 5
        list.printList();

        System.out.println("Medio (5 nodos): " + list.medio().value);      // 3
        list.append(6);
        System.out.println("Medio (6 nodos): " + list.medio().value);      // 4

        list.setNode(0, 10);                   // 10 2 3 4 5 6
        System.out.println("getNode(5): " + list.getNode(5).value);        // 6

        list.reversa();                        // 6 5 4 3 2 10
        list.printList();

        list.removeFirst();                    // 5 4 3 2 10
        list.removeLast();                     // 5 4 3 2
        list.remove(1);                        // 5 3 2
        list.printList();

        System.out.println("¿Ciclo? " + list.esCiclo());                   // false
        list.getTail().next = list.getHead();  // creamos un ciclo
        System.out.println("¿Ciclo? " + list.esCiclo());                   // true
    }
}