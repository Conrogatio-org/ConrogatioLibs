package org.conrogatio.libs;

import android.content.Context;

public class ScoreHandler {
	private String latestName;
	private int latestScore;
	private int latestPlace;
	private String[] names;
	private int[] scores;
	private static final String KEY_NAMES = "hsname";
	private static final String KEY_SCORES = "hsscore";
	private static final String KEY_LATEST_NAME = "latestName";
	private static final String KEY_LATEST_SCORE = "latestScore";
	private static final String KEY_LATEST_PLACE = "latestPlace";
	private static PrefsStorageHandler scorePSH;

	public ScoreHandler(int scoreListLength, String storageLocation, Context context) {
		names = new String[scoreListLength];
		scores = new int[scoreListLength];
		for (int i = 0; i < names.length; i++) {
			names[i] = "none";
			scores[i] = 0;
		}
		scorePSH = new PrefsStorageHandler(storageLocation, context);
		getScores();
	}

	private void getScores() {
		scorePSH.fetch(KEY_LATEST_NAME, "");
		scorePSH.fetch(KEY_LATEST_SCORE, 0);
		scorePSH.fetch(KEY_LATEST_PLACE, 0);
		for (int i = 0; i < names.length; i++) {
			names[i] = scorePSH.fetch(KEY_NAMES + Integer.toString(i), names[i]);
			scores[i] = scorePSH.fetch(KEY_SCORES + Integer.toString(i), scores[i]);
		}
	}

	public void addScore(String name, int score) {
		// Adds latest playername and score to lastName and lastScore
		latestName = name;
		latestScore = score;
		// Adds playername and score to the correct place in names[] and
		// scores[].
		int k = names.length - 1; // names.length currently equals 20
		for (int i = 0; i < names.length; i++) {
			if (score > scores[i]) {
				k = i;
				for (int j = names.length - 1; j >= i; j--) {
					if (j != 0) {
						names[j] = names[j - 1];
						scores[j] = scores[j - 1];
					}
				}
				break;
			}
		}
		if (score > scores[k]) {
			names[k] = name;
			scores[k] = score;
			latestPlace = k + 1;
		} else {
			latestPlace = 0;
		}
		writeScores();
	}

	public String[] getNameList() {
		return names;
	}

	public int[] getScoreList() {
		return scores;
	}

	public String getLatestName() {
		return latestName;
	}

	public int getLatestScore() {
		return latestScore;
	}

	public int getLatestPlace() {
		return latestPlace;
	}

	private void writeScores() {
		for (int i = 0; i < names.length; i++) {
			scorePSH.put(KEY_NAMES + Integer.toString(i), names[i]);
			scorePSH.put(KEY_SCORES + Integer.toString(i), scores[i]);
		}
		scorePSH.put(KEY_LATEST_NAME, latestName);
		scorePSH.put(KEY_LATEST_SCORE, latestScore);
		scorePSH.put(KEY_LATEST_PLACE, latestPlace);
	}

	public void resetScores() {
		for (int i = 0; i < names.length; i++) {
			names[i] = "none";
			scores[i] = 0;
			scorePSH.put(KEY_NAMES + Integer.toString(i), names[i]);
			scorePSH.put(KEY_SCORES + Integer.toString(i), scores[i]);
		}
		scorePSH.put(KEY_LATEST_NAME, "none");
		scorePSH.put(KEY_LATEST_SCORE, 0);
		scorePSH.put(KEY_LATEST_PLACE, "none");
	}
}
