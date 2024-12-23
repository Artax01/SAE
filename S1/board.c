#include <stdio.h>
#include <stdlib.h>
#include "board.h"

/**
 * \file board.c
 *
 * \brief Source code associated with \ref board.h
*/

struct board_s {
	int tab[MAX_DIMENSION][MAX_DIMENSION];
	int n_pos[2];
	int s_pos[2];
	int turn;
	bool is_hex;
	bool use_range;
	bool can_move;
	bool can_kill;
};

/**
 * @brief Fills a non-hexagonal board with content (EMPTY, NORTH_KING, SOUTH_KING, KILLED)
 *
 * @param game the game to consider
 */
void fill_board(board game) {
	for (int l = 0; l < MAX_DIMENSION; l++) {
		for (int c = 0; c < MAX_DIMENSION; c++) {
			if (l == 0 && c == NB_COLS/2) {
				game->tab[l][c] = NORTH_KING;
				game->n_pos[0] = l;
				game->n_pos[1] = c;
			}
			else if (l == NB_LINES-1 && c == NB_COLS/2) {
				game->tab[l][c] = SOUTH_KING;
				game->s_pos[0] = l;
				game->s_pos[1] = c;
			}
			else if (l >= NB_LINES || c >= NB_COLS) {
				game->tab[l][c] = KILLED;
			}
			else {
				game->tab[l][c] = EMPTY;
			}
		}
	}
}

/**
 * @brief Fills an hexagonal board with content (EMPTY, NORTH_KING, SOUTH_KING, KILLED)
 *
 * @param game the board to consider
 */
void fill_board_hex(board game) {
	for (int l = 0; l < MAX_DIMENSION; l++) {
		for (int c = 0; c < MAX_DIMENSION; c++) {
			if (l == 0 && c == HEX_SIDE/2) {
				game->tab[l][c] = NORTH_KING;
				game->n_pos[0] = l;
				game->n_pos[1] = c;
			}
			else if (l == MAX_DIMENSION-1 && c == (MAX_DIMENSION-HEX_SIDE)+(HEX_SIDE/2)) {
				game->tab[l][c] = SOUTH_KING;
				game->s_pos[0] = l;
				game->s_pos[1] = c;
			}
			else if (l < HEX_SIDE-1 && c > (HEX_SIDE-1)+l) {
				game->tab[l][c] = KILLED;
			}
			else if (l > HEX_SIDE-1 && c <= l-HEX_SIDE) {
				game->tab[l][c] = KILLED;
			} else {
				game->tab[l][c] = EMPTY;
			}
		}
	}
}

board new_game(){
	board new_board = malloc(sizeof(struct board_s));
	fill_board(new_board);

	new_board->turn = 0;
	new_board->is_hex = false;
	new_board->use_range = false;
	new_board->can_move = true;
	new_board->can_kill = false;
	return new_board;
}

board new_special_game(bool is_hex, bool use_range) {
	if (!is_hex) { 
		board new_board = new_game();
		if (use_range) { new_board->use_range = true; }
		return new_board;
	}

	board new_board = malloc(sizeof(struct board_s));
	fill_board_hex(new_board);

	new_board->turn = 0;
	new_board->is_hex = is_hex;
	new_board->use_range = use_range;
	new_board->can_move = true;
	new_board->can_kill = false;

	return new_board;
}

board copy_game(board original_game){
	board new_board = malloc(sizeof(struct board_s));
	
	for (int i = 0; i < MAX_DIMENSION; i++) {
		for (int j = 0; j < MAX_DIMENSION; j++) {
			new_board->tab[i][j] = original_game->tab[i][j];
		}
	}

	new_board->n_pos[0] = original_game->n_pos[0];
	new_board->n_pos[1] = original_game->n_pos[1];
	new_board->s_pos[0] = original_game->s_pos[0];
	new_board->s_pos[1] = original_game->s_pos[1];
	new_board->turn = original_game->turn;
	new_board->is_hex = original_game->is_hex;
	new_board->use_range = original_game->use_range;
	new_board->can_move = original_game->can_move;
	new_board->can_kill = original_game->can_kill;
	return new_board;
}

void destroy_game(board game){
	free(game);
};

bool is_hex(board game) {
	return game->is_hex;
}

bool uses_range(board game) {
	return game->use_range;
}

player current_player(board game) {
	player result = NORTH;

	if (game->turn % 2 == 0) { result =  NORTH; }
	else if (game->turn % 2 == 1) { result = SOUTH; }
	return result;
}

cell get_content(board game, int line, int column) {
	if (line < 0 || line >= MAX_DIMENSION || column < 0 || column >= MAX_DIMENSION) { return KILLED; }
	if (game->tab[line][column] == EMPTY) { return EMPTY; }
	else if (game->tab[line][column] == NORTH_KING) { return NORTH_KING; }
	else if (game->tab[line][column] == SOUTH_KING) { return SOUTH_KING; }
	return KILLED;
}

/**
 * @brief Fills the array passed as parameters with the coordinates of the current player
 *
 * @param tab array that will contain the coordinates
 */
void get_position(board game, int tab[]) {
	if(current_player(game) == NORTH) {
		tab[0] = game->n_pos[0];
		tab[1] = game->n_pos[1];
	} else {
		tab[0] = game->s_pos[0];
		tab[1] = game->s_pos[1]; 
	}
}

player get_winner(board game) {
	int pos[2];
	get_position(game, pos);
	
	for (int l = -1; l < 2; l++) {
		for (int c = -1; c < 2; c++) {
			cell cell = get_content(game, pos[0]+l, pos[1]+c);
			if (cell == EMPTY) {
				return NO_PLAYER;
			}
		}
	}
	
	player player = current_player(game);
	if (player == NORTH) { return SOUTH; }
	if (player == SOUTH) { return NORTH; }
	return NO_PLAYER;
}

enum return_code move_toward(board game, direction direction) {
	if(!(game->can_move)) { return RULES; }

	int pos[2];
	get_position(game, pos);
	int x = 0;
	int y = 0;
	
	if (is_hex(game)) {
		if (direction == NE) { direction = N; }
		else if (direction == N) { return RULES; }
		else if (direction == SW) { direction = S; }
		else if (direction == S) { return RULES; }
	}

	switch (direction)
	{
		case SW: x = 1; y = -1; break;
		case S: x = 1; break;
		case SE: x = 1; y = 1; break;
		case W: y = -1; break;
		case E: y = 1; break;
		case NW: x = -1; y = -1; break;
		case N: x = -1; break;
		case NE: x = -1; y = 1; break;
		default: return RULES;
	}

	int state = get_content(game, pos[0]+x, pos[1]+y);
	if (state == EMPTY) {
		player player = current_player(game);

		if (player == NORTH) {
			game->tab[pos[0]+x][pos[1]+y] = NORTH_KING;
			game->n_pos[0] = pos[0]+x;
			game->n_pos[1] = pos[1]+y;
		}
		else if (player == SOUTH) { 
			game->tab[pos[0]+x][pos[1]+y] = SOUTH_KING; 
			game->s_pos[0] = pos[0]+x;
			game->s_pos[1] = pos[1]+y;
		}

		game->tab[pos[0]][pos[1]] = EMPTY;
		game->can_move = false;
		game->can_kill = true;
		return OK;
	}
	else if (state == NORTH_KING || state == SOUTH_KING) {
		return BUSY;
	}

	return OUT;
}

/**
 * @brief Check that if a cell can be killed in ranged mode, it can be reached within at most 3 moves
 *
 * @param game the board to consider
 * @param x the line of the cell to kill
 * @param y the column of the cell to kill
 * @param t the number of times the function is called recursively
 *
 * @return returns whether the move is possible or not
 */
bool can_kill_ranged(board game, int x, int y, int t) {
    player player = current_player(game);
    int pos[2];
    get_position(game, pos);

    if (pos[0] == x && pos[1] == y) { return 1; }
    if (t >= 3) { return 0; }

    for (int i = SW; i <= NE; i++) {
        board new_board = copy_game(game);
        int new_pos[2] = {pos[0], pos[1]}; 

		new_board->can_move = true;
        move_toward(new_board, i);

        if (player == SOUTH) { 
            new_pos[0] = new_board->s_pos[0];
            new_pos[1] = new_board->s_pos[1];
        } else { 
            new_pos[0] = new_board->n_pos[0];
            new_pos[1] = new_board->n_pos[1];
        }
        
        if (new_pos[0] == x && new_pos[1] == y) { return 1; }

        int result = can_kill_ranged(new_board, x, y, t + 1);
        if (result == 1) { return 1; }
    }

    return 0;
}

enum return_code kill_cell(board game, int line, int column) {
	if(!game->can_kill) { return RULES;	}

	cell cell = get_content(game, line, column);

	if (cell == KILLED || line < 0 || line >= MAX_DIMENSION || column < 0 || column >= MAX_DIMENSION) { return OUT; }
	if (cell == NORTH_KING || cell == SOUTH_KING) { return BUSY; }

	if(uses_range(game)) {
		if (!(can_kill_ranged(game, line, column, 0))) { 
			return RULES; 
		}
	}

	game->tab[line][column] = KILLED;
	game->turn += 1;
	game->can_move = true;
	game->can_kill = false;
	return OK;
}