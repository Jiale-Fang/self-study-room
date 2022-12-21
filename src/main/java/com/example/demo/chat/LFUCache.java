package com.example.demo.chat;

import java.util.HashMap;
import java.util.LinkedList;

public class LFUCache {
    private static int minFreq;
    public static HashMap<Integer, LinkedList<Node>> freqMap = new HashMap<>();
    public static HashMap<Integer, Node> nodeMap = new HashMap<>();
    private static int capacity;

    public LFUCache(int capacity) {
        this.capacity = capacity;
    }

    public static void updateFrequency(int key) {
//        if (capacity == 0) return -1;
//        if (!nodeMap.containsKey(key)) return -1;

        Node node = nodeMap.get(key);
        freqMap.get(node.freq).remove(node);
        if (freqMap.get(node.freq).size() == 0) {
            freqMap.remove(node.freq);
            if (minFreq == node.freq) {
                minFreq++;
            }
        }

        node.freq++;
        LinkedList<Node> list = freqMap.getOrDefault(node.freq, new LinkedList<Node>());
        list.offerLast(node);
        freqMap.put(node.freq, list);
    }

    //return remove user id
    public int put(int key) {
        if (capacity == 0) return -1;
        int removeId = -1;
        //not exist
        if (!nodeMap.containsKey(key)) {
            //maximum capacity
            if (capacity == nodeMap.size()) {
                Node removeNode = freqMap.get(minFreq).pollFirst();
                nodeMap.remove(removeNode.key);
                removeId = removeNode.key;
                if (freqMap.get(minFreq).size() == 0) {
                    freqMap.remove(minFreq);
                    minFreq++;
                }
            }
            Node node = new Node(key);
            LinkedList<Node> list = freqMap.getOrDefault(1, new LinkedList<Node>());
            list.offerLast(node);
            freqMap.put(1, list);
            nodeMap.put(key, node);
            minFreq = 1;
        } else {//exist
            Node node = nodeMap.get(key);
            freqMap.get(node.freq).remove(node);
            if (freqMap.get(node.freq).size() == 0) {
                freqMap.remove(node.freq);
                if (minFreq == node.freq) {
                    minFreq++;
                }
            }
            node.freq++;
            LinkedList<Node> list = freqMap.getOrDefault(node.freq, new LinkedList<Node>());
            list.offerLast(node);
            freqMap.put(node.freq, list);
        }
        return removeId;
    }

}

class Node {
    //userId
    int key;
    int freq;

    public Node(int key) {
        this.key = key;
        this.freq = 1;
    }
}

