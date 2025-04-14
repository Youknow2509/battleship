package github.com.youknow2509.battleship.model.repo;

import com.google.gson.annotations.Expose;

import java.util.List;

public class ResponseAI {
    @Expose
    private List<Integer> optimal_shot;


    public List<Integer> getOptimal_shot() {
        return optimal_shot;
    }

    public void setOptimal_shot(List<Integer> optimal_shot) {
        this.optimal_shot = optimal_shot;
    }
}
