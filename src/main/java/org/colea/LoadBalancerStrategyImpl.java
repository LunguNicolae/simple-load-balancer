package org.colea;

import java.util.List;
import java.util.Random;

public class LoadBalancerStrategyImpl implements LoadBalancerStrategy {

    private final Random random = new Random();

    @Override
    public String getServer(List<String> serverReferences) {
        return serverReferences.get(random.nextInt(serverReferences.size()));
    }
}
