package code_principale.JEU;

public class IA {

    private static final int PROFONDEUR_MAX = 1; // Profondeur de recherche

    public IA() {
    }

    public int jouerCoup(char[][] grille) {
        int meilleurCoup = -1;
        int meilleurScore = Integer.MIN_VALUE;

        for (int colonne = 0; colonne < grille[0].length; colonne++) {
            if (grille[0][colonne] == 'V') {
                int ligne = trouverLigne(grille, colonne);
                if (ligne != -1) {
                    grille[ligne][colonne] = 'R'; // Simulation
                    int score = minimax(grille, PROFONDEUR_MAX, false); // Évalue le coup adverse
                    grille[ligne][colonne] = 'V'; // Annulation
                    if (score > meilleurScore) {
                        meilleurScore = score;
                        meilleurCoup = colonne;
                    }
                }
            }
        }

        return meilleurCoup;
    }

    private int minimax(char[][] grille, int profondeur, boolean maximisant) {
        if (profondeur == 0) {
            return evaluer(grille);
        }
        if (maximisant) {
            int meilleurScore = Integer.MIN_VALUE;
            for (int colonne = 0; colonne < grille[0].length; colonne++) {
                if (grille[0][colonne] == 'V') {
                    int ligne = trouverLigne(grille, colonne);
                    if (ligne != -1) {
                        grille[ligne][colonne] = 'R'; // Simulations
                        int score = minimax(grille, profondeur - 1, false);
                        grille[ligne][colonne] = 'V';
                        meilleurScore = Math.max(meilleurScore, score);
                    }
                }
            }
            return meilleurScore;
        } else {
            int meilleurScore = Integer.MAX_VALUE;
            for (int colonne = 0; colonne < grille[0].length; colonne++) {
                if (grille[0][colonne] == 'V') {
                    int ligne = trouverLigne(grille, colonne);
                    if (ligne != -1) {
                        grille[ligne][colonne] = 'J';
                        int score = minimax(grille, profondeur - 1, true);
                        grille[ligne][colonne] = 'V';
                        meilleurScore = Math.min(meilleurScore, score);
                    }
                }
            }
            return meilleurScore;
        }
    }

    private int evaluer(char[][] grille) {
        int score = 0;

        // Évaluation des pions alignés horizontalement
        score += evaluerAlignements(grille, 'R'); // Pions ia
        score -= evaluerAlignements(grille, 'J'); // Pions J

        // Évaluation des pions alignés verticalement
        score += evaluerAlignementsVerticaux(grille, 'R');
        score -= evaluerAlignementsVerticaux(grille, 'J');

        // Évaluation des pions alignés en diagonale
        score += evaluerAlignementsDiagonaux(grille, 'R');
        score -= evaluerAlignementsDiagonaux(grille, 'J');


        return score;
    }

    private int evaluerAlignements(char[][] grille, char symbole) {
        int score = 0;
        for (int ligne = 0; ligne < grille.length; ligne++) {
            for (int colonne = 0; colonne <= grille[0].length - 4; colonne++) {
                int count = 0;
                for (int k = 0; k < 4; k++) {
                    if (grille[ligne][colonne + k] == symbole) {
                        count++;
                    }
                }
                if (count == 4) {
                    score += 999; // Pions alignés, l'IA a de bonnes chances de gagner
                } else if (count == 3 && colonne > 0 && colonne < grille[0].length - 4) {
                    if (grille[ligne][colonne - 1] == 'V' || grille[ligne][colonne + 4] == 'V') {
                        score += 99; // Potentiellement gagnant si le coup est joué
                    }
                }
            }
        }
        return score;
    }

    private int evaluerAlignementsVerticaux(char[][] grille, char symbole) {
        int score = 0;
        for (int colonne = 0; colonne < grille[0].length; colonne++) {
            for (int ligne = 0; ligne <= grille.length - 4; ligne++) {
                int count = 0;
                for (int k = 0; k < 4; k++) {
                    if (grille[ligne + k][colonne] == symbole) {
                        count++;
                    }
                }
                if (count == 4) {
                    score += 999; // gagnant presque sur
                } else if (count == 3 && ligne > 0 && ligne < grille.length - 4) {
                    if ((ligne >= 1 && colonne >= 1 && grille[ligne - 1][colonne - 1] == 'V' &&
                            ligne + 3 < grille.length && colonne + 3 < grille[0].length && grille[ligne + 3][colonne + 3] == 'V') ||
                            (ligne >= 1 && colonne <= grille[0].length - 5 && grille[ligne - 1][colonne + 4] == 'V' &&
                                    ligne + 3 < grille.length && colonne - 1 >= 0 && grille[ligne + 3][colonne - 1] == 'V')) {
                        score += 99; // gagnant mais moins sur
                    }
                }
            }
        }
        return score;
    }

    private int evaluerAlignementsDiagonaux(char[][] grille, char symbole) {
        int score = 0;
        for (int ligne = 0; ligne <= grille.length - 4; ligne++) {
            for (int colonne = 0; colonne <= grille[0].length - 4; colonne++) {
                int count1 = 0;
                int count2 = 0;
                for (int k = 0; k < 4; k++) {
                    if (grille[ligne + k][colonne + k] == symbole) {
                        count1++;
                    }
                    if (grille[ligne + k][colonne + 3 - k] == symbole) {
                        count2++;
                    }
                }
                if (count1 == 4 || count2 == 4) {
                    score += 999;
                } else if ((count1 == 3 && ligne > 0 && ligne < grille.length - 4 && colonne > 0 && colonne < grille[0].length - 4) ||
                        (count2 == 3 && ligne > 0 && ligne < grille.length - 4 && colonne >= 3 && colonne < grille[0].length - 1)) {

                    if ((ligne >= 1 && colonne >= 1 && grille[ligne - 1][colonne - 1] == 'V' && (ligne + 4 < grille.length && colonne + 4 < grille[0].length && grille[ligne + 4][colonne + 4] == 'V')) ||
                            (ligne >= 1 && colonne <= grille[0].length - 5 && grille[ligne - 1][colonne + 4] == 'V' && (ligne + 4 < grille.length && colonne - 1 >= 0 && grille[ligne + 4][colonne - 1] == 'V'))) {
                        score += 99;
                    }
                }
            }
        }
        return score;
    }

    private int trouverLigne(char[][] grille, int colonne) {
        // on cherche ligne vide dans une colonne
        for (int ligne = grille.length - 1; ligne >= 0; ligne--) {
            if (grille[ligne][colonne] == 'V') {
                return ligne;
            }
        }
        return -1;
    }
}

