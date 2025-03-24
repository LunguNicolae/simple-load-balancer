package org.colea;

import java.util.ArrayList;
import java.util.List;

public class LoadBalancer {

    private final List<String> serverReferences = new ArrayList<>();
    private final LoadBalancerStrategy loadBalancerStrategy;

    public LoadBalancer(LoadBalancerStrategy loadBalancerStrategy) {
        this.loadBalancerStrategy = loadBalancerStrategy;
    }

    public void registerServer(String serverAddress) {
        if (serverAddress == null || serverAddress.isBlank()) {
            throw new IllegalArgumentException("The server address is not valid.");
        }

        synchronized (serverReferences) {
            if (serverReferences.size() >= 10) {
                throw new RuntimeException("The maximum allowed number of servers was exceed.");
            }

            if (serverReferences.contains(serverAddress)) {
                throw new IllegalArgumentException("The server address is already present.");
            }

            serverReferences.add(serverAddress);
        }
    }

    public String electServer() {
        if (getServerReferences().isEmpty()) {
            throw new RuntimeException("There are not any servers available for election.");
        }

        return loadBalancerStrategy.getServer(serverReferences);
    }

    public List<String> getServerReferences() {
        return List.copyOf(serverReferences);
    }
}
