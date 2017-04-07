package se.kth.sda.othello.imp;


import java.util.List;
import java.util.Vector;

import se.kth.sda.othello.Othello;
import se.kth.sda.othello.board.Board;
import se.kth.sda.othello.board.Node;
import se.kth.sda.othello.player.Player;

/**
 * Created by robertog on 2/7/17.
 */
public class OthelloImp implements Othello {
    private final Player playerOne;
    private final Player playerTwo;
    private final BoardImp board;
    private Player currentPlayer;


    public OthelloImp(Player one, Player two) {
        this.playerOne = one;
        this.playerTwo = two;
        this.board = new BoardImp();

    }

    @Override
    public Board getBoard() {
        return board;
    }


    @Override
    public Player getPlayerInTurn() {
        return this.currentPlayer;
    }

    @Override
    public List<Player> getPlayers() {
        List<Player> res = new Vector<Player>();
        res.add(playerOne);
        res.add(playerTwo);
        return res;
    }


    /**
     *  By Payal
     *
     *  hasValidMove- Checks if the player has any valid move to play.
     *  call required helper method
     *
     * @param playerId Id of the current player
     * @return  boolean true if opponent disc is present else false
     *
     */
    @Override
    public boolean hasValidMove(String playerId) {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Node node = new NodeImp(x, y);
                if (!IsNodeOccupied(x, y)) {
                    if (checkIfAdjacentNodesNotNull(x, y)) {
                        if (checkIfOpponentPlayerDiscPresentAdjacentDirections(playerId, node.getId()))
                        {
                            if (checkIfCurrentPlayerDiscPresentAhead(x, y, playerId))
                                return true;
                        }

                    }

                }

            }



        }
        return false;
    }


    /**
     *  By Payal
     *
     *  checkIfCurrentPlayerDiscPresentAhead- For a valid move,
     *  this method checks if in any of the 8 directions there is current player
     *  disc available at end only if opponent disc is present in between
     *  Return true if available else false.
     *
     * @param x  x-coordinate of node object
     * @param y  y - coordinate of node object
     * @param  playerId Id of the current player
     * @return  boolean true if opponent disc is present else false
     *
     */
    private  boolean checkIfCurrentPlayerDiscPresentAhead(int x, int y , String playerId) {

        boolean check_left = false;
        boolean bottom_vertical = false;
        boolean check_right = false;
        boolean top_vertical = false;
        boolean top_left_diagonal = false;
        boolean top_right_diagonal = false;
        boolean bottom_right_diagonal = false;
        boolean bottom_left_diagonal = false;
        if (isOpponentPlayerDisc(x - 1, y, playerId)) {
            check_left = Is_current_player_disc_avail_end_left_horizontal(x - 1, y, playerId);
        }
        if (isOpponentPlayerDisc(x + 1, y, playerId)) {
            check_right = Is_current_player_disc_avail_end_right_horizontal(x + 1, y, playerId);
        }
        if (isOpponentPlayerDisc(x, y + 1, playerId)) {
            bottom_vertical = Is_current_player_disc_avail_end_bottom_vertical(x, y + 1, playerId);

        }
        if (isOpponentPlayerDisc(x, y - 1, playerId)) {

            top_vertical = Is_current_player_disc_avail_end_top_vertical(x, y - 1, playerId);
        }
        if (isOpponentPlayerDisc(x - 1, y - 1, playerId)) {
            top_left_diagonal = Is_current_player_disc_avail_end_TopLeftDiagonal(x - 1, y - 1, playerId);
        }
        if (isOpponentPlayerDisc(x + 1, y - 1, playerId)) {
            top_right_diagonal = Is_current_player_disc_avail_end_TopRightDiagonal(x + 1, y - 1, playerId);
        }
        if (isOpponentPlayerDisc(x + 1, y + 1, playerId)) {
            bottom_right_diagonal = Is_current_player_disc_avail_end_BottomRightDiagonal(x + 1, y + 1, playerId);
        }
        if (isOpponentPlayerDisc(x - 1, y + 1, playerId)) {
            bottom_left_diagonal = Is_current_player_disc_avail_end_BottomLeftDiagonal(x - 1, y + 1, playerId);
        }
        if ((check_left == true) ||
                (bottom_vertical  == true)
                || (check_right  == true) || (top_vertical  == true) || (top_left_diagonal  == true) || (top_right_diagonal == true) ||
                (bottom_right_diagonal == true) || (bottom_left_diagonal == true))
            return true;

        else
            return false;

    }

    /**
     *  By Payal
     *
     *  isActive- Checks if current player has any move to play,
     *  if not swap the player and check the same for second player
     *  if both dont have any move to play the game is not active
     *
     *  @return  boolean true if game is not over, false if game over
     *
     */
    @Override
    public boolean isActive() {
        if(hasValidMove(currentPlayer.getId()))
            return true;
        else  {
            swapPlayer();
            if(hasValidMove(currentPlayer.getId()))
                return true;

        }
        return false;

    }


    /**
     *  By Payal
     *
     *  isMoveValid- Check if a player has made a valid move. Call required
     *  helper methods
     *
     * @param  nodeId
     * @param playerId Id of the current player
     * @return  boolean true if is a valid move ,false if invalid
     *
     */
    @Override
    public boolean isMoveValid(String playerId, String nodeId)throws IllegalStateException {
        int x = Integer.valueOf(nodeId.split(",")[0]);
        int y = Integer.valueOf(nodeId.split(",")[1]);


        if (IsNodeOccupied(x, y))
            return false;

        else if (checkIfAdjacentNodesNotNull(x, y)) {
            if (checkIfOpponentPlayerDiscPresentAdjacentDirections(playerId, nodeId)) {
                if (checkIfCurrentPlayerDiscPresentFurtherAdjacentDirections(x, y, playerId)) {
                    return true;

                } else
                    return false;
            }

            return false;
        }
        return false;
    }


    /**
     *  By Payal
     *
     *  checkIfOpponentPlayerDiscPresentAdjacentDirections- For a valid move,
     *  this method checks if in any of the 8 directions there is opponent disc available.
     *  Return true if available else false.
     *
     * @param  nodeId
     *
     *
     * @param playerId_playing Id of the current player
     * @return  boolean true if opponent disc is present else false
     *
     */

    private boolean checkIfOpponentPlayerDiscPresentAdjacentDirections(String playerId_playing, String nodeId )throws IllegalStateException {
        int x = Integer.valueOf(nodeId.split(",")[0]);
        int y = Integer.valueOf(nodeId.split(",")[1]);

        if (!isOpponentPlayerDisc(x + 1, y, playerId_playing) &&
                !isOpponentPlayerDisc(x, y + 1, playerId_playing) &&
                !isOpponentPlayerDisc(x - 1, y, playerId_playing) &&
                !isOpponentPlayerDisc(x, y - 1, playerId_playing) &&
                !isOpponentPlayerDisc(x + 1, y + 1, playerId_playing) &&
                !isOpponentPlayerDisc(x - 1, y - 1, playerId_playing) &&
                !isOpponentPlayerDisc(x - 1, y + 1, playerId_playing) &&
                !isOpponentPlayerDisc(x + 1, y - 1, playerId_playing))

            return false;
        else
            return true;
    }



    /**
     *  By Payal
     *
     *  checkIfCurrentPlayerDiscPresentFurtherAdjacentDirections- After the method
     *  checkIfOpponentPlayerDiscPresentAdjacentDirections returns true this method
     *  will now check for current player disc to look for valid move.
     *  In all 8 direction it will check if current player disc is available or not.
     *  If available its a valid move and therefore set the node to current player node.
     *  corresponding all 8 direction methods are called if opponent disc is in between
     *  to check for current player disc ahead
     *
     * @param x  x-coordinate of node object
     * @param y  y - coordinate of node object
     * @param  playerId_playing Id of the current player
     * @return  boolean true if opponent disc is present else false
     */

    private boolean checkIfCurrentPlayerDiscPresentFurtherAdjacentDirections(int x,int y, String playerId_playing){
        boolean check_left =false;
        boolean bottom_vertical=false;
        boolean check_right=false;
        boolean top_vertical=false;
        boolean top_left_diagonal=false;
        boolean top_right_diagonal=false;
        boolean bottom_right_diagonal=false;
        boolean bottom_left_diagonal=false;


        if (isOpponentPlayerDisc(x - 1, y, playerId_playing)) {
            check_left = checkCurrentPlayerDiscFurtherLeftHorizontal(x - 1, y, playerId_playing);
        }
        if (isOpponentPlayerDisc(x + 1, y, playerId_playing)) {
            check_right = checkCurrentPlayerDiscFurtherRightHorizontal(x + 1, y, playerId_playing);
        }
        if (isOpponentPlayerDisc(x, y + 1, playerId_playing)) {
            bottom_vertical = checkCurrentPlayerDiscFurtherBottomVertical(x, y + 1, playerId_playing);

        }
        if (isOpponentPlayerDisc(x, y - 1, playerId_playing)) {

            top_vertical = checkCurrentPlayerDiscFurtherTopVertical(x, y - 1, playerId_playing);
        }
        if (isOpponentPlayerDisc(x - 1, y - 1, playerId_playing)) {
            top_left_diagonal = checkCurrentPlayerDiscFurtherTopLeftDiagonal(x - 1, y - 1, playerId_playing);
        }
        if (isOpponentPlayerDisc(x + 1, y - 1, playerId_playing)) {
            top_right_diagonal = checkCurrentPlayerDiscFurtherTopRightDiagonal(x + 1, y - 1, playerId_playing);
        }
        if (isOpponentPlayerDisc(x + 1, y + 1, playerId_playing)) {
            bottom_right_diagonal = checkCurrentPlayerDiscFurtherBottomRightDiagonal(x + 1, y + 1, playerId_playing);
        }
        if (isOpponentPlayerDisc(x - 1, y + 1, playerId_playing))
            bottom_left_diagonal = checkCurrentPlayerDiscFurtherBottomLeftDiagonal(x - 1, y + 1, playerId_playing);

        if ((check_left == true) ||
                (bottom_vertical == true)
                || (check_right == true) || (top_vertical == true) || (top_left_diagonal == true) || (top_right_diagonal == true) ||
                (bottom_right_diagonal == true) || (bottom_left_diagonal == true))
            return true;
        else
            return false;


    }


    /**
     *  By Payal
     *
     *  checkCurrentPlayerDiscFurtherLeftHorizontal - the method first check the boolean value for
     *  method Is_current_player_disc_avail_end_left_horizontal. If its is true then the while
     *  loop continues till current player disc is encountered. Once it is encountered, a for loop
     *  iterates in reverse direction from position  where current player disc is found
     *  to position where player clicked. In each iteration of for loop, every node encountered
     *  is being set to current player id and after exiting the for loop the method returns true;
     *
     * @param i  i-coordinate of node object
     * @param j  j - coordinate of node object
     * @param current_player Id of the current player
     * @return  boolean true if opponent disc is present else false
     */
    private boolean checkCurrentPlayerDiscFurtherLeftHorizontal(int i, int j,String current_player) {

        if (Is_current_player_disc_avail_end_left_horizontal(i,j,current_player))

        {   //not opponent , not null then its current player disc
            // its correct move,set all node from this position to
            int opponent_disc_pos = i;
            while (isOpponentPlayerDisc(opponent_disc_pos, j, current_player))
                opponent_disc_pos--;
            for (int current_player_nodes_positions_to_be_changed = opponent_disc_pos;
                 current_player_nodes_positions_to_be_changed <= i; current_player_nodes_positions_to_be_changed++) {
                String nodeId_current_player = NodeImp.format(current_player_nodes_positions_to_be_changed, j);
                Node newNode = new NodeImp(nodeId_current_player, current_player);

                board.setNode(newNode);
            }
            return true;
        }
        else return false;
    }


    /**
     *  By Payal
     *
     *  Is_current_player_disc_avail_end_left_horizontal  - the method moves forward in top right
     *  diagonal direction to look for current player disc. The while loop continues till it finds the opponent
     *  color dis.If it reached the border it return false. The while loop is exited in two cases
     *  if either it is current player disc or there is no player assosciated with the node. If its second case i.e
     *  node is not occupied it returns false else return true (current player disc)
     *
     * @param i  i-coordinate of node object
     * @param j  j - coordinate of node object
     * @param current_player  Id of the current player
     * @return  boolean true if opponent disc is present else false
     */
    private boolean Is_current_player_disc_avail_end_left_horizontal (int i, int j,String current_player){
        int opponent_disc_pos = i;
        while (isOpponentPlayerDisc(opponent_disc_pos, j, current_player)) {
            if(opponent_disc_pos==0) //reached end and only opponent disc till end,not valid
                return false;
            else opponent_disc_pos--;
        }


        if (!(IsNodeOccupied(opponent_disc_pos, j)))//no node at this position so not valid move
            return false;


        return true;

    }

    /**
     *  By Payal
     *
     *  checkCurrentPlayerDiscFurtherRightHorizontal  - the method first check the boolean value for
     *  method Is_current_player_disc_avail_end_right_horizontal. If its is true then the while
     *  loop continues till current player disc is encountered. Once it is encountered, a for loop
     *  iterates in reverse direction from position  where current player disc is found
     *  to position where player clicked. In each iteration of for loop, every node encountered
     *  is being set to current player id and after exiting the for loop the method returns true;
     *
     * @param i  i-coordinate of node object
     * @param j  j - coordinate of node object
     * @param current_player  Id of the current player
     * @return  boolean true if opponent disc is present else false
     */

    private boolean checkCurrentPlayerDiscFurtherRightHorizontal(int i, int j, String current_player){

        if (Is_current_player_disc_avail_end_right_horizontal(i,j,current_player)){

            //not opponent , not null then its current player disc
            // its correct move,set all node from this position to
            int opponent_disc_pos = i;
            while (isOpponentPlayerDisc(opponent_disc_pos, j, current_player) )
                opponent_disc_pos++;
            for (int current_player_nodes_positions_to_be_changed = opponent_disc_pos;
                 current_player_nodes_positions_to_be_changed >=i; current_player_nodes_positions_to_be_changed--) {
                String nodeId_current_player = NodeImp.format(current_player_nodes_positions_to_be_changed, j);
                Node newNode = new NodeImp(nodeId_current_player, current_player);

                board.setNode(newNode);
            }
            return true;
        }
        else return false;

    }

    /**
     *  By Payal
     *
     *  Is_current_player_disc_avail_end_right_horizontal  - the method moves forward in top right
     *  diagonal direction to look for current player disc. The while loop continues till it finds the opponent
     *  color dis.If it reached the border it return false. The while loop is exited in two cases
     *  if either it is current player disc or there is no player assosciated with the node. If its second case i.e
     *  node is not occupied it returns false else return true (current player disc)
     *
     * @param i  i-coordinate of node object
     * @param j  j - coordinate of node object
     * @param current_player  Id of the current player
     * @return  boolean true if opponent disc is present else false
     */

    private boolean Is_current_player_disc_avail_end_right_horizontal (int i, int j,String current_player){
        int opponent_disc_pos = i;
        while (isOpponentPlayerDisc(opponent_disc_pos, j, current_player) ) {
            if( opponent_disc_pos ==7)//reached end and only opponent disc till end,not valid
                return false;
            else opponent_disc_pos++;

        }

        if (!(IsNodeOccupied(opponent_disc_pos, j)))
            return false;//no node at this position so not valid move

        return true;

    }

    /**
     *  By Payal
     *
     *  checkCurrentPlayerDiscFurtherTopVertical  - the method first check the boolean value for
     *  method Is_current_player_disc_avail_end_top_vertical. If its is true then the while
     *  loop continues till current player disc is encountered. Once it is encountered, a for loop
     *  iterates in reverse direction from position  where current player disc is found
     *  to position where player clicked. In each iteration of for loop, every node encountered
     *  is being set to current player id and after exiting the for loop the method returns true;
     *
     * @param i  i-coordinate of node object
     * @param j  j - coordinate of node object
     * @param current_player  Id of the current player
     * @return  boolean true if opponent disc is present else false
     */

    private boolean checkCurrentPlayerDiscFurtherTopVertical(int i, int j, String current_player) {
        if (Is_current_player_disc_avail_end_top_vertical(i,j,current_player)){ //not opponent , not null then its current player disc
            // its correct move,set all node from this position to
            int opponent_disc_pos = j;
            while (isOpponentPlayerDisc(i, opponent_disc_pos, current_player))
                opponent_disc_pos--;
            for (int current_player_nodes_positions_to_be_changed = opponent_disc_pos;
                 current_player_nodes_positions_to_be_changed <= j; current_player_nodes_positions_to_be_changed++) {
                String nodeId_current_player = NodeImp.format(i, current_player_nodes_positions_to_be_changed);
                Node newNode = new NodeImp(nodeId_current_player, current_player);

                board.setNode(newNode);
            }
            return true;
        }
        else return false;
    }


    /**
     *  By Payal
     *
     *  Is_current_player_disc_avail_end_top_vertical  - the method moves forward in top right
     *  diagonal direction to look for current player disc. The while loop continues till it finds the opponent
     *  color dis.If it reached the border it return false. The while loop is exited in two cases
     *  if either it is current player disc or there is no player assosciated with the node. If its second case i.e
     *  node is not occupied it returns false else return true (current player disc)
     *
     * @param i  i-coordinate of node object
     * @param j  j - coordinate of node object
     * @param current_player Id of the current player
     * @return  boolean true if opponent disc is present else false
     */

    private boolean Is_current_player_disc_avail_end_top_vertical (int i, int j,String current_player){
        int opponent_disc_pos = j;
        while (isOpponentPlayerDisc(i, opponent_disc_pos, current_player)) {
            if( opponent_disc_pos ==0)//reached end and only opponent disc till end,not valid
                return false;
            else opponent_disc_pos--;

        }

        if (!(IsNodeOccupied(i, opponent_disc_pos)))
            return false;//no node at this position so not valid move

        return true;

    }

    /**
     *  By Payal
     *
     *  checkCurrentPlayerDiscFurtherBottomVertical  - the method first check the boolean value for
     *  method Is_current_player_disc_avail_end_bottom_vertical. If its is true then the while
     *  loop continues till current player disc is encountered. Once it is encountered, a for loop
     *  iterates in reverse direction from position  where current player disc is found
     *  to position where player clicked. In each iteration of for loop, every node encountered
     *  is being set to current player id and after exiting the for loop the method returns true;
     *
     * @param i  i-coordinate of node object
     * @param j  j - coordinate of node object
     * @param current_player Id of the current player
     * @return  boolean true if opponent disc is present else false
     */

    private boolean checkCurrentPlayerDiscFurtherBottomVertical(int i, int j,String current_player) {

        if(Is_current_player_disc_avail_end_bottom_vertical(i,j,current_player)){ //not opponent , not null then its current player disc
            // its correct move,set all node from this position to
            int opponent_disc_pos = j;
            while (isOpponentPlayerDisc(i, opponent_disc_pos, current_player))
                opponent_disc_pos++;
            for (int current_player_nodes_positions_to_be_changed = opponent_disc_pos;
                 current_player_nodes_positions_to_be_changed >= j; current_player_nodes_positions_to_be_changed--) {
                String nodeId_current_player = NodeImp.format(i, current_player_nodes_positions_to_be_changed);
                Node newNode = new NodeImp(nodeId_current_player, current_player);

                board.setNode(newNode);
            }
            return true;
        }
        else return false;
    }


    /**
     *  By Payal
     *
     *  Is_current_player_disc_avail_end_bottom_vertical  - the method moves forward in top right
     *  diagonal direction to look for current player disc. The while loop continues till it finds the opponent
     *  color dis.If it reached the border it return false. The while loop is exited in two cases
     *  if either it is current player disc or there is no player assosciated with the node. If its second case i.e
     *  node is not occupied it returns false else return true (current player disc)
     *
     * @param i  i-coordinate of node object
     * @param j  j - coordinate of node object
     * @param current_player Id of the current player
     * @return  boolean true if opponent disc is present else false
     */
    private boolean Is_current_player_disc_avail_end_bottom_vertical (int i, int j,String current_player){
        int opponent_disc_pos = j;
        while (isOpponentPlayerDisc(i, opponent_disc_pos, current_player)) {
            if( opponent_disc_pos ==7)//reached end and only opponent disc till end,not valid
                return false;
            else opponent_disc_pos++;
        }

        if (!(IsNodeOccupied(i, opponent_disc_pos)))
            return false;//no node at this position so not valid move

        return true;

    }

    /**
     *  By Payal
     *
     *  checkCurrentPlayerDiscFurtherTopLeftDiagonal  - the method first check the boolean value for
     *  method Is_current_player_disc_avail_end_TopLeftDiagonal. If its is true then the while
     *  loop continues till current player disc is encountered. Once it is encountered, a for loop
     *  iterates in reverse direction from position  where current player disc is found
     *  to position where player clicked. In each iteration of for loop, every node encountered
     *  is being set to current player id and after exiting the for loop the method returns true;
     *
     * @param i  i-coordinate of node object
     * @param j  j - coordinate of node object
     * @param current_player  Id of the current player
     * @return  boolean true if opponent disc is present else false
     */

    private boolean checkCurrentPlayerDiscFurtherTopLeftDiagonal(int i, int j, String current_player){
        //no node at this position so not valid move

        if(Is_current_player_disc_avail_end_TopLeftDiagonal(i,j,current_player)) { //not opponent , not null then its current player disc
            // its correct move,set all node from this position to
            int opponent_disc_pos_x = i;
            int opponent_disc_pos_y = j;
            while (isOpponentPlayerDisc(opponent_disc_pos_x, opponent_disc_pos_y, current_player) )
            {
                opponent_disc_pos_x--;
                opponent_disc_pos_y--;
            }
            for (int from_disc_x_pos = opponent_disc_pos_x;
                 from_disc_x_pos <= i; from_disc_x_pos++) {

                String nodeId_current_player = NodeImp.format(opponent_disc_pos_x, opponent_disc_pos_y);
                Node newNode = new NodeImp(nodeId_current_player, current_player);

                board.setNode(newNode);
                opponent_disc_pos_x++;
                opponent_disc_pos_y++;
            }
            return true;
        }

        else return false;

    }

    /**
     *  By Payal
     *
     *  Is_current_player_disc_avail_end_TopLeftDiagonal  - the method moves forward in top right
     *  diagonal direction to look for current player disc. The while loop continues till it finds the opponent
     *  color dis.If it reached the border it return false. The while loop is exited in two cases
     *  if either it is current player disc or there is no player assosciated with the node. If its second case i.e
     *  node is not occupied it returns false else return true (current player disc)
     *
     * @param i  i-coordinate of node object
     * @param j  j - coordinate of node object
     * @param current_player Id of the current player
     * @return  boolean true if opponent disc is present else false
     */
    private boolean Is_current_player_disc_avail_end_TopLeftDiagonal (int i, int j,String current_player){
        int opponent_disc_pos_x = i;
        int opponent_disc_pos_y = j;
        while (isOpponentPlayerDisc(opponent_disc_pos_x, opponent_disc_pos_y, current_player) ) {
            if( opponent_disc_pos_x ==0 )//reached end and only opponent disc till end,not valid
                return false;
            else {
                opponent_disc_pos_x--;
                opponent_disc_pos_y--;
            }

        }

        if (!(IsNodeOccupied(opponent_disc_pos_x, opponent_disc_pos_y)))
            return false;//no node at this position so not valid move

        return true;

    }

    /**
     *  By Payal
     *
     *  checkCurrentPlayerDiscFurtherTopRightDiagonal  - the method first check the boolean value for
     *  method Is_current_player_disc_avail_end_TopRightDiagonal. If its is true then the while
     *  loop continues till current player disc is encountered. Once it is encountered, a for loop
     *  iterates in reverse direction from position  where current player disc is found
     *  to position where player clicked. In each iteration of for loop, every node encountered
     *  is being set to current player id and after exiting the for loop the method returns true;
     *
     * @param i  i-coordinate of node object
     * @param j  j - coordinate of node object
     * @param current_player  Id of the current player
     * @return  boolean true if opponent disc is present else false
     */

    private boolean checkCurrentPlayerDiscFurtherTopRightDiagonal(int i, int j, String current_player){
        //no node at this position so not valid move

        if(Is_current_player_disc_avail_end_TopRightDiagonal(i,j,current_player)) { //not opponent , not null then its current player disc
            // its correct move,set all node from this position to
            int opponent_disc_pos_x = i;
            int opponent_disc_pos_y = j;
            while (isOpponentPlayerDisc(opponent_disc_pos_x, opponent_disc_pos_y, current_player) )
            {
                opponent_disc_pos_x++;
                opponent_disc_pos_y--;
            }
            for (int from_disc_x_pos = opponent_disc_pos_x;
                 from_disc_x_pos >= i; from_disc_x_pos--) {

                String nodeId_current_player = NodeImp.format(opponent_disc_pos_x, opponent_disc_pos_y);
                Node newNode = new NodeImp(nodeId_current_player, current_player);
                board.setNode(newNode);

                opponent_disc_pos_x--;
                opponent_disc_pos_y++;
            }
            return true;
        }

        else return false;

    }

    /**
     *  By Payal
     *
     *  Is_current_player_disc_avail_end_TopRightDiagonal  - the method moves forward in top right
     *  diagonal direction to look for current player disc. The while loop continues till it finds the opponent
     *  color dis.If it reached the border it return false. The while loop is exited in two cases
     *  if either it is current player disc or there is no player assosciated with the node. If its second case i.e
     *  node is not occupied it returns false else return true (current player disc)
     *
     * @param i  i-coordinate of node object
     * @param j  j - coordinate of node object
     * @param current_player Id of the current player
     * @return  boolean true if opponent disc is present else false
     */

    private boolean Is_current_player_disc_avail_end_TopRightDiagonal (int i, int j,String current_player){
        int opponent_disc_pos_x = i;
        int opponent_disc_pos_y = j;
        while (isOpponentPlayerDisc(opponent_disc_pos_x, opponent_disc_pos_y, current_player) ) {
            if( opponent_disc_pos_x ==7 )//reached end and only opponent disc till end,not valid
                return false;
            else {
                opponent_disc_pos_x++;
                opponent_disc_pos_y--;
            }

        }

        if (!(IsNodeOccupied(opponent_disc_pos_x, opponent_disc_pos_y)))
            return false;//no node at this position so not valid move

        return true;

    }


    /**
     *  By Payal
     *
     *  checkCurrentPlayerDiscFurtherBottomRightDiagonal  - the method first check the boolean value for
     *  method Is_current_player_disc_avail_end_BottomRightDiagonal. If its is true then the while
     *  loop continues till current player disc is encountered. Once it is encountered, a for loop
     *  iterates in reverse direction from position  where current player disc is found
     *  to position where player clicked. In each iteration of for loop, every node encountered
     *  is being set to current player id and after exiting the for loop the method returns true;
     *
     * @param i  i-coordinate of node object
     * @param j  j - coordinate of node object
     * @param current_player Id of the current player
     * @return  boolean true if opponent disc is present else false
     */
    private boolean checkCurrentPlayerDiscFurtherBottomRightDiagonal(int i, int j, String current_player){
        if (Is_current_player_disc_avail_end_BottomRightDiagonal(i,j,current_player)){ //not opponent , not null then its current player disc
            // its correct move,set all node from this position to
            int opponent_disc_pos_x = i;
            int opponent_disc_pos_y = j;
            while (isOpponentPlayerDisc(opponent_disc_pos_x, opponent_disc_pos_y, current_player) )
            {
                opponent_disc_pos_x++;
                opponent_disc_pos_y++;
            }
            for (int from_disc_x_pos = opponent_disc_pos_x;
                 from_disc_x_pos >= i; from_disc_x_pos--) {

                String nodeId_current_player = NodeImp.format(opponent_disc_pos_x, opponent_disc_pos_y);
                Node newNode = new NodeImp(nodeId_current_player, current_player);
                board.setNode(newNode);
                opponent_disc_pos_x--;
                opponent_disc_pos_y--;
            }
            return true;
        }

        else return false;

    }

    /**
     *  By Payal
     *
     *  Is_current_player_disc_avail_end_BottomRightDiagonal  - the method moves forward in bottom right
     *  diagonal direction to look for current player disc. The while loop continues till it finds the opponent
     *  color dis.If it reached the border it return false. The while loop is exited in two cases
     *  if either it is current player disc or there is no player assosciated with the node. If its second case i.e
     *  node is not occupied it returns false else return true (current player disc)
     *
     * @param i  i-coordinate of node object
     * @param j  j - coordinate of node object
     * @param current_player Id of the current player
     * @return  boolean true if opponent disc is present else false
     */

    private boolean Is_current_player_disc_avail_end_BottomRightDiagonal (int i, int j,String current_player){
        int opponent_disc_pos_x = i;
        int opponent_disc_pos_y = j;
        while (isOpponentPlayerDisc(opponent_disc_pos_x, opponent_disc_pos_y, current_player) ) {
            if( opponent_disc_pos_x ==7 )//reached end and only opponent disc till end,not valid
                return false;
            else {
                opponent_disc_pos_x++;
                opponent_disc_pos_y++;
            }

        }

        if (!(IsNodeOccupied(opponent_disc_pos_x, opponent_disc_pos_y)))
            return false;//no node at this position so not valid move

        return true;

    }

    /**
     *  By Payal
     *
     *  checkCurrentPlayerDiscFurtherBottomLeftDiagonal  - the method first check the boolean value for
     *  method Is_current_player_disc_avail_end_BottomLeftDiagonal. If its is true then the while
     *  loop continues till current player disc is encountered. Once it is encountered, a for loop
     *  iterates in reverse direction from position  where current player disc is found
     *  to position where player clicked. In each iteration of for loop, every node encountered
     *  is being set to current player id and after exiting the for loop the method returns true;
     *
     * @param i  i-coordinate of node object
     * @param j  j - coordinate of node object
     * @param current_player Id of the current player
     * @return  boolean true if opponent disc is present else false
     */

    private boolean checkCurrentPlayerDiscFurtherBottomLeftDiagonal(int i, int j, String current_player){
        if (Is_current_player_disc_avail_end_BottomLeftDiagonal(i,j,current_player)){ //not opponent , not null then its current player disc
            // its correct move,set all node from this position to
            int opponent_disc_pos_x = i;
            int opponent_disc_pos_y = j;
            while (isOpponentPlayerDisc(opponent_disc_pos_x, opponent_disc_pos_y, current_player) )
            {
                opponent_disc_pos_x--;
                opponent_disc_pos_y++;
            }
            for (int from_disc_x_pos = opponent_disc_pos_x;
                 from_disc_x_pos <= i; from_disc_x_pos++) {

                String nodeId_current_player = NodeImp.format(opponent_disc_pos_x, opponent_disc_pos_y);
                Node newNode = new NodeImp(nodeId_current_player, current_player);
                board.setNode(newNode);
                opponent_disc_pos_x++;
                opponent_disc_pos_y--;
            }
            return true;
        }

        else return false;

    }

    /**
     *  By Payal
     *
     *  Is_current_player_disc_avail_end_BottomLeftDiagonal  - the method moves forward in bottom left
     *  diagonal direction to look for current player disc. The while loop continues till it finds the opponent
     *  color dis.If it reached the border it return false. The while loop is exited in two cases
     *  if either it is current player disc or there is no player assosciated with the node. If its second case i.e
     *  node is not occupied it returns false else return true (current player disc)
     *
     * @param i  i-coordinate of node object
     * @param j  j - coordinate of node object
     * @param current_player Id of the current player
     * @return  boolean true if opponent disc is present else false
     */

    private boolean Is_current_player_disc_avail_end_BottomLeftDiagonal(int i, int j,String current_player){
        int opponent_disc_pos_x = i;
        int opponent_disc_pos_y = j;
        while (isOpponentPlayerDisc(opponent_disc_pos_x, opponent_disc_pos_y, current_player) ) {
            if( opponent_disc_pos_x ==0 )//reached end and only opponent disc till end,not valid
                return false;
            else {
                opponent_disc_pos_x--;
                opponent_disc_pos_y++;
            }

        }

        if (!(IsNodeOccupied(opponent_disc_pos_x, opponent_disc_pos_y)))
            return false;//no node at this position so not valid move

        return true;

    }

    /**
     *
     *  By Payal
     *
     *  isOpponentPlayerDisc - the method return true if the node at position x,y
     *  has an opponent player ID associated  with it which means opponent disc is
     *  present at that position. Also return false for out of bound value for x and y
     *
     * @param x  x-coordinate of node object
     * @param y  y - coordinate of node object
     * @param playerId_playing Id of the current player
     * @return  boolean true if opponent disc is present else false
     */
    private boolean isOpponentPlayerDisc(int x, int y,String playerId_playing) {
        //check out of border position
        if (x<0||x>7)
            return false;
        if(y<0||y>7)
            return false;
        String nodeId = NodeImp.format(x,y);
        Node node = board.getNode(nodeId);
        String id_player = node.getOccupantPlayerId();
        if(id_player != null)
            return (!(id_player.equals(playerId_playing)));
        else
            return false;

    }

    /**
     *
     *  By Payal
     *
     *  checkIfAdjacentNodesNotNull - checks the eight different adjacent position of node
     *  with x,y coordinates. return true if any adjacent position has any disc
     *  return false if all 8 positions are null as its not a valid move then
     * @param x  x-coordinate of node object
     * @param y  y - coordinate of node object
     * @return  boolean true or false
     */
    private boolean checkIfAdjacentNodesNotNull(int x,int y) {

        if (IsNodeOccupied(x + 1, y))
            return true;

        else if ((IsNodeOccupied(x - 1, y)))
            return true;

        else if ((IsNodeOccupied(x, y + 1)))

            return true;

        else if ((IsNodeOccupied(x, y -1)))

            return true;

        else if ((IsNodeOccupied(x - 1, y - 1)))

            return true;

        else if ((IsNodeOccupied(x - 1, y + 1)))

            return true;

        else if ((IsNodeOccupied(x + 1, y + 1)))

            return true;

        else if ((IsNodeOccupied(x + 1, y - 1)))

            return true;
        else
            return false;

    }



    /**
     * By Payal
     *
     * IsNodeOccupied - return true if there is no player id assosciated with the node else false
     * Also checks if user click on borders with no neighbours for some position
     * @param x  x-coordinate of node object
     * @param y  y - coordinate of node object
     * @return  boolean true or false
     */

    private boolean IsNodeOccupied(int x, int y) {
        //check out of border position
        if (x<0||x>7)
            return false;
        if(y<0||y>7)
            return false;

        String nodeId = NodeImp.format(x, y);
        Node node = board.getNode(nodeId);
        String id_player = node.getOccupantPlayerId();
        if (id_player == null)
            return false;
        else
            return true;
    }


    private void swapPlayer() {
        if (currentPlayer == playerOne)
            currentPlayer = playerTwo;
        else
            currentPlayer = playerOne;
    }

    @Override
    public List<Node> move() {
        if (currentPlayer.getType() != Player.Type.COMPUTER)
            throw new IllegalStateException("Current player is not a computer");

        swapPlayer();

        return new Vector<>();
    }

    @Override
    public List<Node> move(String playerId, String nodeId) throws IllegalStateException {

        if (currentPlayer.getType() != Player.Type.HUMAN)
            throw new IllegalStateException("Current player is not a human");

        if (!isMoveValid(playerId, nodeId))
            throw new IllegalStateException("Invalid move");
        if (playerId != currentPlayer.getId())
            throw new IllegalStateException("Invalid player ID");

        List<Node> res = new Vector<>();
        Node newNode = new NodeImp(nodeId, playerId);
        board.setNode(newNode);
        res.add(newNode);

        swapPlayer();


        return res;
    }

    @Override
    public void start() {
        // TODO: choose a random player
        currentPlayer = playerOne;
    }

    @Override
    public void start(String playerId) {
        // TODO: choose the player with the right ID
        currentPlayer = playerOne;
    }

}