import java.util.*;
import equipment.*;
import collections.EquipmentArraySet;

public class Main {
    public static void main(String[] args) {
        Equipment sword = new Sword("Arming Sword", 1200, 900);
        Equipment shield = new Shield("Kite Shield", 2800, 1400);
        Equipment helm = new Equipment("Bascinet", 1300, 700);
        Equipment swordDup = new Sword("Arming Sword", 1200, 900);

        EquipmentArraySet set = new EquipmentArraySet();
        System.out.println("Порожній сет: розмір=" + set.size());

        set.add(sword);
        set.add(shield);
        set.add(helm);
        set.add(swordDup);
        System.out.println("Після додавання: " + set);

        EquipmentArraySet one = new EquipmentArraySet(shield);
        System.out.println("Сет з одного елемента: " + one);

        List<Equipment> src = Arrays.asList(
                new Equipment("Cuirass", 4800, 2700),
                new Equipment("Gauntlets", 900, 400),
                shield
        );
        EquipmentArraySet fromColl = new EquipmentArraySet(src);
        System.out.println("З колекції: " + fromColl);

        set.addAll(fromColl);
        System.out.println("Після об'єднання: " + set);

        set.remove(helm);
        System.out.println("Після видалення: " + set);

        set.retainAll(Arrays.asList(sword, shield));
        System.out.println("Залишилось: " + set);

        System.out.print("Ітерація: ");
        for (Equipment e : set) System.out.print(e + " | ");
        System.out.println();

        set.clear();
        System.out.println("Після очищення: розмір=" + set.size());
    }
}
