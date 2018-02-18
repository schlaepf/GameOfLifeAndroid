package apps.phil.gameoflife.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.PreferenceManager;

import apps.phil.gameoflife.R;
import apps.phil.gameoflife.util.Config;

public class PatternListFragment extends DialogFragment {

    public PatternListFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.pattern_list_fragment_title)
                .setItems(R.array.pattern_names, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String[] patterns = getResources().getStringArray(R.array.pattern_names);
                        String chosenPattern = patterns[which];
                        Context context = getActivity();
                        PreferenceManager.getDefaultSharedPreferences(context)
                                .edit()
                                .putString(Config.KEY_PATTERN, chosenPattern)
                                .apply();
//                        SharedPreferences sharedPreferences = context.getSharedPreferences(getString(R.string.pattern_pref), Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.putString(getString(R.string.pattern_pref_id), chosenPattern);
                    }
                });
        return builder.create();
    }
}
