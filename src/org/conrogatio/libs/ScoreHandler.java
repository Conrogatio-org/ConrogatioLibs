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

	/**
	 * 
	 * @param scoreListLength
	 *            The number of score entries you want to be stored on the high
	 *            score list
	 * @param storageLocation
	 *            The location to store the scores on. Default is
	 *            com.example.appname.prefs.scores
	 * @param context
	 *            Your current context
	 */
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

	/**
	 * 
	 * @param name
	 *            The player name to assign the score to
	 * @param score
	 *            The score achieved
	 */
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
		getScores();
	}

	/**
	 * 
	 * @return An String[] of all the stored names. The highest score will be in
	 *         String[0]
	 */
	public String[] getNameList() {
		return names;
	}

	/**
	 * 
	 * @return An int[] of all the stored scores. The highest score will be in
	 *         int[0]
	 */
	public int[] getScoreList() {
		return scores;
	}

	/**
	 * 
	 * @return String with the last used name
	 */
	public String getLatestName() {
		return latestName;
	}

	/**
	 * 
	 * @return int with the last achieved score
	 */
	public int getLatestScore() {
		return latestScore;
	}

	/**
	 * 
	 * @return int with the last achieved place in the high score list. If not
	 *         ranked, returned value is 0
	 */
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

	/**
	 * Use to remove all the stored scores
	 */
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
