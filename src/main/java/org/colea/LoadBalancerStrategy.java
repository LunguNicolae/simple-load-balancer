package org.colea;

import java.util.List;

public interface LoadBalancerStrategy {

    String getServer(List<String> serverReferences);
}
