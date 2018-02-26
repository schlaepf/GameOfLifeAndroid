package apps.phil.gameoflife.cellpatterns;

import android.content.Context;

import java.util.HashMap;

import apps.phil.gameoflife.R;

/**
 * Created by phil on 01.02.18.
 */

public class PatternDisposer {

    private HashMap<String, CellPattern> patterns;

    private Context context;

    public PatternDisposer(Context context) {
        this.context = context;
        initPatternDictionary();
    }

    private void initPatternDictionary() {
        patterns = new HashMap<>();
        // add all the patterns to the hashmap
        patterns.put(context.getResources().getStringArray(R.array.pattern_names)[0], new Glider());
        patterns.put(context.getResources().getStringArray(R.array.pattern_names)[1], new LightWeightSpaceShip());
        patterns.put(context.getResources().getStringArray(R.array.pattern_names)[2], new FPentomino());
        patterns.put(context.getResources().getStringArray(R.array.pattern_names)[3], new Pulsator());
        patterns.put(context.getResources().getStringArray(R.array.pattern_names)[4], new TwoMagnets());
        patterns.put(context.getResources().getStringArray(R.array.pattern_names)[5], new TenCellRow());
        patterns.put(context.getResources().getStringArray(R.array.pattern_names)[6], new Tumbler());
        patterns.put(context.getResources().getStringArray(R.array.pattern_names)[7], new GliderGun());
    }

    public CellPattern getPattern(String pattern) {

        return patterns.get(pattern);
    }
}
