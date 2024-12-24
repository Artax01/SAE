#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "board.h"

/**
 * @file main.c
 * @brief SAE1.01
*/

typedef struct stats_s {
    int turn;
    int ncases_del;
    int move_d;
    int coo_x;
    int coo_y;
} stats_s;

char pseudo[2][25] = {
    "Joueur Nord",
    "Joueur Sud",
};

/* initialize structure for statistics. */
stats_s stats = {};

/**
 * @brief Render a stats chart.
 *
 * @param game the game to consider.
 * @param l indicates the row you are on.
 * @param c indicates the column you are on.
 */
void stats_chart(board game, int l, int c) {
    char * player;
    int limit;
    if (is_hex(game)) { limit = MAX_DIMENSION; } else { limit = NB_COLS; }

    if (current_player(game) == NORTH) { player = pseudo[0]; } else { player = pseudo[1]; }
    if (l == 0 && c > limit-2) { printf("\t\e[1m\e[37mTour: \e[93m%d\e[0m\e[37m", stats.turn/2); }
    if (l == 1 && c > limit-2) { printf("\t\e[1m\e[37mQui doit jouer: \e[1m\e[32m%s\e[0m\e[37m", player); }
    if (l == 2 && c > limit-2) { printf("\t\e[1m\e[37mNombre cases détruites: \e[31m%d\e[0m\e[37m", stats.ncases_del); }
    if (l == 3 && c > limit-2) { printf("\t\e[1m\e[37mDeplacement en: \e[95m%d\e[0m\e[37m", stats.move_d); }
    if (l == 4 && c > limit-2) { printf("\t\e[1m\e[37mDernière case détruite: \e[36m(%d,%d)\e[0m\e[37m", stats.coo_x, stats.coo_y); }
    if (l == 5 && c > limit-2) { printf("\t\e[37m\e[41mRéalisé par un anonyme.\e[0m\e[49m"); }
}

/**
 * @brief Display a screen to select the game mode.
 */
int select_mode() {
    int result = 0;
    while (result != 1 && result != 2 && result != 3 && result != 4) {
        printf("\e[H\e[2J");
        printf("\e[1m\e[4m\e[7mSelection du mode de jeu: \e[0m\n");
        printf("\tVersion classique :\t\t(1)\n");
        printf("\tVersion ranged :\t\t(2)\n");
        printf("\tVersion hexagonal :\t\t(3)\n");
        printf("\tVersion hexagonal+ranged :\t(4)\n");
        printf("\n");
        printf("\e[1m\e[32m> \e[0m");
        while(scanf("%d", &result) == 0) {
            getchar();
            printf("\e[1m\e[32m> \e[0m");
        };
    }
    return result;
}

/**
 * @brief Display a screen to choose nickname.
 */
void select_pseudo() {
    printf("\e[H\e[2J");
    printf("\e[1m\e[4m\e[7mVeuillez choisir un pseudonyme pour le joueur 1: \e[0m\n");
    printf("\e[1m\e[32m> \e[0m");
    scanf("%s", pseudo[0]);
    printf("Ok, Joueur Nord devient %s\n\n", pseudo[0]);
    printf("\e[1m\e[4m\e[7mVeuillez choisir un pseudonyme pour le joueur 2: \e[0m\n");
    printf("\e[1m\e[32m> \e[0m");
    scanf("%s", pseudo[1]);
    printf("Ok, Joueur Nord devient %s\n", pseudo[1]);
}

/**
 * @brief Draw different lines to render the board
 *
 * @param l indicates the row you are on.
 */
void draw_line(int l) {
    for (int c = 0; c <= 4*NB_COLS+4; c++) {
        if (l == 0) {
            if (c == 0) { printf("╔"); }
            else if (c == 4*NB_COLS+4) { printf("╗"); }
            else if (c%4 == 0) { printf("╤"); }
            else { printf("═"); }
        } 
        else if (l == 1) {
            if (c == 0) { printf("╟"); }
            else if (c == 4*NB_COLS+4) { printf("╢"); } 
            else if (c%4 == 0) { printf("┼"); } 
            else { printf("─"); }
        } 
        else if (l == 2) {
            if (c == 0) { printf("╚"); }
            else if (c == 4*NB_COLS+4) { printf("╝"); }
            else if (c%4 == 0) { printf("╧"); }
            else { printf("═"); }
        }
    }
    printf("\n");
}

/**
 * @brief Renders the non-hexagonal board
 *
 * @param game the game to consider
 */
void draw_board(board game) {
    printf("\e[1m\e[3mBienvenue sur le jeu de la SAE 1.01  && 1.02.\e[0m\n\n");
    draw_line(0);
    printf("║");
    for (int c = -1; c < NB_COLS; c++) {
        if (c == -1) { printf("\e[1m%2c ", '\\'); } else { printf("\e[1m%2d ", c); }
        if (c == NB_COLS-1) { printf("║"); } else { printf("│"); }
    }
    printf("\t\e[4m\e[1m\e[36mStats:\e[0m\n"); // stats chart title
    draw_line(1);
    for (int l = 0; l < NB_LINES; l++) {
        printf("\e[1m║%2d │\e[0m", l);
        for (int c = 0; c < NB_COLS; c++) {
            switch (get_content(game, l, c)) {
            case EMPTY: printf("%2c ", ' '); break;
            case NORTH_KING: printf("\e[1m\e[34m%2c \e[0m", 'N'); break;
            case SOUTH_KING: printf("\e[1m\e[91m%2c \e[0m", 'S'); break;
            case KILLED: printf("\e[1m\e[31m\e[40m%2c \e[0m", 'X'); break;
            }
            
            if (c == NB_COLS-1) { printf("║"); } else { printf("│"); }

            stats_chart(game, l, c); // displays the corresponding line in the statistics chart
        }
        printf("\n");
        if (l != NB_LINES-1) { draw_line(1); }
    }
    draw_line(2);
    printf("\n");
}

/**
 * @brief Renders the hexagonal board
 *
 * @param game the game to consider
 */
void draw_line_hex(board game, int l, int n) {
    if (n <= 1) {
        int cols = 0;

        for (int m = MAX_DIMENSION-2*l; m >= 0; m--) { printf(" "); }

        int c_max = HEX_SIDE+l;
        if (n == 0 && l > 0) { c_max -= 1; }

        for (int c = 0; c < c_max; c++) {
            if (n == 0) { 
                if (l == 0) { printf("\e[34m%4d\e[0m", cols); cols++;}
                else { 
                    if (c == 0){ printf("\e[92m%d%2s\e[0m", l-1, "|");}
                    switch(get_content(game, l-1, c)) {
                    case EMPTY: printf("%3s", " "); break;
                    case NORTH_KING: printf("%s\e[1m\e[34m%c\e[0m%s", " ", 'N', " "); break;
                    case SOUTH_KING: printf("%s\e[1m\e[91m%c\e[0m%s", " ", 'S', " "); break;
                    case KILLED: printf("%s\e[1m\e[31m\e[40m%c\e[0m%s", " ", 'X', " "); break;
                    }
                    printf("\e[92m%s\e[0m", "|");
                }
            }
            else { printf("\e[34m%4s\e[0m", "/ \\"); }
        }
        if (n == 0) { 
            if (l == 0) { printf("\t\t\t\e[4m\e[1m\e[36mStats:\e[0m"); }
            else {
                if (l == 1) { printf("\t\t"); }
                else { printf("\t"); }
                stats_chart(game, l-1, MAX_DIMENSION-1); 
            }
        }
        printf("\n");
    }
    else {
        int cols = 4;

        int m_max = 2*l;
        if (n == 3) { m_max += 2; }
        if (n == 2 && l > 4) { m_max = 2*(l-1); }

        for (int m = 0; m < m_max; m++) { printf(" "); }

        int c_max = MAX_DIMENSION-l;
        if (l > 4) { c_max = MAX_DIMENSION-4; }

        for (int c = 0; c < c_max; c++) {
            if (n == 2) { 
                if (l > 4) { printf("\e[34m%4d\e[0m", cols); cols++; }
                else {
                    if (c == 0){ printf("\e[92m%d%2s\e[0m", l+4, "|"); }
                    switch(get_content(game, l+4, c+l)) {
                    case EMPTY: printf("%3s", " "); break;
                    case NORTH_KING: printf("%s\e[1m\e[34m%c\e[0m%s", " ", 'N', " "); break;
                    case SOUTH_KING: printf("%s\e[1m\e[91m%c\e[0m%s", " ", 'S', " "); break;
                    case KILLED: printf("%s\e[1m\e[31m\e[40m%c\e[0m%s", " ", 'X', " "); break;
                    }
                    printf("\e[92m%s\e[0m", "|");
                }
            }
            else if (l <= 4) { printf("\e[34m%4s\e[0m", "\\ /"); }
        }
        if (n == 2) {
            printf("\t");
            stats_chart(game, l+4, MAX_DIMENSION-1); 
        }
        printf("\n");
    }
}

/**
 * @brief Draw hexagonal board.
 *
 * @param game the game to consider.
 *
 * @return returns the number of the selected adjacent square.
 */
void draw_hexagone(board game) {
    printf("\e[1m\e[3mBienvenue sur le jeu de la SAE 1.01 && 1.02\e[0m\n\n");
    for (int l1 = 0; l1 < HEX_SIDE; l1++) {
        draw_line_hex(game, l1, 0);
        draw_line_hex(game, l1, 1);
    }
    for (int l2 = 0; l2 <= HEX_SIDE; l2++) {
        draw_line_hex(game, l2, 2);
        draw_line_hex(game, l2, 3);
    }
    printf("\n");
}

/**
 * @brief Show which player must play.
 *
 * @param game the game to consider.
 */
void qui_joue(board game) {
    int result = current_player(game);
    switch (result) {
    case 0: printf("\e[1m\e[32m%s \e[0m\n", "Aucun joueur ne joue !"); break;
    case 1: printf("\e[1m\e[93m%s, \e[37m%s \e[0m\n", pseudo[0], "à vous de jouer :"); break;
    case 2: printf("\e[1m\e[93m%s, \e[37m%s\e[0m\n", pseudo[1], "à vous de jouer :"); break;
    }
    printf("\n");
}

/**
 * @brief Updates board display.
 *
 * @param game the game to consider.
 */
void rafraichir(board game) {
    printf("\e[H\e[2J");
    if (is_hex(game)) { draw_hexagone(game); }
    else { draw_board(game); }
    qui_joue(game);
}

/**
 * @brief Indicates if a move is possible or not.
 *
 * @param game the game to consider.
 * @param direction indicates the direction in which to move.
 *
 * @return returns the number of the adjacent square selected with a specific color according to the result.
 */
char * is_possible(board game, direction direction) {
    board new_game = copy_game(game);
    int mvt = move_toward(new_game, direction);
    char * result = (char *)malloc(20 * sizeof(char));

    switch (direction){
    case SW: direction += 1; break;
    case S: direction += 1; break;
    case SE: direction += 1; break;
    case W: direction += 1; break;
    case E: direction += 2; break;
    case NW: direction += 2; break;
    case N: direction += 2; break;
    case NE: direction += 2; break;
    }

    if (mvt == OK) { snprintf(result, 20, "%s%d%s", "\e[1m\e[32m", direction, "\e[0m"); }
    else { snprintf(result, 20, "%s%d%s", "\e[1m\e[31m", direction, "\e[0m"); }

    return result;
}

/**
 * @brief Displays a selector for moving around the stage.
 *
 * @param game the game to consider.
 *
 * @return returns the number of the selected adjacent square.
 */
int selecteur(board game) {
    int result;
    if (is_hex(game)) { printf("%s%s %s%s\n", " ", is_possible(game, NW), is_possible(game, NE), " "); } // | 7 | 9 |
    else { printf("%s %s %s\n", is_possible(game, NW), is_possible(game, N), is_possible(game, NE)); } // | 7 | 8 | 9 |
    printf("%s \e[36m%c\e[0m %s\n", is_possible(game, W), '*', is_possible(game, E)); // | 4 | * | 6 |
    if (is_hex(game)) { printf("%s%s %s%s\n", " ", is_possible(game, SW), is_possible(game, SE), " "); } // | 1 | 3 |
    else { printf("%s %s %s\n", is_possible(game, SW), is_possible(game, S), is_possible(game, SE)); } // | 1 | 2 | 3 |
    printf("\n\e[1m\e[36m> \e[0m");
    while(scanf("%d", &result) == 0) {
        getchar();
        printf("\e[1m\e[36m> \e[0m");
    };
    if (result >= 1 && result <= 4) { result -= 1; }
    else if (result >= 6 && result <= 9) { result -= 2; }

    return result;
}

/**
 * @brief Check whether it is possible to move on a square and, if so, carry it out.
 *
 * @param game the game to consider.
 * @param pos indicates the square on which you wish to move.
 *
 * @return returns function status after execution.
 */
int deplacer(board game, int pos) {
    enum return_code result = move_toward(game, pos);
    if (result == OK) {
        stats.move_d = pos;
        rafraichir(game);
        printf("\e[93mDéplacement du pion !\e[0m\n");
        return 1;
    }
    else if (result == OUT) {
        rafraichir(game);
        printf("\e[37m\e[41mCette direction sort du plateau !\e[0m\n");
        printf("\e[37m\e[41mVeuillez choisir une autre direction !\e[0m\n");
        return 0;
    }
    else if (result == BUSY) {
        rafraichir(game);
        printf("\e[37m\e[41mAie, vous ne pouvez allez sur cette case car l'autre joueur est dessus !\e[0m\n");
        printf("\e[37m\e[41mVeuillez choisir une autre direction !\e[0m\n");
        return 0;
    }
    else {
        rafraichir(game);
        printf("\e[37m\e[41mAie, vous ne pouvez allez sur cette case !\e[0m\n");
        printf("\e[37m\e[41mVeuillez choisir une autre direction !\e[0m\n");
        return 0;
    }
    return 0;
}

/**
 * @brief Check whether it is possible to destroy a square and, if so, carry it out.
 *
 * @param game the game to consider.
 *
 * @return returns function status after execution.
 */
int detruire(board game) {
    printf("\e[93mQuel case voulez vous détruire ?\e[0m\n");

    printf("\e[93mLigne de la case: \e[0m");
    scanf("%d", &stats.coo_x);

    printf("\e[93mColonne de la case: \e[0m");
    scanf("%d", &stats.coo_y);

    int result = kill_cell(game, stats.coo_x, stats.coo_y);

    if (result == OK) {
        rafraichir(game);
        printf("\e[93mCase (%d,%d) détruite !\e[0m\n", stats.coo_x,stats.coo_y);
        stats.ncases_del++;
        return 1;
    } 
    else if (result == OUT) {
        rafraichir(game);
        printf("\e[37m\e[41mImpossible de détruire cette case !\e[0m\n");
        printf("\e[37m\e[41mVeuillez choisir une case à détruire !\e[0m\n");
        return 0;
    }
    else if (result == BUSY) {
        rafraichir(game);
        printf("\e[37m\e[41mAie, vous ne pouvez détruire cette case car l'autre joueur est dessus !\e[0m\n");
        printf("\e[37m\e[41mVeuillez choisir une autre case à détruire !\e[0m\n");
        return 0;
    }
    else {
        rafraichir(game);
        printf("\e[37m\e[41mAie, vous ne pouvez détruire cette case !\e[0m\n");
        printf("\e[37m\e[41mVeuillez choisir une autre case !\e[0m\n");
        return 0;
    }
    
}

/**
 * @brief Performs operations to move on to the next round
 *
 * @param game the game to consider
 *
 * @return returns function status after execution.
 */
int suivant(board game) {
    int d, k = 0;
    do {
        d = deplacer(game, selecteur(game));
    } while (d != 1);

    do {
        k = detruire(game);
    } while (k != 1);

    stats.turn++;
    printf("\e[0m");

    return d && k;
}

/**
 * @brief create the game
 *
 * @param n indicates the selected game mode
 *
 * @return returns the newly created part
 */
board create_game(int n) {
    switch (n) {
    case 1: return new_game();
    case 2: return new_special_game(false, true);
    case 3: return new_special_game(true, false);
    case 4: return new_special_game(true, true);
    }
    return NULL;
}

/**
 * @brief Performs operations to launch and manage the game
 */
void lancer_jeu() {
    int mode = select_mode();
    board game = create_game(mode);
    select_pseudo();

    if (mode == 2) { mode = 1; }
    else if (mode == 3) { mode = 2; }
    else if (mode == 4) { mode = 2; }

    while (get_winner(game) == NO_PLAYER) {
        switch (mode) {
        case 1:
            rafraichir(game);
            suivant(game);
            break;
        case 2:
            rafraichir(game);
            suivant(game);
            break;
        }
    }

    printf("\e[H\e[2J\e[0m");
    char * winner = "";
    if (get_winner(game) == NORTH) { winner = pseudo[0]; } else { winner = pseudo[1]; }
    printf("\e[7m\e[93mFin de la partie bien jouer au vainqueur: %s !\e[0m\n", winner);
    destroy_game(game);
}


/**
 * @brief Initializes the program.
 * 
 * @return return the state of the program.
 */
int main(int args, char **argv){
    stats.turn = 2;
    lancer_jeu();
    return 0;
}