package double_space;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class QuadradicSpace {
    private final int p;
    private int a;
    private int b;

    private int collisionNumber = 0;
    private Integer[] hashTable;


    public QuadradicSpace(List<Integer> list, int mapIntoSize) {
        int setSize = list.size();
        this.p = nextPrime(setSize);
        set_a_b_randomly();
        this.hashTable = new Integer[mapIntoSize];
        run(list);
    }

    private void run(List<Integer> list) {
        while (!this.buildTable(list)) {
            this.collisionNumber++;
            this.hashTable = new Integer[this.hashTable.length];
            set_a_b_randomly();
        }
    }

    private boolean buildTable(List<Integer> list) {
        for (int key : list) {
            int index = hash(key);
            if (this.hashTable[index] != null && this.hashTable[index] != key) { // collision detected
                return false;
            } else {
                this.hashTable[index] = key;
            }
        }
        return true;
    }

    private int hash(int key) {
        return ((this.a * key + this.b) % this.p) % this.hashTable.length;
    }

    private void set_a_b_randomly() {
        Random random = new Random();
        this.a = 1 + random.nextInt(this.p - 1);
        this.b = random.nextInt(this.p);
    }

    private int nextPrime(int number) {
        while (true) {
            if (isPrime(number)) {
                return number;
            }
            number++;
        }
    }

    private boolean isPrime(int number) {
        return IntStream.rangeClosed(2, (int) (Math.sqrt(number)))
                .allMatch(n -> number % n != 0);
    }


    public int getCollisionNumber() {
        return collisionNumber;
    }

    public Integer[] getHashTable() {
        return hashTable;
    }
}
