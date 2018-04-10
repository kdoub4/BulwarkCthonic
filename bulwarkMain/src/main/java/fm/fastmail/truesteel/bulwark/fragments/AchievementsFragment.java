package fm.fastmail.truesteel.bulwark.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import fm.fastmail.truesteel.bulwark.R;
import fm.fastmail.truesteel.bulwark.util.Achievements;

public class AchievementsFragment extends SherlockFragment {
	@SuppressWarnings("unused")
	private static final String TAG = "AchievementsFragment";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ListView listView = (ListView) inflater.inflate(R.layout.fragment_achievements, container, false);

		final Achievements achievements = new Achievements(getActivity());
		listView.setAdapter(achievements.getNewAchievementsAdapter());

		return listView;
	}
}