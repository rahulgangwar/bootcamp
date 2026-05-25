package com.example.scenarios.ConsistentHashing;

import com.example.scenarios.ConsistentHashing.hashing.MD5Hash;
import com.example.scenarios.ConsistentHashing.node.ServiceNode;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Demo {
    public static int VIRTUAL_NODE_COUNT = 200;

    private static List<ServiceNode> getNodes() {
        return Arrays.asList(
                new ServiceNode("Node 1"),
                new ServiceNode("Node 2"),
                new ServiceNode("Node 3"),
                new ServiceNode("Node 4"));
    }

    private static List<String> getIPAddressForRequests() {
        List<String> ipAddresses = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            ipAddresses.add(getRandomIp());
        }
        return ipAddresses;
    }

    private static String getRandomIp() {
        int[][] range = {
            {607649792, 608174079}, // 36.56.0.0-36.63.255.255
            {1038614528, 1039007743}, // 61.232.0.0-61.237.255.255
            {1783627776, 1784676351}, // 106.80.0.0-106.95.255.255
            {2035023872, 2035154943}, // 121.76.0.0-121.77.255.255
            {2078801920, 2079064063}, // 123.232.0.0-123.235.255.255
            {-1950089216, -1948778497}, // 139.196.0.0-139.215.255.255
            {-1425539072, -1425014785}, // 171.8.0.0-171.15.255.255
            {-1236271104, -1235419137}, // 182.80.0.0-182.92.255.255
            {-770113536, -768606209}, // 210.25.0.0-210.47.255.255
            {-569376768, -564133889}, // 222.16.0.0-222.95.255.255
        };

        int index = new Random().nextInt(10);
        return convertNumberToIP(
                range[index][0] + new Random().nextInt(range[index][1] - range[index][0]));
    }

    private static String convertNumberToIP(int num) {
        int[] b = new int[4];
        b[0] = (num >> 24) & 0xff;
        b[1] = (num >> 16) & 0xff;
        b[2] = (num >> 8) & 0xff;
        b[3] = num & 0xff;
        return b[0] + "." + b[1] + "." + b[2] + "." + b[3];
    }

    public static void main(String[] args) {
        System.out.println("========== Output distribution result==========");
        System.out.println(
                routeRequests(
                        new ConsistentHashRouter<>(getNodes(), VIRTUAL_NODE_COUNT, new MD5Hash()),
                        getIPAddressForRequests()));
    }

    private static TreeMap<String, AtomicInteger> routeRequests(
            ConsistentHashRouter<ServiceNode> router, List<String> requestIps) {
        TreeMap<String, AtomicInteger> countByNode = new TreeMap<>();
        for (String requestIp : requestIps) {
            ServiceNode destNode = router.getNode(requestIp);
            countByNode.putIfAbsent(destNode.getKey(), new AtomicInteger());
            countByNode.get(destNode.getKey()).incrementAndGet();
            System.out.println(requestIp + " is routed to " + destNode);
        }
        return countByNode;
    }
}
