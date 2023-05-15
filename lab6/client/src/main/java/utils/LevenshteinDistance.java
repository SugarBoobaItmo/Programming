package utils;

public class LevenshteinDistance {
    public static int levenshteinDistance(String s1, String s2) {
    if (s1.equals(s2)) return s1.length()+1;
        int matches = 0;
    for (int i = 0; i < s1.length() && i < s2.length(); i++) {
        if (s1.charAt(0) != s2.charAt(0)) {
            return 0;
        }
        if (s1.charAt(i) == s2.charAt(i)) {
            matches++;
        }
    }
    // return matches;
    
    int[][] dp = new int[s1.length() + 1][s2.length() + 1];

    for (int i = 1; i <= s1.length(); i++) {
        for (int j = 1; j <= s2.length(); j++) {
            int match = (s1.charAt(i-1) == s2.charAt(j-1)) ? 1 : 0;
            dp[i][j] = Math.max(dp[i-1][j-1] + match, Math.max(dp[i-1][j], dp[i][j-1]));
        }
    }

    return Math.max(dp[s1.length()][s2.length()], matches);
}
}
