package top.mckingdom.auspice.costs;

import java.util.Set;

public class CostResponse {

    public static final CostResponse SUCCESS = new CostResponse();


    private Set<Cost<?, ?>> successCosts;
    private Set<Cost<?, ?>> failedCosts;

    private CostResponse() {
        
    }

    public CostResponse(Set<Cost<?, ?>> successCosts, Set<Cost<?, ?>>failedCosts) {
        this.successCosts = successCosts;
        this.failedCosts = failedCosts;
    }


    public Set<Cost<?, ?>> getSuccessCosts() {
        return successCosts;
    }

    public void setSuccessCosts(Set<Cost<?, ?>> successCosts) {
        this.successCosts = successCosts;
    }

    public Set<Cost<?, ?>> getFailedCosts() {
        return failedCosts;
    }

    public void setFailedCosts(Set<Cost<?, ?>> failedCosts) {
        this.failedCosts = failedCosts;
    }
}
