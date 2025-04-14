package github.com.youknow2509.battleship.utils.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import github.com.youknow2509.battleship.model.repo.BodyReq;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BodyReqAdapter extends TypeAdapter<BodyReq> {

    @Override
    public void write(JsonWriter out, BodyReq value) throws IOException {
        out.beginObject();
        out.name("board");
        out.beginArray();
        for (List<Integer> row : value.getBoard()) {
            out.beginArray();
            for (Integer cell : row) {
                out.value(cell);
            }
            out.endArray();
        }
        out.endArray();

        out.name("ship_lengths");
        out.beginArray();
        for (Integer ship : value.getListShips()) {
            out.value(ship);
        }
        out.endArray();

        out.endObject();
    }

    @Override
    public BodyReq read(JsonReader in) throws IOException {
        in.beginObject();
        List<List<Integer>> board = new ArrayList<>();
        List<Integer> listShips = new ArrayList<>();

        while (in.hasNext()) {
            String name = in.nextName();
            if (name.equals("board")) {
                in.beginArray();
                while (in.hasNext()) {
                    List<Integer> row = new ArrayList<>();
                    in.beginArray();
                    while (in.hasNext()) {
                        row.add(in.nextInt());
                    }
                    in.endArray();
                    board.add(row);
                }
                in.endArray();
            } else if (name.equals("ship_lengths")) {
                in.beginArray();
                while (in.hasNext()) {
                    listShips.add(in.nextInt());
                }
                in.endArray();
            }
        }
        in.endObject();

        return new BodyReq(board, listShips);
    }
}

