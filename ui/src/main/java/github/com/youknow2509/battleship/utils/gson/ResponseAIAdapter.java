package github.com.youknow2509.battleship.utils.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import github.com.youknow2509.battleship.model.repo.ResponseAI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResponseAIAdapter extends TypeAdapter<ResponseAI> {

    @Override
    public void write(JsonWriter out, ResponseAI value) throws IOException {
        out.beginObject();
        out.name("optimal_shot");
        out.beginArray();
        for (Integer shot : value.getOptimal_shot()) {
            out.value(shot);
        }
        out.endArray();
        out.endObject();
    }

    @Override
    public ResponseAI read(JsonReader in) throws IOException {
        in.beginObject();
        List<Integer> optimalShot = new ArrayList<>();
        while (in.hasNext()) {
            String name = in.nextName();
            if (name.equals("optimal_shot")) {
                in.beginArray();
                while (in.hasNext()) {
                    optimalShot.add(in.nextInt());
                }
                in.endArray();
            }
        }
        in.endObject();
        ResponseAI responseAI = new ResponseAI();
        responseAI.setOptimal_shot(optimalShot);
        return responseAI;
    }
}
