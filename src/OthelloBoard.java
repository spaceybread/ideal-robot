import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class OthelloBoard extends JFrame {
    private static final int SIZE = 8;
    private Cell[][] cells = new Cell[SIZE][SIZE];

    private JLabel turnLabel;
    private JLabel messageLabel;
    private int turn = 1; 

    public OthelloBoard() {
        setTitle("Othello");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 650);
        setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel(new GridLayout(SIZE, SIZE));
        initBoard(boardPanel);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setPreferredSize(new Dimension(600, 30));
        bottomPanel.setBackground(new Color(52, 63, 43));

        turnLabel = new JLabel("Turn: Black", SwingConstants.LEFT);
        turnLabel.setForeground(Color.WHITE);
        turnLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        turnLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        messageLabel = new JLabel("Waiting for move...", SwingConstants.RIGHT);
        messageLabel.setForeground(Color.LIGHT_GRAY);
        messageLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        messageLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

        bottomPanel.add(turnLabel, BorderLayout.WEST);
        bottomPanel.add(messageLabel, BorderLayout.EAST);

        add(boardPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void initBoard(JPanel boardPanel) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                final int r = row;
                final int c = col;

                Cell cell = new Cell(0);
                cell.setBackground(new Color(72, 93, 63));
                cell.setOpaque(true);
                cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                cell.addActionListener(e -> handleMove(r, c));

                cells[row][col] = cell;
                boardPanel.add(cell);
            }
        }
        cells[3][3].setState(-1);
        cells[4][4].setState(-1);
        cells[3][4].setState(1);
        cells[4][3].setState(1);
    }

    private void handleMove(int r, int c) {
        Cell cell = cells[r][c]; 

        if (turn == 1) {
            // black turn
            cell.setState(1);
            flipDiscs(r, c);
            updateAdv();
            turn = -1; 
            turnLabel.setText("Turn: White");
        } else {
            // white turn
            cell.setState(-1);
            flipDiscs(r, c);
            updateAdv();
            turn = 1; 
            turnLabel.setText("Turn: Black");
        }
    }

        private void flipDiscs(int r, int c) {
        int toMatch = cells[r][c].getState(); 
        ArrayList<Cell> toFlip = new ArrayList<Cell>(); 
        
        int[][] directions = {
                {1, 0}, {-1, 0}, {0, 1}, {0, -1},  
                {1, 1}, {1, -1}, {-1, 1},{-1, -1}  
            };

            for (int[] dir : directions) {
                int dr = dir[0];
                int dc = dir[1];
                ArrayList<Cell> candidates = new ArrayList<>();

                int i = r + dr;
                int j = c + dc;

                while (i >= 0 && i < SIZE && j >= 0 && j < SIZE) {
                    int state = cells[i][j].getState();

                    if (state == 0) {
                        break;
                    } 
                    else if (state != toMatch) {
                        candidates.add(cells[i][j]); 
                    } else {
                        toFlip.addAll(candidates); 
                        break;
                    }

                    i += dr;
                    j += dc;
                }
            }

            for (Cell cell : toFlip) cell.setState(toMatch);
    }

    private boolean hasValidMoves(int player) {
        return true; 
    }

    private void updateAdv() {
        int adv = 0; 

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                adv += cells[row][col].getState(); 
            }
        }

        if (adv > 0) {
            messageLabel.setText("Black ahead by " + adv + "!");
        } else if (adv < 0) {
            messageLabel.setText("White ahead by " + Math.abs(adv) + "!");
        } else {
            messageLabel.setText("No advantage!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new OthelloBoard());
    }
}

