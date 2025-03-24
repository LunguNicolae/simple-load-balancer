import org.colea.LoadBalancer;
import org.colea.LoadBalancerStrategyImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LoadBalancerTest {

    private final LoadBalancer loadBalancer = new LoadBalancer(new LoadBalancerStrategyImpl());

    @Test
    public void testServerAddressForNullChecks() {
        assertThrows(IllegalArgumentException.class, () -> loadBalancer.registerServer(""), "The server address is not valid.");
    }

    //TODO check for maximum size and for duplicates

    @Test
    public void testServerAddressesForExceededSize() {
        for (int i = 0; i < 10; i++) {
            loadBalancer.registerServer("Server" + i);
        }

        assertEquals(10, loadBalancer.getServerReferences().size());
        assertThrows(RuntimeException.class, () -> loadBalancer.registerServer("Server11"), "The maximum allowed number of servers was exceed.");
    }

    @Test
    public void testServerAddressesForDuplicates() {
        loadBalancer.registerServer("Server");
        assertThrows(IllegalArgumentException.class, () -> loadBalancer.registerServer("Server"), "The server address is already present.");
        assertEquals(1, loadBalancer.getServerReferences().size());
    }

    @Test
    public void testServerAddressesAreSuccessfullyRegistered() {
        for (int i = 0; i < 10; i++) {
            loadBalancer.registerServer("Server" + i);
        }

        assertEquals(10, loadBalancer.getServerReferences().size());
    }

    @Test
    public void testElectServerWhenThereAreNotAnyAvailable_ShouldThrowAnRuntimeException() {
        assertThrows(RuntimeException.class, loadBalancer::electServer, "There are not any servers available for election.");
    }

    @Test
    public void testElectServerWhenServersAreAvailable_ShouldReturnAValidServer() {
        for (int i = 0; i < 10; i++) {
            loadBalancer.registerServer("Server" + i);
        }

        loadBalancer.electServer();
        assertEquals(10, loadBalancer.getServerReferences().size());
    }
}
