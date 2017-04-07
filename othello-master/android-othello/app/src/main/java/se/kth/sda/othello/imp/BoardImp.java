package se.kth.sda.othello.imp;

import java.util.List;
import java.util.Vector;

import se.kth.sda.othello.board.Board;
import se.kth.sda.othello.board.Node;

/**
 * Created by robertog on 2/7/17.
 */
public class BoardImp implements Board {
    Node nodes[][] = new Node[8][8];

    // TODO: Correctly initialize the four inital nodes
    public BoardImp()    {for (int i=0; i<8; i++) {
        for (int j=0; j<8; j++) {

            if(i==3 && j==3)
            {
                nodes[i][j] = new NodeImp("3,3","P2");
            }
            else if (i==4 && j==4)
            {
                nodes[i][j] = new NodeImp("4,4","P2");
            }
            else if(i==4 && j==3)
            {
                nodes[i][j] = new NodeImp("4,3","P1");
            }
            else if (i==3 && j==4)
            {
                nodes[i][j] = new NodeImp("3,4","P1");
            }
            else
                nodes[i][j] = new NodeImp(i,j);
        }
    }
}






    @Override
    public List<Node> getNodes() {
        List<Node> res = new Vector<Node>();
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                res.add(nodes[i][j]);
            }
        }
        return res;
    }

    public void setNode(Node node) {
        int x = node.getXCoordinate();
        int y = node.getYCoordinate();
        nodes[x][y] = node;
    }
       //changes for othello
    public Node getNode(String id) {
        int x = Integer.valueOf(id.split(",")[0]);
        int y = Integer.valueOf(id.split(",")[1]);
        Node node = nodes [x][y];
        return node;
    }
}
